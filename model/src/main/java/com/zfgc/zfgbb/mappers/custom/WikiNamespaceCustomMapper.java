package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
}
