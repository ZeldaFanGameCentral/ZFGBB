package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDbo;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDboExample;
import com.zfgc.zfgbb.mappers.AccountDeletionRequestDboMapper;

@Repository
public class AccountDeletionRequestDao extends IdentityDao<AccountDeletionRequestDbo, AccountDeletionRequestDboExample> {

	public AccountDeletionRequestDao(AccountDeletionRequestDboMapper mapper) {
		super(mapper);
	}
}
