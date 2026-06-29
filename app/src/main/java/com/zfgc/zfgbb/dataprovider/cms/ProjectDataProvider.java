package com.zfgc.zfgbb.dataprovider.cms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ContentCollectionDboExample;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDboExample;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ProjectViewDbo;
import com.zfgc.zfgbb.dbo.ProjectViewDboExample;
import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
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
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.ContentCollectionDboMapper;
import com.zfgc.zfgbb.mappers.ContentCollectionItemDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ProjectViewDboMapper;
import com.zfgc.zfgbb.mappers.ProjectDownloadDboMapper;
import com.zfgc.zfgbb.mappers.ProjectNewsDboMapper;
import com.zfgc.zfgbb.mappers.ProjectScreenshotDboMapper;
import com.zfgc.zfgbb.mappers.ProjectTagDboMapper;
import com.zfgc.zfgbb.mappers.TagDboMapper;
import com.zfgc.zfgbb.mappers.TeamDboMapper;
import com.zfgc.zfgbb.mappers.TeamMemberDboMapper;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mapstruct.cms.ProjectMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.cms.ProjectNews;
import com.zfgc.zfgbb.model.cms.TeamInfo;
import com.zfgc.zfgbb.model.cms.TeamMember;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.dbo.ContentCollectionDbo;

@Repository
@UnfilteredBoardRead("Resolves thread names for CMS project-news entries, restricting the ThreadDboMapper lookup to guest-visible boards so hidden-board titles are not exposed")
public class ProjectDataProvider extends CatalogDataProvider {

	@Autowired
	private ProjectViewDboMapper projectViewMapper;

	@Autowired
	private ContentEntityDboMapper contentEntityMapper;

	@Autowired
	private ProjectScreenshotDboMapper screenshotMapper;

	@Autowired
	private ProjectDownloadDboMapper downloadMapper;

	@Autowired
	private TagDboMapper tagMapper;

	@Autowired
	private ProjectTagDboMapper projectTagMapper;

	@Autowired
	private TeamDboMapper teamMapper;

	@Autowired
	private TeamMemberDboMapper teamMemberMapper;

	@Autowired
	private ProjectNewsDboMapper newsMapper;

	@Autowired
	private ThreadDboMapper threadMapper;

	@Autowired
	private ContentCollectionDboMapper collectionMapper;

	@Autowired
	private ContentCollectionItemDboMapper collectionItemMapper;

	@Autowired
	private WikiDataProvider wikiDataProvider;

	@Autowired
	private ProjectMap projectMap;

	@Autowired
	private BoardPermissionViewDboMapper boardPermissionViewDboMapper;

	private List<Integer> guestVisibleBoardIds() {
		List<Integer> guestPerms = User.guest().getPermissions().stream()
				.map(Permission::getPermissionId).toList();
		BoardPermissionViewDboExample ex = new BoardPermissionViewDboExample();
		ex.createCriteria().andPermissionIdIn(guestPerms);
		return boardPermissionViewDboMapper.selectByExample(ex).stream()
				.map(BoardPermissionViewDbo::getBoardId).distinct().collect(Collectors.toList());
	}

	public PagedResult<Project> getProjects(String search, String status, String language, String author,
			Boolean hasDownload, String sort, int page, int pageSize) {
		List<ProjectViewDbo> dbos = projectViewMapper.selectByExample(new ProjectViewDboExample());
		Set<Integer> withDownloads = hasDownload == null ? Set.of()
				: downloadMapper.selectByExample(new ProjectDownloadDboExample()).stream()
						.map(ProjectDownloadDbo::getContentEntityId).collect(Collectors.toSet());
		Predicate<String> statusFilter = valueFilter(status);

		PagedResult<Project> result = catalogPage(dbos, ProjectViewDbo::getCreatedUserId,
				(dbo, liveNames) -> statusFilter.test(dbo.getStatus())
						&& (language == null || language.equalsIgnoreCase(trimToNull(dbo.getLanguage())))
						&& (search == null || containsIgnoreCase(dbo.getTitle(), search))
						&& (author == null
								|| containsIgnoreCase(dbo.getAuthorName(), author)
								|| containsIgnoreCase(liveNames.get(dbo.getCreatedUserId()), author))
						&& (hasDownload == null || hasDownload == withDownloads.contains(dbo.getContentEntityId())),
				catalogComparator(sort, ProjectViewDbo::getTitle, ProjectViewDbo::getPublishedTs,
						ProjectViewDbo::getLastUpdatedTs, ProjectViewDbo::getViewCount, ProjectViewDbo::getDownloadCount,
						ProjectViewDbo::getRating, ProjectViewDbo::getVoteCount),
				sort, page, pageSize,
				(dbo, liveNames) -> {
					Project project = projectMap.toModel(dbo);
					project.setId(dbo.getContentEntityId());
					String name = liveNames.get(dbo.getCreatedUserId());
					project.setAuthor(name != null ? name : dbo.getAuthorName());
					return project;
				});
		fillTags(result.getItems());
		return result;
	}

