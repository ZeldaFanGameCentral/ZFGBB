package com.zfgc.zfgbb.migrator.converters;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dbo.ModerationLogDboExample;
import com.zfgc.zfgbb.dbo.UserWarningDbo;
import com.zfgc.zfgbb.dbo.UserWarningDboExample;
import com.zfgc.zfgbb.mappers.ModerationLogDboMapper;
import com.zfgc.zfgbb.mappers.UserWarningDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogActionDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogActionDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogCommentDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogCommentDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFLogActionDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFLogCommentDbMapper;

@Component
public class ModerationLogsConverter extends AbstractConverter<Void> {

	private static final Pattern PHP_STRING_PAIR =
			Pattern.compile("s:\\d+:\"([^\"]*)\";(?:s:\\d+:\"([^\"]*)\"|i:(-?\\d+))");

	private static final Map<String, String> ACTION_CODES = Map.ofEntries(
			Map.entry("usertitle", "EDIT_USER_TITLE"),
			Map.entry("location", "EDIT_USER_LOCATION"),
			Map.entry("personal_text", "EDIT_USER_TEXT"),
			Map.entry("real_name", "EDIT_USER_NAME"),
			Map.entry("email_address", "EDIT_USER_EMAIL"),
			Map.entry("id_group", "CHANGE_USER_GROUP"),
			Map.entry("additional_groups", "CHANGE_USER_GROUPS"),
			Map.entry("posts", "EDIT_USER_POST_COUNT"),
			Map.entry("approve_member", "APPROVE_MEMBER"),
			Map.entry("lock", "LOCK_THREAD"),
			Map.entry("sticky", "PIN_THREAD"),
			Map.entry("move", "MOVE_THREAD"),
			Map.entry("remove", "DELETE_THREAD"),
			Map.entry("merge", "MERGE_THREADS"),
			Map.entry("split", "SPLIT_THREAD"));

	@Override
	public JobType getType() {
		return JobType.MODERATION_LOGS;
	}

	@Autowired
	private SMFLogCommentDbMapper smfCommentMapper;

	@Autowired
	private SMFLogActionDbMapper smfActionMapper;

	@Autowired
	private UserWarningDboMapper warningMapper;

	@Autowired
	private ModerationLogDboMapper modLogMapper;

	@Autowired
	private MigratorIdMapService idMap;

	private Map<Integer, Integer> userMap;
	private Map<Integer, Integer> boardMap;
	private Map<Integer, Integer> threadMap;
	private Map<Integer, Integer> messageMap;
	private Set<String> warningHashes;
	private Set<String> modLogHashes;

	@Override
	@Transactional
	public Void convertToZfgbb() {
		userMap = idMap.getAllForType(LegacyEntityType.USER);
		boardMap = idMap.getAllForType(LegacyEntityType.BOARD);
		threadMap = idMap.getAllForType(LegacyEntityType.THREAD);
		messageMap = idMap.getAllForType(LegacyEntityType.MESSAGE);
		warningHashes = warningMapper.selectByExample(new UserWarningDboExample()).stream()
				.map(UserWarningDbo::getMigrationHash)
				.filter(Objects::nonNull)
				.collect(Collectors.toCollection(HashSet::new));
		modLogHashes = modLogMapper.selectByExample(new ModerationLogDboExample()).stream()
				.map(ModerationLogDbo::getMigrationHash)
				.filter(Objects::nonNull)
				.collect(Collectors.toCollection(HashSet::new));

		for (SMFLogCommentDb comment : smfCommentMapper.selectByExampleWithBLOBs(new SMFLogCommentDbExample())) {
			Cancellable.check();
			if ("warning".equals(comment.getCommentType())) {
				convertWarning(comment);
			}
		}
		for (SMFLogActionDb action : smfActionMapper.selectByExampleWithBLOBs(new SMFLogActionDbExample())) {
			Cancellable.check();
			if (!"delete_member".equals(action.getAction())) {
				convertAction(action);
			}
		}
		return null;
	}

