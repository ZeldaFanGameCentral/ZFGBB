package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText.TextEscaping;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.model.forum.BBCodeConfig.ParsedAttributes;
import com.zfgc.zfgbb.model.forum.BBCodeDateElement;

public final class BBCodeParser {

	public static final String CONTENT_PLACEHOLDER = "{{c}}";

	public record TagToken(boolean closing, String code, String attributeText, int endIndex) {}

	public record ExpandedOpener(BBCodeConfig config, String openMarkup, OptionalInt contentSlotIndex,
			ContentLevel contentLevel, String closeMarkup, ParsedAttributes parsedAttributes,
			Optional<BBCodeAttributeMode> attributeMode) {

		public boolean namesASourceToPullFrom() {
			return config.referencesSourceContent() && attributeMode
					.flatMap(mode -> parsedAttributes.valueOfTheAttributeDeclaredBy(mode,
							config.getSourceReferenceAttribute()))
					.isPresent();
		}

		public boolean suppressesItsBodyOutput() {
			return attributeMode
					.filter(mode -> Boolean.TRUE.equals(mode.getContentIsAttributeFlag()))
					.filter(mode -> !Boolean.TRUE.equals(mode.getOutputContentFlag()))
					.isPresent();
		}

		private String markupWithContentSlotHolding(String bodySource) {
			if (contentSlotIndex.isEmpty())
				return openMarkup;
			int slot = contentSlotIndex.getAsInt();
			return openMarkup.substring(0, slot) + bodySource
					+ openMarkup.substring(slot + CONTENT_PLACEHOLDER.length());
		}
	}

	private final String source;

	private final int length;

	private final Map<String, BBCodeConfig> grammar;

	private final BBCodeDocument document = new BBCodeDocument();

	private final Deque<OpenTag> openTags = new ArrayDeque<>();

	private final Map<String, Integer> openInstanceCountsByCode = new HashMap<>();

	private int cursor;

	private int pendingTextFrom;

	private boolean emittingTagBodies = true;

	private boolean anyTagHasOpened;

	private Optional<OpenTag> tagAwaitingItsContentSlot = Optional.empty();

	BBCodeParser(String source, Map<String, BBCodeConfig> grammar) {
		this.source = source;
		this.length = source.length();
		this.grammar = grammar;
	}

	public static BBCodeDocument parse(String source, Map<String, BBCodeConfig> grammar) {
		return new BBCodeParser(source, grammar).parseDocument();
	}

	BBCodeDocument parseDocument() {
		while (cursor < length) {
			if (implicitItemMarkerOpensHere()) {
				openTheNextImplicitItem();
				continue;
			}
			// did we find a bbcode? maybe..
			if (source.charAt(cursor) != '[') {
				cursor++;
				continue;
			}
			int bracketIndex = cursor;
			int scan = bracketIndex + 1;
			if (scan >= length)
				break;
			// hold the phone, this might be a closing brace
			boolean closing = source.charAt(scan) == '/';
			if (closing) {
				scan++;
				if (scan >= length) {
					flushPendingTextAt(length);
					break;
				}
			}
			// get the alphabetical characters immediately following the brace
			// edge case: we hit the end of the string
			int codeStart = scan;
			scan++;
			while (scan < length && isTagCodeCharacter(source.charAt(scan)))
				scan++;
			String candidateCode = source.substring(codeStart, scan).toUpperCase(Locale.ROOT);
			// check if this matches a valid bbcode. If so, find the next ]
			// edge cases: we hit the end of the string, or we hit another [
			// or we're already in a close brace
			if (!grammar.containsKey(candidateCode)) {
				// it wasn't actually a bbcode..output what we found up to this point
				flushPendingTextAt(scan);
				cursor = scan;
				continue;
			}
			int attributeStart = scan;
			while (scan < length && source.charAt(scan) != ']' && source.charAt(scan) != '[')
				scan++;
			if (scan >= length || source.charAt(scan) == '[') {
				cursor = scan;
				continue;
			}
			cursor = consumeRecognisedTag(new RecognisedTag(closing, candidateCode,
					source.substring(attributeStart, scan), bracketIndex, scan + 1));
		}
		// if we reach the end, but we're not in a bbcode state
		// append the remaining junk
		flushPendingTextAt(length);
		// if we have any unfinished states, close them out
		closeEveryTagLeftOpen();
		return document;
	}

