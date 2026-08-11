package com.zfgc.zfgbb.dao.users;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.mappers.UserBioInfoDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserProfileMapper;

@Repository
public class UserBioInfoDao extends KeyedDao<UserBioInfoDbo, UserBioInfoDboExample, Integer> {

	private final UserProfileMapper userProfileMapper;

	public UserBioInfoDao(UserBioInfoDboMapper mapper, UserProfileMapper userProfileMapper) {
		super(mapper);
		this.userProfileMapper = userProfileMapper;
	}

	public int ensureRow(Integer userId) {
		return userProfileMapper.ensureUserBioInfoRow(userId);
	}

	public int updateSelective(Integer userId,
			boolean personalTextPresent, String personalText,
			boolean signaturePresent, String signature,
			boolean locationPresent, String location,
			boolean birthDatePresent, LocalDate birthDate,
			boolean genderIdPresent, Integer genderId,
			boolean websiteTitlePresent, String websiteTitle,
			boolean websiteUrlPresent, String websiteUrl,
			boolean hideEmailFlagPresent, Boolean hideEmailFlag,
			boolean hideOnlineStatusPresent, Boolean hideOnlineStatus,
			boolean avatarIdPresent, Integer avatarId) {
		return userProfileMapper.updateUserBioInfoSelective(userId,
				personalTextPresent, personalText,
				signaturePresent, signature,
				locationPresent, location,
				birthDatePresent, birthDate,
				genderIdPresent, genderId,
				websiteTitlePresent, websiteTitle,
				websiteUrlPresent, websiteUrl,
				hideEmailFlagPresent, hideEmailFlag,
				hideOnlineStatusPresent, hideOnlineStatus,
				avatarIdPresent, avatarId);
	}
}
