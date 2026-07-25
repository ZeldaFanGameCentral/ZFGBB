package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.migrator.converters.LegacyMarkupRewriter;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RetiredBBCodeRewriter implements LegacyMarkupRewriter {

	private record Replacement(String opener, String closer) {}

	private static final Map<String, Replacement> RETIRED_CODES = Map.of(
			"left", new Replacement("[align=left]", "[/align]"),
			"center", new Replacement("[align=center]", "[/align]"),
			"right", new Replacement("[align=right]", "[/align]"),
			"iurl", new Replacement(null, "[/url]"),
			"ftp", new Replacement(null, "[/url]"));

	private record Splice(int startIndex, int endIndex, String text) {}

	private final BBCodeGrammarHolder grammarHolder;

	@Override
	public String rewriteRetiredCodes(String body) {
		if (body == null || body.indexOf('[') < 0)
			return body;
		List<Splice> splices = new ArrayList<>();
		for (BBCodeNode node : BBCodeParser.parse(body, theGrammarThatAlsoReadsRetiredCodes())
				.selfAndEveryDescendant()) {
			if (!(node instanceof BBCodeTag tag))
				continue;
			Replacement replacement = RETIRED_CODES.get(tag.config().getCode().toLowerCase(Locale.ROOT));
			if (replacement == null)
				continue;
			AuthoredSource authored = tag.authoredSource();
			splices.add(new Splice(authored.startIndex(), authored.bodyStartIndex(),
					openerFor(replacement, authored.textIn(body).substring(0, authored.openerLength()))));
			if (authored.itsAuthorWroteACloser())
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

	private static String openerFor(Replacement replacement, String authoredOpener) {
		if (replacement.opener() != null)
			return replacement.opener();
		return authoredOpener.replaceFirst("(?i)^\\[(?:iurl|ftp)", "[url");
	}

	private Map<String, BBCodeConfig> theGrammarThatAlsoReadsRetiredCodes() {
		Map<String, BBCodeConfig> grammar = new HashMap<>(grammarHolder.current().configs());
		BBCodeConfig blockTemplate = grammar.get("QUOTE");
		BBCodeConfig linkTemplate = grammar.get("URL");
		if (blockTemplate == null || linkTemplate == null)
			throw new IllegalStateException("retired-code parsing borrows the modes of quote and url, and the "
					+ "seeded grammar no longer declares both: " + grammar.keySet());
		for (String code : List.of("left", "center", "right"))
			grammar.put(code.toUpperCase(Locale.ROOT), legacyCodeShapedLike(blockTemplate, code));
		for (String code : List.of("iurl", "ftp"))
			grammar.put(code.toUpperCase(Locale.ROOT), legacyCodeShapedLike(linkTemplate, code));
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
