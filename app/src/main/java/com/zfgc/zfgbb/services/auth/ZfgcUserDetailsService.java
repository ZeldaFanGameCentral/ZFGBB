package com.zfgc.zfgbb.services.auth;

import lombok.RequiredArgsConstructor;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.model.users.User;

@Service
@RequiredArgsConstructor
public class ZfgcUserDetailsService implements UserDetailsService {

	private final UserDataProvider userDataProvider;
	@Value("${zfgbb.auth.password.max-age-days}")
	private final long passwordMaxAgeDays;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDataProvider.findUserForAuthentication(userName)
				.orElseThrow(() -> new UsernameNotFoundException(userName));
		user.setCredentialsNonExpired(isPasswordCurrent(user));
		return user;
	}

	private boolean isPasswordCurrent(User user) {
		if (passwordMaxAgeDays <= 0)
			return true;
		OffsetDateTime passwordChanged = user.getPasswordChangedTs();
		if (passwordChanged == null)
			return true;
		return !passwordChanged.plus(Duration.ofDays(passwordMaxAgeDays)).isBefore(OffsetDateTime.now(ZoneOffset.UTC));
	}
}
