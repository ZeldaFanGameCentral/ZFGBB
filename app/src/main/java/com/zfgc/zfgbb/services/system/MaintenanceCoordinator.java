package com.zfgc.zfgbb.services.system;

import com.zfgc.zfgbb.persistence.RawSqlAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.operations.maintenance.MutationLeaseProvider;
import com.zfgc.zfgbb.operations.postgres.PostgresAdvisoryLock;

@Service
@RawSqlAccess("transaction-scoped advisory lock")
@RequiredArgsConstructor
public class MaintenanceCoordinator implements MutationLeaseProvider {
	private static final long MAINTENANCE_LOCK_KEY = 0x5A464742424D4149L;
	private static final int ALL_WRITERS = Integer.MAX_VALUE;

	private final DataSource dataSource;
	private final BackupRestoreProperties properties;
	private final Semaphore writers = new Semaphore(ALL_WRITERS, true);
	private final ThreadLocal<AtomicInteger> permitsHeldByThread =
			ThreadLocal.withInitial(AtomicInteger::new);

	public Optional<Lease> tryMutationLease() {
		try {
			return admitWriter(Duration.ZERO) ? Optional.of(writerLease()) : Optional.empty();
		} catch (InterruptedException abandoned) {
			return Optional.empty();
		}
	}

	@Override
	public Lease acquireMutationLease() throws InterruptedException {
		if (!admitWriter(properties.getMutationDrainTimeout()))
			throw new IllegalStateException("Application maintenance is in progress.");
		return writerLease();
	}

	public Lease acquireExclusive(Duration timeout) throws SQLException, InterruptedException {
		long drainDeadline = System.nanoTime() + timeout.toNanos();
		PostgresAdvisoryLock maintenanceWindow = PostgresAdvisoryLock
				.tryAcquire(dataSource, MAINTENANCE_LOCK_KEY)
				.orElseThrow(() -> new SQLException("another replica already holds the maintenance window"));
		try {
			assertSingleReplica();
			if (!writers.tryAcquire(ALL_WRITERS, drainDeadline - System.nanoTime(),
					TimeUnit.NANOSECONDS))
				throw new SQLException("timed out draining active application mutations");
			return new Lease(() -> {
				writers.release(ALL_WRITERS);
				maintenanceWindow.close();
			});
		} catch (SQLException | InterruptedException | RuntimeException failure) {
			try {
				maintenanceWindow.close();
			} catch (SQLException | RuntimeException unreleasable) {
				failure.addSuppressed(unreleasable);
			}
			throw failure;
		}
	}

	private boolean admitWriter(Duration timeout) throws InterruptedException {
		AtomicInteger alreadyHeld = permitsHeldByThread.get();
		boolean admitted = (alreadyHeld.get() > 0 && writers.tryAcquire())
				|| writers.tryAcquire(1, timeout.toNanos(), TimeUnit.NANOSECONDS);
		if (admitted)
			alreadyHeld.incrementAndGet();
		return admitted;
	}

	private Lease writerLease() {
		AtomicInteger alreadyHeld = permitsHeldByThread.get();
		return new Lease(() -> {
			alreadyHeld.decrementAndGet();
			writers.release();
		});
	}

	private void assertSingleReplica() throws SQLException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement("""
				select count(distinct coalesce(host(client_addr), 'local'))
				from pg_stat_activity
				where datname = current_database()
					and application_name = current_setting('application_name')
				""");
				ResultSet result = statement.executeQuery()) {
			if (result.next() && result.getLong(1) > 1)
				throw new SQLException("maintenance requires exactly one active API replica, found "
						+ result.getLong(1));
		}
	}

	@FunctionalInterface
	private interface LeaseRelease {
		void release() throws SQLException;
	}

	public static final class Lease implements MutationLeaseProvider.Lease {
		private final LeaseRelease release;
		private final AtomicBoolean closed = new AtomicBoolean();

		private Lease(LeaseRelease release) {
			this.release = release;
		}

		@Override
		public void close() throws SQLException {
			if (closed.compareAndSet(false, true))
				release.release();
		}
	}
}
