package com.zfgc.zfgbb.forum;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.MEMBER_ID;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.OTHER_ID;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.guest;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.member;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.moderator;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.readOnlyMember;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.roleHierarchy;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.siteAdmin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.authorization.RawSqlAccess;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dataprovider.users.UserProfileFacade;
import com.zfgc.zfgbb.dbo.UserAggregateDbo;
import com.zfgc.zfgbb.mappers.custom.UserProfileHydrationMapper;
import com.zfgc.zfgbb.services.core.GuestPermissionService;
import com.zfgc.zfgbb.config.loadoption.user.BasicUserLoadOptions;
import com.zfgc.zfgbb.content.renderer.ContentRenderer;
import com.zfgc.zfgbb.dao.UserPermissionViewDao;
import com.zfgc.zfgbb.dao.users.AvatarDao;
import com.zfgc.zfgbb.dao.users.UserBioInfoDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dataprovider.cms.ProjectDataProvider;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper;
import com.zfgc.zfgbb.mappers.ProjectNewsDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.UserReactionSummaryViewDboMapper;
import com.zfgc.zfgbb.mapstruct.users.AvatarMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.mapstruct.users.UserBioInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.ProjectNews;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.users.UserBioInfo;
import com.zfgc.zfgbb.services.forum.ForumAccessRules;
import com.zfgc.zfgbb.services.forum.ForumAccessRules.MessageState;
import com.zfgc.zfgbb.services.forum.ForumAccessRules.ThreadState;
import com.zfgc.zfgbb.services.forum.ForumService;

class PolicyTest {

	@Nested
	class ModerationRules {

		private final ForumAccessRules rules = new ForumAccessRules(new AuthorityTiers(roleHierarchy()), null, null,
				null);

		private final User member = member();
		private final User moderator = moderator();
		private final User admin = siteAdmin();
		private final User readOnlyMember = readOnlyMember();
		private final User guest = guest();

		private ThreadState openThread(int ownerUserId) {
			return new ThreadState(3, 2, ownerUserId, false, false);
		}

		private ThreadState lockedThread(int ownerUserId) {
			return new ThreadState(3, 2, ownerUserId, true, false);
		}

		private ThreadState recycledThread(int ownerUserId) {
			return new ThreadState(3, 2, ownerUserId, false, true);
		}

		private MessageState message(int ownerUserId, ThreadState thread) {
			return new MessageState(5, ownerUserId, thread);
		}

		@Test
		void replyRequiresWriteOnOpenUnlockedNonRecycledThread() {
			assertTrue(rules.canReplyToThread(member, openThread(OTHER_ID)));
			assertTrue(rules.canReplyToThread(admin, openThread(OTHER_ID)));
			assertFalse(rules.canReplyToThread(guest, openThread(OTHER_ID)));
			assertFalse(rules.canReplyToThread(readOnlyMember, openThread(OTHER_ID)));
			assertFalse(rules.canReplyToThread(member, recycledThread(OTHER_ID)));
		}

		@Test
		void onlyModeratorsReplyToLockedThreads() {
			assertFalse(rules.canReplyToThread(member, lockedThread(OTHER_ID)));
			assertTrue(rules.canReplyToThread(moderator, lockedThread(OTHER_ID)));
			assertTrue(rules.canReplyToThread(admin, lockedThread(OTHER_ID)));
		}

		@Test
		void ownerDeletesOwnMessageOnlyWhileThreadOpen() {
			assertTrue(rules.canDeleteMessage(member, message(MEMBER_ID, openThread(MEMBER_ID))));
			assertFalse(rules.canDeleteMessage(member, message(MEMBER_ID, lockedThread(MEMBER_ID))));
			assertFalse(rules.canDeleteMessage(member, message(MEMBER_ID, recycledThread(MEMBER_ID))));
			assertFalse(rules.canDeleteMessage(member, message(OTHER_ID, openThread(OTHER_ID))));
			assertFalse(rules.canDeleteMessage(readOnlyMember, message(13, openThread(13))));
		}

