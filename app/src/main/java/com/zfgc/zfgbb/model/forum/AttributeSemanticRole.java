package com.zfgc.zfgbb.model.forum;

import java.util.Optional;

import com.zfgc.zfgbb.lookup.EnumeratedCode;

public enum AttributeSemanticRole {
	DESTINATION,
	LIST_STYLE,
	WIDTH,
	HEIGHT;

	public static Optional<AttributeSemanticRole> forCode(String code) {
		return EnumeratedCode.matchingExactly(AttributeSemanticRole.class, code);
	}
}
