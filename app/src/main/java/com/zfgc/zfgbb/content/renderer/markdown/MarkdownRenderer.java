package com.zfgc.zfgbb.content.renderer.markdown;

import static org.jsoup.nodes.Entities.escape;

import java.time.OffsetDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.commonmark.node.Block;
import org.commonmark.node.CustomBlock;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.Text;
import org.commonmark.parser.IncludeSourceSpans;
import org.commonmark.parser.Parser;
import org.commonmark.parser.beta.InlineContentParser;
import org.commonmark.parser.beta.InlineContentParserFactory;
import org.commonmark.parser.beta.InlineParserState;
import org.commonmark.parser.beta.ParsedInline;
import org.commonmark.parser.beta.Scanner;
import org.commonmark.parser.block.AbstractBlockParser;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.BlockParserFactory;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.content.renderer.templates.TemplateExpansion;
import com.zfgc.zfgbb.content.renderer.SourceReferenceService;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeDocument;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeRenderer;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText;
import com.zfgc.zfgbb.content.renderer.bbcode.ContentLevel;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

@Component
public class MarkdownRenderer {

	public static final int MARKDOWN_INDENTED_CODE_BLOCK_INDENT = 4;

	private static final Set<String> ELEMENTS_THAT_MAY_HOLD_PARSED_MARKDOWN_BLOCKS = Set.of("div", "blockquote");

	private static final char SMILEY_MASK_OPEN = '\uE002';

	private static final char SMILEY_MASK_CLOSE = '\uE003';

	private static final Pattern SMILEY_MASK = Pattern.compile(SMILEY_MASK_OPEN + "(\\d+)" + SMILEY_MASK_CLOSE);

	private final BBCodeGrammarHolder grammarHolder;

	private final RenderedTextEnricher enricher;

	private final SourceReferenceService sourceReferenceService;

	private final BBCodeRenderer bbCodeRenderer;

	private final TemplateExpansion templateExpansion;


	private record ParsersBuiltForMarkers(List<String> markers, Parser thatOpensBBCodeBlocks,
			Parser thatLeavesBBCodeBlocksAlone) {}

	private final Map<ContentScope, ParsersBuiltForMarkers> parsersBySurface = new ConcurrentHashMap<>();

	private final HtmlRenderer htmlRenderer;

	public MarkdownRenderer(BBCodeGrammarHolder grammarHolder, RenderedTextEnricher enricher,
			SourceReferenceService sourceReferenceService, BBCodeRenderer bbCodeRenderer,
			TemplateExpansion templateExpansion) {
		this.grammarHolder = grammarHolder;
		this.enricher = enricher;
		this.sourceReferenceService = sourceReferenceService;
		this.bbCodeRenderer = bbCodeRenderer;
		this.templateExpansion = templateExpansion;
		this.htmlRenderer = HtmlRenderer.builder()
				.nodeRendererFactory(BBCodeBlockRenderer::new)
				.build();
	}

	private static List<String> implicitItemMarkersDeclaredBy(BBCodeGrammar grammar) {
		return grammar.implicitItemExpansions().stream()
				.map(BBCodeGrammar.ImplicitItemExpansion::marker)
				.toList();
	}

	private ParsersBuiltForMarkers parsers(ContentScope surface) {
		List<String> markers = implicitItemMarkersDeclaredBy(grammarHolder.current(surface));
		ParsersBuiltForMarkers built = parsersBySurface.get(surface);
		if (built != null && built.markers().equals(markers))
			return built;
		ParsersBuiltForMarkers rebuilt = new ParsersBuiltForMarkers(markers,
				Parser.builder()
						.customBlockParserFactory(new BBCodeBlockParserFactory(surface))
						.customInlineContentParserFactory(new ImplicitItemMarkerFactory(markers))
						.includeSourceSpans(IncludeSourceSpans.BLOCKS_AND_INLINES)
						.build(),
				Parser.builder()
						.customInlineContentParserFactory(new ImplicitItemMarkerFactory(markers))
						.build());
		parsersBySurface.put(surface, rebuilt);
		return rebuilt;
	}



