create table user_bio_info (
	user_id integer not null primary key references zfgbb.user on delete cascade,
	custom_title text,
	personal_text text,
	created_ts timestamptz not null default current_timestamp,
	updated_ts timestamptz not null default current_timestamp
);