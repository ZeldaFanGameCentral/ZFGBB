package com.zfgc.zfgbb.authorization;

import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.MEMBER_ID;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.OTHER_ID;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.guest;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.member;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.moderator;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.profileAdmin;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.readOnlyMember;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.roleHierarchy;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.siteAdmin;
import static com.zfgc.zfgbb.testsupport.AccessControlFixtures.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.UpdateUserProfileRequest;
import com.zfgc.zfgbb.services.core.UserService;

class AccessModelTest {

	private static final String BOARD_TYPE = "BOARD";
	private static final String UNSUPPORTED_TYPE = "GALAXY";

	private static User actor() {
		return user(7);
	}

	private static Authentication authenticatedAs(Object principal) {
		return new UsernamePasswordAuthenticationToken(principal, null);
	}

	static Stream<Arguments> guardClauseDenialCases() {
		return Stream.of(
				arguments("nullAuthenticationDeniesWithoutConsultingRules", null, Integer.valueOf(42)),
				arguments("nullTargetIdDenies", authenticatedAs(actor()), null),
				arguments("principalThatIsNotAUserDenies", authenticatedAs("anonymousUser"), Integer.valueOf(42)),
				arguments("nonNumericTargetIdDenies", authenticatedAs(actor()), "not-a-number"));
	}

	@Nested
	class Evaluator {

		@ParameterizedTest
		@MethodSource("com.zfgc.zfgbb.authorization.AccessModelTest#guardClauseDenialCases")
		void guardClausesDenyWithoutConsultingRules(String caseName, Authentication authentication,
				Serializable targetId) {
			ResourceAccessRules rule = mock(ResourceAccessRules.class);
			ZfgbbPermissionEvaluator evaluator = new ZfgbbPermissionEvaluator(List.of(rule));

			assertFalse(evaluator.hasPermission(authentication, targetId, BOARD_TYPE, "READ"));
			verify(rule, never()).supports(any());
		}

		@Test
		void unsupportedTargetTypeDeniesFailClosed() {
			ResourceAccessRules rule = mock(ResourceAccessRules.class);
			when(rule.supports(UNSUPPORTED_TYPE)).thenReturn(false);
			ZfgbbPermissionEvaluator evaluator = new ZfgbbPermissionEvaluator(List.of(rule));

			assertFalse(evaluator.hasPermission(authenticatedAs(actor()), Integer.valueOf(42), UNSUPPORTED_TYPE, "READ"));
			verify(rule, never()).allows(any(), any(), anyInt(), any());
		}

		@Test
		void emptyRuleListDeniesFailClosed() {
			ZfgbbPermissionEvaluator evaluator = new ZfgbbPermissionEvaluator(List.of());

			assertFalse(evaluator.hasPermission(authenticatedAs(actor()), Integer.valueOf(42), BOARD_TYPE, "READ"));
		}

		@Test
		void supportedTypeDelegatesToMatchingRuleWithStringifiedPermission() {
			User actor = actor();
			ResourceAccessRules rule = mock(ResourceAccessRules.class);
			when(rule.supports(BOARD_TYPE)).thenReturn(true);
			when(rule.allows(eq(actor), eq(BOARD_TYPE), eq(42), eq("99"))).thenReturn(true);
			ZfgbbPermissionEvaluator evaluator = new ZfgbbPermissionEvaluator(List.of(rule));

			assertTrue(evaluator.hasPermission(authenticatedAs(actor), Integer.valueOf(42), BOARD_TYPE, Integer.valueOf(99)));
			verify(rule).allows(actor, BOARD_TYPE, 42, "99");
		}

		@Test
		void supportedTypeReturnsRuleDecisionWhenItDenies() {
			User actor = actor();
			ResourceAccessRules rule = mock(ResourceAccessRules.class);
			when(rule.supports(BOARD_TYPE)).thenReturn(true);
			when(rule.allows(actor, BOARD_TYPE, 42, "READ")).thenReturn(false);
			ZfgbbPermissionEvaluator evaluator = new ZfgbbPermissionEvaluator(List.of(rule));

			assertFalse(evaluator.hasPermission(authenticatedAs(actor), Integer.valueOf(42), BOARD_TYPE, "READ"));
		}

