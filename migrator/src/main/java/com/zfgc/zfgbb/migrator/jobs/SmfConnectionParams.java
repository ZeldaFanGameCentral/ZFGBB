package com.zfgc.zfgbb.migrator.jobs;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public record SmfConnectionParams(
		String jdbcUrl,
		String username,
		String password,
		String smfTablePrefix,
		String smfLegacyHost,
		String appBaseUrl,
		String attachmentsSourcePath,
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

	public static final int DEFAULT_SMF_PORT = 3306;

	private static final Pattern SMF_HOST = Pattern
			.compile("[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?|\\[[0-9A-Fa-f:.]+\\]");

	private static final Pattern SMF_DATABASE = Pattern.compile("[A-Za-z0-9_$-]+");

	public static String smfJdbcUrl(String host, Integer port, String database) {
		int resolvedPort = port == null ? DEFAULT_SMF_PORT : port;
		if (host == null || !SMF_HOST.matcher(host).matches())
			throw new IllegalArgumentException("invalid SMF host: " + host);
		if (resolvedPort < 1 || resolvedPort > 65535)
			throw new IllegalArgumentException("invalid SMF port: " + resolvedPort);
		if (database == null || !SMF_DATABASE.matcher(database).matches())
			throw new IllegalArgumentException("invalid SMF database: " + database);
		return "jdbc:mysql://" + host + ":" + resolvedPort + "/" + database;
	}

	public SmfConnectionParams withForce(boolean force) {
		return new SmfConnectionParams(
				jdbcUrl, username, password, smfTablePrefix, smfLegacyHost, appBaseUrl,
				attachmentsSourcePath, avatarsSourcePath, cmsFilesSourcePath,
				wikiImagesSourcePath, force, createMemberWikiPages, discussionBoardId, resourcesBoardId,
				talkBoardIds, groupPermissionMap, wikiNamespaceCaseModes, wikiNamespaceAliases, wikiNamespaceIds, wikiLegacyHost);
	}
}
