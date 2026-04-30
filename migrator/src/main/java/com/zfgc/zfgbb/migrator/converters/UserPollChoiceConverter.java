package com.zfgc.zfgbb.migrator.converters;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import com.zfgc.zfgbb.mappers.PollChoiceDboMapper;
import com.zfgc.zfgbb.mappers.UserPollChoiceDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogPollsDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFLogPollsDbMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserPollChoiceConverter extends AbstractConverter<Map<Integer, UserPollChoiceDbo>> {

	@Override
	public JobType getType() {
		return JobType.USER_POLL_CHOICES;
	}


	@Autowired
	private UserPollChoiceDboMapper userPollChoiceMapper;
	
	@Autowired
	private PollChoiceDboMapper pollQuestionMapper;
	
	@Autowired
	private SMFLogPollsDbMapper smfLogPollsMapper;
	
	@Override
	public Map<Integer, UserPollChoiceDbo> convertToZfgbb() {
		Map<String, Integer> pollChoiceMap = pollQuestionMapper.selectByExample(new PollChoiceDboExample())
																 .stream()
																 .collect(Collectors.toMap(q -> q.getPollId() + "," + q.getSeqno(), PollChoiceDbo::getPollChoiceId));
		
		SMFLogPollsDbExample logEx = new SMFLogPollsDbExample();
		logEx.createCriteria().andIdMemberNotEqualTo(0);
		return smfLogPollsMapper.selectByExample(logEx)
						 .stream()
						 .map(l -> {
							 Cancellable.check();
							 Integer choiceId = pollChoiceMap.get(l.getIdPoll() + "," + l.getIdChoice());
							 
							 UserPollChoiceDbo pollChoiceDbo = new UserPollChoiceDbo();
							 pollChoiceDbo.setPollChoiceId(choiceId);
							 pollChoiceDbo.setUserId(l.getIdMember());

							 pollChoiceDbo.setMigrationHash(MigrationHasher.hash(pollChoiceDbo.getPollChoiceId().toString()
									 + pollChoiceDbo.getUserId().toString()));
							 UserPollChoiceDboExample ex = new UserPollChoiceDboExample();
							 ex.createCriteria().andMigrationHashEqualTo(pollChoiceDbo.getMigrationHash())
							                    .andUserIdEqualTo(l.getIdMember())
							                    .andPollChoiceIdEqualTo(choiceId);

							 userPollChoiceMapper.selectByExample(ex).stream().findFirst()
									 .ifPresentOrElse(
											 upc -> {
												 pollChoiceDbo.setUserPollChoiceId(upc.getUserPollChoiceId());
												 userPollChoiceMapper.updateByPrimaryKey(pollChoiceDbo);
											 },
											 () -> userPollChoiceMapper.insert(pollChoiceDbo)
									 );
							 
							 //log.info(pollChoiceDbo.getMigrationHash());
							 
							 return pollChoiceDbo;			  
							 
							 
						 }).collect(Collectors.toMap(UserPollChoiceDbo::getUserPollChoiceId, Function.identity()));
		
		
	}
	
}
