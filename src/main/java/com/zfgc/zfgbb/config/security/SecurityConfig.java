package com.zfgc.zfgbb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtUserAuthenticationConverter jwtUserAuthenticationConverter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				// CORS preflight
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// auth endpoints (login/refresh/logout/register) must be reachable without a token
				.requestMatchers("/users/auth/**", "/users/register").permitAll()
				// first-run install endpoints. Token check + installed-marker check live
				// inside SystemController; SecurityConfig just lets the request reach it.
				.requestMatchers("/system/install/**").permitAll()
				// Default rule: every read is public; every write requires auth.
				// Finer-grained role checks live on individual handlers via @PreAuthorize.
				// Permission-table-driven gating is planned but not implemented yet.
				.requestMatchers(HttpMethod.GET,
					"/",
					"/board/**", "/thread/**", "/message/**",
					"/content/**", "/resources/**", "/user-profile/**",
					"/users/loggedInUser").permitAll()
				.anyRequest().authenticated())
			.oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtUserAuthenticationConverter)));

		return http.build();
	}
}
