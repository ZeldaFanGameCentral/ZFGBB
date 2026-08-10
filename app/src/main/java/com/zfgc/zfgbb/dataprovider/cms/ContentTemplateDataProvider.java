package com.zfgc.zfgbb.dataprovider.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.dao.cms.ContentTemplateDao;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContentTemplateDataProvider {

	private static final String WIKI_SCOPE = "WIKI";

	private final ContentTemplateDao contentTemplateDao;

	public void publishWikiTemplate(String code, Integer wikiPageId, ContentFormat contentFormat, String body,
			String source, boolean directivePresent) {
		ContentTemplateDbo existing = findForPage(code, wikiPageId, contentFormat);
		if (existing == null) {
			ContentTemplateDbo row = new ContentTemplateDbo();
			row.setCode(code);
			row.setContentFormat(contentFormat.name());
			row.setScope(WIKI_SCOPE);
			row.setBody(body);
			row.setSource(source);
			row.setWikiPageId(wikiPageId);
			contentTemplateDao.insert(row);
			return;
		}
		existing.setBody(body);
		if (directivePresent)
			existing.setSource(source);
		existing.setWikiPageId(wikiPageId);
		contentTemplateDao.save(existing);
		adoptSeededSiblings(code, wikiPageId);
	}

	private ContentTemplateDbo findForPage(String code, Integer wikiPageId, ContentFormat contentFormat) {
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId).andContentFormatEqualTo(contentFormat.name());
		ContentTemplateDbo adopted = contentTemplateDao.getOne(ex).orElse(null);
		if (adopted != null)
			return adopted;
		ContentTemplateDboExample seeded = new ContentTemplateDboExample();
		seeded.createCriteria().andCodeEqualTo(code).andContentFormatEqualTo(contentFormat.name())
				.andWikiPageIdIsNull();
		return contentTemplateDao.getOne(seeded).orElse(null);
	}

	private void adoptSeededSiblings(String code, Integer wikiPageId) {
		ContentTemplateDboExample otherFormats = new ContentTemplateDboExample();
		otherFormats.createCriteria().andCodeEqualTo(code).andWikiPageIdIsNull();
		for (ContentTemplateDbo sibling : contentTemplateDao.get(otherFormats)) {
			sibling.setWikiPageId(wikiPageId);
			contentTemplateDao.save(sibling);
		}
	}
}
