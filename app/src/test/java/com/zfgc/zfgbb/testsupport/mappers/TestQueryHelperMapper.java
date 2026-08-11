package com.zfgc.zfgbb.testsupport.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestQueryHelperMapper {
	@Select("""
			select member.user_name from zfgbb."user" member
			where member.user_id > 0 and member.user_id <> #{excludedUserId}
				and member.user_name ~ '^[A-Za-z0-9_]{3,32}$'
				and lower(member.user_name) <> '__deleted__'
			order by member.user_id limit 1
			""")
	String findUsableUserNameOtherThan(@Param("excludedUserId") int excludedUserId);

	@Select("""
			select address.email_address from zfgbb.email_address address
			join zfgbb.user_contact_info contact
				on contact.email_address_id = address.email_address_id
			where contact.user_id > 0 and contact.user_id <> #{excludedUserId}
				and address.email_address like '%_@_%._%'
				and position(' ' in address.email_address) = 0
			order by contact.user_id limit 1
			""")
	String findEmailAddressHeldByAnother(@Param("excludedUserId") int excludedUserId);

	@Select("""
			select member.user_name from zfgbb."user" member
			where member.user_id > #{minimumUserId} and member.user_id <> #{excludedUserId}
				and member.user_name ~ '^[A-Za-z0-9_]{3,32}$'
			order by (select count(*) from zfgbb.message post
				where post.owner_id = member.user_id) desc, member.user_id
			limit 1
			""")
	String findMostProlificMember(@Param("minimumUserId") int minimumUserId,
			@Param("excludedUserId") int excludedUserId);

	@Select("""
			select member.user_id from zfgbb."user" member
			join zfgbb.user_contact_info contact on contact.user_id = member.user_id
			where member.user_id > 0 and member.user_id <> #{excludedUserId}
				and member.user_name ~ '^[A-Za-z0-9_]{3,32}$'
			order by member.user_id
			""")
	List<Integer> findMembersHoldingAnEmailAddressOtherThan(@Param("excludedUserId") int excludedUserId);

	@Insert("""
			insert into zfgbb.br_board_permission (board_id, permission_id)
			values (#{boardId}, #{permissionId}) on conflict do nothing
			""")
	int grantBoardPermissionIfAbsent(@Param("boardId") int boardId, @Param("permissionId") int permissionId);

	@Select("select 1 from (select pg_sleep(#{seconds})) slept")
	Integer sleepSeconds(@Param("seconds") int seconds);

	@Select("""
			select c.table_name
			  from information_schema.columns c
			  join information_schema.tables t
			    on t.table_schema = c.table_schema and t.table_name = c.table_name
			 where c.table_schema = 'zfgbb'
			   and c.column_name = 'updated_ts'
			   and t.table_type = 'BASE TABLE'
			   and not exists (select 1 from pg_trigger g
			                    join pg_class r on r.oid = g.tgrelid
			                    join pg_namespace n on n.oid = r.relnamespace
			                   where n.nspname = 'zfgbb'
			                     and r.relname = c.table_name
			                     and g.tgname = 'touch_updated_ts')
			 order by c.table_name
			""")
	List<String> findTablesMissingUpdatedTsTrigger();
}
