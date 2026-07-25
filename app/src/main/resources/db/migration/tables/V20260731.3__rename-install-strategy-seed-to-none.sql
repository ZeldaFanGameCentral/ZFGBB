alter table zfgbb.install_run drop constraint if exists ck_install_run_strategy;

update zfgbb.install_run set install_strategy='NONE' where install_strategy='SEED';

alter table zfgbb.install_run add constraint ck_install_run_strategy check
	(install_strategy is null or install_strategy in ('NONE', 'ARCHIVE'));
