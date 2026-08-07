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
import com.zfgc.zfgbb.dao.users.AccountDeletionAuditDao;
import com.zfgc.zfgbb.dao.users.AccountDeletionRequestDao;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dao.forum.ModerationLogDao;
import com.zfgc.zfgbb.dao.users.UserErasureDao;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeletionAuditLedger {

    private final AccountDeletionAuditDao accountDeletionAuditDao;
    private final AccountDeletionRequestDao accountDeletionRequestDao;
    private final ModerationLogDao moderationLogDao;
    private final ContentResourceDao contentResourceDao;
    private final UserErasureDao userErasureDao;

    public void recordDeletionRequestedAudit(Integer userId, String mode, OffsetDateTime requestedTs) {
        AccountDeletionAuditDbo audit = findOrCreateOpenAuditRow(userId, mode, requestedTs);
        audit.setMode(mode);
        audit.setRequestedTs(requestedTs);
        accountDeletionAuditDao.save(audit);
    }

    public void stampAuditConfirmed(AccountDeletionRequestDbo request, OffsetDateTime now) {
        AccountDeletionAuditDbo audit = findOrCreateOpenAuditRow(request.getUserId(), request.getMode(),
                request.getRequestedTs() != null ? request.getRequestedTs() : now);
        if (audit.getConfirmedTs() != null)
            return;
        audit.setConfirmedTs(now);
        accountDeletionAuditDao.save(audit);
    }

    public void stampAuditExecuted(Integer userId, OffsetDateTime now) {
        AccountDeletionAuditDboExample ex = new AccountDeletionAuditDboExample();
        ex.createCriteria().andSubjectUserIdSnapshotEqualTo(userId).andExecutedTsIsNull();
        ex.setOrderByClause("deletion_id desc");
        accountDeletionAuditDao.getOne(ex).ifPresent(audit -> {
            audit.setExecutedTs(now);
            accountDeletionAuditDao.save(audit);
        });
    }

    public void recordOperatorRemediation(String action, String detail) {
        ModerationLogDboExample ex = new ModerationLogDboExample();
        ex.createCriteria().andActionEqualTo(action).andDetailEqualTo(detail);
        if (!moderationLogDao.get(ex).isEmpty())
            return;
        ModerationLogDbo entry = new ModerationLogDbo();
        entry.setAction(action);
        entry.setDetail(detail);
        OffsetDateTime now = utcNow();
        entry.setLoggedTs(now);
        entry.setCreatedTs(now);
        moderationLogDao.insertSelective(entry);
    }

    public Consumer<List<String>> blobPathSink(Optional<Integer> accountDeletionRequestId) {
        return blobPaths -> accountDeletionRequestId
                .ifPresent(requestId -> appendRecordedBlobPaths(requestId, blobPaths));
    }

    public void clearRecordedBlobPaths(Integer accountDeletionRequestId) {
        accountDeletionRequestDao.find(accountDeletionRequestId).ifPresent(request -> {
            request.setRecordedBlobPaths(null);
            accountDeletionRequestDao.save(request);
        });
    }

    public int countOwnedContentResources(Integer userId) {
        ContentResourceDboExample ownedResourcesExample = new ContentResourceDboExample();
        ownedResourcesExample.createCriteria().andUploadedUserIdEqualTo(userId);
        return (int) contentResourceDao.count(ownedResourcesExample);
    }

    private void appendRecordedBlobPaths(Integer accountDeletionRequestId, List<String> blobPaths) {
        if (blobPaths.isEmpty())
            return;
        AccountDeletionRequestDbo request = accountDeletionRequestDao.find(accountDeletionRequestId).orElse(null);
        if (request == null)
            return;
        String appended = String.join("\n", blobPaths);
        String existing = request.getRecordedBlobPaths();
        request.setRecordedBlobPaths(existing == null || existing.isBlank() ? appended : existing + "\n" + appended);
        accountDeletionRequestDao.save(request);
    }

    private AccountDeletionAuditDbo findOrCreateOpenAuditRow(Integer userId, String mode, OffsetDateTime timestamp) {
        AccountDeletionAuditDboExample ex = new AccountDeletionAuditDboExample();
        ex.createCriteria().andSubjectUserIdSnapshotEqualTo(userId).andExecutedTsIsNull();
        ex.setOrderByClause("deletion_id desc");
        Optional<AccountDeletionAuditDbo> existing = accountDeletionAuditDao.getOne(ex);
        if (existing.isPresent())
            return existing.get();
        AccountDeletionAuditDbo audit = new AccountDeletionAuditDbo();
        audit.setSubjectUserIdSnapshot(userId);
        audit.setSubjectPseudonym(UUID.randomUUID().toString().replace("-", ""));
        audit.setMode(mode);
        audit.setInitiatedBy("SELF");
        audit.setRequestedTs(timestamp);
        audit.setMessageCount(userErasureDao.countOwnedMessages(userId));
        audit.setContentResourceCount(countOwnedContentResources(userId));
        audit.setCreatedTs(timestamp);
        accountDeletionAuditDao.insertSelective(audit);
        return audit;
    }

    private OffsetDateTime utcNow() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
