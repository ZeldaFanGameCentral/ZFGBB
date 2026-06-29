alter table zfgbb.user
add column locked_until_ts     timestamptz,
add column failed_login_count  integer not null default 0,
add column password_changed_ts timestamptz;
