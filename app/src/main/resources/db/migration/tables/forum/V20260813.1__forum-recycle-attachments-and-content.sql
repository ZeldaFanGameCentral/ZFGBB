alter table zfgbb.bb_code_config add column enabled_flag boolean not null default true;

alter table zfgbb.message add column board_id integer;

alter table zfgbb.message
	add constraint fk_message_board_id foreign key (board_id) references zfgbb.board (board_id) not valid;

update zfgbb.message
set board_id = thread.board_id
from zfgbb.thread
where thread.thread_id = message.thread_id
  and message.board_id is null;

create index idx_message_board_id_created_ts on zfgbb.message (board_id, created_ts desc);

alter table zfgbb.message_history alter column ip_address_id drop not null;

alter table zfgbb.thread add column recycled_from_board_id integer;
alter table zfgbb.thread add column recycled_from_thread_id integer;

alter table zfgbb.thread
	add constraint fk_thread_recycled_from_board_id
	foreign key (recycled_from_board_id) references zfgbb.board (board_id) on delete set null;

alter table zfgbb.thread
	add constraint fk_thread_recycled_from_thread_id
	foreign key (recycled_from_thread_id) references zfgbb.thread (thread_id) on delete set null;

create index idx_thread_recycled_from_thread_id
	on zfgbb.thread (recycled_from_thread_id)
	where recycled_from_thread_id is not null;

create unique index ux_message_history_current
	on zfgbb.message_history (message_id)
	where current_flag;

alter table zfgbb.file_attachments
alter column downloads set default 0,
alter column downloads set not null;

create table zfgbb.content_format (
	code text not null primary key,
	label text not null,
	ordinal integer not null default 0
);

insert into zfgbb.content_format (code, label, ordinal) values
	('BBCODE', 'BBCode', 1),
	('MARKDOWN', 'Markdown', 2);

alter table zfgbb.message_history
	add column content_format text not null default 'BBCODE'
		references zfgbb.content_format(code);

alter table zfgbb.content_resource
	add column storage_dir text;

create index idx_file_attachments_content_resource on zfgbb.file_attachments (content_resource_id);

drop table zfgbb.karma;
alter table zfgbb.user_bio_info drop column karma_good;
alter table zfgbb.user_bio_info drop column karma_bad;

create extension if not exists pg_trgm with schema public;

create index idx_thread_name_trgm
	on zfgbb.thread using gin (thread_name gin_trgm_ops);

create index idx_message_text_trgm
	on zfgbb.message_history using gin (message_text gin_trgm_ops)
	where current_flag;