	public static Optional<TagToken> tagWrittenAt(String source, int bracketIndex) {
		return new BBCodeParser(source, Map.of()).tagWrittenAt(bracketIndex);
	}

	Optional<TagToken> tagWrittenAt(int bracketIndex) {
		if (bracketIndex >= length || source.charAt(bracketIndex) != '[')
			return Optional.empty();
		int scan = bracketIndex + 1;
		if (scan >= length)
			return Optional.empty();
		boolean closing = source.charAt(scan) == '/';
		if (closing)
			scan++;
		int codeStart = scan;
		while (scan < length && isTagCodeCharacter(source.charAt(scan)))
			scan++;
		if (scan == codeStart)
			return Optional.empty();
		String code = source.substring(codeStart, scan).toUpperCase(Locale.ROOT);
		int attributeStart = scan;
		while (scan < length && source.charAt(scan) != ']') {
			if (source.charAt(scan) == '[')
				return Optional.empty();
			scan++;
		}
		if (scan >= length)
			return Optional.empty();
		return Optional.of(new TagToken(closing, code, source.substring(attributeStart, scan), scan + 1));
	}

	public static Optional<ExpandedOpener> theTagAnOpeningCodeExpandsTo(BBCodeConfig config, String attributeText) {
		ParsedAttributes parsed = new AttributeTokenizer(config, attributeText).parseAttributeValues();
		BBCodeAttributeMode attributeMode = config.getAttributeConfig().get(parsed.attFormat());
		if (attributeMode == null)
			return Optional.empty();
		String openMarkup =
				markupWithEveryAttributeSlotFilled(attributeMode.getOpenTag(), attributeMode, parsed);
		String closeMarkup = attributeMode.getCloseTag() != null
				? attributeMode.getCloseTag()
				: config.getEndTag();
		int contentSlotIndex = Boolean.TRUE.equals(attributeMode.getContentIsAttributeFlag())
				? openMarkup.indexOf(CONTENT_PLACEHOLDER)
				: -1;
		return Optional.of(new ExpandedOpener(config, openMarkup,
				contentSlotIndex >= 0 ? OptionalInt.of(contentSlotIndex) : OptionalInt.empty(),
				ContentLevel.contentLevelOfMarkup(openMarkup, closeMarkup), closeMarkup, parsed,
				Optional.of(attributeMode)));
	}

	public static String markupWithEveryAttributeSlotFilled(String markup, BBCodeAttributeMode attributeMode,
			ParsedAttributes parsed) {
		String filled = markup;
		List<String> rawValues = parsed.rawValuesInTheOrder(attributeMode);
		for (int index = 0; index < attributeMode.getAttributes().size(); index++) {
			BBCodeAttribute attribute = attributeMode.getAttributes().get(index);
			String value = attribute.getDataType() == AttributeDataType.TIMESTAMP
					? BBCodeDateElement.epochSecondsAsDateElement(rawValues.get(index))
					: attribute.transformValue(rawValues.get(index));
			filled = filled.replace(attribute.getAttributeIndex(), value);
		}
		return filled;
	}

	private static boolean isTagCodeCharacter(char candidate) {
		return (Character.toLowerCase(candidate) >= 'a' && Character.toLowerCase(candidate) <= 'z')
				|| (candidate >= '0' && candidate <= '9');
	}

	private record RecognisedTag(boolean closing, String code, String attributeText, int bracketIndex,
			int endIndex) {}

	private int consumeRecognisedTag(RecognisedTag tag) {
		if (tag.closing())
			return consumeClosingTag(tag);
		BBCodeConfig config = grammar.get(tag.code());
		if (Boolean.TRUE.equals(config.getSelfClosingFlag()) && innermostTagAcceptsMarkup())
			return consumeSelfClosingTag(tag, config);
		return consumeOpeningTag(tag, config);
	}

	private int consumeClosingTag(RecognisedTag tag) {
		if (openTags.stream().noneMatch(alreadyOpen -> alreadyOpen.code().equals(tag.code())))
			document.recordACloserNoOpenerInThisSourceMatched(
					new AuthoredCloser(tag.code(), tag.bracketIndex(), tag.endIndex()));
		if (!anyTagHasOpened)
			return tag.endIndex();
		if (openImplicitItemBelongsTo(tag.code()))
			closeTheOpenImplicitItemAt(tag.bracketIndex());
		if (openTags.isEmpty()
				|| (!openTags.peek().code().equals(tag.code()) && openTags.peek().processesContent()))
			return closeOnBehalfOfAMismatchedTag(tag);
		// this is a matched closing tag
		if (openTags.peek().code().equals(tag.code()))
			return closeTheMatchingTag(tag);
		return tag.endIndex();
	}

