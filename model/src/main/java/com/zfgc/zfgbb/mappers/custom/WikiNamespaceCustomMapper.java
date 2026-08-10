package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import lombok.Getter;
import lombok.Setter;

public interface WikiNamespaceCustomMapper {

	@Getter
	@Setter
	public static class NamespaceRecord {
		private String name;
		private String caseMode;
	}

	@Select("""
			select distinct n.name, n.case_mode
			  from zfgbb.wiki_namespace n
			  left join zfgbb.wiki_namespace_alias a on a.namespace_name=n.name
			 where lower(n.name)=lower(#{prefix}) or lower(a.alias)=lower(#{prefix})
			""")
	@Results({
			@Result(property = "name", column = "name"),
			@Result(property = "caseMode", column = "case_mode")
	})
	List<NamespaceRecord> resolveNamespace(@Param("prefix") String prefix);

	@Select("select case_mode from zfgbb.wiki_namespace where lower(name) = 'main'")
	List<String> findMainCaseMode();

	@Getter
	@Setter
	public static class NamespacePageCount {
		private String namespace;
		private long pageCount;
		private long redirectCount;
	}

	@Select("""
			<script>
			select p.namespace as namespace, count(*) as pageCount,
			       count(*) filter (where p.redirect_to is not null) as redirectCount
			  from zfgbb.wiki_page p
			 where (p.redirect_to is not null
			        or exists (select 1 from zfgbb.wiki_revision_ref r
			                    where r.wiki_page_id = p.wiki_page_id and r.current_flag))
			<if test="hiddenNamespaces != null and !hiddenNamespaces.isEmpty()">
			   and p.namespace not in
			   <foreach item="namespace" collection="hiddenNamespaces" open="(" separator="," close=")">
			   #{namespace}
			   </foreach>
			</if>
			 group by p.namespace
			</script>
			""")
	@Results({
			@Result(property = "namespace", column = "namespace"),
			@Result(property = "pageCount", column = "pageCount"),
			@Result(property = "redirectCount", column = "redirectCount")
	})
	List<NamespacePageCount> countVisiblePagesByNamespace(
			@Param("hiddenNamespaces") List<String> hiddenNamespaces);

	@Select("select count(distinct category_name) from zfgbb.wiki_page_category")
	long countDistinctCategories();

	@Select("select case_mode from zfgbb.wiki_namespace where lower(name)=lower(#{namespace})")
	List<String> findCaseModeByName(@Param("namespace") String namespace);

	@Getter
	@Setter
	public static class EditPolicyRecord {
		private String name;
		private boolean systemManaged;
		private String editPermissionCode;
	}

	@Select("""
			select name, system_managed, edit_permission_code
			  from zfgbb.wiki_namespace
			 where lower(name)=lower(#{namespace})
			""")
	@Results({
			@Result(property = "name", column = "name"),
			@Result(property = "systemManaged", column = "system_managed"),
			@Result(property = "editPermissionCode", column = "edit_permission_code")
	})
	List<EditPolicyRecord> findEditPolicyByName(@Param("namespace") String namespace);

	@Update(
			"update zfgbb.wiki_namespace set engine_role = #{role} where lower(name)=lower(#{name}) and engine_role is null")
	int assignEngineRole(@Param("name") String name, @Param("role") String role);

	@Update("update zfgbb.wiki_namespace set case_mode = #{caseMode} where name = #{name}")
	int updateCaseMode(@Param("name") String name, @Param("caseMode") String caseMode);

	@Insert("insert into zfgbb.wiki_namespace (name, case_mode) values (#{name}, #{caseMode})")
	int insertNamespace(@Param("name") String name, @Param("caseMode") String caseMode);

	@Insert("insert into zfgbb.wiki_namespace_alias (alias, namespace_name) values (#{alias}, #{namespaceName})")
	int insertAlias(@Param("alias") String alias, @Param("namespaceName") String namespaceName);

	@Select("select count(*) from zfgbb.wiki_namespace_alias where namespace_name = #{namespaceName}")
	int countAliasesByNamespace(@Param("namespaceName") String namespaceName);

	@Delete("delete from zfgbb.wiki_namespace where name = #{name}")
	int deleteNamespaceByName(@Param("name") String name);

	@Getter
	@Setter
	public static class ImportNamespaceRecord {
		private Integer sourceNamespaceId;
		private String namespaceName;
	}

	@Select("""
			select source_namespace_id, namespace_name
			  from zfgbb.wiki_import_namespace
			 order by source_namespace_id
			""")
	@Results({
			@Result(property = "sourceNamespaceId", column = "source_namespace_id"),
			@Result(property = "namespaceName", column = "namespace_name")
	})
	List<ImportNamespaceRecord> listImportNamespaces();

	@Insert("""
			insert into zfgbb.wiki_import_namespace (source_namespace_id, namespace_name)
			values (#{sourceNamespaceId}, #{namespaceName})
			on conflict (source_namespace_id)
			do update set namespace_name = excluded.namespace_name, updated_ts = now()
			""")
	int upsertImportNamespace(@Param("sourceNamespaceId") Integer sourceNamespaceId,
			@Param("namespaceName") String namespaceName);

	@Delete(
			"delete from zfgbb.wiki_import_namespace where source_namespace_id = #{sourceNamespaceId}")
	int deleteImportNamespace(@Param("sourceNamespaceId") Integer sourceNamespaceId);
}