	private void convertWarning(SMFLogCommentDb comment) {
		Integer userId = userMap.get(comment.getIdRecipient());
		if (userId == null) {
			return;
		}
		String hash = MigrationHasher.hash("warning" + comment.getIdComment()
				+ comment.getIdRecipient()
				+ comment.getLogTime()
				+ comment.getCounter()
				+ comment.getBody());
		if (warningHashes.contains(hash)) {
			return;
		}

		Integer issuedByUserId = comment.getIdMember() == null || comment.getIdMember() == 0
				? null
				: userMap.get(comment.getIdMember());
		String body = comment.getBody() == null ? null : HtmlUtils.htmlUnescape(comment.getBody());
		int points = comment.getCounter() == null ? 0 : comment.getCounter();
		java.time.OffsetDateTime issuedTs = SmfTimes.fromEpochSeconds(comment.getLogTime());

		UserWarningDbo warning = new UserWarningDbo();
		warning.setUserId(userId);
		warning.setIssuedByUserId(issuedByUserId);
		warning.setIssuedByName(blankToNull(comment.getMemberName()));
		warning.setBody(body);
		warning.setPoints(points);
		warning.setIssuedTs(issuedTs);
		warning.setMigrationHash(hash);
		warningMapper.insert(warning);
		warningHashes.add(hash);

		logWarningAction(comment.getIdComment(), userId, issuedByUserId,
				blankToNull(comment.getRecipientName()), body, points, issuedTs);
	}

	private void logWarningAction(Integer legacyCommentId, Integer targetUserId, Integer actorUserId,
			String targetName, String body, int points, java.time.OffsetDateTime issuedTs) {
		String hash = MigrationHasher.hash("warnlog" + legacyCommentId + targetUserId + points + issuedTs);
		if (modLogHashes.contains(hash)) {
			return;
		}
		ModerationLogDbo entry = new ModerationLogDbo();
		entry.setActorUserId(actorUserId);
		entry.setAction("WARN");
		entry.setTargetUserId(targetUserId);
		entry.setTargetName(targetName);
		entry.setDetail((body == null ? "" : body) + " (+" + points + ")");
		entry.setLoggedTs(issuedTs);
		entry.setMigrationHash(hash);
		modLogMapper.insert(entry);
		modLogHashes.add(hash);
	}

	private void convertAction(SMFLogActionDb action) {
		String hash = MigrationHasher.hash("modlog" + action.getIdAction()
				+ action.getAction()
				+ action.getLogTime()
				+ action.getExtra());
		if (modLogHashes.contains(hash)) {
			return;
		}

		Map<String, String> extra = parseExtra(action.getExtra());

		ModerationLogDbo entry = new ModerationLogDbo();
		entry.setActorUserId(action.getIdMember() == null || action.getIdMember() == 0
				? null
				: userMap.get(action.getIdMember()));
		entry.setAction(ACTION_CODES.getOrDefault(action.getAction(),
				action.getAction().toUpperCase().replace(' ', '_')));
		entry.setTargetUserId(targetUserId(extra));
		entry.setTargetName(blankToNull(extra.get("user")));
		entry.setBoardId(action.getIdBoard() != null && action.getIdBoard() > 0
				? boardMap.get(action.getIdBoard())
				: null);
		entry.setThreadId(action.getIdTopic() != null && action.getIdTopic() > 0
				? threadMap.get(action.getIdTopic())
				: null);
		entry.setMessageId(action.getIdMsg() != null && action.getIdMsg() > 0
				? messageMap.get(action.getIdMsg())
				: null);
		entry.setDetail(readableDetail(extra));
		entry.setLoggedTs(SmfTimes.fromEpochSeconds(action.getLogTime()));
		entry.setMigrationHash(hash);
		modLogMapper.insert(entry);
		modLogHashes.add(hash);
	}

	private Integer targetUserId(Map<String, String> extra) {
		String member = extra.get("member");
		if (member == null || !member.matches("\\d+")) {
			return null;
		}
		return userMap.get(Integer.parseInt(member));
	}

	private Map<String, String> parseExtra(String extra) {
		if (extra == null || extra.isBlank()) {
			return Map.of();
		}
		Matcher matcher = PHP_STRING_PAIR.matcher(extra);
		java.util.LinkedHashMap<String, String> values = new java.util.LinkedHashMap<>();
		while (matcher.find()) {
			String value = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
			values.putIfAbsent(matcher.group(1), value);
		}
		return values;
	}

	private String readableDetail(Map<String, String> extra) {
		if (extra.isEmpty()) {
			return null;
		}
		return extra.entrySet().stream()
				.filter(entry -> !"member".equals(entry.getKey()) && !"user".equals(entry.getKey()))
				.map(entry -> entry.getKey() + "=" + HtmlUtils.htmlUnescape(entry.getValue()))
				.collect(Collectors.joining("; "));
	}

	private String blankToNull(String value) {
		return value == null || value.isBlank() ? null : HtmlUtils.htmlUnescape(value);
	}
}
