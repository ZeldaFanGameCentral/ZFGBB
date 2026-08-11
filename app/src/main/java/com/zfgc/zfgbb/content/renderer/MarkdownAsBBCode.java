package com.zfgc.zfgbb.content.renderer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

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

import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.model.forum.MarkdownEquivalent;

final class MarkdownAsBBCode {

	static final String ORDERED_LIST_STYLE = "decimal";

	private final BBCodeGrammar grammar;

	private final MarkdownRenderer markdownRenderer;

	private final ConversionNotes conversionNotes;

	private final ContentScope scope;

	private final StringBuilder bbCode = new StringBuilder();

	private final Set<String> notes = new LinkedHashSet<>();

	private final Deque<PendingMarkdown> pendingMarkdown = new ArrayDeque<>();

	MarkdownAsBBCode(BBCodeGrammar grammar, MarkdownRenderer markdownRenderer,
			ConversionNotes conversionNotes, ContentScope scope) {
		this.grammar = grammar;
		this.markdownRenderer = markdownRenderer;
		this.conversionNotes = conversionNotes;
		this.scope = scope;
	}

	ConvertedContent write(String markdown) {
		write(markdownRenderer.parserThatLeavesBBCodeBlocksAlone(scope).parse(markdown));
		return new ConvertedContent(bbCode.toString().stripTrailing(), List.copyOf(notes));
	}

	private void write(Node root) {
		writeChildren(root);
		while (!pendingMarkdown.isEmpty())
			switch (pendingMarkdown.pop()) {
				case PendingMarkdown.Unwritten unwritten -> writeNode(unwritten.node());
				case PendingMarkdown.Finishing finishing -> finishing.restOfTheConstruct().run();
			}
	}

	private void writeChildren(Node parent) {
		List<Node> children = new ArrayList<>();
		for (Node child = parent.getFirstChild(); child != null; child = child.getNext())
			children.add(child);
		for (int child = children.size() - 1; child >= 0; child--)
			pendingMarkdown.push(new PendingMarkdown.Unwritten(children.get(child)));
	}

	private void finishWith(Runnable restOfTheConstruct) {
		pendingMarkdown.push(new PendingMarkdown.Finishing(restOfTheConstruct));
	}

	private sealed interface PendingMarkdown {

		record Unwritten(Node node) implements PendingMarkdown {}

		record Finishing(Runnable restOfTheConstruct) implements PendingMarkdown {}
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
			case FencedCodeBlock fenced ->
					writeCodeBlock(fenced.getLiteral(), Optional.ofNullable(fenced.getFenceCharacter()));
			case IndentedCodeBlock indented -> writeCodeBlock(indented.getLiteral(), Optional.empty());
			case ThematicBreak ignored -> writeThematicBreak();
			case HtmlBlock html -> endBlockWith(html.getLiteral().stripTrailing());
			case LinkReferenceDefinition ignored -> {}
			default -> writeChildren(node);
		}
	}

	private Optional<String> canonicalCodeFor(MarkdownEquivalent equivalent) {
		Optional<String> code = grammar.canonicalCodeFor(equivalent).map(BBCodeConfig::getCode);
		if (code.isEmpty())
			notes.add(conversionNotes.noEnabledBBCodeCarriesThisConstruct());
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
			notes.add(conversionNotes.linkTitleText());
		Optional<String> code = canonicalCodeFor(MarkdownEquivalent.LINK);
		code.ifPresent(present ->
				bbCode.append('[').append(present).append('=').append(link.getDestination()).append(']'));
		finishWith(() -> code.ifPresent(present -> bbCode.append("[/").append(present).append(']')));
		writeChildren(link);
	}

	private void writeImage(Image image) {
		if (image.getTitle() != null && !image.getTitle().isBlank())
			notes.add(conversionNotes.linkTitleText());
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
			notes.add(conversionNotes.inlineCodeSpanCarryingBBCode());
		bbCode.append(BBCodeAsMarkdown.inlineCodeSpanHolding(code.getLiteral()));
	}

	private void writeBlock(Node node) {
		finishWith(this::endBlock);
		writeChildren(node);
	}

	private void writeHeading(Heading heading) {
		Optional<String> code = grammar.canonicalHeadingCodeForLevel(heading.getLevel())
				.map(BBCodeConfig::getCode);
		if (code.isEmpty())
			notes.add(conversionNotes.noEnabledBBCodeCarriesThisConstruct());
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
			notes.add(conversionNotes.orderedListNotStartingAtOne());
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
		Optional<String> itemCode = grammar.canonicalCodeFor(MarkdownEquivalent.LIST)
				.flatMap(BBCodeConfig::declaredImplicitItemCode);
		itemCode.ifPresent(present -> bbCode.append('[').append(present).append(']'));
		finishWith(() -> closeListItem(itemCode));
		writeChildren(node);
	}

	private void closeListItem(Optional<String> itemCode) {
		trimTrailingBlankLines();
		itemCode.ifPresent(present -> bbCode.append("[/").append(present).append("]\n"));
	}

	private void writeCodeBlock(String literal, Optional<String> fenceCharacter) {
		String body = literal.stripTrailing();
		Optional<String> code = canonicalCodeFor(MarkdownEquivalent.FENCED_CODE);
		if (code.isEmpty()
				|| body.toLowerCase(Locale.ROOT).contains("[/" + code.get().toLowerCase(Locale.ROOT) + "]")) {
			if (code.isPresent())
				notes.add(conversionNotes.codeBlockCarryingTheBBCodeCodeCloser());
			String fence = fenceCharacter.filter(written -> !written.isEmpty()).orElse("`")
					.repeat(Math.max(3, BBCodeAsMarkdown.longestBacktickRunIn(body) + 1));
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
