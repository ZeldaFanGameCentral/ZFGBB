package com.zfgc.zfgbb.services.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dao.core.ContentResourceDao;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDbo;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDboExample;
import com.zfgc.zfgbb.mappers.AttachmentBoardViewDboMapper;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.services.AbstractService;

@Service
@ConfigurationProperties(prefix = "zfgbb.content")
public class ContentService extends AbstractService {
	private String path;
	private String images;

	public void setPath(String path) {
		this.path = path;
	}

	public void setImages(String images) {
		this.images = images;
	}

	@Autowired
	private ContentResourceDao contentResourceDao;

	@Autowired
	private AttachmentBoardViewDboMapper attachmentBoardViewDboMapper;

	@Autowired
	private BoardPermissionViewDboMapper boardPermissionViewDboMapper;

	public void authorizeAccess(Integer resourceId, User user) {
		AttachmentBoardViewDboExample attEx = new AttachmentBoardViewDboExample();
		attEx.createCriteria().andContentResourceIdEqualTo(resourceId);
		attEx.setLimit(1);
		attEx.setOffset(0);
		List<AttachmentBoardViewDbo> results = attachmentBoardViewDboMapper.selectByExampleWithLimits(attEx);
		Integer boardId = results.isEmpty() ? null : results.get(0).getBoardId();
		if (boardId == null) {
			return;
		}
		BoardPermissionViewDboExample permEx = new BoardPermissionViewDboExample();
		permEx.createCriteria().andBoardIdEqualTo(boardId);
		List<Integer> required = boardPermissionViewDboMapper.selectByExample(permEx).stream()
				.map(BoardPermissionViewDbo::getPermissionId)
				.collect(Collectors.toList());
		Set<Integer> have = user == null ? Set.of()
				: user.getPermissions().stream().map(Permission::getPermissionId).collect(Collectors.toSet());
		if (required.stream().noneMatch(have::contains)) {
			throw new ZfgcNotFoundException();
		}
	}

	public Optional<ContentResourceDbo> getContentResourceDbo(Integer resourceId) {
		ContentResourceDboExample ex = new ContentResourceDboExample();
		ex.createCriteria().andContentResourceIdEqualTo(resourceId);
		return contentResourceDao.get(ex).stream().findFirst();
	}

	public Resource getImageResource(Integer resourceId) throws MalformedURLException {
		ContentResourceDbo dbo = getContentResourceDbo(resourceId).orElseThrow(ZfgcNotFoundException::new);
		return new UrlResource(storedFile(dbo).toUri());
	}

	public Path storedFile(ContentResourceDbo contentResource) {
		Path contentRoot = Paths.get(path).toAbsolutePath().normalize();
		Path resolved;
		if (contentResource.getStorageDir() != null && !contentResource.getStorageDir().isBlank()) {
			if (contentResource.getFilename() == null || contentResource.getFilename().isBlank()) {
				throw new ZfgcNotFoundException();
			}
			resolved = contentRoot.resolve(contentResource.getStorageDir())
					.resolve(String.valueOf(contentResource.getContentResourceId()))
					.resolve(contentResource.getFilename());
		} else {
			resolved = contentRoot.resolve(images)
					.resolve(String.valueOf(contentResource.getContentResourceId()));
		}
		Path confined = resolved.toAbsolutePath().normalize();
		if (!confined.startsWith(contentRoot)) {
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
		} catch (IOException e) {
			throw new ZfgcNotFoundException();
		}
	}
}
