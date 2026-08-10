package com.zfgc.zfgbb.dataprovider.cms;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ContentCollectionDboExample;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDboExample;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
import com.zfgc.zfgbb.dbo.ProjectViewDbo;
import com.zfgc.zfgbb.dbo.ProjectViewDboExample;
import com.zfgc.zfgbb.dbo.ProjectDownloadDboExample;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDboExample;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDboExample;
import com.zfgc.zfgbb.dbo.ProjectTagDbo;
import com.zfgc.zfgbb.dbo.ProjectTagDboExample;
import com.zfgc.zfgbb.dbo.TagDbo;
import com.zfgc.zfgbb.dbo.TagDboExample;
import com.zfgc.zfgbb.dbo.TeamDbo;
import com.zfgc.zfgbb.dbo.TeamMemberDbo;
import com.zfgc.zfgbb.dbo.TeamMemberDboExample;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.cms.ContentCollectionDao;
import com.zfgc.zfgbb.dao.cms.ContentCollectionItemDao;
import com.zfgc.zfgbb.dao.cms.ContentEntityDao;
import com.zfgc.zfgbb.dao.cms.ProjectViewDao;
import com.zfgc.zfgbb.dao.cms.ProjectDownloadDao;
import com.zfgc.zfgbb.dao.cms.ProjectNewsDao;
import com.zfgc.zfgbb.dao.cms.ProjectScreenshotDao;
import com.zfgc.zfgbb.dao.cms.ProjectTagDao;
import com.zfgc.zfgbb.dao.cms.TagDao;
import com.zfgc.zfgbb.dao.users.TeamDao;
import com.zfgc.zfgbb.dao.users.TeamMemberDao;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.mapstruct.cms.ProjectMap;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.cms.ProjectNews;
import com.zfgc.zfgbb.model.cms.TeamInfo;
import com.zfgc.zfgbb.model.cms.TeamMember;
import com.zfgc.zfgbb.dataprovider.users.GuestPermissionDataProvider;
import lombok.RequiredArgsConstructor;

@Repository
@UnfilteredBoardRead("restricted to guest-visible boards")
@RequiredArgsConstructor
public class ProjectDataProvider {

	private final CatalogDataProvider catalogDataProvider;

	private final ProjectViewDao projectViewDao;

	private final ContentEntityDao contentEntityDao;

	private final ProjectScreenshotDao projectScreenshotDao;

	private final ProjectDownloadDao projectDownloadDao;

	private final TagDao tagDao;

	private final ProjectTagDao projectTagDao;

	private final TeamDao teamDao;

	private final TeamMemberDao teamMemberDao;

	private final ProjectNewsDao projectNewsDao;

	private final ThreadDao threadDao;

	private final ContentCollectionDao contentCollectionDao;

	private final ContentCollectionItemDao contentCollectionItemDao;

	private final WikiDataProvider wikiDataProvider;

	private final ProjectMap projectMap;

	private final GuestPermissionDataProvider guestPermissionDataProvider;

	public List<Integer> guestVisibleBoardIds() {
		return guestPermissionDataProvider.guestVisibleBoardIds();
	}

	private List<Integer> contentEntityIdsWithDownloads() {
		return projectDownloadDao.get(new ProjectDownloadDboExample()).stream()
				.map(ProjectDownloadDbo::getContentEntityId)
				.distinct()
				.toList();
	}

