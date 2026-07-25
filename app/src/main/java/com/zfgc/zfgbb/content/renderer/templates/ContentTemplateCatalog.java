package com.zfgc.zfgbb.content.renderer.templates;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.wiki.WikiTitle;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentTemplateCatalog {

	public record Template(String source, String body, ContentScope scope) {
	}

	public record Lookup(Template template, boolean defined) {
	}

	private final ContentTemplateDboMapper templateMapper;

	private final WikiDataProvider wikiDataProvider;

	private final WikiNamespaceDataProvider namespaceData;

	public Template find(String code, ContentFormat format, ContentScope context) {
		return lookup(code, format, context).template();
	}

	public Lookup lookup(String code, ContentFormat format, ContentScope context) {
		String prefix = namespaceData.templateNamespace() + ":";
		String path = code.regionMatches(true, 0, prefix, 0, prefix.length()) ? code : prefix + code;
		WikiTitle title = namespaceData.resolve(path);
		var page = wikiDataProvider.findPage(title.path());
		List<ContentTemplateDbo> variants = page == null ? rows(code) : wikiRows(page.getWikiPageId());
		if (page != null && variants.isEmpty()) {
			variants = rows(code);
			if (variants.isEmpty())
				return new Lookup(null, true);
		}
		final List<ContentTemplateDbo> resolvedVariants = variants;
		boolean defined = !resolvedVariants.isEmpty();
		ContentTemplateDbo match = resolvedVariants.stream()
				.filter(row -> format.name().equals(row.getContentFormat()))
				.findFirst()
				.or(() -> resolvedVariants.stream()
						.filter(row -> ContentFormat.BBCODE.name().equals(row.getContentFormat()))
						.findFirst())
				.or(resolvedVariants.stream()::findFirst)
				.orElse(null);
		if (match == null) {
			return new Lookup(null, defined);
		}
		ContentScope scope = parseScope(match.getScope());
		if (scope != ContentScope.ALL && scope != context) {
			return new Lookup(null, defined);
		}
		return new Lookup(new Template(match.getSource(), match.getBody(), scope), defined);
	}

	private List<ContentTemplateDbo> rows(String code) {
		String normalized = normalizeCode(code);
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andCodeEqualTo(normalized).andWikiPageIdIsNull();
		List<ContentTemplateDbo> exact = templateMapper.selectByExample(ex);
		if (!exact.isEmpty()) {
			return exact;
		}

		ContentTemplateDboExample compatibility = new ContentTemplateDboExample();
		compatibility.createCriteria().andWikiPageIdIsNull();
		List<ContentTemplateDbo> legacyCaseMatches = templateMapper.selectByExample(compatibility).stream()
				.filter(row -> row.getCode().equalsIgnoreCase(normalized))
				.toList();
		long identities = legacyCaseMatches.stream().map(ContentTemplateDbo::getCode).distinct().count();
		return identities == 1 ? legacyCaseMatches : List.of();
	}

	private List<ContentTemplateDbo> wikiRows(Integer wikiPageId) {
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId);
		return templateMapper.selectByExample(ex);
	}

	private String normalizeCode(String code) {
		String normalized = code.trim();
		String templateNamespace = namespaceData.templateNamespace();
		String prefix = templateNamespace + ":";
		if (normalized.regionMatches(true, 0, prefix, 0, prefix.length()))
			normalized = normalized.substring(prefix.length());
		return WikiTitle.normalizeTitle(normalized, namespaceData.caseMode(templateNamespace));
	}

	private static ContentScope parseScope(String value) {
		try {
			return ContentScope.valueOf(value);
		} catch (IllegalArgumentException | NullPointerException e) {
			return ContentScope.ALL;
		}
	}
}
