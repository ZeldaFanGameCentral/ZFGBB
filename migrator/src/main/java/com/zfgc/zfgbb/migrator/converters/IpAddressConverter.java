package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageHistoryDbMapper;

@Component
public class IpAddressConverter extends AbstractConverter<Map<Integer, IpAddressDbo>> {

	@Autowired
	private SMFMessageDbMapper SMFMessageMapper;

	@Autowired
	private IpAddressDboMapper ipAddressMapper;

	Logger logger = LoggerFactory.getLogger(IpAddressConverter.class);

	@Override
	public JobType getType() {
		return JobType.IPS;
	}

	@Override
	@Transactional
	public Map<Integer, IpAddressDbo> convertToZfgbb() {
		List<SMFMessageDb> SMFMessages = SMFMessageMapper.selectByExample(new SMFMessageDbExample());
		Map<String, IpAddressDbo> ipList = new HashMap<>();
		Map<Integer, IpAddressDbo> result = new HashMap<>();
		
		logger.info("Beginning conversion of IP Addresses based on SMF messages");
		logger.info(SMFMessages.size() + " records found");
		
		SMFMessages.forEach(smfMsg -> {
			Cancellable.check();
			if(!ipList.containsKey(smfMsg.getPosterIp())) {
				IpAddressDbo ip = new IpAddressDbo();
				
				ip.setIp(smfMsg.getPosterIp());
				ip.setIpV6Flag(false);
				ip.setIsSpammerFlag(false);
				
				ip.setMigrationHash(MigrationHasher.hash(ip.getIp()
						+ ip.getIpV6Flag().toString()
						+ ip.getIsSpammerFlag().toString()));
				
				IpAddressDboExample ex = new IpAddressDboExample();
				ex.createCriteria().andIpEqualTo(ip.getIp()).andMigrationHashEqualTo(ip.getMigrationHash());
				IpAddressDbo existingIp = ipAddressMapper.selectByExample(ex).stream().findFirst().orElse(null);
				if(existingIp == null) {
					ipAddressMapper.insert(ip);
				}
				else {
					ip.setIpAddressId(existingIp.getIpAddressId());
					ipAddressMapper.updateByPrimaryKeySelective(ip);
				}
				ipList.put(smfMsg.getPosterIp(), ip);
				result.put(ip.getIpAddressId(), ip);
			}
		});
		
		return result;
	}
	
}