		@Test
		void moderatorDeletesAnyMessageRegardlessOfLockOrOwnership() {
			assertTrue(rules.canDeleteMessage(moderator, message(OTHER_ID, lockedThread(OTHER_ID))));
			assertTrue(rules.canDeleteMessage(moderator, message(OTHER_ID, recycledThread(OTHER_ID))));
			assertTrue(rules.canDeleteMessage(admin, message(OTHER_ID, lockedThread(OTHER_ID))));
		}

		@Test
		void restoreRequiresModeratorAndRecycledState() {
			assertTrue(rules.canRestoreThread(moderator, recycledThread(OTHER_ID)));
			assertFalse(rules.canRestoreThread(moderator, openThread(OTHER_ID)));
			assertFalse(rules.canRestoreThread(member, recycledThread(OTHER_ID)));
			assertTrue(rules.canRestoreMessage(admin, message(OTHER_ID, recycledThread(OTHER_ID))));
			assertFalse(rules.canRestoreMessage(moderator, message(OTHER_ID, openThread(OTHER_ID))));
		}

		@Test
		void forumModeratorRecognizesModeratorAndAdminTiers() {
			assertTrue(rules.isForumModerator(moderator));
			assertTrue(rules.isForumModerator(admin));
			assertFalse(rules.isForumModerator(member));
			assertFalse(rules.isForumModerator(guest));
		}
	}

	@Nested
	class RestoreProvenance {
		@Test
		void restoreLocksHighWrapperAndLowOriginInAscendingOrder() {
			assertEquals(List.of(7, 42), ForumService.orderedDistinctIds(List.of(42, 7, 42)));
		}

		@Test
		void restoreRejectsChangedWrapperProvenance() {
			Thread wrapper = new Thread();
			wrapper.setBoardId(9);
			wrapper.setRecycledFromThreadId(7);
			Thread origin = new Thread();
			origin.setThreadId(8);
			assertFalse(ForumService.restoreProvenanceMatches(wrapper, origin, 9));
			origin.setThreadId(7);
			assertTrue(ForumService.restoreProvenanceMatches(wrapper, origin, 9));
		}
	}

	@Nested
	class QueryPartitioning {
		@Test
		void emptyIdsProduceNoChunks() {
			assertEquals(List.of(), ForumService.partition(List.of(), 10000));
		}

		@Test
		void smallIdListStaysSingleChunk() {
			List<Integer> ids = List.of(1, 2, 3);
			List<List<Integer>> chunks = ForumService.partition(ids, 10000);
			assertEquals(1, chunks.size());
			assertEquals(ids, chunks.get(0));
		}

		@Test
		void oversizedIdListIsSplitUnderTheBindParamLimit() {
			List<Integer> ids = IntStream.range(0, 165_000).boxed().toList();
			List<List<Integer>> chunks = ForumService.partition(ids, 10000);
			assertEquals(17, chunks.size());
			int total = 0;
			for (List<Integer> chunk : chunks) {
				assertTrue(chunk.size() <= 10000, "chunk exceeds Postgres bind-param safe size");
				total += chunk.size();
			}
			assertEquals(ids.size(), total);
			assertEquals(Integer.valueOf(0), chunks.get(0).get(0));
			assertEquals(Integer.valueOf(164_999),
					chunks.get(chunks.size() - 1).get(chunks.get(chunks.size() - 1).size() - 1));
		}
	}

	@Nested
	class NewsVisibility {

		private static final int PROJECT_ID = 1;
		private static final int PUBLIC_THREAD_ID = 11;
		private static final int HIDDEN_THREAD_ID = 12;

		private ProjectNewsDboMapper newsMapper;
		private ThreadDboMapper threadMapper;
		private BoardPermissionViewDboMapper boardPermissionViewDboMapper;
		private ProjectDataProvider provider;

