package com.zfgc.zfgbb.dataprovider.cms;

import static com.zfgc.zfgbb.dataprovider.cms.CatalogDataProvider.escapeLike;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ResourceViewDbo;
import com.zfgc.zfgbb.dbo.ResourceViewDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ResourceViewDboMapper;
import com.zfgc.zfgbb.mappers.custom.CmsFacetMapper;
import com.zfgc.zfgbb.mapstruct.cms.ResourceMap;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Resource;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ResourceDataProvider {

	private final CatalogDataProvider catalogDataProvider;

	private final ResourceViewDboMapper resourceViewMapper;

	private final ContentEntityDboMapper contentEntityMapper;

	private final WikiDataProvider wikiDataProvider;

	private final ResourceMap resourceMap;

	private final CmsFacetMapper cmsFacetMapper;

	public PagedResult<Resource> getResources(String search, String type, String author,
			Boolean hasDownload, String sort, int page, int pageSize) {
		ResourceViewDboExample resourceExample = new ResourceViewDboExample();
		ResourceViewDboExample.Criteria criteria = resourceExample.createCriteria();

		if (search != null && !search.isBlank()) {
			criteria.andTitleLike("%" + escapeLike(search.trim()) + "%");
		}
		if (author != null && !author.isBlank()) {
			criteria.andAuthorNameLike("%" + escapeLike(author.trim()) + "%");
		}
		if (type != null && !type.isBlank()) {
			criteria.andResourceTypeEqualTo(type.trim());
		}
		if (Boolean.TRUE.equals(hasDownload)) {
			criteria.andDownloadContentResourceIdIsNotNull();
		} else if (Boolean.FALSE.equals(hasDownload)) {
			criteria.andDownloadContentResourceIdIsNull();
		}

		if ("newest".equals(sort)) {
			resourceExample.setOrderByClause("published_ts desc, title asc");
		} else if ("updated".equals(sort)) {
			resourceExample.setOrderByClause("last_updated_ts desc, title asc");
		} else if ("views".equals(sort)) {
			resourceExample.setOrderByClause("view_count desc, title asc");
		} else if ("downloads".equals(sort)) {
			resourceExample.setOrderByClause("download_count desc, title asc");
		} else if ("rating".equals(sort)) {
			resourceExample.setOrderByClause("rating desc, vote_count desc, title asc");
		} else if ("random".equals(sort)) {
			resourceExample.setOrderByClause("random()");
		} else {
			resourceExample.setOrderByClause("title asc");
		}

		long totalCount = resourceViewMapper.countByExample(resourceExample);

		int safePageSize = Math.max(pageSize, 1);
		int safePage = Math.max(page, 1);
		long zeroBasedOffset = (long) (safePage - 1) * (long) safePageSize;
		if (zeroBasedOffset > Integer.MAX_VALUE) {
			return new PagedResult<>(List.of(), (int) totalCount, safePage, safePageSize);
		}
		resourceExample.setLimit(safePageSize);
		resourceExample.setOffset((int) zeroBasedOffset);

		List<ResourceViewDbo> dbos = resourceViewMapper.selectByExampleWithLimits(resourceExample);
		Map<Integer, String> liveNames = catalogDataProvider.displayNames(
				dbos.stream().map(ResourceViewDbo::getCreatedUserId));

		List<Resource> items = dbos.stream().map(dbo -> {
			Resource resource = resourceMap.toModel(dbo);
			String name = liveNames.get(dbo.getCreatedUserId());
			resource.setAuthor(name != null ? name : dbo.getAuthorName());
			return resource;
		}).collect(Collectors.toList());

		return new PagedResult<>(items, (int) totalCount, safePage, safePageSize);
	}

	public List<Map.Entry<String, Long>> getResourceTypes() {
		return cmsFacetMapper.countResourceTypes().stream()
				.map(fc -> Map.entry(fc.getValue(), fc.getCount()))
				.collect(Collectors.toList());
	}

	public Resource getResource(String slug) {
		ResourceViewDboExample ex = new ResourceViewDboExample();
		ex.createCriteria().andSlugEqualTo(slug);
		ResourceViewDbo dbo = resourceViewMapper.selectByExample(ex).stream().findFirst()
				.orElseThrow(ZfgcNotFoundException::new);
		Resource resource = resourceMap.toModel(dbo);
		String resourceAuthor = catalogDataProvider.displayNames(
				Stream.of(dbo.getCreatedUserId())).get(dbo.getCreatedUserId());
		resource.setAuthor(resourceAuthor != null ? resourceAuthor : dbo.getAuthorName());
		resource.setDownloadFilename(catalogDataProvider.contentFilename(dbo.getDownloadContentResourceId()));
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
