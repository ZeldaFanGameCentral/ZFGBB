package com.zfgc.zfgbb.content.renderer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeDocument;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText;
import com.zfgc.zfgbb.content.renderer.bbcode.ContentLevel;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import com.zfgc.zfgbb.model.forum.AttributeSemanticRole;
import com.zfgc.zfgbb.model.forum.MarkdownEquivalent;

final class BBCodeAsMarkdown {

	record Attempt(String markdown, List<BBCodeTag> tagsWrittenAsMarkdown) {}

	static final String CHARACTERS_MARKDOWN_READS_AS_MARKUP_ANYWHERE = "\\`*_[]";

	static final String CHARACTERS_MARKDOWN_READS_AS_MARKUP_AT_A_LINE_START = "#>-+=~";

	private static final Set<MarkdownEquivalent> EQUIVALENTS_THAT_NEED_THEIR_OWN_LINE = Set.of(
			MarkdownEquivalent.HEADING, MarkdownEquivalent.THEMATIC_BREAK, MarkdownEquivalent.BLOCK_QUOTE,
			MarkdownEquivalent.FENCED_CODE, MarkdownEquivalent.LIST);

	private static final Set<MarkdownEquivalent> EQUIVALENTS_THAT_CARRY_NO_BB_CODE_ATTRIBUTE = Set.of(
			MarkdownEquivalent.STRONG_EMPHASIS, MarkdownEquivalent.EMPHASIS, MarkdownEquivalent.HEADING,
			MarkdownEquivalent.THEMATIC_BREAK, MarkdownEquivalent.BLOCK_QUOTE, MarkdownEquivalent.FENCED_CODE,
			MarkdownEquivalent.INLINE_CODE);

	private final BBCodeGrammar grammar;

	private final String source;

	private final BBCodeDocument document;

	private final int[] paragraphOfOffset;

	private final Set<Integer> paragraphsThatKeepBlockLevelBBCode;

	private final Set<BBCodeTag> tagsWrittenAsMarkdown = new LinkedHashSet<>();

	private final Deque<MarkdownWriter> writersLeftToFinish = new ArrayDeque<>();

	private Set<BBCodeTag> tagsToKeepAsBBCode = Set.of();

	BBCodeAsMarkdown(BBCodeGrammar grammar, String source) {
		this.grammar = grammar;
		this.source = source;
		this.document = BBCodeParser.parse(source, grammar.configs());
		this.paragraphOfOffset = paragraphOfEveryOffsetIn(source);
		this.paragraphsThatKeepBlockLevelBBCode =
				paragraphsThatKeepBlockLevelBBCodeIn(source, paragraphOfOffset, document.children());
	}

	Attempt write(Set<BBCodeTag> tagsToKeepAsBBCode) {
		this.tagsToKeepAsBBCode = tagsToKeepAsBBCode;
		tagsWrittenAsMarkdown.clear();
		writersLeftToFinish.clear();
		MarkdownWriter wholeDocument =
				new MarkdownWriter(document.children(), paragraphsThatKeepBlockLevelBBCode, markdown -> {});
		writersLeftToFinish.push(wholeDocument);
		while (!writersLeftToFinish.isEmpty())
			if (writersLeftToFinish.peek().hasNodeLeftToWrite())
				writersLeftToFinish.peek().writeNextNode();
			else
				writersLeftToFinish.pop().handMarkdownToTheWriterThatAskedForIt();
		return new Attempt(wholeDocument.markdown.toString(), List.copyOf(tagsWrittenAsMarkdown));
	}

	private static Set<Integer> paragraphsThatKeepBlockLevelBBCodeIn(String source, int[] paragraphOfOffset,
			List<BBCodeNode> nodes) {
		Set<Integer> paragraphs = paragraphsTheMarkdownLaneReadsAsRawHtml(source, paragraphOfOffset);
		Deque<BBCodeNode> pending = new ArrayDeque<>(nodes);
		while (!pending.isEmpty()) {
			BBCodeNode node = pending.pop();
			if (node instanceof BBCodeTag tag && tag.contentLevel() == ContentLevel.BLOCK) {
				if (!tagKeepsItsBBCode(source, tag)) {
					if (tag.config().declaredMarkdownEquivalent().filter(MarkdownEquivalent.LIST::equals).isPresent())
						continue;
				} else {
					for (int paragraph = paragraphOfOffset[tag.authoredSource().startIndex()];
							paragraph <= paragraphOfOffset[Math.min(tag.authoredSource().endIndex(), source.length())];
							paragraph++)
						paragraphs.add(paragraph);
				}
			}
			pending.addAll(node.children());
		}
		return paragraphs;
	}

