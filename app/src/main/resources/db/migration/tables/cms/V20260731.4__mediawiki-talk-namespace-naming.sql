update zfgbb.wiki_page
set namespace = regexp_replace(namespace, 'Talk$', '_talk')
where namespace ~ '.Talk$';

update zfgbb.wiki_namespace
set name = regexp_replace(name, 'Talk$', '_talk')
where name ~ '.Talk$';

insert into zfgbb.wiki_namespace(name) values ('Help') on conflict do nothing;
