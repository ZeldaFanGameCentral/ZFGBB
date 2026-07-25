package com.zfgc.zfgbb.util;


public final class ZfgcStringUtils {
	
	private ZfgcStringUtils() {}

	public static String toPlainSummary(String bbcode) {
		if (bbcode == null) {
			return null;
		}
		String plain = bbcode.replaceAll("\\[[^\\]]*\\]", "").replaceAll("\\s+", " ").strip();
		return plain.isEmpty() ? null : plain;
	}
}