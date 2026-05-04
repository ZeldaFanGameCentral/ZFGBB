package com.zfgc.zfgbb.migrator.smf.queries;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;

public interface SmfMessageStreamMapper {

	@Select("""
			select id_msg, id_topic, id_board, poster_time, id_member, id_msg_modified,
			       subject, poster_name, poster_email, poster_ip, smileys_enabled,
			       modified_time, modified_name, icon, approved, body, description
			from ${@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()}messages
			where id_msg > #{lastId}
			order by id_msg asc
			limit #{limit}
			""")
	@ResultMap("com.zfgc.zfgbb.migrator.smf.mappers.SMFMessageDbMapper.ResultMapWithBLOBs")
	List<SMFMessageDbWithBLOBs> selectAfterIdLimit(
			@Param("lastId") Integer lastId,
			@Param("limit") Integer limit);
}
