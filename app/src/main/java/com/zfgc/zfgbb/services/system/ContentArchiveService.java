package com.zfgc.zfgbb.services.system;

import static com.zfgc.zfgbb.operations.archive.OperationFiles.deleteTree;
import static com.zfgc.zfgbb.services.system.OperationStorageService.setPrivateDirectory;
import static com.zfgc.zfgbb.services.system.OperationStorageService.setPrivateFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;

@Service
public class ContentArchiveService {

	private static final Logger LOG = LoggerFactory.getLogger(ContentArchiveService.class);
	private static final Pattern CONTENT_PACK_NAME = Pattern.compile("[a-z0-9][a-z0-9-]{0,31}");
	private static final String ARCHIVE_FILE_NAME = "backup.tar.gz";
	private static final String ARCHIVE_RELATIVE_LOCATION = "/v1/" + ARCHIVE_FILE_NAME;

	private final ResourceLoader resources;
	private final String contentPackRoot;

	public ContentArchiveService(ResourceLoader resources,
			@Value("${zfgbb.install.content-pack-root}") String contentPackRoot) {
		this.resources = resources;
		this.contentPackRoot = contentPackRoot.endsWith("/") ? contentPackRoot : contentPackRoot + "/";
	}

	public boolean hasArchive(String contentPackName) {
		boolean present = archiveIsPresent(contentPackName);
		if (present)
			LOG.info("Content pack {} ships an installation archive at {}, so this installation "
					+ "restores it.", contentPackName, archiveLocation(contentPackName));
		else
			LOG.info("Content pack {} ships no installation archive at {}, so it cannot be "
					+ "installed.", contentPackName, archiveLocation(contentPackName));
		return present;
	}

	public boolean hasContentPack(String contentPackName) {
		boolean present = contentPackName != null
				&& CONTENT_PACK_NAME.matcher(contentPackName).matches()
				&& contentPackResource(contentPackName).exists();
		if (!present)
			LOG.info("This deployment ships no content pack named {} at {}, so the requested "
					+ "pack cannot be installed.", contentPackName, contentPackRoot);
		return present;
	}

	private boolean archiveIsPresent(String contentPackName) {
		return contentPackName != null && CONTENT_PACK_NAME.matcher(contentPackName).matches()
				&& archiveResource(contentPackName).exists();
	}

	public ContentArchive open(String contentPackName) {
		if (!archiveIsPresent(contentPackName))
			throw new ZfgcInvalidRequestException("Content pack " + contentPackName
					+ " does not ship an installation archive.");
		Resource archive = archiveResource(contentPackName);
		Path staging = null;
		try {
			if (archive.isFile())
				return new ContentArchive(archive.getFile().toPath().toAbsolutePath().normalize(),
						false);
			staging = Files.createTempDirectory("zfgbb-content-pack-");
			setPrivateDirectory(staging);
			Path materialized = staging.resolve(ARCHIVE_FILE_NAME);
			setPrivateFile(Files.createFile(materialized));
			try (InputStream packaged = archive.getInputStream();
					OutputStream target = Files.newOutputStream(materialized,
							StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
				packaged.transferTo(target);
			}
			ContentArchive opened = new ContentArchive(materialized, true);
			staging = null;
			return opened;
		} catch (IOException unreadable) {
			throw new IllegalStateException("Unable to read the installation archive for content pack "
					+ contentPackName + ".", unreadable);
		} finally {
			if (staging != null)
				deleteStagingDirectory(staging);
		}
	}

	private String archiveLocation(String contentPackName) {
		return contentPackRoot + contentPackName + ARCHIVE_RELATIVE_LOCATION;
	}

	private Resource archiveResource(String contentPackName) {
		return resources.getResource(contentPackRoot + contentPackName + ARCHIVE_RELATIVE_LOCATION);
	}

	private Resource contentPackResource(String contentPackName) {
		return resources.getResource(contentPackRoot + contentPackName + "/");
	}

	private static void deleteStagingDirectory(Path staging) {
		try {
			deleteTree(staging);
		} catch (IOException undeletable) {
			LOG.warn("Unable to remove the materialized content pack archive directory {}", staging,
					undeletable);
		}
	}

	public record ContentArchive(Path path, boolean materialized) implements AutoCloseable {

		@Override
		public void close() {
			if (!materialized)
				return;
			Path staging = path.getParent();
			if (staging != null)
				deleteStagingDirectory(staging);
		}
	}
}
