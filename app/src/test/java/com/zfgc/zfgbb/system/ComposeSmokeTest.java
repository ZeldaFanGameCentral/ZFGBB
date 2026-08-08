package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers
@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "testcontainers")
class ComposeSmokeTest {

	private static final int PG_PORT = freePort();
	private static final int API_PORT = freePort();

	@SuppressWarnings("resource")
	@Container
	static ComposeContainer stack = new ComposeContainer(composeFile())
			.withEnv("COMPOSE_PROJECT_NAME", "zfgbb-smoke-" + UUID.randomUUID().toString().substring(0, 8))
			.withEnv("POSTGRES_PORT", String.valueOf(PG_PORT))
			.withEnv("ZFGBB_BACKEND_PORT", String.valueOf(API_PORT))
			.withBuild(true)
			.waitingFor("api", Wait.forLogMessage(".*Started.*", 1)
					.withStartupTimeout(Duration.ofMinutes(5)));

	@Test
	void freshStackBootsUninstalled() throws Exception {
		String url = "http://localhost:" + API_PORT + "/zfgbb/system/site";
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = null;
		for (int attempt = 0; attempt < 30; attempt++) {
			try {
				response = client.send(HttpRequest.newBuilder(URI.create(url)).GET().build(),
						HttpResponse.BodyHandlers.ofString());
				if (response.statusCode() == 200) {
					break;
				}
			} catch (Exception e) {
				Thread.sleep(2000);
			}
		}
		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("\"installed\":false"),
				"a fresh compose stack should report uninstalled: " + response.body());
	}

	private static int freePort() {
		try (java.net.ServerSocket s = new java.net.ServerSocket(0)) {
			return s.getLocalPort();
		} catch (java.io.IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private static File composeFile() {
		Path current = new File("").getAbsoluteFile().toPath();
		while (current != null) {
			Path candidate = current.resolve("docker-compose.yml");
			if (Files.exists(candidate)) {
				return candidate.toFile();
			}
			current = current.getParent();
		}
		throw new IllegalStateException("docker-compose.yml not found");
	}
}
