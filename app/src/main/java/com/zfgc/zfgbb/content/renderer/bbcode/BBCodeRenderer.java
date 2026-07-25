package com.zfgc.zfgbb.content.renderer.bbcode;

import static org.jsoup.nodes.Entities.escape;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.SourceReferenceService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateExpansion;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

@Component
public class BBCodeRenderer {

	private final BBCodeGrammarHolder grammarHolder;

	private final SourceReferenceService sourceReferenceService;

	private final TemplateExpansion templateExpansion;

	public BBCodeRenderer(BBCodeGrammarHolder grammarHolder, SourceReferenceService sourceReferenceService,
			TemplateExpansion templateExpansion) {
		this.grammarHolder = grammarHolder;
		this.sourceReferenceService = sourceReferenceService;
		this.templateExpansion = templateExpansion;
	}

	public String render(String source, OffsetDateTime quotingCreatedTs) {
		return render(source, quotingCreatedTs, ContentScope.ALL, Map.of());
	}

	public String render(String source, OffsetDateTime quotingCreatedTs, ContentScope scope,
			Map<String, String> context) {
		if (source == null)
			return "";
		String prepared = source.replace("\n", BBCodeText.LINE_BREAK_MARKUP);
		BBCodeDocument document = BBCodeParser.parse(prepared, grammarHolder.current().configs());
		templateExpansion.expandTree(document, prepared, ContentFormat.BBCODE, scope, context,
				this::theSubtreeParsedFrom);
		sourceReferenceService.resolveEverySourceReferenceIn(document, quotingCreatedTs);
		return BBCodeToHtml(document);
	}

	private List<BBCodeNode> theSubtreeParsedFrom(String expansion, boolean itsOwnInvocationsStillExpand) {
		Map<String, BBCodeConfig> grammar = grammarHolder.current().configs();
		if (!itsOwnInvocationsStillExpand) {
			grammar = new HashMap<>(grammar);
			grammar.remove(TemplateExpansion.TEMPLATE_INVOCATION_CODE);
		}
		return BBCodeParser.parse(expansion.replace("\n", BBCodeText.LINE_BREAK_MARKUP), grammar).children();
	}

	public String BBCodeToHtml(BBCodeNode root) {
		StringBuilder rendered = new StringBuilder();
		Deque<BBCodeNode> pending = new ArrayDeque<>();
		pending.push(root);
		while (!pending.isEmpty()) {
			BBCodeNode node = pending.pop();
			switch (node) {
				case BBCodeText text -> rendered.append(renderText(text));
				case BBCodeDocument document -> pushChildrenInRenderOrder(pending, document.children());
				case BBCodeTag tag -> {
					rendered.append(tag.openMarkup());
					tag.closeMarkup().ifPresent(markup -> pending.push(BBCodeText.passThroughText(markup)));
					pushChildrenInRenderOrder(pending, tag.children());
				}
			}
		}
		return rendered.toString();
	}

	private String renderText(BBCodeText text) {
		return switch (text.escaping()) {
			case PASS_THROUGH -> text.sourceText();
			case VERBATIM_LITERAL -> escape(BBCodeText.authoredLineBreaksAsNewlines(text.sourceText()));
		};
	}

	private static void pushChildrenInRenderOrder(Deque<BBCodeNode> pending, List<BBCodeNode> children) {
		for (int child = children.size() - 1; child >= 0; child--)
			pending.push(children.get(child));
	}
}
