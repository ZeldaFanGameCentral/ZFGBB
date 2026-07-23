package com.zfgc.zfgbb.config.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

	private static final String PLACEHOLDER_SECRET = "dev-only-secret-replace-me-with-32-or-more-bytes-please";
	private static final List<String> DEV_PROFILES = List.of("local");

	private final JwtProperties jwtProperties;
	private final Environment environment;

	public JwtConfig(JwtProperties jwtProperties, Environment environment) {
		this.jwtProperties = jwtProperties;
		this.environment = environment;
	}

	@Bean
	public SecretKeySpec jwtSigningKey() {
		String secret = jwtProperties.secret();
		boolean devProfile = Arrays.stream(environment.getActiveProfiles()).anyMatch(DEV_PROFILES::contains);
		boolean weakSecret = secret == null || secret.isBlank()
				|| secret.equals(PLACEHOLDER_SECRET)
				|| secret.length() < 32;
		if (!devProfile && weakSecret) {
			throw new IllegalStateException(
					"zfgbb.auth.jwt.secret must be set to a strong (>= 32 char) value outside the local profile. "
							+ "Set the ZFGBB_AUTH_JWT_SECRET environment variable.");
		}
		return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
	}

	@Bean
	public JwtEncoder jwtEncoder(SecretKeySpec key) {
		return new NimbusJwtEncoder(new ImmutableSecret<>(key));
	}

	@Bean
	public JwtDecoder jwtDecoder(SecretKeySpec key) {
		return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
	}
}
