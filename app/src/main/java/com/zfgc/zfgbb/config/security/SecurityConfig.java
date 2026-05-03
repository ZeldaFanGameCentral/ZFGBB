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
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtUserAuthenticationConverter jwtUserAuthenticationConverter;

	@Autowired
	private CookieOrHeaderBearerTokenResolver bearerTokenResolver;
	private final PathPatternRequestMatcher.Builder mvc = PathPatternRequestMatcher.withDefaults();

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				// Actuator health endpoints — reachable anonymously so K8s probes succeed.
				.requestMatchers(mvc.matcher("/actuator/health/**")).permitAll()
				.requestMatchers(mvc.matcher("/error")).permitAll()
				// CORS preflight
				.requestMatchers(mvc.matcher(HttpMethod.OPTIONS, "/**")).permitAll()
				// auth endpoints (login/refresh/logout/register) reachable without a token
				.requestMatchers(
					mvc.matcher("/users/auth/**"),
					mvc.matcher("/users/register")
				).permitAll()
				// first-run install endpoints. Token check + installed-marker check live
				// inside SystemController; SecurityConfig just lets the request reach it.
				.requestMatchers(mvc.matcher("/system/install/**")).permitAll()
				// Default rule: every read is public; every write requires auth.
				// Finer-grained role checks live on individual handlers via @PreAuthorize.
				.requestMatchers(
					mvc.matcher(HttpMethod.GET, "/"),
					mvc.matcher(HttpMethod.GET, "/board/**"),
					mvc.matcher(HttpMethod.GET, "/thread/**"),
					mvc.matcher(HttpMethod.GET, "/message/**"),
					mvc.matcher(HttpMethod.GET, "/content/**"),
					mvc.matcher(HttpMethod.GET, "/resources/**"),
					mvc.matcher(HttpMethod.GET, "/user-profile/**"),
					mvc.matcher(HttpMethod.GET, "/users/loggedInUser")
				).permitAll()
				.anyRequest().authenticated())
			.oauth2ResourceServer(oauth -> oauth
				.bearerTokenResolver(bearerTokenResolver)
				.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtUserAuthenticationConverter)));

		return http.build();
	}
}
