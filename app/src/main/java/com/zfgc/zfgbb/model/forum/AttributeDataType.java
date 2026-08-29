package com.zfgc.zfgbb.model.forum;

import java.util.Optional;

import com.zfgc.zfgbb.lookup.EnumeratedCode;

public enum AttributeDataType {
	TIMESTAMP,
	TEXT,
	COLOR,
	INTEGER,
	URL,
	IDENTIFIER,
	FONT_NAME,
	LIST_TYPE,
	DIMENSION,
	SIZE,
	ALIGNMENT;

	public static Optional<AttributeDataType> forCode(String code) {
		return EnumeratedCode.matchingExactly(AttributeDataType.class, code);
	}

	public static String knownCodes() {
		return EnumeratedCode.everyCodeOf(AttributeDataType.class);
	}
}
