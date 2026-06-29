package com.zfgc.zfgbb.content.renderer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.wiki.WikiTitle;
import com.zfgc.zfgbb.services.cms.WikiNamespaceRegistry;

@Service
public class ContentTemplateService {

	public record Template(String source, String body, ContentScope scope) {
	}

	public record Lookup(Template template, boolean defined) {
	}

	@Autowired
	private ContentTemplateDboMapper templateMapper;

	@Autowired
	private WikiDataProvider wikiDataProvider;

	@Autowired
	private WikiNamespaceRegistry namespaceRegistry;

	public boolean isDefined(String code) {
		return lookup(code, ContentFormat.BBCODE, ContentScope.WIKI).defined();
	}

	public Template find(String code, ContentFormat format, ContentScope context) {
		return lookup(code, format, context).template();
	}

	public Lookup lookup(String code, ContentFormat format, ContentScope context) {
		String path = code.regionMatches(true, 0, "Template:", 0, 9) ? code : "Template:" + code;
		WikiTitle title = namespaceRegistry == null ? null : namespaceRegistry.resolve(path);
		if (title == null) title = WikiTitle.parse(path);
		var page = wikiDataProvider == null ? null : wikiDataProvider.findPage(title.path());
		List<ContentTemplateDbo> variants = page == null ? rows(code) : wikiRows(page.getWikiPageId());
		if (page != null && variants.isEmpty()) {
			if (namespaceRegistry != null && namespaceRegistry.isSyntheticSystemPage(
					page.getWikiPageId(), normalizeCode(code)))
				variants = rows(code);
			else
				return new Lookup(null, true);
		}
		final List<ContentTemplateDbo> resolvedVariants = variants;
		boolean defined = !resolvedVariants.isEmpty();
		ContentTemplateDbo match = resolvedVariants.stream()
				.filter(row -> format.name().equals(row.getContentFormat()))
				.findFirst()
				.orElseGet(() -> resolvedVariants.stream()
						.filter(row -> ContentFormat.BBCODE.name().equals(row.getContentFormat()))
						.findFirst().orElse(null));
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
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andCodeEqualTo(normalizeCode(code)).andWikiPageIdIsNull();
		return templateMapper.selectByExample(ex);
	}

	private List<ContentTemplateDbo> wikiRows(Integer wikiPageId) {
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId);
		return templateMapper.selectByExample(ex);
	}

	private String normalizeCode(String code) {
		String normalized = code.trim();
		if (normalized.regionMatches(true, 0, "Template:", 0, "Template:".length()))
			normalized = normalized.substring("Template:".length());
		WikiTitle.CaseMode mode = namespaceRegistry == null ? null : namespaceRegistry.caseMode("Template");
		return WikiTitle.normalizeTitle(normalized, mode == null ? WikiTitle.CaseMode.FIRST_LETTER : mode);
	}

	private static ContentScope parseScope(String value) {
		try {
			return ContentScope.valueOf(value);
		} catch (IllegalArgumentException | NullPointerException e) {
			return ContentScope.ALL;
		}
	}
}
