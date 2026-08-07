package com.zfgc.zfgbb.content.renderer.templates;

import lombok.RequiredArgsConstructor;

import static org.jsoup.nodes.Entities.escape;

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
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.model.cms.WikiPage;

@Component
@RequiredArgsConstructor
public class TemplateExpander {

	private static final int MAX_CACHED_TEMPLATES = 256;
	private static final String TEMPLATE_INVOCATION_CODE = TemplateExpansion.TEMPLATE_INVOCATION_CODE;
	private static final Mustache.Compiler SOURCE_PATH_COMPILER =
			Mustache.compiler().withDelims("{ }").escapeHTML(false).defaultValue("");
	private static final Pattern FILE_REF = Pattern.compile("\\[\\[(?:File|Image):([^\\]|]+)(?:\\|[^\\]]*)?\\]\\]", Pattern.CASE_INSENSITIVE);
	private static final DateTimeFormatter TEMPLATE_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d, yyyy",
			Locale.ENGLISH);

	private final Logger logger = LoggerFactory.getLogger(TemplateExpander.class);

	private final ContentResourceDao contentResourceDao;
	private final ContentTemplateCatalog templates;
	private final TemplateDataFetcher fetcher;
	private final WikiDataProvider wikiPages;
	private final BBCodeGrammarHolder grammarHolder;

	private final Map<String, Template> compiled = new ConcurrentHashMap<>();




	public boolean isATemplateTag(BBCodeTag tag) {
		return TEMPLATE_INVOCATION_CODE.equalsIgnoreCase(tag.config().getCode());
	}

	public boolean isATemplateInvocation(BBCodeTag tag) {
		return TEMPLATE_INVOCATION_CODE.equalsIgnoreCase(tag.config().getCode())
				&& tag.authoredSource().itsAuthorWroteACloser();
	}

	public record Expansion(String text, boolean itsOwnInvocationsStillExpand) {}

	public Expansion theExpansionOf(String name, Map<String, String> params, ContentFormat target,
			ContentScope scope, int depth) {
		ContentTemplateCatalog.Lookup lookup = templates.lookup(name, target, scope);
		ContentTemplateCatalog.Template def = lookup.template();
		if (def != null && (def.source() == null || def.source().isBlank()))
			return new Expansion(execute(name, def.body(), markupTemplateData(params)), true);
		if (def != null)
			return new Expansion(renderTemplate(name, def, params, target, scope, depth), false);
		if (lookup.defined())
			return new Expansion("", false);
		String transcluded = transcludeWikiPage(name, scope);
		return transcluded != null
				? new Expansion(transcluded, true)
				: new Expansion("{{" + name + "}}", false);
	}

	public Map<String, String> theParametersWrittenIn(String body) {
		return parseParams(body);
	}

	public String theNameInvokedBy(BBCodeTag invocation) {
		return invocation.parsedAttributes().attributeValues()
				.getOrDefault(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME, "").trim();
	}

	private Map<String, Object> markupTemplateData(Map<String, String> params) {
		Map<String, Object> data = new HashMap<>(params);
		data.put("formatDate", FORMAT_DATE);
		return data;
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
			out.write("<time datetime=\"" + escape(value) + "\">"
					+ TEMPLATE_DATE_FORMAT.format(parsed) + "</time>");
		} catch (RuntimeException e) {
			out.write(escape(value));
		}
	};


	private String renderTemplate(String name, ContentTemplateCatalog.Template def, Map<String, String> params,
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
			ContentTemplateCatalog.Template nestedDef = templates.find(nestedName, target, scope);
			if (nestedDef == null || depth + 1 >= TemplateExpansion.MAX_DEPTH) {
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

	private String buildSourcePath(String source, Map<String, String> params) {
		Mustache.CustomContext urlEncodedParams = key -> {
			String value = params.containsKey(key) ? params.get(key) : params.getOrDefault("_" + key, "");
			return URLEncoder.encode(value, StandardCharsets.UTF_8);
		};
		try {
			return SOURCE_PATH_COMPILER.compile(source).execute(urlEncodedParams);
		} catch (RuntimeException unparseablePath) {
			logger.warn("template source path '{}' failed to substitute: {}", source, unparseablePath.toString());
			return source;
		}
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

	public String theFileReferencesResolvedIn(String input, ContentFormat target) {
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
		for (ContentResourceDbo dbo : contentResourceDao.get(ex)) {
			result.putIfAbsent(dbo.getFilename(), dbo.getContentResourceId());
		}
		return result;
	}
}
