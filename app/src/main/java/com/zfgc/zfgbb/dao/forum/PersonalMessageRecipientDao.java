package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDboExample;
import com.zfgc.zfgbb.mappers.PersonalMessageRecipientDboMapper;

@Repository
public class PersonalMessageRecipientDao extends IdentityDao<PersonalMessageRecipientDbo, PersonalMessageRecipientDboExample> {

	public PersonalMessageRecipientDao(PersonalMessageRecipientDboMapper mapper) {
		super(mapper);
	}
}
