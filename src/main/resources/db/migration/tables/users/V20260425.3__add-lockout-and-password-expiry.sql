alter table zfgbb.user
add column locked_until_ts     timestamp without time zone,
add column failed_login_count  integer not null default 0,
add column password_changed_ts timestamp without time zone;
