package com.zfgc.zfgbb.content.renderer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.LinkReferenceDefinition;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeDocument;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText;
import com.zfgc.zfgbb.content.renderer.bbcode.ContentLevel;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import com.zfgc.zfgbb.model.forum.AttributeSemanticRole;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.model.forum.MarkdownEquivalent;

@Component
public class ContentFormatConverter {

	public record ConvertedContent(String content, List<String> notes) {}

	static final String CHARACTERS_MARKDOWN_READS_AS_MARKUP_ANYWHERE = "\\`*_[]";

	static final String CHARACTERS_MARKDOWN_READS_AS_MARKUP_AT_A_LINE_START = "#>-+=~";

	static final String INLINE_CODE_SPAN_CARRYING_BB_CODE =
			"an inline code span whose text carries bb code markup stays a markdown code span, because bb code has "
					+ "no inline element that holds its own markup literally; it will render as literal backticks";

	static final String CODE_BLOCK_CARRYING_THE_BB_CODE_CODE_CLOSER =
			"a code block whose text contains [/code] stays a markdown fence, because that text would close the bb "
					+ "code block early; it will render as literal backticks";

	static final String ORDERED_LIST_STYLE = "decimal";

	static final String ORDERED_LIST_NOT_STARTING_AT_ONE =
			"an ordered list that does not start at 1 becomes a bb code decimal list, which always restarts at 1";

	static final String NO_ENABLED_BB_CODE_CARRIES_THIS_CONSTRUCT =
			"a markdown construct whose bb code equivalent is not enabled is written as its plain text, because no "
					+ "enabled code carries it";

	static final String LINK_TITLE_TEXT =
			"the title text of a link or image is dropped, because bb code carries no title attribute";

	static final int CHARACTERS_THE_SEARCH_MAY_RENDER = 2_000_000;

	private final BBCodeGrammarHolder grammarHolder;

	private final ContentRenderingService contentRenderingService;

	private final MarkdownRenderer markdownRenderer;

	public ContentFormatConverter(BBCodeGrammarHolder grammarHolder, ContentRenderingService contentRenderingService,
			MarkdownRenderer markdownRenderer) {
		this.grammarHolder = grammarHolder;
		this.contentRenderingService = contentRenderingService;
		this.markdownRenderer = markdownRenderer;
	}

	public ConvertedContent convert(String source, ContentFormat from, ContentFormat to, ContentScope scope) {
		if (source == null || source.isEmpty() || from == to)
			return new ConvertedContent(source == null ? "" : source, List.of());
		if (from == ContentFormat.BBCODE)
			return theClosestMarkdownThatStillReadsLikeTheBBCode(source, to, scope);
		ConvertedContent converted = markdownSourceAsBBCodeSource(source, scope);
		return withANoteForWhateverTheFlipDoesNotCarry(source, from, converted, to, scope);
	}

