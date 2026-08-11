package com.zfgc.zfgbb.dao.forum;

import java.util.List;
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

	public List<Integer> findMessageIdsReceivedBy(Integer userId) {
		PersonalMessageRecipientDboExample received = new PersonalMessageRecipientDboExample();
		received.createCriteria().andRecipientUserIdEqualTo(userId);
		return get(received).stream().map(PersonalMessageRecipientDbo::getPersonalMessageId).toList();
	}
}
