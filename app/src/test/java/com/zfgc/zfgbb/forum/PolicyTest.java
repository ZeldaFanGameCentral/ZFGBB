package com.zfgc.zfgbb.forum;

import com.zfgc.zfgbb.dao.users.UserRefreshTokenDao;
import com.zfgc.zfgbb.model.Securable;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noConstructors;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import com.zfgc.zfgbb.persistence.RawSqlAccess;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.annotation.PostConstruct;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaFieldAccess;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.exception.ZfgcUnauthorizedException;
import com.zfgc.zfgbb.testsupport.RawSqlIdentifiers;
import com.zfgc.zfgbb.dataprovider.users.GuestPermissionDataProvider;
import com.zfgc.zfgbb.dataprovider.users.UserProfileFacade;
import com.zfgc.zfgbb.dbo.UserAggregateDbo;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dataprovider.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.content.renderer.ContentOutputSanitizer;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.templates.ContentTemplateCatalog;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeRenderer;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import com.zfgc.zfgbb.dao.users.BrUserPermissionDao;
import com.zfgc.zfgbb.dao.users.EmailAddressDao;
import com.zfgc.zfgbb.dao.users.UserBioInfoDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dataprovider.cms.ProjectDataProvider;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dao.forum.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.users.AwardDao;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper;
import com.zfgc.zfgbb.dao.cms.ProjectNewsDao;
import com.zfgc.zfgbb.dao.users.AccountDeletionAuditDao;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dao.users.UserErasureDao;
import com.zfgc.zfgbb.dao.users.UserPermissionGroupAssocDao;
import com.zfgc.zfgbb.dao.users.AvatarDao;
import com.zfgc.zfgbb.dao.meta.MigratorIdMapDao;
import com.zfgc.zfgbb.dao.users.UserAwardDao;
import com.zfgc.zfgbb.dao.users.UserAwardDao;
import com.zfgc.zfgbb.dao.users.UserContactInfoDao;
import com.zfgc.zfgbb.dao.users.UserPermissionViewDao;
import com.zfgc.zfgbb.dao.users.UserReactionSummaryViewDao;
import com.zfgc.zfgbb.dao.users.UserSettingsDao;
import com.zfgc.zfgbb.mapstruct.users.AvatarMap;
import com.zfgc.zfgbb.mapstruct.users.EmailAddressMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.mapstruct.users.UserBioInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserContactInfoMap;
import com.zfgc.zfgbb.mapstruct.users.AwardMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.mapstruct.users.UserSettingsMap;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.mapstruct.cms.ProjectMapImpl;
import com.zfgc.zfgbb.model.cms.ProjectNews;
import com.zfgc.zfgbb.model.forum.Board;
import com.zfgc.zfgbb.model.forum.BoardSummary;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.users.UserBioInfo;
import com.zfgc.zfgbb.services.AbstractService;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules.MessageState;
import com.zfgc.zfgbb.authorization.access.ForumAccessRules.ThreadState;
import com.zfgc.zfgbb.services.forum.ForumModerationOrchestrator;
import com.zfgc.zfgbb.dataprovider.forum.MessageDataProvider;
import com.zfgc.zfgbb.services.forum.ForumService;

class PolicyTest {

	@Nested
	class DependencyInjectionArchitecture {

		@Test
		void productionCodeUsesConstructorInjectionWithoutAutowiredAnnotations() {
			JavaClasses productionClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			noFields().should().beAnnotatedWith(Autowired.class).check(productionClasses);
			noConstructors().should().beAnnotatedWith(Autowired.class).check(productionClasses);
		}

		@Test
		void renderingLaneInternalsAreReachedOnlyThroughTheContentRenderingFrontDoor() {
			JavaClasses productionClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			noClasses().that().resideOutsideOfPackage("com.zfgc.zfgbb.content.renderer..")
					.should().dependOnClassesThat(JavaClass.Predicates.belongToAnyOf(
							BBCodeRenderer.class, MarkdownRenderer.class, BBCodeGrammarHolder.class,
							ContentOutputSanitizer.class, RenderedTextEnricher.class))
					.as("rendering-lane internals are reached only through the ContentRenderingService front door")
					.because("BBCodeRenderer.render returns unsanitized HTML and the grammar holder exposes raw "
							+ "grammar state; both are safe only behind the front door's sanitize chokepoint and "
							+ "quote scope, so a caller wiring them directly bypasses the sanitizer")
					.check(productionClasses);
		}

		@Test
		void productionCodeNeverRendersWithoutNamingTheSurfaceItRendersFor() {
			JavaClasses productionClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			ArchCondition<JavaClass> reachForTheWildcardSurface =
					new ArchCondition<>("reach for ContentScope.ALL") {
						@Override
						public void check(JavaClass reaching, ConditionEvents events) {
							if (reaching.getModifiers().contains(JavaModifier.SYNTHETIC))
								return;
							for (JavaFieldAccess access : reaching.getFieldAccessesFromSelf()) {
								if (!ContentScope.class.getName().equals(
										access.getTargetOwner().getFullName())
										|| !"ALL".equals(access.getTarget().getName()))
									continue;
								if (access.getOrigin().getName().startsWith("$SWITCH_TABLE$"))
									continue;
								events.add(SimpleConditionEvent.satisfied(reaching, access.getDescription()));
							}
						}
					};

			noClasses().that().doNotBelongToAnyOf(ContentScope.class, ContentTemplateCatalog.class)
					.should(reachForTheWildcardSurface)
					.as("production code never renders without naming the surface it renders for")
					.because("a switch over the enum is fine -- the compiler's synthetic switch table reads every "
							+ "constant -- but ContentScope.ALL is a wildcard a template row stores rather than a surface anything "
							+ "renders on; a caller that reaches it resolves every surface-scoped template to "
							+ "nothing and, once codes carry per-surface flags, silently escapes scoping "
							+ "altogether. ContentTemplateCatalog is exempt because comparing a stored scope "
							+ "against the wildcard is what the wildcard is for")
					.check(productionClasses);
		}

