package com.zfgc.zfgbb.migrator.converters;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFBoardDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDbExample;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageHistoryDbMapper;

@Component
public class MessageHistoryConverter extends AbstractConverter<Map<Integer, MessageHistoryDbo>> {

	@Override
	public JobType getType() {
		return JobType.MESSAGE_HISTORY;
	}

	
	@Autowired
	private SMFMessageHistoryDbMapper smfMsgHistoryMapper;
	
	@Autowired
	private SMFMessageDbMapper smfMsgMapper;
	
	@Autowired
	private MessageHistoryDboMapper msgHistoryMapper;
	
	@Autowired
	private IpAddressDboMapper ipMapper;
	
	Logger logger = LoggerFactory.getLogger(MessageConverter.class);
	
	
	@Override
	public Map<Integer, MessageHistoryDbo> convertToZfgbb() {
		Map<Integer, List<SMFMessageHistoryDb>> SMFMessageHistory = smfMsgHistoryMapper.selectByExampleWithBLOBs(new SMFMessageHistoryDbExample()).stream()
																																			.collect(Collectors.groupingBy(SMFMessageHistoryDb::getIdMsg));
		Map<String, IpAddressDbo> ipMap = ipMapper.selectByExample(new IpAddressDboExample()).stream()
																							 .collect(Collectors.toMap(IpAddressDbo::getIp, Function.identity()));
		Map<Integer, SMFMessageDbWithBLOBs> msgMap = smfMsgMapper.selectByExampleWithBLOBs(new SMFMessageDbExample()).stream()
																								   .collect(Collectors.toMap(SMFMessageDbWithBLOBs::getIdMsg, Function.identity()));
		
		Map<Integer, MessageHistoryDbo> result = new HashMap<>();
		
		AtomicInteger totalCount = new AtomicInteger(0);
		AtomicInteger historyCount = new AtomicInteger(0);
		
		logger.info("Beginning conversion of SMF message history to ZFGBB message history");
		logger.info(msgMap.size() + " records found");
		
		msgMap.values().forEach(msg -> {
			Cancellable.check();
			if(totalCount.get() % 10000 == 0) {
				logger.info("Processing message history for msg Id " + msg.getIdMsg() + " record " + totalCount.get() + " of " + msgMap.size());
			}
			
			//iterate over any SMF history records for this message
			if(SMFMessageHistory.containsKey(msg.getIdMsg())) {
				SMFMessageHistory.get(msg.getIdMsg()).forEach(smfHist -> {
					MessageHistoryDbo currentMsg = new MessageHistoryDbo();
					
					currentMsg.setMessageHistoryId(historyCount.incrementAndGet());
					currentMsg.setCreatedTs(smfHist.getPostTimeAsTime());
					currentMsg.setCurrentFlag(false);
					currentMsg.setIpAddressId(ipMap.get(msg.getPosterIp()).getIpAddressId());
					currentMsg.setLegacyId(null);
					currentMsg.setMessageId(msg.getIdMsg());
					currentMsg.setMessageText(smfHist.getBody());
					currentMsg.setUpdatedTs(currentMsg.getCreatedTs());
					
					currentMsg.setMigrationHash(MigrationHasher.hash(currentMsg.getMessageId()
							+ currentMsg.getMessageText()
							+ currentMsg.getCurrentFlag().toString()
							+ currentMsg.getCreatedTs().toString()
							+ (currentMsg.getUpdatedTs() == null ? 0 : currentMsg.getUpdatedTs().toString())
							+ currentMsg.getIpAddressId()
							+ currentMsg.getLegacyId()));

					MessageHistoryDboExample ex = new MessageHistoryDboExample();
					ex.createCriteria().andMessageHistoryIdEqualTo(currentMsg.getMessageHistoryId());
					MessageHistoryDbo existingMsg = msgHistoryMapper.selectByExample(ex).stream().findFirst().orElse(null);
					if(existingMsg == null) {
						msgHistoryMapper.insert(currentMsg);
					}
					else if(!existingMsg.getMigrationHash().equals(currentMsg.getMigrationHash())) {
						currentMsg.setMessageHistoryId(existingMsg.getMessageHistoryId());
						msgHistoryMapper.updateByPrimaryKey(currentMsg);
					}
					
					result.put(currentMsg.getMessageHistoryId(), currentMsg);
					
					
				});
			}
			
			//the Message record in SMF is always the current record
			MessageHistoryDbo currentMsg = new MessageHistoryDbo();
			
			currentMsg.setMessageHistoryId(historyCount.incrementAndGet());
			currentMsg.setCreatedTs(msg.getPostTimeAsTime());
			currentMsg.setCurrentFlag(true);
			currentMsg.setIpAddressId(ipMap.get(msg.getPosterIp()).getIpAddressId());
			currentMsg.setLegacyId(null);
			currentMsg.setMessageId(msg.getIdMsg());
			currentMsg.setMessageText(msg.getBody());
			currentMsg.setUpdatedTs(currentMsg.getCreatedTs());
			
			currentMsg.setMigrationHash(MigrationHasher.hash(currentMsg.getMessageId()
					+ currentMsg.getMessageText()
					+ currentMsg.getCurrentFlag().toString()
					+ currentMsg.getCreatedTs().toString()
					+ (currentMsg.getUpdatedTs() == null ? 0 : currentMsg.getUpdatedTs().toString())
					+ currentMsg.getIpAddressId()
					+ currentMsg.getLegacyId()));

			MessageHistoryDboExample ex = new MessageHistoryDboExample();
			ex.createCriteria().andMigrationHashEqualTo(currentMsg.getMigrationHash())
			   				   .andMessageHistoryIdEqualTo(currentMsg.getMessageHistoryId());
			MessageHistoryDbo existingMsg = msgHistoryMapper.selectByExample(ex).stream().findFirst().orElse(null);
			if(existingMsg == null) {
				msgHistoryMapper.insert(currentMsg);
			}
			else if(!existingMsg.getMigrationHash().equals(currentMsg.getMigrationHash())) {
				currentMsg.setMessageHistoryId(existingMsg.getMessageHistoryId());
				msgHistoryMapper.updateByPrimaryKey(currentMsg);
			}
			
			result.put(currentMsg.getMessageHistoryId(), currentMsg);
			totalCount.incrementAndGet();
		});
		
		logger.info("Finished converting messages");
		
		return result;
	}
}