	public PagedResult<Project> getProjects(String search, String status, String language, String author,
			Boolean hasDownload, String sort, int page, int pageSize) {
		ProjectViewDboExample projectExample = new ProjectViewDboExample();
		ProjectViewDboExample.Criteria criteria = projectExample.createCriteria();

		if (search != null && !search.isBlank()) {
			criteria.andTitleContains(search.trim());
		}
		if (author != null && !author.isBlank()) {
			criteria.andAuthorNameContains(author.trim());
		}
		if (status != null && !status.isBlank()) {
			List<String> includedStatuses = Stream.of(status.split(",")).map(String::trim)
					.filter(value -> !value.isEmpty() && !value.startsWith("-")).toList();
			List<String> excludedStatuses = Stream.of(status.split(",")).map(String::trim)
					.filter(value -> value.startsWith("-") && value.length() > 1)
					.map(value -> value.substring(1)).toList();
			if (!includedStatuses.isEmpty()) {
				criteria.andStatusIn(includedStatuses);
			}
			if (!excludedStatuses.isEmpty()) {
				criteria.andStatusNotIn(excludedStatuses);
			}
		}
		if (language != null && !language.isBlank()) {
			criteria.andLanguageEqualTo(language.trim());
		}
		if (hasDownload != null) {
			List<Integer> entityIdsWithDownloads = contentEntityIdsWithDownloads();
			if (Boolean.TRUE.equals(hasDownload)) {
				criteria.andContentEntityIdIn(entityIdsWithDownloads.isEmpty()
						? List.of(Integer.MIN_VALUE)
						: entityIdsWithDownloads);
			} else if (!entityIdsWithDownloads.isEmpty()) {
				criteria.andContentEntityIdNotIn(entityIdsWithDownloads);
			}
		}

		projectExample.setOrderByClause(switch (sort == null ? "" : sort) {
			case "newest" -> "published_ts desc, title asc";
			case "updated" -> "last_updated_ts desc, title asc";
			case "views" -> "view_count desc, title asc";
			case "downloads" -> "download_count desc, title asc";
			case "rating" -> "rating desc, vote_count desc, title asc";
			case "random" -> "random()";
			default -> "title asc";
		});

		long totalCount = projectViewDao.count(projectExample);

		int safePageSize = Math.max(pageSize, 1);
		int safePage = Math.max(page, 1);
		long zeroBasedOffset = (long) (safePage - 1) * (long) safePageSize;
		if (zeroBasedOffset > Integer.MAX_VALUE) {
			return new PagedResult<>(List.of(), (int) totalCount, safePage, safePageSize);
		}
		projectExample.setLimit(safePageSize);
		projectExample.setOffset((int) zeroBasedOffset);

		List<ProjectViewDbo> dbos = projectViewDao.get(projectExample);
		Map<Integer, String> liveNames = catalogDataProvider.displayNames(
				dbos.stream().map(ProjectViewDbo::getCreatedUserId));

		List<Project> items = dbos.stream().map(dbo -> {
			Project project = projectMap.toModel(dbo);
			String name = liveNames.get(dbo.getCreatedUserId());
			project.setAuthor(name != null ? name : dbo.getAuthorName());
			return project;
		}).collect(Collectors.toList());

		PagedResult<Project> result = new PagedResult<>(items, (int) totalCount, safePage, safePageSize);
		fillTags(result.getItems());
		return result;
	}

	public Map<String, List<Map.Entry<String, Long>>> getFacets() {
		List<Map.Entry<String, Long>> languages = projectViewDao.countProjectLanguages().stream()
				.map(fc -> Map.entry(fc.getValue(), fc.getCount()))
				.collect(Collectors.toList());
		List<Map.Entry<String, Long>> statuses = projectViewDao.countProjectStatuses().stream()
				.map(fc -> Map.entry(fc.getValue(), fc.getCount()))
				.collect(Collectors.toList());
		return Map.of(
				"languages", languages,
				"statuses", statuses);
	}

	public Project getProject(String slug) {
		ProjectViewDboExample ex = new ProjectViewDboExample();
		ex.createCriteria().andSlugEqualTo(slug);
		ProjectViewDbo dbo = projectViewDao.getOne(ex)
				.orElseThrow(ZfgcNotFoundException::new);
		Project project = projectMap.toModel(dbo);
		String projectAuthor = catalogDataProvider.displayNames(
				Stream.of(dbo.getCreatedUserId())).get(dbo.getCreatedUserId());
		project.setAuthor(projectAuthor != null ? projectAuthor : dbo.getAuthorName());

		ProjectScreenshotDboExample screenshotEx = new ProjectScreenshotDboExample();
		screenshotEx.createCriteria().andContentEntityIdEqualTo(dbo.getContentEntityId());
		screenshotEx.setOrderByClause("ordinal asc");
		project.setScreenshots(projectScreenshotDao.get(screenshotEx).stream().map(projectMap::toModel).collect(Collectors.toList()));

		ProjectDownloadDboExample downloadEx = new ProjectDownloadDboExample();
		downloadEx.createCriteria().andContentEntityIdEqualTo(dbo.getContentEntityId());
		downloadEx.setOrderByClause("ordinal asc");
		project.setDownloads(projectDownloadDao.get(downloadEx).stream().map(projectMap::toModel).collect(Collectors.toList()));
		project.getDownloads().forEach(download ->
				download.setFilename(catalogDataProvider.contentFilename(download.getContentResourceId())));

		fillTags(List.of(project));
		project.setTeam(loadTeam(dbo.getTeamId()));
		project.setNews(getProjectNews(dbo.getContentEntityId()));

		if (dbo.getWikiPageId() != null) {
			wikiDataProvider.getWikiPage(dbo.getWikiPageId()).ifPresent(project::setPage);
		}
		return project;
	}

	public Optional<Project> getProjectCardData(String slug) {
		ProjectViewDboExample ex = new ProjectViewDboExample();
		ex.createCriteria().andSlugEqualTo(slug);
		return projectViewDao.getOne(ex).map(dbo -> {
			Project project = projectMap.toModel(dbo);
			String projectAuthor = catalogDataProvider.displayNames(
					Stream.of(dbo.getCreatedUserId())).get(dbo.getCreatedUserId());
			project.setAuthor(projectAuthor != null ? projectAuthor : dbo.getAuthorName());
			return project;
		});
	}

