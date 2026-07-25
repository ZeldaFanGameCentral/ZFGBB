package com.zfgc.zfgbb.migrator.converters;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.NotificationSubscriptionDbo;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDboExample;
import com.zfgc.zfgbb.mappers.NotificationSubscriptionDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogNotifyDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogNotifyDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFLogNotifyDbMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionsConverter extends AbstractConverter<Void> {

	private final SMFLogNotifyDbMapper smfNotifyMapper;
	private final NotificationSubscriptionDboMapper subscriptionMapper;
	private final MigratorIdMapService idMap;

	@Override
	public JobType getType() {
		return JobType.SUBSCRIPTIONS;
	}

	private Map<Integer, Integer> userMap;
	private Map<Integer, Integer> threadMap;
	private Map<Integer, Integer> boardMap;
	private Set<String> threadSubs;
	private Set<String> boardSubs;

	@Override
	@Transactional
	public Void convertToZfgbb() {
		userMap = idMap.getAllForType(LegacyEntityType.USER);
		threadMap = idMap.getAllForType(LegacyEntityType.THREAD);
		boardMap = idMap.getAllForType(LegacyEntityType.BOARD);
		threadSubs = new HashSet<>();
		boardSubs = new HashSet<>();
		for (NotificationSubscriptionDbo sub : subscriptionMapper.selectByExample(new NotificationSubscriptionDboExample())) {
			if (sub.getThreadId() != null) {
				threadSubs.add(sub.getUserId() + ":" + sub.getThreadId());
			}
			if (sub.getBoardId() != null) {
				boardSubs.add(sub.getUserId() + ":" + sub.getBoardId());
			}
		}

		List<SMFLogNotifyDb> rows = smfNotifyMapper.selectByExample(new SMFLogNotifyDbExample());
		for (SMFLogNotifyDb row : rows) {
			Cancellable.check();
			convertOne(row);
		}
		return null;
	}

	private void convertOne(SMFLogNotifyDb row) {
		Integer userId = userMap.get(row.getIdMember());
		if (userId == null) {
			return;
		}
		Integer threadId = row.getIdTopic() != null && row.getIdTopic() > 0
				? threadMap.get(row.getIdTopic())
				: null;
		Integer boardId = threadId == null && row.getIdBoard() != null && row.getIdBoard() > 0
				? boardMap.get(row.getIdBoard())
				: null;
		if (threadId == null && boardId == null) {
			return;
		}

		String key;
		if (threadId != null) {
			key = userId + ":" + threadId;
			if (threadSubs.contains(key)) {
				return;
			}
		} else {
			key = userId + ":" + boardId;
			if (boardSubs.contains(key)) {
				return;
			}
		}

		NotificationSubscriptionDbo subscription = new NotificationSubscriptionDbo();
		subscription.setUserId(userId);
		subscription.setThreadId(threadId);
		subscription.setBoardId(boardId);
		subscription.setMigrationHash(MigrationHasher.hash("notify" + row.getIdMember()
				+ "t" + row.getIdTopic() + "b" + row.getIdBoard()));
		subscriptionMapper.insert(subscription);
		if (threadId != null) {
			threadSubs.add(key);
		} else {
			boardSubs.add(key);
		}
	}
}
