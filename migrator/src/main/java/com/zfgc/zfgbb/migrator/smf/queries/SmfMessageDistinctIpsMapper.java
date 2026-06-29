package com.zfgc.zfgbb.migrator.smf.queries;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface SmfMessageDistinctIpsMapper {

	@Select("""
			select distinct poster_ip
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}messages
			where poster_ip is not null and poster_ip != ''
			""")
	List<String> selectDistinctPosterIps();

	@Select("""
			select count(*)
			from information_schema.tables
			where table_schema = database()
			  and table_name = '${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}game_comments'
			""")
	int gameCommentsTableExists();

	@Select("""
			select distinct postIP
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}game_comments
			where postIP is not null and postIP != ''
			""")
	List<String> selectDistinctGameCommentIps();

	@Select("""
			select count(*)
			from information_schema.tables
			where table_schema = database()
			  and table_name = '${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}resource_comments'
			""")
	int resourceCommentsTableExists();

	@Select("""
			select distinct postIP
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}resource_comments
			where postIP is not null and postIP != ''
			""")
	List<String> selectDistinctResourceCommentIps();
}