		@Test
		void testMappersBindValuesInsteadOfInterpolatingThem() {
			JavaClasses testMappers = new ClassFileImporter()
					.importPackages("com.zfgc.zfgbb.testsupport.mappers");

			methods().that(new DescribedPredicate<JavaMethod>("declare MyBatis SQL") {
				@Override
				public boolean test(JavaMethod method) {
					return !statementsOf(method).isEmpty();
				}
			}).should(new ArchCondition<JavaMethod>(
					"bind every value with #{} unless @RawSqlIdentifiers justifies an identifier") {
				@Override
				public void check(JavaMethod method, ConditionEvents events) {
					boolean interpolates = statementsOf(method).stream().anyMatch(sql -> sql.contains("${"));
					if (interpolates && !method.isAnnotatedWith(RawSqlIdentifiers.class))
						events.add(SimpleConditionEvent.violated(method, method.getFullName()
								+ " interpolates SQL with ${}; bind the value with #{} or, if it is genuinely"
								+ " an identifier, annotate the method with @RawSqlIdentifiers"));
				}
			}).check(testMappers);
		}

		private static final Set<String> SPRING_BEAN_SENTINELS = Set.of(
				"com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader",
				"com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder",
				"com.zfgc.zfgbb.content.renderer.bbcode.BBCodeRenderer",
				"com.zfgc.zfgbb.content.renderer.ContentOutputSanitizer",
				"com.zfgc.zfgbb.content.renderer.RenderedTextEnricher",
				"com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer",
				"com.zfgc.zfgbb.services.cms.wiki.WikiService",
				"com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider");

		private static boolean isSpringBean(JavaClass javaClass) {
			return javaClass.isAnnotatedWith(Component.class) || javaClass.isMetaAnnotatedWith(Component.class);
		}

		private static JavaClass topLevelClassOf(JavaClass javaClass) {
			JavaClass current = javaClass;
			while (current.getEnclosingClass().isPresent())
				current = current.getEnclosingClass().get();
			return current;
		}

		@Test
		void springBeansReachIntoOneAnotherOnlyThroughMethodCalls() {
			JavaClasses productionAndTestClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.importPackages("com.zfgc.zfgbb");

			Set<String> resolvedBeans = new HashSet<>();
			for (JavaClass javaClass : productionAndTestClasses)
				if (isSpringBean(javaClass))
					resolvedBeans.add(javaClass.getFullName());
			assertTrue(resolvedBeans.containsAll(SPRING_BEAN_SENTINELS),
					"springBeansReachIntoOneAnotherOnlyThroughMethodCalls resolved " + resolvedBeans.size()
							+ " Spring beans and is missing at least one sentinel. The rule only reports a field "
							+ "access whose OWNER is a resolved bean, so a stereotype this predicate stops "
							+ "recognising -- @Service and @Repository are only reachable through the meta "
							+ "annotation -- would leave the rule green while guarding nothing. Resolved: "
							+ resolvedBeans);

			ArchRule rule = classes()
					.should(new ArchCondition<JavaClass>(
							"reach another Spring bean's instance state only through its methods") {
						@Override
						public void check(JavaClass javaClass, ConditionEvents events) {
							for (JavaFieldAccess access : javaClass.getFieldAccessesFromSelf()) {
								JavaClass owner = access.getTargetOwner();
								if (!isSpringBean(owner))
									continue;
								if (access.getTarget().resolveMember()
										.map(field -> field.getModifiers().contains(JavaModifier.STATIC))
										.orElse(true))
									continue;
								if (topLevelClassOf(javaClass).equals(topLevelClassOf(owner)))
									continue;
								events.add(SimpleConditionEvent.violated(access, access.getDescription()
										+ "; call an accessor on " + owner.getSimpleName()
										+ " instead of reading its field"));
							}
						}
					})
					.as("a Spring bean's instance state is reached through its methods, never through a field")
					.because("Spring wraps a bean carrying @Transactional in a CGLIB subclass whose own fields are "
							+ "never initialised: a method call on the proxy delegates to the target, but a field "
							+ "read resolves against the proxy's null field, so the collaborator silently reads null "
							+ "in production while every unit test that constructs the bean directly still passes; "
							+ "expose the state as an accessor and call it. Tests are in scope because a test that "
							+ "assigns or reads a bean's field is what keeps that field widened past private, and a "
							+ "field the test lane can reach is a field production can reach too");

			rule.check(productionAndTestClasses);
		}

		@Test
		void noBeanReadsThePublishedGrammarBeforeTheContextFinishesBuilding() {
			JavaClasses productionClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			List<String> readers = new ArrayList<>();
			List<String> readWhileTheBeanIsStillBeingBuilt = new ArrayList<>();
			for (JavaClass javaClass : productionClasses)
				for (JavaCodeUnit codeUnit : javaClass.getCodeUnits()) {
					if (codeUnit.getMethodCallsFromSelf().stream()
							.noneMatch(call -> call.getTargetOwner().isEquivalentTo(BBCodeGrammarHolder.class)
									&& call.getName().equals("current")))
						continue;
					readers.add(javaClass.getSimpleName() + "." + codeUnit.getName());
					if (codeUnit instanceof JavaConstructor || codeUnit.isAnnotatedWith(PostConstruct.class))
						readWhileTheBeanIsStillBeingBuilt.add(javaClass.getFullName() + "." + codeUnit.getName());
				}

			assertTrue(readers.size() >= 4,
					"this rule only says something while beans actually read the published grammar; a reader "
							+ "that stops calling current() escapes it silently: " + readers);
			assertTrue(readWhileTheBeanIsStillBeingBuilt.isEmpty(),
					"BBCodeGrammarHolder starts on the grammar that declares nothing and is filled by "
							+ "BBCodeGrammarLoader's @PostConstruct. Bean build order is free precisely because every "
							+ "reader calls current() per render, by which time the context is up; a reader that "
							+ "calls it while it is itself being built captures the empty grammar for the process "
							+ "lifetime and no reload can reach it: " + readWhileTheBeanIsStillBeingBuilt);
		}

		private List<String> statementsOf(JavaMethod method) {
			List<String> statements = new ArrayList<>();
			if (method.isAnnotatedWith(Select.class))
				statements.addAll(List.of(method.getAnnotationOfType(Select.class).value()));
			if (method.isAnnotatedWith(Insert.class))
				statements.addAll(List.of(method.getAnnotationOfType(Insert.class).value()));
			if (method.isAnnotatedWith(Update.class))
				statements.addAll(List.of(method.getAnnotationOfType(Update.class).value()));
			if (method.isAnnotatedWith(Delete.class))
				statements.addAll(List.of(method.getAnnotationOfType(Delete.class).value()));
			return statements;
		}
	}

	@Nested
	class ModerationRules {

		private final ForumAccessRules rules = new ForumAccessRules(new AuthorityTiers(roleHierarchy()));

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

