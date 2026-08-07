package com.zfgc.zfgbb.content.renderer;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;

@Component
@RequiredArgsConstructor
public class ContentFormatConverter {

	static final int CHARACTERS_THE_SEARCH_MAY_RENDER = 2_000_000;

	private final BBCodeGrammarHolder grammarHolder;

	private final ContentRenderingService contentRenderingService;

	private final MarkdownRenderer markdownRenderer;

	private final ConversionNotes conversionNotes;

	public ConvertedContent convert(String source, ContentFormat from, ContentFormat to, ContentScope scope) {
		if (source == null || source.isEmpty() || from == to)
			return new ConvertedContent(source == null ? "" : source, List.of());
		BBCodeGrammar grammar = grammarHolder.current(scope);
		if (from == ContentFormat.BBCODE)
			return theClosestMarkdownThatStillReadsLikeTheBBCode(grammar, source, to, scope);
		ConvertedContent converted =
				new MarkdownAsBBCode(grammar, markdownRenderer, conversionNotes, scope).write(source);
		return withANoteForWhateverTheFlipDoesNotCarry(grammar, source, from, converted, to, scope);
	}

	private ConvertedContent theClosestMarkdownThatStillReadsLikeTheBBCode(BBCodeGrammar grammar, String source,
			ContentFormat to, ContentScope scope) {
		BBCodeAsMarkdown bbCodeAsMarkdown = new BBCodeAsMarkdown(grammar, source);
		Set<BBCodeTag> keptAsBBCode = new LinkedHashSet<>();
		BBCodeAsMarkdown.Attempt firstAttempt = bbCodeAsMarkdown.write(keptAsBBCode);
		String converted = firstAttempt.markdown();

		String asTheSourceLaneShowsIt = asTheLaneShowsIt(source, ContentFormat.BBCODE, scope);
		if (theTargetLaneShowsTheSameText(converted, asTheSourceLaneShowsIt, to, scope))
			return new ConvertedContent(converted, List.of());

		List<BBCodeTag> everyTagWrittenAsMarkdown = firstAttempt.tagsWrittenAsMarkdown();
		keptAsBBCode.addAll(everyTagWrittenAsMarkdown);
		String withEveryTagKeptAsBBCode = bbCodeAsMarkdown.write(keptAsBBCode).markdown();
		if (everyTagWrittenAsMarkdown.isEmpty()
				|| !theTargetLaneShowsTheSameText(withEveryTagKeptAsBBCode, asTheSourceLaneShowsIt, to, scope))
			return new ConvertedContent(converted,
					List.of(theCodeTheTargetLaneShowsAsPlainText(grammar, converted, asTheSourceLaneShowsIt,
							asTheLaneShowsIt(converted, to, scope))
							.map(code -> conversionNotes.aCodeTheOtherFormatDoesNotCarry(code, to))
							.orElseGet(() -> conversionNotes.contentTheOtherFormatDoesNotCarry(to))));

		int candidatesTheSearchCanAfford = candidatesTheSearchCanAfford(source);
		String verified = withEveryTagKeptAsBBCode;
		int candidatesTried = 0;
		for (BBCodeTag candidate : everyTagWrittenAsMarkdown) {
			if (candidatesTried == candidatesTheSearchCanAfford)
				break;
			candidatesTried++;
			keptAsBBCode.remove(candidate);
			String withThisTagConverted = bbCodeAsMarkdown.write(keptAsBBCode).markdown();
			if (theTargetLaneShowsTheSameText(withThisTagConverted, asTheSourceLaneShowsIt, to, scope))
				verified = withThisTagConverted;
			else
				keptAsBBCode.add(candidate);
		}
		if (candidatesTried == everyTagWrittenAsMarkdown.size())
			return new ConvertedContent(verified,
					List.of(conversionNotes.theCodesTheFlipCouldNotCarry(keptAsBBCode, to)));
		return new ConvertedContent(verified, List.of(conversionNotes.theCodesTheFlipCouldNotCarry(keptAsBBCode, to),
				conversionNotes.theSearchStoppedBeforeItTriedEveryCode(candidatesTried,
						everyTagWrittenAsMarkdown.size(), to)));
	}

