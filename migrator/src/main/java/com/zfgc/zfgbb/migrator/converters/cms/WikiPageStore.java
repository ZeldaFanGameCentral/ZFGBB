package com.zfgc.zfgbb.migrator.converters.cms;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.WikiPageCategoryDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.migrator.converters.MigrationHasher;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.wiki.WikiTitle;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WikiPageStore {

	private final WikiPageDboMapper pageMapper;
	private final WikiPageRevisionDboMapper revisionMapper;
	private final WikiPageCategoryDboMapper categoryMapper;

	private static int byteSize(String content) {
		return content == null ? 0 : content.getBytes(StandardCharsets.UTF_8).length;
	}

	public Integer ensurePage(String namespace, String title, String slug) {
		WikiTitle canonical = WikiTitle.of(namespace, title, JobContextHolder.getWikiNamespaceCaseMode(namespace));
		namespace = canonical.namespace();
		title = canonical.title();
		WikiPageDboExample ex = new WikiPageDboExample();
		ex.createCriteria().andNamespaceEqualTo(namespace).andTitleEqualTo(title);
		WikiPageDbo existing = pageMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			WikiPageDboExample exSlug = new WikiPageDboExample();
			exSlug.createCriteria().andNamespaceEqualTo(namespace).andSlugEqualTo(slug);
			existing = pageMapper.selectByExample(exSlug).stream().findFirst().orElse(null);
		}
		if (existing != null) {
			if (!Objects.equals(existing.getTitle(), title)) {
				existing.setTitle(title);
				pageMapper.updateByPrimaryKey(existing);
			}
			return existing.getWikiPageId();
		}
		WikiPageDbo page = new WikiPageDbo();
		page.setNamespace(namespace);
		page.setTitle(title);
		page.setSlug(slug);
		page.setMigrationHash(MigrationHasher.hash("entitypage" + namespace + slug));
		pageMapper.insert(page);
		return page.getWikiPageId();
	}

	public void ensureCategory(Integer wikiPageId, String categoryName) {
		WikiPageCategoryDboExample ex = new WikiPageCategoryDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId).andCategoryNameEqualTo(categoryName);
		if (categoryMapper.selectByExample(ex).isEmpty()) {
			WikiPageCategoryDbo row = new WikiPageCategoryDbo();
			row.setWikiPageId(wikiPageId);
			row.setCategoryName(categoryName);
			categoryMapper.insert(row);
		}
	}

	public boolean upsertCurrentRevision(Integer wikiPageId, String content, OffsetDateTime createdTs) {
		return upsertCurrentRevision(wikiPageId, content, createdTs, null, null);
	}

	public boolean upsertCurrentRevision(Integer wikiPageId, String content, OffsetDateTime authoredTs,
			String authorName, String summary) {
		WikiPageRevisionDbo revision = new WikiPageRevisionDbo();
		revision.setWikiPageId(wikiPageId);
		revision.setContent(content);
		revision.setContentSize(byteSize(content));
		revision.setContentFormat("BBCODE");
		revision.setCurrentFlag(true);
		revision.setAuthoredTs(authoredTs);
		revision.setAuthorName(authorName);
		revision.setSummary(summary);
		revision.setStatus("APPROVED");
		revision.setMigrationHash(MigrationHasher.hash(wikiPageId + "current" + content + authoredTs + authorName));

		WikiPageRevisionDboExample ex = new WikiPageRevisionDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId).andCurrentFlagEqualTo(true);
		WikiPageRevisionDbo existing = revisionMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			revisionMapper.insert(revision);
			return true;
		}
		boolean protectedRevision = existing.getMigrationHash() == null || "seed".equals(existing.getMigrationHash());
		if (JobContextHolder.isForce()
				|| (!protectedRevision && !Objects.equals(existing.getMigrationHash(), revision.getMigrationHash()))) {
			revision.setWikiPageRevisionId(existing.getWikiPageRevisionId());
			revisionMapper.updateByPrimaryKey(revision);
			return true;
		}
		return !protectedRevision;
	}

	public void upsertHistoricalRevision(Integer wikiPageId, Integer legacyRevId, String content,
			OffsetDateTime authoredTs, String authorName, String summary) {
		String hash = MigrationHasher.hash(wikiPageId + "hist" + legacyRevId);
		WikiPageRevisionDbo revision = new WikiPageRevisionDbo();
		revision.setWikiPageId(wikiPageId);
		revision.setContent(content);
		revision.setContentSize(byteSize(content));
		revision.setContentFormat("BBCODE");
		revision.setCurrentFlag(false);
		revision.setAuthoredTs(authoredTs);
		revision.setAuthorName(authorName);
		revision.setSummary(summary);
		revision.setStatus("APPROVED");
		revision.setMigrationHash(hash);

		WikiPageRevisionDboExample ex = new WikiPageRevisionDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId).andMigrationHashEqualTo(hash);
		WikiPageRevisionDbo existing = revisionMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			revisionMapper.insert(revision);
			return;
		}
		if (JobContextHolder.isForce() || !Objects.equals(existing.getContent(), content)
				|| !Objects.equals(existing.getAuthoredTs(), authoredTs)) {
			revision.setWikiPageRevisionId(existing.getWikiPageRevisionId());
			revisionMapper.updateByPrimaryKey(revision);
		}
	}
}
