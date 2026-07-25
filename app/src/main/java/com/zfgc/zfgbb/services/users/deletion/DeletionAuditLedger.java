package com.zfgc.zfgbb.services.users.deletion;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.AccountDeletionAuditDbo;
import com.zfgc.zfgbb.dbo.AccountDeletionAuditDboExample;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dbo.ModerationLogDboExample;
import com.zfgc.zfgbb.mappers.AccountDeletionAuditDboMapper;
import com.zfgc.zfgbb.mappers.AccountDeletionRequestDboMapper;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.ModerationLogDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserDeletionMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeletionAuditLedger {

    private final AccountDeletionAuditDboMapper deletionAuditMapper;
    private final AccountDeletionRequestDboMapper deletionRequestMapper;
    private final ModerationLogDboMapper moderationLogMapper;
    private final ContentResourceDboMapper contentResourceMapper;
    private final UserDeletionMapper deletionMapper;

    public void recordDeletionRequestedAudit(Integer userId, String mode, OffsetDateTime requestedTs) {
        AccountDeletionAuditDbo audit = findOrCreateOpenAuditRow(userId, mode, requestedTs);
        audit.setMode(mode);
        audit.setRequestedTs(requestedTs);
        audit.setUpdatedTs(requestedTs);
        deletionAuditMapper.updateByPrimaryKey(audit);
    }

    public void stampAuditConfirmed(AccountDeletionRequestDbo request, OffsetDateTime now) {
        AccountDeletionAuditDbo audit = findOrCreateOpenAuditRow(request.getUserId(), request.getMode(),
                request.getRequestedTs() != null ? request.getRequestedTs() : now);
        if (audit.getConfirmedTs() != null)
            return;
        audit.setConfirmedTs(now);
        audit.setUpdatedTs(now);
        deletionAuditMapper.updateByPrimaryKey(audit);
    }

    public void stampAuditExecuted(Integer userId, OffsetDateTime now) {
        AccountDeletionAuditDboExample ex = new AccountDeletionAuditDboExample();
        ex.createCriteria().andSubjectUserIdSnapshotEqualTo(userId).andExecutedTsIsNull();
        ex.setOrderByClause("deletion_id desc");
        deletionAuditMapper.selectByExample(ex).stream().findFirst().ifPresent(audit -> {
            audit.setExecutedTs(now);
            audit.setUpdatedTs(now);
            deletionAuditMapper.updateByPrimaryKey(audit);
        });
    }

    public void recordOperatorRemediation(String action, String detail) {
        ModerationLogDboExample ex = new ModerationLogDboExample();
        ex.createCriteria().andActionEqualTo(action).andDetailEqualTo(detail);
        if (!moderationLogMapper.selectByExample(ex).isEmpty())
            return;
        ModerationLogDbo entry = new ModerationLogDbo();
        entry.setAction(action);
        entry.setDetail(detail);
        OffsetDateTime now = utcNow();
        entry.setLoggedTs(now);
        entry.setCreatedTs(now);
        entry.setUpdatedTs(now);
        moderationLogMapper.insertSelective(entry);
    }

    public Consumer<List<String>> blobPathSink(Optional<Integer> accountDeletionRequestId) {
        return blobPaths -> accountDeletionRequestId
                .ifPresent(requestId -> appendRecordedBlobPaths(requestId, blobPaths));
    }

    public void clearRecordedBlobPaths(Integer accountDeletionRequestId) {
        Optional.ofNullable(deletionRequestMapper.selectByPrimaryKey(accountDeletionRequestId)).ifPresent(request -> {
            request.setRecordedBlobPaths(null);
            request.setUpdatedTs(utcNow());
            deletionRequestMapper.updateByPrimaryKey(request);
        });
    }

    public int countOwnedContentResources(Integer userId) {
        ContentResourceDboExample ownedResourcesExample = new ContentResourceDboExample();
        ownedResourcesExample.createCriteria().andUploadedUserIdEqualTo(userId);
        return (int) contentResourceMapper.countByExample(ownedResourcesExample);
    }

    private void appendRecordedBlobPaths(Integer accountDeletionRequestId, List<String> blobPaths) {
        if (blobPaths.isEmpty())
            return;
        AccountDeletionRequestDbo request = deletionRequestMapper.selectByPrimaryKey(accountDeletionRequestId);
        if (request == null)
            return;
        String appended = String.join("\n", blobPaths);
        String existing = request.getRecordedBlobPaths();
        request.setRecordedBlobPaths(existing == null || existing.isBlank() ? appended : existing + "\n" + appended);
        request.setUpdatedTs(utcNow());
        deletionRequestMapper.updateByPrimaryKey(request);
    }

    private AccountDeletionAuditDbo findOrCreateOpenAuditRow(Integer userId, String mode, OffsetDateTime timestamp) {
        AccountDeletionAuditDboExample ex = new AccountDeletionAuditDboExample();
        ex.createCriteria().andSubjectUserIdSnapshotEqualTo(userId).andExecutedTsIsNull();
        ex.setOrderByClause("deletion_id desc");
        Optional<AccountDeletionAuditDbo> existing = deletionAuditMapper.selectByExample(ex).stream().findFirst();
        if (existing.isPresent())
            return existing.get();
        AccountDeletionAuditDbo audit = new AccountDeletionAuditDbo();
        audit.setSubjectUserIdSnapshot(userId);
        audit.setSubjectPseudonym(UUID.randomUUID().toString().replace("-", ""));
        audit.setMode(mode);
        audit.setInitiatedBy("SELF");
        audit.setRequestedTs(timestamp);
        audit.setMessageCount(deletionMapper.countOwnedMessages(userId));
        audit.setContentResourceCount(countOwnedContentResources(userId));
        audit.setCreatedTs(timestamp);
        audit.setUpdatedTs(timestamp);
        deletionAuditMapper.insertSelective(audit);
        return audit;
    }

    private OffsetDateTime utcNow() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
