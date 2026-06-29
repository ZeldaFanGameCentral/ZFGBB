package com.zfgc.zfgbb.mappers.custom;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ProjectMergeMapper {

	@Delete("""
			delete from zfgbb.reaction s where s.reactable_type = 'PROJECT' and s.reactable_id = #{sourceId}
			and s.reactor_user_id is not null and exists (select 1 from zfgbb.reaction t
			where t.reactable_type = 'PROJECT' and t.reactable_id = #{targetId} and t.reactor_user_id = s.reactor_user_id)
			""")
	int deleteDuplicateProjectReactions(@Param("sourceId") Integer sourceId, @Param("targetId") Integer targetId);

	@Update("""
			update zfgbb.project_tag s set content_entity_id = #{targetId} where content_entity_id = #{sourceId}
			and not exists (select 1 from zfgbb.project_tag t
			where t.content_entity_id = #{targetId} and t.tag_id = s.tag_id)
			""")
	int repointProjectTags(@Param("targetId") Integer targetId, @Param("sourceId") Integer sourceId);
}
