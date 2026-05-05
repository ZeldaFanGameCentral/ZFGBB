package com.zfgc.zfgbb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.config.loadoption.user.LoggedInUserLoadOptions;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.model.User;

@Component
public class JwtUserAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	@Autowired
	private UserDataProvider userDataProvider;

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		Integer userId = Integer.valueOf(jwt.getSubject());
		User user = userDataProvider.findUser(userId, new LoggedInUserLoadOptions())
				.orElseThrow(() -> new BadJwtException("JWT subject references unknown user " + userId));
		return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
	}
}
