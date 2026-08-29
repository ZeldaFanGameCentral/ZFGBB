package com.zfgc.zfgbb.testsupport.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestFixtureSetupMapper {

	@Select("""
			insert into zfgbb.category (category_id, category_name, description, category_order)
			values (1, 'General', 'Integration test forum', 1)
			on conflict (category_id) do update
				set category_name = excluded.category_name,
					description = excluded.description,
					category_order = excluded.category_order
			""")
	void ensureDefaultCategory();

	@Select("""
			insert into zfgbb.board
				(board_id, board_name, description, category_id, seqno)
			values (1, 'General Discussion', 'Integration test board', 1, 1)
			on conflict (board_id) do update
				set board_name = excluded.board_name,
					description = excluded.description,
					category_id = excluded.category_id,
					seqno = excluded.seqno
			""")
	void ensureGeneralBoard();

	@Select("""
			insert into zfgbb.board
				(board_id, board_name, description, category_id, seqno)
			values (2, 'Recycle Bin', 'Removed posts awaiting permanent deletion.', 1, 2)
			on conflict (board_id) do update
				set board_name = excluded.board_name,
					description = excluded.description,
					category_id = excluded.category_id,
					seqno = excluded.seqno
			""")
	void ensureRecycleBoard();

	@Select("delete from zfgbb.br_board_permission where board_id in (1, 2)")
	void resetBoardPermissions();

	@Select("""
			insert into zfgbb.br_board_permission (board_id, permission_id)
			select 1, permission_id
			from zfgbb.permission
			where permission_code in ('ZFGC_USER', 'ZFGC_GUEST')
			""")
	void grantGeneralBoardPermissions();

	@Select("""
			insert into zfgbb.br_board_permission (board_id, permission_id)
			select 2, permission_id
			from zfgbb.permission
			where permission_code in ('ZFGC_SITE_ADMIN', 'ZFGC_SITE_MODERATOR')
			""")
	void grantRecycleBoardPermissions();

	@Select("""
			insert into zfgbb.system_config (config_key, config_value)
			values ('recycle_board_id', '2')
			on conflict (config_key) do update set config_value = excluded.config_value
			""")
	void setRecycleBoardConfig();

	@Insert("""
			insert into zfgbb.br_board_permission (board_id, permission_id)
			values (#{boardId}, #{permissionId}) on conflict do nothing
			""")
	int grantBoardPermissionIfAbsent(@Param("boardId") int boardId, @Param("permissionId") int permissionId);

	@Select("select setval(pg_get_serial_sequence('zfgbb.category', 'category_id'), greatest((select max(category_id) from zfgbb.category), 1), true)")
	Long resetCategorySequence();

	@Select("select setval(pg_get_serial_sequence('zfgbb.board', 'board_id'), greatest((select max(board_id) from zfgbb.board), 2), true)")
	Long resetBoardSequence();
}