	public Parser parserThatLeavesBBCodeBlocksAlone(ContentScope surface) {
		return parsers(surface).thatLeavesBBCodeBlocksAlone();
	}

	public String render(String markdown, OffsetDateTime quotingCreatedTs, ContentScope scope,
			Map<String, String> context) {
		if (markdown == null || markdown.isBlank()) {
			return "";
		}
		List<String> codesCommonmarkWouldEat = enricher.smileyCodesCommonmarkReadsAsBlockMarkers();
		Node document = parsers(scope).thatOpensBBCodeBlocks()
				.parse(maskLeadingSmileyCodes(markdown, codesCommonmarkWouldEat));
		closeEveryBlockWhereItsAuthorWroteTheCloser(document, scope);
		expandTemplatesInMarkdownText(document, scope, context);
		expandBBCodeInMarkdownText(document, quotingCreatedTs, scope);
		return restoreMaskedSmileyCodes(htmlRenderer.render(document), codesCommonmarkWouldEat);
	}

	private static String maskLeadingSmileyCodes(String prepared, List<String> codesCommonmarkWouldEat) {
		String source = withoutMaskCharactersTheAuthorTyped(prepared);
		StringBuilder masked = new StringBuilder(source.length());
		int lineStart = 0;
		while (true) {
			int newline = source.indexOf('\n', lineStart);
			appendLineWithItsLeadingSmileyMasked(masked,
					source.substring(lineStart, newline < 0 ? source.length() : newline), codesCommonmarkWouldEat);
			if (newline < 0)
				return masked.toString();
			masked.append('\n');
			lineStart = newline + 1;
		}
	}

	private static String withoutMaskCharactersTheAuthorTyped(String prepared) {
		return prepared.replace(String.valueOf(SMILEY_MASK_OPEN), "")
				.replace(String.valueOf(SMILEY_MASK_CLOSE), "");
	}

	private static void appendLineWithItsLeadingSmileyMasked(StringBuilder masked, String line,
			List<String> codesCommonmarkWouldEat) {
		int lineContentStart = 0;
		while (lineContentStart < line.length()
				&& (line.charAt(lineContentStart) == ' ' || line.charAt(lineContentStart) == '\t'))
			lineContentStart++;
		int code = indexOfCodeOpeningTheLine(line, lineContentStart, codesCommonmarkWouldEat);
		if (code < 0) {
			masked.append(line);
			return;
		}
		masked.append(line, 0, lineContentStart)
				.append(SMILEY_MASK_OPEN).append(code).append(SMILEY_MASK_CLOSE)
				.append(line, lineContentStart + codesCommonmarkWouldEat.get(code).length(), line.length());
	}

	private static int indexOfCodeOpeningTheLine(String line, int lineContentStart,
			List<String> codesCommonmarkWouldEat) {
		for (int candidate = 0; candidate < codesCommonmarkWouldEat.size(); candidate++) {
			String code = codesCommonmarkWouldEat.get(candidate);
			if (!line.startsWith(code, lineContentStart))
				continue;
			int codeEnd = lineContentStart + code.length();
			if (Character.isLetterOrDigit(code.charAt(code.length() - 1))
					&& codeEnd < line.length() && Character.isLetterOrDigit(line.charAt(codeEnd)))
				continue;
			return candidate;
		}
		return -1;
	}

	private static String restoreMaskedSmileyCodes(String html, List<String> codesCommonmarkWouldEat) {
		Matcher mask = SMILEY_MASK.matcher(html);
		StringBuilder restored = new StringBuilder(html.length());
		while (mask.find())
			mask.appendReplacement(restored, Matcher.quoteReplacement(
					escape(codesCommonmarkWouldEat.get(Integer.parseInt(mask.group(1))))));
		mask.appendTail(restored);
		return restored.toString();
	}

	private record TheCloserItsAuthorWroteInside(List<Node> run, BBCodeBlock block, Node hostBlock,
			String beforeTheCloser, String afterTheCloser) {}

