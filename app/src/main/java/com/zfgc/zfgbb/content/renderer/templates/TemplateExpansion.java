package com.zfgc.zfgbb.content.renderer.templates;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import java.util.ArrayList;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.content.renderer.bbcode.AuthoredSource;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TemplateExpansion {

	public static final int MAX_DEPTH = 3;

	static final int MAX_INVOCATIONS_PER_DOCUMENT = 200;

	private final Logger logger = LoggerFactory.getLogger(TemplateExpansion.class);

	private final TemplateExpander templates;

	private final BBCodeGrammarHolder grammar;

	public static final String TEMPLATE_INVOCATION_CODE = "TEMPLATE";




	public String expandSource(String source, ContentFormat target,
			ContentScope scope, Map<String, String> context) {
		if (source == null)
			return null;
		return templates.theFileReferencesResolvedIn(
				expandedSourceOf(source, target, scope, context, 0, new int[] { 0 }), target);
	}

	private String expandedSourceOf(String source, ContentFormat target, ContentScope scope,
			Map<String, String> context, int depth, int[] invocationsSoFar) {
		if (depth >= MAX_DEPTH || source.indexOf('[') < 0)
			return source;
		List<BBCodeTag> invocations = new ArrayList<>();
		for (BBCodeNode node : BBCodeParser.parse(source, grammar.current().configs())
				.selfAndEveryDescendant())
			if (node instanceof BBCodeTag tag && templates.isATemplateInvocation(tag))
				invocations.add(tag);
		if (invocations.isEmpty())
			return source;
		StringBuilder expanded = new StringBuilder(source.length());
		int copiedUpTo = 0;
		for (BBCodeTag invocation : invocations) {
			if (theBudgetIsSpent(invocationsSoFar))
				break;
			AuthoredSource authored = invocation.authoredSource();
			TemplateExpander.Expansion replacement = templates.theExpansionOf(
					templates.theNameInvokedBy(invocation),
					theParametersOf(source.substring(authored.bodyStartIndex(), authored.bodyEndIndex()), context),
					target, scope, depth);
			String replacementText = replacement == null || replacement.text() == null ? "" : replacement.text();
			expanded.append(source, copiedUpTo, authored.startIndex())
					.append(replacement != null && replacement.itsOwnInvocationsStillExpand()
							? expandedSourceOf(replacementText, target, scope, context, depth + 1,
									invocationsSoFar)
							: replacementText);
			copiedUpTo = authored.endIndex();
		}
		expanded.append(source, copiedUpTo, source.length());
		return expanded.toString();
	}

	public void expandTree(BBCodeNode root, String source, ContentFormat target,
			ContentScope scope, Map<String, String> context,
			BiFunction<String, Boolean, List<BBCodeNode>> parseExpansion) {
		expandAmong(root.children(), source, target, scope, context, parseExpansion, 0,
				new int[] { 0 });
		resolveFileReferencesIn(root, target);
	}

	private void resolveFileReferencesIn(BBCodeNode root, ContentFormat target) {
		Deque<List<BBCodeNode>> pending = new ArrayDeque<>();
		pending.push(root.children());
		while (!pending.isEmpty()) {
			List<BBCodeNode> siblings = pending.pop();
			for (int index = 0; index < siblings.size(); index++) {
				BBCodeNode node = siblings.get(index);
				if (node instanceof BBCodeTag tag) {
					if (Boolean.TRUE.equals(tag.config().getProcessContentFlag()))
						pending.push(tag.children());
					continue;
				}
				index = resolveFileReferencesAt(siblings, index, target);
			}
		}
	}

	private void expandAmong(List<BBCodeNode> roots, String source, ContentFormat target,
			ContentScope scope, Map<String, String> context,
			BiFunction<String, Boolean, List<BBCodeNode>> parseExpansion, int depth, int[] invocationsSoFar) {
		if (depth >= MAX_DEPTH)
			return;
		Deque<List<BBCodeNode>> pending = new ArrayDeque<>();
		pending.push(roots);
		while (!pending.isEmpty()) {
			List<BBCodeNode> siblings = pending.pop();
			for (int index = 0; index < siblings.size(); index++) {
				if (!(siblings.get(index) instanceof BBCodeTag tag)) {
					continue;
				}
				if (templates.isATemplateTag(tag) && !templates.isATemplateInvocation(tag)) {
					siblings.set(index, BBCodeText.passThroughText(tag.authoredSource().textIn(source)));
					continue;
				}
				if (!templates.isATemplateInvocation(tag)) {
					pending.push(tag.children());
					continue;
				}
				if (theBudgetIsSpent(invocationsSoFar)) {
					siblings.set(index, BBCodeText.passThroughText(tag.authoredSource().textIn(source)));
					continue;
				}
				List<BBCodeNode> expanded = theExpansionOf(tag, source, target, scope, context, parseExpansion,
						depth, invocationsSoFar);
				siblings.remove(index);
				siblings.addAll(index, expanded);
				index += expanded.size() - 1;
			}
		}
	}

	private int resolveFileReferencesAt(List<BBCodeNode> siblings, int start,
			ContentFormat target) {
		int end = start;
		StringBuilder authored = new StringBuilder();
		while (end < siblings.size() && siblings.get(end) instanceof BBCodeText text
				&& text.escaping() == BBCodeText.TextEscaping.PASS_THROUGH) {
			authored.append(text.sourceText());
			end++;
		}
		if (end == start)
			return start;
		String resolved = templates.theFileReferencesResolvedIn(authored.toString(), target);
		if (resolved == null || resolved.contentEquals(authored))
			return end - 1;
		siblings.subList(start, end).clear();
		siblings.add(start, BBCodeText.passThroughText(resolved));
		return start;
	}

	private List<BBCodeNode> theExpansionOf(BBCodeTag invocation, String source, ContentFormat target,
			ContentScope scope, Map<String, String> context,
			BiFunction<String, Boolean, List<BBCodeNode>> parseExpansion, int depth, int[] invocationsSoFar) {
		Map<String, String> params = theParametersOf(bodyOf(invocation), context);
		String name = templates.theNameInvokedBy(invocation);
		TemplateExpander.Expansion expansion = templates.theExpansionOf(name, params, target, scope, depth);
		if (expansion == null || expansion.text() == null)
			return List.of();
		List<BBCodeNode> expanded = parseExpansion.apply(expansion.text(),
				expansion.itsOwnInvocationsStillExpand());
		if (!expansion.itsOwnInvocationsStillExpand())
			return expanded;
		expandAmong(expanded, expansion.text(), target, scope, context, parseExpansion,
				depth + 1, invocationsSoFar);
		return expanded;
	}

	private Map<String, String> theParametersOf(String body, Map<String, String> context) {
		Map<String, String> params = new HashMap<>(templates.theParametersWrittenIn(body));
		context.forEach(params::putIfAbsent);
		return params;
	}

	private boolean theBudgetIsSpent(int[] invocationsSoFar) {
		if (invocationsSoFar[0]++ < MAX_INVOCATIONS_PER_DOCUMENT)
			return false;
		logger.warn("template invocations in this document exceed the budget of {}; the rest are left as "
				+ "authored", MAX_INVOCATIONS_PER_DOCUMENT);
		return true;
	}

	private static String bodyOf(BBCodeTag invocation) {
		StringBuilder body = new StringBuilder();
		for (BBCodeNode child : invocation.children())
			if (child instanceof com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText text)
				body.append(text.sourceText());
		return com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText.authoredLineBreaksAsNewlines(body.toString());
	}
}
