package com.zfgc.zfgbb.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.zfgc.zfgbb.lookup.EnumeratedCode;

public enum ContentFormat {
    BBCODE,
    MARKDOWN;

    public static Optional<ContentFormat> parse(String code) {
        return EnumeratedCode.matchingIgnoringCaseAndSurroundingSpace(ContentFormat.class, code);
    }

    public static List<String> authorableCodes() {
        return Arrays.stream(values()).map(ContentFormat::name).toList();
    }
}
