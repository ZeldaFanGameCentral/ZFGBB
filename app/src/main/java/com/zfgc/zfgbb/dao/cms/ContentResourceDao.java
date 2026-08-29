package com.zfgc.zfgbb.dao.cms;

import java.util.ArrayList;
import static java.util.function.Predicate.not;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.users.PermissionGroupDao;
import com.zfgc.zfgbb.dao.cms.WikiPageDao;
import com.zfgc.zfgbb.dao.forum.FileAttachmentDao;
import com.zfgc.zfgbb.dao.users.AvatarDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;

@Repository
public class ContentResourceDao extends IdentityDao<ContentResourceDbo, ContentResourceDboExample> {

	private final PermissionGroupDao permissionGroupDao;

	private final WikiPageDao wikiPageDao;

	private final FileAttachmentDao fileAttachmentDao;

	private final AvatarDao avatarDao;

	public ContentResourceDao(ContentResourceDboMapper mapper,
			AvatarDao avatarDao,
			FileAttachmentDao fileAttachmentDao,
			WikiPageDao wikiPageDao,
			PermissionGroupDao permissionGroupDao) {
		super(mapper);
		this.avatarDao = avatarDao;
		this.fileAttachmentDao = fileAttachmentDao;
		this.wikiPageDao = wikiPageDao;
		this.permissionGroupDao = permissionGroupDao;
	}

	public List<Integer> findOwnedUnreferencedContentResourceIds(Integer userId, int limit) {
		List<Integer> unreferenced = new ArrayList<>();
		int lastSeenId = 0;
		while (unreferenced.size() < limit) {
			ContentResourceDboExample ownedPage = new ContentResourceDboExample();
			ownedPage.createCriteria().andUploadedUserIdEqualTo(userId).andContentResourceIdGreaterThan(lastSeenId);
			ownedPage.setOrderByClause("content_resource_id");
			ownedPage.setLimit(limit);
			List<Integer> pageIds = get(ownedPage).stream().map(ContentResourceDbo::getContentResourceId).toList();
			if (pageIds.isEmpty())
				break;
			lastSeenId = pageIds.get(pageIds.size() - 1);
			Set<Integer> referenced = referencedAmong(pageIds);
			for (Integer contentResourceId : pageIds)
				if (!referenced.contains(contentResourceId) && unreferenced.size() < limit)
					unreferenced.add(contentResourceId);
		}
		return unreferenced;
	}

	public List<Integer> findUnreferencedContentResourceIds(List<Integer> resourceIds) {
		return resourceIds.stream().filter(not(referencedAmong(resourceIds)::contains)).toList();
	}

	public int reassignContentResources(Integer userId, Integer sentinelId) {
		ContentResourceDbo reassigned = new ContentResourceDbo();
		reassigned.setUploadedUserId(sentinelId);
		ContentResourceDboExample uploadedByUser = new ContentResourceDboExample();
		uploadedByUser.createCriteria().andUploadedUserIdEqualTo(userId);
		return updateWhereSettingColumns(reassigned, Set.of("uploaded_user_id", "migration_hash"), uploadedByUser);
	}

	private Set<Integer> referencedAmong(List<Integer> contentResourceIds) {
		if (contentResourceIds.isEmpty())
			return Set.of();
		Set<Integer> referenced = new HashSet<>();
		referenced.addAll(avatarDao.contentResourceIdsAmong(contentResourceIds));
		referenced.addAll(fileAttachmentDao.contentResourceIdsAmong(contentResourceIds));
		referenced.addAll(wikiPageDao.contentResourceIdsAmong(contentResourceIds));
		referenced.addAll(permissionGroupDao.starImageResourceIdsAmong(contentResourceIds));
		return referenced;
	}
}
