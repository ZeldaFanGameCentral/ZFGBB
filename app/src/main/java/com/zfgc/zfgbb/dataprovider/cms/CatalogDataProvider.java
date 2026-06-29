package com.zfgc.zfgbb.dataprovider.cms;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.model.cms.PagedResult;

public abstract class CatalogDataProvider {

	@Autowired
	protected UserDboMapper userMapper;

	@Autowired
	protected ContentResourceDboMapper contentResourceMapper;

	protected Map<Integer, String> displayNames(Stream<Integer> userIds) {
		Map<Integer, String> names = new HashMap<>();
		List<Integer> ids = userIds.filter(Objects::nonNull).distinct().toList();
		if (ids.isEmpty()) {
			return names;
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserIdIn(ids);
		userMapper.selectByExample(ex).forEach(user -> names.put(user.getUserId(), user.getDisplayName()));
		return names;
	}

	protected String contentFilename(Integer contentResourceId) {
		if (contentResourceId == null) {
			return null;
		}
		ContentResourceDbo content = contentResourceMapper.selectByPrimaryKey(contentResourceId);
		return content == null ? null : content.getFilename();
	}

	static Predicate<String> valueFilter(String spec) {
		if (spec == null || spec.isBlank()) {
			return value -> true;
		}
		Set<String> includes = new HashSet<>();
		Set<String> excludes = new HashSet<>();
		for (String token : spec.split(",")) {
			String trimmed = token.trim();
			if (trimmed.isEmpty()) {
				continue;
			}
			if (trimmed.startsWith("-")) {
				excludes.add(trimmed.substring(1));
			} else {
				includes.add(trimmed);
			}
		}
		return value -> (includes.isEmpty() || includes.contains(value)) && !excludes.contains(value);
	}

	static String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}

	static boolean containsIgnoreCase(String haystack, String needle) {
		return haystack != null && haystack.toLowerCase().contains(needle.trim().toLowerCase());
	}

	static <T> Stream<T> pageSlice(List<T> items, int page, int pageSize) {
		return items.stream().skip((long) (page - 1) * pageSize).limit(pageSize);
	}

	static <T> List<Map.Entry<String, Long>> countDistinct(List<T> rows, Function<T, String> field) {
		return rows.stream()
				.map(row -> trimToNull(field.apply(row)))
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(value -> value, TreeMap::new, Collectors.counting()))
				.entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.toList();
	}

	protected <D, M> PagedResult<M> catalogPage(
			List<D> rows,
			Function<D, Integer> createdUserId,
			BiPredicate<D, Map<Integer, String>> filter,
			Comparator<D> comparator,
			String sort,
			int page, int pageSize,
			BiFunction<D, Map<Integer, String>, M> toModel) {
		Map<Integer, String> liveNames = displayNames(rows.stream().map(createdUserId));
		List<D> filtered = rows.stream()
				.filter(row -> filter.test(row, liveNames))
				.collect(Collectors.toCollection(ArrayList::new));
		if ("random".equals(sort)) {
			Collections.shuffle(filtered);
		} else {
			filtered.sort(comparator);
		}
		List<M> items = pageSlice(filtered, page, pageSize)
				.map(row -> toModel.apply(row, liveNames))
				.toList();
		return new PagedResult<>(items, filtered.size(), page, pageSize);
	}

	protected static <D> Comparator<D> catalogComparator(
			String sort,
			Function<D, String> title,
			Function<D, OffsetDateTime> published,
			Function<D, OffsetDateTime> lastUpdated,
			Function<D, Integer> views,
			Function<D, Integer> downloads,
			Function<D, Float> rating,
			Function<D, Integer> voteCount) {
		Comparator<D> byTitle = Comparator.comparing(row -> {
			String value = title.apply(row);
			return value == null ? "" : value.toLowerCase();
		});
		return switch (sort == null ? "" : sort) {
			case "newest" -> nullsLastDesc(published).thenComparing(byTitle);
			case "updated" -> nullsLastDesc(lastUpdated).thenComparing(byTitle);
			case "views" -> nullsLastDesc(views).thenComparing(byTitle);
			case "downloads" -> nullsLastDesc(downloads).thenComparing(byTitle);
			case "rating" -> nullsLastDesc(rating).thenComparing(nullsLastDesc(voteCount)).thenComparing(byTitle);
			default -> byTitle;
		};
	}

	private static <D, C extends Comparable<? super C>> Comparator<D> nullsLastDesc(Function<D, C> key) {
		return Comparator.comparing(key, Comparator.nullsLast(Comparator.reverseOrder()));
	}
}
