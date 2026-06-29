package com.zfgc.zfgbb.migrator.jobs;

public record SmfConnectionParams(
		String jdbcUrl,
		String username,
		String password,
		String smfTablePrefix,
		String smfLegacyHost,
		String appBaseUrl,
		String attachmentsSourcePath,
		String attachmentsTargetPath,
		String avatarsSourcePath,
		String cmsFilesSourcePath,
		String wikiImagesSourcePath,
		boolean force,
		boolean createMemberWikiPages,
		Integer discussionBoardId,
		Integer resourcesBoardId,
		java.util.Map<String, Integer> talkBoardIds,
		java.util.Map<Integer, java.util.List<String>> groupPermissionMap,
		java.util.Map<String, String> wikiNamespaceCaseModes,
		java.util.Map<String, String> wikiNamespaceAliases,
		java.util.Map<Integer, String> wikiNamespaceIds) {
}
