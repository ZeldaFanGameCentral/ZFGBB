package com.zfgc.zfgbb.config;

import lombok.RequiredArgsConstructor;
import com.zfgc.zfgbb.persistence.RawSqlAccess;
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.health.contributor.AbstractHealthIndicator;
import org.springframework.boot.health.contributor.Health;
import org.springframework.stereotype.Component;

/**
 * Checks only the application database. The migrator datasource is intentionally
 * request-scoped and has no connection until an administrator starts a migration.
 */
@Component
@RawSqlAccess("liveness probe")
@RequiredArgsConstructor
public class PrimaryDatabaseHealthIndicator extends AbstractHealthIndicator {
	@Qualifier("dataSource")
	private final DataSource dataSource;

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			if (!connection.isValid(2))
				throw new IllegalStateException("application database validation failed");
			builder.up().withDetail("database", connection.getCatalog());
		}
	}
}
