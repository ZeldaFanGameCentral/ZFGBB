package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PersonalMessageDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageDboExample;
import com.zfgc.zfgbb.mappers.PersonalMessageDboMapper;

@Repository
public class PersonalMessageDao extends IdentityDao<PersonalMessageDbo, PersonalMessageDboExample> {

	public PersonalMessageDao(PersonalMessageDboMapper mapper) {
		super(mapper);
	}
}