	private ConvertedContent theClosestMarkdownThatStillReadsLikeTheBBCode(String source, ContentFormat to,
			ContentScope scope) {
		BBCodeDocument document = BBCodeParser.parse(source, grammarHolder.current(scope).configs());
		int[] paragraphOfOffset = paragraphOfEveryOffsetIn(source);
		Set<Integer> paragraphs = paragraphsThatKeepBlockLevelBBCode(source, paragraphOfOffset, document.children());
		Set<BBCodeTag> keptAsBBCode = new LinkedHashSet<>();
		MarkdownConversion firstAttempt = new MarkdownConversion(source, paragraphOfOffset, keptAsBBCode, scope);
		String converted = firstAttempt.write(document.children(), paragraphs);

		String asTheSourceLaneShowsIt = withoutWhitespace(visibleTextOf(
				contentRenderingService.renderWithTemplates(source, ContentFormat.BBCODE, scope, Map.of())));
		if (theTargetLaneShowsTheSameText(converted, asTheSourceLaneShowsIt, to, scope))
			return new ConvertedContent(converted, List.of());

		List<BBCodeTag> everyTagWrittenAsMarkdown = new ArrayList<>(firstAttempt.tagsWrittenAsMarkdown);
		keptAsBBCode.addAll(everyTagWrittenAsMarkdown);
		String withEveryTagKeptAsBBCode =
				rewrite(source, paragraphOfOffset, paragraphs, keptAsBBCode, document, scope);
		if (everyTagWrittenAsMarkdown.isEmpty()
				|| !theTargetLaneShowsTheSameText(withEveryTagKeptAsBBCode, asTheSourceLaneShowsIt, to, scope))
			return new ConvertedContent(converted,
					List.of(theCodeTheTargetLaneShowsAsPlainText(converted, scope, asTheSourceLaneShowsIt,
							withoutWhitespace(visibleTextOf(contentRenderingService
									.renderWithTemplates(converted, to, scope, Map.of()))))
							.map(code -> aCodeTheOtherFormatDoesNotCarry(code, to))
							.orElseGet(() -> contentTheOtherFormatDoesNotCarry(to))));

		int candidatesTheSearchCanAfford = candidatesTheSearchCanAfford(source);
		String verified = withEveryTagKeptAsBBCode;
		int candidatesTried = 0;
		for (BBCodeTag candidate : everyTagWrittenAsMarkdown) {
			if (candidatesTried == candidatesTheSearchCanAfford)
				break;
			candidatesTried++;
			keptAsBBCode.remove(candidate);
			String withThisTagConverted = rewrite(source, paragraphOfOffset, paragraphs, keptAsBBCode, document, scope);
			if (theTargetLaneShowsTheSameText(withThisTagConverted, asTheSourceLaneShowsIt, to, scope))
				verified = withThisTagConverted;
			else
				keptAsBBCode.add(candidate);
		}
		if (candidatesTried == everyTagWrittenAsMarkdown.size())
			return new ConvertedContent(verified, List.of(theCodesTheFlipCouldNotCarry(keptAsBBCode, to)));
		return new ConvertedContent(verified, List.of(theCodesTheFlipCouldNotCarry(keptAsBBCode, to),
				theSearchStoppedBeforeItTriedEveryCode(candidatesTried, everyTagWrittenAsMarkdown.size(), to)));
	}

	static int candidatesTheSearchCanAfford(String source) {
		return Math.max(1, CHARACTERS_THE_SEARCH_MAY_RENDER / Math.max(1, source.length()));
	}

	private String rewrite(String source, int[] paragraphOfOffset, Set<Integer> paragraphs,
			Set<BBCodeTag> keptAsBBCode, BBCodeDocument document, ContentScope scope) {
		return new MarkdownConversion(source, paragraphOfOffset, keptAsBBCode, scope)
				.write(document.children(), paragraphs);
	}

	private boolean theTargetLaneShowsTheSameText(String converted, String asTheSourceLaneShowsIt, ContentFormat to,
			ContentScope scope) {
		return asTheSourceLaneShowsIt.equals(withoutWhitespace(visibleTextOf(
				contentRenderingService.renderWithTemplates(converted, to, scope, Map.of()))));
	}

	static String theCodesTheFlipCouldNotCarry(Set<BBCodeTag> keptAsBBCode, ContentFormat target) {
		Set<String> codes = new LinkedHashSet<>();
		for (BBCodeTag tag : keptAsBBCode)
			codes.add("[" + tag.config().getCode() + "]");
		return String.join(", ", codes) + " stayed as bb code: written as " + nameOf(target)
				+ " the post did not read the same, and the " + nameOf(target)
				+ " lane renders bb code as it is, so leaving it costs the post nothing";
	}

	static String theSearchStoppedBeforeItTriedEveryCode(int codesTried, int codesFound, ContentFormat target) {
		return "this post is long enough that only " + codesTried + " of its " + codesFound
				+ " codes were measured; the rest stayed as bb code untried, which the " + nameOf(target)
				+ " lane renders as it is, so the post reads right but converts less than a shorter one would";
	}

	private ConvertedContent withANoteForWhateverTheFlipDoesNotCarry(String source, ContentFormat from,
			ConvertedContent converted, ContentFormat to, ContentScope scope) {
		String asTheSourceLaneShowsIt = withoutWhitespace(visibleTextOf(
				contentRenderingService.renderWithTemplates(source, from, scope, Map.of())));
		String asTheTargetLaneShowsIt = withoutWhitespace(visibleTextOf(
				contentRenderingService.renderWithTemplates(converted.content(), to, scope, Map.of())));
		if (asTheSourceLaneShowsIt.equals(asTheTargetLaneShowsIt))
			return converted;
		List<String> notes = new ArrayList<>(converted.notes());
		notes.add(theCodeTheTargetLaneShowsAsPlainText(converted.content(), scope, asTheSourceLaneShowsIt,
				asTheTargetLaneShowsIt)
				.map(code -> aCodeTheOtherFormatDoesNotCarry(code, to))
				.orElseGet(() -> contentTheOtherFormatDoesNotCarry(to)));
		return new ConvertedContent(converted.content(), List.copyOf(notes));
	}

