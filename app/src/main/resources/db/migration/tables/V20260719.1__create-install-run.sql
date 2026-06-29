create table if not exists zfgbb.install_run (
	install_id smallint primary key check (install_id = 1),
	state varchar(24) not null,
	last_completed_state varchar(24) not null default 'READY',
	request_version integer not null default 1,
	request_fingerprint varchar(64),
	admin_user_id integer references zfgbb.user(user_id) on delete set null,
	content_pack text,
	provision_recycle_bin boolean,
	site_name text,
	last_error text,
	created_ts timestamptz not null default current_timestamp,
	updated_ts timestamptz not null default current_timestamp,
	constraint ck_install_run_state check
		(state in ('READY','CORE_READY','PACK_READY','RECYCLE_READY','INSTALLED','FAILED')),
	constraint ck_install_run_completed_state check
		(last_completed_state in ('READY','CORE_READY','PACK_READY','RECYCLE_READY','INSTALLED'))
);

insert into zfgbb.install_run (install_id, state, last_completed_state)
select 1, installed_state, installed_state from (select case when exists (
	select 1 from zfgbb.system_config where config_key = 'installed' and config_value = 'true'
) then 'INSTALLED' else 'READY' end as installed_state) seed
on conflict (install_id) do nothing;