		@BeforeEach
		void setup() {
			newsMapper = mock(ProjectNewsDboMapper.class);
			threadMapper = mock(ThreadDboMapper.class);
			boardPermissionViewDboMapper = mock(BoardPermissionViewDboMapper.class);
			provider = new ProjectDataProvider();
			GuestPermissionService guestPermissionService = new GuestPermissionService();
			ReflectionTestUtils.setField(guestPermissionService, "boardPermissionViewDboMapper", boardPermissionViewDboMapper);
			ReflectionTestUtils.setField(provider, "guestPermissionService", guestPermissionService);
			ReflectionTestUtils.setField(provider, "newsMapper", newsMapper);
			ReflectionTestUtils.setField(provider, "threadMapper", threadMapper);
			when(newsMapper.selectByExample(any()))
					.thenReturn(List.of(newsRow(PUBLIC_THREAD_ID), newsRow(HIDDEN_THREAD_ID)));
		}

		private static ProjectNewsDbo newsRow(int threadId) {
			ProjectNewsDbo row = new ProjectNewsDbo();
			row.setThreadId(threadId);
			return row;
		}

		private static ProjectNews entryFor(List<ProjectNews> news, int threadId) {
			return news.stream().filter(entry -> Integer.valueOf(threadId).equals(entry.getThreadId()))
					.findFirst().orElseThrow();
		}

		@Test
		void emptyGuestBoardSetSkipsThreadLookup() {
			when(boardPermissionViewDboMapper.selectByExample(any())).thenReturn(List.of());
			List<ProjectNews> news = provider.getProjectNews(PROJECT_ID);
			verify(threadMapper, never()).selectByExample(any());
			assertEquals(2, news.size());
			for (ProjectNews entry : news)
				assertNull(entry.getThreadName());
			assertEquals(Integer.valueOf(PUBLIC_THREAD_ID), entryFor(news, PUBLIC_THREAD_ID).getThreadId());
			assertEquals(Integer.valueOf(HIDDEN_THREAD_ID), entryFor(news, HIDDEN_THREAD_ID).getThreadId());
		}

		@Test
		void hiddenBoardThreadTitleNotResolved() {
			BoardPermissionViewDbo perm = new BoardPermissionViewDbo();
			perm.setBoardId(1);
			when(boardPermissionViewDboMapper.selectByExample(any())).thenReturn(List.of(perm));
			ThreadDbo publicThread = new ThreadDbo();
			publicThread.setThreadId(PUBLIC_THREAD_ID);
			publicThread.setThreadName("PUBLIC_TITLE");
			when(threadMapper.selectByExample(any())).thenReturn(List.of(publicThread));
			ArgumentCaptor<ThreadDboExample> exampleCaptor = ArgumentCaptor.forClass(ThreadDboExample.class);

			List<ProjectNews> news = provider.getProjectNews(PROJECT_ID);

			verify(threadMapper).selectByExample(exampleCaptor.capture());
			ProjectNews visible = entryFor(news, PUBLIC_THREAD_ID);
			ProjectNews hidden = entryFor(news, HIDDEN_THREAD_ID);
			assertEquals("PUBLIC_TITLE", visible.getThreadName());
			assertNull(hidden.getThreadName());
			assertEquals(Integer.valueOf(PUBLIC_THREAD_ID), visible.getThreadId());
			assertEquals(Integer.valueOf(HIDDEN_THREAD_ID), hidden.getThreadId());
			List<String> conditions = exampleCaptor.getValue().getOredCriteria().get(0).getAllCriteria().stream()
					.map(ThreadDboExample.Criterion::getCondition).toList();
			assertTrue(conditions.stream().anyMatch(condition -> condition.contains("board_id in")));
		}
	}

	@Nested
	class PostCountVisibility {

		private static final int USER_ID = 100;

		private UserDao userDao;
		private UserBioInfoDao bioInfoDao;
		private BoardPermissionViewDboMapper boardPermissionViewDboMapper;
		private MessageDboMapper messageDboMapper;
		private MessagePostCountMapper messagePostCountMapper;
		private ContentRenderer contentRenderer;
		private UserBioInfoMap userBioInfoMap;
		private UserMap userMap;
		private UserDataProvider provider;

