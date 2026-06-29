package com.zfgc.zfgbb.migrator.converters.cms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.migrator.converters.MigrationHasher;

public class CmsAssetStore {

	public static final int TYPE_IMAGE = 3;
	public static final int TYPE_DOWNLOAD = 4;

	private static final Logger logger = LoggerFactory.getLogger(CmsAssetStore.class);

	private final ContentResourceDboMapper contentMapper;
	private final Path targetDir;
	private final String storageDir;
	private final Map<String, Integer> cache = new HashMap<>();

	public CmsAssetStore(ContentResourceDboMapper contentMapper, String targetPath, String storageDir) {
		this.contentMapper = contentMapper;
		this.targetDir = Paths.get(targetPath);
		this.storageDir = storageDir;
		try {
			Files.createDirectories(targetDir);
		} catch (IOException e) {
			throw new IllegalStateException("cannot create content target dir " + targetPath, e);
		}
	}

	public Integer store(Path sourceFile, Integer uploaderId, int contentTypeId) {
		if (sourceFile == null) {
			return null;
		}
		return store(sourceFile, sourceFile.getFileName().toString(), uploaderId, contentTypeId);
	}

	public Integer store(Path sourceFile, String filename, Integer uploaderId, int contentTypeId) {
		if (sourceFile == null) {
			return null;
		}
		String key = sourceFile.toString();
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		Integer id = copy(sourceFile, filename, uploaderId, contentTypeId);
		cache.put(key, id);
		return id;
	}

	private Integer copy(Path sourceFile, String filename, Integer uploaderId, int contentTypeId) {
		if (!Files.isRegularFile(sourceFile)) {
			logger.warn("cms asset missing, skipping: {}", sourceFile);
			return null;
		}
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(sourceFile);
		} catch (IOException e) {
			logger.warn("cms asset unreadable, skipping: {} ({})", sourceFile, e.toString());
			return null;
		}

		ContentResourceDbo resource = new ContentResourceDbo();
		resource.setContentTypeId(contentTypeId);
		resource.setFilename(filename);
		resource.setChecksum(md5Hex(bytes));
		resource.setFileExt(extension(filename));
		resource.setMimeType(MediaTypeFactory.getMediaType(filename)
				.orElse(MediaType.APPLICATION_OCTET_STREAM).toString());
		resource.setUploadedUserId(uploaderId != null ? uploaderId : 1);
		resource.setFileSize((long) bytes.length);
		resource.setStorageDir(storageDir);
		resource.setMigrationHash(MigrationHasher.hash("cmsasset" + contentTypeId + filename
				+ resource.getChecksum() + resource.getFileSize()));

		ContentResourceDboExample ex = new ContentResourceDboExample();
		ex.createCriteria().andMigrationHashEqualTo(resource.getMigrationHash());
		contentMapper.selectByExample(ex).stream().findFirst()
				.ifPresentOrElse(
						existing -> {
							resource.setContentResourceId(existing.getContentResourceId());
							if (!storageDir.equals(existing.getStorageDir())) {
								contentMapper.updateByPrimaryKeySelective(resource);
							}
						},
						() -> contentMapper.insert(resource));

		Path destination = targetDir.resolve(storageDir)
				.resolve(String.valueOf(resource.getContentResourceId()))
				.resolve(filename);
		try {
			Files.createDirectories(destination.getParent());
			Files.copy(sourceFile, destination, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("failed to write " + destination, e);
		}
		return resource.getContentResourceId();
	}

	private static String extension(String filename) {
		int dot = filename.lastIndexOf('.');
		return dot >= 0 ? filename.substring(dot + 1).toLowerCase() : "";
	}

	static String md5Hex(byte[] bytes) {
		try {
			return HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(bytes));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 unavailable", e);
		}
	}
}
