package com.zfgc.zfgbb.dao.users;

import java.util.List;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.users.UserContactInfoDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import com.zfgc.zfgbb.mappers.EmailAddressDboMapper;

@Repository
public class EmailAddressDao extends IdentityDao<EmailAddressDbo, EmailAddressDboExample> {

	private final UserContactInfoDao userContactInfoDao;

	public EmailAddressDao(EmailAddressDboMapper mapper,
			UserContactInfoDao userContactInfoDao) {
		super(mapper);
		this.userContactInfoDao = userContactInfoDao;
	}

	public int deleteEmailAddressIfUnreferenced(Integer emailAddressId) {
		UserContactInfoDboExample stillReferencing = new UserContactInfoDboExample();
		stillReferencing.createCriteria().andEmailAddressIdEqualTo(emailAddressId);
		if (userContactInfoDao.exists(stillReferencing))
			return 0;
		EmailAddressDboExample byId = new EmailAddressDboExample();
		byId.createCriteria().andEmailAddressIdEqualTo(emailAddressId);
		return deleteWhere(byId);
	}

	public Optional<String> findPrimaryEmailAddress(Integer userId) {
		List<Integer> emailAddressIds = userContactInfoDao.findEmailAddressIds(userId);
		if (emailAddressIds.isEmpty())
			return Optional.empty();
		EmailAddressDboExample byIds = new EmailAddressDboExample();
		byIds.createCriteria().andEmailAddressIdIn(emailAddressIds);
		byIds.setLimit(1);
		return get(byIds).stream().map(EmailAddressDbo::getEmailAddress).findFirst();
	}
}
