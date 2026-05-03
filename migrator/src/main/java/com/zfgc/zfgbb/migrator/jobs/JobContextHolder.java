package com.zfgc.zfgbb.migrator.jobs;

import javax.sql.DataSource;

public class JobContextHolder {

	public static final String DEFAULT_TABLE_PREFIX = "smf_";

	private static final ThreadLocal<DataSource> DATA_SOURCE = new ThreadLocal<>();
	private static final ThreadLocal<String> ATTACHMENTS_SOURCE_PATH = new ThreadLocal<>();
	private static final ThreadLocal<String> ATTACHMENTS_TARGET_PATH = new ThreadLocal<>();
	private static final ThreadLocal<String> TABLE_PREFIX = new ThreadLocal<>();
	private static final ThreadLocal<String> LEGACY_HOST = new ThreadLocal<>();
	private static final ThreadLocal<String> APP_BASE_URL = new ThreadLocal<>();
	private static final ThreadLocal<Boolean> FORCE = new ThreadLocal<>();

	public static void set(DataSource dataSource,
			String sourcePath,
			String targetPath,
			String tablePrefix,
			String legacyHost,
			String appBaseUrl,
			boolean force) {
		DATA_SOURCE.set(dataSource);
		ATTACHMENTS_SOURCE_PATH.set(sourcePath);
		ATTACHMENTS_TARGET_PATH.set(targetPath);
		TABLE_PREFIX.set(normalizePrefix(tablePrefix));
		LEGACY_HOST.set(normalizeBlankToNull(legacyHost));
		APP_BASE_URL.set(normalizeAppBaseUrl(appBaseUrl));
		FORCE.set(force);
	}

	public static DataSource getDataSource() {
		return DATA_SOURCE.get();
	}

	public static String getAttachmentsSourcePath() {
		return ATTACHMENTS_SOURCE_PATH.get();
	}

	public static String getAttachmentsTargetPath() {
		return ATTACHMENTS_TARGET_PATH.get();
	}

	public static String getTablePrefix() {
		String value = TABLE_PREFIX.get();
		return value != null ? value : DEFAULT_TABLE_PREFIX;
	}

	public static String getLegacyHost() {
		return LEGACY_HOST.get();
	}

	public static String getAppBaseUrl() {
		return APP_BASE_URL.get();
	}

	public static boolean isForce() {
		Boolean value = FORCE.get();
		return value != null && value;
	}

	public static void clear() {
		DATA_SOURCE.remove();
		ATTACHMENTS_SOURCE_PATH.remove();
		ATTACHMENTS_TARGET_PATH.remove();
		TABLE_PREFIX.remove();
		LEGACY_HOST.remove();
		APP_BASE_URL.remove();
		FORCE.remove();
	}

	private static String normalizePrefix(String tablePrefix) {
		if (tablePrefix == null || tablePrefix.isBlank()) {
			return DEFAULT_TABLE_PREFIX;
		}
		return tablePrefix;
	}

	private static String normalizeBlankToNull(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		return value.trim();
	}

	private static String normalizeAppBaseUrl(String value) {
		String trimmed = normalizeBlankToNull(value);
		if (trimmed == null) {
			return null;
		}
		return trimmed.endsWith("/") ? trimmed.substring(0, trimmed.length() - 1) : trimmed;
	}

	private JobContextHolder() {}
}