		private final BasicUserLoadOptions bioOnly = new BasicUserLoadOptions() {
			@Override
			public boolean loadAvatar() {
				return false;
			}

			@Override
			public boolean loadReactions() {
				return false;
			}
		};

		@BeforeEach
		void setup() {
			userDao = mock(UserDao.class);
			bioInfoDao = mock(UserBioInfoDao.class);
			boardPermissionViewDboMapper = mock(BoardPermissionViewDboMapper.class);
			messageDboMapper = mock(MessageDboMapper.class);
			messagePostCountMapper = mock(MessagePostCountMapper.class);
			contentRenderer = mock(ContentRenderer.class);
			userBioInfoMap = mock(UserBioInfoMap.class);
			userMap = mock(UserMap.class);
			provider = new UserDataProvider();
			UserProfileFacade userProfileFacade = new UserProfileFacade();
			GuestPermissionService guestPermissionService = new GuestPermissionService();
			UserProfileHydrationMapper userProfileHydrationMapper = mock(UserProfileHydrationMapper.class);
			ReflectionTestUtils.setField(userProfileFacade, "messagePostCountMapper", messagePostCountMapper);
			ReflectionTestUtils.setField(guestPermissionService, "boardPermissionViewDboMapper", boardPermissionViewDboMapper);
			ReflectionTestUtils.setField(userProfileFacade, "guestPermissionService", guestPermissionService);
			ReflectionTestUtils.setField(userProfileFacade, "userProfileHydrationMapper", userProfileHydrationMapper);
			ReflectionTestUtils.setField(userProfileFacade, "contentRenderer", contentRenderer);
			ReflectionTestUtils.setField(userProfileFacade, "userBioInfoMap", userBioInfoMap);
			ReflectionTestUtils.setField(userProfileFacade, "userMap", userMap);
			ReflectionTestUtils.setField(provider, "userProfileFacade", userProfileFacade);
			ReflectionTestUtils.setField(provider, "userDao", userDao);
			ReflectionTestUtils.setField(provider, "bioInfoDao", bioInfoDao);
			ReflectionTestUtils.setField(provider, "contentRenderer", contentRenderer);
			ReflectionTestUtils.setField(provider, "userBioInfoMap", userBioInfoMap);
			ReflectionTestUtils.setField(provider, "userMap", userMap);

			UserDbo userDbo = new UserDbo();
			userDbo.setUserId(USER_ID);
			UserBioInfoDbo bioDbo = new UserBioInfoDbo();
			UserAggregateDbo agg = new UserAggregateDbo();
			agg.setUser(userDbo);
			agg.setBio(bioDbo);
			when(userProfileHydrationMapper.hydrateUsers(any())).thenReturn(List.of(agg));
			when(userDao.get(any(UserDboExample.class))).thenReturn(List.of(userDbo));
			when(bioInfoDao.get(USER_ID)).thenReturn(Optional.of(new UserBioInfoDbo()));
			when(userBioInfoMap.toModel(any())).thenReturn(new UserBioInfo());
			when(userMap.toModel(any())).thenReturn(new User());
		}

		private UserBioInfo loadBio() {
			return provider.findUser(USER_ID, bioOnly).orElseThrow().getBioInfo();
		}

		@Test
		@SuppressWarnings("unchecked")
		void guestBaselineUsesFullGuestPermissionSet() {
			BoardPermissionViewDbo perm = new BoardPermissionViewDbo();
			perm.setBoardId(1);
			when(boardPermissionViewDboMapper.selectByExample(any())).thenReturn(List.of(perm));
			ArgumentCaptor<com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample> exampleCaptor = ArgumentCaptor.forClass(com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample.class);
			loadBio();
			verify(boardPermissionViewDboMapper).selectByExample(exampleCaptor.capture());
			List<String> conditions = exampleCaptor.getValue().getOredCriteria().get(0).getAllCriteria().stream()
					.map(com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample.Criterion::getCondition).toList();
			assertTrue(conditions.stream().anyMatch(condition -> condition.contains("permission_id in")));
		}

