package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMembersDbMapper;

@Component
public class UsersConverter extends AbstractConverter<Map<Integer, UserDbo>> {

	@Autowired
	public SMFMembersDbMapper smfMembersMapper;

	@Autowired
	public UserDboMapper userDboMapper;

	@Override
	public JobType getType() {
		return JobType.USERS;
	}

	@Override
	@Transactional
	public Map<Integer, UserDbo> convertToZfgbb() {
		List<SMFMembersDbWithBLOBs> SMFMembers = smfMembersMapper.selectByExampleWithBLOBs(new SMFMembersDbExample());
		Map<Integer,UserDbo> result = new HashMap<>();
		
		SMFMembers.forEach((smfMember) -> {
			Cancellable.check();
			UserDbo user = new UserDbo();
			
			user.setUserId(smfMember.getIdMember());
			user.setDisplayName(smfMember.getRealName());
			user.setSsoKey(smfMember.getMemberName());
			user.setUserName(smfMember.getMemberName());
			user.setActiveFlag(smfMember.getIsActivated().intValue() > 0);
			user.setFailedLoginCount(0);

			user.setMigrationHash(MigrationHasher.hash(user.getUserId()
					+ user.getSsoKey()
					+ user.getActiveFlag().toString()
					+ user.getDisplayName()
					+ user.getUserName()));
			
			result.put(user.getUserId(), user);
			
			
			UserDbo existingUser = userDboMapper.selectByPrimaryKey(user.getUserId());
			if(existingUser == null) {
				userDboMapper.insert(user);
			}
			else if(!existingUser.getMigrationHash().equals(user.getMigrationHash())) {
				userDboMapper.updateByPrimaryKey(user);
			}
		});
		
		return result;
	}
	
}
