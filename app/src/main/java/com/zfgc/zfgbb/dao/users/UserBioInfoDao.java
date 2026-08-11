package com.zfgc.zfgbb.dao.users;

import java.util.Set;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.mappers.UserBioInfoDboMapper;

@Repository
public class UserBioInfoDao extends KeyedDao<UserBioInfoDbo, UserBioInfoDboExample, Integer> {

	public UserBioInfoDao(UserBioInfoDboMapper mapper) {
		super(mapper);
	}

	public Optional<Integer> findBioAvatarId(Integer userId) {
		UserBioInfoDboExample byUser = new UserBioInfoDboExample();
		byUser.createCriteria().andUserIdEqualTo(userId);
		return getOne(byUser).map(UserBioInfoDbo::getAvatarId);
	}

	public int scrubUserBioInfo(Integer userId) {
		UserBioInfoDboExample byUser = new UserBioInfoDboExample();
		byUser.createCriteria().andUserIdEqualTo(userId);
		return updateWhereSettingColumns(new UserBioInfoDbo(), Set.of("real_name", "birth_date", "location",
				"website_title", "website_url", "signature", "personal_text", "custom_title", "gender_id",
				"preferred_timezone", "migration_hash", "avatar_id"), byUser);
	}
}
