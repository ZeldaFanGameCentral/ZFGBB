package com.zfgc.zfgbb.operations.postgres;

import lombok.RequiredArgsConstructor;
import com.zfgc.zfgbb.persistence.RawSqlAccess;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.operations.archive.InvalidBackupException;

@Component
@RawSqlAccess("pg_dump and pg_restore orchestration")
@RequiredArgsConstructor
public class PostgresBackupTool {
	private static final Logger LOG = LoggerFactory.getLogger(PostgresBackupTool.class);
	private static final Pattern MAJOR = Pattern.compile("(\\d+)(?:\\.\\d+)?");
	private static final int MAX_DIAGNOSTIC_BYTES = 64 * 1024;
	private static final int MAX_TOC_ENTRY_BYTES = 512;
	private static final int MAX_TOC_BYTES = 64 * 1024 * 1024;

	private final DataSource dataSource;
	private final ObjectProvider<Flyway> migrationRunnerProvider;
	@Value("${spring.datasource.url}")
	private final String jdbcUrl;
	@Value("${spring.datasource.username}")
	private final String username;
	@Value("${spring.datasource.password}")
	private final String password;
	private final BackupRestoreProperties properties;

	public DatabaseMetadata metadata() throws IOException {
		String schemaVersion = appliedSchemaVersion().orElseThrow(() -> new IOException(
				"No successful Flyway migration is recorded for this database"));
		try (Connection connection = dataSource.getConnection();
				var statement = connection.createStatement();
				var result = statement.executeQuery(
						"select current_database(), "
								+ "current_setting('server_version_num')::integer,"
								+ "pg_database_size(current_database())")) {
			if (!result.next())
				throw new IOException("PostgreSQL metadata query returned no rows");
			int serverMajor = result.getInt(2) / 10_000;
			if (serverMajor != 18)
				throw new IOException("PostgreSQL server major is unsupported: " + serverMajor);
			String version = command(List.of(properties.getPgDump(), "--version"), Map.of());
			int toolMajor = parseMajor(version);
			if (toolMajor != 18)
				throw new IOException("Backup format v1 requires pg_dump major 18, found " + toolMajor);
			return new DatabaseMetadata(result.getString(1), serverMajor, version.trim(),
					toolMajor, schemaVersion, result.getLong(3));
		} catch (SQLException unqueryable) {
			throw new IOException("Unable to read PostgreSQL metadata", unqueryable);
		}
	}

	private Flyway migrationRunner() {
		return Optional.ofNullable(migrationRunnerProvider.getIfAvailable())
				.orElseThrow(() -> new IllegalStateException(
						"Database migrations are managed outside this application "
								+ "(spring.flyway.enabled is false), so backup and restore cannot "
								+ "resolve the database schema version."));
	}

	private Optional<String> appliedSchemaVersion() {
		return appliedSchemaVersion(migrationRunner().info());
	}

	public String expectedSchemaVersion() {
		return expectedSchemaVersion(migrationRunner().info());
	}

	public String requireDatabaseAtExpectedSchemaVersion() {
		MigrationInfoService migrations = migrationRunner().info();
		String applicationSchemaVersion = expectedSchemaVersion(migrations);
		String databaseSchemaVersion = appliedSchemaVersion(migrations)
				.orElseThrow(() -> new IllegalStateException(
						"This database records no applied migration, but this application build "
								+ "expects database schema version " + applicationSchemaVersion
								+ ". Migrate the database before backing it up or restoring into it."));
		if (MigrationVersion.fromVersion(databaseSchemaVersion)
				.equals(MigrationVersion.fromVersion(applicationSchemaVersion)))
			return applicationSchemaVersion;
		throw new IllegalStateException("database schema " + databaseSchemaVersion
				+ " does not match application schema " + applicationSchemaVersion);
	}

	private static Optional<String> appliedSchemaVersion(MigrationInfoService migrations) {
		return highestSchemaVersion(migrations.applied(),
				migration -> !migration.getState().isFailed());
	}

	private static String expectedSchemaVersion(MigrationInfoService migrations) {
		return highestSchemaVersion(migrations.all(),
				migration -> migration.getState().isResolved())
				.orElseThrow(() -> new IllegalStateException(
						"No versioned database migration is resolved on the application classpath."));
	}

	public void requireArchiveSchemaMatchesApplication(String archiveSchemaVersion) {
		String applicationSchemaVersion = requireDatabaseAtExpectedSchemaVersion();
		MigrationVersion archiveVersion = parseSchemaVersion(archiveSchemaVersion);
		if (MigrationVersion.fromVersion(applicationSchemaVersion).equals(archiveVersion))
			return;
		throw new ZfgcInvalidRequestException("archive schema " + archiveVersion.getVersion()
				+ " does not match application schema " + applicationSchemaVersion);
	}

