package com.zfgc.zfgbb.wiki;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;

public record WikiTitle(String namespace, String title) {

	public enum CaseMode { FIRST_LETTER, CASE_SENSITIVE }

	private static final Map<String, String> NAMESPACE_ALIASES = Map.ofEntries(
			Map.entry("", "MAIN"), Map.entry("main", "MAIN"),
			Map.entry("template", "Template"), Map.entry("category", "Category"),
			Map.entry("file", "File"), Map.entry("image", "File"),
			Map.entry("user", "User"), Map.entry("talk", "Talk"),
			Map.entry("special", "Special"), Map.entry("mediawiki", "MediaWiki"),
			Map.entry("project", "Project"), Map.entry("resource", "Resource"),
			Map.entry("site", "Site"));

	public static WikiTitle parse(String path) {
		String value = normalizeText(path);
		int colon = value.indexOf(':');
		if (colon <= 0)
			return new WikiTitle("MAIN", normalizeTitle(value, CaseMode.FIRST_LETTER));
		String candidate = value.substring(0, colon).trim().toLowerCase(Locale.ROOT);
		String namespace = NAMESPACE_ALIASES.get(candidate);
		if (namespace == null)
			return new WikiTitle("MAIN", normalizeTitle(value, CaseMode.FIRST_LETTER));
		return new WikiTitle(namespace,
				normalizeTitle(value.substring(colon + 1), CaseMode.FIRST_LETTER));
	}

	public static WikiTitle of(String namespace, String title, CaseMode caseMode) {
		String normalizedNamespace = normalizeText(namespace).trim();
		String alias = NAMESPACE_ALIASES.get(normalizedNamespace.toLowerCase(Locale.ROOT));
		return new WikiTitle(alias == null ? normalizedNamespace : alias, normalizeTitle(title, caseMode));
	}

	public static String normalizeTitle(String value, CaseMode caseMode) {
		String normalized = normalizeText(value).replace('_', ' ').trim().replaceAll("\\s+", " ");
		if (normalized.isEmpty() || caseMode == CaseMode.CASE_SENSITIVE)
			return normalized;
		int firstLength = Character.charCount(normalized.codePointAt(0));
		return normalized.substring(0, firstLength).toUpperCase(Locale.ROOT) + normalized.substring(firstLength);
	}

	private static String normalizeText(String value) {
		return Normalizer.normalize(value == null ? "" : value, Normalizer.Form.NFC);
	}

	public String path() {
		return "MAIN".equals(namespace) ? title.replace(' ', '_')
				: namespace + ":" + title.replace(' ', '_');
	}

	public String persistenceKey() {
		return namespace.toLowerCase(Locale.ROOT) + ":" + title;
	}
}