	// we've got a stray closing tag
	private int closeOnBehalfOfAMismatchedTag(RecognisedTag tag) {
		appendText(pendingTextFrom, tag.endIndex(), TextEscaping.PASS_THROUGH);
		pendingTextFrom = tag.endIndex();
		// revert to the previous state if one exists
		if (!openTags.isEmpty()) {
			String codeOfTheTagBeingPopped = openTags.peek().code();
			closeInnermostTag(tag.endIndex());
			adjustOpenInstanceCount(codeOfTheTagBeingPopped, -1);
		}
		return tag.endIndex();
	}

	private int closeTheMatchingTag(RecognisedTag tag) {
		// revert to previous state
		OpenTag innermost = openTags.peek();
		innermost.recordThatItsAuthorsCloserArrived();
		if (innermost.processesContent() || innermost.openInstanceOrdinal() == 0) {
			fillTheContentSlotFrom(tag.bracketIndex());
			if (emittingTagBodies)
				appendText(pendingTextFrom, tag.bracketIndex(), innermost.processesContent()
						? TextEscaping.PASS_THROUGH
						: TextEscaping.VERBATIM_LITERAL);
			closeInnermostTag(tag.endIndex(), tag.endIndex() - tag.bracketIndex());
			pendingTextFrom = tag.endIndex();
			emittingTagBodies = true;
		} else {
			OpenTag popped = openTags.pop();
			popped.recordThatTheAuthoredSourceEndsAt(tag.endIndex(), tag.endIndex() - tag.bracketIndex());
			currentChildren().add(popped.finishedWithoutACloser());
		}
		adjustOpenInstanceCount(tag.code(), -1);
		return tag.endIndex();
	}

	private int consumeSelfClosingTag(RecognisedTag tag, BBCodeConfig config) {
		appendText(pendingTextFrom, tag.bracketIndex(), TextEscaping.PASS_THROUGH);
		currentChildren().add(new BBCodeTag(config, config.getEndTag(), Optional.empty(),
				ContentLevel.contentLevelOfMarkup(config.getEndTag(), config.getEndTag()),
				new AuthoredSource(tag.attributeText(), tag.bracketIndex(), tag.endIndex(),
						tag.endIndex() - tag.bracketIndex(), 0),
				ParsedAttributes.noneWereWritten(), Optional.empty(), false, false, new ArrayList<>()));
		pendingTextFrom = tag.endIndex();
		return tag.endIndex();
	}

	private int consumeOpeningTag(RecognisedTag tag, BBCodeConfig config) {
		// just doing this lazy for now...
		if (!innermostTagAcceptsMarkup())
			return tag.endIndex();
		Optional<ExpandedOpener> expanded = theTagAnOpeningCodeExpandsTo(config, tag.attributeText());
		if (expanded.isEmpty()) {
			document.recordAnOpenerWhoseShapeMatchedNoMode(new AuthoredOpener(config, tag.attributeText(),
					new AttributeTokenizer(config, tag.attributeText()).parseAttributeValues()));
			appendText(pendingTextFrom, tag.endIndex(), TextEscaping.PASS_THROUGH);
			pendingTextFrom = tag.endIndex();
			return tag.endIndex();
		}
		// state change
		// record whatever we found up to this point
		// replace the bbcode with its html opening
		appendText(pendingTextFrom, tag.bracketIndex(), TextEscaping.PASS_THROUGH);
		if (expanded.get().suppressesItsBodyOutput())
			emittingTagBodies = false;
		pendingTextFrom = tag.endIndex();
		OpenTag opened = pushOpenTag(expanded.get(), tag.code(), new AuthoredSource(tag.attributeText(),
				tag.bracketIndex(), length, tag.endIndex() - tag.bracketIndex(), 0), Optional.empty());
		if (expanded.get().contentSlotIndex().isPresent())
			tagAwaitingItsContentSlot = Optional.of(opened);
		anyTagHasOpened = true;
		return tag.endIndex();
	}

