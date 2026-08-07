package com.zfgc.zfgbb.config.security;

import com.zfgc.zfgbb.authorization.AllowAnonymousRequestMatcher;
import com.zfgc.zfgbb.services.auth.ZfgcPasswordEncoder;
import com.zfgc.zfgbb.services.auth.ZfgcUserDetailsPasswordService;
import com.zfgc.zfgbb.services.auth.ZfgcUserDetailsService;
import com.zfgc.zfgbb.web.filter.AccessCookieBearerHeaderFilter;
import com.zfgc.zfgbb.web.filter.MaintenanceGateFilter;
import com.zfgc.zfgbb.web.filter.PartialInstallGateFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.zfgc.zfgbb.services.auth.AuthCookieService;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserJwtAuthenticationConverter jwtUserAuthenticationConverter;
	private final AccessCookieBearerHeaderFilter accessCookieBearerHeaderFilter;
	private final PartialInstallGateFilter partialInstallGateFilter;
	private final MaintenanceGateFilter maintenanceGateFilter;
	private final AllowAnonymousRequestMatcher allowAnonymous;
	private final AuthCookieService authCookieService;
	private final PathPatternRequestMatcher.Builder mvc = PathPatternRequestMatcher.withDefaults();

	private static final Set<String> CSRF_SAFE_METHODS = Set.of("GET", "HEAD", "OPTIONS", "TRACE");

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler csrfHandler = new CsrfTokenRequestAttributeHandler();
		csrfHandler.setCsrfRequestAttributeName(null);

		CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		csrfTokenRepository.setCookieCustomizer(c -> c.path("/"));

		RequestMatcher refreshEndpoint = mvc.matcher(HttpMethod.POST, "/users/auth/refresh");
		RequestMatcher installEndpoint = mvc.matcher(HttpMethod.POST, "/system/install");
		RequestMatcher requireCsrf = request -> !CSRF_SAFE_METHODS.contains(request.getMethod())
				&& !refreshEndpoint.matches(request)
				&& !installEndpoint.matches(request)
				&& request.getHeader(HttpHeaders.AUTHORIZATION) == null
				&& authCookieService.readAccessCookie(request).isPresent();

		http
				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf
						.csrfTokenRepository(csrfTokenRepository)
						.csrfTokenRequestHandler(csrfHandler)
						.requireCsrfProtectionMatcher(requireCsrf))
					.addFilterBefore(accessCookieBearerHeaderFilter, BearerTokenAuthenticationFilter.class)
					.addFilterBefore(partialInstallGateFilter, AccessCookieBearerHeaderFilter.class)
					.addFilterBefore(maintenanceGateFilter, PartialInstallGateFilter.class)
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(mvc.matcher("/actuator/health/**")).permitAll()
						.requestMatchers(mvc.matcher("/error")).permitAll()
						.requestMatchers(mvc.matcher(HttpMethod.OPTIONS, "/**")).permitAll()
						.requestMatchers(allowAnonymous).permitAll()
						.anyRequest().authenticated())
				.oauth2ResourceServer(oauth -> oauth
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtUserAuthenticationConverter)));

		return http.build();
	}

	@Bean
	public FilterRegistrationBean<MaintenanceGateFilter> maintenanceGateFilterRegistration() {
		return securityChainOnly(maintenanceGateFilter);
	}

	@Bean
	public FilterRegistrationBean<PartialInstallGateFilter> partialInstallGateFilterRegistration() {
		return securityChainOnly(partialInstallGateFilter);
	}

	@Bean
	public FilterRegistrationBean<AccessCookieBearerHeaderFilter> accessCookieBearerHeaderFilterRegistration() {
		return securityChainOnly(accessCookieBearerHeaderFilter);
	}

	private static <T extends Filter> FilterRegistrationBean<T> securityChainOnly(T filter) {
		FilterRegistrationBean<T> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false);
		return registration;
	}

	@Bean
	public AuthenticationManager loginAuthenticationManager(ZfgcUserDetailsService userDetailsService,
			ZfgcPasswordEncoder passwordEncoder,
			ZfgcUserDetailsPasswordService userDetailsPasswordService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsPasswordService(userDetailsPasswordService);
		return new ProviderManager(provider);
	}
}
