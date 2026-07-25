delete from zfgbb.br_user_permission duplicate_grant
where duplicate_grant.br_user_permission_id > (
	select min(surviving_grant.br_user_permission_id)
	from zfgbb.br_user_permission surviving_grant
	where surviving_grant.user_id = duplicate_grant.user_id
		and surviving_grant.user_permission_id = duplicate_grant.user_permission_id
);

create unique index if not exists ux_br_user_permission_user_id_user_permission_id
	on zfgbb.br_user_permission (user_id, user_permission_id);
