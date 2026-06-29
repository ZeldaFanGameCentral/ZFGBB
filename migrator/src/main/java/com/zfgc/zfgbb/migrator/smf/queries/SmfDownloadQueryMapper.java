package com.zfgc.zfgbb.migrator.smf.queries;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SmfDownloadQueryMapper {

	class DownloadRow {
		private Integer id;
		private Integer type;
		private String fileUrl;
		private String description;
		private Integer fileSize;
		private Integer postTime;

		public Integer getPostTime() {
			return postTime;
		}

		public void setPostTime(Integer postTime) {
			this.postTime = postTime;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public String getFileUrl() {
			return fileUrl;
		}

		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getFileSize() {
			return fileSize;
		}

		public void setFileSize(Integer fileSize) {
			this.fileSize = fileSize;
		}
	}

	@Select("""
			select fileURL from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}game_downloads
			where ID_DOWNLOAD = #{id}
			""")
	String selectFileUrl(@Param("id") Integer id);

	@Select("""
			select ID_DOWNLOAD as id, type, fileURL as fileUrl, description, fileSize, postTime
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}game_downloads
			where ID_GAME = #{gameId} order by ID_DOWNLOAD
			""")
	List<DownloadRow> selectByGame(@Param("gameId") Integer gameId);

	@Select("""
			select ID_DOWNLOAD as id, type, fileURL as fileUrl, description, fileSize, postTime
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}resource_downloads
			where ID_RESOURCE = #{resourceId} order by ID_DOWNLOAD
			""")
	List<DownloadRow> selectByResource(@Param("resourceId") Integer resourceId);

	class CuratedCollectionRow {
		private String code;
		private String title;
		private String kind;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getKind() {
			return kind;
		}

		public void setKind(String kind) {
			this.kind = kind;
		}
	}

	class CuratedItemRow {
		private String entityType;
		private Integer legacyId;

		public String getEntityType() {
			return entityType;
		}

		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}

		public Integer getLegacyId() {
			return legacyId;
		}

		public void setLegacyId(Integer legacyId) {
			this.legacyId = legacyId;
		}
	}

	@Select("""
			select count(*) from information_schema.tables
			where table_schema = database() and table_name = 'curated_collection'
			""")
	int curatedCollectionTableExists();

	class CuratedMergeRow {
		private String sourceEntityType;
		private Integer sourceLegacyId;
		private String targetEntityType;
		private Integer targetLegacyId;

		public String getSourceEntityType() {
			return sourceEntityType;
		}

		public void setSourceEntityType(String sourceEntityType) {
			this.sourceEntityType = sourceEntityType;
		}

		public Integer getSourceLegacyId() {
			return sourceLegacyId;
		}

		public void setSourceLegacyId(Integer sourceLegacyId) {
			this.sourceLegacyId = sourceLegacyId;
		}

		public String getTargetEntityType() {
			return targetEntityType;
		}

		public void setTargetEntityType(String targetEntityType) {
			this.targetEntityType = targetEntityType;
		}

		public Integer getTargetLegacyId() {
			return targetLegacyId;
		}

		public void setTargetLegacyId(Integer targetLegacyId) {
			this.targetLegacyId = targetLegacyId;
		}
	}

	@Select("""
			select count(*) from information_schema.tables
			where table_schema = database() and table_name = 'curated_project_merge'
			""")
	int curatedMergeTableExists();

	@Select("""
			select source_entity_type as sourceEntityType, source_legacy_id as sourceLegacyId,
			       target_entity_type as targetEntityType, target_legacy_id as targetLegacyId
			from curated_project_merge
			""")
	List<CuratedMergeRow> selectCuratedMerges();

	@Select("select code, title, kind from curated_collection order by code")
	List<CuratedCollectionRow> selectCuratedCollections();

	@Select("""
			select entity_type as entityType, legacy_id as legacyId from curated_collection_item
			where collection_code = #{code} order by ordinal, legacy_id
			""")
	List<CuratedItemRow> selectCuratedItems(@Param("code") String code);

	class LegacyTeamRow {
		private Integer id;
		private String title;
		private String description;
		private Integer memberId;
		private Integer time;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getMemberId() {
			return memberId;
		}

		public void setMemberId(Integer memberId) {
			this.memberId = memberId;
		}

		public Integer getTime() {
			return time;
		}

		public void setTime(Integer time) {
			this.time = time;
		}
	}

	class LegacyPairRow {
		private Integer leftId;
		private Integer rightId;

		public Integer getLeftId() {
			return leftId;
		}

		public void setLeftId(Integer leftId) {
			this.leftId = leftId;
		}

		public Integer getRightId() {
			return rightId;
		}

		public void setRightId(Integer rightId) {
			this.rightId = rightId;
		}
	}

	class LegacyTagRow {
		private Integer id;
		private String name;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	class LegacyNewsRow {
		private Integer id;
		private Integer gameId;
		private Integer memberId;
		private String subject;
		private String body;
		private Integer postTime;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getGameId() {
			return gameId;
		}

		public void setGameId(Integer gameId) {
			this.gameId = gameId;
		}

		public Integer getMemberId() {
			return memberId;
		}

		public void setMemberId(Integer memberId) {
			this.memberId = memberId;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public Integer getPostTime() {
			return postTime;
		}

		public void setPostTime(Integer postTime) {
			this.postTime = postTime;
		}
	}

	@Select("select id_team as id, title, description, id_member as memberId, time from ci_teams order by id_team")
	List<LegacyTeamRow> selectCiTeams();

	@Select("select id_team as leftId, id_member as rightId from ci_team_members order by id_team, id_member")
	List<LegacyPairRow> selectCiTeamMembers();

	@Select("select id, name from ci_tags order by id")
	List<LegacyTagRow> selectCiTags();

	@Select("select cl_from as id, cl_to as name from zfgc_wikicategorylinks order by cl_from, cl_to")
	List<LegacyTagRow> selectWikiCategoryLinks();

	@Select("""
			select id_member as id, member_name as name
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}members order by id_member
			""")
	List<LegacyTagRow> selectMemberNames();

	class WikiProjectLinkRow {
		private String wikiTitle;
		private String entityType;
		private Integer legacyId;

		public String getWikiTitle() {
			return wikiTitle;
		}

		public void setWikiTitle(String wikiTitle) {
			this.wikiTitle = wikiTitle;
		}

		public String getEntityType() {
			return entityType;
		}

		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}

		public Integer getLegacyId() {
			return legacyId;
		}

		public void setLegacyId(Integer legacyId) {
			this.legacyId = legacyId;
		}
	}

	@Select("""
			select count(*) from information_schema.tables
			where table_schema = database() and table_name = 'curated_wiki_project_link'
			""")
	int wikiProjectLinkTableExists();

	@Select("select wiki_title as wikiTitle, entity_type as entityType, legacy_id as legacyId from curated_wiki_project_link")
	List<WikiProjectLinkRow> selectWikiProjectLinks();

	class WikiRevisionRow {
		private Integer id;
		private Integer pageId;
		private Integer textId;
		private String userText;
		private String revTimestamp;
		private String comment;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getPageId() {
			return pageId;
		}

		public void setPageId(Integer pageId) {
			this.pageId = pageId;
		}

		public Integer getTextId() {
			return textId;
		}

		public void setTextId(Integer textId) {
			this.textId = textId;
		}

		public String getUserText() {
			return userText;
		}

		public void setUserText(String userText) {
			this.userText = userText;
		}

		public String getRevTimestamp() {
			return revTimestamp;
		}

		public void setRevTimestamp(String revTimestamp) {
			this.revTimestamp = revTimestamp;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}
	}

	@Select("""
			select rev_id as id, rev_page as pageId, rev_text_id as textId,
			convert(rev_user_text using utf8mb4) as userText,
			convert(rev_timestamp using utf8mb4) as revTimestamp,
			convert(rev_comment using utf8mb4) as comment
			from zfgc_wikirevision order by rev_id
			""")
	List<WikiRevisionRow> selectWikiRevisions();

	@Select("select project_id as leftId, tag_id as rightId from ci_project_tags order by project_id, tag_id")
	List<LegacyPairRow> selectCiProjectTags();

	@Select("select project_id as leftId, topic_id as rightId from ci_project_news order by project_id, topic_id")
	List<LegacyPairRow> selectCiProjectNews();

	@Select("""
			select ID_NEWS as id, ID_GAME as gameId, ID_MEMBER as memberId, subject, body, postTime
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}game_news order by ID_NEWS
			""")
	List<LegacyNewsRow> selectGameNews();
}
