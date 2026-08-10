package com.zfgc.zfgbb.operations.postgres;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zfgc.zfgbb.operations.archive.InvalidBackupException;

public final class PostgresDumpTocValidator {
	private static final Pattern ENTRY = Pattern.compile(
			"^\\d+; \\d+ \\d+ ([A-Z][A-Z ]*[A-Z]|[A-Z]+) (\\S+) (.+) (\\S+)$");

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
			if (description.equals("SCHEMA")) {
				if (!namespace.equals("-") || !tag.equals("zfgbb"))
					throw new InvalidBackupException(
							"database dump contains an object outside zfgbb");
			} else if (!namespace.equals("zfgbb")) {
				throw new InvalidBackupException(
						"database dump contains an object outside zfgbb");
			}
		}
		if (entries == 0)
			throw new InvalidBackupException("database dump TOC is empty");
		return entries;
	}
}
