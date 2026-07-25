create table backup_job (
	backup_id uuid primary key,
	state varchar(16) not null,
	revision bigint not null default 0,
	creator_user_id integer,
	created_ts timestamp with time zone not null,
	updated_ts timestamp with time zone not null,
	expires_ts timestamp with time zone not null,
	archive_bytes bigint,
	archive_sha256 char(64),
	generation_id varchar(128),
	installer_compatible boolean,
	installer_anchor_administrator_id integer,
	last_error varchar(255),
	constraint backup_job_state_chk check (
		state in ('CREATING', 'READY', 'DOWNLOADING', 'CONSUMED', 'EXPIRED', 'FAILED')
	),
	constraint backup_job_revision_chk check (revision >= 0),
	constraint backup_job_archive_bytes_chk check (
		archive_bytes is null or archive_bytes >= 0
	)
);

create index backup_job_state_expires_idx on backup_job (state, expires_ts);
