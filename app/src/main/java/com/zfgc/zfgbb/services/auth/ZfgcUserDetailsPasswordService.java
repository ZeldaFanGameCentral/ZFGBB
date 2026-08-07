package com.zfgc.zfgbb.services.auth;

import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ConcurrentModificationException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.EncodedPassword;

@Service
@RequiredArgsConstructor
public class ZfgcUserDetailsPasswordService implements UserDetailsPasswordService {

	private final UserDao userDao;

	@Override
	public UserDetails updatePassword(UserDetails user, String newEncodedPassword) {
		User principal = (User) user;
		EncodedPassword upgraded = EncodedPassword.parse(newEncodedPassword);
		UserDbo userDbo = userDao.find(principal.getUserId()).orElseThrow();
		userDbo.setPasswordHash(upgraded.hash());
		userDbo.setPasswordAlgo(upgraded.algo().name());
		userDbo.setPasswordSalt(upgraded.salt());
		userDbo.setPasswordChangedTs(OffsetDateTime.now(ZoneOffset.UTC));
		try {
			userDao.save(userDbo);
		} catch (ConcurrentModificationException upgradeRaced) {
			return user;
		}

		principal.setPasswordHash(upgraded.hash());
		principal.setPasswordAlgo(upgraded.algo());
		principal.setPasswordSalt(upgraded.salt());
		return principal;
	}
}
