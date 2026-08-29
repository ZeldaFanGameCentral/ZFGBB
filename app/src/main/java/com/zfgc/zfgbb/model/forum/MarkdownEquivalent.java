package com.zfgc.zfgbb.model.forum;

import java.util.Optional;
import java.util.Set;

import com.zfgc.zfgbb.lookup.EnumeratedCode;

public enum MarkdownEquivalent {
	STRONG_EMPHASIS,
	EMPHASIS,
	HEADING,
	THEMATIC_BREAK,
	LINK,
	IMAGE,
	BLOCK_QUOTE,
	FENCED_CODE,
	LIST,
	INLINE_CODE;

	private static final Set<MarkdownEquivalent> CONSTRUCTS_MARKDOWN_READS_INSIDE_A_PARAGRAPH =
			Set.of(STRONG_EMPHASIS, EMPHASIS, LINK, IMAGE, INLINE_CODE);

	public boolean markdownReadsItInsideAParagraph() {
		return CONSTRUCTS_MARKDOWN_READS_INSIDE_A_PARAGRAPH.contains(this);
	}

	public static Optional<MarkdownEquivalent> forCode(String code) {
		return EnumeratedCode.matchingExactly(MarkdownEquivalent.class, code);
	}
}