	private void fillTheContentSlotFrom(int closingBracketIndex) {
		if (tagAwaitingItsContentSlot.isEmpty())
			return;
		tagAwaitingItsContentSlot.get()
				.fillItsContentSlotFrom(source.substring(pendingTextFrom, closingBracketIndex));
		tagAwaitingItsContentSlot = Optional.empty();
	}

	private OpenTag pushOpenTag(ExpandedOpener opener, String code, AuthoredSource authoredSource,
			Optional<OpenTag> implicitContainer) {
		int openInstanceOrdinal = openInstanceCountsByCode.getOrDefault(code, 0);
		OpenTag opened = new OpenTag(opener, code, authoredSource, openInstanceOrdinal, implicitContainer);
		openTags.push(opened);
		openInstanceCountsByCode.put(code, openInstanceOrdinal + 1);
		return opened;
	}

	private boolean implicitItemMarkerOpensHere() {
		return containerAwaitingImplicitItems()
				.filter(container -> source.startsWith(container.implicitItemMarker().orElseThrow(), cursor))
				.isPresent();
	}

	private Optional<OpenTag> containerAwaitingImplicitItems() {
		OpenTag innermost = openTags.peek();
		if (innermost == null)
			return Optional.empty();
		OpenTag container = innermost.implicitContainer().orElse(innermost);
		return container.implicitItemMarker().isPresent()
				&& grammar.containsKey(container.implicitItemCode())
						? Optional.of(container)
						: Optional.empty();
	}

	private void openTheNextImplicitItem() {
		OpenTag container = containerAwaitingImplicitItems().orElseThrow();
		int markerEnd = cursor + container.implicitItemMarker().orElseThrow().length();
		if (openTags.peek() == container)
			appendText(pendingTextFrom, cursor, escapingForTextInsideTheInnermostTag());
		else
			closeTheOpenImplicitItemAt(cursor);
		BBCodeConfig itemConfig = grammar.get(container.implicitItemCode());
		Optional<ExpandedOpener> expanded = theTagAnOpeningCodeExpandsTo(itemConfig, "");
		pendingTextFrom = expanded.isPresent() ? markerEnd : cursor;
		expanded.ifPresent(opener -> pushOpenTag(opener, container.implicitItemCode(),
				new AuthoredSource("", cursor, length, markerEnd - cursor, 0), Optional.of(container)));
		cursor = markerEnd;
	}

	private boolean openImplicitItemBelongsTo(String containerCode) {
		OpenTag innermost = openTags.peek();
		return innermost != null && innermost.implicitContainer()
				.filter(container -> container.code().equals(containerCode)).isPresent();
	}

	private void closeTheOpenImplicitItemAt(int boundary) {
		String itemCode = openTags.peek().code();
		appendText(pendingTextFrom, boundary, escapingForTextInsideTheInnermostTag());
		pendingTextFrom = boundary;
		closeInnermostTag(boundary);
		adjustOpenInstanceCount(itemCode, -1);
	}

	private void closeInnermostTag(int authoredSourceBoundary) {
		closeInnermostTag(authoredSourceBoundary, 0);
	}

	private void closeInnermostTag(int authoredSourceBoundary, int closerLength) {
		OpenTag closing = openTags.pop();
		closing.recordThatTheAuthoredSourceEndsAt(authoredSourceBoundary, closerLength);
		BBCodeTag finished = closing.finished();
		if (closing.openedByAnImplicitMarker() && finished.children().isEmpty())
			return;
		currentChildren().add(finished);
	}

	private void closeEveryTagLeftOpen() {
		while (!openTags.isEmpty())
			closeInnermostTag(length);
	}

	private boolean innermostTagAcceptsMarkup() {
		return openTags.isEmpty() || openTags.peek().processesContent();
	}

	private void adjustOpenInstanceCount(String code, int delta) {
		openInstanceCountsByCode.put(code, openInstanceCountsByCode.getOrDefault(code, 0) + delta);
	}

	private void flushPendingTextAt(int boundary) {
		appendText(pendingTextFrom, boundary, escapingForTextInsideTheInnermostTag());
		pendingTextFrom = boundary;
	}

	private TextEscaping escapingForTextInsideTheInnermostTag() {
		if (openTags.isEmpty() || !emittingTagBodies || openTags.peek().processesContent())
			return TextEscaping.PASS_THROUGH;
		return TextEscaping.VERBATIM_LITERAL;
	}

