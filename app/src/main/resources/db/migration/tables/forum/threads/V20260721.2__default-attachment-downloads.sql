update zfgbb.file_attachments
set downloads = 0
where downloads is null;

alter table zfgbb.file_attachments
alter column downloads set default 0,
alter column downloads set not null;
