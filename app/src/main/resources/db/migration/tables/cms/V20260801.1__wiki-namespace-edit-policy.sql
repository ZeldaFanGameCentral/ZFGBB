alter table zfgbb.wiki_namespace
	add column if not exists system_managed boolean not null default false;
alter table zfgbb.wiki_namespace
	add column if not exists edit_permission_code text;

update zfgbb.wiki_namespace
set system_managed = true
where name in ('Special', 'Project', 'Resource');

update zfgbb.wiki_namespace
set edit_permission_code = 'ZFGC_SITE_ADMIN'
where name = 'MediaWiki';

update zfgbb.wiki_namespace
set edit_permission_code = 'ZFGC_WIKI_MODERATOR'
where name in ('Site', 'Template');