	private static Set<Integer> paragraphsTheMarkdownLaneReadsAsRawHtml(String source, int[] paragraphOfOffset) {
		Set<Integer> paragraphs = new LinkedHashSet<>();
		for (int lineStart = 0; lineStart <= source.length(); lineStart = source.indexOf('\n', lineStart) + 1) {
			if (aRawHtmlBlockOpensAt(source, lineStart))
				paragraphs.add(paragraphOfOffset[lineStart]);
			if (source.indexOf('\n', lineStart) < 0)
				break;
		}
		return paragraphs;
	}

	private static boolean aRawHtmlBlockOpensAt(String source, int lineStart) {
		if (lineStart >= source.length() || source.charAt(lineStart) != '<' || lineStart + 1 >= source.length())
			return false;
		char afterBracket = source.charAt(lineStart + 1);
		return Character.isLetter(afterBracket) || afterBracket == '/' || afterBracket == '!';
	}

	private static int[] paragraphOfEveryOffsetIn(String source) {
		int[] paragraphOfOffset = new int[source.length() + 1];
		int paragraph = 0;
		for (int offset = 0; offset <= source.length(); offset++) {
			if (offset > 0 && source.charAt(offset - 1) == '\n' && lineStartingAtIsBlank(source, offset))
				paragraph++;
			paragraphOfOffset[offset] = paragraph;
		}
		return paragraphOfOffset;
	}

	private static boolean lineStartingAtIsBlank(String source, int lineStart) {
		for (int cursor = lineStart; cursor < source.length(); cursor++) {
			if (source.charAt(cursor) == '\n')
				return true;
			if (!Character.isWhitespace(source.charAt(cursor)))
				return false;
		}
		return true;
	}

	private static boolean tagKeepsItsBBCode(String source, BBCodeTag tag) {
		Optional<MarkdownEquivalent> declared = tag.config().declaredMarkdownEquivalent();
		if (declared.isEmpty())
			return true;
		if (EQUIVALENTS_THAT_NEED_THEIR_OWN_LINE.contains(declared.get()) && !tagOwnsItsOwnLine(source, tag))
			return true;
		return EQUIVALENTS_THAT_CARRY_NO_BB_CODE_ATTRIBUTE.contains(declared.get())
				&& !tag.authoredSource().attributeText().isBlank();
	}

	private static boolean tagOwnsItsOwnLine(String source, BBCodeTag tag) {
		int start = tag.authoredSource().startIndex();
		if (start > 0 && source.charAt(start - 1) != '\n')
			return false;
		for (int cursor = tag.authoredSource().endIndex(); cursor < source.length(); cursor++) {
			if (source.charAt(cursor) == '\n')
				return true;
			if (!Character.isWhitespace(source.charAt(cursor)))
				return false;
		}
		return true;
	}

	private static int indentWidthAt(String text, int lineStart) {
		int width = 0;
		for (int cursor = lineStart; cursor < text.length(); cursor++) {
			if (text.charAt(cursor) == ' ')
				width++;
			else if (text.charAt(cursor) == '\t')
				width += MarkdownRenderer.MARKDOWN_INDENTED_CODE_BLOCK_INDENT;
			else
				break;
		}
		return width;
	}

	private static boolean isPunctuation(char character) {
		return !Character.isLetterOrDigit(character) && !Character.isWhitespace(character);
	}

	private static boolean containsWhitespace(String text) {
		for (int index = 0; index < text.length(); index++)
			if (Character.isWhitespace(text.charAt(index)))
				return true;
		return false;
	}

	static String inlineCodeSpanHolding(String body) {
		String fence = "`".repeat(longestBacktickRunIn(body) + 1);
		String padding = body.startsWith("`") || body.endsWith("`") ? " " : "";
		return fence + padding + body + padding + fence;
	}

	static int longestBacktickRunIn(String text) {
		int longest = 0;
		int run = 0;
		for (int index = 0; index < text.length(); index++) {
			run = text.charAt(index) == '`' ? run + 1 : 0;
			longest = Math.max(longest, run);
		}
		return longest;
	}

	private static String escapedText(String text) {
		StringBuilder escaped = new StringBuilder(text.length());
		appendEscapedText(escaped, text);
		return escaped.toString();
	}

	private static void appendEscapedText(StringBuilder markdown, String text) {
		int cursor = 0;
		while (cursor < text.length()) {
			char character = text.charAt(cursor);
			if (character == '\n') {
				escapeTheWhitespaceEndingTheCurrentLine(markdown);
				markdown.append('\n');
				cursor++;
				continue;
			}
			if (atLineStart(markdown)) {
				cursor = appendLineOpeningCharacters(markdown, text, cursor);
				continue;
			}
			appendInlineCharacter(markdown, character);
			cursor++;
		}
	}

