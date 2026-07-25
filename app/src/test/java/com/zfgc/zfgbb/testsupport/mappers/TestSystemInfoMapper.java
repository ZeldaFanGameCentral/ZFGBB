package com.zfgc.zfgbb.testsupport.mappers;

import com.zfgc.zfgbb.testsupport.RawSqlIdentifiers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface TestSystemInfoMapper {

	@Select("select pg_get_userbyid(datdba) from pg_database where datname = current_database()")
	String getDatabaseOwner();

	@Select("select schema_owner from information_schema.schemata where schema_name = 'zfgbb'")
	String getSchemaOwner();

	@Select("select (rolsuper or rolcreatedb or rolcreaterole) from pg_roles where rolname = current_user")
	Boolean isCurrentUserSuperuser();

	@Select("select pg_has_role(current_user, 'zfgcadmin', 'member')")
	Boolean hasZfgcAdminRole();

	@Select("""
			select bool_or(admin_option)
			from pg_auth_members membership
			join pg_roles granted_role on granted_role.oid = membership.roleid
			join pg_roles member_role on member_role.oid = membership.member
			where granted_role.rolname = 'zfgcadmin'
				and member_role.rolname = current_user
			""")
	Boolean isZfgcAdminOptionGranted();

	@Select("select has_database_privilege(current_user, current_database(), 'connect,create,temp')")
	Boolean hasDatabasePrivileges();

	@Select("""
			select c.conrelid::regclass::text as referrer_table, a.attname as referrer_column
			from pg_constraint c
			join pg_attribute a on a.attrelid = c.conrelid and a.attnum = any(c.conkey)
			where c.contype = 'f' and c.confrelid = 'zfgbb."user"'::regclass
			""")
	List<Map<String, Object>> listUserReferrerColumns();

	@Select("select setval(pg_get_serial_sequence(#{tableName}, #{columnName}), (select coalesce(max(${columnName}), 1) from ${tableName}))")
	@RawSqlIdentifiers("a sequence reset needs the table and column as identifiers, which cannot be bound")
	Long resetSequence(@Param("tableName") String tableName, @Param("columnName") String columnName);

	@Select("""
			select con.conname
			from pg_constraint con
			where con.contype = 'f'
				and con.confdeltype in ('a', 'r')
				and con.confrelid in (
					'zfgbb."user"'::regclass,
					'zfgbb.thread'::regclass,
					'zfgbb.message'::regclass,
					'zfgbb.message_history'::regclass,
					'zfgbb.content_resource'::regclass,
					'zfgbb.email_address'::regclass,
					'zfgbb.avatar'::regclass,
					'zfgbb.wiki_page'::regclass,
					'zfgbb.content_entity'::regclass
				)
			order by con.conname
			""")
	List<String> listRestrictReferrersOfCensusTargets();

	@Select("select current_setting('application_name')")
	String getApplicationName();

	@Select("select pg_get_functiondef('zfgbb.touch_updated_ts()'::regprocedure)")
	String getTouchUpdatedTsDefinition();

	@Select("""
			select count(*) from pg_locks
			where locktype = 'advisory' and granted and mode = 'ExclusiveLock'
				and classid = #{classId} and objid = #{objId} and objsubid = 1
				and database = (select oid from pg_database where datname = current_database())
			""")
	long countAdvisoryExclusiveLocks(@Param("classId") long classId,
			@Param("objId") long objId);

	@Select("select count(*) from zfgbb.flyway_schema_history")
	long countFlywaySchemaHistoryRows();

	@Select("select count(*) from pg_indexes where schemaname = #{schemaName} and indexname = #{indexName}")
	long countIndexes(@Param("schemaName") String schemaName,
			@Param("indexName") String indexName);

	@Select("""
			select count(*) from pg_indexes
			where schemaname = #{schemaName} and tablename = #{tableName} and indexname = #{indexName}
			""")
	long countIndexesOnTable(@Param("schemaName") String schemaName,
			@Param("tableName") String tableName,
			@Param("indexName") String indexName);

	@Select("select count(*) from ${referrerTable} where ${referrerColumn} = #{userId}")
	@RawSqlIdentifiers("table and column names come from the live pg_constraint FK census, not from any input")
	long countRowsReferencingUser(@Param("referrerTable") String referrerTable,
			@Param("referrerColumn") String referrerColumn,
			@Param("userId") int userId);

	@Select("select zfgbb.wiki_title_key(#{namespace}, #{title}, #{caseMode})")
	String wikiTitleKey(@Param("namespace") String namespace, @Param("title") String title, @Param("caseMode") String caseMode);

	@Select("""
			select column_name
			from information_schema.columns
			where table_schema = 'zfgbb' and table_name = #{viewName}
			order by ordinal_position
			""")
	List<String> getViewColumns(@Param("viewName") String viewName);
}