	public Map<String, List<Map.Entry<String, Long>>> getFacets() {
		List<ProjectViewDbo> views = projectViewMapper.selectByExample(new ProjectViewDboExample());
		return Map.of(
				"languages", countDistinct(views, ProjectViewDbo::getLanguage),
				"statuses", countDistinct(views, ProjectViewDbo::getStatus));
	}

	public Project getProject(String slug) {
		ProjectViewDboExample ex = new ProjectViewDboExample();
		ex.createCriteria().andSlugEqualTo(slug);
		ProjectViewDbo dbo = projectViewMapper.selectByExample(ex).stream().findFirst()
				.orElseThrow(ZfgcNotFoundException::new);
		Project project = projectMap.toModel(dbo);
		project.setId(dbo.getContentEntityId());
		String projectAuthor = displayNames(Stream.of(dbo.getCreatedUserId())).get(dbo.getCreatedUserId());
		project.setAuthor(projectAuthor != null ? projectAuthor : dbo.getAuthorName());

		ProjectScreenshotDboExample screenshotEx = new ProjectScreenshotDboExample();
		screenshotEx.createCriteria().andContentEntityIdEqualTo(dbo.getContentEntityId());
		screenshotEx.setOrderByClause("ordinal asc");
		project.setScreenshots(screenshotMapper.selectByExample(screenshotEx).stream().map(projectMap::toModel).collect(Collectors.toList()));

		ProjectDownloadDboExample downloadEx = new ProjectDownloadDboExample();
		downloadEx.createCriteria().andContentEntityIdEqualTo(dbo.getContentEntityId());
		downloadEx.setOrderByClause("ordinal asc");
		project.setDownloads(downloadMapper.selectByExample(downloadEx).stream().map(projectMap::toModel).collect(Collectors.toList()));
		project.getDownloads().forEach(download ->
				download.setFilename(contentFilename(download.getContentResourceId())));

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
		return projectViewMapper.selectByExample(ex).stream().findFirst().map(dbo -> {
			Project project = projectMap.toModel(dbo);
			project.setId(dbo.getContentEntityId());
			String projectAuthor = displayNames(Stream.of(dbo.getCreatedUserId())).get(dbo.getCreatedUserId());
			project.setAuthor(projectAuthor != null ? projectAuthor : dbo.getAuthorName());
			return project;
		});
	}

	public Project getFeaturedCollectionProject() {
		ContentCollectionDboExample colEx = new ContentCollectionDboExample();
		colEx.createCriteria().andCodeEqualTo("potm");
		ContentCollectionDbo collection =
				collectionMapper.selectByExample(colEx).stream().findFirst().orElse(null);
		if (collection == null) {
			return null;
		}
		ContentCollectionItemDboExample itemEx = new ContentCollectionItemDboExample();
		itemEx.createCriteria().andContentCollectionIdEqualTo(collection.getContentCollectionId());
		itemEx.setOrderByClause("ordinal asc");
		Integer projectId = collectionItemMapper.selectByExample(itemEx).stream()
				.map(ContentCollectionItemDbo::getContentEntityId)
				.filter(Objects::nonNull)
				.findFirst().orElse(null);
		if (projectId == null) {
			return null;
		}
		ContentEntityDbo dbo = contentEntityMapper.selectByPrimaryKey(projectId);
		return dbo == null ? null : getProject(dbo.getSlug());
	}

