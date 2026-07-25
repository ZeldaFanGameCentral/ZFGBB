package com.zfgc.zfgbb.migrator.jobs;

import java.util.List;
import java.util.Map;

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
		Map<String, Integer> talkBoardIds,
		Map<Integer, List<String>> groupPermissionMap,
		Map<String, String> wikiNamespaceCaseModes,
		Map<String, String> wikiNamespaceAliases,
		Map<Integer, String> wikiNamespaceIds,
		String wikiLegacyHost) {

	public SmfConnectionParams withForce(boolean force) {
		return new SmfConnectionParams(
				jdbcUrl, username, password, smfTablePrefix, smfLegacyHost, appBaseUrl,
				attachmentsSourcePath, attachmentsTargetPath, avatarsSourcePath, cmsFilesSourcePath,
				wikiImagesSourcePath, force, createMemberWikiPages, discussionBoardId, resourcesBoardId,
				talkBoardIds, groupPermissionMap, wikiNamespaceCaseModes, wikiNamespaceAliases, wikiNamespaceIds, wikiLegacyHost);
	}
}