	private Optional<String> theCodeTheTargetLaneShowsAsPlainText(String converted, ContentScope scope,
			String asTheSourceLaneShowsIt,
			String asTheTargetLaneShowsIt) {
		Optional<String> leaked = Optional.empty();
		int leakedAt = Integer.MAX_VALUE;
		Deque<BBCodeNode> pending = new ArrayDeque<>(
				BBCodeParser.parse(converted, grammarHolder.current(scope).configs()).children());
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

	static String aCodeTheOtherFormatDoesNotCarry(String code, ContentFormat target) {
		return "[" + code + "] does not survive the switch to " + nameOf(target)
				+ ": its markup shows in the post as plain text instead of formatting it";
	}

	static String contentTheOtherFormatDoesNotCarry(ContentFormat target) {
		return "this post does not read the same in " + nameOf(target)
				+ ": some of it shows as plain text instead of formatting the post";
	}

	private static String nameOf(ContentFormat format) {
		return format == ContentFormat.MARKDOWN ? "Markdown" : "BBCode";
	}

	private static String visibleTextOf(String html) {
		Document parsed = Jsoup.parse(html);
		parsed.outputSettings().prettyPrint(false);
		return parsed.wholeText();
	}

	private static String withoutWhitespace(String text) {
		StringBuilder kept = new StringBuilder(text.length());
		for (int index = 0; index < text.length(); index++)
			if (!Character.isWhitespace(text.charAt(index)))
				kept.append(text.charAt(index));
		return kept.toString();
	}

	private static Set<Integer> paragraphsThatKeepBlockLevelBBCode(String source, int[] paragraphOfOffset,
			List<BBCodeNode> nodes) {
		Set<Integer> paragraphs = paragraphsTheMarkdownLaneReadsAsRawHtml(source, paragraphOfOffset);
		Deque<BBCodeNode> pending = new ArrayDeque<>(nodes);
		while (!pending.isEmpty()) {
			BBCodeNode node = pending.pop();
			if (node instanceof BBCodeTag tag && tag.contentLevel() == ContentLevel.BLOCK) {
				if (!theTagKeepsItsBBCode(source, tag)) {
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
			if (offset > 0 && source.charAt(offset - 1) == '\n' && theLineStartingAtIsBlank(source, offset))
				paragraph++;
			paragraphOfOffset[offset] = paragraph;
		}
		return paragraphOfOffset;
	}

	private static boolean theLineStartingAtIsBlank(String source, int lineStart) {
		for (int cursor = lineStart; cursor < source.length(); cursor++) {
			if (source.charAt(cursor) == '\n')
				return true;
			if (!Character.isWhitespace(source.charAt(cursor)))
				return false;
		}
		return true;
	}

	private static boolean theTagKeepsItsBBCode(String source, BBCodeTag tag) {
		MarkdownEquivalent equivalent = tag.config().declaredMarkdownEquivalent().orElse(null);
		if (equivalent == null)
			return true;
		if (EQUIVALENTS_THAT_NEED_THEIR_OWN_LINE.contains(equivalent) && !theTagOwnsItsOwnLine(source, tag))
			return true;
		return EQUIVALENTS_THAT_CARRY_NO_BB_CODE_ATTRIBUTE.contains(equivalent)
				&& !tag.authoredSource().attributeText().isBlank();
	}

	private static boolean theTagOwnsItsOwnLine(String source, BBCodeTag tag) {
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

	private static final Set<MarkdownEquivalent> EQUIVALENTS_THAT_NEED_THEIR_OWN_LINE = Set.of(
			MarkdownEquivalent.HEADING, MarkdownEquivalent.THEMATIC_BREAK, MarkdownEquivalent.BLOCK_QUOTE,
			MarkdownEquivalent.FENCED_CODE, MarkdownEquivalent.LIST);

	private static final Set<MarkdownEquivalent> EQUIVALENTS_THAT_CARRY_NO_BB_CODE_ATTRIBUTE = Set.of(
			MarkdownEquivalent.STRONG_EMPHASIS, MarkdownEquivalent.EMPHASIS, MarkdownEquivalent.HEADING,
			MarkdownEquivalent.THEMATIC_BREAK, MarkdownEquivalent.BLOCK_QUOTE, MarkdownEquivalent.FENCED_CODE,
			MarkdownEquivalent.INLINE_CODE);

	private final class MarkdownConversion {

		private final String source;

		private final int[] paragraphOfOffset;

		private final Set<BBCodeTag> tagsToKeepAsBBCode;

		private final Set<BBCodeTag> tagsWrittenAsMarkdown = new LinkedHashSet<>();

		private final Deque<MarkdownWriter> writersLeftToFinish = new ArrayDeque<>();

		private final ContentScope scope;

		private MarkdownConversion(String source, int[] paragraphOfOffset, Set<BBCodeTag> tagsToKeepAsBBCode,
				ContentScope scope) {
			this.scope = scope;
			this.source = source;
			this.paragraphOfOffset = paragraphOfOffset;
			this.tagsToKeepAsBBCode = tagsToKeepAsBBCode;
		}

		private String write(List<BBCodeNode> nodes, Set<Integer> paragraphsThatKeepBlockLevelBBCode) {
			MarkdownWriter wholeDocument =
					new MarkdownWriter(nodes, paragraphsThatKeepBlockLevelBBCode, itsMarkdown -> {});
			writersLeftToFinish.push(wholeDocument);
			while (!writersLeftToFinish.isEmpty())
				if (writersLeftToFinish.peek().hasANodeLeftToWrite())
					writersLeftToFinish.peek().writeItsNextNode();
				else
					writersLeftToFinish.pop().handItsMarkdownToTheWriterThatAskedForIt();
			return wholeDocument.markdown.toString();
		}

		private final class MarkdownWriter {

			private final Deque<BBCodeNode> nodesLeftToWrite = new ArrayDeque<>();

			private final Set<Integer> paragraphsThatKeepBlockLevelBBCode;

			private final Consumer<String> theWriterThatAskedForIt;

			private final StringBuilder markdown = new StringBuilder();

			private MarkdownWriter(List<BBCodeNode> nodes, Set<Integer> paragraphsThatKeepBlockLevelBBCode,
					Consumer<String> theWriterThatAskedForIt) {
				this.paragraphsThatKeepBlockLevelBBCode = paragraphsThatKeepBlockLevelBBCode;
				this.theWriterThatAskedForIt = theWriterThatAskedForIt;
				pushInWritingOrder(nodesLeftToWrite, nodes);
			}

			private boolean hasANodeLeftToWrite() {
				return !nodesLeftToWrite.isEmpty();
			}

			private void writeItsNextNode() {
				switch (nodesLeftToWrite.pop()) {
					case BBCodeText text -> appendEscapedText(text.sourceText());
					case BBCodeDocument document -> pushInWritingOrder(nodesLeftToWrite, document.children());
					case BBCodeTag tag -> writeTag(tag);
				}
			}

			private static void pushInWritingOrder(Deque<BBCodeNode> nodesLeftToWrite, List<BBCodeNode> nodes) {
				for (int node = nodes.size() - 1; node >= 0; node--)
					nodesLeftToWrite.push(nodes.get(node));
			}

			private void handItsMarkdownToTheWriterThatAskedForIt() {
				theWriterThatAskedForIt.accept(markdown.toString());
			}

			private void writeTag(BBCodeTag tag) {
				Optional<MarkdownEquivalent> equivalent = equivalentFor(tag);
				if (equivalent.isEmpty()) {
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				tagsWrittenAsMarkdown.add(tag);
				switch (equivalent.get()) {
					case STRONG_EMPHASIS -> convertItsChildren(tag, inner -> appendEmphasis(tag, "**", inner));
					case EMPHASIS -> convertItsChildren(tag, inner -> appendEmphasis(tag, "*", inner));
					case HEADING -> convertItsChildren(tag, inner -> appendHeading(tag, inner));
					case THEMATIC_BREAK -> markdown.append("---\n");
					case LINK -> appendLink(tag);
					case IMAGE -> appendImage(tag);
					case BLOCK_QUOTE -> convertItsChildren(tag, inner -> appendBlockQuote(tag, inner));
					case FENCED_CODE -> appendFencedCode(tag);
					case LIST -> appendList(tag);
					case INLINE_CODE -> appendInlineCode(tag);
				}
			}

			private void convertItsChildren(BBCodeTag tag, Consumer<String> theRestOfThisTag) {
				writersLeftToFinish.push(new MarkdownWriter(tag.children(), paragraphsThatKeepBlockLevelBBCode,
						theRestOfThisTag));
			}

			private Optional<MarkdownEquivalent> equivalentFor(BBCodeTag tag) {
				if (tagsToKeepAsBBCode.contains(tag) || theTagKeepsItsBBCode(source, tag)
						|| paragraphsThatKeepBlockLevelBBCode.contains(
								paragraphOfOffset[tag.authoredSource().startIndex()]))
					return Optional.empty();
				return tag.config().declaredMarkdownEquivalent();
			}

			private void appendEmphasis(BBCodeTag tag, String marker, String inner) {
				if (inner.isBlank() || !inner.equals(inner.strip()) || inner.contains("\n\n")
						|| theDelimiterRunWouldNotFlankTheContent(tag, inner)) {
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				markdown.append(marker).append(inner).append(marker);
			}

			private boolean theDelimiterRunWouldNotFlankTheContent(BBCodeTag tag, String inner) {
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
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				markdown.append("#".repeat(headingLevelOf(tag))).append(' ').append(inner.strip()).append('\n');
			}

			private int headingLevelOf(BBCodeTag tag) {
				return BBCodeGrammar.headingLevelDeclaredByTheMarkup(tag.config());
			}

			private void appendLink(BBCodeTag tag) {
				if (tag.children().isEmpty())
					appendLink(tag, escapedText(destinationOf(tag)));
				else
					convertItsChildren(tag, inner -> appendLink(tag, inner));
			}

			private void appendLink(BBCodeTag tag, String inner) {
				String destination = destinationOf(tag);
				if (destination.isBlank() || containsWhitespace(destination) || destination.indexOf(')') >= 0
						|| inner.isBlank() || inner.indexOf('\n') >= 0) {
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				markdown.append('[').append(inner.strip()).append("](").append(destination).append(')');
			}

			private void appendImage(BBCodeTag tag) {
				String destination = destinationOf(tag);
				if (theTagCarriesASizeMarkdownCannotWrite(tag) || destination.isBlank()
						|| containsWhitespace(destination) || destination.indexOf(')') >= 0) {
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				markdown.append("![](").append(destination).append(')');
			}

			private static boolean theTagCarriesASizeMarkdownCannotWrite(BBCodeTag tag) {
				return tag.valueWithRole(AttributeSemanticRole.WIDTH).isPresent()
						|| tag.valueWithRole(AttributeSemanticRole.HEIGHT).isPresent();
			}

			private String destinationOf(BBCodeTag tag) {
				return tag.valueWithRole(AttributeSemanticRole.DESTINATION)
						.orElseGet(() -> tag.itsBodyCarriesTheRole(AttributeSemanticRole.DESTINATION)
								? authoredBodyOf(tag).trim()
								: "");
			}

			private void appendBlockQuote(BBCodeTag tag, String inner) {
				if (inner.isBlank()) {
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				for (String line : inner.strip().split("\n", -1))
					markdown.append(line.isEmpty() ? ">" : "> " + line).append('\n');
			}

			private void appendInlineCode(BBCodeTag tag) {
				String body = authoredBodyOf(tag);
				if (body.isBlank() || body.indexOf('\n') >= 0) {
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				markdown.append(theInlineCodeSpanHolding(body));
			}

			private void appendFencedCode(BBCodeTag tag) {
				String body = authoredBodyOf(tag);
				String fence = "`".repeat(Math.max(3, longestBacktickRunIn(body) + 1));
				markdown.append(fence).append('\n').append(body.strip()).append('\n').append(fence).append('\n');
			}

			private void appendList(BBCodeTag tag) {
				List<BBCodeTag> items = itemsOf(tag);
				if (items.isEmpty()) {
					appendTagAsBBCodeWithItsTextEscaped(tag);
					return;
				}
				String itemMarker = tag.valueWithRole(AttributeSemanticRole.LIST_STYLE)
						.filter(grammarHolder.current(scope)::listStyleTypeNumbersItsItems)
						.isPresent() ? "1. " : "- ";
				Set<Integer> paragraphsInsideTheList =
						paragraphsThatKeepBlockLevelBBCode(source, paragraphOfOffset, tag.children());
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

			private List<BBCodeTag> itemsOf(BBCodeTag tag) {
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

			private void appendTagAsBBCodeWithItsTextEscaped(BBCodeTag tag) {
				Deque<PendingBBCode> pendingBBCode = new ArrayDeque<>();
				pendingBBCode.push(new PendingBBCode.Unwritten(tag));
				while (!pendingBBCode.isEmpty())
					switch (pendingBBCode.pop()) {
						case PendingBBCode.Closer closer -> markdown.append(closer.markup());
						case PendingBBCode.Unwritten unwritten -> {
							switch (unwritten.node()) {
								case BBCodeText text -> appendEscapedText(text.sourceText());
								case BBCodeDocument document ->
										pushAsUnwrittenBBCode(pendingBBCode, document.children());
								case BBCodeTag nested -> pushTheAuthoredBBCodeOf(nested, pendingBBCode);
							}
						}
					}
			}

			private void pushTheAuthoredBBCodeOf(BBCodeTag tag, Deque<PendingBBCode> pendingBBCode) {
				tagsWrittenAsMarkdown.remove(tag);
				String authored = tag.authoredSource().textIn(source);
				if (tag.children().isEmpty() || !Boolean.TRUE.equals(tag.config().getProcessContentFlag())) {
					appendVerbatimBBCode(authored, theTagOwnsItsOwnLine(source, tag));
					return;
				}
				markdown.append(authored, 0, Math.min(tag.authoredSource().openerLength(), authored.length()));
				if (tag.authoredSource().itsAuthorWroteACloser())
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

			private void appendVerbatimBBCode(String authored, boolean theMarkdownLaneWillReadItAsItsOwnBlock) {
				if (theMarkdownLaneWillReadItAsItsOwnBlock) {
					markdown.append(authored);
					return;
				}
				for (int cursor = 0; cursor < authored.length(); cursor++) {
					if (atLineStart() && CHARACTERS_MARKDOWN_READS_AS_MARKUP_AT_A_LINE_START
							.indexOf(authored.charAt(cursor)) >= 0)
						markdown.append('\\');
					markdown.append(authored.charAt(cursor));
				}
			}

			private boolean atLineStart() {
				return markdown.isEmpty() || markdown.charAt(markdown.length() - 1) == '\n';
			}

			private void appendEscapedText(String text) {
				int cursor = 0;
				while (cursor < text.length()) {
					char character = text.charAt(cursor);
					if (character == '\n') {
						escapeTheWhitespaceEndingTheCurrentLine();
						markdown.append('\n');
						cursor++;
						continue;
					}
					if (atLineStart()) {
						cursor = appendLineOpeningCharacters(text, cursor);
						continue;
					}
					appendInlineCharacter(character);
					cursor++;
				}
			}

			private String escapedText(String text) {
				MarkdownWriter escaping =
						new MarkdownWriter(List.of(), paragraphsThatKeepBlockLevelBBCode, itsMarkdown -> {});
				escaping.appendEscapedText(text);
				return escaping.markdown.toString();
			}

			private int appendLineOpeningCharacters(String text, int cursor) {
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
					appendInlineCharacter(character);
				return cursor + 1;
			}

			private void appendInlineCharacter(char character) {
				if (CHARACTERS_MARKDOWN_READS_AS_MARKUP_ANYWHERE.indexOf(character) >= 0)
					markdown.append('\\');
				markdown.append(character);
			}

			private void escapeTheWhitespaceEndingTheCurrentLine() {
				if (markdown.isEmpty())
					return;
				char last = markdown.charAt(markdown.length() - 1);
				if (last != ' ' && last != '\t')
					return;
				markdown.setLength(markdown.length() - 1);
				markdown.append(last == ' ' ? "&#32;" : "&#9;");
			}
		}
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

	private static String theInlineCodeSpanHolding(String body) {
		String fence = "`".repeat(longestBacktickRunIn(body) + 1);
		String padding = body.startsWith("`") || body.endsWith("`") ? " " : "";
		return fence + padding + body + padding + fence;
	}

	private static int longestBacktickRunIn(String text) {
		int longest = 0;
		int run = 0;
		for (int index = 0; index < text.length(); index++) {
			run = text.charAt(index) == '`' ? run + 1 : 0;
			longest = Math.max(longest, run);
		}
		return longest;
	}

	private ConvertedContent markdownSourceAsBBCodeSource(String markdown, ContentScope scope) {
		BBCodeWriter writer = new BBCodeWriter(scope);
		writer.write(markdownRenderer.theParserThatLeavesBBCodeBlocksAlone(scope)
				.parse(markdown));
		return new ConvertedContent(writer.finished(), List.copyOf(writer.notes));
	}

	private final class BBCodeWriter {

		private final ContentScope scope;

		private BBCodeWriter(ContentScope scope) {
			this.scope = scope;
		}

		private final StringBuilder bbCode = new StringBuilder();

		private final Set<String> notes = new LinkedHashSet<>();

		private final Deque<PendingMarkdown> pendingMarkdown = new ArrayDeque<>();

		private String finished() {
			return bbCode.toString().stripTrailing();
		}

		private void write(Node root) {
			writeChildren(root);
			while (!pendingMarkdown.isEmpty())
				switch (pendingMarkdown.pop()) {
					case PendingMarkdown.Unwritten unwritten -> writeNode(unwritten.node());
					case PendingMarkdown.Finishing finishing -> finishing.theRestOfTheConstruct().run();
				}
		}

		private void writeChildren(Node parent) {
			List<Node> children = new ArrayList<>();
			for (Node child = parent.getFirstChild(); child != null; child = child.getNext())
				children.add(child);
			for (int child = children.size() - 1; child >= 0; child--)
				pendingMarkdown.push(new PendingMarkdown.Unwritten(children.get(child)));
		}

		private void finishWith(Runnable theRestOfTheConstruct) {
			pendingMarkdown.push(new PendingMarkdown.Finishing(theRestOfTheConstruct));
		}

		private sealed interface PendingMarkdown {

			record Unwritten(Node node) implements PendingMarkdown {}

			record Finishing(Runnable theRestOfTheConstruct) implements PendingMarkdown {}
		}

		private void writeNode(Node node) {
			switch (node) {
				case Text text -> bbCode.append(text.getLiteral());
				case SoftLineBreak ignored -> bbCode.append('\n');
				case HardLineBreak ignored -> bbCode.append('\n');
				case StrongEmphasis ignored -> writeWrapped(node, MarkdownEquivalent.STRONG_EMPHASIS);
				case Emphasis ignored -> writeWrapped(node, MarkdownEquivalent.EMPHASIS);
				case Link link -> writeLink(link);
				case Image image -> writeImage(image);
				case Code code -> writeInlineCode(code);
				case HtmlInline html -> bbCode.append(html.getLiteral());
				case Paragraph ignored -> writeBlock(node);
				case Heading heading -> writeHeading(heading);
				case BlockQuote ignored -> writeQuote(node);
				case BulletList ignored -> writeList(node, "");
				case OrderedList ordered -> writeOrderedList(ordered);
				case ListItem ignored -> writeListItem(node);
				case FencedCodeBlock fenced -> writeCodeBlock(fenced.getLiteral(), fenced.getFenceCharacter());
				case IndentedCodeBlock indented -> writeCodeBlock(indented.getLiteral(), null);
				case ThematicBreak ignored -> writeThematicBreak();
				case HtmlBlock html -> endBlockWith(html.getLiteral().stripTrailing());
				case LinkReferenceDefinition ignored -> {}
				default -> writeChildren(node);
			}
		}

		private Optional<String> canonicalCodeFor(MarkdownEquivalent equivalent) {
			Optional<String> code = grammarHolder.current(scope).theCanonicalCodeFor(equivalent).map(BBCodeConfig::getCode);
			if (code.isEmpty())
				notes.add(NO_ENABLED_BB_CODE_CARRIES_THIS_CONSTRUCT);
			return code;
		}

		private void writeWrapped(Node node, MarkdownEquivalent equivalent) {
			writeWrappedInTheCode(node, canonicalCodeFor(equivalent));
		}

		private void writeWrappedInTheCode(Node node, Optional<String> code) {
			code.ifPresent(present -> bbCode.append('[').append(present).append(']'));
			finishWith(() -> code.ifPresent(present -> bbCode.append("[/").append(present).append(']')));
			writeChildren(node);
		}

		private void writeThematicBreak() {
			canonicalCodeFor(MarkdownEquivalent.THEMATIC_BREAK)
					.ifPresent(code -> endBlockWith("[" + code + "]"));
		}

		private void writeLink(Link link) {
			if (link.getTitle() != null && !link.getTitle().isBlank())
				notes.add(LINK_TITLE_TEXT);
			Optional<String> code = canonicalCodeFor(MarkdownEquivalent.LINK);
			code.ifPresent(present ->
					bbCode.append('[').append(present).append('=').append(link.getDestination()).append(']'));
			finishWith(() -> code.ifPresent(present -> bbCode.append("[/").append(present).append(']')));
			writeChildren(link);
		}

		private void writeImage(Image image) {
			if (image.getTitle() != null && !image.getTitle().isBlank())
				notes.add(LINK_TITLE_TEXT);
			canonicalCodeFor(MarkdownEquivalent.IMAGE).ifPresent(code ->
					bbCode.append('[').append(code).append(']').append(image.getDestination())
							.append("[/").append(code).append(']'));
		}

		private void writeInlineCode(Code code) {
			Optional<String> inlineCode = code.getLiteral().indexOf('[') < 0
					? canonicalCodeFor(MarkdownEquivalent.INLINE_CODE)
					: Optional.empty();
			if (inlineCode.isPresent()) {
				bbCode.append('[').append(inlineCode.get()).append(']').append(code.getLiteral())
						.append("[/").append(inlineCode.get()).append(']');
				return;
			}
			if (code.getLiteral().indexOf('[') >= 0)
				notes.add(INLINE_CODE_SPAN_CARRYING_BB_CODE);
			bbCode.append(theInlineCodeSpanHolding(code.getLiteral()));
		}

		private void writeBlock(Node node) {
			finishWith(this::endBlock);
			writeChildren(node);
		}

		private void writeHeading(Heading heading) {
			Optional<String> code = grammarHolder.current(scope).theCanonicalHeadingCodeForLevel(heading.getLevel())
					.map(BBCodeConfig::getCode);
			if (code.isEmpty())
				notes.add(NO_ENABLED_BB_CODE_CARRIES_THIS_CONSTRUCT);
			finishWith(this::endBlock);
			writeWrappedInTheCode(heading, code);
		}

		private void writeQuote(Node node) {
			Optional<String> code = canonicalCodeFor(MarkdownEquivalent.BLOCK_QUOTE);
			code.ifPresent(present -> bbCode.append('[').append(present).append("]\n"));
			finishWith(() -> closeQuote(code));
			writeChildren(node);
		}

		private void closeQuote(Optional<String> code) {
			trimTrailingBlankLines();
			code.ifPresent(present -> bbCode.append("\n[/").append(present).append(']'));
			endBlock();
		}

		private void writeOrderedList(OrderedList list) {
			if (list.getMarkerStartNumber() != null && list.getMarkerStartNumber() != 1)
				notes.add(ORDERED_LIST_NOT_STARTING_AT_ONE);
			writeList(list, " type=" + ORDERED_LIST_STYLE);
		}

		private void writeList(Node node, String attributeText) {
			Optional<String> code = canonicalCodeFor(MarkdownEquivalent.LIST);
			code.ifPresent(present -> bbCode.append('[').append(present).append(attributeText).append("]\n"));
			finishWith(() -> closeList(code));
			writeChildren(node);
		}

		private void closeList(Optional<String> code) {
			code.ifPresent(present -> bbCode.append("[/").append(present).append(']'));
			endBlock();
		}

		private void writeListItem(Node node) {
			Optional<String> itemCode = grammarHolder.current(scope).theCanonicalCodeFor(MarkdownEquivalent.LIST)
					.flatMap(BBCodeConfig::declaredImplicitItemCode);
			itemCode.ifPresent(present -> bbCode.append('[').append(present).append(']'));
			finishWith(() -> closeListItem(itemCode));
			writeChildren(node);
		}

		private void closeListItem(Optional<String> itemCode) {
			trimTrailingBlankLines();
			itemCode.ifPresent(present -> bbCode.append("[/").append(present).append("]\n"));
		}

		private void writeCodeBlock(String literal, String fenceCharacter) {
			String body = literal.stripTrailing();
			Optional<String> code = canonicalCodeFor(MarkdownEquivalent.FENCED_CODE);
			if (code.isEmpty()
					|| body.toLowerCase(Locale.ROOT).contains("[/" + code.get().toLowerCase(Locale.ROOT) + "]")) {
				if (code.isPresent())
					notes.add(CODE_BLOCK_CARRYING_THE_BB_CODE_CODE_CLOSER);
				String fence = (fenceCharacter == null || fenceCharacter.isEmpty() ? "`" : fenceCharacter)
						.repeat(Math.max(3, longestBacktickRunIn(body) + 1));
				endBlockWith(fence + "\n" + body + "\n" + fence);
				return;
			}
			endBlockWith("[" + code.get() + "]\n" + body + "\n[/" + code.get() + "]");
		}

		private void endBlockWith(String markup) {
			bbCode.append(markup);
			endBlock();
		}

		private void endBlock() {
			trimTrailingBlankLines();
			bbCode.append("\n\n");
		}

		private void trimTrailingBlankLines() {
			while (!bbCode.isEmpty() && bbCode.charAt(bbCode.length() - 1) == '\n')
				bbCode.setLength(bbCode.length() - 1);
		}
	}
}
