package com.zfgc.zfgbb.services.migration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.renderer.bbcode.AuthoredSource;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.migrator.converters.LegacyMarkupRewriter;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RetiredBBCodeRewriter implements LegacyMarkupRewriter {

	private record AuthoredTag(String opener, String attributeText, String body) {}

	private record Replacement(Function<AuthoredTag, String> opener, String closer) {}

	private static Replacement rewrittenAs(String opener, String closer) {
		return new Replacement(authored -> opener, closer);
	}

	private static final Map<String, Replacement> RETIRED_CODES = Map.of(
			"left", rewrittenAs("[align=left]", "[/align]"),
			"center", rewrittenAs("[align=center]", "[/align]"),
			"right", rewrittenAs("[align=right]", "[/align]"),
			"iurl", new Replacement(RetiredBBCodeRewriter::sameLinkUnderTheUrlCode, "[/url]"),
			"ftp", new Replacement(RetiredBBCodeRewriter::sameLinkUnderTheUrlCode, "[/url]"),
			"email", new Replacement(RetiredBBCodeRewriter::sameAddressAsAMailtoLink, "[/url]"),
			"screenshot", rewrittenAs("[img]/content/", "[/img]"));

	private static String sameLinkUnderTheUrlCode(AuthoredTag authored) {
		return authored.opener().replaceFirst("(?i)^\\[(?:iurl|ftp)", "[url");
	}

	private static String sameAddressAsAMailtoLink(AuthoredTag authored) {
		String written = authored.attributeText() == null ? "" : authored.attributeText().trim();
		if (written.startsWith("="))
			written = written.substring(1).trim();
		String address = written.isBlank() ? authored.body() : written;
		return "[url=mailto:" + address + "]";
	}

	private record Splice(int startIndex, int endIndex, String text) {}

	private final BBCodeGrammarHolder grammarHolder;

	@Override
	public String rewriteRetiredCodes(String body) {
		if (body == null || body.indexOf('[') < 0)
			return body;
		List<Splice> splices = new ArrayList<>();
		for (BBCodeNode node : BBCodeParser.parse(body, grammarThatAlsoReadsRetiredCodes())
				.selfAndEveryDescendant()) {
			if (!(node instanceof BBCodeTag tag))
				continue;
			Replacement replacement = RETIRED_CODES.get(tag.config().getCode().toLowerCase(Locale.ROOT));
			if (replacement == null)
				continue;
			AuthoredSource authored = tag.authoredSource();
			String authoredOpener = body.substring(authored.startIndex(), authored.bodyStartIndex());
			String authoredBody = body.substring(authored.bodyStartIndex(),
					Math.min(authored.bodyEndIndex(), body.length()));
			splices.add(new Splice(authored.startIndex(), authored.bodyStartIndex(), replacement.opener()
					.apply(new AuthoredTag(authoredOpener, authored.attributeText(), authoredBody))));
			if (authored.hasCloser())
				splices.add(new Splice(authored.bodyEndIndex(), authored.endIndex(), replacement.closer()));
		}
		if (splices.isEmpty())
			return body;
		splices.sort(Comparator.comparingInt(Splice::startIndex).reversed());
		StringBuilder rewritten = new StringBuilder(body);
		for (Splice splice : splices)
			rewritten.replace(splice.startIndex(), splice.endIndex(), splice.text());
		return rewritten.toString();
	}

	private static final Map<String, String> THE_CODE_EACH_RETIREMENT_IS_SHAPED_LIKE = Map.of(
			"left", "QUOTE", "center", "QUOTE", "right", "QUOTE",
			"iurl", "URL", "ftp", "URL", "email", "URL",
			"screenshot", "IMG");

	private Map<String, BBCodeConfig> grammarThatAlsoReadsRetiredCodes() {
		Map<String, BBCodeConfig> grammar = new HashMap<>(grammarHolder.current().configs());
		for (Map.Entry<String, String> retired : THE_CODE_EACH_RETIREMENT_IS_SHAPED_LIKE.entrySet()) {
			BBCodeConfig template = grammar.get(retired.getValue());
			if (template == null)
				throw new IllegalStateException("missing bbcode: " + retired.getValue());
			grammar.put(retired.getKey().toUpperCase(Locale.ROOT),
					legacyCodeShapedLike(template, retired.getKey()));
		}
		return grammar;
	}

	private static BBCodeConfig legacyCodeShapedLike(BBCodeConfig template, String code) {
		BBCodeConfig config = new BBCodeConfig();
		config.setCode(code);
		config.setEndTag(template.getEndTag());
		config.setProcessContentFlag(template.getProcessContentFlag());
		config.setSelfClosingFlag(template.getSelfClosingFlag());
		config.setAllAttributeNamesAsString(template.getAllAttributeNamesAsString());
		config.setAttributeConfig(template.getAttributeConfig());
		config.setValuePolicyByAttributeName(template.getValuePolicyByAttributeName());
		return config;
	}
}