		@Test
		void restoreGatesAskOnlyForModeratorTierWhileTheActionListAlsoDemandsRecycledState() {
			AtomicInteger threadLoads = new AtomicInteger();
			assertTrue(rules.allowsThreadAction(moderator, "thread.restore", () -> {
				threadLoads.incrementAndGet();
				return Optional.of(openThread(OTHER_ID));
			}));
			AtomicInteger messageLoads = new AtomicInteger();
			assertTrue(rules.allowsMessageAction(moderator, "message.restore", () -> {
				messageLoads.incrementAndGet();
				return Optional.of(message(OTHER_ID, openThread(OTHER_ID)));
			}));
			assertEquals(0, threadLoads.get() + messageLoads.get(),
					"the restore gates answer from the actor tier alone and must never load the subject");
			assertFalse(rules.permittedThreadActions(moderator, openThread(OTHER_ID)).contains("thread.restore"));
			assertFalse(rules.permittedMessageActions(moderator, message(OTHER_ID, openThread(OTHER_ID)))
					.contains("message.restore"));
			assertFalse(rules.allowsThreadAction(readOnlyMember, "thread.restore", () -> Optional.empty()));
			assertFalse(rules.allowsMessageAction(member, "message.restore", () -> Optional.empty()));
		}

		@Test
		void moderatorDeleteGateAnswersBeforeTheMessageIsLoadedWhileEditGateRequiresIt() {
			AtomicInteger deleteLoads = new AtomicInteger();
			assertTrue(rules.allowsMessageAction(moderator, "message.delete", () -> {
				deleteLoads.incrementAndGet();
				return Optional.empty();
			}));
			assertEquals(0, deleteLoads.get(),
					"a moderator's delete gate short-circuits on tier, so a missing message still passes");
			assertFalse(rules.allowsMessageAction(moderator, "message.edit", () -> Optional.empty()),
					"the edit gate has no such short-circuit and denies when the message cannot be loaded");
			assertTrue(rules.allowsMessageAction(moderator, "message.edit",
					() -> Optional.of(message(OTHER_ID, lockedThread(OTHER_ID)))));
			assertTrue(rules.allowsMessageAction(member, "message.delete",
					() -> Optional.of(message(MEMBER_ID, openThread(MEMBER_ID)))));
			assertFalse(rules.allowsMessageAction(member, "message.delete",
					() -> Optional.of(message(MEMBER_ID, lockedThread(MEMBER_ID)))));
			assertFalse(rules.allowsMessageAction(readOnlyMember, "message.delete", () -> Optional.empty()));
		}

		@Test
		void replyGateLoadsTheThreadAndUnknownActionsAreDenied() {
			assertTrue(rules.allowsThreadAction(member, "thread.reply", () -> Optional.of(openThread(OTHER_ID))));
			assertFalse(rules.allowsThreadAction(member, "thread.reply", () -> Optional.empty()));
			assertFalse(rules.allowsThreadAction(guest, "thread.reply", () -> Optional.of(openThread(OTHER_ID))));
			assertFalse(rules.allowsThreadAction(moderator, "thread.split", () -> Optional.of(openThread(OTHER_ID))));
			assertFalse(rules.allowsMessageAction(moderator, "message.purge", () -> Optional.empty()));
		}
	}

	@Nested
	class ResourcePermissions {

		private static final int BOARD_READ_PERMISSION_ID = 1;
		private static final int STAFF_ONLY_PERMISSION_ID = 2;

		private List<Permission> permissions(Integer... permissionIds) {
			List<Permission> permissions = new ArrayList<>();
			for (Integer permissionId : permissionIds) {
				Permission permission = new Permission();
				permission.setPermissionId(permissionId);
				permissions.add(permission);
			}
			return permissions;
		}

		private User actorHolding(Integer... permissionIds) {
			return User.builder().userId(MEMBER_ID).permissions(permissions(permissionIds)).build();
		}

		private Thread threadRequiring(Integer... permissionIds) {
			Thread thread = new Thread();
			thread.setBoardPermissions(permissions(permissionIds));
			return thread;
		}

		private Board boardRequiring(Integer... permissionIds) {
			Board board = new Board();
			board.setBoardPerms(permissions(permissionIds));
			return board;
		}

		private BoardSummary boardSummaryRequiring(Integer... permissionIds) {
			BoardSummary boardSummary = new BoardSummary();
			boardSummary.setBoardPerms(permissions(permissionIds));
			return boardSummary;
		}

		private final AbstractService securingService = new AbstractService() {};

		@Test
		void securedResourceAdmitsOverlappingPermissionAndRejectsDisjointOne() {
			assertTrue(actorHolding(BOARD_READ_PERMISSION_ID)
					.canAccess(threadRequiring(BOARD_READ_PERMISSION_ID, STAFF_ONLY_PERMISSION_ID)));
			assertFalse(actorHolding(BOARD_READ_PERMISSION_ID)
					.canAccess(threadRequiring(STAFF_ONLY_PERMISSION_ID)));
		}

		@Test
		void securedResourceWithoutRequiredPermissionsRejectsEveryActor() {
			assertFalse(actorHolding(BOARD_READ_PERMISSION_ID).canAccess(threadRequiring()));
			assertFalse(actorHolding(BOARD_READ_PERMISSION_ID).canAccess(boardRequiring()));
		}

		@Test
		void everyBoardShapedResourceAnswersTheSameOverlapQuestion() {
			User reader = actorHolding(BOARD_READ_PERMISSION_ID);
			assertTrue(reader.canAccess(boardRequiring(BOARD_READ_PERMISSION_ID)));
			assertFalse(reader.canAccess(boardRequiring(STAFF_ONLY_PERMISSION_ID)));
			assertTrue(reader.canAccess(boardSummaryRequiring(BOARD_READ_PERMISSION_ID)));
			assertFalse(reader.canAccess(boardSummaryRequiring(STAFF_ONLY_PERMISSION_ID)));
		}

		@Test
		void securingAServiceResourceRaisesUnauthorizedOnlyWhenAccessIsDenied() {
			ReflectionTestUtils.invokeMethod(securingService, "secureObject",
					threadRequiring(BOARD_READ_PERMISSION_ID), actorHolding(BOARD_READ_PERMISSION_ID));
			assertThrows(ZfgcUnauthorizedException.class,
					() -> ReflectionTestUtils.invokeMethod(securingService, "secureObject",
							threadRequiring(STAFF_ONLY_PERMISSION_ID), actorHolding(BOARD_READ_PERMISSION_ID)));
		}

