insert into zfgbb.system_config (config_key, config_value)
select 'recycle_board_id', b.board_id::text
from zfgbb.board b
where b.board_name = 'Deleted Posts'
	and not exists (select 1 from zfgbb.system_config where config_key = 'recycle_board_id')
order by b.board_id
limit 1;

insert into zfgbb.board (board_name, description, category_id, seqno)
select 'Recycle Bin', 'Removed posts awaiting permanent deletion.',
	(select category_id from zfgbb.category order by category_order, category_id limit 1),
	coalesce((select max(seqno) + 1 from zfgbb.board), 1)
where not exists (select 1 from zfgbb.system_config where config_key = 'recycle_board_id')
	and not exists (select 1 from zfgbb.board where board_name = 'Recycle Bin');

insert into zfgbb.system_config (config_key, config_value)
select 'recycle_board_id', b.board_id::text
from zfgbb.board b
where b.board_name = 'Recycle Bin'
	and not exists (select 1 from zfgbb.system_config where config_key = 'recycle_board_id')
order by b.board_id
limit 1;

delete from zfgbb.br_board_permission bp
using zfgbb.system_config c
where c.config_key = 'recycle_board_id'
	and bp.board_id = c.config_value::integer
	and bp.permission_id not in (
		select permission_id from zfgbb.permission
		where permission_code in ('ZFGC_SITE_ADMIN', 'ZFGC_SITE_MODERATOR'));

insert into zfgbb.br_board_permission (board_id, permission_id)
select c.config_value::integer, p.permission_id
from zfgbb.system_config c
join zfgbb.permission p
	on p.permission_code in ('ZFGC_SITE_ADMIN', 'ZFGC_SITE_MODERATOR')
where c.config_key = 'recycle_board_id'
	and not exists (
		select 1 from zfgbb.br_board_permission bp
		where bp.board_id = c.config_value::integer
			and bp.permission_id = p.permission_id);
