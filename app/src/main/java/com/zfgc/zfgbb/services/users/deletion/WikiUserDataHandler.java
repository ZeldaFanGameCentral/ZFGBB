package com.zfgc.zfgbb.services.users.deletion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserDeletionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class WikiUserDataHandler implements UserDataHandler {

    public static final String REMEDIATION_TEMPLATE_LINKED_WIKI_PAGE = "ACCOUNT_DELETION_TEMPLATE_LINKED_WIKI_PAGE";

    private final UserDeletionMapper deletionMapper;
    private final WikiPageDboMapper wikiPageMapper;
    private final WikiPageRevisionDboMapper wikiPageRevisionMapper;
    private final ContentEntityDboMapper contentEntityMapper;
    private final CoreUserDataHandler coreUserDataHandler;

    public List<String> purgeOwnedCmsEntities(Optional<Integer> accountDeletionRequestId, Integer userId) {
        List<String> blobPaths = new ArrayList<>();
        for (String entityType : List.of("PROJECT", "RESOURCE")) {
            List<Integer> entityIds = deletionMapper.findOwnedContentEntityIdsByType(userId, entityType);
            if (entityIds.isEmpty())
                continue;
            coreUserDataHandler.deleteReactions(entityType, entityIds);
            List<Integer> releasedResourceIds = deletionMapper.findEntityReleasedContentResourceIds(entityIds);
            coreUserDataHandler.deleteMigratorIdMapEntries(entityType, entityIds);
            ContentEntityDboExample entitiesExample = new ContentEntityDboExample();
            entitiesExample.createCriteria().andContentEntityIdIn(entityIds);
            contentEntityMapper.deleteByExample(entitiesExample);
            blobPaths.addAll(coreUserDataHandler.deleteContentResourcesIfUnreferenced(
                    coreUserDataHandler.blobPathSink(accountDeletionRequestId), releasedResourceIds));
        }
        return blobPaths;
    }

    public List<String> purgeOwnedWikiPages(Optional<Integer> accountDeletionRequestId, Integer userId) {
        for (Integer templateLinkedPageId : deletionMapper.findOwnedTemplateLinkedWikiPageIds(userId)) {
            String detail = "wiki_page_id=" + templateLinkedPageId
                    + " is referenced by a content_template and was retained anonymized; "
                    + "operator must re-home or unlink the template";
            log.warn("operator remediation required: {} {}", REMEDIATION_TEMPLATE_LINKED_WIKI_PAGE, detail);
            coreUserDataHandler.recordOperatorRemediation(REMEDIATION_TEMPLATE_LINKED_WIKI_PAGE, detail);
        }
        List<Integer> pageIds = deletionMapper.findOwnedHardDeletableWikiPageIds(userId);
        if (pageIds.isEmpty())
            return List.of();
        coreUserDataHandler.deleteReactions("WIKI_PAGE", pageIds);
        deletionMapper.nullRetainedEntityWikiPageLinks(pageIds);
        List<Integer> releasedResourceIds = deletionMapper.findWikiPageContentResourceIds(pageIds);
        WikiPageRevisionDboExample wikiRevisionsExample = new WikiPageRevisionDboExample();
        wikiRevisionsExample.createCriteria().andWikiPageIdIn(pageIds);
        wikiPageRevisionMapper.deleteByExample(wikiRevisionsExample);
        coreUserDataHandler.deleteMigratorIdMapEntries("WIKI_PAGE", pageIds);
        WikiPageDboExample wikiPagesExample = new WikiPageDboExample();
        wikiPagesExample.createCriteria().andWikiPageIdIn(pageIds);
        wikiPageMapper.deleteByExample(wikiPagesExample);
        return coreUserDataHandler.deleteContentResourcesIfUnreferenced(
                coreUserDataHandler.blobPathSink(accountDeletionRequestId), releasedResourceIds);
    }

    public void scrubRetainedCmsContributions(Integer userId) {
        deletionMapper.nullWikiPageCreators(userId);
        deletionMapper.scrubRetainedWikiRevisions(userId);
        deletionMapper.scrubRetainedContentEntities(userId);
        deletionMapper.scrubProjectNewsAuthors(userId);
        deletionMapper.nullTeamCreators(userId);
    }

    @Override
    public List<String> purgeData(Integer userId, Optional<Integer> accountDeletionRequestId) {
        List<String> allReleasedBlobPaths = new ArrayList<>();
        allReleasedBlobPaths.addAll(purgeOwnedCmsEntities(accountDeletionRequestId, userId));
        allReleasedBlobPaths.addAll(purgeOwnedWikiPages(accountDeletionRequestId, userId));
        scrubRetainedCmsContributions(userId);
        return allReleasedBlobPaths;
    }

    @Override
    public List<String> anonymizeData(Integer userId, Optional<Integer> accountDeletionRequestId) {
        scrubRetainedCmsContributions(userId);
        return List.of();
    }
}
