package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDbo;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDboExample;
import com.zfgc.zfgbb.mappers.NotificationSubscriptionDboMapper;

@Repository
public class NotificationSubscriptionDao extends IdentityDao<NotificationSubscriptionDbo, NotificationSubscriptionDboExample> {

	public NotificationSubscriptionDao(NotificationSubscriptionDboMapper mapper) {
		super(mapper);
	}
}
