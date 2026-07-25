package com.zfgc.zfgbb.operations.postgres;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zfgc.zfgbb.operations.archive.InvalidBackupException;

/**
 * Parses pg_restore's stable list format and confines every accepted object to
 * the zfgbb schema. Searching for the word "zfgbb" is insufficient because an
 * attacker can put it in an object name or owner field.
 */
public final class PostgresDumpTocValidator {
	private static final Pattern ENTRY = Pattern.compile(
			"^\\d+; \\d+ \\d+ ([A-Z][A-Z ]*[A-Z]|[A-Z]+) (\\S+) (.+) (\\S+)$");

	/**
	 * Object kinds that can reach outside the zfgbb schema even when pg_dump reports them
	 * inside it, or that grant privileges. Everything else is permitted on the strength of
	 * the namespace confinement below: pg_dump's vocabulary is open ended, so an allowlist
	 * silently breaks the day an ordinary migration adds a COMMENT, matview or procedure.
	 */
	private static final Set<String> DENIED = Set.of(
			"ACL", "DATABASE", "EVENT TRIGGER", "EXTENSION", "FOREIGN DATA WRAPPER",
			"FOREIGN TABLE", "LARGE OBJECT", "POLICY", "PUBLICATION", "ROLE", "SERVER",
			"SUBSCRIPTION", "TABLESPACE", "USER MAPPING");

	public int validate(String toc, int maximumEntries) throws InvalidBackupException {
		if (toc == null || maximumEntries <= 0)
			throw new InvalidBackupException("database dump TOC input is invalid");
		int entries = 0;
		for (String raw : toc.lines().toList()) {
			if (raw.isBlank() || raw.startsWith(";"))
				continue;
			if (++entries > maximumEntries)
				throw new InvalidBackupException("database dump TOC count exceeded");
			Matcher matcher = ENTRY.matcher(raw);
			if (!matcher.matches() || DENIED.contains(matcher.group(1)))
				throw new InvalidBackupException(
						"database dump contains an unknown or forbidden object");
			String description = matcher.group(1);
			String namespace = matcher.group(2);
			String tag = matcher.group(3);
			if ("SCHEMA".equals(description)) {
				if (!"-".equals(namespace) || !"zfgbb".equals(tag))
					throw new InvalidBackupException(
							"database dump contains an object outside zfgbb");
			} else if (!"zfgbb".equals(namespace)) {
				throw new InvalidBackupException(
						"database dump contains an object outside zfgbb");
			}
		}
		if (entries == 0)
			throw new InvalidBackupException("database dump TOC is empty");
		return entries;
	}
}