	static int candidatesTheSearchCanAfford(String source) {
		return Math.max(1, CHARACTERS_THE_SEARCH_MAY_RENDER / Math.max(1, source.length()));
	}

	private boolean theTargetLaneShowsTheSameText(String converted, String asTheSourceLaneShowsIt, ContentFormat to,
			ContentScope scope) {
		return asTheSourceLaneShowsIt.equals(asTheLaneShowsIt(converted, to, scope));
	}

	private String asTheLaneShowsIt(String content, ContentFormat lane, ContentScope scope) {
		return withoutWhitespace(ContentRenderingService.visibleTextOf(
				contentRenderingService.renderWithTemplates(content, lane, scope, Map.of())));
	}

	private ConvertedContent withANoteForWhateverTheFlipDoesNotCarry(BBCodeGrammar grammar, String source,
			ContentFormat from, ConvertedContent converted, ContentFormat to, ContentScope scope) {
		String asTheSourceLaneShowsIt = asTheLaneShowsIt(source, from, scope);
		String asTheTargetLaneShowsIt = asTheLaneShowsIt(converted.content(), to, scope);
		if (asTheSourceLaneShowsIt.equals(asTheTargetLaneShowsIt))
			return converted;
		List<String> notes = new ArrayList<>(converted.notes());
		notes.add(theCodeTheTargetLaneShowsAsPlainText(grammar, converted.content(), asTheSourceLaneShowsIt,
				asTheTargetLaneShowsIt)
				.map(code -> conversionNotes.aCodeTheOtherFormatDoesNotCarry(code, to))
				.orElseGet(() -> conversionNotes.contentTheOtherFormatDoesNotCarry(to)));
		return new ConvertedContent(converted.content(), List.copyOf(notes));
	}

	private static Optional<String> theCodeTheTargetLaneShowsAsPlainText(BBCodeGrammar grammar, String converted,
			String asTheSourceLaneShowsIt, String asTheTargetLaneShowsIt) {
		Optional<String> leaked = Optional.empty();
		int leakedAt = Integer.MAX_VALUE;
		Deque<BBCodeNode> pending = new ArrayDeque<>(
				BBCodeParser.parse(converted, grammar.configs()).children());
		while (!pending.isEmpty()) {
			BBCodeNode node = pending.pop();
			if (node instanceof BBCodeTag tag && tag.authoredSource().startIndex() < leakedAt
					&& theTargetLaneShowsThisTagsOwnMarkup(tag, converted, asTheSourceLaneShowsIt,
							asTheTargetLaneShowsIt)) {
				leaked = Optional.of(tag.config().getCode());
				leakedAt = tag.authoredSource().startIndex();
			}
			pending.addAll(node.children());
		}
		return leaked;
	}

	private static boolean theTargetLaneShowsThisTagsOwnMarkup(BBCodeTag tag, String converted,
			String asTheSourceLaneShowsIt, String asTheTargetLaneShowsIt) {
		int start = tag.authoredSource().startIndex();
		int end = Math.min(start + tag.authoredSource().openerLength(), converted.length());
		String openTag = withoutWhitespace(converted.substring(start, end));
		return !openTag.isEmpty() && asTheTargetLaneShowsIt.contains(openTag)
				&& !asTheSourceLaneShowsIt.contains(openTag);
	}

	private static String withoutWhitespace(String text) {
		StringBuilder kept = new StringBuilder(text.length());
		for (int index = 0; index < text.length(); index++)
			if (!Character.isWhitespace(text.charAt(index)))
				kept.append(text.charAt(index));
		return kept.toString();
	}
}
