package com.zfgc.zfgbb.config.security;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
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
				.orElseThrow(() -> new InvalidBearerTokenException("JWT subject references unknown user " + userId));
		if (!user.isEnabled())
			throw new InvalidBearerTokenException("JWT subject references disabled user " + userId);
		if (issuedBeforeTokenCutoff(jwt, user.getTokensValidAfterTs()))
			throw new InvalidBearerTokenException(
					"JWT for user " + userId + " was issued before the token validity cutoff");
		return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
	}

	private boolean issuedBeforeTokenCutoff(Jwt jwt, OffsetDateTime tokensValidAfterTs) {
		if (tokensValidAfterTs == null)
			return false;
		return jwt.getIssuedAt() == null || jwt.getIssuedAt().isBefore(tokensValidAfterTs.toInstant());
	}
}
