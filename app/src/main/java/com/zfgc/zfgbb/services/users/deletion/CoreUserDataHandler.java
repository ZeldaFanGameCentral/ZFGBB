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
import com.zfgc.zfgbb.mappers.AccountDeletionRequestDboMapper;
import com.zfgc.zfgbb.mappers.AvatarDboMapper;
import com.zfgc.zfgbb.mappers.BrUserPermissionDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.EmailAddressDboMapper;
import com.zfgc.zfgbb.mappers.MigratorIdMapDboMapper;
import com.zfgc.zfgbb.mappers.NotificationSubscriptionDboMapper;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;
import com.zfgc.zfgbb.mappers.TeamMemberDboMapper;
import com.zfgc.zfgbb.mappers.UserAwardDboMapper;
import com.zfgc.zfgbb.mappers.UserBioInfoDboMapper;
import com.zfgc.zfgbb.mappers.UserContactInfoDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserDeletionMapper;
import com.zfgc.zfgbb.mappers.UserPermissionGroupAssocDboMapper;
import com.zfgc.zfgbb.mappers.UserSettingsDboMapper;
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

    private final UserDeletionMapper deletionMapper;
    private final UserDao userDao;
    private final AuthService authService;
    private final ContentService contentService;
    private final AccountDeletionRequestDboMapper deletionRequestMapper;
    private final DeletionAuditLedger deletionAuditLedger;
    private final BrUserPermissionDboMapper brUserPermissionMapper;
    private final UserPermissionGroupAssocDboMapper userPermissionGroupAssocMapper;
    private final UserSettingsDboMapper userSettingsMapper;
    private final UserContactInfoDboMapper userContactInfoMapper;
    private final EmailAddressDboMapper emailAddressMapper;
    private final TeamMemberDboMapper teamMemberMapper;
    private final UserAwardDboMapper userAwardMapper;
    private final NotificationSubscriptionDboMapper notificationSubscriptionMapper;
    private final UserBioInfoDboMapper userBioInfoMapper;
    private final AvatarDboMapper avatarMapper;
    private final ReactionDboMapper reactionMapper;
    private final ContentEntityDboMapper contentEntityMapper;
    private final ContentResourceDboMapper contentResourceMapper;
    private final MigratorIdMapDboMapper migratorIdMapDboMapper;

    public Integer ensureSentinelUser() {
        Optional<Integer> existing = deletionMapper.findUserIdBySsoKey(SENTINEL_SSO_KEY);
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
        return deletionMapper.listUsers();
    }

    public Optional<AccountDeletionRequestDbo> findDeletionRequest(Integer accountDeletionRequestId) {
        return Optional.ofNullable(deletionRequestMapper.selectByPrimaryKey(accountDeletionRequestId));
    }

    public void assertSelfDeletionAllowed(Integer userId) {
        if (deletionMapper.findUserIdBySsoKey(SENTINEL_SSO_KEY).filter(userId::equals).isPresent())
            throw new ZfgcInvalidRequestException("The deleted-user account cannot be deleted.");
        deletionMapper.acquireAdminRosterLock();
        if (deletionMapper.isSiteAdmin(userId) && deletionMapper.countSiteAdmins() <= 1)
            throw new ZfgcInvalidRequestException("The last site administrator cannot delete their own account.");
    }

    public boolean adminReplacementRequired(Integer userId) {
        return deletionMapper.isSiteAdmin(userId) && deletionMapper.countSiteAdmins() <= 1;
    }

    public void neutralizeAccount(Integer accountDeletionRequestId) {
        AccountDeletionRequestDbo request = findDeletionRequest(accountDeletionRequestId)
                .orElseThrow(ZfgcNotFoundException::new);
        Integer userId = request.getUserId();
        assertSelfDeletionAllowed(userId);
        String subjectUserName = deletionMapper.findUserName(userId).orElse(null);
        if (request.getAvatarIdSnapshot() == null)
            deletionMapper.findBioAvatarId(userId).ifPresent(request::setAvatarIdSnapshot);
        neutralizeUserIdentity(userId);
        scrubMigratedModerationTargetsByName(subjectUserName);
        OffsetDateTime now = utcNow();
        deletionAuditLedger.stampAuditConfirmed(request, now);
        AccountDeletionRequestDbo executingTransition = new AccountDeletionRequestDbo();
        executingTransition.setStatus(REQUEST_STATUS_EXECUTING);
        executingTransition.setConfirmedTs(request.getConfirmedTs() != null ? request.getConfirmedTs() : now);
        executingTransition.setAvatarIdSnapshot(request.getAvatarIdSnapshot());
        executingTransition.setUpdatedTs(now);
        AccountDeletionRequestDboExample stillConfirmed = new AccountDeletionRequestDboExample();
        stillConfirmed.createCriteria()
                .andAccountDeletionRequestIdEqualTo(accountDeletionRequestId)
                .andStatusEqualTo(REQUEST_STATUS_CONFIRMED);
        deletionRequestMapper.updateByExampleSelective(executingTransition, stillConfirmed);
    }

    public void neutralizeUserIdentity(Integer userId) {
        deletionMapper.neutralizeUserRow(userId, SENTINEL_SSO_KEY + userId);
        deletionMapper.scrubUserBioInfo(userId);
        authService.revokeAllForUser(userId);
        BrUserPermissionDboExample brUserPermissionExample = new BrUserPermissionDboExample();
        brUserPermissionExample.createCriteria().andUserIdEqualTo(userId);
        brUserPermissionMapper.deleteByExample(brUserPermissionExample);
        UserPermissionGroupAssocDboExample userPermissionGroupAssocExample = new UserPermissionGroupAssocDboExample();
        userPermissionGroupAssocExample.createCriteria().andUserIdEqualTo(userId);
        userPermissionGroupAssocMapper.deleteByExample(userPermissionGroupAssocExample);
        deletionMapper.deleteUserContactTypes(userId);
        UserSettingsDboExample userSettingsExample = new UserSettingsDboExample();
        userSettingsExample.createCriteria().andUserIdEqualTo(userId);
        userSettingsMapper.deleteByExample(userSettingsExample);
        List<Integer> emailAddressIds = deletionMapper.findEmailAddressIds(userId);
        UserContactInfoDboExample userContactInfoExample = new UserContactInfoDboExample();
        userContactInfoExample.createCriteria().andUserIdEqualTo(userId);
        userContactInfoMapper.deleteByExample(userContactInfoExample);
        releaseEmailAddresses(emailAddressIds);
    }

    public void releaseEmailAddresses(List<Integer> emailAddressIds) {
        for (Integer emailAddressId : emailAddressIds.stream().distinct().toList()) {
            deletionMapper.deleteEmailAddressIfUnreferenced(emailAddressId);
            if (emailAddressMapper.selectByPrimaryKey(emailAddressId) != null)
                deletionAuditLedger.recordOperatorRemediation(REMEDIATION_SHARED_EMAIL,
                        "email_address_id=" + emailAddressId
                                + " is shared with other accounts and was retained; operator remediation required");
        }
    }

    public void cancelDeletionRequest(Integer accountDeletionRequestId) {
        findDeletionRequest(accountDeletionRequestId).ifPresent(request -> {
            request.setStatus(REQUEST_STATUS_CANCELLED);
            request.setUpdatedTs(utcNow());
            deletionRequestMapper.updateByPrimaryKey(request);
        });
    }

    public void completePurge(Integer accountDeletionRequestId) {
        findDeletionRequest(accountDeletionRequestId).ifPresent(request -> {
            OffsetDateTime now = utcNow();
            request.setStatus(REQUEST_STATUS_COMPLETED);
            request.setPurgeCursor(REQUEST_STATUS_COMPLETED);
            request.setUpdatedTs(now);
            deletionRequestMapper.updateByPrimaryKey(request);
            deletionAuditLedger.stampAuditExecuted(request.getUserId(), now);
        });
    }

    public Optional<String> findPrimaryEmailAddress(Integer userId) {
        return deletionMapper.findPrimaryEmailAddress(userId);
    }

    public void orphanUserAssociations(Integer userId) {
        TeamMemberDboExample teamMemberExample = new TeamMemberDboExample();
        teamMemberExample.createCriteria().andUserIdEqualTo(userId);
        teamMemberMapper.deleteByExample(teamMemberExample);
        UserAwardDboExample userAwardExample = new UserAwardDboExample();
        userAwardExample.createCriteria().andUserIdEqualTo(userId);
        userAwardMapper.deleteByExample(userAwardExample);
        NotificationSubscriptionDboExample notificationSubscriptionExample = new NotificationSubscriptionDboExample();
        notificationSubscriptionExample.createCriteria().andUserIdEqualTo(userId);
        notificationSubscriptionMapper.deleteByExample(notificationSubscriptionExample);
    }

    public List<String> purgeBioInfoAndAvatar(Integer userId, Optional<Integer> accountDeletionRequestId) {
        Optional<Integer> avatarId = accountDeletionRequestId
                .flatMap(this::findDeletionRequest)
                .map(AccountDeletionRequestDbo::getAvatarIdSnapshot)
                .or(() -> deletionMapper.findBioAvatarId(userId));
        UserBioInfoDboExample bioInfoExample = new UserBioInfoDboExample();
        bioInfoExample.createCriteria().andUserIdEqualTo(userId);
        userBioInfoMapper.deleteByExample(bioInfoExample);
        if (avatarId.isEmpty())
            return List.of();
        Optional<Integer> avatarResourceId = deletionMapper.findAvatarContentResourceId(avatarId.get());
        avatarMapper.deleteByPrimaryKey(avatarId.get());
        return avatarResourceId
                .map(resourceId -> deleteContentResourcesIfUnreferenced(blobPathSink(accountDeletionRequestId),
                        List.of(resourceId)))
                .orElse(List.of());
    }

    public PurgeBatchOutcome deleteUnreferencedOwnedContentResourcesBatch(Optional<Integer> accountDeletionRequestId,
            Integer userId, int chunkSize) {
        List<Integer> deletableResourceIds = deletionMapper.findOwnedUnreferencedContentResourceIds(userId, chunkSize);
        if (deletableResourceIds.isEmpty())
            return new PurgeBatchOutcome(0, List.of());
        List<String> blobPaths = deleteContentResourceRows(blobPathSink(accountDeletionRequestId),
                deletableResourceIds);
        return new PurgeBatchOutcome(deletableResourceIds.size(), blobPaths);
    }

    public void reassignRemainingContentResources(Integer userId, Integer sentinelId) {
        deletionMapper.reassignContentResources(userId, sentinelId);
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
        List<Integer> deletableResourceIds = deletionMapper.findUnreferencedContentResourceIds(candidateResourceIds);
        if (deletableResourceIds.isEmpty())
            return List.of();
        return deleteContentResourceRows(releasedBlobPathSink, deletableResourceIds);
    }

    public List<String> deleteContentResourceRows(Consumer<List<String>> releasedBlobPathSink,
            List<Integer> resourceIds) {
        ContentResourceDboExample ex = new ContentResourceDboExample();
        ex.createCriteria().andContentResourceIdIn(resourceIds);
        List<String> blobPaths = new ArrayList<>();
        for (ContentResourceDbo resource : contentResourceMapper.selectByExample(ex)) {
            try {
                blobPaths.add(contentService.storedFile(resource).toString());
            } catch (RuntimeException pathUnresolvable) {
            }
        }
        releasedBlobPathSink.accept(blobPaths);
        ContentResourceDboExample deletableResourcesExample = new ContentResourceDboExample();
        deletableResourcesExample.createCriteria().andContentResourceIdIn(resourceIds);
        contentResourceMapper.deleteByExample(deletableResourcesExample);
        return blobPaths;
    }

    public void deleteMigratorIdMapEntries(String entityType, List<Integer> zfgbbIds) {
        MigratorIdMapDboExample example = new MigratorIdMapDboExample();
        example.createCriteria().andEntityTypeEqualTo(entityType).andZfgbbIdIn(zfgbbIds);
        migratorIdMapDboMapper.deleteByExample(example);
    }

    public void deleteReactions(String reactableType, List<Integer> reactableIds) {
        ReactionDboExample reactionExample = new ReactionDboExample();
        reactionExample.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdIn(reactableIds);
        reactionMapper.deleteByExample(reactionExample);
    }

    private void scrubMigratedModerationTargetsByName(String subjectUserName) {
        if (subjectUserName == null || subjectUserName.isBlank())
            return;
        deletionMapper.scrubModerationLogTargetsByName(subjectUserName);
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
        return (int) contentEntityMapper.countByExample(ownedEntitiesExample);
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
