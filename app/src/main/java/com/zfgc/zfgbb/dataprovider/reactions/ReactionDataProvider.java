package com.zfgc.zfgbb.dataprovider.reactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.dbo.ReactionTypeDboExample;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dao.forum.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.cms.ContentEntityDao;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.reactions.ReactionDao;
import com.zfgc.zfgbb.dao.reactions.ReactionTypeDao;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.dao.cms.WikiPageDao;
import com.zfgc.zfgbb.mapstruct.reactions.ReactionTypeMap;
import com.zfgc.zfgbb.model.reactions.ReactionType;
import lombok.RequiredArgsConstructor;

@Repository
@UnfilteredBoardRead("board ids only")
@RequiredArgsConstructor
public class ReactionDataProvider {

	private final ReactionDao reactionDao;

	private final ReactionTypeDao reactionTypeDao;

	private final MessageDao messageDao;

	private final ThreadDao threadDao;

	private final BoardPermissionViewDao boardPermissionViewDao;

	private final ContentEntityDao contentEntityDao;

	private final WikiPageDao wikiPageDao;

	private final ReactionTypeMap reactionTypeMap;

	public List<ReactionType> getReactionTypes() {
		ReactionTypeDboExample example = new ReactionTypeDboExample();
		example.setOrderByClause("ordinal asc");
		return reactionTypeDao.get(example).stream().map(reactionTypeMap::toModel).toList();
	}

	public List<ReactionDbo> getReactionsForTarget(String reactableType, Integer reactableId) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdEqualTo(reactableId);
		return reactionDao.get(example);
	}

	public List<ReactionDbo> getReactionsForTargets(String reactableType, List<Integer> reactableIds) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdIn(reactableIds);
		return reactionDao.get(example);
	}

	public List<ReactionDbo> findUserReactions(String reactableType, List<Integer> reactableIds, Integer userId) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdIn(reactableIds)
				.andReactorUserIdEqualTo(userId);
		return reactionDao.get(example);
	}

	public Optional<ReactionDbo> findUserReaction(String reactableType, Integer reactableId, Integer userId) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdEqualTo(reactableId)
				.andReactorUserIdEqualTo(userId);
		return reactionDao.getOne(example);
	}

	public void insert(ReactionDbo reaction) {
		reactionDao.insertSelective(reaction);
	}

	public void upsert(ReactionDbo reaction) {
		reactionDao.upsert(reaction.getReactableType(), reaction.getReactableId(),
				reaction.getReactorUserId(), reaction.getReactionTypeId());
	}

	public boolean targetExists(String reactableType, Integer reactableId) {
		if (reactableId == null) {
			return false;
		}
		switch (reactableType) {
			case "PROJECT", "RESOURCE" -> {
				ContentEntityDboExample example = new ContentEntityDboExample();
				example.createCriteria().andContentEntityIdEqualTo(reactableId).andEntityTypeEqualTo(reactableType);
				return contentEntityDao.exists(example);
			}
			case "WIKI_PAGE" -> {
				WikiPageDboExample example = new WikiPageDboExample();
				example.createCriteria().andWikiPageIdEqualTo(reactableId);
				return wikiPageDao.exists(example);
			}
			default -> {
				return false;
			}
		}
	}

	public void update(ReactionDbo reaction) {
		reactionDao.updateSelective(reaction);
	}

	public void delete(Integer reactionId) {
		reactionDao.delete(reactionId);
	}

	public Map<Integer, Integer> getBoardIdsForMessages(List<Integer> messageIds) {
		if (messageIds == null || messageIds.isEmpty()) {
			return Map.of();
		}
		MessageDboExample messageExample = new MessageDboExample();
		messageExample.createCriteria().andMessageIdIn(messageIds);
		Map<Integer, Integer> threadByMessage = messageDao.get(messageExample).stream()
				.collect(Collectors.toMap(MessageDbo::getMessageId, MessageDbo::getThreadId, (a, b) -> a));
		if (threadByMessage.isEmpty()) {
			return Map.of();
		}

		ThreadDboExample threadExample = new ThreadDboExample();
		threadExample.createCriteria().andThreadIdIn(new ArrayList<>(new HashSet<>(threadByMessage.values())));
		Map<Integer, Integer> boardByThread = threadDao.get(threadExample).stream()
				.collect(Collectors.toMap(ThreadDbo::getThreadId, ThreadDbo::getBoardId, (a, b) -> a));

		Map<Integer, Integer> boardByMessage = new HashMap<>();
		for (Map.Entry<Integer, Integer> entry : threadByMessage.entrySet()) {
			Integer boardId = boardByThread.get(entry.getValue());
			if (boardId != null) {
				boardByMessage.put(entry.getKey(), boardId);
			}
		}
		return boardByMessage;
	}

	public List<Integer> getBoardPermissionIds(Integer boardId) {
		BoardPermissionViewDboExample ex = new BoardPermissionViewDboExample();
		ex.createCriteria().andBoardIdEqualTo(boardId);
		return boardPermissionViewDao.get(ex).stream()
				.map(BoardPermissionViewDbo::getPermissionId).collect(Collectors.toList());
	}
}