		@Test
		void emptyGuestBoardSetYieldsZeroWithoutQuery() {
			when(boardPermissionViewDboMapper.selectByExample(any())).thenReturn(List.of());
			UserBioInfo bio = loadBio();
			assertEquals(0, bio.getPostCount().intValue());
			verify(messageDboMapper, never()).countByExample(any());
		}

		@Test
		void nonEmptyGuestBoardSetFiltersByOwnerAndBoard() {
			BoardPermissionViewDbo perm1 = new BoardPermissionViewDbo();
			perm1.setBoardId(1);
			BoardPermissionViewDbo perm2 = new BoardPermissionViewDbo();
			perm2.setBoardId(2);
			when(boardPermissionViewDboMapper.selectByExample(any())).thenReturn(List.of(perm1, perm2));
			MessagePostCountMapper.OwnerPostCount count = new MessagePostCountMapper.OwnerPostCount();
			count.setOwnerId(USER_ID);
			count.setPostCount(3L);
			when(messagePostCountMapper.postCountsByOwnerWithinBoards(any(), any())).thenReturn(List.of(count));
			UserBioInfo bio = loadBio();
			assertEquals(3, bio.getPostCount().intValue());
			verify(messagePostCountMapper).postCountsByOwnerWithinBoards(any(), any());
		}
	}

	@Nested
	class BatchAuthorLoading {

		private UserDao userDao;
		private UserPermissionViewDao userPermissionDao;
		private UserBioInfoDao bioInfoDao;
		private AvatarDao avatarDao;
		private UserReactionSummaryViewDboMapper reactionSummaryMapper;
		private MessagePostCountMapper messagePostCountMapper;
		private UserProfileHydrationMapper userProfileHydrationMapper;
		private BoardPermissionViewDboMapper boardPermissionViewDboMapper;
		private ContentRenderer contentRenderer;
		private UserMap userMap;
		private UserBioInfoMap userBioInfoMap;
		private PermissionMap permissionMap;
		private AvatarMap avatarMap;
		private UserDataProvider provider;

