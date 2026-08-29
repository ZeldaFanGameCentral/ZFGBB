package com.zfgc.zfgbb.model.search;

import java.util.List;

public record SearchResults(String query, int total, List<SearchGroup> groups) {
}
