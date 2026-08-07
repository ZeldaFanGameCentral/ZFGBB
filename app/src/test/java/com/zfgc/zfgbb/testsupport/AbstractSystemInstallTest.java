package com.zfgc.zfgbb.testsupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.ComposeContainer;

import com.zfgc.zfgbb.dbo.InstallRunDboExample;
import com.zfgc.zfgbb.mappers.InstallRunDboMapper;

public abstract class AbstractSystemInstallTest extends ZfgbbIntegrationTest {

	@Autowired
	protected InstallRunDboMapper installRunDboMapper;

	protected static Path contentRoot(String label) {
		return Path.of(System.getProperty("java.io.tmpdir"),
				"zfgbb-" + label + "-" + UUID.randomUUID());
	}

	protected static void installDatasource(DynamicPropertyRegistry registry,
			ComposeContainer pg, Path contentRoot) {
		datasource(registry, pg);
		registry.add("zfgbb.content.path", contentRoot::toString);
	}

	protected void assertInstalledWithStrategy(String installStrategy) {
		InstallRunDboExample ranTheStrategy = new InstallRunDboExample();
		ranTheStrategy.createCriteria().andInstallIdEqualTo((short) 1)
				.andInstallStrategyEqualTo(installStrategy);
		assertEquals(1, installRunDboMapper.countByExample(ranTheStrategy),
				"the installation must have run the " + installStrategy + " strategy");
	}

	public static String installBody(boolean recycle) {
		return installBody(recycle, true);
	}

	public static String installBody(boolean recycle, boolean installSampleData) {
		return """
				{
				  "adminUserName": "pack_admin",
				  "adminDisplayName": "Pack Administrator",
				  "adminEmail": "pack-admin@example.invalid",
				  "adminPassword": "pack-admin-password",
				  "siteName": "Installer Site Name",
				  "installSampleData": %s,
				  "provisionRecycleBin": %s
				}
				""".formatted(installSampleData, recycle);
	}
}
