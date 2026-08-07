alter table zfgbb.bb_code_config
	add column honoured_in_forum_flag boolean default true,
	add column honoured_in_wiki_flag boolean default true,
	add column honoured_in_project_flag boolean default true,
	add column honoured_in_resource_flag boolean default true,
	add column honoured_in_signature_flag boolean default true;