		@Test
		void firstSupportingRuleWinsAndLaterRulesAreNotConsulted() {
			User actor = actor();
			ResourceAccessRules first = mock(ResourceAccessRules.class);
			ResourceAccessRules second = mock(ResourceAccessRules.class);
			when(first.supports(BOARD_TYPE)).thenReturn(true);
			when(first.allows(actor, BOARD_TYPE, 42, "READ")).thenReturn(true);
			ZfgbbPermissionEvaluator evaluator = new ZfgbbPermissionEvaluator(List.of(first, second));

			assertTrue(evaluator.hasPermission(authenticatedAs(actor), Integer.valueOf(42), BOARD_TYPE, "READ"));
			verify(second, never()).supports(any());
			verify(second, never()).allows(any(), any(), anyInt(), any());
		}

		@Test
		void twoArgumentOverloadAlwaysDenies() {
			ResourceAccessRules rule = mock(ResourceAccessRules.class);
			ZfgbbPermissionEvaluator evaluator = new ZfgbbPermissionEvaluator(List.of(rule));

			assertFalse(evaluator.hasPermission(authenticatedAs(actor()), new Object(), "READ"));
		}
	}

	@Nested
	class Tiers {

		private final AuthorityTiers authorityTiers = new AuthorityTiers(roleHierarchy());

		@ParameterizedTest
		@CsvSource({
				"authenticatedIsFalseForNullActor, NULL_ACTOR, false",
				"authenticatedIsFalseForNullUserId, NULL_USER_ID, false",
				"authenticatedIsFalseForZeroUserId, 0, false",
				"authenticatedIsTrueForPositiveUserId, 5, true" })
		void authenticatedRequiresAPositiveUserId(String caseName, String actorSpec, boolean expected) {
			User actor = switch (actorSpec) {
			case "NULL_ACTOR" -> null;
			case "NULL_USER_ID" -> user(null);
			default -> user(Integer.valueOf(actorSpec));
			};

			assertEquals(expected, authorityTiers.authenticated(actor));
		}

		@Test
		void hasRoleIsTrueForADirectlyGrantedRole() {
			assertTrue(authorityTiers.hasRole(user(5, "ZFGC_FORUM_READ"), "ROLE_ZFGC_FORUM_READ"));
		}

		@Test
		void hasRoleIsTrueForARoleReachableThroughTheHierarchy() {
			User siteAdmin = user(5, "ZFGC_SITE_ADMIN");
			assertTrue(authorityTiers.hasRole(siteAdmin, "ROLE_ZFGC_PROFILE_ADMIN"));
			assertTrue(authorityTiers.hasRole(siteAdmin, "ROLE_ZFGC_FORUM_READ"));
		}

		@Test
		void hasRoleIsFalseForAnUnreachableRole() {
			assertFalse(authorityTiers.hasRole(user(5, "ZFGC_FORUM_READ"), "ROLE_ZFGC_SITE_ADMIN"));
		}

		@Test
		void isReadOnlyReflectsTheReadOnlyRole() {
			assertTrue(authorityTiers.isReadOnly(user(5, "ZFGC_READ_ONLY")));
			assertFalse(authorityTiers.isReadOnly(user(5, "ZFGC_USER")));
		}

		@Test
		void reachableRolesExpandsThroughTheHierarchy() {
			Set<String> reachable = authorityTiers.reachableRoles(user(5, "ZFGC_SITE_ADMIN"));

			assertTrue(reachable.contains("ROLE_ZFGC_SITE_ADMIN"));
			assertTrue(reachable.contains("ROLE_ZFGC_SITE_MODERATOR"));
			assertTrue(reachable.contains("ROLE_ZFGC_PROFILE_ADMIN"));
			assertTrue(reachable.contains("ROLE_ZFGC_WIKI_MODERATOR"));
			assertTrue(reachable.contains("ROLE_ZFGC_FORUM_MODERATE"));
			assertTrue(reachable.contains("ROLE_ZFGC_FORUM_WRITE"));
			assertTrue(reachable.contains("ROLE_ZFGC_FORUM_READ"));
			assertTrue(reachable.contains("ROLE_ZFGC_PROFILE_WRITE"));
			assertTrue(reachable.contains("ROLE_ZFGC_PROFILE_READ"));
		}

