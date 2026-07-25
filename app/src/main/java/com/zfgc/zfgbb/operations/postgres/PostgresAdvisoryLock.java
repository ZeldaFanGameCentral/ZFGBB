package com.zfgc.zfgbb.operations.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PostgresAdvisoryLock implements AutoCloseable {

	private static final Logger LOG = LoggerFactory.getLogger(PostgresAdvisoryLock.class);

	private final Connection session;
	private final long key;
	private final AtomicBoolean released = new AtomicBoolean();

	private PostgresAdvisoryLock(Connection session, long key) {
		this.session = session;
		this.key = key;
	}

	public static Optional<PostgresAdvisoryLock> tryAcquire(DataSource dataSource, long key)
			throws SQLException {
		Connection session = dataSource.getConnection();
		try {
			session.setAutoCommit(true);
			if (!advisoryLock(session, "pg_try_advisory_lock", key)) {
				session.close();
				return Optional.empty();
			}
			return Optional.of(new PostgresAdvisoryLock(session, key));
		} catch (SQLException | RuntimeException unacquirable) {
			discard(session, key);
			throw unacquirable;
		}
	}

	@Override
	public void close() throws SQLException {
		if (!released.compareAndSet(false, true))
			return;
		boolean unlocked;
		try {
			unlocked = advisoryLock(session, "pg_advisory_unlock", key);
		} catch (SQLException | RuntimeException unreleasable) {
			discard(session, key);
			throw unreleasable;
		}
		if (!unlocked) {
			LOG.warn("PostgreSQL reported that advisory lock {} was not held by the session releasing it; "
					+ "the lock is not outstanding, but the session is being discarded because its state "
					+ "no longer matches what this lock believed it held.", key);
			discard(session, key);
			return;
		}
		session.close();
	}

	private static boolean advisoryLock(Connection session, String function, long key) throws SQLException {
		try (PreparedStatement statement = session.prepareStatement("select " + function + "(?)")) {
			statement.setLong(1, key);
			try (ResultSet result = statement.executeQuery()) {
				return result.next() && result.getBoolean(1);
			}
		}
	}

	private static void discard(Connection session, long key) {
		try {
			advisoryLock(session, "pg_advisory_unlock", key);
		} catch (SQLException | RuntimeException unreleasable) {
			LOG.warn("Unable to release advisory lock {} on a discarded session; terminating it so "
					+ "PostgreSQL releases the lock rather than pooling a session that still holds it.",
					key, unreleasable);
			abortQuietly(session);
		}
		closeQuietly(session);
	}

	private static void abortQuietly(Connection session) {
		try {
			session.abort(termination -> termination.run());
		} catch (SQLException | RuntimeException unabortable) {
			LOG.warn("Unable to terminate a discarded advisory-lock session", unabortable);
		}
	}

	private static void closeQuietly(Connection session) {
		try {
			session.close();
		} catch (SQLException | RuntimeException unclosable) {
			LOG.warn("Unable to close a discarded advisory-lock session", unclosable);
		}
	}
}
