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
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;
import com.zfgc.zfgbb.mappers.ReactionTypeDboMapper;
import com.zfgc.zfgbb.mappers.custom.ReactionUpsertMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mapstruct.reactions.ReactionTypeMap;
import com.zfgc.zfgbb.model.reactions.ReactionType;
import lombok.RequiredArgsConstructor;

@Repository
@UnfilteredBoardRead("board ids only")
@RequiredArgsConstructor
public class ReactionDataProvider {

	private final ReactionDboMapper reactionMapper;

	private final ReactionTypeDboMapper reactionTypeMapper;

	private final MessageDboMapper messageMapper;

	private final ThreadDboMapper threadMapper;

	private final BoardPermissionViewDboMapper boardPermissionViewDboMapper;

	private final ContentEntityDboMapper contentEntityMapper;

	private final WikiPageDboMapper wikiPageMapper;

	private final ReactionUpsertMapper reactionUpsertMapper;

	private final ReactionTypeMap reactionTypeMap;

	public List<ReactionType> getReactionTypes() {
		ReactionTypeDboExample example = new ReactionTypeDboExample();
		example.setOrderByClause("ordinal asc");
		return reactionTypeMapper.selectByExample(example).stream().map(reactionTypeMap::toModel).toList();
	}

	public List<ReactionDbo> getReactionsForTarget(String reactableType, Integer reactableId) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdEqualTo(reactableId);
		return reactionMapper.selectByExample(example);
	}

	public List<ReactionDbo> getReactionsForTargets(String reactableType, List<Integer> reactableIds) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdIn(reactableIds);
		return reactionMapper.selectByExample(example);
	}

	public List<ReactionDbo> findUserReactions(String reactableType, List<Integer> reactableIds, Integer userId) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdIn(reactableIds)
				.andReactorUserIdEqualTo(userId);
		return reactionMapper.selectByExample(example);
	}

	public Optional<ReactionDbo> findUserReaction(String reactableType, Integer reactableId, Integer userId) {
		ReactionDboExample example = new ReactionDboExample();
		example.createCriteria().andReactableTypeEqualTo(reactableType).andReactableIdEqualTo(reactableId)
				.andReactorUserIdEqualTo(userId);
		return reactionMapper.selectByExample(example).stream().findFirst();
	}

	public void insert(ReactionDbo reaction) {
		reactionMapper.insertSelective(reaction);
	}

	public void upsert(ReactionDbo reaction) {
		reactionUpsertMapper.upsertReaction(reaction.getReactableType(), reaction.getReactableId(),
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
				return contentEntityMapper.countByExample(example) > 0;
			}
			case "WIKI_PAGE" -> {
				WikiPageDboExample example = new WikiPageDboExample();
				example.createCriteria().andWikiPageIdEqualTo(reactableId);
				return wikiPageMapper.countByExample(example) > 0;
			}
			default -> {
				return false;
			}
		}
	}

	public void update(ReactionDbo reaction) {
		reactionMapper.updateByPrimaryKeySelective(reaction);
	}

	public void delete(Integer reactionId) {
		reactionMapper.deleteByPrimaryKey(reactionId);
	}

	public Map<Integer, Integer> getBoardIdsForMessages(List<Integer> messageIds) {
		if (messageIds == null || messageIds.isEmpty()) {
			return Map.of();
		}
		MessageDboExample messageExample = new MessageDboExample();
		messageExample.createCriteria().andMessageIdIn(messageIds);
		Map<Integer, Integer> threadByMessage = messageMapper.selectByExample(messageExample).stream()
				.collect(Collectors.toMap(MessageDbo::getMessageId, MessageDbo::getThreadId, (a, b) -> a));
		if (threadByMessage.isEmpty()) {
			return Map.of();
		}

		ThreadDboExample threadExample = new ThreadDboExample();
		threadExample.createCriteria().andThreadIdIn(new ArrayList<>(new HashSet<>(threadByMessage.values())));
		Map<Integer, Integer> boardByThread = threadMapper.selectByExample(threadExample).stream()
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
		return boardPermissionViewDboMapper.selectByExample(ex).stream()
				.map(BoardPermissionViewDbo::getPermissionId).collect(Collectors.toList());
	}
}
