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
}
