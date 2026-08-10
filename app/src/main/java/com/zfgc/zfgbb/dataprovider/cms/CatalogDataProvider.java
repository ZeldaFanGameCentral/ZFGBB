package com.zfgc.zfgbb.dataprovider.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dao.users.UserErasureDao;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.model.cms.ReleasedResource;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CatalogDataProvider {

	protected final UserDao userDao;

	protected final ContentResourceDao contentResourceDao;

	protected final UserErasureDao userErasureDao;

	Map<Integer, String> displayNames(Stream<Integer> userIds) {
		Map<Integer, String> names = new HashMap<>();
		List<Integer> ids = userIds.filter(Objects::nonNull).distinct().toList();
		if (ids.isEmpty()) {
			return names;
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserIdIn(ids);
		userDao.get(ex).forEach(user -> names.put(user.getUserId(), user.getDisplayName()));
		return names;
	}

	String contentFilename(Integer contentResourceId) {
		if (contentResourceId == null) {
			return null;
		}
		return contentResourceDao.find(contentResourceId).map(ContentResourceDbo::getFilename).orElse(null);
	}

	static boolean containsIgnoreCase(String haystack, String needle) {
		return haystack != null && haystack.toLowerCase().contains(needle.trim().toLowerCase());
	}

	static <Item> Stream<Item> pageSlice(List<Item> items, int page, int pageSize) {
		return items.stream().skip((long) (page - 1) * pageSize).limit(pageSize);
	}

	public List<ReleasedResource> deleteContentResourcesIfUnreferenced(List<Integer> candidateResourceIds) {
		if (candidateResourceIds.isEmpty())
			return List.of();
		List<Integer> deletableResourceIds = userErasureDao.findUnreferencedContentResourceIds(candidateResourceIds);
		if (deletableResourceIds.isEmpty())
			return List.of();
		return deleteContentResourceRows(deletableResourceIds);
	}

	public List<ReleasedResource> deleteContentResourceRows(List<Integer> resourceIds) {
		if (resourceIds.isEmpty())
			return List.of();
		ContentResourceDboExample ex = new ContentResourceDboExample();
		ex.createCriteria().andContentResourceIdIn(resourceIds);
		List<ReleasedResource> released = contentResourceDao.get(ex).stream()
				.map(resource -> new ReleasedResource(resource.getContentResourceId(), resource.getStorageDir(),
						resource.getFilename()))
				.toList();
		ContentResourceDboExample deletableResourcesExample = new ContentResourceDboExample();
		deletableResourcesExample.createCriteria().andContentResourceIdIn(resourceIds);
		contentResourceDao.deleteWhere(deletableResourcesExample);
		return released;
	}
}
