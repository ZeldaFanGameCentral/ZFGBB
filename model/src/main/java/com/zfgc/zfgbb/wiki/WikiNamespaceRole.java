package com.zfgc.zfgbb.wiki;

import java.util.Map;

public enum WikiNamespaceRole {

	MAIN, TALK, USER, USER_TALK, META, META_TALK, FILE, FILE_TALK, MEDIAWIKI, MEDIAWIKI_TALK,
	TEMPLATE, TEMPLATE_TALK, HELP, HELP_TALK, CATEGORY, CATEGORY_TALK, SPECIAL;

	private static final Map<Integer, WikiNamespaceRole> BY_MEDIAWIKI_ID = Map.ofEntries(
			Map.entry(-1, SPECIAL),
			Map.entry(0, MAIN), Map.entry(1, TALK),
			Map.entry(2, USER), Map.entry(3, USER_TALK),
			Map.entry(4, META), Map.entry(5, META_TALK),
			Map.entry(6, FILE), Map.entry(7, FILE_TALK),
			Map.entry(8, MEDIAWIKI), Map.entry(9, MEDIAWIKI_TALK),
			Map.entry(10, TEMPLATE), Map.entry(11, TEMPLATE_TALK),
			Map.entry(12, HELP), Map.entry(13, HELP_TALK),
			Map.entry(14, CATEGORY), Map.entry(15, CATEGORY_TALK));

	public static WikiNamespaceRole ofMediaWikiNamespaceId(Integer sourceNamespaceId) {
		return sourceNamespaceId == null ? null : BY_MEDIAWIKI_ID.get(sourceNamespaceId);
	}

	public static boolean isTalkNamespaceId(Integer sourceNamespaceId) {
		return sourceNamespaceId != null && sourceNamespaceId > 0 && (sourceNamespaceId % 2) == 1;
	}

	public static Integer subjectNamespaceId(Integer sourceNamespaceId) {
		return isTalkNamespaceId(sourceNamespaceId) ? sourceNamespaceId - 1 : null;
	}

	public static WikiNamespaceRole parse(String value) {
		if (value == null || value.isBlank())
			return null;
		try {
			return valueOf(value.trim());
		}
		catch (IllegalArgumentException unknownRole) {
			return null;
		}
	}
}