		@BeforeEach
		void setup() {
			userDao = mock(UserDao.class);
			userPermissionDao = mock(UserPermissionViewDao.class);
			bioInfoDao = mock(UserBioInfoDao.class);
			avatarDao = mock(AvatarDao.class);
			reactionSummaryMapper = mock(UserReactionSummaryViewDboMapper.class);
			messagePostCountMapper = mock(MessagePostCountMapper.class);
			boardPermissionViewDboMapper = mock(BoardPermissionViewDboMapper.class);
			contentRenderer = mock(ContentRenderer.class);
			userMap = mock(UserMap.class);
			userBioInfoMap = mock(UserBioInfoMap.class);
			permissionMap = mock(PermissionMap.class);
			avatarMap = mock(AvatarMap.class);
			provider = new UserDataProvider();
			UserProfileFacade userProfileFacade = new UserProfileFacade();
			GuestPermissionService guestPermissionService = new GuestPermissionService();
			userProfileHydrationMapper = mock(UserProfileHydrationMapper.class);
			ReflectionTestUtils.setField(guestPermissionService, "boardPermissionViewDboMapper", boardPermissionViewDboMapper);
			ReflectionTestUtils.setField(userProfileFacade, "guestPermissionService", guestPermissionService);
			ReflectionTestUtils.setField(userProfileFacade, "userProfileHydrationMapper", userProfileHydrationMapper);
			ReflectionTestUtils.setField(userProfileFacade, "contentRenderer", contentRenderer);
			when(userProfileHydrationMapper.hydrateUsers(any())).thenAnswer(invocation -> {
				List<Integer> userIds = invocation.getArgument(0);
				return userIds.stream().map(userId -> {
					UserDbo dbo = new UserDbo();
					dbo.setUserId(userId);
					UserAggregateDbo agg = new UserAggregateDbo();
					agg.setUser(dbo);
					return agg;
				}).toList();
			});
			ReflectionTestUtils.setField(userProfileFacade, "userPermissionDao", userPermissionDao);
			ReflectionTestUtils.setField(userProfileFacade, "reactionSummaryMapper", reactionSummaryMapper);
			ReflectionTestUtils.setField(userProfileFacade, "messagePostCountMapper", messagePostCountMapper);
			ReflectionTestUtils.setField(userProfileFacade, "userMap", userMap);
			ReflectionTestUtils.setField(userProfileFacade, "userBioInfoMap", userBioInfoMap);
			ReflectionTestUtils.setField(userProfileFacade, "avatarMap", avatarMap);
			ReflectionTestUtils.setField(provider, "userProfileFacade", userProfileFacade);
			ReflectionTestUtils.setField(provider, "userDao", userDao);
			ReflectionTestUtils.setField(provider, "userPermissionDao", userPermissionDao);
			ReflectionTestUtils.setField(provider, "bioInfoDao", bioInfoDao);
			ReflectionTestUtils.setField(provider, "avatarDao", avatarDao);
			ReflectionTestUtils.setField(provider, "contentRenderer", contentRenderer);
			ReflectionTestUtils.setField(provider, "userMap", userMap);
			ReflectionTestUtils.setField(provider, "userBioInfoMap", userBioInfoMap);

			BoardPermissionViewDbo perm = new BoardPermissionViewDbo();
			perm.setBoardId(1);
			when(boardPermissionViewDboMapper.selectByExample(any())).thenReturn(List.of(perm));
			when(userMap.toModel(any())).thenReturn(new User());
			when(userBioInfoMap.toModel(any())).thenReturn(new UserBioInfo());
		}

		private void primeSubEntitiesFor(List<Integer> userIds) {
			List<UserDbo> userDbos = userIds.stream().map(userId -> {
				UserDbo userDbo = new UserDbo();
				userDbo.setUserId(userId);
				return userDbo;
			}).toList();
			List<UserBioInfoDbo> bioDbos = userIds.stream().map(userId -> {
				UserBioInfoDbo bioDbo = new UserBioInfoDbo();
				bioDbo.setUserId(userId);
				bioDbo.setAvatarId(500 + userId);
				return bioDbo;
			}).toList();
			when(userDao.get(any(UserDboExample.class))).thenReturn(userDbos);
			when(bioInfoDao.get(any(UserBioInfoDboExample.class))).thenReturn(bioDbos);
			when(userPermissionDao.get(any(UserPermissionViewDboExample.class))).thenReturn(List.of());
			when(avatarDao.get(any(AvatarDboExample.class))).thenReturn(List.of());
			when(reactionSummaryMapper.selectByExample(any())).thenReturn(List.of());
			when(messagePostCountMapper.postCountsByOwnerWithinBoards(any(), any())).thenReturn(List.of());
		}

		private void verifyExactlyOneQueryPerSubEntity() {
			verify(userProfileHydrationMapper, times(1)).hydrateUsers(any());
			verify(reactionSummaryMapper, times(1)).selectByExample(any());
			verify(messagePostCountMapper, times(1)).postCountsByOwnerWithinBoards(any(), any());
			verify(boardPermissionViewDboMapper, times(1)).selectByExample(any());
		}

		@Test
		void singleAuthorIssuesOneQueryPerSubEntity() {
			primeSubEntitiesFor(List.of(100));
			provider.findPublicAuthorsByIds(List.of(100));
			verifyExactlyOneQueryPerSubEntity();
		}

		@Test
		void manyAuthorsStillIssueOneQueryPerSubEntity() {
			primeSubEntitiesFor(List.of(100, 101, 102, 103, 104));
			provider.findPublicAuthorsByIds(List.of(100, 101, 102, 103, 104));
			verifyExactlyOneQueryPerSubEntity();
		}
	}

