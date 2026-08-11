package com.zfgc.zfgbb.dao.cms;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.cms.ContentTemplateDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;

@Repository
public class WikiPageDao extends IdentityDao<WikiPageDbo, WikiPageDboExample> {

	private final ContentTemplateDao contentTemplateDao;

	public WikiPageDao(WikiPageDboMapper mapper,
			ContentTemplateDao contentTemplateDao) {
		super(mapper);
		this.contentTemplateDao = contentTemplateDao;
	}

	public List<Integer> findOwnedHardDeletableWikiPageIds(Integer userId) {
		return ownedPageIdsPartitionedByTemplateLink(userId, false);
	}

	public List<Integer> findOwnedTemplateLinkedWikiPageIds(Integer userId) {
		return ownedPageIdsPartitionedByTemplateLink(userId, true);
	}

	public List<Integer> findWikiPageContentResourceIds(List<Integer> pageIds) {
		WikiPageDboExample withResources = new WikiPageDboExample();
		withResources.createCriteria().andContentResourceIdIsNotNull().andWikiPageIdIn(pageIds);
		return get(withResources).stream().map(WikiPageDbo::getContentResourceId).toList();
	}

	public int nullWikiPageCreators(Integer userId) {
		WikiPageDbo orphaned = new WikiPageDbo();
		WikiPageDboExample createdByUser = new WikiPageDboExample();
		createdByUser.createCriteria().andCreatedUserIdEqualTo(userId);
		return updateWhereSettingColumns(orphaned, Set.of("created_user_id"), createdByUser);
	}

	private List<Integer> ownedPageIdsPartitionedByTemplateLink(Integer userId, boolean templateLinked) {
		Set<Integer> pagesBackingATemplate = contentTemplateDao.wikiPageIdsBackingATemplate();
		WikiPageDboExample owned = new WikiPageDboExample();
		owned.createCriteria().andCreatedUserIdEqualTo(userId);
		return get(owned).stream()
				.map(WikiPageDbo::getWikiPageId)
				.filter(pageId -> pagesBackingATemplate.contains(pageId) == templateLinked)
				.toList();
	}

	public Set<Integer> contentResourceIdsAmong(List<Integer> contentResourceIds) {
		WikiPageDboExample referencing = new WikiPageDboExample();
		referencing.createCriteria().andContentResourceIdIn(contentResourceIds);
		return get(referencing).stream().map(WikiPageDbo::getContentResourceId).collect(Collectors.toSet());
	}
}
