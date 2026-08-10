package com.zfgc.zfgbb.services.cms.merge;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.dataprovider.cms.CmsMergeDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.ProjectDataProvider;
import com.zfgc.zfgbb.model.cms.ContentMergeSide;
import com.zfgc.zfgbb.model.cms.MergeApplyRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CmsEntityMerger {

	private final CmsMergeDataProvider cmsMergeDataProvider;

	private final ProjectDataProvider projectDataProvider;

	public void applyMerge(MergeApplyRequest request) {
		switch (request) {
			case MergeApplyRequest(ContentMergeSide source, Integer entityId, ContentMergeSide target, Integer articleId)
					when target == ContentMergeSide.WIKI_PAGE
							&& (source == ContentMergeSide.PROJECT || source == ContentMergeSide.RESOURCE) ->
					cmsMergeDataProvider.linkEntityWiki(entityId, articleId);
			case MergeApplyRequest(ContentMergeSide source, Integer entityId, ContentMergeSide target, Integer threadId)
					when target == ContentMergeSide.THREAD
							&& (source == ContentMergeSide.PROJECT || source == ContentMergeSide.RESOURCE) ->
					projectDataProvider.linkProjectThread(entityId, threadId);
			case MergeApplyRequest(ContentMergeSide source, Integer sourceId, ContentMergeSide target, Integer targetId)
					when source == ContentMergeSide.PROJECT && target == ContentMergeSide.PROJECT ->
					cmsMergeDataProvider.mergeProjects(sourceId, targetId);
			default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Unsupported merge: " + request.sourceType() + ">" + request.targetType());
		}
	}
}
