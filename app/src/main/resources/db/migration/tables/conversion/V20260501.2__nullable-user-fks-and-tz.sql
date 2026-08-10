alter table zfgbb.thread alter column created_user_id drop not null;
alter table zfgbb.message alter column owner_id drop not null;
alter table zfgbb.karma alter column commenting_user_id drop not null;
alter table zfgbb.poll alter column created_user_id drop not null;

alter table zfgbb.user_bio_info add column preferred_timezone varchar(64);
