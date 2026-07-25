delete from zfgbb.bb_code_config where code in ('left', 'center', 'right', 'iurl', 'ftp');

do $$
declare
	surviving int;
begin
	select count(*) into surviving
	from zfgbb.bb_code_attribute_mode
	where bb_code_config_id not in (select bb_code_config_id from zfgbb.bb_code_config);
	if surviving > 0 then
		raise exception 'deleting a bb_code_config left % attribute mode rows behind, so the cascade this '
			'migration relies on is not in force', surviving;
	end if;
end $$;
