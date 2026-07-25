package com.zfgc.zfgbb.migrator.smf.queries;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import lombok.Getter;
import lombok.Setter;

public interface SmfDownloadQueryMapper {

	@Getter
	@Setter
	class DownloadRow {
		private Integer id;
		private Integer type;
		private String fileUrl;
		private String description;
		private Integer fileSize;
		private Integer postTime;
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

	@Getter
	@Setter
	class CuratedCollectionRow {
		private String code;
		private String title;
		private String kind;
	}

	@Getter
	@Setter
	class CuratedItemRow {
		private String entityType;
		private Integer legacyId;
	}

	@Select("""
			select count(*) from information_schema.tables
			where table_schema = database() and table_name = 'curated_collection'
			""")
	int curatedCollectionTableExists();

	@Getter
	@Setter
	class CuratedMergeRow {
		private String sourceEntityType;
		private Integer sourceLegacyId;
		private String targetEntityType;
		private Integer targetLegacyId;
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

	@Getter
	@Setter
	class LegacyTeamRow {
		private Integer id;
		private String title;
		private String description;
		private Integer memberId;
		private Integer time;
	}

	@Getter
	@Setter
	class LegacyPairRow {
		private Integer leftId;
		private Integer rightId;
	}

	@Getter
	@Setter
	class LegacyTagRow {
		private Integer id;
		private String name;
	}

	@Getter
	@Setter
	class LegacyNewsRow {
		private Integer id;
		private Integer gameId;
		private Integer memberId;
		private String subject;
		private String body;
		private Integer postTime;
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

	@Getter
	@Setter
	class WikiProjectLinkRow {
		private String wikiTitle;
		private String entityType;
		private Integer legacyId;
	}

	@Select("""
			select count(*) from information_schema.tables
			where table_schema = database() and table_name = 'curated_wiki_project_link'
			""")
	int wikiProjectLinkTableExists();

	@Select("select wiki_title as wikiTitle, entity_type as entityType, legacy_id as legacyId from curated_wiki_project_link")
	List<WikiProjectLinkRow> selectWikiProjectLinks();

	@Getter
	@Setter
	class WikiRevisionRow {
		private Integer id;
		private Integer pageId;
		private Integer textId;
		private String userText;
		private String revTimestamp;
		private String comment;
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