	public Project getFeaturedCollectionProjectCard() {
		ContentCollectionDboExample colEx = new ContentCollectionDboExample();
		colEx.createCriteria().andCodeEqualTo("potm");
		ContentCollectionDbo collection =
				collectionMapper.selectByExample(colEx).stream().findFirst().orElse(null);
		if (collection == null)
			return null;
		ContentCollectionItemDboExample itemEx = new ContentCollectionItemDboExample();
		itemEx.createCriteria().andContentCollectionIdEqualTo(collection.getContentCollectionId());
		itemEx.setOrderByClause("ordinal asc");
		Integer projectId = collectionItemMapper.selectByExample(itemEx).stream()
				.map(ContentCollectionItemDbo::getContentEntityId)
				.filter(Objects::nonNull)
				.findFirst().orElse(null);
		if (projectId == null)
			return null;
		ContentEntityDbo dbo = contentEntityMapper.selectByPrimaryKey(projectId);
		return dbo == null ? null : getProjectCardData(dbo.getSlug()).orElse(null);
	}

	public List<ProjectNews> getProjectNews(Integer projectId) {
		ProjectNewsDboExample ex = new ProjectNewsDboExample();
		ex.createCriteria().andContentEntityIdEqualTo(projectId);
		List<ProjectNewsDbo> rows = newsMapper.selectByExample(ex);

		List<Integer> threadIds = rows.stream().map(ProjectNewsDbo::getThreadId)
				.filter(Objects::nonNull).distinct().toList();
		List<Integer> publicBoards = guestVisibleBoardIds();
		Map<Integer, String> threadNames = new HashMap<>();
		if (!threadIds.isEmpty() && !publicBoards.isEmpty()) {
			ThreadDboExample threadEx = new ThreadDboExample();
			threadEx.createCriteria().andThreadIdIn(threadIds).andBoardIdIn(publicBoards);
			threadMapper.selectByExample(threadEx)
					.forEach(thread -> threadNames.put(thread.getThreadId(), thread.getThreadName()));
		}

		return rows.stream().map(dbo -> {
			ProjectNews entry = new ProjectNews();
			entry.setThreadId(dbo.getThreadId());
			entry.setSubject(dbo.getSubject());
			entry.setBody(dbo.getBody());
			entry.setAuthorUserId(dbo.getAuthorUserId());
			entry.setAuthorName(dbo.getAuthorName());
			entry.setPublishedTs(dbo.getPublishedTs());
			if (dbo.getThreadId() != null) {
				entry.setThreadName(threadNames.get(dbo.getThreadId()));
			}
			return entry;
		}).sorted(Comparator.comparing(ProjectNews::getPublishedTs,
				Comparator.nullsLast(Comparator.reverseOrder()))).toList();
	}

	public void linkProjectThread(Integer projectId, Integer threadId) {
		ContentEntityDbo dbo = contentEntityMapper.selectByPrimaryKey(projectId);
		dbo.setThreadId(threadId);
		contentEntityMapper.updateByPrimaryKey(dbo);
	}

	private void fillTags(List<Project> items) {
		if (items.isEmpty()) {
			return;
		}
		List<Integer> ids = items.stream().map(Project::getId).toList();
		ProjectTagDboExample ex = new ProjectTagDboExample();
		ex.createCriteria().andContentEntityIdIn(ids);
		List<ProjectTagDbo> links = projectTagMapper.selectByExample(ex);
		if (links.isEmpty()) {
			return;
		}
		TagDboExample tagEx = new TagDboExample();
		tagEx.createCriteria().andTagIdIn(links.stream().map(ProjectTagDbo::getTagId).distinct().toList());
		Map<Integer, String> names = tagMapper.selectByExample(tagEx).stream()
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
		TeamDbo dbo = teamMapper.selectByPrimaryKey(teamId);
		if (dbo == null) {
			return null;
		}
		TeamInfo team = new TeamInfo();
		team.setTeamId(dbo.getTeamId());
		team.setName(dbo.getName());
		team.setDescription(dbo.getDescription());
		TeamMemberDboExample ex = new TeamMemberDboExample();
		ex.createCriteria().andTeamIdEqualTo(teamId);
		List<TeamMemberDbo> members = teamMemberMapper.selectByExample(ex);
		Map<Integer, String> names = displayNames(members.stream().map(TeamMemberDbo::getUserId));
		members.forEach(member -> {
			TeamMember entry = new TeamMember();
			entry.setUserId(member.getUserId());
			entry.setDisplayName(names.get(member.getUserId()));
			entry.setMemberRole(member.getMemberRole());
			team.getMembers().add(entry);
		});
		return team;
	}

}
