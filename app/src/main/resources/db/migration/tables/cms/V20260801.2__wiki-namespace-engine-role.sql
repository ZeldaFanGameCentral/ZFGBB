alter table zfgbb.wiki_namespace
	add column if not exists engine_role text;

create unique index if not exists ux_wiki_namespace_engine_role
	on zfgbb.wiki_namespace(engine_role) where engine_role is not null;

update zfgbb.wiki_namespace n
set engine_role = seeded.role
from (values
	('MAIN', 'MAIN'),
	('Talk', 'TALK'),
	('User', 'USER'),
	('User_talk', 'USER_TALK'),
	('File', 'FILE'),
	('File_talk', 'FILE_TALK'),
	('MediaWiki', 'MEDIAWIKI'),
	('MediaWiki_talk', 'MEDIAWIKI_TALK'),
	('Template', 'TEMPLATE'),
	('Template_talk', 'TEMPLATE_TALK'),
	('Help', 'HELP'),
	('Help_talk', 'HELP_TALK'),
	('Category', 'CATEGORY'),
	('Category_talk', 'CATEGORY_TALK'),
	('Special', 'SPECIAL')
) as seeded(name, role)
where lower(n.name) = lower(seeded.name) and n.engine_role is null;
