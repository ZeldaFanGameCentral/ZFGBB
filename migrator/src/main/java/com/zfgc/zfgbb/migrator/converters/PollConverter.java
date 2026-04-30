package com.zfgc.zfgbb.migrator.converters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.mappers.PollDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollsDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollsDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFTopicDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFTopicDbExample;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFPollsDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFTopicDbMapper;

@Component
public class PollConverter extends AbstractConverter<Map<Integer, PollDbo>> {

	@Override
	public JobType getType() {
		return JobType.POLLS;
	}


	@Autowired
	private SMFPollsDbMapper smfPollsMapper;
	
	@Autowired
	private PollDboMapper pollMapper;
	
	@Autowired
	private SMFTopicDbMapper threadMapper;
	
	@Override
	public Map<Integer, PollDbo> convertToZfgbb() {
		List<SMFPollsDb> smfPolls = smfPollsMapper.selectByExample(new SMFPollsDbExample());
		SMFTopicDbExample threadEx = new SMFTopicDbExample();
		threadEx.createCriteria().andIdPollIsNotNull().andIdPollNotEqualTo(0);
		Map<Integer, Integer> threadMap = threadMapper.selectByExample(threadEx).stream().collect(Collectors.toMap(SMFTopicDb::getIdPoll, SMFTopicDb::getIdTopic));
		
		return smfPolls.stream()
				.map(smfPoll -> {
					Cancellable.check();
					PollDbo poll = new PollDbo();
					
					Instant instant = Instant.ofEpochMilli(TimeUnit.SECONDS.toMillis(smfPoll.getExpireTime()));
				    LocalDateTime expireTime =
				      LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

					poll.setChangeVoteFlag(smfPoll.getChangeVote());
					poll.setCreatedTs(LocalDateTime.now());
					poll.setCreatedUserId(smfPoll.getIdMember());
					poll.setExpireTime(smfPoll.getExpireTime() == 0 ? null : expireTime);
					poll.setGuestVoteCount(smfPoll.getNumGuestVoters());
					poll.setGuestVoteFlag(smfPoll.getGuestVote());
					poll.setHideResultsFlag(smfPoll.getHideResults());
					poll.setMaxVotes(smfPoll.getMaxVotes());
					poll.setPollId(smfPoll.getIdPoll());
					poll.setPollQuestion(smfPoll.getQuestion());
					poll.setResetPoll(smfPoll.getResetPoll());
					poll.setThreadId(smfPoll.getIdTopic());
					poll.setVotingLockedFlag(smfPoll.getVotingLocked());
					poll.setPollQuestion(smfPoll.getQuestion());
					poll.setThreadId(threadMap.get(poll.getPollId()));
					
					
					poll.setMigrationHash(MigrationHasher.hash(poll.getPollQuestion()
							+ poll.getCreatedUserId().toString()
							+ poll.getGuestVoteCount().toString()
							+ poll.getPollId().toString()
							+ poll.getResetPoll().toString()
							+ poll.getThreadId().toString()
							+ poll.getChangeVoteFlag().toString()
							+ (poll.getExpireTime() == null ? "0" : poll.getExpireTime().toString())
							+ poll.getGuestVoteFlag().toString()
							+ poll.getHideResultsFlag().toString()
							+ poll.getMaxVotes().toString()
							+ poll.getPollQuestion()
							+ poll.getVotingLockedFlag().toString()));
					PollDboExample pollEx = new PollDboExample();
					pollEx.createCriteria().andMigrationHashEqualTo(poll.getMigrationHash());
					pollMapper.selectByExample(pollEx).stream().findFirst().ifPresentOrElse(
							pollDb -> pollMapper.updateByPrimaryKey(poll),
							() -> pollMapper.insert(poll));
					
					return poll;
				})
				.collect(Collectors.toMap(PollDbo::getPollId, Function.identity()));
	}
	
}