	private void closeEveryBlockWhereItsAuthorWroteTheCloser(Node root, ContentScope surface) {
		Map<String, BBCodeConfig> grammar = grammarHolder.current(surface).configs();
		List<List<Node>> runs = new ArrayList<>();
		collectAdjacentTextRuns(root, runs);
		for (List<Node> run : runs)
			closerThisRunLeavesUnmatched(run, grammar)
					.ifPresent(MarkdownRenderer::closeTheBlockAtTheCloserItsAuthorWrote);
	}

	private static Optional<TheCloserItsAuthorWroteInside> closerThisRunLeavesUnmatched(List<Node> run,
			Map<String, BBCodeConfig> grammar) {
		Node hostBlock = run.get(0).getParent();
		if (hostBlock == null || !(hostBlock.getParent() instanceof BBCodeBlock block))
			return Optional.empty();
		String literal = literalOf(run);
		if (literal.indexOf('[') < 0)
			return Optional.empty();
		return BBCodeParser.parse(literal, grammar).firstUnmatchedCloser(block.code())
				.map(closer -> new TheCloserItsAuthorWroteInside(run, block, hostBlock,
						literal.substring(0, closer.startIndex()), literal.substring(closer.endIndex())));
	}

	private static void closeTheBlockAtTheCloserItsAuthorWrote(TheCloserItsAuthorWroteInside closing) {
		List<Node> afterTheRun = siblingsAfter(closing.run().get(closing.run().size() - 1));
		List<Node> afterTheHostBlock = siblingsAfter(closing.hostBlock());

		Text kept = new Text(closing.beforeTheCloser());
		closing.run().get(0).insertBefore(kept);
		for (Node member : closing.run())
			member.unlink();
		if (closing.beforeTheCloser().isEmpty())
			kept.unlink();

		Paragraph movedOut = new Paragraph();
		if (!closing.afterTheCloser().isEmpty())
			movedOut.appendChild(new Text(closing.afterTheCloser()));
		for (Node node : afterTheRun) {
			node.unlink();
			movedOut.appendChild(node);
		}
		Node anchor = closing.block();
		if (movedOut.getFirstChild() != null) {
			anchor.insertAfter(movedOut);
			anchor = movedOut;
		}
		for (Node node : afterTheHostBlock) {
			node.unlink();
			anchor.insertAfter(node);
			anchor = node;
		}
		if (closing.hostBlock().getFirstChild() == null)
			closing.hostBlock().unlink();
	}

	private static List<Node> siblingsAfter(Node node) {
		List<Node> siblings = new ArrayList<>();
		for (Node sibling = node.getNext(); sibling != null; sibling = sibling.getNext())
			siblings.add(sibling);
		return siblings;
	}

	private void expandTemplatesInMarkdownText(Node root, ContentScope scope, Map<String, String> context) {
		List<List<Node>> runs = new ArrayList<>();
		collectAdjacentTextRuns(root, runs);
		for (List<Node> run : runs)
			expandTemplatesInOneTextRun(run, scope, context);
	}

	private void expandTemplatesInOneTextRun(List<Node> run, ContentScope scope,
			Map<String, String> context) {
		String literal = literalOf(run);
		if (literal.indexOf('[') < 0)
			return;
		String expanded = templateExpansion.expandSource(literal,
				ContentFormat.MARKDOWN, scope, context);
		if (expanded.equals(literal))
			return;
		Node reparsed = parsers(scope).thatOpensBBCodeBlocks().parse(expanded);
		spliceReparsedMarkdownOver(run, reparsed);
	}

	private static void spliceReparsedMarkdownOver(List<Node> run, Node reparsed) {
		Node onlyBlock = reparsed.getFirstChild();
		boolean staysInline = onlyBlock instanceof Paragraph && onlyBlock.getNext() == null;
		if (staysInline) {
			Node anchor = run.get(0);
			for (Node inline = onlyBlock.getFirstChild(); inline != null;) {
				Node next = inline.getNext();
				anchor.insertBefore(inline);
				inline = next;
			}
			for (Node node : run)
				node.unlink();
			return;
		}
		if (!(run.get(0).getParent() instanceof Paragraph enclosingBlock))
			return;
		Node anchor = enclosingBlock;
		for (Node block = reparsed.getFirstChild(); block != null;) {
			Node next = block.getNext();
			anchor.insertAfter(block);
			anchor = block;
			block = next;
		}
		enclosingBlock.unlink();
	}

