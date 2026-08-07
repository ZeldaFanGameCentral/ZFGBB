package com.zfgc.zfgbb.services.users.deletion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDboExample;
import com.zfgc.zfgbb.dbo.PollDboExample;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import com.zfgc.zfgbb.dao.forum.FileAttachmentDao;
import com.zfgc.zfgbb.dao.forum.PersonalMessageRecipientDao;
import com.zfgc.zfgbb.dao.forum.PollDao;
import com.zfgc.zfgbb.dao.forum.UserPollChoiceDao;
import com.zfgc.zfgbb.dao.users.UserErasureDao;

import lombok.RequiredArgsConstructor;

@Component
@Order(1)
@RequiredArgsConstructor
public class ForumUserDataHandler implements UserDataHandler {

    private final UserErasureDao userErasureDao;
    private final PollDao pollDao;
    private final FileAttachmentDao fileAttachmentDao;
    private final UserPollChoiceDao userPollChoiceDao;
    private final PersonalMessageRecipientDao personalMessageRecipientDao;
    private final CoreUserDataHandler coreUserDataHandler;
    private final PlatformTransactionManager transactionManager;

    public PurgeBatchOutcome purgeOwnedMessagesBatch(Optional<Integer> accountDeletionRequestId, Integer userId,
            int chunkSize) {
        List<Integer> messageIds = userErasureDao.findOwnedMessageIds(userId, chunkSize);
        if (messageIds.isEmpty())
            return new PurgeBatchOutcome(0, List.of());
        List<String> blobPaths = purgeMessagesByIds(messageIds,
                coreUserDataHandler.blobPathSink(accountDeletionRequestId));
        return new PurgeBatchOutcome(messageIds.size(), blobPaths);
    }

    public List<String> purgeMessagesByIds(List<Integer> messageIds, Consumer<List<String>> releasedBlobPathSink) {
        if (messageIds.isEmpty())
            return List.of();
        return new TransactionTemplate(transactionManager)
                .execute(transaction -> purgeMessagesByIdsAtomically(messageIds, releasedBlobPathSink));
    }