		@Test
		void permissionIdOverlapDecidesAttachmentVisibility() {
			assertTrue(actorHolding(STAFF_ONLY_PERMISSION_ID, BOARD_READ_PERMISSION_ID)
					.hasAnyPermissionId(List.of(BOARD_READ_PERMISSION_ID)));
			assertFalse(actorHolding(BOARD_READ_PERMISSION_ID)
					.hasAnyPermissionId(List.of(STAFF_ONLY_PERMISSION_ID)));
			assertFalse(actorHolding().hasAnyPermissionId(List.of(BOARD_READ_PERMISSION_ID)));
			assertFalse(actorHolding(BOARD_READ_PERMISSION_ID).hasAnyPermissionId(List.of()));
		}

		@Test
		void unhydratedPermissionListDeniesInsteadOfFailing() {
			User unhydratedReader = new User();
			unhydratedReader.setPermissions(null);
			assertEquals(List.of(), unhydratedReader.permissionIds());
			assertFalse(unhydratedReader.hasAnyPermissionId(List.of(BOARD_READ_PERMISSION_ID)));
			assertFalse(unhydratedReader.canAccess(threadRequiring(BOARD_READ_PERMISSION_ID)));
			Thread unhydratedResource = new Thread();
			unhydratedResource.setBoardPermissions(null);
			assertFalse(actorHolding(BOARD_READ_PERMISSION_ID).canAccess(unhydratedResource));
			assertFalse(actorHolding(BOARD_READ_PERMISSION_ID).canAccess(null));
		}

		@Test
		void permissionsWithoutIdsNeverOverlapEachOther() {
			User actorWithUnidentifiedPermission = User.builder().userId(MEMBER_ID)
					.permissions(new ArrayList<>(List.of(new Permission()))).build();
			assertEquals(List.of(), actorWithUnidentifiedPermission.permissionIds());
			assertFalse(actorWithUnidentifiedPermission.canAccess(threadRequiring((Integer) null)));
			assertFalse(actorWithUnidentifiedPermission.hasAnyPermissionId(singletonListOfNull()));
		}

		private List<Integer> singletonListOfNull() {
			List<Integer> ids = new ArrayList<>();
			ids.add(null);
			return ids;
		}

		private static final ArchCondition<JavaClass> neverReadPermissionIdsDirectly =
				new ArchCondition<JavaClass>("never read Permission.getPermissionId() directly") {
					@Override
					public void check(JavaClass javaClass, ConditionEvents events) {
						List<JavaAccess<?>> accesses = new ArrayList<>();
						accesses.addAll(javaClass.getMethodCallsFromSelf());
						accesses.addAll(javaClass.getMethodReferencesFromSelf());
						for (JavaAccess<?> access : accesses)
							if (access.getTargetOwner().isEquivalentTo(Permission.class)
									&& "getPermissionId".equals(access.getTarget().getName()))
								events.add(SimpleConditionEvent.violated(javaClass, access.getDescription()
										+ " reads a permission id outside the actor model, which is how every "
										+ "hand-rolled permission-overlap check has started"));
					}
				};

		@Test
		void permissionOverlapIsAskedOfTheActorAndNowhereElse() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			ArchRule rule = classes()
					.that().resideOutsideOfPackages("com.zfgc.zfgbb.model..", "com.zfgc.zfgbb.mapstruct..")
					.should(neverReadPermissionIdsDirectly)
					.as("only the actor model may read the id off a Permission")
					.because("'does the actor hold any permission this resource requires' is one question with one "
							+ "answer: User.canAccess(Securable) for a secured resource, User.hasAnyPermissionId "
							+ "for permission ids that came back from a query. Projecting Permission ids anywhere "
							+ "else is the first line of a fifth copy of that predicate; ask the actor instead");

