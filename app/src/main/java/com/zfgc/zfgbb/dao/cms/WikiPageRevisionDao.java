package com.zfgc.zfgbb.dao.cms;

import java.util.Set;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;

@Repository
public class WikiPageRevisionDao extends IdentityDao<WikiPageRevisionDbo, WikiPageRevisionDboExample> {

	private static final String DELETED_NAME = "[deleted]";

	public WikiPageRevisionDao(WikiPageRevisionDboMapper mapper) {
		super(mapper);
	}

	public int scrubRetainedWikiRevisions(Integer userId) {
		WikiPageRevisionDbo scrubbed = new WikiPageRevisionDbo();
		scrubbed.setAuthorName(DELETED_NAME);
		WikiPageRevisionDboExample authoredByUser = new WikiPageRevisionDboExample();
		authoredByUser.createCriteria().andAuthorUserIdEqualTo(userId);
		return updateWhereSettingColumns(scrubbed, Set.of("author_user_id", "author_name"), authoredByUser);
	}
}
