package com.zfgc.zfgbb.services.users.deletion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDbo;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDboExample;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDboExample;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.dbo.TeamMemberDboExample;
import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserPermissionGroupAssocDboExample;
import com.zfgc.zfgbb.dbo.UserSettingsDboExample;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.users.AccountDeletionRequestDao;
import com.zfgc.zfgbb.dao.users.AvatarDao;
import com.zfgc.zfgbb.dao.users.BrUserPermissionDao;
import com.zfgc.zfgbb.dao.cms.ContentEntityDao;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dao.users.EmailAddressDao;
import com.zfgc.zfgbb.dao.meta.MigratorIdMapDao;
import com.zfgc.zfgbb.dao.forum.NotificationSubscriptionDao;
import com.zfgc.zfgbb.dao.reactions.ReactionDao;
import com.zfgc.zfgbb.dao.users.TeamMemberDao;
import com.zfgc.zfgbb.dao.users.UserAwardDao;
import com.zfgc.zfgbb.dao.users.UserBioInfoDao;
import com.zfgc.zfgbb.dao.users.UserContactInfoDao;
import com.zfgc.zfgbb.dao.users.UserErasureDao;
import com.zfgc.zfgbb.dao.users.UserPermissionGroupAssocDao;
import com.zfgc.zfgbb.dao.users.UserSettingsDao;
import com.zfgc.zfgbb.model.users.UserSummary;
import com.zfgc.zfgbb.services.contentstore.ContentService;
import com.zfgc.zfgbb.services.auth.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class CoreUserDataHandler implements UserDataHandler {

    public static final String REQUEST_STATUS_PENDING = "PENDING";
    public static final String REQUEST_STATUS_SUPERSEDED = "SUPERSEDED";
    public static final String REQUEST_STATUS_CONFIRMED = "CONFIRMED";
    public static final String REQUEST_STATUS_EXECUTING = "EXECUTING";
    public static final String REQUEST_STATUS_COMPLETED = "COMPLETED";
    public static final String REQUEST_STATUS_CANCELLED = "CANCELLED";

    public static final String REMEDIATION_SHARED_EMAIL = "ACCOUNT_DELETION_SHARED_EMAIL";
    static final String SENTINEL_SSO_KEY = "__deleted__";
    static final String SENTINEL_DISPLAY_NAME = "[deleted]";

    private final UserErasureDao userErasureDao;
    private final UserDao userDao;
    private final AuthService authService;
    private final ContentService contentService;
    private final AccountDeletionRequestDao accountDeletionRequestDao;
    private final DeletionAuditLedger deletionAuditLedger;
    private final BrUserPermissionDao brUserPermissionDao;
    private final UserPermissionGroupAssocDao userPermissionGroupAssocDao;
    private final UserSettingsDao userSettingsDao;
    private final UserContactInfoDao userContactInfoDao;
    private final EmailAddressDao emailAddressDao;
    private final TeamMemberDao teamMemberDao;
    private final UserAwardDao userAwardDao;
    private final NotificationSubscriptionDao notificationSubscriptionDao;
    private final UserBioInfoDao userBioInfoDao;
    private final AvatarDao avatarDao;
    private final ReactionDao reactionDao;
    private final ContentEntityDao contentEntityDao;
    private final ContentResourceDao contentResourceDao;
    private final MigratorIdMapDao migratorIdMapDao;

    public Integer ensureSentinelUser() {
        Optional<Integer> existing = userErasureDao.findUserIdBySsoKey(SENTINEL_SSO_KEY);
        if (existing.isPresent())
            return existing.get();
        UserDbo sentinel = new UserDbo();
        sentinel.setSsoKey(SENTINEL_SSO_KEY);
        sentinel.setUserName(SENTINEL_SSO_KEY);
        sentinel.setDisplayName(SENTINEL_DISPLAY_NAME);
        sentinel.setActiveFlag(false);
        sentinel.setFailedLoginCount(0);
        userDao.save(sentinel);
        return sentinel.getUserId();
    }

    public List<UserSummary> listUsers() {
        return userErasureDao.listUsers();
    }

    public Optional<AccountDeletionRequestDbo> findDeletionRequest(Integer accountDeletionRequestId) {
        return accountDeletionRequestDao.find(accountDeletionRequestId);
    }

    public void assertSelfDeletionAllowed(Integer userId) {
        if (userErasureDao.findUserIdBySsoKey(SENTINEL_SSO_KEY).filter(userId::equals).isPresent())
            throw new ZfgcInvalidRequestException("The deleted-user account cannot be deleted.");
        userErasureDao.acquireAdminRosterLock();
        if (userErasureDao.isSiteAdmin(userId) && userErasureDao.countSiteAdmins() <= 1)
            throw new ZfgcInvalidRequestException("The last site administrator cannot delete their own account.");
    }

    public boolean adminReplacementRequired(Integer userId) {
        return userErasureDao.isSiteAdmin(userId) && userErasureDao.countSiteAdmins() <= 1;
    }

    public void neutralizeAccount(Integer accountDeletionRequestId) {
        AccountDeletionRequestDbo request = findDeletionRequest(accountDeletionRequestId)
                .orElseThrow(ZfgcNotFoundException::new);
        Integer userId = request.getUserId();
        assertSelfDeletionAllowed(userId);
        String subjectUserName = userErasureDao.findUserName(userId).orElse(null);
        if (request.getAvatarIdSnapshot() == null)
            userErasureDao.findBioAvatarId(userId).ifPresent(request::setAvatarIdSnapshot);
        neutralizeUserIdentity(userId);
        scrubMigratedModerationTargetsByName(subjectUserName);
        OffsetDateTime now = utcNow();
        deletionAuditLedger.stampAuditConfirmed(request, now);
        AccountDeletionRequestDbo executingTransition = new AccountDeletionRequestDbo();
        executingTransition.setStatus(REQUEST_STATUS_EXECUTING);
        executingTransition.setConfirmedTs(request.getConfirmedTs() != null ? request.getConfirmedTs() : now);
        executingTransition.setAvatarIdSnapshot(request.getAvatarIdSnapshot());
        AccountDeletionRequestDboExample stillConfirmed = new AccountDeletionRequestDboExample();
        stillConfirmed.createCriteria()
                .andAccountDeletionRequestIdEqualTo(accountDeletionRequestId)
                .andStatusEqualTo(REQUEST_STATUS_CONFIRMED);
        accountDeletionRequestDao.updateWhere(executingTransition, stillConfirmed);
    }

    public void neutralizeUserIdentity(Integer userId) {
        userErasureDao.neutralizeUserRow(userId, SENTINEL_SSO_KEY + userId);
        userErasureDao.scrubUserBioInfo(userId);
        authService.revokeAllForUser(userId);
        BrUserPermissionDboExample brUserPermissionExample = new BrUserPermissionDboExample();
        brUserPermissionExample.createCriteria().andUserIdEqualTo(userId);
        brUserPermissionDao.deleteWhere(brUserPermissionExample);
        UserPermissionGroupAssocDboExample userPermissionGroupAssocExample = new UserPermissionGroupAssocDboExample();
        userPermissionGroupAssocExample.createCriteria().andUserIdEqualTo(userId);
        userPermissionGroupAssocDao.deleteWhere(userPermissionGroupAssocExample);
        userErasureDao.deleteUserContactTypes(userId);
        UserSettingsDboExample userSettingsExample = new UserSettingsDboExample();
        userSettingsExample.createCriteria().andUserIdEqualTo(userId);
        userSettingsDao.deleteWhere(userSettingsExample);
        List<Integer> emailAddressIds = userErasureDao.findEmailAddressIds(userId);
        UserContactInfoDboExample userContactInfoExample = new UserContactInfoDboExample();
        userContactInfoExample.createCriteria().andUserIdEqualTo(userId);
        userContactInfoDao.deleteWhere(userContactInfoExample);
        releaseEmailAddresses(emailAddressIds);
    }

    public void releaseEmailAddresses(List<Integer> emailAddressIds) {
        for (Integer emailAddressId : emailAddressIds.stream().distinct().toList()) {
            userErasureDao.deleteEmailAddressIfUnreferenced(emailAddressId);
            if (emailAddressDao.existsWithPrimaryKey(emailAddressId))
                deletionAuditLedger.recordOperatorRemediation(REMEDIATION_SHARED_EMAIL,
                        "email_address_id=" + emailAddressId
                                + " is shared with other accounts and was retained; operator remediation required");
        }
    }

    public void cancelDeletionRequest(Integer accountDeletionRequestId) {
        findDeletionRequest(accountDeletionRequestId).ifPresent(request -> {
            request.setStatus(REQUEST_STATUS_CANCELLED);
            accountDeletionRequestDao.save(request);
        });
    }

    public void completePurge(Integer accountDeletionRequestId) {
        findDeletionRequest(accountDeletionRequestId).ifPresent(request -> {
            OffsetDateTime now = utcNow();
            request.setStatus(REQUEST_STATUS_COMPLETED);
            request.setPurgeCursor(REQUEST_STATUS_COMPLETED);
            accountDeletionRequestDao.save(request);
            deletionAuditLedger.stampAuditExecuted(request.getUserId(), now);
        });
    }

    public Optional<String> findPrimaryEmailAddress(Integer userId) {
        return userErasureDao.findPrimaryEmailAddress(userId);
    }

    public void orphanUserAssociations(Integer userId) {
        TeamMemberDboExample teamMemberExample = new TeamMemberDboExample();
        teamMemberExample.createCriteria().andUserIdEqualTo(userId);
        teamMemberDao.deleteWhere(teamMemberExample);
        UserAwardDboExample userAwardExample = new UserAwardDboExample();
        userAwardExample.createCriteria().andUserIdEqualTo(userId);
        userAwardDao.deleteWhere(userAwardExample);
        NotificationSubscriptionDboExample notificationSubscriptionExample = new NotificationSubscriptionDboExample();
        notificationSubscriptionExample.createCriteria().andUserIdEqualTo(userId);
        notificationSubscriptionDao.deleteWhere(notificationSubscriptionExample);
    }

    public List<String> purgeBioInfoAndAvatar(Integer userId, Optional<Integer> accountDeletionRequestId) {
        Optional<Integer> avatarId = accountDeletionRequestId
                .flatMap(this::findDeletionRequest)
                .map(AccountDeletionRequestDbo::getAvatarIdSnapshot)
                .or(() -> userErasureDao.findBioAvatarId(userId));
        UserBioInfoDboExample bioInfoExample = new UserBioInfoDboExample();
        bioInfoExample.createCriteria().andUserIdEqualTo(userId);
        userBioInfoDao.deleteWhere(bioInfoExample);
        if (avatarId.isEmpty())
            return List.of();
        Optional<Integer> avatarResourceId = userErasureDao.findAvatarContentResourceId(avatarId.get());
        avatarDao.delete(avatarId.get());
        return avatarResourceId
                .map(resourceId -> deleteContentResourcesIfUnreferenced(blobPathSink(accountDeletionRequestId),
                        List.of(resourceId)))
                .orElse(List.of());
    }

    public PurgeBatchOutcome deleteUnreferencedOwnedContentResourcesBatch(Optional<Integer> accountDeletionRequestId,
            Integer userId, int chunkSize) {
        List<Integer> deletableResourceIds = userErasureDao.findOwnedUnreferencedContentResourceIds(userId, chunkSize);
        if (deletableResourceIds.isEmpty())
            return new PurgeBatchOutcome(0, List.of());
        List<String> blobPaths = deleteContentResourceRows(blobPathSink(accountDeletionRequestId),
                deletableResourceIds);
        return new PurgeBatchOutcome(deletableResourceIds.size(), blobPaths);
    }

    public void reassignRemainingContentResources(Integer userId, Integer sentinelId) {
        userErasureDao.reassignContentResources(userId, sentinelId);
    }

    public void deleteMigratorUserMapEntry(Integer userId) {
        deleteMigratorIdMapEntries("USER", List.of(userId));
    }

    public void deleteUserRow(Integer userId) {
        userDao.delete(userId);
    }

    public Consumer<List<String>> blobPathSink(Optional<Integer> accountDeletionRequestId) {
        return deletionAuditLedger.blobPathSink(accountDeletionRequestId);
    }

    public void deleteBlobFiles(List<String> blobPaths) {
        for (String blobPath : blobPaths) {
            try {
                Files.deleteIfExists(Path.of(blobPath));
            } catch (IOException | RuntimeException blobDeleteFailure) {
                log.warn("stored blob {} could not be deleted; a later sweep may reconcile it", blobPath,
                        blobDeleteFailure);
            }
        }
    }

    public List<String> deleteContentResourcesIfUnreferenced(Consumer<List<String>> releasedBlobPathSink,
            List<Integer> candidateResourceIds) {
        if (candidateResourceIds.isEmpty())
            return List.of();
        List<Integer> deletableResourceIds = userErasureDao.findUnreferencedContentResourceIds(candidateResourceIds);
        if (deletableResourceIds.isEmpty())
            return List.of();
        return deleteContentResourceRows(releasedBlobPathSink, deletableResourceIds);
    }

    public List<String> deleteContentResourceRows(Consumer<List<String>> releasedBlobPathSink,
            List<Integer> resourceIds) {
        ContentResourceDboExample ex = new ContentResourceDboExample();
        ex.createCriteria().andContentResourceIdIn(resourceIds);
        List<String> blobPaths = new ArrayList<>();
        for (ContentResourceDbo resource : contentResourceDao.get(ex)) {
            try {
                blobPaths.add(contentService.storedFile(resource).toString());
            } catch (RuntimeException pathUnresolvable) {
            }
        }
        releasedBlobPathSink.accept(blobPaths);
        ContentResourceDboExample deletableResourcesExample = new ContentResourceDboExample();
        deletableResourcesExample.createCriteria().andContentResourceIdIn(resourceIds);
        contentResourceDao.deleteWhere(deletableResourcesExample);
        return blobPaths;
    }

    public void deleteMigratorIdMapEntries(String entityType, List<Integer> zfgbbIds) {
        MigratorIdMapDboExample example = new MigratorIdMapDboExample();
        example.createCriteria().andEntityTypeEqualTo(entityType).andZfgbbIdIn(zfgbbIds);
        migratorIdMapDao.deleteWhere(example);
    }

    public void deleteReactions(String reactableType, List<Integer> reactableIds) {
        ReactionDboExample reactionExample = new ReactionDboExample();
        reactionExample.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdIn(reactableIds);
        reactionDao.deleteWhere(reactionExample);
    }

    private void scrubMigratedModerationTargetsByName(String subjectUserName) {
        if (subjectUserName == null || subjectUserName.isBlank())
            return;
        userErasureDao.scrubModerationLogTargetsByName(subjectUserName);
    }

    public void recordOperatorRemediation(String action, String detail) {
        deletionAuditLedger.recordOperatorRemediation(action, detail);
    }

    private OffsetDateTime utcNow() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    public int countOwnedContentEntities(Integer userId, String entityType) {
        ContentEntityDboExample ownedEntitiesExample = new ContentEntityDboExample();
        ownedEntitiesExample.createCriteria().andCreatedUserIdEqualTo(userId).andEntityTypeEqualTo(entityType);
        return (int) contentEntityDao.count(ownedEntitiesExample);
    }

    @Override
    public List<String> purgeData(Integer userId, Optional<Integer> accountDeletionRequestId) {
        List<String> allReleasedBlobPaths =
                new ArrayList<>(purgeBioInfoAndAvatar(userId, accountDeletionRequestId));
        while (true) {
            PurgeBatchOutcome outcome = deleteUnreferencedOwnedContentResourcesBatch(
                    accountDeletionRequestId, userId, PURGE_CHUNK_SIZE);
            allReleasedBlobPaths.addAll(outcome.releasedBlobPaths());
            if (outcome.processedCount() == 0)
                break;
        }
        reassignRemainingContentResources(userId, ensureSentinelUser());
        deleteMigratorUserMapEntry(userId);
        deleteUserRow(userId);
        return allReleasedBlobPaths;
    }

    @Override
    public List<String> anonymizeData(Integer userId, Optional<Integer> accountDeletionRequestId) {
        orphanUserAssociations(userId);
        List<String> releasedBlobPaths = purgeBioInfoAndAvatar(userId, accountDeletionRequestId);
        reassignRemainingContentResources(userId, ensureSentinelUser());
        deleteMigratorUserMapEntry(userId);
        return releasedBlobPaths;
    }
}