			rule.check(appClasses);
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
			assertFalse(ForumModerationOrchestrator.restoreProvenanceMatches(wrapper, origin, 9));
			origin.setThreadId(7);
			assertTrue(ForumModerationOrchestrator.restoreProvenanceMatches(wrapper, origin, 9));
		}
	}

	@Nested
	class QueryPartitioning {
		@Test
		void emptyIdsProduceNoChunks() {
			assertEquals(List.of(), MessageDataProvider.partition(List.of(), 10000));
		}

		@Test
		void smallIdListStaysSingleChunk() {
			List<Integer> ids = List.of(1, 2, 3);
			List<List<Integer>> chunks = MessageDataProvider.partition(ids, 10000);
			assertEquals(1, chunks.size());
			assertEquals(ids, chunks.get(0));
		}

		@Test
		void oversizedIdListIsSplitUnderTheBindParamLimit() {
			List<Integer> ids = IntStream.range(0, 165_000).boxed().toList();
			List<List<Integer>> chunks = MessageDataProvider.partition(ids, 10000);
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

		private ProjectNewsDao projectNewsDao;
		private ThreadDao threadDao;
		private BoardPermissionViewDao boardPermissionViewDao;
		private ProjectDataProvider provider;

		@BeforeEach
		void setup() {
			projectNewsDao = mock(ProjectNewsDao.class);
			threadDao = mock(ThreadDao.class);
			boardPermissionViewDao = mock(BoardPermissionViewDao.class);
			provider = mock(ProjectDataProvider.class, CALLS_REAL_METHODS);
			GuestPermissionDataProvider guestPermissionDataProvider =
					new GuestPermissionDataProvider(boardPermissionViewDao);
			ReflectionTestUtils.setField(provider, "guestPermissionDataProvider", guestPermissionDataProvider);
			ReflectionTestUtils.setField(provider, "projectNewsDao", projectNewsDao);
			ReflectionTestUtils.setField(provider, "threadDao", threadDao);
			ReflectionTestUtils.setField(provider, "projectMap", new ProjectMapImpl());
			when(projectNewsDao.get(any()))
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
			when(boardPermissionViewDao.get(any())).thenReturn(List.of());
			List<ProjectNews> news = provider.getProjectNews(PROJECT_ID);
			verify(threadDao, never()).get(any());
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
			when(boardPermissionViewDao.get(any())).thenReturn(List.of(perm));
			ThreadDbo publicThread = new ThreadDbo();
			publicThread.setThreadId(PUBLIC_THREAD_ID);
			publicThread.setThreadName("PUBLIC_TITLE");
			when(threadDao.get(any())).thenReturn(List.of(publicThread));
			ArgumentCaptor<ThreadDboExample> exampleCaptor = ArgumentCaptor.forClass(ThreadDboExample.class);

			List<ProjectNews> news = provider.getProjectNews(PROJECT_ID);

			verify(threadDao).get(exampleCaptor.capture());
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
		private BoardPermissionViewDao boardPermissionViewDao;
		private MessageDao messageDao;
		private ContentRenderingService contentRenderingService;
		private UserBioInfoMap userBioInfoMap;
		private UserMap userMap;
		private UserDataProvider provider;

		private final UserLoadOptions bioOnly = new UserLoadOptions(false, true, false, false, false, false, false);

		@BeforeEach
		void setup() {
			userDao = mock(UserDao.class);
			bioInfoDao = mock(UserBioInfoDao.class);
			boardPermissionViewDao = mock(BoardPermissionViewDao.class);
			messageDao = mock(MessageDao.class);
			contentRenderingService = mock(ContentRenderingService.class);
			userBioInfoMap = mock(UserBioInfoMap.class);
			userMap = mock(UserMap.class);
			GuestPermissionDataProvider guestPermissionDataProvider =
					new GuestPermissionDataProvider(boardPermissionViewDao);
			UserDao hydrationUserDao = mock(UserDao.class);
			UserProfileFacade userProfileFacade = new UserProfileFacade(
					hydrationUserDao,
					mock(UserPermissionViewDao.class),
					contentRenderingService,
					userMap,
					userBioInfoMap,
					mock(UserContactInfoMap.class),
					mock(AvatarMap.class),
					mock(PermissionMap.class),
					mock(UserReactionSummaryViewDao.class),
					mock(UserAwardDao.class),
					mock(AwardDao.class),
					messageDao,
					guestPermissionDataProvider);
			provider = new UserDataProvider(
					userDao,
					mock(BrUserPermissionDao.class),
					mock(EmailAddressDao.class),
					bioInfoDao,
					mock(UserContactInfoDao.class),
					userMap,
					mock(UserSettingsMap.class),
					mock(AwardMap.class),
					userBioInfoMap,
					mock(EmailAddressMap.class),
					mock(UserSettingsDao.class),
						mock(AwardDao.class),
						mock(UserAwardDao.class),
						mock(UserPermissionViewDao.class),
						mock(UserRefreshTokenDao.class),
						mock(UserErasureDao.class),
						mock(AccountDeletionAuditDao.class),
						mock(ContentResourceDao.class),
						mock(UserPermissionGroupAssocDao.class),
						mock(AvatarDao.class),
						mock(MigratorIdMapDao.class),
						userProfileFacade);

			UserDbo userDbo = new UserDbo();
			userDbo.setUserId(USER_ID);
			UserBioInfoDbo bioDbo = new UserBioInfoDbo();
			UserAggregateDbo agg = new UserAggregateDbo();
			agg.setUser(userDbo);
			agg.setBio(bioDbo);
			when(hydrationUserDao.hydrate(any())).thenReturn(List.of(agg));
			when(userDao.getOne(any(UserDboExample.class))).thenReturn(Optional.of(userDbo));
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
			when(boardPermissionViewDao.get(any())).thenReturn(List.of(perm));
			ArgumentCaptor<BoardPermissionViewDboExample> exampleCaptor = ArgumentCaptor.forClass(BoardPermissionViewDboExample.class);
			loadBio();
			verify(boardPermissionViewDao).get(exampleCaptor.capture());
			List<String> conditions = exampleCaptor.getValue().getOredCriteria().get(0).getAllCriteria().stream()
					.map(BoardPermissionViewDboExample.Criterion::getCondition).toList();
			assertTrue(conditions.stream().anyMatch(condition -> condition.contains("permission_id in")));
		}

		@Test
		void emptyGuestBoardSetYieldsZeroWithoutQuery() {
			when(boardPermissionViewDao.get(any())).thenReturn(List.of());
			UserBioInfo bio = loadBio();
			assertEquals(0, bio.getPostCount().intValue());
			verify(messageDao, never()).postCountsByOwnerWithinBoards(any(), any());
		}

		@Test
		void nonEmptyGuestBoardSetFiltersByOwnerAndBoard() {
			BoardPermissionViewDbo perm1 = new BoardPermissionViewDbo();
			perm1.setBoardId(1);
			BoardPermissionViewDbo perm2 = new BoardPermissionViewDbo();
			perm2.setBoardId(2);
			when(boardPermissionViewDao.get(any())).thenReturn(List.of(perm1, perm2));
			MessagePostCountMapper.OwnerPostCount count = new MessagePostCountMapper.OwnerPostCount();
			count.setOwnerId(USER_ID);
			count.setPostCount(3L);
			when(messageDao.postCountsByOwnerWithinBoards(any(), any())).thenReturn(List.of(count));
			UserBioInfo bio = loadBio();
			assertEquals(3, bio.getPostCount().intValue());
			verify(messageDao).postCountsByOwnerWithinBoards(any(), any());
		}
	}

	@Nested
	class BatchAuthorLoading {

		private UserDao userDao;
		private UserPermissionViewDao userPermissionViewDao;
		private UserReactionSummaryViewDao reactionSummaryDao;
		private MessageDao messageDao;
		private UserDao hydrationUserDao;
		private BoardPermissionViewDao boardPermissionViewDao;
		private ContentRenderingService contentRenderingService;
		private UserMap userMap;
		private UserBioInfoMap userBioInfoMap;
		private PermissionMap permissionMap;
		private AvatarMap avatarMap;
		private UserDataProvider provider;

		@BeforeEach
		void setup() {
			userDao = mock(UserDao.class);
			userPermissionViewDao = mock(UserPermissionViewDao.class);
			reactionSummaryDao = mock(UserReactionSummaryViewDao.class);
			messageDao = mock(MessageDao.class);
			boardPermissionViewDao = mock(BoardPermissionViewDao.class);
			contentRenderingService = mock(ContentRenderingService.class);
			userMap = mock(UserMap.class);
			userBioInfoMap = mock(UserBioInfoMap.class);
			permissionMap = mock(PermissionMap.class);
			avatarMap = mock(AvatarMap.class);
			GuestPermissionDataProvider guestPermissionDataProvider =
					new GuestPermissionDataProvider(boardPermissionViewDao);
			hydrationUserDao = mock(UserDao.class);
			when(hydrationUserDao.hydrate(any())).thenAnswer(invocation -> {
				List<Integer> userIds = invocation.getArgument(0);
				return userIds.stream().map(userId -> {
					UserDbo dbo = new UserDbo();
					dbo.setUserId(userId);
					UserAggregateDbo agg = new UserAggregateDbo();
					agg.setUser(dbo);
					return agg;
				}).toList();
			});
			UserProfileFacade userProfileFacade = new UserProfileFacade(
					hydrationUserDao,
					userPermissionViewDao,
					contentRenderingService,
					userMap,
					userBioInfoMap,
					mock(UserContactInfoMap.class),
					avatarMap,
					permissionMap,
					reactionSummaryDao,
					mock(UserAwardDao.class),
					mock(AwardDao.class),
					messageDao,
					guestPermissionDataProvider);
			provider = new UserDataProvider(
					userDao,
					mock(BrUserPermissionDao.class),
					mock(EmailAddressDao.class),
					mock(UserBioInfoDao.class),
					mock(UserContactInfoDao.class),
					userMap,
					mock(UserSettingsMap.class),
					mock(AwardMap.class),
					userBioInfoMap,
					mock(EmailAddressMap.class),
					mock(UserSettingsDao.class),
						mock(AwardDao.class),
						mock(UserAwardDao.class),
						mock(UserPermissionViewDao.class),
						mock(UserRefreshTokenDao.class),
						mock(UserErasureDao.class),
						mock(AccountDeletionAuditDao.class),
						mock(ContentResourceDao.class),
						mock(UserPermissionGroupAssocDao.class),
						mock(AvatarDao.class),
						mock(MigratorIdMapDao.class),
						userProfileFacade);

			BoardPermissionViewDbo perm = new BoardPermissionViewDbo();
			perm.setBoardId(1);
			when(boardPermissionViewDao.get(any())).thenReturn(List.of(perm));
			when(userMap.toModel(any())).thenReturn(new User());
			when(userBioInfoMap.toModel(any())).thenReturn(new UserBioInfo());
		}

		private void primeSubEntities() {
			when(userPermissionViewDao.get(any(UserPermissionViewDboExample.class)))
					.thenReturn(List.of());
			when(reactionSummaryDao.get(any())).thenReturn(List.of());
			when(messageDao.postCountsByOwnerWithinBoards(any(), any())).thenReturn(List.of());
		}

		private void verifyExactlyOneQueryPerSubEntity() {
			verify(hydrationUserDao, times(1)).hydrate(any());
			verify(reactionSummaryDao, times(1)).get(any());
			verify(messageDao, times(1)).postCountsByOwnerWithinBoards(any(), any());
			verify(boardPermissionViewDao, times(1)).get(any());
		}

		@Test
		void singleAuthorIssuesOneQueryPerSubEntity() {
			primeSubEntities();
			provider.findPublicAuthorsByIds(List.of(100));
			verifyExactlyOneQueryPerSubEntity();
		}

		@Test
		void manyAuthorsStillIssueOneQueryPerSubEntity() {
			primeSubEntities();
			provider.findPublicAuthorsByIds(List.of(100, 101, 102, 103, 104));
			verifyExactlyOneQueryPerSubEntity();
		}
	}

	@Nested
	class ReadChokepoint {

		private static final String DBO_PACKAGE = "com.zfgc.zfgbb.dbo";

		private static final String EXAMPLE_SUFFIX = "Example";

		private static final Set<String> BOARD_SCOPED_DBO_SIMPLE_NAMES = Set.of(
				"BoardDbo",
				"ThreadDbo",
				"MessageDbo",
				"MessageHistoryDbo",
				"CurrentMessageDbo",
				"BoardSummaryViewDbo",
				"ChildBoardViewDbo",
				"LatestMessageInThreadViewDbo",
				"RecentActivityViewDbo",
				"AttachmentBoardViewDbo");

		private static final Set<String> UNDERIVABLE_BOARD_READ_ACCESSOR_NAMES = Set.of(
				"com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper",
				"com.zfgc.zfgbb.mappers.custom.ForumSearchQueryMapper");

		private static final Set<String> ALWAYS_DERIVED_BOARD_READ_ACCESSOR_NAMES = Set.of(
				"com.zfgc.zfgbb.mappers.BoardDboMapper",
				"com.zfgc.zfgbb.mappers.ThreadDboMapper",
				"com.zfgc.zfgbb.mappers.MessageDboMapper",
				"com.zfgc.zfgbb.mappers.MessageHistoryDboMapper",
				"com.zfgc.zfgbb.mappers.CurrentMessageDboMapper");

		private static final DescribedPredicate<JavaClass> resideInTheDataAccessLayer =
				JavaClass.Predicates.resideInAnyPackage("com.zfgc.zfgbb.dao..", "com.zfgc.zfgbb.mappers..");

		private static String withoutExampleSuffix(String simpleName) {
			return simpleName.endsWith(EXAMPLE_SUFFIX)
					? simpleName.substring(0, simpleName.length() - EXAMPLE_SUFFIX.length())
					: simpleName;
		}

		private static boolean isBoardScopedDbo(JavaClass javaClass) {
			return javaClass.getPackageName().equals(DBO_PACKAGE)
					&& BOARD_SCOPED_DBO_SIMPLE_NAMES.contains(withoutExampleSuffix(javaClass.getSimpleName()));
		}

		private static boolean readsBoardScopedDbos(JavaClass javaClass) {
			return javaClass.getDirectDependenciesFromSelf().stream()
					.map(Dependency::getTargetClass)
					.anyMatch(ReadChokepoint::isBoardScopedDbo);
		}

		@Test
		void rawBoardReadsOnlyThroughFilteredPathOrAnnotatedReaders() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			Set<String> resolvedBoardScopedDboNames = new HashSet<>();
			Set<String> unresolvedUnderivableNames = new HashSet<>(UNDERIVABLE_BOARD_READ_ACCESSOR_NAMES);
			Set<String> boardReadAccessorNames = new HashSet<>(UNDERIVABLE_BOARD_READ_ACCESSOR_NAMES);
			for (JavaClass javaClass : appClasses) {
				if (isBoardScopedDbo(javaClass))
					resolvedBoardScopedDboNames.add(withoutExampleSuffix(javaClass.getSimpleName()));
				unresolvedUnderivableNames.remove(javaClass.getFullName());
				if (resideInTheDataAccessLayer.test(javaClass) && readsBoardScopedDbos(javaClass))
					boardReadAccessorNames.add(javaClass.getFullName());
			}

			assertEquals(BOARD_SCOPED_DBO_SIMPLE_NAMES, resolvedBoardScopedDboNames,
					"every board-scoped Dbo name must resolve against the imported classes; a rename that matched "
							+ "nothing would silently empty the derived accessor set and disable this rule");
			assertEquals(Set.of(), unresolvedUnderivableNames,
					"the accessors derivation cannot see are named by string only, so a rename or move drops them "
							+ "from the accessor set and silently stops guarding their callers; every such name "
							+ "must resolve against the imported classes");
			assertTrue(boardReadAccessorNames.containsAll(ALWAYS_DERIVED_BOARD_READ_ACCESSOR_NAMES),
					"derivation stopped seeing the generated board mappers, which name their Dbo only as a type "
							+ "argument of the root interface they extend; the rule would still pass while "
							+ "guarding nothing. Derived: " + boardReadAccessorNames);

			DescribedPredicate<JavaClass> areBoardReadAccessors =
					new DescribedPredicate<JavaClass>("board/thread/message read accessors") {
						@Override
						public boolean test(JavaClass javaClass) {
							return boardReadAccessorNames.contains(javaClass.getFullName());
						}
					};

			ArchRule rule = noClasses()
					.that().resideOutsideOfPackages("com.zfgc.zfgbb.migrator..")
					.and(DescribedPredicate.not(areBoardReadAccessors))
					.and().areNotAnnotatedWith(BoardVisibilityChokepoint.class)
					.and().areNotAnnotatedWith(UnfilteredBoardRead.class)
					.should().dependOnClassesThat(areBoardReadAccessors)
					.as("board/thread/message content must be read only through the board-visibility-filtered chokepoint")
					.because("raw unfiltered reads of board/thread/message can leak hidden-board content; "
							+ "route the read through an existing chokepoint, or annotate the class with "
							+ "@BoardVisibilityChokepoint(\"how it filters\") when the class itself enforces board "
							+ "visibility, or @UnfilteredBoardRead(\"honest reason\") when the unfiltered access is "
							+ "deliberate and safe");

			rule.check(appClasses);
		}
	}

	@Nested
	class LayerDirection {

		private static final String SERVICE_LAYER_PACKAGE_PREFIX = "com.zfgc.zfgbb.services.";

		private static final DescribedPredicate<JavaClass> areServiceLayerClasses =
				JavaClass.Predicates.resideInAPackage(SERVICE_LAYER_PACKAGE_PREFIX + ".");

		private static final DescribedPredicate<JavaClass> areServiceOrDataAccessClasses =
				JavaClass.Predicates.resideInAnyPackage(SERVICE_LAYER_PACKAGE_PREFIX + ".", "..dataprovider..",
						"..dao..", "..mappers..");

		private static final Set<String> SERVICE_LAYER_SENTINELS = Set.of(
				"com.zfgc.zfgbb.services.forum.ForumService",
				"com.zfgc.zfgbb.services.users.UserService",
				"com.zfgc.zfgbb.services.install.InstallService",
				"com.zfgc.zfgbb.services.cms.wiki.WikiService");

		private static final Set<String> SERVICE_AND_DATA_ACCESS_SENTINELS = Set.of(
				"com.zfgc.zfgbb.services.users.UserService",
				"com.zfgc.zfgbb.dataprovider.users.UserProfileFacade",
				"com.zfgc.zfgbb.dao.users.UserDao",
				"com.zfgc.zfgbb.mappers.UserDboMapper");

		private static void requireTheForbiddenTargetsWereFound(JavaClasses appClasses,
				DescribedPredicate<JavaClass> forbiddenTargets, Set<String> sentinels, String rule) {
			Set<String> matched = new HashSet<>();
			for (JavaClass javaClass : appClasses)
				if (forbiddenTargets.test(javaClass))
					matched.add(javaClass.getFullName());
			assertTrue(matched.containsAll(sentinels),
					rule + " resolved " + matched.size() + " target classes and is missing at least one "
							+ "sentinel. noClasses().should().dependOnClassesThat(...) passes silently when the "
							+ "TARGET set is empty -- failOnEmptyShould only guards the subject -- so renaming or "
							+ "emptying a target package would leave this rule green while guarding nothing. "
							+ "Expected: " + sentinels + " Matched: " + matched);
		}

		@Test
		void operationalArchiveUtilitiesNeverDependOnTheServiceLayer() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			requireTheForbiddenTargetsWereFound(appClasses, areServiceLayerClasses, SERVICE_LAYER_SENTINELS,
					"operationalArchiveUtilitiesNeverDependOnTheServiceLayer");

			ArchRule rule = noClasses()
					.that().resideInAPackage("com.zfgc.zfgbb.operations..")
					.should().dependOnClassesThat(areServiceLayerClasses)
					.as("the operations layer must not depend on the service layer")
					.because("services.system already builds on operations.archive, so any dependency back into "
							+ "services closes a package cycle and drags Spring-free archive and dump utilities "
							+ "into the service layer; a helper both sides need belongs in operations, the way "
							+ "BackupArchiveWriter.isOperationalArtifact and OperationFiles.deleteTree do");

			rule.check(appClasses);
		}

		@Test
		void layersBelowTheServiceLayerNeverDependOnIt() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			requireTheForbiddenTargetsWereFound(appClasses, areServiceLayerClasses, SERVICE_LAYER_SENTINELS,
					"layersBelowTheServiceLayerNeverDependOnIt");

			ArchRule rule = noClasses()
					.that().resideInAnyPackage("..dataprovider..", "com.zfgc.zfgbb.content..", "..mappers..", "..dao..")
					.should().dependOnClassesThat(areServiceLayerClasses)
					.as("the dataprovider, content, mapper and dao layers must not depend on the service layer")
					.because("services orchestrate dataproviders, data access and content rendering, never the "
							+ "reverse; a query several services share belongs in the layer below them, not in a "
							+ "service they call back into, and a row a mapper reads or writes is a model type, not "
							+ "a service type");

			rule.check(appClasses);
		}

		@Test
		void authorizationRulesNeverDependOnServicesOrDataAccess() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			requireTheForbiddenTargetsWereFound(appClasses, areServiceOrDataAccessClasses,
					SERVICE_AND_DATA_ACCESS_SENTINELS, "authorizationRulesNeverDependOnServicesOrDataAccess");

			ArchRule rule = noClasses()
					.that().resideInAPackage("com.zfgc.zfgbb.authorization..")
					.should().dependOnClassesThat(areServiceOrDataAccessClasses)
					.as("the authorization layer must not depend on services, dataproviders, daos or mappers")
					.because("an access rule that can load its own data decides on state its caller never "
							+ "supplied, so the same rule answers differently depending on what it happened to "
							+ "fetch; authorization takes the caller and the subject as arguments and stays a "
							+ "pure function of them, the way ResourceAccessRules and AuthorityTiers do");

			rule.check(appClasses);
		}
	}

	@Nested
	class PersistenceMechanism {

		@Test
		void generatedMapperWritesHappenOnlyInsideTheDaoLayer() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			DescribedPredicate<JavaCall<?>> writesThroughAGeneratedMapper =
					new DescribedPredicate<>("a write through a generated DBO mapper") {
						@Override
						public boolean test(JavaCall<?> call) {
							String owner = call.getTargetOwner().getSimpleName();
							String method = call.getTarget().getName();
							return owner.endsWith("DboMapper")
									&& (method.startsWith("insert") || method.startsWith("update")
											|| method.startsWith("delete"));
						}
					};

			long recognised = 0;
			for (JavaClass javaClass : appClasses)
				if (javaClass.getPackageName().startsWith("com.zfgc.zfgbb.migrator"))
					for (JavaAccess<?> access : javaClass.getAccessesFromSelf())
						if (access instanceof JavaCall<?> call && writesThroughAGeneratedMapper.test(call))
							recognised++;
			assertTrue(recognised > 0,
					"this rule matches call sites by target-owner name, so renaming the generated "
							+ "*DboMapper suffix would leave it green while guarding nothing; the migrator "
							+ "is exempt but still writes through those mappers, so it is the proof the "
							+ "predicate still resolves. The dao layer itself cannot serve as that proof: "
							+ "KeyedDao and IdentityDao call the generic CrudMapper interface, never a "
							+ "concrete *DboMapper");

			ArchRule rule = noClasses()
					.that().resideOutsideOfPackages("com.zfgc.zfgbb.dao..", "com.zfgc.zfgbb.migrator..")
					.should().callMethodWhere(writesThroughAGeneratedMapper)
					.as("inserts, updates and deletes go through the dao layer")
					.because("IdentityDao.save() is the only write that carries the updated_ts "
							+ "compare-and-set; a service calling mapper.updateByPrimaryKey directly does a "
							+ "full-row write with no version predicate, so a concurrent edit is silently "
							+ "reverted instead of raising a conflict");

			rule.check(appClasses);
		}

		@Test
		void generatedMapperReadsHappenOnlyInsideTheDaoLayer() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			DescribedPredicate<JavaCall<?>> readsThroughAGeneratedMapper =
					new DescribedPredicate<>("a read through a generated DBO mapper") {
						@Override
						public boolean test(JavaCall<?> call) {
							String owner = call.getTargetOwner().getSimpleName();
							String method = call.getTarget().getName();
							return owner.endsWith("DboMapper")
									&& (method.startsWith("select") || method.startsWith("count"));
						}
					};

			long recognised = 0;
			for (JavaClass javaClass : appClasses)
				if (javaClass.getPackageName().startsWith("com.zfgc.zfgbb.migrator"))
					for (JavaAccess<?> access : javaClass.getAccessesFromSelf())
						if (access instanceof JavaCall<?> call && readsThroughAGeneratedMapper.test(call))
							recognised++;
			assertTrue(recognised > 0,
					"this rule matches call sites by target-owner name, so renaming the generated "
							+ "*DboMapper suffix would leave it green while guarding nothing; the migrator "
							+ "is exempt but still reads through those mappers, so it is the proof the "
							+ "predicate still resolves. The dao layer itself cannot serve as that proof: "
							+ "ReadDao and KeyedDao call the generic ReadMapper and CrudMapper interfaces, "
							+ "never a concrete *DboMapper");

			ArchRule rule = noClasses()
					.that().resideOutsideOfPackages("com.zfgc.zfgbb.dao..", "com.zfgc.zfgbb.migrator..")
					.should().callMethodWhere(readsThroughAGeneratedMapper)
					.as("selects and counts go through the dao layer")
					.because("a dao is the only place a read can be given a name, a return shape and a "
							+ "board-visibility guard; scattering selectByExample across services spreads "
							+ "raw Example construction over the codebase and leaves the read-side "
							+ "chokepoints with nothing to hold on to");

			rule.check(appClasses);
		}

		@Test
		void rawJdbcTemplateBannedFromProductionCode() {
			JavaClasses appClasses = new ClassFileImporter()
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
					.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
					.importPackages("com.zfgc.zfgbb");

			ArchRule rule = noClasses()
					.that().resideOutsideOfPackages("com.zfgc.zfgbb.migrator..")
					.and().areNotAnnotatedWith(RawSqlAccess.class)
					.and().areNotAssignableTo(TypeHandler.class)
					.should().dependOnClassesThat().areAssignableTo(JdbcTemplate.class)
					.orShould().dependOnClassesThat().areAssignableTo(NamedParameterJdbcTemplate.class)
					.orShould().dependOnClassesThat().areAssignableTo(Connection.class)
					.orShould().dependOnClassesThat().areAssignableTo(PreparedStatement.class)
					.orShould().dependOnClassesThat().areAssignableTo(Statement.class)
					.as("production classes reach the database through mappers, not raw SQL")
					.because("banning only JdbcTemplate left dataSource.getConnection() plus "
							+ "PreparedStatement completely unguarded, so the tidy way of writing raw "
							+ "SQL was forbidden while the untidy way was not; a class that genuinely "
							+ "needs raw SQL says why in @RawSqlAccess. MyBatis TypeHandler "
							+ "implementations are exempt because the SPI dictates their signatures");

			long exempted = 0;
			for (JavaClass javaClass : appClasses)
				if (javaClass.isAnnotatedWith(RawSqlAccess.class)) {
					exempted++;
					assertFalse(javaClass.getAnnotationOfType(RawSqlAccess.class).value().isBlank(),
							() -> javaClass.getName() + " claims @RawSqlAccess without saying why");
				}
			assertTrue(exempted > 0,
					"this rule matches by assignability to java.sql types, so a refactor that stopped "
							+ "using them would leave it green while guarding nothing; the annotated "
							+ "exemptions are the proof the predicate still has something to resolve");

			rule.check(appClasses);
		}
	}
}
