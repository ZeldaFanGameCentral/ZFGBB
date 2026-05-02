package com.zfgc.zfgbb.migrator;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;

public class SmfRoutingDataSource extends AbstractDataSource {

	@Override
	public Connection getConnection() throws SQLException {
		return resolve().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return resolve().getConnection(username, password);
	}

	private DataSource resolve() {
		DataSource ds = JobContextHolder.getDataSource();
		if (ds == null) {
			throw new IllegalStateException(
					"No SMF datasource configured for the current job. "
							+ "Connection parameters must be provided with the job request.");
		}
		return ds;
	}
}
