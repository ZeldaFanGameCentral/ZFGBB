package com.zfgc.zfgbb.testsupport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

public final class FixtureSemanticInventory {
	private static final String NORMALIZED =
			"jsonb_strip_nulls(to_jsonb(t)-'created_ts'-'updated_ts')";
	private static final List<Table> TABLES = List.of(
			table("avatar"), table("board"), table("br_board_permission"),
			table("br_user_permission", "where user_id<>1"), table("category"),
			table("content_collection"), table("content_collection_item"),
			table("content_entity"), table("content_resource"), table("content_template"),
			new Table("email_address", NORMALIZED,
					"where not exists(select 1 from zfgbb.user_contact_info a "
							+ "where a.user_id=1 and a.email_address_id=t.email_address_id)"),
			table("file_attachments"), table("ip_address"), table("message"),
			table("message_history"), table("moderation_log"),
			table("notification_subscription"), table("permission"), table("permission_group"),
			table("permission_group_assoc"), table("personal_message"),
			table("personal_message_conversation"), table("personal_message_recipient"),
			table("poll"), table("poll_choice"), table("project"), table("project_download"),
			table("project_news"), table("project_screenshot"), table("project_tag"),
			table("reaction"), table("resource"),
			new Table("system_config", NORMALIZED,
					"where config_key not in ('installed','installed_at',"
							+ "'installed_by_user_id','site_name','content_generation',"
							+ "'authoring_default_content_format')"),
			table("tag"), table("team"), table("team_member"), table("thread"),
			new Table("user",
					"jsonb_strip_nulls(to_jsonb(t)-'created_ts'-'updated_ts'"
							+ "-'password_hash'-'password_algo'-'password_salt'"
							+ "-'locked_until_ts'-'failed_login_count'-'password_changed_ts'"
							+ "-'tokens_valid_after_ts')",
					"where user_id<>1"),
			table("user_award"), table("user_bio_info", "where user_id<>1"),
			table("user_contact_info", "where user_id<>1"),
			table("user_contact_types", "where user_id<>1"),
			table("user_permission_group_assoc"), table("user_poll_choice"),
			table("user_settings", "where user_id<>1"), table("user_warning"),
			table("wiki_namespace"), table("wiki_namespace_alias"), table("wiki_page"),
			table("wiki_page_category"), table("wiki_system_template_page"),
			new Table("wiki_page_revision",
					"case when content like E'[template=UserProfile]\\nuserid=%\\n[/template]' "
							+ "then jsonb_strip_nulls(to_jsonb(t)-'created_ts'-'updated_ts'"
							+ "-'authored_ts'-'migration_hash') "
							+ "else " + NORMALIZED + " end",
					""));

	private FixtureSemanticInventory() {}

	public static List<String> capture(javax.sql.DataSource dataSource, Path contentRoot) throws IOException {
		List<String> entries;
		try (Connection connection = dataSource.getConnection()) {
			List<String> rows = new ArrayList<>();
			try (Statement statement = connection.createStatement()) {
				statement.execute("set time zone 'UTC'");
				for (Table table : TABLES) {
					String sql = "select (" + table.normalizedRow() + ")::text from zfgbb."
							+ table.name() + " t " + table.predicate();
					try (var result = statement.executeQuery(sql)) {
						while (result.next()) {
							String row = result.getString(1);
							rows.add("row\t" + table.name() + "\t"
									+ sha256(row.getBytes(StandardCharsets.UTF_8)));
						}
					}
				}
			}
			entries = rows;
		} catch (SQLException e) {
			throw new IOException("unable to read fixture database inventory", e);
		}
		if (!Files.isDirectory(contentRoot))
			throw new IOException("fixture content root is missing: " + contentRoot);
		try (var paths = Files.walk(contentRoot)) {
			for (Path file : paths.filter(Files::isRegularFile)
					.filter(path -> !contentRoot.relativize(path).startsWith(".zfgbb"))
					.toList()) {
				String relative = contentRoot.relativize(file).toString().replace(file.getFileSystem()
						.getSeparator(), "/");
				entries.add("file\t" + relative + "\t" + Files.size(file) + "\t"
						+ sha256(Files.readAllBytes(file)));
			}
		}
		entries.sort(Comparator.naturalOrder());
		return List.copyOf(entries);
	}

	public static void approve(Path approvedInventory, List<String> captured) throws IOException {
		List<String> header;
		try (var lines = Files.lines(approvedInventory, StandardCharsets.UTF_8)) {
			header = lines.takeWhile(line -> line.startsWith("#")).toList();
		}
		if (header.isEmpty())
			throw new IOException("approved inventory is missing its provenance header: "
					+ approvedInventory);
		Files.writeString(approvedInventory,
				String.join("\n", header) + "\n" + String.join("\n", captured) + "\n",
				StandardCharsets.UTF_8);
	}

	public static List<String> expected() throws IOException {
		try (InputStream input = FixtureSemanticInventory.class
				.getResourceAsStream("/fixture-semantic-inventory.tsv")) {
			if (input == null)
				throw new IOException("fixture semantic inventory resource is missing");
			return new String(input.readAllBytes(), StandardCharsets.UTF_8).lines()
					.filter(line -> !line.isBlank() && !line.startsWith("#"))
					.sorted()
					.toList();
		}
	}

	public static String describeDifference(List<String> expected, List<String> actual) {
		Map<String, Integer> delta = new HashMap<>();
		expected.forEach(entry -> delta.merge(entry, 1, Integer::sum));
		actual.forEach(entry -> delta.merge(entry, -1, Integer::sum));
		List<String> missing = delta.entrySet().stream()
				.filter(entry -> entry.getValue() > 0)
				.sorted(Map.Entry.comparingByKey())
				.limit(20)
				.map(entry -> entry.getKey() + " x" + entry.getValue())
				.toList();
		List<String> unexpected = delta.entrySet().stream()
				.filter(entry -> entry.getValue() < 0)
				.sorted(Map.Entry.comparingByKey())
				.limit(20)
				.map(entry -> entry.getKey() + " x" + -entry.getValue())
				.toList();
		long missingCount = delta.values().stream().filter(value -> value > 0)
				.mapToLong(Integer::longValue).sum();
		long unexpectedCount = delta.values().stream().filter(value -> value < 0)
				.mapToLong(value -> -value).sum();
		return "missing=" + missingCount + " " + missing
				+ "; unexpected=" + unexpectedCount + " " + unexpected;
	}

	private static String sha256(byte[] bytes) {
		try {
			return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private static Table table(String name) {
		return table(name, "");
	}

	private static Table table(String name, String predicate) {
		return new Table(name, NORMALIZED, predicate);
	}

	private record Table(String name, String normalizedRow, String predicate) {}
}
