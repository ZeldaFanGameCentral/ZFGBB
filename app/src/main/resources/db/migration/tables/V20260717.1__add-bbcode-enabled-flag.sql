alter table zfgbb.bb_code_config add column enabled_flag boolean not null default true;

update zfgbb.bb_code_config set enabled_flag = false where code = 'you';
