package com.zfgc.zfgbb.services.cms;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class CatalogSupport {

	public static final int SHOWCASE_RECENT = 12;
	public static final int SHOWCASE_RAIL = 4;

	private CatalogSupport() {}

	public static String blankToNull(String value) {
		return value == null || value.isBlank() ? null : value;
	}

	public static int clampPage(Integer page) {
		return page == null || page < 1 ? 1 : page;
	}

	public static int clampPageSize(Integer pageSize) {
		return pageSize == null || pageSize < 1 || pageSize > 60 ? 12 : pageSize;
	}

	public static <T> List<T> rail(BiFunction<String, Integer, List<T>> fetch, Function<T, String> slug,
			String sort, int limit, String excludeSlug) {
		return fetch.apply(sort, limit + 1).stream()
				.filter(item -> excludeSlug == null || !slug.apply(item).equals(excludeSlug))
				.limit(limit).toList();
	}
}