	private void expandBBCodeInMarkdownText(Node root, OffsetDateTime quotingCreatedTs, ContentScope surface) {
		Map<String, BBCodeConfig> grammar = grammarHolder.current(surface).configs();
		List<List<Node>> runs = new ArrayList<>();
		collectAdjacentTextRuns(root, runs);
		for (List<Node> run : runs)
			expandOneTextRun(run, grammar, quotingCreatedTs);
	}

	private void expandOneTextRun(List<Node> run, Map<String, BBCodeConfig> grammar,
			OffsetDateTime quotingCreatedTs) {
		String literal = literalOf(run);
		if (literal.indexOf('[') < 0)
			return;
		BBCodeDocument parsed = BBCodeParser.parse(literal, grammar);
		if (!anyTagWasRecognised(parsed) || anyTagIsStillAwaitingItsAuthorsCloser(parsed))
			return;
		sourceReferenceService.resolveEverySourceReferenceIn(parsed, quotingCreatedTs);
		if (anyTagEmitsBlockLevelMarkup(parsed)) {
			liftTheWholeParagraphOntoTheBlockLevelExpansion(run, parsed);
			return;
		}
		replaceRunWithInlineExpansion(run, parsed);
	}

	private void liftTheWholeParagraphOntoTheBlockLevelExpansion(List<Node> run, BBCodeDocument parsed) {
		if (!(run.get(0).getParent() instanceof Paragraph enclosingBlock))
			return;
		if (!runIsTheWholeContentOf(run, enclosingBlock))
			return;
		Node anchor = enclosingBlock;
		List<BBCodeNode> phrasing = new ArrayList<>();
		for (BBCodeNode child : parsed.children()) {
			if (child instanceof BBCodeTag tag && tag.contentLevel() == ContentLevel.BLOCK) {
				anchor = anchorAfterPhrasingParagraph(anchor, phrasing);
				HtmlBlock expanded = new HtmlBlock();
				expanded.setLiteral(bbCodeRenderer.renderToHtml(tag));
				anchor.insertAfter(expanded);
				anchor = expanded;
				continue;
			}
			phrasing.add(child);
		}
		anchorAfterPhrasingParagraph(anchor, phrasing);
		enclosingBlock.unlink();
	}

	private static Node anchorAfterPhrasingParagraph(Node anchor, List<BBCodeNode> phrasing) {
		List<Node> expanded = new ArrayList<>();
		for (BBCodeNode node : phrasing)
			expanded.addAll(inlineNodesFor(node));
		phrasing.clear();
		if (allNodesAreBlankText(expanded))
			return anchor;
		Paragraph paragraph = new Paragraph();
		for (Node node : expanded)
			paragraph.appendChild(node);
		anchor.insertAfter(paragraph);
		return paragraph;
	}

	private static boolean allNodesAreBlankText(List<Node> nodes) {
		for (Node node : nodes)
			if (!(node instanceof Text text) || !text.getLiteral().isBlank())
				return false;
		return true;
	}

	private void replaceRunWithInlineExpansion(List<Node> run, BBCodeDocument parsed) {
		List<Node> expanded = inlineNodesFor(parsed);
		Node cursor = run.get(0);
		for (Node node : expanded) {
			cursor.insertAfter(node);
			cursor = node;
		}
		for (Node member : run)
			member.unlink();
	}

	private sealed interface PendingExpansion {

		record Unexpanded(BBCodeNode node) implements PendingExpansion {}

		record AlreadyBuilt(Node node) implements PendingExpansion {}
	}

