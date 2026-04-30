package com.zfgc.zfgbb.migrator.converters;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFTopicDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFTopicDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFTopicDbMapper;

@Component
public class ThreadConverter extends AbstractConverter<Map<Integer, ThreadDbo>> {

	@Override
	public JobType getType() {
		return JobType.THREADS;
	}

	@Autowired
	public SMFTopicDbMapper smfTopicMapper;
	
	@Autowired
	public ThreadDboMapper threadDboMapper;
	
	@Autowired
	private SMFMessageDbMapper smfMessageMapper;
	
	Logger logger = LoggerFactory.getLogger(ThreadConverter.class);
	
	@Override
	public Map<Integer, ThreadDbo> convertToZfgbb() {
		List<SMFTopicDb> SMFCategories = smfTopicMapper.selectByExample(new SMFTopicDbExample());
		Map<Integer,ThreadDbo> result = new HashMap<>();
		
		logger.info("Beginning conversion of SMF Topic to ZFGBB Thread");
		logger.info(SMFCategories.size() + " records found");
		SMFCategories.forEach((smfTopic) -> {
			Cancellable.check();
			ThreadDbo thread = new ThreadDbo();
			
			//get messages for this thread so we can get the subject
			SMFMessageDbExample ex = new SMFMessageDbExample();
			ex.createCriteria().andIdTopicEqualTo(smfTopic.getIdTopic());
			SMFMessageDb msg = smfMessageMapper.selectByExample(ex)
											   .stream()
											   .sorted(Comparator.comparingInt(SMFMessageDb::getPosterTime))
											   .findFirst().orElse(null);
			
			if(msg != null) {
				thread.setBoardId(smfTopic.getIdBoard().intValue());
				thread.setCreatedUserId(smfTopic.getIdMemberStarted());
				thread.setLockedFlag(smfTopic.getLocked().intValue() > 0);
				thread.setPinnedFlag(smfTopic.getIsSticky().intValue() > 0);
				thread.setThreadId(smfTopic.getIdTopic());
				thread.setThreadName(msg.getSubject());
				thread.setViewCount(smfTopic.getNumViews());
				
				thread.setMigrationHash(MigrationHasher.hash(thread.getThreadId()
						+ thread.getThreadName()
						+ thread.getLockedFlag().toString()
						+ thread.getPinnedFlag().toString()
						+ thread.getBoardId()
						+ thread.getCreatedUserId()
						+ thread.getViewCount()));
				
				result.put(thread.getThreadId(), thread);
				
				
				ThreadDbo existingThread = threadDboMapper.selectByPrimaryKey(thread.getThreadId());
				if(existingThread == null) {
					threadDboMapper.insert(thread);
				}
				else if(!existingThread.getMigrationHash().equals(thread.getMigrationHash())) {
					threadDboMapper.updateByPrimaryKey(thread);
				}
			}
		});
		
		logger.info("Finished conversion of ZFGBB Thread");
		
		return result;
	}
}
