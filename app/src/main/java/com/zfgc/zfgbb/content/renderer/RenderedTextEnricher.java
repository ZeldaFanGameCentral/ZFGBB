package com.zfgc.zfgbb.content.renderer;

import static org.jsoup.nodes.Entities.escape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.EnumSet;
import java.util.regex.Pattern;


import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.SmileyDboExample;
import com.zfgc.zfgbb.dao.forum.SmileyDao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RenderedTextEnricher {

	public record SmileyToken(String code, String name, String label) {}

	private static final Pattern A_CLASS_THE_RENDERER_EMITS = Pattern.compile("^bb-[a-z0-9-]+$");
	private static final LinkExtractor LINK_EXTRACTOR = LinkExtractor.builder()
			.linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW))
			.build();
	private static final Set<String> AUTOLINK_SKIP_TAGS = Set.of(
			"a", "pre", "code", "tt", "iframe", "script", "style", "textarea");
	private static final String SMILEY_WRAPPER_CLASS = "bb-smiley";
	private static final String TELETYPE_WRAPPER_CLASS = "bb-code-tt";
	private static final Set<String> AUTOLINK_SKIP_CLASSES =
			Set.of(SMILEY_WRAPPER_CLASS, TELETYPE_WRAPPER_CLASS);

	private final SmileyDao smileyDao;

	private List<SmileyToken> smileys = List.of();

	private List<String> codesCommonmarkReadsAsBlockMarkers = List.of();

	@PostConstruct
	public void loadSmilies() {
		registerSmilies(smileyDao.get(new SmileyDboExample()).stream()
				.filter(dbo -> !Boolean.TRUE.equals(dbo.getHiddenFlag()))
				.map(dbo -> new SmileyToken(dbo.getCode(), dbo.getName(),
						dbo.getLabel() == null ? dbo.getName() : dbo.getLabel()))
				.toList());
	}

	public void registerSmilies(List<SmileyToken> tokens) {
		List<SmileyToken> sorted = new ArrayList<>(tokens);
		sorted.sort((left, right) -> right.code().length() - left.code().length());
		smileys = List.copyOf(sorted);
		codesCommonmarkReadsAsBlockMarkers = smileys.stream()
				.map(SmileyToken::code)
				.filter(RenderedTextEnricher::readsAsAMarkdownBlockMarker)
				.toList();
	}

	public List<String> smileyCodesCommonmarkReadsAsBlockMarkers() {
		return codesCommonmarkReadsAsBlockMarkers;
	}

	static boolean readsAsAMarkdownBlockMarker(String code) {
		if (code.startsWith(">"))
			return true;
		int afterLeadingDigits = 0;
		while (afterLeadingDigits < code.length() && isAsciiDigit(code.charAt(afterLeadingDigits)))
			afterLeadingDigits++;
		if (afterLeadingDigits == 0 || afterLeadingDigits >= code.length())
			return false;
		char afterDigits = code.charAt(afterLeadingDigits);
		return afterDigits == ')' || afterDigits == '.';
	}

	private static boolean isAsciiDigit(char candidate) {
		return candidate >= '0' && candidate <= '9';
	}

	public static boolean isAClassTheRendererCanEmit(String token) {
		return A_CLASS_THE_RENDERER_EMITS.matcher(token).matches();
	}

	public void enrichEveryTextNodeIn(Element body) {
		autolink(body);
		applySmilies(body);
	}

	private void autolink(Element body) {
		for (TextNode target : collectTextNodes(body)) {
			linkifyTextNode(target);
		}
	}

	private void applySmilies(Element body) {
		if (smileys.isEmpty()) {
			return;
		}
		for (TextNode target : collectTextNodes(body)) {
			smilifyTextNode(target);
		}
	}

	private static List<TextNode> collectTextNodes(Element body) {
		List<TextNode> targets = new ArrayList<>();
		NodeTraversor.traverse(new NodeVisitor() {
			@Override
			public void head(Node node, int depth) {
				if (node instanceof TextNode textNode && !textNode.isBlank() && !hasSkippedAncestor(textNode)) {
					targets.add(textNode);
				}
			}

			@Override
			public void tail(Node node, int depth) {
			}
		}, body);
		return targets;
	}

	private void smilifyTextNode(TextNode node) {
		String text = node.getWholeText();
		StringBuilder html = null;
		int cursor = 0;
		int copiedUpTo = 0;
		while (cursor < text.length()) {
			SmileyToken match = smileyAt(text, cursor);
			if (match == null) {
				cursor++;
				continue;
			}
			if (html == null) {
				html = new StringBuilder();
			}
			html.append(escape(text.substring(copiedUpTo, cursor)))
					.append("<span class=\"").append(wrapperClassesFor(match))
					.append("\" title=\"").append(escape(match.label())).append("\">")
					.append(escape(match.code())).append("</span>");
			cursor += match.code().length();
			copiedUpTo = cursor;
		}
		if (html == null) {
			return;
		}
		html.append(escape(text.substring(copiedUpTo)));
		node.before(html.toString());
		node.remove();
	}

	private static String wrapperClassesFor(SmileyToken match) {
		String namedAfterTheSmiley = SMILEY_WRAPPER_CLASS + "-" + match.name();
		return isAClassTheRendererCanEmit(namedAfterTheSmiley)
				? SMILEY_WRAPPER_CLASS + " " + namedAfterTheSmiley
				: SMILEY_WRAPPER_CLASS;
	}

	private SmileyToken smileyAt(String text, int index) {
		if (index > 0 && Character.isLetterOrDigit(text.charAt(index - 1))) {
			return null;
		}
		for (SmileyToken token : smileys) {
			String code = token.code();
			if (!text.startsWith(code, index)) {
				continue;
			}
			int end = index + code.length();
			if (Character.isLetterOrDigit(code.charAt(code.length() - 1))
					&& end < text.length() && Character.isLetterOrDigit(text.charAt(end))) {
				continue;
			}
			return token;
		}
		return null;
	}

	private static boolean hasSkippedAncestor(Node node) {
		for (Node parent = node.parent(); parent != null; parent = parent.parent()) {
			if (!(parent instanceof Element element)) {
				continue;
			}
			if (AUTOLINK_SKIP_TAGS.contains(element.normalName())) {
				return true;
			}
			for (String skippedClass : AUTOLINK_SKIP_CLASSES)
				if (element.hasClass(skippedClass))
					return true;
		}
		return false;
	}

	private static void linkifyTextNode(TextNode node) {
		String text = node.getWholeText();
		StringBuilder html = null;
		int copiedUpTo = 0;
		for (LinkSpan span : LINK_EXTRACTOR.extractLinks(text)) {
			String url = text.substring(span.getBeginIndex(), span.getEndIndex());
			Optional<String> href = ContentOutputSanitizer.safeHrefFor(url);
			if (html == null)
				html = new StringBuilder();
			html.append(escape(text.substring(copiedUpTo, span.getBeginIndex())));
			if (href.isEmpty())
				html.append(escape(url));
			else
				html.append("<a href=\"").append(escape(href.get())).append("\">").append(escape(url))
						.append("</a>");
			copiedUpTo = span.getEndIndex();
		}
		if (html == null)
			return;
		html.append(escape(text.substring(copiedUpTo)));
		node.before(html.toString());
		node.remove();
	}
}
