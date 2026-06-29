package com.zfgc.zfgbb.migrator.web;

public record MigrateMemberGroupsRequest(
		String smfHost,
		Integer smfPort,
		String smfDatabase,
		String smfUser,
		String smfPassword,
		String smfTablePrefix) {
}
