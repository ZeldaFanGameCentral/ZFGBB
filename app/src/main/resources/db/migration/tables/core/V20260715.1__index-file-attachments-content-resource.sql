create index idx_file_attachments_content_resource on zfgbb.file_attachments (content_resource_id);

create index idx_wiki_page_revision_content_trgm
	on zfgbb.wiki_page_revision using gin (content gin_trgm_ops)
	where current_flag;

create index idx_content_entity_summary_trgm
	on zfgbb.content_entity using gin (summary gin_trgm_ops);
