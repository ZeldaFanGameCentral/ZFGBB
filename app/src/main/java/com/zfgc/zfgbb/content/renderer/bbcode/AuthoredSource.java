package com.zfgc.zfgbb.content.renderer.bbcode;

public record AuthoredSource(String attributeText, int startIndex, int endIndex, int openerLength, int closerLength) {

	public String textIn(String source) {
		return source.substring(startIndex, Math.min(endIndex, source.length()));
	}

	public int bodyStartIndex() {
		return startIndex + openerLength;
	}

	public int bodyEndIndex() {
		return endIndex - closerLength;
	}

	public boolean hasCloser() {
		return closerLength > 0;
	}
}