	@Nested
	class ReadChokepoint {

		private static final Set<String> RAW_BOARD_READ_ACCESSOR_NAMES = Set.of(
				"com.zfgc.zfgbb.dao.BoardDao",
				"com.zfgc.zfgbb.dao.ThreadDao",
				"com.zfgc.zfgbb.dao.forum.MessageDao",
				"com.zfgc.zfgbb.dao.forum.MessageHistoryDao",
				"com.zfgc.zfgbb.dao.forum.CurrentMessageDao",
				"com.zfgc.zfgbb.mappers.BoardDboMapper",
				"com.zfgc.zfgbb.mappers.ThreadDboMapper",
				"com.zfgc.zfgbb.mappers.MessageDboMapper",
				"com.zfgc.zfgbb.mappers.MessageHistoryDboMapper",
				"com.zfgc.zfgbb.mappers.CurrentMessageDboMapper",
				"com.zfgc.zfgbb.mappers.BoardSummaryViewDboMapper",
				"com.zfgc.zfgbb.mappers.ChildBoardViewDboMapper",
				"com.zfgc.zfgbb.mappers.LatestMessageInThreadViewDboMapper",
				"com.zfgc.zfgbb.mappers.AllMessagesInThreadViewDboMapper",
				"com.zfgc.zfgbb.mappers.RecentActivityViewDboMapper",
				"com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper");

		private static final DescribedPredicate<JavaClass> areRawUnfilteredBoardReadAccessors =
				new DescribedPredicate<JavaClass>("raw unfiltered board/thread/message read accessors") {
					@Override
					public boolean test(JavaClass javaClass) {
						return RAW_BOARD_READ_ACCESSOR_NAMES.contains(javaClass.getFullName());
					}
				};

		@Test
		void rawBoardReadsOnlyThroughFilteredPathOrAnnotatedReaders() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			ArchRule rule = noClasses()
					.that().resideOutsideOfPackages(
							"com.zfgc.zfgbb.migrator..",
							"com.zfgc.zfgbb.dao..",
							"com.zfgc.zfgbb.services.forum..",
							"com.zfgc.zfgbb.services.search..",
							"com.zfgc.zfgbb.dataprovider.forum..")
					.and().areNotAnnotatedWith(UnfilteredBoardRead.class)
					.should().dependOnClassesThat(areRawUnfilteredBoardReadAccessors)
					.as("board/thread/message content must be read only through the board-visibility-filtered chokepoint")
					.because("raw unfiltered reads of board/thread/message can leak hidden-board content; "
							+ "route the read through ForumService or SearchService, or annotate the class with "
							+ "@UnfilteredBoardRead(\"honest reason\") when the unfiltered access is deliberate and safe");

			rule.check(appClasses);
		}
	}

	@Nested
	class PersistenceMechanism {

		@Test
		void rawJdbcTemplateOnlyThroughAnnotatedRepositories() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			ArchRule rule = noClasses()
					.that().resideOutsideOfPackages("com.zfgc.zfgbb.migrator..")
					.and().areNotAnnotatedWith(RawSqlAccess.class)
					.should().dependOnClassesThat().areAssignableTo(JdbcTemplate.class)
					.orShould().dependOnClassesThat().areAssignableTo(NamedParameterJdbcTemplate.class)
					.as("plain single-table equality/IN CRUD against the zfgbb schema must use the generated "
							+ "*DboMapper/*DboExample, never raw JdbcTemplate")
					.because("raw JdbcTemplate against the zfgbb target schema is banned; hand SQL is allowed only "
							+ "for CAS / FOR UPDATE / null-set / ON CONFLICT / aggregates / joins / isGeneratedAlways / "
							+ "legacy-SMF-source, and such a class must declare @RawSqlAccess(\"honest reason\")");

			rule.check(appClasses);
		}
	}
}
