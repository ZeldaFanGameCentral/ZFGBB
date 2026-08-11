package com.zfgc.zfgbb.model.search;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record SearchHit(String type, String title, Optional<String> snippet, Optional<String> context, String url) {
}
