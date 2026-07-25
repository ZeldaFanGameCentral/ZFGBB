package com.zfgc.zfgbb.services;

import com.zfgc.zfgbb.exception.ZfgcUnauthorizedException;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.security.Securable;

public abstract class AbstractService {

	protected void secureObject(Securable secureThis, User zfgcUser) {
		if (!zfgcUser.canAccess(secureThis))
			throw new ZfgcUnauthorizedException("Insufficient permissions for resource.", zfgcUser);
	}

}
