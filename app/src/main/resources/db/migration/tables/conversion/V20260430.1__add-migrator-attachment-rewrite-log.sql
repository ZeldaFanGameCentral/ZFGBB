create table zfgbb.migrator_attachment_ref_rewrites (
	message_history_id integer not null primary key references zfgbb.message_history(message_history_id),
	rewritten_ts timestamp not null default current_timestamp
);
