package com.zfgc.zfgbb.content.renderer;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class MarkdownRenderer {

	private final Parser parser = Parser.builder().build();
	private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
	private final BBCodeOutputSanitizer outputSanitizer;

	public MarkdownRenderer(BBCodeOutputSanitizer outputSanitizer) {
		this.outputSanitizer = outputSanitizer;
	}

	public String render(String markdown) {
		if (markdown == null || markdown.isBlank()) {
			return "";
		}
		return outputSanitizer.sanitize(htmlRenderer.render(parser.parse(markdown)));
	}
}
