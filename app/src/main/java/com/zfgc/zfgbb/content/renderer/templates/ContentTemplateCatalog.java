package com.zfgc.zfgbb.content.renderer.templates;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.dao.cms.ContentTemplateDao;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.wiki.WikiTitle;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentTemplateCatalog {

	public record Template(String source, String body, ContentScope scope) {

		public boolean namesADataSource() {
			return source != null && !source.isBlank();
		}
	}

	public record Lookup(Template template, boolean defined) {
	}

	private final ContentTemplateDao contentTemplateDao;

	private final WikiDataProvider wikiDataProvider;

	private final WikiNamespaceDataProvider namespaceData;

	public Template find(String code, ContentFormat format, ContentScope scope) {
		return lookup(code, format, scope).template();
	}

	public Lookup lookup(String code, ContentFormat format, ContentScope scope) {
		if (scope == null || !scope.isAConcreteSurface())
			throw new IllegalArgumentException("render scope is not a surface: " + scope);
		String prefix = namespaceData.templateNamespace() + ":";
		String path = code.regionMatches(true, 0, prefix, 0, prefix.length()) ? code : prefix + code;
		WikiTitle title = namespaceData.resolve(path);
		var page = wikiDataProvider.findPage(title.path());
		List<ContentTemplateDbo> variants = page.isPresent()
				? wikiRows(page.get().getWikiPageId())
				: rows(title);
		if (page.isPresent() && variants.isEmpty()) {
			variants = rows(title);
			if (variants.isEmpty())
				return new Lookup(null, true);
		}
		final List<ContentTemplateDbo> resolvedVariants = variants;
		boolean defined = !resolvedVariants.isEmpty();
		Optional<ContentTemplateDbo> match = resolvedVariants.stream()
				.filter(row -> format.name().equals(row.getContentFormat()))
				.findFirst()
				.or(() -> resolvedVariants.stream()
						.filter(row -> ContentFormat.BBCODE.name().equals(row.getContentFormat()))
						.findFirst())
				.or(() -> resolvedVariants.stream().findFirst());
		if (match.isEmpty()) {
			return new Lookup(null, defined);
		}
		ContentScope templateScope = parseScope(match.get().getScope());
		if (templateScope != ContentScope.ALL && templateScope != scope) {
			return new Lookup(null, defined);
		}
		return new Lookup(new Template(match.get().getSource(), match.get().getBody(), templateScope), defined);
	}

	private List<ContentTemplateDbo> rows(WikiTitle title) {
		ContentTemplateDboExample exactCodeExample = new ContentTemplateDboExample();
		exactCodeExample.createCriteria().andCodeEqualTo(title.title()).andWikiPageIdIsNull();
		List<ContentTemplateDbo> exact = contentTemplateDao.get(exactCodeExample);
		if (!exact.isEmpty()) {
			return exact;
		}

		ContentTemplateDboExample anyCaseExample = new ContentTemplateDboExample();
		anyCaseExample.createCriteria().andCodeLike(title.title()).andWikiPageIdIsNull();
		List<ContentTemplateDbo> caseInsensitiveMatches = contentTemplateDao.get(anyCaseExample).stream()
				.filter(row -> row.getCode().equalsIgnoreCase(title.title()))
				.toList();
		long identities = caseInsensitiveMatches.stream().map(ContentTemplateDbo::getCode).distinct().count();
		return identities == 1 ? caseInsensitiveMatches : List.of();
	}

	private List<ContentTemplateDbo> wikiRows(Integer wikiPageId) {
		ContentTemplateDboExample wikiPageTemplatesExample = new ContentTemplateDboExample();
		wikiPageTemplatesExample.createCriteria().andWikiPageIdEqualTo(wikiPageId);
		return contentTemplateDao.get(wikiPageTemplatesExample);
	}

	private static ContentScope parseScope(String value) {
		if (value == null) {
			return ContentScope.ALL;
		}
		try {
			return ContentScope.valueOf(value);
		} catch (IllegalArgumentException notADeclaredScope) {
			return ContentScope.ALL;
		}
	}
}
