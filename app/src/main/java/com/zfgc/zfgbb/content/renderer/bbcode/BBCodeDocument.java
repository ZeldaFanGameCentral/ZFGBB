package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BBCodeDocument implements BBCodeNode {

	private final List<BBCodeNode> children = new ArrayList<>();

	private final List<AuthoredOpener> openersThatMatchedNoMode = new ArrayList<>();

	private final List<AuthoredCloser> closersNoOpenerInThisSourceMatched = new ArrayList<>();

	@Override
	public List<BBCodeNode> children() {
		return children;
	}

	public List<AuthoredOpener> openersThatMatchedNoMode() {
		return List.copyOf(openersThatMatchedNoMode);
	}

	void recordAnOpenerWhoseShapeMatchedNoMode(AuthoredOpener opener) {
		openersThatMatchedNoMode.add(opener);
	}

	public Optional<AuthoredCloser> firstUnmatchedCloser(String code) {
		for (AuthoredCloser closer : closersNoOpenerInThisSourceMatched)
			if (closer.code().equalsIgnoreCase(code))
				return Optional.of(closer);
		return Optional.empty();
	}

	void recordACloserNoOpenerInThisSourceMatched(AuthoredCloser closer) {
		closersNoOpenerInThisSourceMatched.add(closer);
	}
}
