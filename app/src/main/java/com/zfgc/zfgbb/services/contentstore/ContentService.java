package com.zfgc.zfgbb.services.contentstore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.model.cms.ReleasedResource;
import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDbo;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDboExample;
import com.zfgc.zfgbb.dao.forum.AttachmentBoardViewDao;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dao.forum.BoardPermissionViewDao;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.services.contentstore.ContentRoot;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class ContentService {
	private static final String LEGACY_IMAGE_DIRECTORY = "images";

	private final ContentResourceDao contentResourceDao;

	private final AttachmentBoardViewDao attachmentBoardViewDao;

	private final BoardPermissionViewDao boardPermissionViewDao;

	private final ContentRoot contentRoot;

	public void authorizeAccess(Integer resourceId, User user) {
		AttachmentBoardViewDboExample attEx = new AttachmentBoardViewDboExample();
		attEx.createCriteria().andContentResourceIdEqualTo(resourceId);
		attEx.setLimit(1);
		attEx.setOffset(0);
		List<AttachmentBoardViewDbo> results = attachmentBoardViewDao.get(attEx);
		Integer boardId = results.isEmpty() ? null : results.get(0).getBoardId();
		if (boardId == null) {
			return;
		}
		BoardPermissionViewDboExample permEx = new BoardPermissionViewDboExample();
		permEx.createCriteria().andBoardIdEqualTo(boardId);
		List<Integer> required = boardPermissionViewDao.get(permEx).stream()
				.map(BoardPermissionViewDbo::getPermissionId)
				.toList();
		if (!user.hasAnyPermissionId(required)) {
			throw new ZfgcNotFoundException();
		}
	}

	public Optional<ContentResourceDbo> getContentResourceDbo(Integer resourceId) {
		ContentResourceDboExample ex = new ContentResourceDboExample();
		ex.createCriteria().andContentResourceIdEqualTo(resourceId);
		return contentResourceDao.getOne(ex);
	}

	public Resource getImageResource(Integer resourceId) throws MalformedURLException {
		ContentResourceDbo dbo = getContentResourceDbo(resourceId).orElseThrow(ZfgcNotFoundException::new);
		return new UrlResource(storedFile(dbo).toUri());
	}

	public Path storedFile(ContentResourceDbo contentResource) {
		return storedFile(contentResource.getContentResourceId(), contentResource.getStorageDir(),
				contentResource.getFilename());
	}

	public Path storedFile(ReleasedResource released) {
		return storedFile(released.contentResourceId(), released.storageDir(), released.filename());
	}

	public Path storedFile(Integer contentResourceId, String storageDir, String filename) {
		Path activeContentRoot = contentRoot.activeContentRoot();
		Path resolved;
		if (storageDir != null && !storageDir.isBlank()) {
			if (filename == null || filename.isBlank()) {
				throw new ZfgcNotFoundException();
			}
			resolved = activeContentRoot.resolve(storageDir)
					.resolve(String.valueOf(contentResourceId))
					.resolve(filename);
		} else {
			resolved = activeContentRoot.resolve(LEGACY_IMAGE_DIRECTORY)
					.resolve(String.valueOf(contentResourceId));
		}
		Path confined = resolved.toAbsolutePath().normalize();
		if (!confined.startsWith(activeContentRoot)) {
			throw new ZfgcNotFoundException();
		}
		return confined;
	}

	public Optional<MediaType> getMimeType(String filename) {
		return MediaTypeFactory.getMediaType(filename);
	}

	public record ArchiveEntry(String name, long size) {}

	public List<ArchiveEntry> getArchiveEntries(Integer resourceId) {
		ContentResourceDbo dbo = getContentResourceDbo(resourceId).orElseThrow(ZfgcNotFoundException::new);
		if (dbo.getFilename() == null || !dbo.getFilename().toLowerCase().endsWith(".zip"))
			throw new ZfgcNotFoundException();
		Path filePath = storedFile(dbo);
		try (ZipFile zip = new ZipFile(filePath.toFile())) {
			return zip.stream()
					.filter(entry -> !entry.isDirectory())
					.map(entry -> new ArchiveEntry(entry.getName(), entry.getSize()))
					.toList();
		} catch (IOException storageFailure) {
			throw new ZfgcNotFoundException();
		}
	}
}
