package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public enum ContentLevel {

	BLOCK,
	INLINE;

	private static final Set<String> BLOCK_ELEMENT_NAMES = Set.of("div", "table", "thead", "tbody", "tr", "td",
			"th", "ul", "ol", "li", "pre", "hr", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote", "p");

	public static ContentLevel theContentLevelOfMarkup(String openMarkup, String closeMarkup) {
		return anyElementNamedIsBlockLevel(openMarkup) || anyElementNamedIsBlockLevel(closeMarkup)
				? BLOCK
				: INLINE;
	}

	public static Set<ContentLevel> everyContentLevelDeclaredBy(BBCodeConfig config) {
		Set<ContentLevel> declared = new LinkedHashSet<>();
		for (BBCodeAttributeMode mode : config.getAttributeConfig().values())
			declared.add(theContentLevelOfMarkup(mode.getOpenTag(), mode.getCloseTag()));
		if (declared.isEmpty())
			declared.add(theContentLevelOfMarkup("", config.getEndTag()));
		return declared;
	}

	public static List<String> everyElementNamedIn(String markup) {
		List<String> named = new ArrayList<>();
		if (markup == null)
			return named;
		for (int elementStart = markup.indexOf('<'); elementStart >= 0;
				elementStart = markup.indexOf('<', elementStart + 1)) {
			int nameStart = elementStart + 1;
			if (nameStart < markup.length() && markup.charAt(nameStart) == '/')
				nameStart++;
			int nameEnd = nameStart;
			while (nameEnd < markup.length() && Character.isLetterOrDigit(markup.charAt(nameEnd)))
				nameEnd++;
			named.add(markup.substring(nameStart, nameEnd).toLowerCase(Locale.ROOT));
		}
		return named;
	}

	private static boolean anyElementNamedIsBlockLevel(String markup) {
		for (String elementName : everyElementNamedIn(markup))
			if (BLOCK_ELEMENT_NAMES.contains(elementName))
				return true;
		return false;
	}
}
