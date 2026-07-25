package com.zfgc.zfgbb.services.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Fail-closed classification for backups that may be used as installer content packs.
 */
@Service
public class InstallerCompatibilityService {

	private static final Logger LOG = LoggerFactory.getLogger(InstallerCompatibilityService.class);
	private static final Map<String, Set<String>> AUTH_TABLE_INVENTORY = inventory();
	private static final Map<String, Set<String>> SECURITY_COLUMNS = Map.of(
			"user", Set.of("password_hash", "password_algo", "password_salt",
					"password_changed_ts", "tokens_valid_after_ts"),
			"user_refresh_token", Set.of("user_refresh_token_id", "token_hash",
					"family_id", "successor_id"),
			"account_deletion_request", Set.of("token_sha256"));

	private final DataSource dataSource;

	public InstallerCompatibilityService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Classification classify(Path contentRoot) {
		try (Connection connection = dataSource.getConnection()) {
			assertExactAuthInventory(connection);
			assertNoUnclassifiedSecurityColumns(connection);
			List<Integer> anchors = siteAdministratorIds(connection);
			if (anchors.size() != 1 || anchors.get(0) == null || anchors.get(0) <= 0)
				return Classification.incompatible("site administrator anchor is not unique");
			int anchor = anchors.get(0);
			if (hasCredentialsOutsideAnchor(connection, anchor))
				return Classification.incompatible(
						"usable authentication state exists outside the anchor administrator");
			verifyContentResources(connection, contentRoot);
			return Classification.compatible(anchor);
		} catch (SQLException | IOException | RuntimeException e) {
			LOG.warn("Content pack compatibility proof failed", e);
			return Classification.incompatible("compatibility proof failed");
		}
	}

	private static void assertExactAuthInventory(Connection connection) throws SQLException {
		for (Map.Entry<String, Set<String>> expected : AUTH_TABLE_INVENTORY.entrySet()) {
			Set<String> actual = new LinkedHashSet<>();
			try (PreparedStatement statement = connection.prepareStatement(
					"select column_name from information_schema.columns "
							+ "where table_schema='zfgbb' and table_name=? "
							+ "order by ordinal_position")) {
				statement.setString(1, expected.getKey());
				try (ResultSet result = statement.executeQuery()) {
					while (result.next())
						actual.add(result.getString(1));
				}
			}
			if (!actual.equals(expected.getValue()))
				throw new SQLException("authentication table inventory changed");
		}
	}

