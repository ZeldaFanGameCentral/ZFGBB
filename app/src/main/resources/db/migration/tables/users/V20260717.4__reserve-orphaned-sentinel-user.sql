do $$
declare
	duplicate_user record;
begin
	for duplicate_user in
		select ranked.user_id, ranked.sso_key
		from (
			select u.user_id, u.sso_key,
				row_number() over (partition by u.sso_key order by u.user_id) as position_in_group
			from zfgbb."user" u
		) ranked
		where ranked.position_in_group > 1
	loop
		update zfgbb."user"
		set sso_key = left(duplicate_user.sso_key, 64 - 7 - length(duplicate_user.user_id::text)) || '__dup__' || duplicate_user.user_id
		where user_id = duplicate_user.user_id;
		raise notice 'duplicate sso_key % rewritten for user_id % pending operator follow-up', duplicate_user.sso_key, duplicate_user.user_id;
	end loop;
end $$;

create unique index if not exists ux_user_sso_key on zfgbb."user" (sso_key);

insert into zfgbb."user" (user_id, sso_key, user_name, display_name, active_flag, failed_login_count)
select 0, '__deleted__', '__deleted__', '[deleted]', false, 0
where not exists (
	select 1 from zfgbb."user" where user_id = 0 or sso_key = '__deleted__'
)
on conflict do nothing;