	private static int appendLineOpeningCharacters(StringBuilder markdown, String text, int cursor) {
		char character = text.charAt(cursor);
		if (character == ' ' || character == '\t') {
			if (indentWidthAt(text, cursor) >= MarkdownRenderer.MARKDOWN_INDENTED_CODE_BLOCK_INDENT)
				markdown.append(character == ' ' ? "&#32;" : "&#9;");
			else
				markdown.append(character);
			return cursor + 1;
		}
		int afterDigits = cursor;
		while (afterDigits < text.length() && Character.isDigit(text.charAt(afterDigits)))
			afterDigits++;
		if (afterDigits > cursor && afterDigits < text.length()
				&& (text.charAt(afterDigits) == '.' || text.charAt(afterDigits) == ')')) {
			markdown.append(text, cursor, afterDigits).append('\\').append(text.charAt(afterDigits));
			return afterDigits + 1;
		}
		if (CHARACTERS_MARKDOWN_READS_AS_MARKUP_AT_A_LINE_START.indexOf(character) >= 0)
			markdown.append('\\').append(character);
		else
			appendInlineCharacter(markdown, character);
		return cursor + 1;
	}

	private static void appendInlineCharacter(StringBuilder markdown, char character) {
		if (CHARACTERS_MARKDOWN_READS_AS_MARKUP_ANYWHERE.indexOf(character) >= 0)
			markdown.append('\\');
		markdown.append(character);
	}

	private static void escapeTheWhitespaceEndingTheCurrentLine(StringBuilder markdown) {
		if (markdown.isEmpty())
			return;
		char last = markdown.charAt(markdown.length() - 1);
		if (last != ' ' && last != '\t')
			return;
		markdown.setLength(markdown.length() - 1);
		markdown.append(last == ' ' ? "&#32;" : "&#9;");
	}

	private static void appendVerbatimBBCode(StringBuilder markdown, String authored,
			boolean markdownLaneReadsItAsItsOwnBlock) {
		if (markdownLaneReadsItAsItsOwnBlock) {
			markdown.append(authored);
			return;
		}
		for (int cursor = 0; cursor < authored.length(); cursor++) {
			if (atLineStart(markdown) && CHARACTERS_MARKDOWN_READS_AS_MARKUP_AT_A_LINE_START
					.indexOf(authored.charAt(cursor)) >= 0)
				markdown.append('\\');
			markdown.append(authored.charAt(cursor));
		}
	}

	private static boolean atLineStart(StringBuilder markdown) {
		return markdown.isEmpty() || markdown.charAt(markdown.length() - 1) == '\n';
	}

	private final class MarkdownWriter {

		private final Deque<BBCodeNode> nodesLeftToWrite = new ArrayDeque<>();

		private final Set<Integer> paragraphsThatKeepBlockLevelBBCode;

		private final Consumer<String> writerThatAskedForIt;

		private final StringBuilder markdown = new StringBuilder();

		private MarkdownWriter(List<BBCodeNode> nodes, Set<Integer> paragraphsThatKeepBlockLevelBBCode,
				Consumer<String> writerThatAskedForIt) {
			this.paragraphsThatKeepBlockLevelBBCode = paragraphsThatKeepBlockLevelBBCode;
			this.writerThatAskedForIt = writerThatAskedForIt;
			pushInWritingOrder(nodesLeftToWrite, nodes);
		}

		private boolean hasNodeLeftToWrite() {
			return !nodesLeftToWrite.isEmpty();
		}

		private void writeNextNode() {
			switch (nodesLeftToWrite.pop()) {
				case BBCodeText text -> appendEscapedText(markdown, text.sourceText());
				case BBCodeDocument nested -> pushInWritingOrder(nodesLeftToWrite, nested.children());
				case BBCodeTag tag -> writeTag(tag);
			}
		}

		private static void pushInWritingOrder(Deque<BBCodeNode> nodesLeftToWrite, List<BBCodeNode> nodes) {
			for (int node = nodes.size() - 1; node >= 0; node--)
				nodesLeftToWrite.push(nodes.get(node));
		}

		private void handMarkdownToTheWriterThatAskedForIt() {
			writerThatAskedForIt.accept(markdown.toString());
		}

