package com.zfgc.zfgbb.services.cms.catalog;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public record CatalogListing(int page, int pageSize) {

	public static final int SHOWCASE_RECENT = 12;
	public static final int SHOWCASE_RAIL = 4;

	private static final int DEFAULT_PAGE_SIZE = 12;
	private static final int LARGEST_PAGE_A_CALLER_MAY_ASK_FOR = 60;

	public static CatalogListing of(Integer page, Integer pageSize) {
		return new CatalogListing(
				page == null || page < 1 ? 1 : page,
				pageSize == null || pageSize < 1 || pageSize > LARGEST_PAGE_A_CALLER_MAY_ASK_FOR
						? DEFAULT_PAGE_SIZE
						: pageSize);
	}

	public static String blankToNull(String value) {
		return value == null || value.isBlank() ? null : value;
	}

	public static <T> List<T> rail(BiFunction<String, Integer, List<T>> fetch, Function<T, String> slug,
			String sort, int limit, String excludeSlug) {
		return fetch.apply(sort, limit + 1).stream()
				.filter(item -> excludeSlug == null || !slug.apply(item).equals(excludeSlug))
				.limit(limit).toList();
	}
}
