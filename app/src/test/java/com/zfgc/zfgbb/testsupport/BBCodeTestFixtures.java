package com.zfgc.zfgbb.testsupport;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.zfgc.zfgbb.content.renderer.QuotedMessageLookup;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public final class BBCodeTestFixtures {

	public static final String QUOTE_HEADER =
			"<div class=\"q\"><div class=\"qh\">Quote from {{msg.author}} on {{msg.date}} "
					+ "(<a class=\"bb-resource-link\" href=\"{{msg.link}}\" data-resource=\"thread\">jump</a>)"
					+ "</div><div class=\"qb\">";

	private BBCodeTestFixtures() {}

	public static BBCodeAttribute attr(String name, String index) {
		BBCodeAttribute attribute = new BBCodeAttribute();
		attribute.setName(name);
		attribute.setAttributeIndex(index);
		attribute.setDataType(AttributeDataType.TEXT);
		return attribute;
	}

	public static BBCodeAttributeMode mode(String openTag, BBCodeAttribute... attributes) {
		BBCodeAttributeMode attributeMode = new BBCodeAttributeMode();
		attributeMode.setOpenTag(openTag);
		attributeMode.setCloseTag("</div></div>");
		attributeMode.setAttributes(new ArrayList<>(Arrays.asList(attributes)));
		return attributeMode;
	}

	public static BBCodeConfig quoteConfig() {
		BBCodeConfig quote = new BBCodeConfig();
		quote.setCode("quote");
		quote.setProcessContentFlag(true);
		quote.setEndTag("</div></div>");
		quote.setAllAttributeNamesAsString("author=,thread=,msg=");
		quote.setAttributeConfig(new HashMap<>());
		quote.getAttributeConfig().put("", mode("<div class=\"q\"><div class=\"qb\">"));
		quote.getAttributeConfig().put("author=",
				mode("<div class=\"q\"><div class=\"qh\">Quote from {{0}}</div><div class=\"qb\">",
						attr("author=", "{{0}}")));
		quote.getAttributeConfig().put("msg=", mode(QUOTE_HEADER, attr("msg=", "{{0}}")));
		quote.getAttributeConfig().put("thread=msg=",
				mode(QUOTE_HEADER, attr("thread=", "{{0}}"), attr("msg=", "{{1}}")));
		quote.getAttributeConfig().put("author=thread=msg=",
				mode(QUOTE_HEADER, attr("author=", "{{0}}"), attr("thread=", "{{1}}"), attr("msg=", "{{2}}")));
		return quote;
	}

	public static BBCodeConfig codeConfig() {
		return simpleTag("code", "<code>", "</code>", false);
	}

	public static BBCodeConfig simpleTag(String code, String openTag, String closeTag, boolean processContent) {
		BBCodeConfig config = new BBCodeConfig();
		config.setCode(code);
		config.setProcessContentFlag(processContent);
		config.setEndTag(closeTag);
		config.setAllAttributeNamesAsString("");
		config.setAttributeConfig(new HashMap<>());
		BBCodeAttributeMode attributeMode = new BBCodeAttributeMode();
		attributeMode.setOpenTag(openTag);
		attributeMode.setCloseTag(closeTag);
		attributeMode.setAttributes(new ArrayList<>());
		config.getAttributeConfig().put("", attributeMode);
		return config;
	}

	public static QuotedMessageLookup.Resolved resolved(boolean permitted, OffsetDateTime revisionTs, String body) {
		NavigableMap<OffsetDateTime, String> revisions = new TreeMap<>();
		revisions.put(revisionTs, body);
		return new QuotedMessageLookup.Resolved("Alice", 7, revisionTs, 42, 1, 3, permitted, revisions);
	}
}
