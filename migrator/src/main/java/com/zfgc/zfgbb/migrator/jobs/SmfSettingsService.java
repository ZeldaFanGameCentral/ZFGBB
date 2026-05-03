package com.zfgc.zfgbb.migrator.jobs;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SmfSettingsService {

	private static final Logger log = LoggerFactory.getLogger(SmfSettingsService.class);

	private final DataSource smfDataSource;

	public SmfSettingsService(@Qualifier("smfDataSource") DataSource smfDataSource) {
		this.smfDataSource = smfDataSource;
	}

	public Optional<String> getDefaultTimezone() {
		try {
			JdbcTemplate jdbc = new JdbcTemplate(smfDataSource);
			List<String> rows = jdbc.queryForList(
					"select value from " + JobContextHolder.getTablePrefix() + "settings where variable = ?",
					String.class, "default_timezone");
			Optional<String> tz = rows.stream().filter(v -> v != null && !v.isBlank()).findFirst();
			tz.ifPresent(zone -> log.info("SMF default_timezone is '{}'", zone));
			if (tz.isEmpty()) {
				log.warn("SMF default_timezone setting is not present or empty; users will have no preferred_timezone");
			}
			return tz;
		} catch (Exception e) {
			log.warn("Could not read SMF default_timezone: {}", e.getMessage());
			return Optional.empty();
		}
	}
}
