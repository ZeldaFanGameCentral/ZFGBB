package com.zfgc.zfgbb.config.security;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.config.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.model.User;

@Component
@RequiredArgsConstructor
public class JwtUserAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final UserDataProvider userDataProvider;

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		Integer userId = Integer.valueOf(jwt.getSubject());
		User user = userDataProvider.findUser(userId, UserLoadOptions.loggedIn())
				.orElseThrow(() -> new InvalidBearerTokenException("JWT subject references unknown user " + userId));
		if (!user.isEnabled())
			throw new InvalidBearerTokenException("JWT subject references disabled user " + userId);
		if (user.invalidatesTokenIssuedAt(Optional.ofNullable(jwt.getIssuedAt())))
			throw new InvalidBearerTokenException(
					"JWT for user " + userId + " was issued before the token validity cutoff");
		return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
	}
}
