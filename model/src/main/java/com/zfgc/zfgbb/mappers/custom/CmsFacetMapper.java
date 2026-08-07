package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import lombok.Getter;
import lombok.Setter;

public interface CmsFacetMapper {

	@Getter
	@Setter
	class FacetCount {
		private String value;
		private long count;
	}

	@Select("""
			select resource_type as value, count(*) as count
			from zfgbb.resource_view
			where resource_type is not null
			group by resource_type
			order by count desc, value asc
			""")
	List<FacetCount> countResourceTypes();

	@Select("""
			select language as value, count(*) as count
			from zfgbb.project_view
			where language is not null
			group by language
			order by count desc, value asc
			""")
	List<FacetCount> countProjectLanguages();

	@Select("""
			select status as value, count(*) as count
			from zfgbb.project_view
			where status is not null
			group by status
			order by count desc, value asc
			""")
	List<FacetCount> countProjectStatuses();
}