	private static List<Node> inlineNodesFor(BBCodeNode root) {
		List<Node> expanded = new ArrayList<>();
		Deque<PendingExpansion> pending = new ArrayDeque<>();
		pending.push(new PendingExpansion.Unexpanded(root));
		while (!pending.isEmpty()) {
			switch (pending.pop()) {
				case PendingExpansion.AlreadyBuilt built -> expanded.add(built.node());
				case PendingExpansion.Unexpanded unexpanded -> {
					switch (unexpanded.node()) {
						case BBCodeText text -> expanded.add(markdownTextFor(text));
						case BBCodeDocument document ->
								pushChildrenInExpansionOrder(pending, document.children());
						case BBCodeTag tag -> {
							expanded.add(rawMarkupFor(tag.openMarkup()));
							tag.closeMarkup().ifPresent(markup ->
									pending.push(new PendingExpansion.AlreadyBuilt(rawMarkupFor(markup))));
							pushChildrenInExpansionOrder(pending, tag.children());
						}
					}
				}
			}
		}
		return expanded;
	}

	private static void pushChildrenInExpansionOrder(Deque<PendingExpansion> pending, List<BBCodeNode> children) {
		for (int child = children.size() - 1; child >= 0; child--)
			pending.push(new PendingExpansion.Unexpanded(children.get(child)));
	}

	private static Node markdownTextFor(BBCodeText text) {
		return new Text(switch (text.escaping()) {
			case PASS_THROUGH -> text.sourceText();
			case VERBATIM_LITERAL -> BBCodeText.lineBreakMarkupAsNewlines(text.sourceText());
		});
	}

	private static Node rawMarkupFor(String markup) {
		HtmlInline raw = new HtmlInline();
		raw.setLiteral(markup);
		return raw;
	}

	private static void collectAdjacentTextRuns(Node root, List<List<Node>> runs) {
		Deque<Node> pendingParents = new ArrayDeque<>();
		pendingParents.push(root);
		while (!pendingParents.isEmpty()) {
			List<Node> current = new ArrayList<>();
			for (Node child = pendingParents.pop().getFirstChild(); child != null; child = child.getNext()) {
				if (child instanceof Text || child instanceof SoftLineBreak) {
					current.add(child);
					continue;
				}
				if (!current.isEmpty()) {
					runs.add(current);
					current = new ArrayList<>();
				}
				pendingParents.push(child);
			}
			if (!current.isEmpty())
				runs.add(current);
		}
	}

	private static boolean runIsTheWholeContentOf(List<Node> run, Node enclosingBlock) {
		int members = 0;
		for (Node child = enclosingBlock.getFirstChild(); child != null; child = child.getNext())
			members++;
		return members == run.size();
	}

	private static String literalOf(List<Node> run) {
		StringBuilder joined = new StringBuilder();
		for (Node member : run)
			joined.append(member instanceof Text text ? text.getLiteral() : "\n");
		return joined.toString();
	}

	private static boolean anyTagWasRecognised(BBCodeNode node) {
		return anyTagMatches(node, tag -> true);
	}

	private static boolean anyTagEmitsBlockLevelMarkup(BBCodeNode node) {
		return anyTagMatches(node, tag -> tag.contentLevel() == ContentLevel.BLOCK);
	}

	private static boolean anyTagIsStillAwaitingItsAuthorsCloser(BBCodeNode node) {
		return anyTagMatches(node, BBCodeTag::isAwaitingItsAuthorsCloser);
	}

	private static boolean anyTagMatches(BBCodeNode root, Predicate<BBCodeTag> matches) {
		for (BBCodeNode node : root.selfAndEveryDescendant())
			if (node instanceof BBCodeTag tag && matches.test(tag))
				return true;
		return false;
	}

	static final class ImplicitItemMarkerFactory implements InlineContentParserFactory {

		private final Map<Character, String> markerByItsInnerCharacter;

		ImplicitItemMarkerFactory(Collection<String> declaredMarkers) {
			Map<Character, String> indexed = new LinkedHashMap<>();
			for (String marker : declaredMarkers)
				indexed.putIfAbsent(marker.charAt(1), marker);
			this.markerByItsInnerCharacter = Map.copyOf(indexed);
		}

