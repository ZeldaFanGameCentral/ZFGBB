alter table zfgbb.bb_code_config
add column if not exists self_closing_flag boolean not null default false;
