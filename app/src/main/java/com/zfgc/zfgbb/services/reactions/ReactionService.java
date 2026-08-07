package com.zfgc.zfgbb.services.reactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.dataprovider.reactions.ReactionDataProvider;
import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.exception.ZfgcUnauthorizedException;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.reactions.ContentReactionSummary;
import com.zfgc.zfgbb.model.reactions.ReactionRequest;
import com.zfgc.zfgbb.model.reactions.ReactionTally;
import com.zfgc.zfgbb.model.reactions.ReactionType;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReactionService {

	private static final Set<String> ALLOWED_TYPES = Set.of("MESSAGE", "PROJECT", "RESOURCE", "WIKI_PAGE");

	private static final String MESSAGE_TYPE = "MESSAGE";

	private static final int MAX_SUMMARY_IDS = 200;

	private final ReactionDataProvider reactionDataProvider;

	private final AuthorityTiers authorityTiers;

	public List<ReactionType> getReactionTypes() {
		return reactionDataProvider.getReactionTypes();
	}

	public ContentReactionSummary getSummary(String reactableType, Integer reactableId, User user) {
		validateType(reactableType);
		if (MESSAGE_TYPE.equals(reactableType)) {
			authorizeMessage(reactableId, user);
		}
		Integer userId = user.getUserId();
		List<ReactionType> reactionTypes = reactionDataProvider.getReactionTypes();
		List<ReactionDbo> reactions = reactionDataProvider.getReactionsForTarget(reactableType, reactableId);

		Map<Integer, Long> countsByType = reactions.stream()
				.collect(Collectors.groupingBy(ReactionDbo::getReactionTypeId, Collectors.counting()));

		Integer userReactionTypeId = null;
		if (userId != null && userId > 0) {
			userReactionTypeId = reactionDataProvider.findUserReaction(reactableType, reactableId, userId)
					.map(ReactionDbo::getReactionTypeId).orElse(null);
		}
		return buildSummary(reactableType, reactableId, reactionTypes, countsByType, userReactionTypeId);
	}

	public List<ContentReactionSummary> getSummaries(String reactableType, List<Integer> reactableIds, User user) {
		validateType(reactableType);
		if (reactableIds == null || reactableIds.isEmpty()) {
			return List.of();
		}
		if (reactableIds.size() > MAX_SUMMARY_IDS) {
			reactableIds = reactableIds.subList(0, MAX_SUMMARY_IDS);
		}
		if (MESSAGE_TYPE.equals(reactableType)) {
			Set<Integer> permitted = permittedMessageIds(reactableIds, user);
			reactableIds = reactableIds.stream().filter(permitted::contains).toList();
			if (reactableIds.isEmpty()) {
				return List.of();
			}
		}
		Integer userId = user.getUserId();
		List<ReactionType> reactionTypes = reactionDataProvider.getReactionTypes();
		Map<Integer, Map<Integer, Long>> countsByTarget = reactionDataProvider
				.getReactionsForTargets(reactableType, reactableIds).stream()
				.collect(Collectors.groupingBy(ReactionDbo::getReactableId,
						Collectors.groupingBy(ReactionDbo::getReactionTypeId, Collectors.counting())));

		Map<Integer, Integer> userReactionByTarget = new HashMap<>();
		if (userId != null && userId > 0) {
			for (ReactionDbo reaction : reactionDataProvider.findUserReactions(reactableType, reactableIds, userId)) {
				userReactionByTarget.put(reaction.getReactableId(), reaction.getReactionTypeId());
			}
		}

		List<ContentReactionSummary> summaries = new ArrayList<>();
		for (Integer reactableId : reactableIds) {
			summaries.add(buildSummary(reactableType, reactableId, reactionTypes,
					countsByTarget.getOrDefault(reactableId, Map.of()),
					userReactionByTarget.get(reactableId)));
		}
		return summaries;
	}

	private ContentReactionSummary buildSummary(String reactableType, Integer reactableId,
			List<ReactionType> reactionTypes, Map<Integer, Long> countsByType, Integer userReactionTypeId) {
		ContentReactionSummary summary = new ContentReactionSummary();
		summary.setReactableType(reactableType);
		summary.setReactableId(reactableId);

		int totalCount = 0;
		int totalPoints = 0;
		for (ReactionType reactionType : reactionTypes) {
			int count = countsByType.getOrDefault(reactionType.getReactionTypeId(), 0L).intValue();
			ReactionTally tally = new ReactionTally();
			tally.setReactionTypeId(reactionType.getReactionTypeId());
			tally.setCode(reactionType.getCode());
			tally.setLabel(reactionType.getLabel());
			tally.setIcon(reactionType.getIcon());
			tally.setPoints(reactionType.getPoints());
			tally.setOrdinal(reactionType.getOrdinal());
			tally.setCount(count);
			summary.getTallies().add(tally);
			totalCount += count;
			totalPoints += count * (reactionType.getPoints() == null ? 0 : reactionType.getPoints());
		}
		summary.setTotalCount(totalCount);
		summary.setTotalPoints(totalPoints);
		summary.setUserReactionTypeId(userReactionTypeId);
		return summary;
	}

	@Transactional
	public ContentReactionSummary toggle(ReactionRequest request, User user) {
		Integer userId = user.getUserId();
		requireSignedIn(userId);
		if (authorityTiers.isReadOnly(user))
			throw new ZfgcUnauthorizedException("Read-only accounts cannot react.", user);
		validateType(request.getReactableType());
		if (request.getReactableId() == null || request.getReactionTypeId() == null) {
			throw new ZfgcInvalidRequestException("reactableId and reactionTypeId are required.");
		}
		validateReactionType(request.getReactionTypeId());
		if (MESSAGE_TYPE.equals(request.getReactableType())) {
			authorizeMessage(request.getReactableId(), user);
		} else if (!reactionDataProvider.targetExists(request.getReactableType(), request.getReactableId())) {
			throw new ZfgcNotFoundException();
		}
		Optional<ReactionDbo> existing = reactionDataProvider.findUserReaction(
				request.getReactableType(), request.getReactableId(), userId);
		if (existing.isEmpty()) {
			ReactionDbo reaction = new ReactionDbo();
			reaction.setReactableType(request.getReactableType());
			reaction.setReactableId(request.getReactableId());
			reaction.setReactorUserId(userId);
			reaction.setReactionTypeId(request.getReactionTypeId());
			reactionDataProvider.upsert(reaction);
		} else if (existing.get().getReactionTypeId().equals(request.getReactionTypeId())) {
			reactionDataProvider.delete(existing.get().getReactionId());
		} else {
			ReactionDbo current = existing.get();
			current.setReactionTypeId(request.getReactionTypeId());
			reactionDataProvider.update(current);
		}
		return getSummary(request.getReactableType(), request.getReactableId(), user);
	}

	@Transactional
	public ContentReactionSummary remove(String reactableType, Integer reactableId, User user) {
		Integer userId = user.getUserId();
		requireSignedIn(userId);
		if (authorityTiers.isReadOnly(user))
			throw new ZfgcUnauthorizedException("Read-only accounts cannot react.", user);
		validateType(reactableType);
		if (MESSAGE_TYPE.equals(reactableType)) {
			authorizeMessage(reactableId, user);
		}
		reactionDataProvider.findUserReaction(reactableType, reactableId, userId)
				.ifPresent(existing -> reactionDataProvider.delete(existing.getReactionId()));
		return getSummary(reactableType, reactableId, user);
	}

	private void authorizeMessage(Integer messageId, User user) {
		if (!permittedMessageIds(List.of(messageId), user).contains(messageId)) {
			throw new ZfgcUnauthorizedException("Insufficient permissions for resource.", user);
		}
	}

	private Set<Integer> permittedMessageIds(List<Integer> messageIds, User user) {
		Map<Integer, Integer> boardByMessage = reactionDataProvider.getBoardIdsForMessages(messageIds);
		Map<Integer, Boolean> boardAllowed = new HashMap<>();
		Set<Integer> permitted = new HashSet<>();
		for (Map.Entry<Integer, Integer> entry : boardByMessage.entrySet()) {
			boolean allowed = boardAllowed.computeIfAbsent(entry.getValue(),
					boardId -> user.hasAnyPermissionId(reactionDataProvider.getBoardPermissionIds(boardId)));
			if (allowed) {
				permitted.add(entry.getKey());
			}
		}
		return permitted;
	}

	private void validateType(String reactableType) {
		if (reactableType == null || !ALLOWED_TYPES.contains(reactableType)) {
			throw new ZfgcNotFoundException();
		}
	}

	private void validateReactionType(Integer reactionTypeId) {
		boolean known = reactionDataProvider.getReactionTypes().stream()
				.map(ReactionType::getReactionTypeId)
				.anyMatch(reactionTypeId::equals);
		if (!known)
			throw new ZfgcInvalidRequestException("Unknown reaction type.");
	}

	private void requireSignedIn(Integer userId) {
		if (userId == null || userId <= 0) {
			throw new ZfgcInvalidRequestException("You must be signed in to react.");
		}
	}
}