		@Override
		public Set<Character> getTriggerCharacters() {
			return markerByItsInnerCharacter.keySet();
		}

		@Override
		public InlineContentParser create() {
			return new ImplicitItemMarker(markerByItsInnerCharacter);
		}
	}

	private static final class ImplicitItemMarker implements InlineContentParser {

		private final Map<Character, String> markerByItsInnerCharacter;

		private ImplicitItemMarker(Map<Character, String> markerByItsInnerCharacter) {
			this.markerByItsInnerCharacter = markerByItsInnerCharacter;
		}

		@Override
		public ParsedInline tryParse(InlineParserState state) {
			Scanner scanner = state.scanner();
			String marker = markerByItsInnerCharacter.get((char) scanner.peek());
			if (marker == null || scanner.peekPreviousCodePoint() != marker.charAt(0))
				return ParsedInline.none();
			scanner.next();
			if (scanner.peek() != marker.charAt(2))
				return ParsedInline.none();
			return ParsedInline.of(new Text(String.valueOf(marker.charAt(1))), scanner.position());
		}
	}

	public static final class BBCodeBlock extends CustomBlock {

		private final String code;

		private final String openMarkup;

		private final String closeMarkup;

		private final List<String> verbatimLines = new ArrayList<>();

		BBCodeBlock(String code, String openMarkup, String closeMarkup) {
			this.code = code;
			this.openMarkup = openMarkup;
			this.closeMarkup = closeMarkup;
		}

		String code() {
			return code;
		}

		String openMarkup() {
			return openMarkup;
		}

		String closeMarkup() {
			return closeMarkup;
		}

		List<String> verbatimLines() {
			return verbatimLines;
		}
	}

	private final class BBCodeBlockParserFactory implements BlockParserFactory {

		private final ContentScope surface;

		private BBCodeBlockParserFactory(ContentScope surface) {
			this.surface = surface;
		}

		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (state.getIndent() >= MARKDOWN_INDENTED_CODE_BLOCK_INDENT)
				return BlockStart.none();
			if (matchedBlockParser.getMatchedBlockParser().getBlock() instanceof Paragraph)
				return BlockStart.none();
			CharSequence line = state.getLine().getContent();
			Optional<BBCodeParser.TagToken> token =
					BBCodeParser.tagWrittenAt(line.toString(), state.getNextNonSpaceIndex())
							.filter(written -> !written.closing())
							.filter(written -> restOfTheLineIsBlank(line, written.endIndex()));
			if (token.isEmpty())
				return BlockStart.none();
			BBCodeConfig config = grammarHolder.current(surface).configs().get(token.get().code());
			if (config == null || Boolean.TRUE.equals(config.getSelfClosingFlag()))
				return BlockStart.none();
			Optional<BBCodeParser.ExpandedOpener> expanded =
					BBCodeParser.theTagAnOpeningCodeExpandsTo(config, token.get().attributeText());
			if (expanded.isEmpty())
				return BlockStart.none();
			BBCodeParser.ExpandedOpener opening = expanded.get();
			if (opening.contentSlotIndex().isPresent() || opening.suppressesItsBodyOutput()
					|| opening.namesASourceToPullFrom())
				return BlockStart.none();
			boolean holdsVerbatimText = Boolean.FALSE.equals(config.getProcessContentFlag());
			if (!holdsVerbatimText && !expansionCanHoldParsedMarkdownBlocks(opening.openMarkup()))
				return BlockStart.none();
			BBCodeBlockParser opened = new BBCodeBlockParser(
					new BBCodeBlock(token.get().code(), opening.openMarkup(), opening.closeMarkup()),
					token.get().code(), holdsVerbatimText);
			return BlockStart.of(opened).atIndex(token.get().endIndex());
		}

		private boolean restOfTheLineIsBlank(CharSequence line, int from) {
			return line.subSequence(Math.min(from, line.length()), line.length()).toString().isBlank();
		}

