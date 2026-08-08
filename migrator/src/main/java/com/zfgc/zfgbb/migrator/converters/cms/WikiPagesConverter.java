package com.zfgc.zfgbb.migrator.converters.cms;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.migrator.converters.AbstractConverter;
import com.zfgc.zfgbb.migrator.converters.Cancellable;
import com.zfgc.zfgbb.migrator.converters.LegacyIdMaps;
import com.zfgc.zfgbb.migrator.converters.LegacyUrlRewriter;
import com.zfgc.zfgbb.migrator.converters.MigrationHasher;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;
import com.zfgc.zfgbb.migrator.markup.MarkupConverter;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwPageDb;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwPageDbExample;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwTextDb;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwTextDbExample;
import com.zfgc.zfgbb.migrator.smf.queries.SmfDownloadQueryMapper;
import com.zfgc.zfgbb.migrator.smf.queries.SmfDownloadQueryMapper.WikiRevisionRow;
import com.zfgc.zfgbb.migrator.wiki.mappers.MwPageDbMapper;
import com.zfgc.zfgbb.migrator.wiki.mappers.MwTextDbMapper;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import java.util.stream.Stream;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.ProjectDboMapper;
import com.zfgc.zfgbb.mappers.ProjectScreenshotDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageCategoryDboMapper;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;
import com.zfgc.zfgbb.wiki.WikiTitle;
import com.zfgc.zfgbb.migrator.markup.Node;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WikiPagesConverter extends AbstractConverter<Void> {

	private static final Pattern WIKI_FILE = Pattern.compile("\\[img\\]wiki-file:([^\\[\\]]+)\\[/img\\]");
	private static final Pattern USER_PROFILE_BLOCK = Pattern.compile(
			"\\[template=UserProfile\\](.*?)\\[/template\\]", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	private static final Pattern USERID_PARAM = Pattern.compile("(?m)^userid=(\\d*)$");
	private static final Pattern FILE_REF = Pattern.compile("\\[\\[(?:File|Image):([^\\]|]+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern BODY_IMG = Pattern.compile("\\[img\\]/content/(\\d+)\\[/img\\]");

	private record SpecialRedirect(String localName, String title, String route) {}

	private static final List<SpecialRedirect> SPECIAL_REDIRECTS = List.of(
			new SpecialRedirect("listusers", "ListUsers", "/forum/memberList/1"));

	private static final Logger logger = LoggerFactory.getLogger(WikiPagesConverter.class);

	private final MwPageDbMapper mwPageMapper;
	private final MwTextDbMapper mwTextMapper;
	private final WikiPageDboMapper wikiPageMapper;
	private final UserDboMapper userMapper;
	private final ContentResourceDboMapper contentMapper;
	private final WikiPageStore wikiPages;
	private final WikiPageCategoryDboMapper categoryMapper;
	private final MigratorIdMapService idMap;
	private final ThreadDboMapper threadMapper;
	private final MessageDboMapper messageMapper;
	private final MessageHistoryDboMapper messageHistoryMapper;
	private final MigratorTimestampMapper migratorTimestampMapper;
	private final IpAddressDboMapper ipAddressMapper;
	private final SmfDownloadQueryMapper legacyQueries;
	private final ProjectDboMapper projectMapper;
	private final ContentEntityDboMapper contentEntityMapper;
	private final ProjectScreenshotDboMapper screenshotMapper;
	private final WikiPageRevisionDboMapper revisionMapper;
	private final ContentTemplateDboMapper contentTemplateMapper;
	private final TransactionTemplate transactionTemplate;
	private final JdbcTemplate targetJdbc;

	private record TalkPage(Integer sourceNamespaceId, String namespace, String title, String content,
			OffsetDateTime authoredTs, String authorName) {
	}

	private record RunContext(
			LegacyUrlRewriter urlRewriter,
			LegacyIdMaps urlMaps,
			Map<String, Integer> legacyMembersByName,
			List<TalkPage> talkPages) {
	}

	@Override
	public JobType getType() {
		return JobType.WIKI_PAGES;
	}

	@Override
	public Void convertToZfgbb() {
		List<WikiRevisionRow> allRevs = legacyQueries.selectWikiRevisions();
		Map<Integer, WikiRevisionRow> revById = allRevs.stream()
				.collect(Collectors.toMap(WikiRevisionRow::getId, Function.identity(), (a, b) -> a));
		Map<Integer, List<WikiRevisionRow>> revsByPage = allRevs.stream()
				.collect(Collectors.groupingBy(WikiRevisionRow::getPageId));
		Map<Integer, String> textByOldId = mwTextMapper.selectByExampleWithBLOBs(new MwTextDbExample()).stream()
				.collect(Collectors.toMap(MwTextDb::getOldId, text -> text.getOldText() == null ? "" : text.getOldText(), (a, b) -> a));
		Map<Integer, List<String>> categoryLinks = legacyQueries.selectWikiCategoryLinks().stream()
				.collect(Collectors.groupingBy(row -> row.getId(),
						Collectors.mapping(row -> CmsSupport.wikiTitleDisplay(row.getName()), Collectors.toList())));
		Map<String, SmfDownloadQueryMapper.WikiProjectLinkRow> wikiProjectLinks = new HashMap<>();
		if (legacyQueries.wikiProjectLinkTableExists() > 0) {
			legacyQueries.selectWikiProjectLinks().forEach(row -> wikiProjectLinks.put(row.getWikiTitle(), row));
		}
		LegacyIdMaps urlMaps = new LegacyIdMaps(
				idMap.getAllForType(LegacyEntityType.THREAD),
				idMap.getAllForType(LegacyEntityType.MESSAGE),
				idMap.getAllForType(LegacyEntityType.BOARD),
				idMap.getAllForType(LegacyEntityType.USER),
				idMap.getAllForType(LegacyEntityType.ATTACHMENT),
				idMap.getAllForType(LegacyEntityType.GAME));
		LegacyUrlRewriter urlRewriter = LegacyUrlRewriter.forLegacyHost(
				JobContextHolder.getLegacyHost(), JobContextHolder.getAppBaseUrl());
		Map<String, Integer> legacyMembersByName = new HashMap<>();
		legacyQueries.selectMemberNames().forEach(
				row -> legacyMembersByName.put(row.getName().toLowerCase(Locale.ROOT), row.getId()));
		RunContext runContext = new RunContext(
				urlRewriter, urlMaps, legacyMembersByName, new ArrayList<>());

		String imagesSourcePath = JobContextHolder.getWikiImagesSourcePath();
		CmsAssetStore assets = null;
		Path imagesRoot = null;
		if (imagesSourcePath != null) {
			String targetPath = JobContextHolder.getAttachmentsTargetPath();
			if (targetPath == null || targetPath.isBlank()) {
				throw new IllegalStateException("attachmentsTargetPath must be provided when wikiImagesSourcePath is set");
			}
			assets = new CmsAssetStore(contentMapper, targetPath, "wiki");
			imagesRoot = Paths.get(imagesSourcePath);
		}

		List<MwPageDb> pages = mwPageMapper.selectByExample(new MwPageDbExample());
		registerDiscoveredNamespaces(pages);
		preflightTitles(pages);
		logger.info("Beginning conversion of {} MediaWiki pages", pages.size());
		final CmsAssetStore pageAssets = assets;
		final Path pageImagesRoot = imagesRoot;
		int converted = 0;
		for (MwPageDb page : pages) {
			Cancellable.check();
			Boolean written = transactionTemplate.execute(status -> convertOne(page, revById, revsByPage,
					textByOldId, categoryLinks, wikiProjectLinks, pageAssets, pageImagesRoot, runContext));
			if (Boolean.TRUE.equals(written)) {
				converted++;
			}
		}
		logger.info("Finished converting wiki pages ({} written)", converted);
		transactionTemplate.executeWithoutResult(status -> {
			seedSpecialRedirects();
			if (JobContextHolder.isCreateMemberWikiPages()) {
				seedMemberPages();
			}
			convertTalkPages(runContext);
		});
		return null;
	}

	private String unheldEngineRole(Integer sourceNamespaceId) {
		String role = CmsSupport.engineRoleName(sourceNamespaceId);
		if (role == null)
			return null;
		Integer held = targetJdbc.queryForObject(
				"select count(*) from zfgbb.wiki_namespace where engine_role = ?", Integer.class, role);
		return held != null && held > 0 ? null : role;
	}

	private void registerDiscoveredNamespaces(List<MwPageDb> pages) {
		pages.stream().map(MwPageDb::getPageNamespace).distinct()
				.sorted(Comparator.comparing(CmsSupport::wikiNamespace, String.CASE_INSENSITIVE_ORDER))
				.forEach(sourceNamespaceId -> {
			String namespace = CmsSupport.wikiNamespace(sourceNamespaceId);
			if (targetJdbc.queryForObject("select count(*) from zfgbb.wiki_namespace where lower(name)=lower(?)",
					Integer.class, namespace) == 0) targetJdbc.update(
					"insert into zfgbb.wiki_namespace(name,case_mode,edit_permission_code,engine_role) "
					+ "values (?,?,?,?)", namespace,
					JobContextHolder.getWikiNamespaceCaseMode(namespace).name(),
					CmsSupport.defaultEditPermissionCode(sourceNamespaceId),
					unheldEngineRole(sourceNamespaceId));
		});
		targetJdbc.queryForObject("select zfgbb.sync_wiki_namespace_space_aliases()", Integer.class);
		Map<String, String> resolved = targetJdbc.query("select name,case_mode from zfgbb.wiki_namespace", rs -> {
			Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
			while (rs.next()) result.put(rs.getString(1), rs.getString(2));
			return result;
		});
		JobContextHolder.setResolvedWikiNamespaceCaseModes(resolved);
	}

	private void preflightTitles(List<MwPageDb> pages) {
		Map<String, List<MwPageDb>> groups = pages.stream().collect(Collectors.groupingBy(page -> {
			String namespace = CmsSupport.wikiNamespace(page.getPageNamespace());
			return WikiTitle.of(namespace, page.getPageTitle(), JobContextHolder.getWikiNamespaceCaseMode(namespace)).persistenceKey();
		}));
		List<String> conflicts = groups.entrySet().stream().filter(entry -> entry.getValue().size() > 1)
				.sorted(Map.Entry.comparingByKey())
				.map(entry -> entry.getKey() + " <- " + entry.getValue().stream()
						.map(page -> page.getPageId() + ":" + page.getPageTitle()).sorted().toList())
				.toList();
		if (!conflicts.isEmpty())
			throw new IllegalStateException("MediaWiki title collisions must be resolved before migration: " + conflicts);
		Map<String, List<WikiPageDbo>> targets = wikiPageMapper.selectByExample(new WikiPageDboExample()).stream()
				.collect(Collectors.groupingBy(page -> WikiTitle.of(page.getNamespace(), page.getTitle(),
						JobContextHolder.getWikiNamespaceCaseMode(page.getNamespace())).persistenceKey()));
		List<String> targetConflicts = pages.stream().flatMap(page -> {
			String namespace = CmsSupport.wikiNamespace(page.getPageNamespace());
			String key = WikiTitle.of(namespace, page.getPageTitle(),
					JobContextHolder.getWikiNamespaceCaseMode(namespace)).persistenceKey();
			Integer mapped = idMap.lookupOrNull(LegacyEntityType.WIKI_PAGE, page.getPageId());
			return targets.getOrDefault(key, List.of()).stream()
					.filter(target -> !Objects.equals(mapped, target.getWikiPageId()))
					.map(target -> key + " <- source " + page.getPageId() + ":" + page.getPageTitle()
							+ ", target " + target.getWikiPageId() + ":" + target.getSlug());
		}).sorted().toList();
		if (!targetConflicts.isEmpty() && !JobContextHolder.isForce())
			throw new IllegalStateException("MediaWiki titles conflict with target pages; resolve before migration: "
					+ targetConflicts);
	}

	private void seedMemberPages() {
		Map<Integer, Integer> members = idMap.getAllForType(LegacyEntityType.USER);
		if (members.isEmpty()) {
			return;
		}
		Map<Integer, Integer> existingPagesByUserId = new HashMap<>();
		targetJdbc.query("""
				select p.wiki_page_id, p.slug, p.migration_hash, r.content
				from zfgbb.wiki_page p
				join zfgbb.wiki_page_revision r
					on r.wiki_page_id = p.wiki_page_id and r.current_flag
				where p.namespace = 'User'
				""", rs -> {
			String generatedHash = MigrationHasher.hash("entitypageUser" + rs.getString("slug"));
			if (!generatedHash.equals(rs.getString("migration_hash"))) {
				return;
			}
			Matcher block = USER_PROFILE_BLOCK.matcher(rs.getString("content"));
			while (block.find()) {
				Matcher param = USERID_PARAM.matcher(block.group(1));
				if (param.find() && !param.group(1).isEmpty()) {
					existingPagesByUserId.putIfAbsent(
							Integer.parseInt(param.group(1)), rs.getInt("wiki_page_id"));
				}
			}
		});
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserIdIn(new ArrayList<>(members.values()));
		int seeded = 0;
		int adoptedMemberPages = 0;
		for (UserDbo user : userMapper.selectByExample(ex)) {
			String name = user.getDisplayName();
			if (name == null || name.isBlank()) {
				continue;
			}
			String slug = "User:" + name.trim().replace(' ', '_');
			Integer generatedPageId = existingPagesByUserId.get(user.getUserId());
			Integer pageId = generatedPageId;
			if (pageId == null) {
				pageId = wikiPages.findPageId("User", name, slug);
			}
			String loginName = user.getUserName();
			if (pageId == null && loginName != null && !loginName.isBlank()) {
				pageId = wikiPages.findPageId("User", loginName,
						"User:" + loginName.trim().replace(' ', '_'));
			}
			if (pageId == null) {
				pageId = wikiPages.ensurePage("User", name, slug);
			}
			boolean generatedPage = generatedPageId != null
					|| wikiPages.isGeneratedEntityPage(pageId, "User", slug);
			if (generatedPage) {
				wikiPages.upsertCurrentRevision(pageId,
						"[template=UserProfile]\nuserid=" + user.getUserId() + "\n[/template]",
						user.getCreatedTs());
			} else {
				adoptedMemberPages++;
			}
			wikiPages.ensureCategory(pageId, "Members");
			seeded++;
		}
		logger.info("Seeded {} member wiki page(s); left {} imported page(s) as authored", seeded, adoptedMemberPages);
	}

	private void convertTalkPages(RunContext runContext) {
		if (runContext.talkPages().isEmpty()) {
			return;
		}
		for (TalkPage talk : runContext.talkPages()) {
			String subjectNamespace = CmsSupport.wikiNamespace(
					WikiNamespaceRole.subjectNamespaceId(talk.sourceNamespaceId()));
			Integer legacyBoardId = JobContextHolder.getTalkBoardId(subjectNamespace);
			Integer boardId = legacyBoardId == null ? null
					: idMap.lookupOrNull(LegacyEntityType.BOARD, legacyBoardId);
			if (boardId == null) {
				logger.info("no board configured for {} talk pages; skipping '{}'", subjectNamespace, talk.title());
				continue;
			}
			WikiPageDbo subject = findPage(subjectNamespace, CmsSupport.wikiSlug(subjectNamespace, talk.title()));
			if (subject == null) {
				logger.warn("talk page '{}' has no migrated subject page in {}", talk.title(), subjectNamespace);
				continue;
			}
			if (subject.getThreadId() != null) {
				continue;
			}
			subject.setThreadId(createTalkThread(boardId, subject.getTitle(), talk));
			wikiPageMapper.updateByPrimaryKey(subject);
		}
	}

	private Integer userIdByDisplayName(String displayName) {
		if (displayName == null || displayName.isBlank()) {
			return null;
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andDisplayNameEqualTo(displayName.trim());
		return userMapper.selectByExample(ex).stream().findFirst()
				.map(UserDbo::getUserId).orElse(null);
	}

	private WikiPageDbo findPage(String namespace, String slug) {
		WikiPageDboExample ex = new WikiPageDboExample();
		ex.createCriteria().andNamespaceEqualTo(namespace).andSlugEqualTo(slug);
		return wikiPageMapper.selectByExample(ex).stream().findFirst().orElse(null);
	}

	private Integer createTalkThread(Integer boardId, String subjectTitle, TalkPage talk) {
		Integer authorUserId = userIdByDisplayName(talk.authorName());

		ThreadDbo thread = new ThreadDbo();
		thread.setBoardId(boardId);
		thread.setThreadName("Talk: " + subjectTitle);
		thread.setCreatedUserId(authorUserId);
		thread.setLockedFlag(false);
		thread.setPinnedFlag(false);
		thread.setViewCount(0);
		thread.setMigrationHash(MigrationHasher.hash("talkthread" + talk.namespace() + talk.title()));
		threadMapper.insert(thread);

		MessageDbo message = new MessageDbo();
		message.setThreadId(thread.getThreadId());
		message.setBoardId(boardId);
		message.setOwnerId(authorUserId);
		message.setPostInThread(1);
		message.setMigrationHash(MigrationHasher.hash("talkmsg" + talk.namespace() + talk.title()));
		messageMapper.insert(message);

		MessageHistoryDbo history = new MessageHistoryDbo();
		history.setMessageId(message.getMessageId());
		history.setMessageText(talk.content());
		history.setContentFormat(ContentFormat.BBCODE.name());
		history.setCurrentFlag(true);
		history.setIpAddressId(CmsSupport.ensureIpAddress(ipAddressMapper, "127.0.0.1"));
		messageHistoryMapper.insert(history);

		if (talk.authoredTs() != null) {
			migratorTimestampMapper.setThreadTimestamps(thread.getThreadId(), talk.authoredTs(), talk.authoredTs());
			migratorTimestampMapper.setMessageTimestamps(message.getMessageId(), talk.authoredTs(), talk.authoredTs());
			migratorTimestampMapper.setMessageHistoryTimestamps(history.getMessageHistoryId(), talk.authoredTs(), talk.authoredTs());
		}
		return thread.getThreadId();
	}

	private String namespaceForRole(WikiNamespaceRole role, String fallback) {
		return targetJdbc.query("select name from zfgbb.wiki_namespace where engine_role = ?",
				rs -> rs.next() ? rs.getString(1) : fallback, role.name());
	}

	private void seedSpecialRedirects() {
		String specialNamespace = namespaceForRole(WikiNamespaceRole.SPECIAL, "Special");
		for (SpecialRedirect redirect : SPECIAL_REDIRECTS) {
			String slug = specialNamespace + ":" + redirect.localName();
			WikiPageDboExample ex = new WikiPageDboExample();
			ex.createCriteria().andNamespaceEqualTo(specialNamespace).andSlugEqualTo(slug);
			WikiPageDbo existing = wikiPageMapper.selectByExample(ex).stream().findFirst().orElse(null);
			String hash = MigrationHasher.hash("special:" + slug + redirect.route());
			if (existing == null) {
				WikiPageDbo page = new WikiPageDbo();
				page.setNamespace(specialNamespace);
				page.setTitle(redirect.title());
				page.setSlug(slug);
				page.setRedirectTo(redirect.route());
				page.setMigrationHash(hash);
				wikiPageMapper.insert(page);
			} else if (!Objects.equals(hash, existing.getMigrationHash())) {
				existing.setTitle(redirect.title());
				existing.setRedirectTo(redirect.route());
				existing.setMigrationHash(hash);
				wikiPageMapper.updateByPrimaryKey(existing);
			}
		}
		logger.info("Seeded {} special-page redirect(s)", SPECIAL_REDIRECTS.size());
	}

	private boolean convertOne(MwPageDb page, Map<Integer, WikiRevisionRow> revById,
			Map<Integer, List<WikiRevisionRow>> revsByPage, Map<Integer, String> textByOldId,
			Map<Integer, List<String>> categoryLinks,
			Map<String, SmfDownloadQueryMapper.WikiProjectLinkRow> wikiProjectLinks,
			CmsAssetStore assets, Path imagesRoot, RunContext runContext) {
		WikiRevisionRow rev = revById.get(page.getPageLatest());
		if (rev == null) {
			return false;
		}
		String wikitext = textByOldId.getOrDefault(rev.getTextId(), "");
		Node.Document document = MarkupConverter.parse(wikitext);
		String namespace = CmsSupport.wikiNamespace(page.getPageNamespace());
		String content = remapUserIds(runContext.urlRewriter().rewriteBody(
				resolveImages(MarkupConverter.toBBCode(document), assets, imagesRoot), runContext.urlMaps()),
				namespace, page.getPageTitle(), runContext);

		WikiTitle canonicalTitle = WikiTitle.of(namespace, page.getPageTitle(), JobContextHolder.getWikiNamespaceCaseMode(namespace));
		namespace = canonicalTitle.namespace();
		String slug = CmsSupport.wikiSlug(namespace, page.getPageTitle());

		if (WikiNamespaceRole.isTalkNamespaceId(page.getPageNamespace())) {
			runContext.talkPages().add(new TalkPage(page.getPageNamespace(), namespace, page.getPageTitle(), content,
					CmsSupport.parseMwTimestamp(rev.getRevTimestamp()), rev.getUserText()));
			return false;
		}

		WikiNamespaceRole sourceRole = WikiNamespaceRole.ofMediaWikiNamespaceId(page.getPageNamespace());
		boolean templatePage = sourceRole == WikiNamespaceRole.TEMPLATE;
		if (templatePage) {
			content = CmsSupport.mustacheBody(content);
		}

		WikiPageDbo wikiPage = new WikiPageDbo();
		wikiPage.setNamespace(namespace);
		wikiPage.setTitle(canonicalTitle.title());
		wikiPage.setSlug(slug);
		wikiPage.setRedirectTo(page.getPageIsRedirect() != null && page.getPageIsRedirect() > 0
				? CmsSupport.redirectTarget(wikitext)
				: null);
		if (sourceRole == WikiNamespaceRole.FILE && assets != null && imagesRoot != null) {
			wikiPage.setContentResourceId(assets.store(
					CmsSupport.wikiImagePath(imagesRoot, page.getPageTitle()), 1,
					fileContentType(page.getPageTitle())));
		}
		wikiPage.setMigrationHash(MigrationHasher.hash(page.getPageId() + namespace + slug
				+ wikiPage.getRedirectTo() + wikiPage.getContentResourceId()));

		ContentEntityDbo adoptingProject = null;
		SmfDownloadQueryMapper.WikiProjectLinkRow link =
				"MAIN".equals(namespace) ? wikiProjectLinks.get(page.getPageTitle()) : null;
		if (link != null) {
			adoptingProject = linkedProject(link);
			if (adoptingProject == null || adoptingProject.getWikiPageId() == null) {
				adoptingProject = null;
				logger.warn("wiki-project link for '{}' references unmigrated {} {}",
						page.getPageTitle(), link.getEntityType(), link.getLegacyId());
			}
		}
		Integer wikiPageId;
		if (adoptingProject != null) {
			Integer adoptionTargetId = adoptingProject.getWikiPageId();
			WikiPageDbo target = wikiPageMapper.selectByPrimaryKey(adoptionTargetId);
			wikiPage.setRedirectTo(target.getSlug());
			wikiPage.setMigrationHash(MigrationHasher.hash(
					page.getPageId() + namespace + slug + target.getSlug() + "adopted"));
			Integer redirectPageId = upsertPage(page.getPageId(), wikiPage);
			purgeMigratedRevisions(redirectPageId);
			syncCategories(redirectPageId, List.of());
			backfillProjectMedia(link, content, assets, imagesRoot);
			backfillProjectSummary(adoptingProject, content);
			wikiPageId = adoptionTargetId;
		} else {
			wikiPageId = upsertPage(page.getPageId(), wikiPage);
		}
		boolean currentMigrated = wikiPages.upsertCurrentRevision(wikiPageId, content,
				CmsSupport.parseMwTimestamp(rev.getRevTimestamp()), revAuthor(rev), revSummary(rev));
		if (!currentMigrated) {
			wikiPages.upsertHistoricalRevision(wikiPageId, rev.getId(), content,
					CmsSupport.parseMwTimestamp(rev.getRevTimestamp()), revAuthor(rev), revSummary(rev));
		}
		for (WikiRevisionRow oldRev : revsByPage.getOrDefault(page.getPageId(), List.of())) {
			if (Objects.equals(oldRev.getId(), rev.getId())) {
				continue;
			}
			try {
				String oldWikitext = textByOldId.getOrDefault(oldRev.getTextId(), "");
				String oldContent = remapUserIds(runContext.urlRewriter().rewriteBody(
						resolveImages(MarkupConverter.toBBCode(MarkupConverter.parse(oldWikitext)), assets,
								imagesRoot),
						runContext.urlMaps()), namespace, page.getPageTitle(), runContext);
				if (templatePage) {
					oldContent = CmsSupport.mustacheBody(oldContent);
				}
				wikiPages.upsertHistoricalRevision(wikiPageId, oldRev.getId(), oldContent,
						CmsSupport.parseMwTimestamp(oldRev.getRevTimestamp()), revAuthor(oldRev), revSummary(oldRev));
			} catch (Exception e) {
				logger.warn("Skipping historical revision {} of '{}': {}", oldRev.getId(), page.getPageTitle(), e.toString());
			}
		}
		if (templatePage) {
			publishTemplate(page.getPageTitle(), wikiPageId, currentMigrated ? content : null);
		}
		syncCategories(wikiPageId, Stream
				.concat(adoptingProject != null ? Stream.of("ZFGC Projects")
						: Stream.<String>empty(),
						Stream.concat(MarkupConverter.categories(document).stream(),
								categoryLinks.getOrDefault(page.getPageId(), List.of()).stream()))
				.filter(name -> !maintenanceCategory(name))
				.distinct().toList());
		return true;
	}

	private static int fileContentType(String name) {
		return MediaTypeFactory.getMediaType(name)
				.map(mediaType -> "image".equals(mediaType.getType())
						? CmsAssetStore.TYPE_IMAGE
						: CmsAssetStore.TYPE_DOWNLOAD)
				.orElse(CmsAssetStore.TYPE_DOWNLOAD);
	}

	private ContentEntityDbo linkedProject(SmfDownloadQueryMapper.WikiProjectLinkRow link) {
		Integer projectId = idMap.lookupOrNull(LegacyEntityType.valueOf(link.getEntityType()), link.getLegacyId());
		return projectId == null ? null : contentEntityMapper.selectByPrimaryKey(projectId);
	}

	private void backfillProjectSummary(ContentEntityDbo project, String content) {
		if (project.getSummary() != null && !project.getSummary().isBlank()) {
			return;
		}
		String lead = CmsSupport.leadSummary(content);
		if (lead == null || lead.isBlank()) {
			return;
		}
		project.setSummary(lead);
		contentEntityMapper.updateByPrimaryKey(project);
	}

	private void backfillProjectMedia(SmfDownloadQueryMapper.WikiProjectLinkRow link, String content,
			CmsAssetStore assets, Path imagesRoot) {
		if (assets == null || imagesRoot == null) {
			return;
		}
		Integer projectId = idMap.lookupOrNull(LegacyEntityType.valueOf(link.getEntityType()), link.getLegacyId());
		if (projectId == null) {
			return;
		}
		ContentEntityDbo project = contentEntityMapper.selectByPrimaryKey(projectId);
		if (project == null) {
			return;
		}

		Integer bannerId = null;
		Matcher banner = FILE_REF.matcher(content);
		if (banner.find()) {
			bannerId = assets.store(CmsSupport.wikiImagePath(imagesRoot, banner.group(1).trim()), 1,
					CmsAssetStore.TYPE_IMAGE);
		}

		List<Integer> bodyImages = new ArrayList<>();
		Set<Integer> deduped = new HashSet<>();
		Matcher img = BODY_IMG.matcher(content);
		while (img.find()) {
			Integer contentId = Integer.valueOf(img.group(1));
			if (deduped.add(contentId)) {
				bodyImages.add(contentId);
			}
		}

		Integer previewId = bannerId != null ? bannerId : (bodyImages.isEmpty() ? null : bodyImages.get(0));
		if (previewId != null && project.getPreviewContentResourceId() == null) {
			project.setPreviewContentResourceId(previewId);
			contentEntityMapper.updateByPrimaryKey(project);
		}

		ProjectScreenshotDboExample wipeEx = new ProjectScreenshotDboExample();
		wipeEx.createCriteria().andContentEntityIdEqualTo(projectId).andMigrationHashLike("wiki:%");
		screenshotMapper.deleteByExample(wipeEx);
		ProjectScreenshotDboExample ssEx = new ProjectScreenshotDboExample();
		ssEx.createCriteria().andContentEntityIdEqualTo(projectId);
		if (!screenshotMapper.selectByExample(ssEx).isEmpty()) {
			return;
		}
		int ordinal = 0;
		for (Integer contentId : bodyImages) {
			if (contentId.equals(previewId)) {
				continue;
			}
			ProjectScreenshotDbo screenshot = new ProjectScreenshotDbo();
			screenshot.setContentEntityId(projectId);
			screenshot.setContentResourceId(contentId);
			screenshot.setOrdinal(ordinal++);
			screenshot.setMigrationHash("wiki:" + projectId + ":" + contentId);
			screenshotMapper.insert(screenshot);
		}
	}

	private void purgeMigratedRevisions(Integer wikiPageId) {
		WikiPageRevisionDboExample ex = new WikiPageRevisionDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId).andMigrationHashIsNotNull();
		revisionMapper.deleteByExample(ex);
	}

	private void publishTemplate(String pageTitle, Integer wikiPageId, String migratedBody) {
		String code = WikiTitle.normalizeTitle(CmsSupport.wikiTitleDisplay(pageTitle),
				JobContextHolder.getWikiNamespaceCaseMode(namespaceForRole(WikiNamespaceRole.TEMPLATE, "Template")));
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId).andContentFormatEqualTo("BBCODE");
		ContentTemplateDbo existing = contentTemplateMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			if (anEngineTemplateAlreadyOwns(code)) {
				logger.info("template '{}' is engine-owned; keeping the engine body and leaving the wiki page unlinked",
						code);
				return;
			}
			String body = migratedBody != null ? migratedBody : currentRevisionContent(wikiPageId);
			ContentTemplateDbo row = new ContentTemplateDbo();
			row.setCode(code);
			row.setContentFormat("BBCODE");
			row.setScope("WIKI");
			row.setBody(body == null ? "" : body);
			row.setWikiPageId(wikiPageId);
			contentTemplateMapper.insert(row);
			return;
		}
		if (existing.getWikiPageId() == null) {
			return;
		}
		boolean changed = false;
		if (migratedBody != null && !migratedBody.equals(existing.getBody())) {
			existing.setBody(migratedBody);
			changed = true;
		}
		if (changed) {
			contentTemplateMapper.updateByPrimaryKey(existing);
		}
	}

	private boolean anEngineTemplateAlreadyOwns(String code) {
		ContentTemplateDboExample engineOwned = new ContentTemplateDboExample();
		engineOwned.createCriteria().andWikiPageIdIsNull();
		return contentTemplateMapper.selectByExample(engineOwned).stream()
				.map(ContentTemplateDbo::getCode)
				.anyMatch(existing -> existing != null && existing.equalsIgnoreCase(code));
	}

	private String currentRevisionContent(Integer wikiPageId) {
		WikiPageRevisionDboExample ex = new WikiPageRevisionDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId).andCurrentFlagEqualTo(true);
		return revisionMapper.selectByExample(ex).stream().findFirst()
				.map(WikiPageRevisionDbo::getContent).orElse(null);
	}

	private static final Set<String> PLUMBING_CATEGORIES = Set.of(
			"Documentation subpages without corresponding pages",
			"Templates with print versions",
			"Templates generating microformats");

	private static boolean maintenanceCategory(String name) {
		return name.startsWith("Wikipedia ") || PLUMBING_CATEGORIES.contains(name);
	}

	private String remapUserIds(String content, String namespace, String pageTitle, RunContext runContext) {
		Matcher block = USER_PROFILE_BLOCK.matcher(content);
		StringBuilder out = new StringBuilder();
		Integer pageMember = memberByPageTitle(namespace, pageTitle, runContext);
		while (block.find()) {
			Matcher param = USERID_PARAM.matcher(block.group(1));
			StringBuilder params = new StringBuilder();
			while (param.find()) {
				Integer userId = param.group(1).isEmpty()
						? pageMember
						: runContext.urlMaps().userMap().get(Integer.parseInt(param.group(1)));
				if (userId == null) {
					userId = pageMember;
				}
				param.appendReplacement(params,
						userId != null ? "userid=" + userId : "");
			}
			param.appendTail(params);
			block.appendReplacement(out, Matcher.quoteReplacement(
					"[template=UserProfile]" + params + "[/template]"));
		}
		block.appendTail(out);
		return out.toString();
	}

	private Integer memberByPageTitle(String namespace, String pageTitle, RunContext runContext) {
		if (!"User".equals(namespace) || pageTitle == null) {
			return null;
		}
		Integer legacyId = runContext.legacyMembersByName().get(
				CmsSupport.wikiTitleDisplay(pageTitle).toLowerCase(Locale.ROOT));
		return legacyId == null ? null : runContext.urlMaps().userMap().get(legacyId);
	}

	private static String revAuthor(WikiRevisionRow rev) {
		String name = rev.getUserText();
		return name == null || name.isBlank() ? null : name;
	}

	private static String revSummary(WikiRevisionRow rev) {
		String comment = rev.getComment();
		if (comment == null || comment.isBlank()) {
			return null;
		}
		return comment.trim();
	}

	private void syncCategories(Integer wikiPageId, List<String> categories) {
		WikiPageCategoryDboExample ex = new WikiPageCategoryDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(wikiPageId);
		Set<String> existing = categoryMapper.selectByExample(ex).stream()
				.map(WikiPageCategoryDbo::getCategoryName)
				.collect(Collectors.toSet());
		Set<String> wanted = new HashSet<>(categories);
		if (existing.equals(wanted)) {
			return;
		}
		categoryMapper.deleteByExample(ex);
		for (String name : categories) {
			WikiPageCategoryDbo row = new WikiPageCategoryDbo();
			row.setWikiPageId(wikiPageId);
			row.setCategoryName(name);
			categoryMapper.insert(row);
		}
	}

	private String resolveImages(String content, CmsAssetStore assets, Path imagesRoot) {
		if (assets == null) {
			return content;
		}
		String result = content;
		if (result.contains("wiki-file:")) {
			Matcher wikiFileMatcher = WIKI_FILE.matcher(result);
			StringBuilder out = new StringBuilder();
			while (wikiFileMatcher.find()) {
				Integer id = assets.store(CmsSupport.wikiImagePath(imagesRoot, wikiFileMatcher.group(1)), 1, CmsAssetStore.TYPE_IMAGE);
				wikiFileMatcher.appendReplacement(out, Matcher.quoteReplacement(
						id != null ? "[img]/content/" + id + "[/img]" : wikiFileMatcher.group()));
			}
			wikiFileMatcher.appendTail(out);
			result = out.toString();
		}
		Matcher fileRef = FILE_REF.matcher(result);
		while (fileRef.find()) {
			assets.store(CmsSupport.wikiImagePath(imagesRoot, fileRef.group(1)), 1, CmsAssetStore.TYPE_IMAGE);
		}
		return result;
	}

	private Integer upsertPage(Integer legacyPageId, WikiPageDbo wikiPage) {
		Integer existingId = idMap.lookupOrNull(LegacyEntityType.WIKI_PAGE, legacyPageId);
		if (existingId == null) {
			WikiPageDboExample slugEx = new WikiPageDboExample();
			slugEx.createCriteria().andNamespaceEqualTo(wikiPage.getNamespace()).andSlugEqualTo(wikiPage.getSlug());
			existingId = wikiPageMapper.selectByExample(slugEx).stream().findFirst()
					.map(WikiPageDbo::getWikiPageId).orElse(null);
			if (existingId == null) {
				wikiPageMapper.insert(wikiPage);
				idMap.record(LegacyEntityType.WIKI_PAGE, legacyPageId, wikiPage.getWikiPageId());
				return wikiPage.getWikiPageId();
			}
			idMap.record(LegacyEntityType.WIKI_PAGE, legacyPageId, existingId);
		}
		WikiPageDbo existing = wikiPageMapper.selectByPrimaryKey(existingId);
		if (existing == null) {
			wikiPageMapper.insert(wikiPage);
			idMap.record(LegacyEntityType.WIKI_PAGE, legacyPageId, wikiPage.getWikiPageId());
			return wikiPage.getWikiPageId();
		}
		wikiPage.setWikiPageId(existingId);
		if (JobContextHolder.isForce() || !Objects.equals(existing.getMigrationHash(), wikiPage.getMigrationHash())) {
			wikiPageMapper.updateByPrimaryKey(wikiPage);
		}
		return existingId;
	}
}
