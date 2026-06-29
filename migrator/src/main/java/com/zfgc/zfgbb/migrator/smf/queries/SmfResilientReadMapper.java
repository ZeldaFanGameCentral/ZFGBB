package com.zfgc.zfgbb.migrator.smf.queries;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFBoardDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogKarmaDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembergroupDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollsDb;

public interface SmfResilientReadMapper {

	@Select("""
			select * from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}boards
			""")
	@ResultMap("com.zfgc.zfgbb.migrator.smf.mappers.SMFBoardDbMapper.BaseResultMap")
	List<SMFBoardDb> selectAllBoards();

	@Select("""
			select * from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}members
			""")
	@ResultMap("com.zfgc.zfgbb.migrator.smf.mappers.SMFMembersDbMapper.ResultMapWithBLOBs")
	List<SMFMembersDbWithBLOBs> selectAllMembers();

	@Select("""
			select * from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}polls
			""")
	@ResultMap("com.zfgc.zfgbb.migrator.smf.mappers.SMFPollsDbMapper.BaseResultMap")
	List<SMFPollsDb> selectAllPolls();

	@Select("""
			select * from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}membergroups
			""")
	@ResultMap("com.zfgc.zfgbb.migrator.smf.mappers.SMFMembergroupDbMapper.ResultMapWithBLOBs")
	List<SMFMembergroupDb> selectAllMembergroups();

	@Select("""
			select * from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}log_karma
			""")
	@ResultMap("com.zfgc.zfgbb.migrator.smf.mappers.SMFLogKarmaDbMapper.ResultMapWithBLOBs")
	List<SMFLogKarmaDbWithBLOBs> selectAllKarma();

	@Select("""
			select * from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}messages_history
			""")
	@ResultMap("com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageHistoryDbMapper.ResultMapWithBLOBs")
	List<SMFMessageHistoryDb> selectAllMessageHistory();

	@Select("""
			select count(*) from information_schema.tables
			where table_schema = database()
			  and table_name = '${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}messages_history'
			""")
	int messagesHistoryTableExists();
}