		private boolean expansionCanHoldParsedMarkdownBlocks(String markup) {
			for (String element : ContentLevel.elementNamesIn(markup))
				if (!ELEMENTS_THAT_MAY_HOLD_PARSED_MARKDOWN_BLOCKS.contains(element))
					return false;
			return true;
		}
	}

	static final class BBCodeBlockParser extends AbstractBlockParser {

		private final BBCodeBlock block;

		private final String code;

		private final boolean holdsVerbatimText;

		private int nestingDepth;

		private boolean authorsCloserArrived;

		BBCodeBlockParser(BBCodeBlock block, String code, boolean holdsVerbatimText) {
			this.block = block;
			this.code = code;
			this.holdsVerbatimText = holdsVerbatimText;
		}

		@Override
		public Block getBlock() {
			return block;
		}

		@Override
		public boolean isContainer() {
			return !holdsVerbatimText;
		}

		@Override
		public boolean canContain(Block childBlock) {
			return !holdsVerbatimText;
		}

		@Override
		public BlockContinue tryContinue(ParserState state) {
			if (authorsCloserArrived)
				return BlockContinue.none();
			CharSequence line = state.getLine().getContent();
			if (!holdsVerbatimText && aMarkdownVerbatimBlockOwnsThisLine(state))
				return BlockContinue.atIndex(state.getIndex());
			String remainder = line
					.subSequence(Math.min(state.getNextNonSpaceIndex(), line.length()), line.length())
					.toString()
					.stripTrailing();
			if (holdsVerbatimText) {
				if (namesTheCloserOfThisCode(remainder)) {
					authorsCloserArrived = true;
					return BlockContinue.finished();
				}
				block.verbatimLines().add(line
						.subSequence(Math.min(state.getIndex(), line.length()), line.length()).toString());
				return BlockContinue.atIndex(line.length());
			}
			if (namesAnOpenerOfThisCode(remainder)) {
				nestingDepth++;
				return BlockContinue.atIndex(state.getIndex());
			}
			if (namesTheCloserOfThisCode(remainder)) {
				if (nestingDepth == 0) {
					authorsCloserArrived = true;
					return BlockContinue.finished();
				}
				nestingDepth--;
			}
			return BlockContinue.atIndex(state.getIndex());
		}

		private static boolean aMarkdownVerbatimBlockOwnsThisLine(ParserState state) {
			Block active = state.getActiveBlockParser().getBlock();
			return active instanceof FencedCodeBlock || active instanceof IndentedCodeBlock;
		}

		private boolean namesTheCloserOfThisCode(String text) {
			return onlyTagWrittenIn(text)
					.filter(BBCodeParser.TagToken::closing)
					.filter(token -> token.attributeText().isEmpty())
					.isPresent();
		}

		private boolean namesAnOpenerOfThisCode(String text) {
			return onlyTagWrittenIn(text).filter(token -> !token.closing()).isPresent();
		}

		private Optional<BBCodeParser.TagToken> onlyTagWrittenIn(String text) {
			return BBCodeParser.tagWrittenAt(text, 0)
					.filter(token -> token.endIndex() == text.length())
					.filter(token -> token.code().equalsIgnoreCase(code));
		}
	}

	static final class BBCodeBlockRenderer implements NodeRenderer {

		private final HtmlNodeRendererContext context;

		BBCodeBlockRenderer(HtmlNodeRendererContext context) {
			this.context = context;
		}

		@Override
		public Set<Class<? extends Node>> getNodeTypes() {
			return Set.of(BBCodeBlock.class);
		}

		@Override
		public void render(Node node) {
			BBCodeBlock block = (BBCodeBlock) node;
			context.getWriter().line();
			context.getWriter().raw(block.openMarkup());
			if (!block.verbatimLines().isEmpty())
				context.getWriter().raw(escape(String.join("\n", block.verbatimLines())));
			for (Node child = node.getFirstChild(); child != null; child = child.getNext())
				context.render(child);
			context.getWriter().raw(block.closeMarkup());
			context.getWriter().line();
		}
	}
}