	private static MigrationVersion parseSchemaVersion(String archiveSchemaVersion) {
		if (archiveSchemaVersion == null || archiveSchemaVersion.isBlank())
			throw new ZfgcInvalidRequestException(
					"Backup archive does not record a database schema version.");
		try {
			MigrationVersion parsed = MigrationVersion.fromVersion(archiveSchemaVersion);
			if (!parsed.isPredefined() && parsed.getVersion() != null)
				return parsed;
		} catch (RuntimeException unreadable) {
			throw unreadableSchemaVersion(archiveSchemaVersion);
		}
		throw unreadableSchemaVersion(archiveSchemaVersion);
	}

	private static ZfgcInvalidRequestException unreadableSchemaVersion(String archiveSchemaVersion) {
		return new ZfgcInvalidRequestException(
				"Backup archive records an unreadable database schema version: "
						+ archiveSchemaVersion);
	}

	private static Optional<String> highestSchemaVersion(MigrationInfo[] migrations,
			Predicate<MigrationInfo> accepted) {
		MigrationVersion highest = null;
		for (MigrationInfo migration : migrations) {
			MigrationVersion version = migration.getVersion();
			if (version == null || version.getVersion() == null || !accepted.test(migration))
				continue;
			if (highest == null || version.compareTo(highest) > 0)
				highest = version;
		}
		return Optional.ofNullable(highest).map(MigrationVersion::getVersion);
	}

	public DatabaseMetadata dump(Path destination) throws IOException {
		DatabaseMetadata metadata = metadata();
		dump(destination, metadata);
		return metadata;
	}

	public void dump(Path destination, DatabaseMetadata metadata) throws IOException {
		ConnectionTarget target = target(jdbcUrl, metadata.database());
		command(dumpCommand(properties.getPgDump(), destination, metadata.database()),
				environment(target));
		validateToc(destination);
	}

	static List<String> dumpCommand(String executable, Path destination, String database) {
		List<String> command = new ArrayList<>();
		command.add(executable);
		command.add("--format=custom");
		command.add("--schema=zfgbb");
		command.add("--exclude-table-data=zfgbb.backup_job");
		command.add("--exclude-table-data=zfgbb.install_run");
		command.add("--exclude-table-data=zfgbb.migration_conflict");
		command.add("--exclude-table-data=zfgbb.migrator_attachment_ref_rewrites");
		command.add("--exclude-table-data=zfgbb.migrator_id_map");
		command.add("--exclude-table-data=zfgbb.quote_strip_audit");
		command.add("--exclude-table-data=zfgbb.quote_strip_run");
		command.add("--no-owner");
		command.add("--no-acl");
		command.add("--file=" + destination.toAbsolutePath().normalize());
		command.add(database);
		return List.copyOf(command);
	}

	public void restore(Path dump) throws IOException {
		validateToc(dump);
		DatabaseMetadata metadata = metadata();
		ConnectionTarget target = target(jdbcUrl, metadata.database());
		command(restoreCommand(properties.getPgRestore(), dump, metadata.database()),
				environment(target));
		retirePooledConnections();
	}

	private void retirePooledConnections() throws IOException {
		try {
			if (!dataSource.isWrapperFor(HikariDataSource.class)) {
				LOG.warn("DataSource {} is not Hikari; pooled connections were not retired after the restore",
						dataSource.getClass().getName());
				return;
			}
			HikariPoolMXBean pool = dataSource.unwrap(HikariDataSource.class).getHikariPoolMXBean();
			if (pool == null)
				LOG.warn("Hikari pool not registered; pooled connections were not retired after the restore");
			else
				pool.softEvictConnections();
		} catch (SQLException unreachable) {
			throw new IOException("unable to retire pooled connections after the restore", unreachable);
		}
	}

	static List<String> restoreCommand(String executable, Path dump, String database) {
		List<String> command = new ArrayList<>();
		command.add(executable);
		command.add("--format=custom");
		command.add("--clean");
		command.add("--if-exists");
		command.add("--no-owner");
		command.add("--no-acl");
		command.add("--single-transaction");
		command.add("--dbname=" + database);
		command.add(dump.toAbsolutePath().normalize().toString());
		return List.copyOf(command);
	}

	public int validateToc(Path dump) throws IOException {
		CommandOutput listing = run(List.of(properties.getPgRestore(), "--list",
				dump.toAbsolutePath().normalize().toString()), Map.of(),
				tocByteBudget());
		if (listing.truncated())
			throw new ZfgcInvalidRequestException("archive table of contents exceeds " + properties.getEntries() + " entries");
		try {
			return new PostgresDumpTocValidator().validate(listing.text(), properties.getEntries());
		} catch (InvalidBackupException invalid) {
			throw new ZfgcInvalidRequestException(invalid.getMessage());
		}
	}

