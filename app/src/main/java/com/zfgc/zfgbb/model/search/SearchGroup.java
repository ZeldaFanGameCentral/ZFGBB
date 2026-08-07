package com.zfgc.zfgbb.model.search;

import java.util.List;

public record SearchGroup(String type, String label, int total, List<SearchHit> hits) {
}
