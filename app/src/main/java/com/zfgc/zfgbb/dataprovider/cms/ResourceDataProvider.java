package com.zfgc.zfgbb.dataprovider.cms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ResourceViewDbo;
import com.zfgc.zfgbb.dbo.ResourceViewDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ResourceViewDboMapper;
import com.zfgc.zfgbb.mapstruct.cms.ResourceMap;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Resource;

@Repository
public class ResourceDataProvider extends CatalogDataProvider {

	@Autowired
	private ResourceViewDboMapper resourceViewMapper;

	@Autowired
	private ContentEntityDboMapper contentEntityMapper;

	@Autowired
	private WikiDataProvider wikiDataProvider;

	@Autowired
	private ResourceMap resourceMap;

	public PagedResult<Resource> getResources(String search, String type, String author,
			Boolean hasDownload, String sort, int page, int pageSize) {
		List<ResourceViewDbo> dbos = resourceViewMapper.selectByExample(new ResourceViewDboExample());
		Predicate<String> typeFilter = valueFilter(type);

		return catalogPage(dbos, ResourceViewDbo::getCreatedUserId,
				(dbo, liveNames) -> typeFilter.test(dbo.getResourceType())
						&& (search == null || containsIgnoreCase(dbo.getTitle(), search))
						&& (author == null
								|| containsIgnoreCase(dbo.getAuthorName(), author)
								|| containsIgnoreCase(liveNames.get(dbo.getCreatedUserId()), author))
						&& (hasDownload == null || hasDownload == (dbo.getDownloadContentResourceId() != null
								|| trimToNull(dbo.getDownloadUrl()) != null)),
				catalogComparator(sort, ResourceViewDbo::getTitle, ResourceViewDbo::getPublishedTs,
						ResourceViewDbo::getLastUpdatedTs, ResourceViewDbo::getViewCount, ResourceViewDbo::getDownloadCount,
						ResourceViewDbo::getRating, ResourceViewDbo::getVoteCount),
				sort, page, pageSize,
				(dbo, liveNames) -> {
					Resource resource = resourceMap.toModel(dbo);
					resource.setId(dbo.getContentEntityId());
					String name = liveNames.get(dbo.getCreatedUserId());
					resource.setAuthor(name != null ? name : dbo.getAuthorName());
					return resource;
				});
	}

	public List<Map.Entry<String, Long>> getResourceTypes() {
		return countDistinct(resourceViewMapper.selectByExample(new ResourceViewDboExample()), ResourceViewDbo::getResourceType);
	}

	public Resource getResource(String slug) {
		ResourceViewDboExample ex = new ResourceViewDboExample();
		ex.createCriteria().andSlugEqualTo(slug);
		ResourceViewDbo dbo = resourceViewMapper.selectByExample(ex).stream().findFirst()
				.orElseThrow(ZfgcNotFoundException::new);
		Resource resource = resourceMap.toModel(dbo);
		resource.setId(dbo.getContentEntityId());
		String resourceAuthor = displayNames(Stream.of(dbo.getCreatedUserId())).get(dbo.getCreatedUserId());
		resource.setAuthor(resourceAuthor != null ? resourceAuthor : dbo.getAuthorName());
		resource.setDownloadFilename(contentFilename(dbo.getDownloadContentResourceId()));
		if (dbo.getWikiPageId() != null) {
			wikiDataProvider.getWikiPage(dbo.getWikiPageId()).ifPresent(resource::setPage);
		}
		return resource;
	}

	public void linkResourceThread(Integer resourceId, Integer threadId) {
		ContentEntityDbo dbo = contentEntityMapper.selectByPrimaryKey(resourceId);
		dbo.setThreadId(threadId);
		contentEntityMapper.updateByPrimaryKey(dbo);
	}

}
