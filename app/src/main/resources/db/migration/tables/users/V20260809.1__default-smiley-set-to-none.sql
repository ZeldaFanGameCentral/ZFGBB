update zfgbb.user_settings set smiley_set = 'NONE' where smiley_set is null;

alter table zfgbb.user_settings alter column smiley_set set default 'NONE';
alter table zfgbb.user_settings alter column smiley_set set not null;
