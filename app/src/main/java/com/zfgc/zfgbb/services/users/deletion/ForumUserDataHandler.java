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
import com.zfgc.zfgbb.mappers.FileAttachmentDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageRecipientDboMapper;
import com.zfgc.zfgbb.mappers.PollDboMapper;
import com.zfgc.zfgbb.mappers.UserPollChoiceDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserDeletionMapper;

import lombok.RequiredArgsConstructor;

@Component
@Order(1)
@RequiredArgsConstructor
public class ForumUserDataHandler implements UserDataHandler {

    private final UserDeletionMapper deletionMapper;
    private final PollDboMapper pollMapper;
    private final FileAttachmentDboMapper fileAttachmentMapper;
    private final UserPollChoiceDboMapper userPollChoiceMapper;
    private final PersonalMessageRecipientDboMapper personalMessageRecipientMapper;
    private final CoreUserDataHandler coreUserDataHandler;
    private final PlatformTransactionManager transactionManager;

    public PurgeBatchOutcome purgeOwnedMessagesBatch(Optional<Integer> accountDeletionRequestId, Integer userId,
            int chunkSize) {
        List<Integer> messageIds = deletionMapper.findOwnedMessageIds(userId, chunkSize);
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
        List<Integer> touchedThreadIds = deletionMapper.findThreadIdsForMessages(messageIds);
        deletionMapper.deleteAttachmentRefRewritesForMessages(messageIds);
        List<Integer> attachmentIds = deletionMapper.findAttachmentIdsForMessages(messageIds);
        List<Integer> releasedResourceIds = deletionMapper.findAttachmentContentResourceIds(messageIds);
        FileAttachmentDboExample attachmentsExample = new FileAttachmentDboExample();
        attachmentsExample.createCriteria().andMessageIdIn(messageIds);
        fileAttachmentMapper.deleteByExample(attachmentsExample);
        if (!attachmentIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("ATTACHMENT", attachmentIds);
        List<Integer> candidateIpIds = deletionMapper.findHistoryIpAddressIds(messageIds);
        deletionMapper.deleteHistoryForMessages(messageIds);
        if (!candidateIpIds.isEmpty())
            deletionMapper.deleteUnreferencedIpAddresses(candidateIpIds);
        coreUserDataHandler.deleteReactions("MESSAGE", messageIds);
        coreUserDataHandler.deleteMigratorIdMapEntries("MESSAGE", messageIds);
        deletionMapper.deleteMessagesByIds(messageIds);
        gcThreadsEmptiedByDeletion(touchedThreadIds);
        if (!touchedThreadIds.isEmpty())
            deletionMapper.resequencePostInThread(touchedThreadIds);
        return coreUserDataHandler.deleteContentResourcesIfUnreferenced(releasedBlobPathSink, releasedResourceIds);
    }

    public int orphanOwnedMessagesBatch(Integer userId, Integer sentinelId, int chunkSize) {
        List<Integer> messageIds = deletionMapper.findOwnedMessageIds(userId, chunkSize);
        if (messageIds.isEmpty())
            return 0;
        List<Integer> candidateIpIds = deletionMapper.findHistoryIpAddressIds(messageIds);
        deletionMapper.scrubHistoryForMessages(messageIds);
        if (!candidateIpIds.isEmpty())
            deletionMapper.deleteUnreferencedIpAddresses(candidateIpIds);
        List<Integer> attachmentIds = deletionMapper.findAttachmentIdsForMessages(messageIds);
        if (!attachmentIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("ATTACHMENT", attachmentIds);
        deletionMapper.scrubAttachmentMigrationHashesForMessages(messageIds);
        coreUserDataHandler.deleteMigratorIdMapEntries("MESSAGE", messageIds);
        deletionMapper.reassignAndScrubMessages(messageIds, sentinelId);
        return messageIds.size();
    }

    public void purgeOwnedPolls(Integer userId) {
        List<Integer> pollIds = deletionMapper.findOwnedPollIds(userId);
        if (!pollIds.isEmpty()) {
            deletionMapper.deleteUserPollVotes(userId);
            deletionMapper.deleteUserPollChoices(userId);
            coreUserDataHandler.deleteMigratorIdMapEntries("POLL", pollIds);
            deleteOwnedPolls(userId);
        }
        deleteOwnVotesAndRecount(userId);
    }

    public void orphanOwnedPolls(Integer userId, Integer sentinelId) {
        List<Integer> pollIds = deletionMapper.findOwnedPollIds(userId);
        if (!pollIds.isEmpty()) {
            coreUserDataHandler.deleteMigratorIdMapEntries("POLL", pollIds);
            deletionMapper.reassignPolls(userId, sentinelId);
        }
        deleteOwnVotesAndRecount(userId);
    }

    private void deleteOwnedPolls(Integer userId) {
        PollDboExample ownedPollsExample = new PollDboExample();
        ownedPollsExample.createCriteria().andCreatedUserIdEqualTo(userId);
        pollMapper.deleteByExample(ownedPollsExample);
    }

    private void deleteOwnVotesAndRecount(Integer userId) {
        List<Integer> votedChoiceIds = deletionMapper.findVotedPollChoiceIds(userId);
        UserPollChoiceDboExample ownVotesExample = new UserPollChoiceDboExample();
        ownVotesExample.createCriteria().andUserIdEqualTo(userId);
        userPollChoiceMapper.deleteByExample(ownVotesExample);
        if (!votedChoiceIds.isEmpty())
            deletionMapper.recountPollChoiceVotes(votedChoiceIds);
    }

    public void purgePersonalMessages(Integer userId) {
        List<Integer> conversationIds = deletionMapper.findParticipantConversationIds(userId);
        deletionMapper.scrubSentPersonalMessages(userId);
        PersonalMessageRecipientDboExample recipientExample = new PersonalMessageRecipientDboExample();
        recipientExample.createCriteria().andRecipientUserIdEqualTo(userId);
        personalMessageRecipientMapper.deleteByExample(recipientExample);
        if (conversationIds.isEmpty())
            return;
        deletionMapper.scrubPersonalMessageMigrationHashesInConversations(conversationIds);
        deletionMapper.scrubConversationMigrationHashes(conversationIds);
        deletionMapper.gcEmptyConversationsAmong(conversationIds);
    }

    public void purgeThreadsWithGc(Integer userId, Integer sentinelId) {
        List<Integer> ownedThreadIds = deletionMapper.findOwnedThreadIds(userId);
        if (!ownedThreadIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("THREAD", ownedThreadIds);
        gcThreadsEmptiedByDeletion(ownedThreadIds);
        deletionMapper.reassignThreads(userId, sentinelId);
    }

    public void gcThreadsEmptiedByDeletion(List<Integer> candidateThreadIds) {
        if (candidateThreadIds.isEmpty())
            return;
        List<Integer> emptyThreadIds = deletionMapper.findEmptyThreadIdsAmong(candidateThreadIds);
        if (emptyThreadIds.isEmpty())
            return;
        List<Integer> pollIds = deletionMapper.findPollIdsOnThreads(emptyThreadIds);
        if (!pollIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("POLL", pollIds);
        deletionMapper.deleteVotesForPollsOnThreads(emptyThreadIds);
        deletionMapper.deleteChoicesForPollsOnThreads(emptyThreadIds);
        PollDboExample pollsOnThreadsExample = new PollDboExample();
        pollsOnThreadsExample.createCriteria().andThreadIdIn(emptyThreadIds);
        pollMapper.deleteByExample(pollsOnThreadsExample);
        coreUserDataHandler.deleteMigratorIdMapEntries("THREAD", emptyThreadIds);
        deletionMapper.deleteThreadsByIds(emptyThreadIds);
    }

    public void orphanThreads(Integer userId, Integer sentinelId) {
        List<Integer> ownedThreadIds = deletionMapper.findOwnedThreadIds(userId);
        if (!ownedThreadIds.isEmpty())
            coreUserDataHandler.deleteMigratorIdMapEntries("THREAD", ownedThreadIds);
        deletionMapper.reassignThreads(userId, sentinelId);
    }

    public void scrubModerationAndReactionResidue(Integer userId) {
        deletionMapper.scrubIssuedWarnings(userId);
        deletionMapper.scrubReceivedWarningMigrationHashes(userId);
        deletionMapper.nullModerationLogActors(userId);
        deletionMapper.scrubModerationLogTargets(userId);
        deletionMapper.nullMigrationConflictResolvers(userId);
        deletionMapper.scrubGivenReactions(userId);
        deletionMapper.nullAwardGranters(userId);
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
