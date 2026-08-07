package com.zfgc.zfgbb.services.contentstore;

import com.zfgc.zfgbb.services.contentstore.ContentRoot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ContentRootTest {

	@TempDir
	Path temporary;

	@Test
	void relativeContentPathUsesConfiguredLaunchBase() {
		String previous = System.getProperty("zfgbb.content.base");
		try {
			System.setProperty("zfgbb.content.base", temporary.toString());
			assertEquals(temporary.resolve("backend-assets"),
					new ContentRoot("backend-assets").activeContentRoot());
		} finally {
			restore(previous);
		}
	}

	@Test
	void absoluteOverrideIsNeverPrefixedByLaunchBase() {
		String previous = System.getProperty("zfgbb.content.base");
		try {
			System.setProperty("zfgbb.content.base",
					temporary.resolve("unwanted-base").toString());
			Path absolute = temporary.resolve("absolute-assets");
			assertEquals(absolute, new ContentRoot(absolute.toString()).activeContentRoot());
		} finally {
			restore(previous);
		}
	}

	@Test
	void contentDirectoryIsCreatedOnceInsteadOfOnEveryRead() throws IOException {
		ContentRoot contentRoot = new ContentRoot(temporary.resolve("created-once").toString());
		Path created = contentRoot.activeContentRoot();
		assertTrue(Files.isDirectory(created), "the first read must materialise the content root");
		Files.delete(created);
		assertEquals(created, contentRoot.activeContentRoot());
		assertFalse(Files.exists(created),
				"image serving is a hot path, so the content root must be created once and cached, "
						+ "not re-created on every read");
	}

	private static void restore(String previous) {
		if (previous == null)
			System.clearProperty("zfgbb.content.base");
		else
			System.setProperty("zfgbb.content.base", previous);
	}
}