	private String command(List<String> command, Map<String, String> extraEnvironment)
			throws IOException {
		return run(command, extraEnvironment, MAX_DIAGNOSTIC_BYTES).text();
	}

	private int tocByteBudget() {
		long budget = (long) properties.getEntries() * MAX_TOC_ENTRY_BYTES;
		return (int) Math.clamp(budget, MAX_DIAGNOSTIC_BYTES, MAX_TOC_BYTES);
	}

	private CommandOutput run(List<String> command, Map<String, String> extraEnvironment, int maxBytes)
			throws IOException {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectErrorStream(true);
		Map<String, String> environment = builder.environment();
		environment.putAll(extraEnvironment);
		Process process = builder.start();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		AtomicBoolean truncated = new AtomicBoolean();
		Thread reader = Thread.ofVirtual().start(
				() -> copyLimited(process.getInputStream(), output, maxBytes, truncated));
		boolean completed;
		try {
			completed = process.waitFor(properties.getCommandTimeout().toMillis(),
					TimeUnit.MILLISECONDS);
			if (!completed) {
				process.destroyForcibly();
				throw new IOException("PostgreSQL command timed out");
			}
			if (!reader.join(Duration.ofSeconds(5)))
				truncated.set(true);
		} catch (InterruptedException abandoned) {
			Thread.currentThread().interrupt();
			process.destroyForcibly();
			throw new IOException("PostgreSQL command interrupted", abandoned);
		}
		String diagnostic = output.toString(StandardCharsets.UTF_8);
		if (process.exitValue() != 0)
			throw new IOException("PostgreSQL command failed: " + sanitize(diagnostic));
		return new CommandOutput(diagnostic, truncated.get());
	}

	private record CommandOutput(String text, boolean truncated) {}

	private Map<String, String> environment(ConnectionTarget target) {
		HashMap<String, String> environment = new HashMap<>();
		environment.put("PGHOST", target.host());
		environment.put("PGPORT", Integer.toString(target.port()));
		environment.put("PGUSER", username);
		environment.put("PGPASSWORD", password);
		if (target.sslMode() != null)
			environment.put("PGSSLMODE", target.sslMode());
		return environment;
	}

	private static ConnectionTarget target(String jdbcUrl, String database) throws IOException {
		if (!jdbcUrl.startsWith("jdbc:postgresql://"))
			throw new IOException("Unsupported PostgreSQL JDBC URL");
		try {
			URI uri = new URI(jdbcUrl.substring("jdbc:".length()));
			String sslMode = null;
			if (uri.getQuery() != null) {
				for (String parameter : uri.getQuery().split("&")) {
					String[] pair = parameter.split("=", 2);
					if (pair.length == 2 && "sslmode".equalsIgnoreCase(pair[0]))
						sslMode = pair[1];
				}
			}
			return new ConnectionTarget(uri.getHost(), uri.getPort() < 0 ? 5432 : uri.getPort(),
					database, sslMode);
		} catch (URISyntaxException unparseable) {
			throw new IOException("Invalid PostgreSQL JDBC URL", unparseable);
		}
	}

	private static int parseMajor(String output) throws IOException {
		Matcher matcher = MAJOR.matcher(output);
		if (!matcher.find())
			throw new IOException("Unable to parse PostgreSQL tool version");
		return Integer.parseInt(matcher.group(1));
	}

	private static void copyLimited(InputStream input, ByteArrayOutputStream output, int maxBytes,
			AtomicBoolean truncated) {
		try (input) {
			byte[] buffer = new byte[4096];
			int retained = 0;
			for (int read; (read = input.read(buffer)) >= 0;) {
				int length = Math.min(read, maxBytes - retained);
				if (length > 0) {
					output.write(buffer, 0, length);
					retained += length;
				}
				if (length < read)
					truncated.set(true);
			}
		} catch (IOException processExitStatusStaysAuthoritative) {
		}
	}

	private static String sanitize(String diagnostic) {
		String bounded = diagnostic.length() <= MAX_DIAGNOSTIC_BYTES
				? diagnostic : diagnostic.substring(0, MAX_DIAGNOSTIC_BYTES);
		String oneLine = bounded.replaceAll("[\\r\\n]+", " ").trim();
		return oneLine.length() <= 1000 ? oneLine : oneLine.substring(0, 1000);
	}

	public record DatabaseMetadata(String database, int serverMajor, String dumpToolVersion,
			int dumpToolMajor, String schemaVersion, long databaseBytes) {}

	private record ConnectionTarget(String host, int port, String database, String sslMode) {}
}