	private void appendText(int from, int to, TextEscaping escaping) {
		if (to <= from)
			return;
		currentChildren().add(new BBCodeText(source.substring(from, to), escaping));
	}

	private List<BBCodeNode> currentChildren() {
		return openTags.isEmpty() ? document.children() : openTags.peek().children();
	}

	private final class OpenTag {

		private final ExpandedOpener opener;

		private final String code;

		private final int openInstanceOrdinal;

		private final Optional<OpenTag> implicitContainer;

		private final List<BBCodeNode> children = new ArrayList<>();

		private AuthoredSource authoredSource;

		private String openMarkup;

		private boolean isAwaitingItsAuthorsCloser = true;

		private OpenTag(ExpandedOpener opener, String code, AuthoredSource authoredSource, int openInstanceOrdinal,
				Optional<OpenTag> implicitContainer) {
			this.opener = opener;
			this.code = code;
			this.authoredSource = authoredSource;
			this.openMarkup = opener.openMarkup();
			this.openInstanceOrdinal = openInstanceOrdinal;
			this.implicitContainer = implicitContainer;
		}

		private ExpandedOpener opener() {
			return opener;
		}

		private String code() {
			return code;
		}

		private List<BBCodeNode> children() {
			return children;
		}

		private Optional<OpenTag> implicitContainer() {
			return implicitContainer;
		}

		private boolean openedByAnImplicitMarker() {
			return implicitContainer.isPresent();
		}

		private Optional<String> implicitItemMarker() {
			return opener.config().declaredImplicitItemMarker();
		}

		private String implicitItemCode() {
			return opener.config().declaredImplicitItemCode().map(String::toUpperCase).orElse("");
		}

		private boolean processesContent() {
			return Boolean.TRUE.equals(opener.config().getProcessContentFlag());
		}

		private int openInstanceOrdinal() {
			return openInstanceOrdinal;
		}

		private void recordThatItsAuthorsCloserArrived() {
			isAwaitingItsAuthorsCloser = false;
		}

		private void recordThatTheAuthoredSourceEndsAt(int boundary, int closerLength) {
			authoredSource = new AuthoredSource(authoredSource.attributeText(), authoredSource.startIndex(), boundary,
					authoredSource.openerLength(), closerLength);
		}

		private void fillItsContentSlotFrom(String bodySource) {
			openMarkup = opener.markupWithContentSlotHolding(bodySource);
		}

		private BBCodeTag finished() {
			return node(Optional.ofNullable(opener.closeMarkup()));
		}

		private BBCodeTag finishedWithoutACloser() {
			return node(Optional.empty());
		}

		private BBCodeTag node(Optional<String> closeMarkup) {
			if (openedByAnImplicitMarker())
				trimTheBlankEdgesTheContainerDiscards(children);
			return new BBCodeTag(opener.config(), openMarkup, closeMarkup, opener.contentLevel(),
					authoredSource, opener.parsedAttributes(), opener.attributeMode(),
					isAwaitingItsAuthorsCloser && !openedByAnImplicitMarker(), openedByAnImplicitMarker(), children);
		}
	}

	private static void trimTheBlankEdgesTheContainerDiscards(List<BBCodeNode> children) {
		boolean removedAChild = true;
		while (removedAChild && !children.isEmpty())
			removedAChild = trimLeadingBlankEdge(children);
		removedAChild = true;
		while (removedAChild && !children.isEmpty())
			removedAChild = trimTrailingBlankEdge(children);
	}

	private static boolean trimLeadingBlankEdge(List<BBCodeNode> children) {
		if (!(children.get(0) instanceof BBCodeText text))
			return false;
		String kept = BBCodeText.withoutLeadingBlankRun(text.sourceText());
		if (kept.equals(text.sourceText()))
			return false;
		if (kept.isEmpty())
			children.remove(0);
		else
			children.set(0, new BBCodeText(kept, text.escaping()));
		return kept.isEmpty();
	}

	private static boolean trimTrailingBlankEdge(List<BBCodeNode> children) {
		int lastIndex = children.size() - 1;
		if (!(children.get(lastIndex) instanceof BBCodeText text))
			return false;
		String kept = BBCodeText.withoutTrailingBlankRun(text.sourceText());
		if (kept.equals(text.sourceText()))
			return false;
		if (kept.isEmpty())
			children.remove(lastIndex);
		else
			children.set(lastIndex, new BBCodeText(kept, text.escaping()));
		return kept.isEmpty();
	}

}
