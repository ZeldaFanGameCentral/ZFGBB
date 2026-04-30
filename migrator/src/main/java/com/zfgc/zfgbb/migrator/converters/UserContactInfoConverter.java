package com.zfgc.zfgbb.migrator.converters;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.mappers.EmailAddressDboMapper;
import com.zfgc.zfgbb.mappers.UserContactInfoDboMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMembersDbMapper;

@Component
public class UserContactInfoConverter extends AbstractConverter {
	Logger logger = LoggerFactory.getLogger(UserContactInfoConverter.class);
	
	@Autowired
	private SMFMembersDbMapper membersMapper;
	
	@Autowired
	private UserContactInfoDboMapper contactInfoMapper;
	
	@Autowired
	private EmailAddressDboMapper emailMapper;
	
	@Transactional
	public Map<Integer,UserContactInfoDbo> convertToZfgbb() {
		AtomicInteger emailId = new AtomicInteger(1);
		return membersMapper.selectByExample(new SMFMembersDbExample())
					 .stream()
					 .map(member -> {
						 Cancellable.check();
						 EmailAddressDbo email = new EmailAddressDbo();
						 email.setEmailAddress(member.getEmailAddress());
						 email.setSpammerFlag(member.getIsSpammer() == 1);
						 email.setEmailAddressId(emailId.getAndIncrement());
						 email.setMigrationHash(MigrationHasher.hash(email.getEmailAddress()
								+ email.getSpammerFlag().toString()));
						 
						 Optional.ofNullable(emailMapper.selectByPrimaryKey(email.getEmailAddressId()))
						 		 .ifPresentOrElse(existing -> {
						 			 if(!existing.getMigrationHash().equals(email.getMigrationHash())) {
						 				 emailMapper.updateByPrimaryKey(email);
						 			 }
						 		 }, 
						 		 () -> {
						 			 emailMapper.insert(email);
						 		 });
						 
						 
						 UserContactInfoDbo dbo = new UserContactInfoDbo();
						 dbo.setAllowEmailFlag(Boolean.FALSE.equals(member.getHideEmail()));
						 dbo.setAllowPmFlag(true);
						 dbo.setEmailAddressId(email.getEmailAddressId());
						 dbo.setUserId(member.getIdMember());
						 dbo.setMigrationHash(MigrationHasher.hash(dbo.getUserId().toString()
								+ dbo.getEmailAddressId().toString()
								+ dbo.getAllowEmailFlag().toString()
								+ dbo.getAllowPmFlag().toString()));
						 
						 Optional.ofNullable(contactInfoMapper.selectByPrimaryKey(dbo.getUserId()))
						 		 .ifPresentOrElse(existing -> {
						 			 if(!existing.getMigrationHash().equals(dbo.getMigrationHash())) {
						 				contactInfoMapper.updateByPrimaryKey(dbo);
						 			 }
						 		 },
						 		 () -> {
						 			 contactInfoMapper.insert(dbo);
						 		 });
						 
						 return dbo;
						 
					 })
					 .collect(Collectors.toMap(UserContactInfoDbo::getUserId, Function.identity()));
	}
}