	private static void assertNoUnclassifiedSecurityColumns(Connection connection)
			throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				"select table_name,column_name from information_schema.columns "
						+ "where table_schema='zfgbb' and lower(column_name) ~ "
						+ "'(password|token|secret|credential|oauth|mfa|webauthn)' "
						+ "order by table_name,column_name");
				ResultSet result = statement.executeQuery()) {
			while (result.next()) {
				String table = result.getString(1);
				String column = result.getString(2);
				if (!SECURITY_COLUMNS.getOrDefault(table, Set.of()).contains(column))
					throw new SQLException("unclassified security-sensitive column");
			}
		}
	}

	public List<Integer> siteAdministratorIds() {
		try (Connection connection = dataSource.getConnection()) {
			return siteAdministratorIds(connection);
		} catch (SQLException failure) {
			throw new IllegalStateException("Unable to resolve the site administrator anchor.", failure);
		}
	}

	private static List<Integer> siteAdministratorIds(Connection connection) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				"select distinct permissions.user_id "
						+ "from zfgbb.user_permission_view permissions "
						+ "join zfgbb.\"user\" account on account.user_id=permissions.user_id "
						+ "where permissions.permission_code='ZFGC_SITE_ADMIN' "
						+ "and permissions.user_id<>0 and ("
						+ "nullif(account.password_hash,'') is not null "
						+ "or exists(select 1 from zfgbb.user_refresh_token token "
						+ "where token.user_id=permissions.user_id)) "
						+ "order by permissions.user_id");
				ResultSet result = statement.executeQuery()) {
			ArrayList<Integer> anchors = new ArrayList<>();
			while (result.next())
				anchors.add(result.getInt(1));
			return List.copyOf(anchors);
		}
	}

	private static boolean hasCredentialsOutsideAnchor(Connection connection, int anchor)
			throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				"select exists("
						+ "select 1 from zfgbb.\"user\" where user_id<>? and user_id<>0 and ("
						+ "nullif(password_hash,'') is not null "
						+ "or nullif(password_algo,'') is not null "
						+ "or nullif(password_salt,'') is not null) "
						+ "union all select 1 from zfgbb.user_refresh_token where user_id<>? "
						+ "union all select 1 from zfgbb.account_deletion_request where user_id<>?"
						+ ")")) {
			statement.setInt(1, anchor);
			statement.setInt(2, anchor);
			statement.setInt(3, anchor);
			try (ResultSet result = statement.executeQuery()) {
				return !result.next() || result.getBoolean(1);
			}
		}
	}

	private static void verifyContentResources(Connection connection, Path contentRoot)
			throws SQLException, IOException {
		Path normalizedRoot = contentRoot.toAbsolutePath().normalize();
		if (!Files.isDirectory(normalizedRoot, LinkOption.NOFOLLOW_LINKS))
			throw new IOException("content root is unavailable");
		try (PreparedStatement statement = connection.prepareStatement(
				"select content_resource_id,storage_dir,filename,file_size "
						+ "from zfgbb.content_resource order by content_resource_id");
				ResultSet result = statement.executeQuery()) {
			while (result.next()) {
				int id = result.getInt(1);
				String storageDirectory = result.getString(2);
				String filename = result.getString(3);
				Long expectedSize = result.getObject(4, Long.class);
				Path file = resourcePath(normalizedRoot, id, storageDirectory, filename);
				assertNoLinks(normalizedRoot, file);
				if (!Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS))
					throw new IOException("content resource is missing");
				if (expectedSize != null && expectedSize.longValue() != Files.size(file))
					throw new IOException("content resource size differs");
			}
		}
	}

	private static Path resourcePath(Path root, int id, String storageDirectory, String filename)
			throws IOException {
		Path candidate;
		if (storageDirectory == null || storageDirectory.isBlank()) {
			candidate = root.resolve("images").resolve(Integer.toString(id));
		} else {
			if (filename == null || filename.isBlank())
				throw new IOException("content resource filename is missing");
			candidate = root.resolve(storageDirectory).resolve(Integer.toString(id))
					.resolve(filename);
		}
		Path normalized = candidate.toAbsolutePath().normalize();
		if (!normalized.startsWith(root))
			throw new IOException("content resource path escapes the content root");
		return normalized;
	}

	private static void assertNoLinks(Path root, Path file) throws IOException {
		Path relative = root.relativize(file);
		Path current = root;
		for (Path component : relative) {
			current = current.resolve(component);
			if (Files.isSymbolicLink(current))
				throw new IOException("content resource path contains a symbolic link");
		}
	}

	private static Map<String, Set<String>> inventory() {
		Map<String, Set<String>> tables = new LinkedHashMap<>();
		tables.put("user", ordered("user_id", "created_ts", "updated_ts", "sso_key",
				"active_flag", "display_name", "user_name", "migration_hash",
				"password_hash", "password_algo", "password_salt", "locked_until_ts",
				"failed_login_count", "password_changed_ts", "tokens_valid_after_ts"));
		tables.put("user_refresh_token", ordered("user_refresh_token_id", "user_id",
				"token_hash", "issued_ts", "expires_ts", "revoked_flag", "created_ts",
				"updated_ts", "rotated_ts", "revoked_ts", "family_id", "successor_id"));
		tables.put("account_deletion_request", ordered("account_deletion_request_id",
				"user_id", "mode", "status", "token_sha256", "requested_ts",
				"expires_ts", "confirmed_ts", "resend_count", "last_sent_ts",
				"avatar_id_snapshot", "purge_cursor", "recorded_blob_paths", "created_ts",
				"updated_ts"));
		tables.put("email_address", ordered("email_address_id", "email_address", "created_ts",
				"updated_ts", "spammer_flag", "migration_hash"));
		tables.put("user_contact_info", ordered("user_id", "email_address_id",
				"allow_email_flag", "allow_pm_flag", "created_ts", "updated_ts",
				"migration_hash"));
		return Map.copyOf(tables);
	}

	private static Set<String> ordered(String... columns) {
		return Collections.unmodifiableSet(
				new LinkedHashSet<>(Arrays.asList(columns)));
	}

	public record Classification(boolean compatible, Integer anchorAdministratorId, String reason) {
		static Classification compatible(int anchor) {
			return new Classification(true, anchor, null);
		}

		static Classification incompatible(String reason) {
			return new Classification(false, null, reason);
		}
	}
}
