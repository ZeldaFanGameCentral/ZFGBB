package com.zfgc.zfgbb.migrator.jobs;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import com.zfgc.zfgbb.wiki.WikiTitle;

public class JobContextHolder {

	public static final String DEFAULT_TABLE_PREFIX = "smf_";

	private static final Pattern VALID_TABLE_PREFIX = Pattern.compile("^[A-Za-z0-9_]+$");

	private static final ThreadLocal<DataSource> DATA_SOURCE = new ThreadLocal<>();
	private static final ThreadLocal<String> ATTACHMENTS_SOURCE_PATH = new ThreadLocal<>();
	private static final ThreadLocal<String> ATTACHMENTS_TARGET_PATH = new ThreadLocal<>();
	private static final ThreadLocal<String> AVATARS_SOURCE_PATH = new ThreadLocal<>();
	private static final ThreadLocal<String> CMS_FILES_SOURCE_PATH = new ThreadLocal<>();
	private static final ThreadLocal<String> WIKI_IMAGES_SOURCE_PATH = new ThreadLocal<>();
	private static final ThreadLocal<String> TABLE_PREFIX = new ThreadLocal<>();
	private static final ThreadLocal<String> LEGACY_HOST = new ThreadLocal<>();
	private static final ThreadLocal<String> APP_BASE_URL = new ThreadLocal<>();
	private static final ThreadLocal<Boolean> FORCE = new ThreadLocal<>();
	private static final ThreadLocal<Boolean> CREATE_MEMBER_WIKI_PAGES = new ThreadLocal<>();
	private static final ThreadLocal<Integer> DISCUSSION_BOARD_ID = new ThreadLocal<>();
	private static final ThreadLocal<Integer> RESOURCES_BOARD_ID = new ThreadLocal<>();
	private static final ThreadLocal<Map<String, Integer>> TALK_BOARD_IDS = new ThreadLocal<>();
	private static final ThreadLocal<Map<Integer, List<String>>> GROUP_PERMISSION_MAP = new ThreadLocal<>();
	private static final ThreadLocal<Map<String, String>> WIKI_NAMESPACE_CASE_MODES = new ThreadLocal<>();
	private static final ThreadLocal<Map<String, String>> WIKI_NAMESPACE_ALIASES = new ThreadLocal<>();
	private static final ThreadLocal<Map<Integer, String>> WIKI_NAMESPACE_IDS = new ThreadLocal<>();

	public static void set(DataSource dataSource,
			String sourcePath,
			String targetPath,
			String avatarsSourcePath,
			String cmsFilesSourcePath,
			String wikiImagesSourcePath,
			String tablePrefix,
			String legacyHost,
			String appBaseUrl,
			boolean force,
			boolean createMemberWikiPages,
			Integer discussionBoardId,
			Integer resourcesBoardId,
			Map<String, Integer> talkBoardIds,
			Map<Integer, List<String>> groupPermissionMap,
			Map<String, String> wikiNamespaceCaseModes,
			Map<String, String> wikiNamespaceAliases,
			Map<Integer, String> wikiNamespaceIds) {
		DATA_SOURCE.set(dataSource);
		ATTACHMENTS_SOURCE_PATH.set(sourcePath);
		ATTACHMENTS_TARGET_PATH.set(targetPath);
		AVATARS_SOURCE_PATH.set(avatarsSourcePath);
		CMS_FILES_SOURCE_PATH.set(normalizeBlankToNull(cmsFilesSourcePath));
		WIKI_IMAGES_SOURCE_PATH.set(normalizeBlankToNull(wikiImagesSourcePath));
		TABLE_PREFIX.set(normalizePrefix(tablePrefix));
		LEGACY_HOST.set(normalizeBlankToNull(legacyHost));
		APP_BASE_URL.set(normalizeAppBaseUrl(appBaseUrl));
		FORCE.set(force);
		CREATE_MEMBER_WIKI_PAGES.set(createMemberWikiPages);
		DISCUSSION_BOARD_ID.set(discussionBoardId);
		RESOURCES_BOARD_ID.set(resourcesBoardId);
		TALK_BOARD_IDS.set(talkBoardIds);
		GROUP_PERMISSION_MAP.set(groupPermissionMap);
		WIKI_NAMESPACE_CASE_MODES.set(wikiNamespaceCaseModes);
		WIKI_NAMESPACE_ALIASES.set(wikiNamespaceAliases);
		WIKI_NAMESPACE_IDS.set(wikiNamespaceIds);
	}