		private void writeTag(BBCodeTag tag) {
			Optional<MarkdownEquivalent> equivalent = equivalentFor(tag);
			if (equivalent.isEmpty()) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			tagsWrittenAsMarkdown.add(tag);
			switch (equivalent.get()) {
				case STRONG_EMPHASIS -> convertChildren(tag, inner -> appendEmphasis(tag, "**", inner));
				case EMPHASIS -> convertChildren(tag, inner -> appendEmphasis(tag, "*", inner));
				case HEADING -> convertChildren(tag, inner -> appendHeading(tag, inner));
				case THEMATIC_BREAK -> markdown.append("---\n");
				case LINK -> appendLink(tag);
				case IMAGE -> appendImage(tag);
				case BLOCK_QUOTE -> convertChildren(tag, inner -> appendBlockQuote(tag, inner));
				case FENCED_CODE -> appendFencedCode(tag);
				case LIST -> appendList(tag);
				case INLINE_CODE -> appendInlineCode(tag);
			}
		}

		private void convertChildren(BBCodeTag tag, Consumer<String> restOfThisTag) {
			writersLeftToFinish.push(new MarkdownWriter(tag.children(), paragraphsThatKeepBlockLevelBBCode,
					restOfThisTag));
		}

		private Optional<MarkdownEquivalent> equivalentFor(BBCodeTag tag) {
			if (tagsToKeepAsBBCode.contains(tag) || tagKeepsItsBBCode(source, tag)
					|| paragraphsThatKeepBlockLevelBBCode.contains(
							paragraphOfOffset[tag.authoredSource().startIndex()]))
				return Optional.empty();
			return tag.config().declaredMarkdownEquivalent();
		}

		private void appendEmphasis(BBCodeTag tag, String marker, String inner) {
			if (inner.isBlank() || !inner.equals(inner.strip()) || inner.contains("\n\n")
					|| delimiterRunWouldNotFlankContent(tag, inner)) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			markdown.append(marker).append(inner).append(marker);
		}

		private boolean delimiterRunWouldNotFlankContent(BBCodeTag tag, String inner) {
			return isPunctuation(inner.charAt(0)) && wordCharacterPrecedes()
					|| isPunctuation(inner.charAt(inner.length() - 1))
							&& wordCharacterFollows(tag.authoredSource().endIndex());
		}

		private boolean wordCharacterPrecedes() {
			return !markdown.isEmpty() && Character.isLetterOrDigit(markdown.charAt(markdown.length() - 1));
		}

		private boolean wordCharacterFollows(int boundary) {
			return boundary < source.length() && Character.isLetterOrDigit(source.charAt(boundary));
		}

		private void appendHeading(BBCodeTag tag, String inner) {
			if (inner.isBlank() || inner.indexOf('\n') >= 0) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			markdown.append("#".repeat(BBCodeGrammar.headingLevelDeclaredByTheMarkup(tag.config())))
					.append(' ').append(inner.strip()).append('\n');
		}

		private void appendLink(BBCodeTag tag) {
			if (tag.children().isEmpty())
				appendLink(tag, escapedText(destinationOf(tag)));
			else
				convertChildren(tag, inner -> appendLink(tag, inner));
		}

		private void appendLink(BBCodeTag tag, String inner) {
			String destination = destinationOf(tag);
			if (destination.isBlank() || containsWhitespace(destination) || destination.indexOf(')') >= 0
					|| inner.isBlank() || inner.indexOf('\n') >= 0) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			markdown.append('[').append(inner.strip()).append("](").append(destination).append(')');
		}

		private void appendImage(BBCodeTag tag) {
			String destination = destinationOf(tag);
			if (tagCarriesASizeMarkdownCannotWrite(tag) || destination.isBlank()
					|| containsWhitespace(destination) || destination.indexOf(')') >= 0) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			markdown.append("![](").append(destination).append(')');
		}

		private static boolean tagCarriesASizeMarkdownCannotWrite(BBCodeTag tag) {
			return tag.valueWithRole(AttributeSemanticRole.WIDTH).isPresent()
					|| tag.valueWithRole(AttributeSemanticRole.HEIGHT).isPresent();
		}

		private String destinationOf(BBCodeTag tag) {
			return tag.valueWithRole(AttributeSemanticRole.DESTINATION)
					.orElseGet(() -> tag.bodyCarriesRole(AttributeSemanticRole.DESTINATION)
							? authoredBodyOf(tag).trim()
							: "");
		}

		private void appendBlockQuote(BBCodeTag tag, String inner) {
			if (inner.isBlank()) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			for (String line : inner.strip().split("\n", -1))
				markdown.append(line.isEmpty() ? ">" : "> " + line).append('\n');
		}

