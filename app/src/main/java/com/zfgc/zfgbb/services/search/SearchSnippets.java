package com.zfgc.zfgbb.services.search;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.mappers.custom.SearchMatchRow;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchSnippets {

	private static final int SNIPPET_RADIUS = 80;

	private final ContentRenderingService contentRenderingService;

	public Optional<String> renderedSnippet(SearchMatchRow row, String term, ContentScope scope) {
		return Optional.ofNullable(row.getBody())
				.map(body -> contentRenderingService.plainText(body,
						ContentFormat.parse(row.getContentFormat()).orElse(ContentFormat.BBCODE), scope))
				.flatMap(visible -> snippetWindow(visible, term));
	}

	public Optional<String> storedSnippet(SearchMatchRow row, String term) {
		return Optional.ofNullable(row.getBody()).flatMap(summary -> snippetWindow(summary, term));
	}

	private static Optional<String> snippetWindow(String text, String term) {
		String collapsed = text.replaceAll("\\s+", " ").trim();
		if (collapsed.isEmpty())
			return Optional.empty();
		int matchIndex = collapsed.toLowerCase().indexOf(term.toLowerCase());
		if (matchIndex < 0)
			return Optional.of(collapsed.length() > SNIPPET_RADIUS * 2
					? collapsed.substring(0, SNIPPET_RADIUS * 2) + "…"
					: collapsed);
		int start = Math.max(0, matchIndex - SNIPPET_RADIUS);
		int end = Math.min(collapsed.length(), matchIndex + term.length() + SNIPPET_RADIUS);
		return Optional.of((start > 0 ? "…" : "") + collapsed.substring(start, end)
				+ (end < collapsed.length() ? "…" : ""));
	}
}