    private List<String> purgeMessagesByIdsAtomically(List<Integer> messageIds,
            Consumer<List<String>> releasedBlobPathSink) {
        List<Integer> touchedThreadIds = userErasureDao.findThreadIdsForMessages(messageIds);
        userErasureDao.deleteAttachmentRefRewritesForMessages(messageIds);
        List<Integer> attachmentIds = userErasureDao.findAttachmentIdsForMessages(messageIds);
        List<Integer> releasedResourceIds = userErasureDao.findAttachmentContentResourceIds(messageIds);
        FileAttachmentDboExample attachmentsExample = new FileAttachmentDboExample();
        attachmentsExample.createCriteria().andMessageIdIn(messageIds);
        fileAttachmentDao.deleteWhere(attachmentsExample);
        if (!attachmentIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("ATTACHMENT", attachmentIds);
        List<Integer> candidateIpIds = userErasureDao.findHistoryIpAddressIds(messageIds);
        userErasureDao.deleteHistoryForMessages(messageIds);
        if (!candidateIpIds.isEmpty())
            userErasureDao.deleteUnreferencedIpAddresses(candidateIpIds);
        coreUserDataHandler.deleteReactions("MESSAGE", messageIds);
        coreUserDataHandler.deleteMigratorIdMapEntries("MESSAGE", messageIds);
        userErasureDao.deleteMessagesByIds(messageIds);
        gcThreadsEmptiedByDeletion(touchedThreadIds);
        if (!touchedThreadIds.isEmpty())
            userErasureDao.resequencePostInThread(touchedThreadIds);
        return coreUserDataHandler.deleteContentResourcesIfUnreferenced(releasedBlobPathSink, releasedResourceIds);
    }

    public int orphanOwnedMessagesBatch(Integer userId, Integer sentinelId, int chunkSize) {
        List<Integer> messageIds = userErasureDao.findOwnedMessageIds(userId, chunkSize);
        if (messageIds.isEmpty())
            return 0;
        List<Integer> candidateIpIds = userErasureDao.findHistoryIpAddressIds(messageIds);
        userErasureDao.scrubHistoryForMessages(messageIds);
        if (!candidateIpIds.isEmpty())
            userErasureDao.deleteUnreferencedIpAddresses(candidateIpIds);
        List<Integer> attachmentIds = userErasureDao.findAttachmentIdsForMessages(messageIds);
        if (!attachmentIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("ATTACHMENT", attachmentIds);
        userErasureDao.scrubAttachmentMigrationHashesForMessages(messageIds);
        coreUserDataHandler.deleteMigratorIdMapEntries("MESSAGE", messageIds);
        userErasureDao.reassignAndScrubMessages(messageIds, sentinelId);
        return messageIds.size();
    }

    public void purgeOwnedPolls(Integer userId) {
        List<Integer> pollIds = userErasureDao.findOwnedPollIds(userId);
        if (!pollIds.isEmpty()) {
            userErasureDao.deleteUserPollVotes(userId);
            userErasureDao.deleteUserPollChoices(userId);
            coreUserDataHandler.deleteMigratorIdMapEntries("POLL", pollIds);
            deleteOwnedPolls(userId);
        }
        deleteOwnVotesAndRecount(userId);
    }

    public void orphanOwnedPolls(Integer userId, Integer sentinelId) {
        List<Integer> pollIds = userErasureDao.findOwnedPollIds(userId);
        if (!pollIds.isEmpty()) {
            coreUserDataHandler.deleteMigratorIdMapEntries("POLL", pollIds);
            userErasureDao.reassignPolls(userId, sentinelId);
        }
        deleteOwnVotesAndRecount(userId);
    }

    private void deleteOwnedPolls(Integer userId) {
        PollDboExample ownedPollsExample = new PollDboExample();
        ownedPollsExample.createCriteria().andCreatedUserIdEqualTo(userId);
        pollDao.deleteWhere(ownedPollsExample);
    }

    private void deleteOwnVotesAndRecount(Integer userId) {
        List<Integer> votedChoiceIds = userErasureDao.findVotedPollChoiceIds(userId);
        UserPollChoiceDboExample ownVotesExample = new UserPollChoiceDboExample();
        ownVotesExample.createCriteria().andUserIdEqualTo(userId);
        userPollChoiceDao.deleteWhere(ownVotesExample);
        if (!votedChoiceIds.isEmpty())
            userErasureDao.recountPollChoiceVotes(votedChoiceIds);
    }

    public void purgePersonalMessages(Integer userId) {
        List<Integer> conversationIds = userErasureDao.findParticipantConversationIds(userId);
        userErasureDao.scrubSentPersonalMessages(userId);
        PersonalMessageRecipientDboExample recipientExample = new PersonalMessageRecipientDboExample();
        recipientExample.createCriteria().andRecipientUserIdEqualTo(userId);
        personalMessageRecipientDao.deleteWhere(recipientExample);
        if (conversationIds.isEmpty())
            return;
        userErasureDao.scrubPersonalMessageMigrationHashesInConversations(conversationIds);
        userErasureDao.scrubConversationMigrationHashes(conversationIds);
        userErasureDao.gcEmptyConversationsAmong(conversationIds);
    }

    public void purgeThreadsWithGc(Integer userId, Integer sentinelId) {
        List<Integer> ownedThreadIds = userErasureDao.findOwnedThreadIds(userId);
        if (!ownedThreadIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("THREAD", ownedThreadIds);
        gcThreadsEmptiedByDeletion(ownedThreadIds);
        userErasureDao.reassignThreads(userId, sentinelId);
    }

    public void gcThreadsEmptiedByDeletion(List<Integer> candidateThreadIds) {
        if (candidateThreadIds.isEmpty())
            return;
        List<Integer> emptyThreadIds = userErasureDao.findEmptyThreadIdsAmong(candidateThreadIds);
        if (emptyThreadIds.isEmpty())
            return;
        List<Integer> pollIds = userErasureDao.findPollIdsOnThreads(emptyThreadIds);
        if (!pollIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("POLL", pollIds);
        userErasureDao.deleteVotesForPollsOnThreads(emptyThreadIds);
        userErasureDao.deleteChoicesForPollsOnThreads(emptyThreadIds);
        PollDboExample pollsOnThreadsExample = new PollDboExample();
        pollsOnThreadsExample.createCriteria().andThreadIdIn(emptyThreadIds);
        pollDao.deleteWhere(pollsOnThreadsExample);
        coreUserDataHandler.deleteMigratorIdMapEntries("THREAD", emptyThreadIds);
        userErasureDao.deleteThreadsByIds(emptyThreadIds);
    }

    public void orphanThreads(Integer userId, Integer sentinelId) {
        List<Integer> ownedThreadIds = userErasureDao.findOwnedThreadIds(userId);
        if (!ownedThreadIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("THREAD", ownedThreadIds);
        userErasureDao.reassignThreads(userId, sentinelId);
    }

    public void scrubModerationAndReactionResidue(Integer userId) {
        userErasureDao.scrubIssuedWarnings(userId);
        userErasureDao.scrubReceivedWarningMigrationHashes(userId);
        userErasureDao.nullModerationLogActors(userId);
        userErasureDao.scrubModerationLogTargets(userId);
        userErasureDao.nullMigrationConflictResolvers(userId);
        userErasureDao.scrubGivenReactions(userId);
        userErasureDao.nullAwardGranters(userId);
    }

    @Override
    public List<String> purgeData(Integer userId, Optional<Integer> accountDeletionRequestId) {
        List<String> allReleasedBlobPaths = new ArrayList<>();
        while (true) {
            PurgeBatchOutcome outcome = purgeOwnedMessagesBatch(accountDeletionRequestId, userId, PURGE_CHUNK_SIZE);
            allReleasedBlobPaths.addAll(outcome.releasedBlobPaths());
            if (outcome.processedCount() == 0)
                break;
        }
        purgeOwnedPolls(userId);
        purgePersonalMessages(userId);
        scrubModerationAndReactionResidue(userId);
        purgeThreadsWithGc(userId, coreUserDataHandler.ensureSentinelUser());
        return allReleasedBlobPaths;
    }

    @Override
    public List<String> anonymizeData(Integer userId, Optional<Integer> accountDeletionRequestId) {
        Integer sentinelId = coreUserDataHandler.ensureSentinelUser();
        while (true) {
            int processedCount = orphanOwnedMessagesBatch(userId, sentinelId, PURGE_CHUNK_SIZE);
            if (processedCount == 0)
                break;
        }
        orphanOwnedPolls(userId, sentinelId);
        purgePersonalMessages(userId);
        scrubModerationAndReactionResidue(userId);
        orphanThreads(userId, sentinelId);
        return List.of();
    }
}
