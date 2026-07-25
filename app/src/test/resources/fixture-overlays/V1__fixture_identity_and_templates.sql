update zfgbb."user"
set display_name = 'MG-Zero'
where user_name = 'mgzero'
	and display_name = 'mgzero';

insert into zfgbb.content_template (
	content_template_id, code, content_format, scope, source, body, wiki_page_id,
	created_ts, updated_ts, source_code)
select
	p.wiki_page_id, p.title, r.content_format, 'WIKI', null, r.content, p.wiki_page_id,
	r.created_ts, r.updated_ts, null
from zfgbb.wiki_page p
join zfgbb.wiki_page_revision r
	on r.wiki_page_id = p.wiki_page_id and r.current_flag
where p.namespace = 'Template'
	and p.title in ('Tasks', 'FeaturedProject', 'KOT:News')
on conflict do nothing;

delete from zfgbb.content_template
where wiki_page_id is null
	and lower(code) in ('zfgcproject', 'zfgc');

insert into zfgbb.content_template (
	content_template_id, code, content_format, scope, source, body, source_code)
values
	(1025, 'Zfgcproject', 'BBCODE', 'ALL', null, '', 'zfgcproject'),
	(1026, 'Zfgc', 'BBCODE', 'ALL', null, '[url=https://zfgc.com]ZFGC[/url]', 'zfgc');

select setval(
	pg_get_serial_sequence('zfgbb.content_template', 'content_template_id'),
	greatest((select coalesce(max(content_template_id), 0) from zfgbb.content_template), 1),
	true);
