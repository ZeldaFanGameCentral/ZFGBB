package com.zfgc.zfgbb.dao.users;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PersonalMessageConversationDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageConversationDboExample;
import com.zfgc.zfgbb.mappers.PersonalMessageConversationDboMapper;

@Repository
public class PersonalMessageConversationDao
		extends IdentityDao<PersonalMessageConversationDbo, PersonalMessageConversationDboExample> {

	public PersonalMessageConversationDao(PersonalMessageConversationDboMapper mapper) {
		super(mapper);
	}

	public int deleteByIds(List<Integer> conversationIds) {
		PersonalMessageConversationDboExample byIds = new PersonalMessageConversationDboExample();
		byIds.createCriteria().andPersonalMessageConversationIdIn(conversationIds);
		return deleteWhere(byIds);
	}
}
