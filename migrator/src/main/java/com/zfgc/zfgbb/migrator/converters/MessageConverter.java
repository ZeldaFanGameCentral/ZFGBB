package com.zfgc.zfgbb.migrator.converters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper;

@Component
public class MessageConverter {

	@Autowired
	private MessageDboMapper messageMapper;
	
	@Autowired
	private SMFMessageDbMapper smfMessageMapper;
	
	Logger logger = LoggerFactory.getLogger(MessageConverter.class);
	
	@Transactional
	public Map<Integer,MessageDbo> convertToZfgbb() {
		SMFMessageDbExample smfEx = new SMFMessageDbExample();
		smfEx.setOrderByClause("poster_time asc");
		List<SMFMessageDbWithBLOBs> SMFMembers = smfMessageMapper.selectByExampleWithBLOBs(smfEx);
		Map<Integer,MessageDbo> result = new HashMap<>();
		Map<Integer, AtomicInteger> counts = new HashMap<>();
		AtomicInteger totalCount = new AtomicInteger(1);
		
		logger.info("Beginning conversion of SMF messages to ZFGBB messages");
		logger.info(SMFMembers.size() + " records found");
		
		SMFMembers.forEach((smfMsg) -> {
			Cancellable.check();
			logger.info("Processing msg record " + totalCount.getAndIncrement() + " of " + SMFMembers.size());
			MessageDbo msg = new MessageDbo();
			
			counts.putIfAbsent(smfMsg.getIdTopic(), new AtomicInteger(1));
			
			msg.setMessageId(smfMsg.getIdMsg());
			msg.setOwnerId(smfMsg.getIdMember());
			msg.setPostInThread(counts.get(smfMsg.getIdTopic()).getAndIncrement());
			msg.setThreadId(smfMsg.getIdTopic());
			msg.setCreatedTs(smfMsg.getPostTimeAsTime());
			
			LocalDateTime updatedTime = smfMsg.getUpdatedTimeAsTime();
			if(updatedTime != null) {
				msg.setUpdatedTs(updatedTime);
			}
			else {
				msg.setUpdatedTs(msg.getCreatedTs());
			}
			
			msg.setMigrationHash(MigrationHasher.hash(msg.getMessageId()
					+ "" + msg.getOwnerId()
					+ msg.getThreadId()
					+ (msg.getPostInThread() == null ? -1 : msg.getPostInThread())
					+ msg.getCreatedTs().toString()
					+ (msg.getUpdatedTs() == null ? 0 : msg.getUpdatedTs().toString())));
			
			result.put(msg.getMessageId(), msg);
			
			
			MessageDbo existingMsg = messageMapper.selectByPrimaryKey(msg.getMessageId());
			if(existingMsg == null) {
				messageMapper.insert(msg);
			}
			else if(!existingMsg.getMigrationHash().equals(msg.getMigrationHash())) {
				messageMapper.updateByPrimaryKey(msg);
			}
		});
		
		logger.info("Finished converting messages");
		
		return result;
	}
	
}
