alter table zfgbb.user_refresh_token
	add column revoked_ts timestamptz;

update zfgbb.user_refresh_token
set revoked_ts = coalesce(updated_ts, issued_ts)
where revoked_flag and rotated_ts is null;
