package com.zfgc.zfgbb.dao.users;

import java.util.Set;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.UserWarningDbo;
import com.zfgc.zfgbb.dbo.UserWarningDboExample;
import com.zfgc.zfgbb.mappers.UserWarningDboMapper;

@Repository
public class UserWarningDao extends IdentityDao<UserWarningDbo, UserWarningDboExample> {

	private static final String DELETED_NAME = "[deleted]";

	public UserWarningDao(UserWarningDboMapper mapper) {
		super(mapper);
	}

	public int scrubIssuedWarnings(Integer userId) {
		UserWarningDbo scrubbed = new UserWarningDbo();
		scrubbed.setIssuedByName(DELETED_NAME);
		UserWarningDboExample issuedByUser = new UserWarningDboExample();
		issuedByUser.createCriteria().andIssuedByUserIdEqualTo(userId);
		return updateWhereSettingColumns(scrubbed, Set.of("issued_by_user_id", "issued_by_name"), issuedByUser);
	}
}
