alter table zfgbb.bb_code_config
	add column source_reference_attribute text,
	add column source_reference_resolver text;

alter table zfgbb.bb_code_config
	add constraint ck_bb_code_config_source_reference_paired
	check ((source_reference_attribute is null) = (source_reference_resolver is null));
