package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BBCodeDocument implements BBCodeNode {

	private final List<BBCodeNode> children = new ArrayList<>();

	private final List<AuthoredOpener> openersWhoseShapeMatchedNoMode = new ArrayList<>();

	private final List<AuthoredCloser> closersNoOpenerInThisSourceMatched = new ArrayList<>();

	@Override
	public List<BBCodeNode> children() {
		return children;
	}

	public List<AuthoredOpener> openersWhoseShapeMatchedNoMode() {
		return List.copyOf(openersWhoseShapeMatchedNoMode);
	}

	void recordAnOpenerWhoseShapeMatchedNoMode(AuthoredOpener opener) {
		openersWhoseShapeMatchedNoMode.add(opener);
	}

	public Optional<AuthoredCloser> theFirstCloserNoOpenerInThisSourceMatched(String code) {
		for (AuthoredCloser closer : closersNoOpenerInThisSourceMatched)
			if (closer.code().equalsIgnoreCase(code))
				return Optional.of(closer);
		return Optional.empty();
	}

	void recordACloserNoOpenerInThisSourceMatched(AuthoredCloser closer) {
		closersNoOpenerInThisSourceMatched.add(closer);
	}
}
