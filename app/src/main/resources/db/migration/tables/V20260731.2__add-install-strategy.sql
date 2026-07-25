alter table zfgbb.install_run add column if not exists install_strategy varchar(16);

alter table zfgbb.install_run drop constraint if exists ck_install_run_strategy;
alter table zfgbb.install_run add constraint ck_install_run_strategy check
	(install_strategy is null or install_strategy in ('SEED', 'ARCHIVE'));
