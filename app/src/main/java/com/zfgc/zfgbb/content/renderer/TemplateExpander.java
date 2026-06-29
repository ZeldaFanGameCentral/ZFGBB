package com.zfgc.zfgbb.content.renderer;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.model.cms.WikiPage;

@Component
public class TemplateExpander {

	private static final int MAX_DEPTH = 3;
	private static final int MAX_CACHED_TEMPLATES = 256;
	private static final Pattern BLOCK = Pattern.compile(
			"\\[template=([^\\]\\n]+)\\]((?:(?!\\[template=|\\[/template\\]).)*)\\[/template\\]", Pattern.DOTALL);
	private static final Pattern PARAM = Pattern.compile("\\{([A-Za-z0-9_]+)\\}");
	private static final Pattern FILE_REF = Pattern.compile("\\[\\[(?:File|Image):([^\\]|]+)(?:\\|[^\\]]*)?\\]\\]", Pattern.CASE_INSENSITIVE);
	private static final DateTimeFormatter TEMPLATE_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d, yyyy",
			Locale.ENGLISH);

	private final Logger logger = LoggerFactory.getLogger(TemplateExpander.class);

	private final ContentResourceDboMapper contentMapper;
	private final ContentTemplateService templates;
	private final TemplateDataFetcher fetcher;
	private final WikiDataProvider wikiPages;

	private final Map<String, Template> compiled = new ConcurrentHashMap<>();

	public TemplateExpander(ContentResourceDboMapper contentMapper, ContentTemplateService templates,
			TemplateDataFetcher fetcher, WikiDataProvider wikiPages) {
		this.contentMapper = contentMapper;
		this.templates = templates;
		this.fetcher = fetcher;
		this.wikiPages = wikiPages;
	}

	public String expand(String source, ContentFormat target, ContentScope scope) {
		return expand(source, target, scope, Map.of());
	}

	public String expand(String source, ContentFormat target, ContentScope scope, Map<String, String> context) {
		if (source == null) {
			return null;
		}
		return resolveFileRefs(expandText(source, target, scope, context, 0), target);
	}

	private String expandText(String text, ContentFormat target, ContentScope scope, Map<String, String> context,
			int depth) {
		if (depth >= MAX_DEPTH || !text.contains("[template=")) {
			return text;
		}
		Matcher blockMatcher = BLOCK.matcher(text);
		StringBuilder out = new StringBuilder();
		while (blockMatcher.find()) {
			String name = blockMatcher.group(1).trim();
			Map<String, String> params = parseParams(blockMatcher.group(2));
			context.forEach(params::putIfAbsent);
			String replacement;
			ContentTemplateService.Lookup lookup = templates.lookup(name, target, scope);
			ContentTemplateService.Template def = lookup.template();
			if (def != null && (def.source() == null || def.source().isBlank())) {
				replacement = renderMarkupTemplate(name, def, params, target, scope, context, depth);
			} else if (def != null) {
				replacement = renderTemplate(name, def, params, target, scope, depth);
			} else if (lookup.defined()) {
				replacement = "";
			} else {
				String transcluded = transcludeWikiPage(name, scope);
				replacement = transcluded != null
						? expandText(transcluded, target, scope, context, depth + 1)
						: "{{" + name + "}}";
			}
			blockMatcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		blockMatcher.appendTail(out);
		return out.toString();
	}

	private String transcludeWikiPage(String name, ContentScope scope) {
		if (scope != ContentScope.WIKI)
			return null;
		return wikiPages.getWikiPageQuietly(name).map(WikiPage::getContent).filter(c -> !c.isBlank()).orElse(null);
	}

	private static final Mustache.Lambda FORMAT_DATE = (frag, out) -> {
		String value = frag.execute().trim();
		try {
			OffsetDateTime parsed = OffsetDateTime.parse(value);
			out.write("<time datetime=\"" + escapeAttribute(value) + "\">"
					+ TEMPLATE_DATE_FORMAT.format(parsed) + "</time>");
		} catch (RuntimeException e) {
			out.write(escapeAttribute(value));
		}
	};

	private String renderMarkupTemplate(String name, ContentTemplateService.Template def, Map<String, String> params,
			ContentFormat target, ContentScope scope, Map<String, String> context, int depth) {
		Map<String, Object> data = new HashMap<>(params);
		data.put("formatDate", FORMAT_DATE);
		return expandText(execute(name, def.body(), data), target, scope, context, depth + 1);
	}

	private String renderTemplate(String name, ContentTemplateService.Template def, Map<String, String> params,
			ContentFormat target, ContentScope scope, int depth) {
		Map<String, Object> data = new HashMap<>();
		if (def.source() != null && !def.source().isBlank()) {
			Object fetched = fetcher.fetch(buildSourcePath(def.source(), params));
			if (fetched instanceof Map<?, ?> map) {
				map.forEach((k, v) -> data.put(String.valueOf(k), v));
			} else if (fetched != null) {
				data.put("data", fetched);
			}
		}
		data.putAll(params);
		data.put("formatDate", FORMAT_DATE);
		data.put("template", (Mustache.Lambda) (frag, out) -> {
			String block = frag.execute();
			int nameEnd = block.indexOf('\n');
			String nestedName = (nameEnd < 0 ? block : block.substring(0, nameEnd)).trim();
			ContentTemplateService.Template nestedDef = templates.find(nestedName, target, scope);
			if (nestedDef == null || depth + 1 >= MAX_DEPTH) {
				return;
			}
			Map<String, String> nestedParams = nameEnd < 0 ? new HashMap<>()
					: parseParams(block.substring(nameEnd + 1));
			out.write(renderTemplate(nestedName, nestedDef, nestedParams, target, scope, depth + 1));
		});
		return execute(name, def.body(), data);
	}

	private String execute(String name, String body, Map<String, Object> data) {
		try {
			if (compiled.size() >= MAX_CACHED_TEMPLATES) {
				compiled.clear();
			}
			return compiled.computeIfAbsent(body, this::compile).execute(data);
		} catch (RuntimeException e) {
			logger.warn("content_template '{}' failed to render: {}", name, e.toString());
			return "";
		}
	}

	private Template compile(String body) {
		return Mustache.compiler().escapeHTML(false).defaultValue("").emptyStringIsFalse(true).compile(body);
	}

	private static String escapeAttribute(String value) {
		return value.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;");
	}

	private String buildSourcePath(String source, Map<String, String> params) {
		Matcher paramMatcher = PARAM.matcher(source);
		StringBuilder out = new StringBuilder();
		while (paramMatcher.find()) {
			String key = paramMatcher.group(1).trim();
			String value = params.containsKey(key) ? params.get(key) : params.getOrDefault("_" + key, "");
			paramMatcher.appendReplacement(out, Matcher.quoteReplacement(URLEncoder.encode(value, StandardCharsets.UTF_8)));
		}
		paramMatcher.appendTail(out);
		return out.toString();
	}

	private static Map<String, String> parseParams(String body) {
		Map<String, String> params = new HashMap<>();
		for (String line : body.split("\n")) {
			int eqIdx = line.indexOf('=');
			if (eqIdx > 0) {
				params.put(line.substring(0, eqIdx).trim(), line.substring(eqIdx + 1).trim());
			}
		}
		return params;
	}

	private String resolveFileRefs(String input, ContentFormat target) {
		if (!input.contains("[[")) {
			return input;
		}
		Set<String> names = new HashSet<>();
		Matcher scan = FILE_REF.matcher(input);
		while (scan.find()) {
			String name = normalizeFileName(scan.group(1));
			if (!name.isEmpty()) {
				names.add(name);
			}
		}
		Map<String, Integer> imageIds = findImages(names);

		Matcher fileRefMatcher = FILE_REF.matcher(input);
		StringBuilder out = new StringBuilder();
		while (fileRefMatcher.find()) {
			String name = normalizeFileName(fileRefMatcher.group(1));
			Integer contentId = imageIds.get(name);
			String replacement;
			if (contentId == null) {
				replacement = name.replace('_', ' ');
			} else if (target == ContentFormat.MARKDOWN) {
				replacement = "![](/content/" + contentId + ")";
			} else {
				replacement = "[img]/content/" + contentId + "[/img]";
			}
			fileRefMatcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		fileRefMatcher.appendTail(out);
		return out.toString();
	}

	private static String normalizeFileName(String raw) {
		String name = raw.trim().replace(' ', '_');
		if (!name.isEmpty()) {
			name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		}
		return name;
	}

	private Map<String, Integer> findImages(Set<String> filenames) {
		Map<String, Integer> result = new HashMap<>();
		if (filenames.isEmpty()) {
			return result;
		}
		ContentResourceDboExample ex = new ContentResourceDboExample();
		ex.createCriteria().andFilenameIn(new ArrayList<>(filenames));
		for (ContentResourceDbo dbo : contentMapper.selectByExample(ex)) {
			result.putIfAbsent(dbo.getFilename(), dbo.getContentResourceId());
		}
		return result;
	}
}
