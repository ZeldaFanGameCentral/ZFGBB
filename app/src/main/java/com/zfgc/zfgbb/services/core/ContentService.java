package com.zfgc.zfgbb.services.core;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
import com.zfgc.zfgbb.services.AbstractService;

@Service
@ConfigurationProperties(prefix = "zfgbb.content")
public class ContentService extends AbstractService {
	private String path;
	private String images;

	@Autowired
	private ContentResourceDao contentResourceDao;

	public Resource getImageResource(Integer resourceId) throws MalformedURLException {
		ContentResourceDboExample ex = new ContentResourceDboExample();
		ex.createCriteria().andContentResourceIdEqualTo(resourceId);

		ContentResourceDbo dbo = contentResourceDao.get(ex).stream().findFirst().orElse(null);
		if (dbo == null) {
			throw new ZfgcNotFoundException();
		} else {
			return createContentResource(path + images, dbo.getFilename());
		}
	}

	public Optional<MediaType> getMimeType(String filename) {
		return MediaTypeFactory.getMediaType(filename);
	}

	private Resource createContentResource(String fullPath, String filename) throws MalformedURLException {
		Path path = Paths.get(fullPath + "/" + filename);
		Resource resource = new UrlResource(path.toUri());

		return resource;
	}
}