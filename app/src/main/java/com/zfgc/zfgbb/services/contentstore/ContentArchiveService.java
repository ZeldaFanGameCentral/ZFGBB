package com.zfgc.zfgbb.services.contentstore;

import lombok.extern.slf4j.Slf4j;
import static com.zfgc.zfgbb.operations.archive.OperationFiles.deleteTree;
import static com.zfgc.zfgbb.services.backup.OperationStorageService.setPrivateDirectory;
import static com.zfgc.zfgbb.services.backup.OperationStorageService.setPrivateFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentArchiveService {

	private static final String ARCHIVE_FILE_NAME = "backup.tar.gz";

	private final ResourceLoader resources;
	@Value("${zfgbb.install.sample-archive}")
	private final String sampleArchiveLocation;

	public boolean hasSampleArchive() {
		return sampleArchive().exists();
	}

	public ContentArchive openSampleArchive() {
		Resource archive = sampleArchive();
		if (!archive.exists())
			throw new ZfgcInvalidRequestException(
					"no sample data archive at " + sampleArchiveLocation);
		Path staging = null;
		try {
			if (archive.isFile())
				return new ContentArchive(archive.getFile().toPath().toAbsolutePath().normalize(),
						false);
			staging = Files.createTempDirectory("zfgbb-sample-data-");
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
			throw new IllegalStateException(
					"unable to read the sample data archive at " + sampleArchiveLocation, unreadable);
		} finally {
			if (staging != null)
				deleteStagingDirectory(staging);
		}
	}

	private Resource sampleArchive() {
		return resources.getResource(sampleArchiveLocation);
	}

	private static void deleteStagingDirectory(Path staging) {
		try {
			deleteTree(staging);
		} catch (IOException undeletable) {
			log.warn("unable to remove the materialized sample data directory {}", staging, undeletable);
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
