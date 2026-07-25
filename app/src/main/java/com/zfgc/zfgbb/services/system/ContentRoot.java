package com.zfgc.zfgbb.services.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContentRoot {
	private final Path contentRoot;

	private volatile boolean directoryCreated;

	public ContentRoot(@Value("${zfgbb.content.path}") String configuredPath) {
		Path configured = Path.of(configuredPath);
		contentRoot = configured.isAbsolute() ? configured.normalize()
				: Path.of(System.getProperty("zfgbb.content.base", System.getProperty("user.dir")))
						.resolve(configured).toAbsolutePath().normalize();
	}

	public Path configuredContentRoot() {
		return contentRoot;
	}

	public Path activeContentRoot() {
		if (!directoryCreated) {
			try {
				Files.createDirectories(contentRoot);
			} catch (IOException unusableContentRoot) {
				throw new IllegalStateException("unable to access configured content root",
						unusableContentRoot);
			}
			directoryCreated = true;
		}
		return contentRoot;
	}
}