	public static void set(DataSource dataSource, String sourcePath, String targetPath, String avatarsSourcePath,
			String cmsFilesSourcePath, String wikiImagesSourcePath, String tablePrefix, String legacyHost,
			String appBaseUrl, boolean force, boolean createMemberWikiPages, Integer discussionBoardId,
			Integer resourcesBoardId, Map<String, Integer> talkBoardIds,
			Map<Integer, List<String>> groupPermissionMap) {
		set(dataSource, sourcePath, targetPath, avatarsSourcePath, cmsFilesSourcePath, wikiImagesSourcePath,
				tablePrefix, legacyHost, appBaseUrl, force, createMemberWikiPages, discussionBoardId,
				resourcesBoardId, talkBoardIds, groupPermissionMap, null, null, null);
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

	public static String getAvatarsSourcePath() {
		return AVATARS_SOURCE_PATH.get();
	}

	public static String getCmsFilesSourcePath() {
		return CMS_FILES_SOURCE_PATH.get();
	}

	public static String getWikiImagesSourcePath() {
		return WIKI_IMAGES_SOURCE_PATH.get();
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

	public static boolean isCreateMemberWikiPages() {
		Boolean value = CREATE_MEMBER_WIKI_PAGES.get();
		return value != null && value;
	}

	public static Integer getDiscussionBoardId() {
		return DISCUSSION_BOARD_ID.get();
	}

	public static Integer getResourcesBoardId() {
		Integer value = RESOURCES_BOARD_ID.get();
		return value != null ? value : DISCUSSION_BOARD_ID.get();
	}

	public static List<String> getGroupPermissionCodes(Integer legacyGroupId) {
		Map<Integer, List<String>> mapping = GROUP_PERMISSION_MAP.get();
		return mapping == null ? null : mapping.get(legacyGroupId);
	}

	public static Integer getTalkBoardId(String subjectNamespace) {
		Map<String, Integer> mapping = TALK_BOARD_IDS.get();
		Integer value = mapping == null ? null : mapping.get(subjectNamespace);
		return value != null ? value : DISCUSSION_BOARD_ID.get();
	}
	public static Map<String, String> getWikiNamespaceCaseModes() { return WIKI_NAMESPACE_CASE_MODES.get(); }
	public static Map<String, String> getWikiNamespaceAliases() { return WIKI_NAMESPACE_ALIASES.get(); }
	public static Map<Integer, String> getWikiNamespaceIds() { return WIKI_NAMESPACE_IDS.get(); }
	public static WikiTitle.CaseMode getWikiNamespaceCaseMode(String namespace) {
		String configured = WIKI_NAMESPACE_CASE_MODES.get() == null ? null : WIKI_NAMESPACE_CASE_MODES.get().get(namespace);
		return configured == null ? WikiTitle.CaseMode.FIRST_LETTER
				: WikiTitle.CaseMode.valueOf(configured);
	}
	public static void setResolvedWikiNamespaceCaseModes(Map<String, String> modes) {
		WIKI_NAMESPACE_CASE_MODES.set(Map.copyOf(modes));
	}

	public static void clear() {
		DATA_SOURCE.remove();
		ATTACHMENTS_SOURCE_PATH.remove();
		ATTACHMENTS_TARGET_PATH.remove();
		AVATARS_SOURCE_PATH.remove();
		CMS_FILES_SOURCE_PATH.remove();
		WIKI_IMAGES_SOURCE_PATH.remove();
		TABLE_PREFIX.remove();
		LEGACY_HOST.remove();
		APP_BASE_URL.remove();
		FORCE.remove();
		CREATE_MEMBER_WIKI_PAGES.remove();
		DISCUSSION_BOARD_ID.remove();
		RESOURCES_BOARD_ID.remove();
		TALK_BOARD_IDS.remove();
		GROUP_PERMISSION_MAP.remove();
		WIKI_NAMESPACE_CASE_MODES.remove();
		WIKI_NAMESPACE_ALIASES.remove();
		WIKI_NAMESPACE_IDS.remove();
	}

	private static String normalizePrefix(String tablePrefix) {
		if (tablePrefix == null || tablePrefix.isBlank()) {
			return DEFAULT_TABLE_PREFIX;
		}
		String trimmed = tablePrefix.trim();
		if (!VALID_TABLE_PREFIX.matcher(trimmed).matches()) {
			throw new IllegalArgumentException(
					"Invalid migrator table prefix '" + trimmed + "': must match ^[A-Za-z0-9_]+$");
		}
		return trimmed;
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
