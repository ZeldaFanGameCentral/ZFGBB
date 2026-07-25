create table if not exists zfgbb.wiki_import_namespace (
	source_namespace_id integer primary key,
	namespace_name text not null,
	created_ts timestamptz not null default now(),
	updated_ts timestamptz not null default now()
);
create unique index if not exists ux_wiki_import_namespace_name_ci
	on zfgbb.wiki_import_namespace(lower(namespace_name));

insert into zfgbb.wiki_import_namespace(source_namespace_id, namespace_name) values
	(0, 'MAIN'),
	(1, 'Talk'),
	(2, 'User'),
	(3, 'User_talk'),
	(4, 'Meta'),
	(5, 'Meta_talk'),
	(6, 'File'),
	(7, 'File_talk'),
	(8, 'MediaWiki'),
	(9, 'MediaWiki_talk'),
	(10, 'Template'),
	(11, 'Template_talk'),
	(12, 'Help'),
	(13, 'Help_talk'),
	(14, 'Category'),
	(15, 'Category_talk')
on conflict (source_namespace_id) do nothing;
