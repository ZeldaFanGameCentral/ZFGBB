alter table zfgbb.install_run drop constraint ck_install_run_state;
alter table zfgbb.install_run add constraint ck_install_run_state check
	(state in ('READY','CORE_READY','PACK_READY','ASSETS_READY','RECYCLE_READY','INSTALLED','FAILED'));

alter table zfgbb.install_run drop constraint ck_install_run_completed_state;
alter table zfgbb.install_run add constraint ck_install_run_completed_state check
	(last_completed_state in ('READY','CORE_READY','PACK_READY','ASSETS_READY','RECYCLE_READY','INSTALLED'));
