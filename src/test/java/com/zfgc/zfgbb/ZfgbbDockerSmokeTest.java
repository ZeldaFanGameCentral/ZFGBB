package com.zfgc.zfgbb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "testcontainers")
class ZfgbbComposeSmokeTest {

	private static final String PROJECT_NAME = "zfgbb-test-" + UUID.randomUUID();

	@SuppressWarnings("resource")
	@Container
	static ComposeContainer environment = new ComposeContainer(new File("docker-compose.yml"))
			.withLocalCompose(true)
			.withEnv("COMPOSE_PROJECT_NAME", PROJECT_NAME)
			.withExposedService(
					"api",
					8080,
					Wait.forListeningPort()
							.withStartupTimeout(Duration.ofMinutes(5)));

	@Test
	void apiStartsSuccessfully() {
		String host = environment.getServiceHost("api", 8080);
		Integer port = environment.getServicePort("api", 8080);

		assertNotNull(host);
		assertNotNull(port);

		System.out.println(
				"API running at http://" + host + ":" + port +
						" (project=" + PROJECT_NAME + ")");
	}
}
