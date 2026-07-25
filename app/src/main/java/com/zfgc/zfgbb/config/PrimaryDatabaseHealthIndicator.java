package com.zfgc.zfgbb.config;

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
public class PrimaryDatabaseHealthIndicator extends AbstractHealthIndicator {
	private final DataSource dataSource;

	public PrimaryDatabaseHealthIndicator(@Qualifier("dataSource") DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			if (!connection.isValid(2))
				throw new IllegalStateException("application database validation failed");
			builder.up().withDetail("database", connection.getCatalog());
		}
	}
}
