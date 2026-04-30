package com.zfgc.zfgbb.migrator.converters;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.mappers.PollChoiceDboMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollChoicesDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFPollChoicesDbMapper;

@Component
public class PollChoiceConverter {

	@Autowired
	private PollChoiceDboMapper pollChoiceMapper;
	
	@Autowired
	private SMFPollChoicesDbMapper smfPollChoiceMapper;
	
	public Map<Integer,PollChoiceDbo> convertToZfgbb() {
		
		return smfPollChoiceMapper.selectByExample(new SMFPollChoicesDbExample()).stream()
						   .map(smfChoice -> {
							   Cancellable.check();
							   PollChoiceDbo pollChoice = new PollChoiceDbo();
							   pollChoice.setActiveFlag(true);
							   pollChoice.setChoiceText(smfChoice.getLabel());
							   pollChoice.setSeqno(smfChoice.getIdChoice());
							   pollChoice.setPollId(smfChoice.getIdPoll());
							   pollChoice.setVotes(smfChoice.getVotes().intValue());

							   pollChoice.setMigrationHash(MigrationHasher.hash(pollChoice.getChoiceText()
									   + pollChoice.getSeqno().toString()
									   + pollChoice.getPollId().toString()
									   + pollChoice.getVotes().toString()
									   + pollChoice.getActiveFlag().toString()));
							   PollChoiceDboExample pollEx = new PollChoiceDboExample();
							   pollEx.createCriteria().andMigrationHashEqualTo(pollChoice.getMigrationHash());

							   pollChoiceMapper.selectByExample(pollEx).stream().findFirst().ifPresentOrElse(
									   poll -> {
										   pollChoice.setPollChoiceId(poll.getPollChoiceId());
										   pollChoiceMapper.updateByPrimaryKey(pollChoice);
									   },
									   () -> pollChoiceMapper.insert(pollChoice));
							   
							   return pollChoice;
						   }).collect(Collectors.toMap(PollChoiceDbo::getPollChoiceId, Function.identity()));
		
		
		
	}
}
