package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.List;
import java.util.regex.Pattern;

public final class BBCodeText implements BBCodeNode {

	public enum TextEscaping {
		PASS_THROUGH,
		VERBATIM_LITERAL
	}

	public static final String LINE_BREAK_MARKUP = "<br/>";

	private static final Pattern AUTHORED_LINE_BREAK_MARKUP = Pattern.compile("<br\\s*/?>", Pattern.CASE_INSENSITIVE);

	private static final Pattern LEADING_BLANK_RUN =
			Pattern.compile("\\A(?:\\s|<br\\s*/?>)+", Pattern.CASE_INSENSITIVE);

	private static final Pattern TRAILING_BLANK_RUN =
			Pattern.compile("(?:\\s|<br\\s*/?>)+\\z", Pattern.CASE_INSENSITIVE);

	public static String withoutLeadingBlankRun(String text) {
		return LEADING_BLANK_RUN.matcher(text).replaceFirst("");
	}

	public static String withoutTrailingBlankRun(String text) {
		return TRAILING_BLANK_RUN.matcher(text).replaceFirst("");
	}

	public static boolean isBlankOnceLineBreaksAreRead(String text) {
		return withoutLeadingBlankRun(text).isEmpty();
	}

	public static String lineBreakMarkupAsNewlines(String sourceText) {
		return AUTHORED_LINE_BREAK_MARKUP.matcher(sourceText).replaceAll("\n");
	}

	private final String sourceText;

	private final TextEscaping escaping;

	BBCodeText(String sourceText, TextEscaping escaping) {
		this.sourceText = sourceText;
		this.escaping = escaping;
	}

	public static BBCodeText passThroughText(String value) {
		return new BBCodeText(value, TextEscaping.PASS_THROUGH);
	}

	public String sourceText() {
		return sourceText;
	}

	public TextEscaping escaping() {
		return escaping;
	}

	@Override
	public List<BBCodeNode> children() {
		return List.of();
	}
}
