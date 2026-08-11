package com.zfgc.zfgbb.content.renderer.templates;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import java.util.ArrayList;
import com.zfgc.zfgbb.content.renderer.bbcode.AuthoredSource;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText;

import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemplateExpansion {

	public static final int MAX_DEPTH = 3;

	static final int MAX_INVOCATIONS_PER_DOCUMENT = 200;

	private final TemplateExpander templates;

	private final BBCodeGrammarHolder grammar;

	public static final String TEMPLATE_INVOCATION_CODE = "TEMPLATE";

	public String expandSource(String source, ContentFormat target,
			ContentScope scope, Map<String, String> context) {
		return templates.theFileReferencesResolvedIn(
				expandedSourceOf(source, target, scope, context, 0, new int[] { 0 }), target);
	}

	private String expandedSourceOf(String source, ContentFormat target, ContentScope scope,
			Map<String, String> context, int depth, int[] invocationsSoFar) {
		if (depth >= MAX_DEPTH || source.indexOf('[') < 0)
			return source;
		List<BBCodeTag> invocations = new ArrayList<>();
		for (BBCodeNode node : BBCodeParser.parse(source, grammar.current(scope).configs())
				.selfAndEveryDescendant())
			if (node instanceof BBCodeTag tag && templates.isTemplateInvocation(tag))
				invocations.add(tag);
		if (invocations.isEmpty())
			return source;
		StringBuilder expanded = new StringBuilder(source.length());
		int copiedUpTo = 0;
		for (BBCodeTag invocation : invocations) {
			if (budgetIsSpent(invocationsSoFar))
				break;
			AuthoredSource authored = invocation.authoredSource();
			TemplateExpander.Expansion replacement = templates.expansionOf(
					templates.nameInvokedBy(invocation),
					parametersOf(source.substring(authored.bodyStartIndex(), authored.bodyEndIndex()), context),
					target, scope, depth);
			expanded.append(source, copiedUpTo, authored.startIndex())
					.append(replacement.invocationsStillExpand()
							? expandedSourceOf(replacement.text(), target, scope, context, depth + 1,
									invocationsSoFar)
							: replacement.text());
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
				if (templates.isTemplateTag(tag) && !templates.isTemplateInvocation(tag)) {
					siblings.set(index, BBCodeText.passThroughText(tag.authoredSource().textIn(source)));
					continue;
				}
				if (!templates.isTemplateInvocation(tag)) {
					pending.push(tag.children());
					continue;
				}
				if (budgetIsSpent(invocationsSoFar)) {
					siblings.set(index, BBCodeText.passThroughText(tag.authoredSource().textIn(source)));
					continue;
				}
				List<BBCodeNode> expanded = expansionOf(tag, source, target, scope, context, parseExpansion,
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
		if (resolved.contentEquals(authored))
			return end - 1;
		siblings.subList(start, end).clear();
		siblings.add(start, BBCodeText.passThroughText(resolved));
		return start;
	}

	private List<BBCodeNode> expansionOf(BBCodeTag invocation, String source, ContentFormat target,
			ContentScope scope, Map<String, String> context,
			BiFunction<String, Boolean, List<BBCodeNode>> parseExpansion, int depth, int[] invocationsSoFar) {
		Map<String, String> params = parametersOf(bodyOf(invocation), context);
		String name = templates.nameInvokedBy(invocation);
		TemplateExpander.Expansion expansion = templates.expansionOf(name, params, target, scope, depth);
		List<BBCodeNode> expanded = parseExpansion.apply(expansion.text(),
				expansion.invocationsStillExpand());
		if (!expansion.invocationsStillExpand())
			return expanded;
		expandAmong(expanded, expansion.text(), target, scope, context, parseExpansion,
				depth + 1, invocationsSoFar);
		return expanded;
	}

	private Map<String, String> parametersOf(String body, Map<String, String> context) {
		Map<String, String> params = new HashMap<>(templates.parametersWrittenIn(body));
		context.forEach(params::putIfAbsent);
		return params;
	}

	private static boolean budgetIsSpent(int[] invocationsSoFar) {
		if (invocationsSoFar[0]++ < MAX_INVOCATIONS_PER_DOCUMENT)
			return false;
		log.warn("template invocations in this document exceed the budget of {}; the rest are left as "
				+ "authored", MAX_INVOCATIONS_PER_DOCUMENT);
		return true;
	}

	private static String bodyOf(BBCodeTag invocation) {
		StringBuilder body = new StringBuilder();
		for (BBCodeNode child : invocation.children())
			if (child instanceof BBCodeText text)
				body.append(text.sourceText());
		return BBCodeText.lineBreakMarkupAsNewlines(body.toString());
	}
}