		@Test
		void reachableRolesIsEmptyForNoPermissions() {
			assertEquals(Set.of(), authorityTiers.reachableRoles(user(5)));
		}

		@Test
		void reachableRolesIsEmptyForNullPermissions() {
			User userWithoutPermissionList = user(5);
			userWithoutPermissionList.setPermissions(null);
			assertEquals(Set.of(), authorityTiers.reachableRoles(userWithoutPermissionList));
		}
	}

	@Nested
	class ProfileAccessRules {

		private final com.zfgc.zfgbb.services.core.ProfileAccessRules rules =
				new com.zfgc.zfgbb.services.core.ProfileAccessRules(new AuthorityTiers(roleHierarchy()));

		private final User member = member();
		private final User moderator = moderator();
		private final User admin = siteAdmin();
		private final User readOnlyMember = readOnlyMember();
		private final User profileAdmin = profileAdmin();
		private final User guest = guest();

		@Test
		void privateProfileVisibleToSelfOrProfileAdminOnly() {
			assertTrue(rules.canViewPrivateProfile(member, MEMBER_ID));
			assertTrue(rules.canViewPrivateProfile(profileAdmin, OTHER_ID));
			assertTrue(rules.canViewPrivateProfile(admin, OTHER_ID));
			assertFalse(rules.canViewPrivateProfile(member, OTHER_ID));
			assertFalse(rules.canViewPrivateProfile(moderator, OTHER_ID));
			assertFalse(rules.canViewPrivateProfile(guest, OTHER_ID));
		}

		@Test
		void permittedProfileActionsReflectTierAndReadOnly() {
			assertTrue(rules.permittedProfileActions(admin, OTHER_ID).contains("profile.award.grant"));
			assertFalse(rules.permittedProfileActions(member, OTHER_ID).contains("profile.award.grant"));
			assertTrue(rules.permittedProfileActions(member, MEMBER_ID).contains("profile.edit"));
			assertFalse(rules.permittedProfileActions(readOnlyMember, 13).contains("profile.edit"));
			assertTrue(rules.permittedProfileActions(member, MEMBER_ID).contains("profile.view.private"));
			assertTrue(rules.permittedProfileActions(guest, OTHER_ID).isEmpty());
		}
	}

	@Nested
	class ProfileUpdateContract {
		@Test
		void omittedFieldsRemainAbsentAndExplicitNullIsPresent() {
			UpdateUserProfileRequest request = new UpdateUserProfileRequest();
			request.setDisplayName("Link");
			request.setAvatarId(null);
			assertTrue(request.displayNamePresent());
			assertEquals("Link", request.displayName());
			assertFalse(request.personalTextPresent());
			assertTrue(request.avatarIdPresent());
			assertEquals(null, request.avatarId());
		}

		@Test
		void birthDateIsTyped() {
			UpdateUserProfileRequest request = new UpdateUserProfileRequest();
			request.setBirthDate(java.time.LocalDate.of(2000, 1, 2));
			assertEquals(java.time.LocalDate.of(2000, 1, 2), request.birthDate());
		}

		@Test
		void explicitNullPrivacyFlagsRemainPresentForServiceValidation() {
			UpdateUserProfileRequest request = new UpdateUserProfileRequest();
			request.setHideEmailFlag(null);
			request.setHideOnlineStatus(null);
			assertTrue(request.hideEmailFlagPresent());
			assertTrue(request.hideOnlineStatusPresent());
			assertEquals(null, request.hideEmailFlag());
			assertEquals(null, request.hideOnlineStatus());
			assertThrows(ZfgcInvalidRequestException.class,
					() -> ReflectionTestUtils.invokeMethod(new UserService(), "validateProfileUpdate", request));
		}
	}
}
