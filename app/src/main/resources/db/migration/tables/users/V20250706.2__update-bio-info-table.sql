alter table zfgbb.user_bio_info
add column date_registered timestamp,
add column last_login timestamp,
add column real_name text,
add column birth_date date,
add column website_title text,
add column website_url text,
add column location text,
add column time_format text,
add column karma_good integer not null default 0,
add column karma_bad integer not null default 0,
add column hide_email_flag boolean not null default false,
add column hide_online_status boolean not null default false;

create table zfgbb.user_notif_settings(
	user_id integer not null primary key references zfgbb.user,
	notify_announcements_flag boolean not null default false,
	notify_send_body_flag boolean not null default false,
	send_happy_birthday_flag boolean not null default false,
	migration_hash text,
	created_ts timestamp not null default current_timestamp,
	updated_ts timestamp not null default current_timestamp
);

alter table zfgbb.user_contact_info
add column migration_hash text;

alter table zfgbb.email_address
add column migration_hash text,
drop column user_id,
alter column email_address type text;