		private void appendInlineCode(BBCodeTag tag) {
			String body = authoredBodyOf(tag);
			if (body.isBlank() || body.indexOf('\n') >= 0) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			markdown.append(inlineCodeSpanHolding(body));
		}

		private void appendFencedCode(BBCodeTag tag) {
			String body = authoredBodyOf(tag);
			String fence = "`".repeat(Math.max(3, longestBacktickRunIn(body) + 1));
			markdown.append(fence).append('\n').append(body.strip()).append('\n').append(fence).append('\n');
		}

		private void appendList(BBCodeTag tag) {
			List<BBCodeTag> items = itemsOf(tag);
			if (items.isEmpty()) {
				appendTagAsBBCodeWithTextEscaped(tag);
				return;
			}
			String itemMarker = tag.valueWithRole(AttributeSemanticRole.LIST_STYLE)
					.filter(grammar::listStyleTypeNumbersItems)
					.isPresent() ? "1. " : "- ";
			Set<Integer> paragraphsInsideTheList =
					paragraphsThatKeepBlockLevelBBCodeIn(source, paragraphOfOffset, tag.children());
			MarkdownWriter list =
					new MarkdownWriter(List.of(), paragraphsThatKeepBlockLevelBBCode, markdown::append);
			writersLeftToFinish.push(list);
			for (int item = items.size() - 1; item >= 0; item--)
				writersLeftToFinish.push(new MarkdownWriter(items.get(item).children(), paragraphsInsideTheList,
						converted -> list.appendListItem(itemMarker, converted)));
		}

		private void appendListItem(String itemMarker, String converted) {
			markdown.append(itemMarker).append(converted.strip().replace("\n", "\n  ")).append('\n');
		}

		private static List<BBCodeTag> itemsOf(BBCodeTag tag) {
			String itemCode = tag.config().declaredImplicitItemCode().orElse("");
			List<BBCodeTag> items = new ArrayList<>();
			for (BBCodeNode child : tag.children())
				if (child instanceof BBCodeTag item && itemCode.equalsIgnoreCase(item.config().getCode()))
					items.add(item);
			return items;
		}

		private String authoredBodyOf(BBCodeTag tag) {
			int bodyStart = Math.min(tag.authoredSource().bodyStartIndex(), source.length());
			int bodyEnd = Math.min(tag.authoredSource().bodyEndIndex(), source.length());
			return bodyEnd <= bodyStart ? "" : source.substring(bodyStart, bodyEnd);
		}

		private void appendTagAsBBCodeWithTextEscaped(BBCodeTag tag) {
			Deque<PendingBBCode> pendingBBCode = new ArrayDeque<>();
			pendingBBCode.push(new PendingBBCode.Unwritten(tag));
			while (!pendingBBCode.isEmpty())
				switch (pendingBBCode.pop()) {
					case PendingBBCode.Closer closer -> markdown.append(closer.markup());
					case PendingBBCode.Unwritten unwritten -> {
						switch (unwritten.node()) {
							case BBCodeText text -> appendEscapedText(markdown, text.sourceText());
							case BBCodeDocument innerDocument ->
									pushAsUnwrittenBBCode(pendingBBCode, innerDocument.children());
							case BBCodeTag nested -> pushTheAuthoredBBCodeOf(nested, pendingBBCode);
						}
					}
				}
		}

		private void pushTheAuthoredBBCodeOf(BBCodeTag tag, Deque<PendingBBCode> pendingBBCode) {
			tagsWrittenAsMarkdown.remove(tag);
			String authored = tag.authoredSource().textIn(source);
			if (tag.children().isEmpty() || !Boolean.TRUE.equals(tag.config().getProcessContentFlag())) {
				appendVerbatimBBCode(markdown, authored, tagOwnsItsOwnLine(source, tag));
				return;
			}
			markdown.append(authored, 0, Math.min(tag.authoredSource().openerLength(), authored.length()));
			if (tag.authoredSource().hasCloser())
				pendingBBCode.push(new PendingBBCode.Closer("[/" + tag.config().getCode() + "]"));
			pushAsUnwrittenBBCode(pendingBBCode, tag.children());
		}

		private static void pushAsUnwrittenBBCode(Deque<PendingBBCode> pendingBBCode, List<BBCodeNode> children) {
			for (int child = children.size() - 1; child >= 0; child--)
				pendingBBCode.push(new PendingBBCode.Unwritten(children.get(child)));
		}

		private sealed interface PendingBBCode {

			record Unwritten(BBCodeNode node) implements PendingBBCode {}

			record Closer(String markup) implements PendingBBCode {}
		}
	}
}
