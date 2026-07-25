package com.zfgc.zfgbb.testsupport.mappers;

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

	@Select("""
			insert into zfgbb.content_entity
				(content_entity_id, entity_type, title, slug, summary, author_name)
			values
				(1, 'PROJECT', 'Ocarina of Time', 'ocarina-of-time',
					'Integration fixture project', 'Test Author'),
				(2, 'PROJECT', 'Majora''s Mask 3D', 'majora-s-mask-3d',
					'Integration fixture project', 'Test Author'),
				(3, 'RESOURCE', 'Eiji Aonuma Zelda Credits List',
					'eiji-aonuma-zelda-credits-list',
					'Integration fixture resource', 'Test Author')
			on conflict (content_entity_id) do update
				set entity_type = excluded.entity_type,
					title = excluded.title,
					slug = excluded.slug,
					summary = excluded.summary,
					author_name = excluded.author_name
			""")
	void ensureDefaultContentEntities();

	@Select("""
			insert into zfgbb.project (content_entity_id, status, progress)
			values (1, 'COMPLETE', 100), (2, 'WIP', 50)
			on conflict (content_entity_id) do update
				set status = excluded.status, progress = excluded.progress
			""")
	void ensureDefaultProjects();

	@Select("""
			insert into zfgbb.resource (content_entity_id, resource_type)
			values (3, 'OTHER')
			on conflict (content_entity_id) do update
				set resource_type = excluded.resource_type
			""")
	void ensureDefaultResources();

	@Select("select setval(pg_get_serial_sequence('zfgbb.category', 'category_id'), greatest((select max(category_id) from zfgbb.category), 1), true)")
	Long resetCategorySequence();

	@Select("select setval(pg_get_serial_sequence('zfgbb.board', 'board_id'), greatest((select max(board_id) from zfgbb.board), 2), true)")
	Long resetBoardSequence();

	@Select("select setval(pg_get_serial_sequence('zfgbb.content_entity', 'content_entity_id'), greatest((select max(content_entity_id) from zfgbb.content_entity), 3), true)")
	Long resetContentEntitySequence();

	@Select("select max(tag_id) from zfgbb.tag")
	Integer findMaxTagId();

	@Select("insert into zfgbb.tag(name) values(#{name}) returning tag_id")
	Integer insertTagReturningId(@Param("name") String name);
}
