package com.zfgc.zfgbb.migrator.converters;

import java.util.Map;

public record LegacyIdMaps(
		Map<Integer, Integer> threadMap,
		Map<Integer, Integer> messageMap,
		Map<Integer, Integer> boardMap,
		Map<Integer, Integer> userMap,
		Map<Integer, Integer> attachmentMap) {

	public static LegacyIdMaps empty() {
		return new LegacyIdMaps(Map.of(), Map.of(), Map.of(), Map.of(), Map.of());
	}
}
