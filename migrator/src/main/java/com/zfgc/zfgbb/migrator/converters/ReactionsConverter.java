package com.zfgc.zfgbb.migrator.converters;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.dbo.ReactionTypeDbo;
import com.zfgc.zfgbb.dbo.ReactionTypeDboExample;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;
import com.zfgc.zfgbb.mappers.ReactionTypeDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogKarmaDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.queries.SmfResilientReadMapper;

@Component
public class ReactionsConverter extends AbstractConverter<Void> {

	@Override
	public JobType getType() {
		return JobType.REACTIONS;
	}

	@Autowired
	private ReactionDboMapper reactionMapper;

	@Autowired
	private ReactionTypeDboMapper reactionTypeMapper;

	@Autowired
	private SmfResilientReadMapper resilientReads;

	@Autowired
	private MigratorIdMapService idMap;

	@Autowired
	private MigratorTimestampMapper migratorTimestampMapper;

	private final String MSG_REGEX = "[0-9]+$";

	@Override
	@Transactional
	public Void convertToZfgbb() {
		Integer likeTypeId = reactionTypeIdByCode("LIKE");
		Integer dislikeTypeId = reactionTypeIdByCode("DISLIKE");
		Map<Integer, Integer> messageMap = idMap.getAllForType(LegacyEntityType.MESSAGE);
		Map<Integer, Integer> userMap = idMap.getAllForType(LegacyEntityType.USER);
		List<ReactionDbo> existingReactions = reactionMapper.selectByExample(new ReactionDboExample());
		Map<String, Integer> reactionIdByHash = existingReactions.stream()
				.filter(row -> row.getMigrationHash() != null)
				.collect(Collectors.toMap(ReactionDbo::getMigrationHash, ReactionDbo::getReactionId, (a, b) -> a));
		java.util.Set<String> seenReactionKeys = existingReactions.stream()
				.filter(row -> row.getReactorUserId() != null)
				.map(row -> row.getReactableType() + ":" + row.getReactableId() + ":" + row.getReactorUserId())
				.collect(Collectors.toCollection(java.util.HashSet::new));
		List<SMFLogKarmaDbWithBLOBs> karmaSMF = resilientReads.selectAllKarma();
		Pattern pattern = Pattern.compile(MSG_REGEX);

		for (SMFLogKarmaDbWithBLOBs smfKarma : karmaSMF) {
			Cancellable.check();
			if (smfKarma.getLink() == null) {
				continue;
			}
			Matcher matcher = pattern.matcher(smfKarma.getLink());
			if (!matcher.find()) {
				continue;
			}
			Integer smfMsgId = Integer.parseInt(matcher.group());
			Integer zfgbbMsgId = messageMap.get(smfMsgId);
			if (zfgbbMsgId == null) {
				continue;
			}
			Integer smfExecutor = smfKarma.getIdExecutor();
			Integer reactorUserId = (smfExecutor == null || smfExecutor == 0)
					? null
					: userMap.get(smfExecutor);
			boolean positive = smfKarma.getAction().equals(1);

			ReactionDbo reaction = new ReactionDbo();
			reaction.setReactableType("MESSAGE");
			reaction.setReactableId(zfgbbMsgId);
			reaction.setReactorUserId(reactorUserId);
			reaction.setReactionTypeId(positive ? likeTypeId : dislikeTypeId);
			reaction.setComment(smfKarma.getDescription());
			reaction.setMigrationHash(MigrationHasher.hash(String.valueOf(smfKarma.getIdExecutor())
					+ smfMsgId
					+ positive
					+ (reaction.getComment() == null ? "" : reaction.getComment())
					+ smfKarma.getLogTime()));

			Integer existingId = reactionIdByHash.get(reaction.getMigrationHash());
			if (existingId != null) {
				reaction.setReactionId(existingId);
				reactionMapper.updateByPrimaryKey(reaction);
			} else if (reactorUserId != null
					&& !seenReactionKeys.add("MESSAGE:" + zfgbbMsgId + ":" + reactorUserId)) {
				continue;
			} else {
				reactionMapper.insert(reaction);
				reactionIdByHash.put(reaction.getMigrationHash(), reaction.getReactionId());
			}

			if (smfKarma.getLogTime() != null) {
				migratorTimestampMapper.setReactionTimestamps(reaction.getReactionId(),
						SmfTimes.fromEpochSeconds(smfKarma.getLogTime()),
						SmfTimes.fromEpochSeconds(smfKarma.getLogTime()));
			}
		}
		return null;
	}

	private Integer reactionTypeIdByCode(String code) {
		ReactionTypeDboExample ex = new ReactionTypeDboExample();
		ex.createCriteria().andCodeEqualTo(code);
		return reactionTypeMapper.selectByExample(ex).stream().findFirst()
				.map(ReactionTypeDbo::getReactionTypeId).orElse(null);
	}
}