	public Optional<Project> findFeaturedCollectionProject() {
		return featuredCollectionProjectSlug().map(this::getProject);
	}

	public Optional<Project> findFeaturedCollectionProjectCard() {
		return featuredCollectionProjectSlug().flatMap(this::getProjectCardData);
	}

	private Optional<String> featuredCollectionProjectSlug() {
		ContentCollectionDboExample featuredCollection = new ContentCollectionDboExample();
		featuredCollection.createCriteria().andCodeEqualTo("potm");
		return contentCollectionDao.getOne(featuredCollection)
				.flatMap(collection -> {
					ContentCollectionItemDboExample itemsInOrder = new ContentCollectionItemDboExample();
					itemsInOrder.createCriteria()
							.andContentCollectionIdEqualTo(collection.getContentCollectionId());
					itemsInOrder.setOrderByClause("ordinal asc");
					return contentCollectionItemDao.get(itemsInOrder).stream()
							.map(ContentCollectionItemDbo::getContentEntityId)
							.filter(Objects::nonNull)
							.findFirst();
				})
				.flatMap(contentEntityDao::find)
				.map(ContentEntityDbo::getSlug);
	}

	public List<ProjectNews> getProjectNews(Integer projectId) {
		ProjectNewsDboExample ex = new ProjectNewsDboExample();
		ex.createCriteria().andContentEntityIdEqualTo(projectId);
		List<ProjectNewsDbo> rows = projectNewsDao.get(ex);

		List<Integer> threadIds = rows.stream().map(ProjectNewsDbo::getThreadId)
				.filter(Objects::nonNull).distinct().toList();
		List<Integer> publicBoards = guestVisibleBoardIds();
		Map<Integer, String> threadNames = new HashMap<>();
		if (!threadIds.isEmpty() && !publicBoards.isEmpty()) {
			ThreadDboExample threadEx = new ThreadDboExample();
			threadEx.createCriteria().andThreadIdIn(threadIds).andBoardIdIn(publicBoards);
			threadDao.get(threadEx)
					.forEach(thread -> threadNames.put(thread.getThreadId(), thread.getThreadName()));
		}

		return rows.stream()
				.map(dbo -> projectMap.toNews(dbo, threadNames.get(dbo.getThreadId())))
				.sorted(Comparator.comparing(ProjectNews::getPublishedTs,
				Comparator.nullsLast(Comparator.reverseOrder()))).toList();
	}

	public void linkProjectThread(Integer projectId, Integer threadId) {
		ContentEntityDbo dbo = contentEntityDao.find(projectId).orElse(null);
		dbo.setThreadId(threadId);
		contentEntityDao.save(dbo);
	}

	private void fillTags(List<Project> items) {
		if (items.isEmpty()) {
			return;
		}
		List<Integer> ids = items.stream().map(Project::getId).toList();
		ProjectTagDboExample ex = new ProjectTagDboExample();
		ex.createCriteria().andContentEntityIdIn(ids);
		List<ProjectTagDbo> links = projectTagDao.get(ex);
		if (links.isEmpty()) {
			return;
		}
		TagDboExample tagEx = new TagDboExample();
		tagEx.createCriteria().andTagIdIn(links.stream().map(ProjectTagDbo::getTagId).distinct().toList());
		Map<Integer, String> names = tagDao.get(tagEx).stream()
				.collect(Collectors.toMap(TagDbo::getTagId, TagDbo::getName));
		Map<Integer, List<String>> byProject = new HashMap<>();
		links.forEach(link -> byProject.computeIfAbsent(link.getContentEntityId(), k -> new ArrayList<>())
				.add(names.get(link.getTagId())));
		items.forEach(item -> item.setTags(byProject.getOrDefault(item.getId(), List.of())));
	}

	private TeamInfo loadTeam(Integer teamId) {
		if (teamId == null) {
			return null;
		}
		TeamDbo dbo = teamDao.find(teamId).orElse(null);
		if (dbo == null) {
			return null;
		}
		TeamInfo team = projectMap.toTeam(dbo);
		TeamMemberDboExample ex = new TeamMemberDboExample();
		ex.createCriteria().andTeamIdEqualTo(teamId);
		List<TeamMemberDbo> members = teamMemberDao.get(ex);
		Map<Integer, String> names = catalogDataProvider.displayNames(
				members.stream().map(TeamMemberDbo::getUserId));
		members.forEach(member -> team.getMembers()
				.add(projectMap.toTeamMember(member, names.get(member.getUserId()))));
		return team;
	}

}
