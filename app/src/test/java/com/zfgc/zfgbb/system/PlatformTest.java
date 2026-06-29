package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.zfgc.zfgbb.config.MailDispatcherConfig;
import com.zfgc.zfgbb.config.security.PartialInstallGateFilter;
import com.zfgc.zfgbb.services.core.MailDispatcher;
import com.zfgc.zfgbb.services.system.InstallRunRepository;

class PlatformTest {

	@Nested
	class InstallGate {

		private record GateRun(MockFilterChain chain, MockHttpServletResponse response) {}

		private InstallRunRepository installs;
		private PartialInstallGateFilter filter;

		@BeforeEach
		void setup() {
			installs = mock(InstallRunRepository.class);
			filter = new PartialInstallGateFilter(installs);
		}

		private GateRun runFilter(String state, String method, String contextPath, String requestUri) throws Exception {
			when(installs.get()).thenReturn(new InstallRunRepository.Run(state, null, null, null, null));
			MockHttpServletRequest request = new MockHttpServletRequest(method, requestUri);
			request.setContextPath(contextPath);
			MockHttpServletResponse response = new MockHttpServletResponse();
			MockFilterChain chain = new MockFilterChain();
			filter.doFilter(request, response, chain);
			return new GateRun(chain, response);
		}

		@ParameterizedTest
		@MethodSource("gateDecisionCases")
		void gateDecisionMatrix(String caseName, String state, String method, String contextPath, String uri,
				boolean expectPass) throws Exception {
			GateRun run = runFilter(state, method, contextPath, uri);

			if (expectPass) {
				assertNotNull(run.chain().getRequest());
				assertEquals(HttpStatus.OK.value(), run.response().getStatus());
			} else {
				assertNull(run.chain().getRequest());
				assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), run.response().getStatus());
			}
		}

		static Stream<Arguments> gateDecisionCases() {
			return Stream.of(
					arguments("uninstalledStateStillAllowsAllowlistedPaths install",
							"PROVISIONING", "GET", "", "/system/install", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths installStatus",
							"PROVISIONING", "GET", "", "/system/install/status", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths actuatorHealth",
							"PROVISIONING", "GET", "", "/actuator/health", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths actuatorHealthLiveness",
							"PROVISIONING", "GET", "", "/actuator/health/liveness", true),
					arguments("uninstalledStateStillAllowsAllowlistedPaths error",
							"PROVISIONING", "GET", "", "/error", true),
					arguments("uninstalledStateAllowsOptionsPreflightRegardlessOfPath",
							"PROVISIONING", "OPTIONS", "", "/thread", true),
					arguments("installedStatePassesEveryRequestThrough",
							"INSTALLED", "GET", "", "/thread", true),
					arguments("allowlistIsEvaluatedAgainstThePathWithTheContextPathStripped allowlisted",
							"PROVISIONING", "GET", "/api", "/api/system/install", true),
					arguments("allowlistIsEvaluatedAgainstThePathWithTheContextPathStripped blocked",
							"PROVISIONING", "GET", "/api", "/api/thread", false),
					arguments("contextPathInstallEndpointRemainsReachableDuringRecovery",
							"FAILED", "POST", "/zfgbb", "/zfgbb/system/install", true),
					arguments("readyStillGatesApplicationButAllowsOptions gated",
							"READY", "GET", "/zfgbb", "/zfgbb/users/register", false),
					arguments("readyStillGatesApplicationButAllowsOptions optionsAllowed",
							"READY", "OPTIONS", "/zfgbb", "/zfgbb/users/register", true),
					arguments("prefixLookalikeDoesNotBypassGate",
							"CORE_READY", "GET", "", "/system/install-secret", false));
		}

		@Test
		void uninstalledStateBlocksNonAllowlistedPathWithServiceUnavailable() throws Exception {
			GateRun run = runFilter("PROVISIONING", "GET", "", "/thread");

			assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), run.response().getStatus());
			assertEquals("Installation recovery is in progress.", run.response().getErrorMessage());
			assertNull(run.chain().getRequest());
		}
	}

	@Nested
	class MailDispatch {

		private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withUserConfiguration(MailDispatcherConfig.class);

		@Test
		void blankHostActivatesTheInMemoryCaptureDispatcher() {
			contextRunner.withPropertyValues("spring.mail.host=").run(context -> {
				assertEquals(1, context.getBeansOfType(MailDispatcher.class).size());
				assertInstanceOf(MailDispatcherConfig.InMemoryMailDispatcher.class, context.getBean(MailDispatcher.class));
			});
		}

		@Test
		void missingHostPropertyActivatesTheInMemoryCaptureDispatcher() {
			contextRunner.run(context -> assertInstanceOf(MailDispatcherConfig.InMemoryMailDispatcher.class,
					context.getBean(MailDispatcher.class)));
		}

		@Test
		void configuredHostActivatesTheSmtpDispatcher() {
			contextRunner.withPropertyValues("spring.mail.host=smtp.fastmail.com")
					.withBean(JavaMailSender.class, JavaMailSenderImpl::new)
					.run(context -> {
						assertEquals(1, context.getBeansOfType(MailDispatcher.class).size());
						assertFalse(context.getBean(MailDispatcher.class)
								instanceof MailDispatcherConfig.InMemoryMailDispatcher);
					});
		}

		@Test
		void prodProfileWithoutHostDisablesOutboundMailEntirely() {
			contextRunner.withPropertyValues("spring.profiles.active=prod", "spring.mail.host=")
					.run(context -> assertTrue(context.getBeansOfType(MailDispatcher.class).isEmpty()));
		}

		@Test
		void inMemoryDispatcherCapturesMessagesForAssertions() {
			MailDispatcherConfig.InMemoryMailDispatcher dispatcher = new MailDispatcherConfig.InMemoryMailDispatcher();
			dispatcher.dispatch(new MailDispatcher.OutboundMail("someone@example.test", "Subject line", "Body text"));
			assertEquals(1, dispatcher.sentMessages().size());
			assertEquals("Subject line", dispatcher.sentMessages().get(0).subject());
			dispatcher.clear();
			assertTrue(dispatcher.sentMessages().isEmpty());
		}
	}
}
