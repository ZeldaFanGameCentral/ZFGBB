create table if not exists zfgbb.wiki_namespace (
	name text primary key,
	case_mode text not null default 'FIRST_LETTER' check (case_mode in ('FIRST_LETTER', 'CASE_SENSITIVE')),
	unique (name)
);
create unique index if not exists ux_wiki_namespace_name_ci on zfgbb.wiki_namespace(lower(name));
insert into zfgbb.wiki_namespace(name) values
	('MAIN'), ('Template'), ('Category'), ('File'), ('User'), ('Talk'),
	('Special'), ('MediaWiki'), ('Project'), ('Resource'), ('Site')
on conflict do nothing;

create table if not exists zfgbb.wiki_namespace_alias (
	alias text primary key,
	namespace_name text not null references zfgbb.wiki_namespace(name) on update cascade on delete cascade
);
create unique index if not exists ux_wiki_namespace_alias_ci on zfgbb.wiki_namespace_alias(lower(alias));
do $$ begin
	if exists (select 1 from information_schema.columns where table_schema='zfgbb'
		and table_name='wiki_namespace' and column_name='alias') then
		execute 'insert into zfgbb.wiki_namespace_alias(alias, namespace_name) '
			|| 'select alias, name from zfgbb.wiki_namespace where alias is not null on conflict do nothing';
		execute 'alter table zfgbb.wiki_namespace drop column alias';
	end if;
end $$;
insert into zfgbb.wiki_namespace_alias(alias, namespace_name) values ('Image', 'File') on conflict do nothing;

create table if not exists zfgbb.wiki_system_template_page (
	wiki_page_id integer primary key references zfgbb.wiki_page on delete cascade,
	code text not null,
	source_code text
);
alter table zfgbb.wiki_system_template_page add column if not exists source_code text;
alter table zfgbb.content_template add column if not exists source_code text;
update zfgbb.content_template set source_code = normalize(regexp_replace(replace(trim(code), '_', ' '), '\s+', ' ', 'g'), NFC)
where wiki_page_id is null and source_code is null;
create or replace function zfgbb.canonical_template_code(p_code text)
returns text language sql stable strict as $$
	select case when coalesce((select case_mode from zfgbb.wiki_namespace where lower(name)='template'), 'FIRST_LETTER') = 'CASE_SENSITIVE'
		then normalize(regexp_replace(replace(trim(p_code), '_', ' '), '\s+', ' ', 'g'), NFC)
		else upper(left(normalize(regexp_replace(replace(trim(p_code), '_', ' '), '\s+', ' ', 'g'), NFC), 1)) ||
			substring(normalize(regexp_replace(replace(trim(p_code), '_', ' '), '\s+', ' ', 'g'), NFC) from 2) end
$$;

alter table zfgbb.content_template
	drop constraint if exists content_template_code_content_format_key;

do $$ declare synthetic record;
begin
	for synthetic in
		select ct.content_template_id, p.wiki_page_id
		from zfgbb.content_template ct join zfgbb.wiki_page p on p.wiki_page_id = ct.wiki_page_id
		where p.namespace = 'Template' and p.created_user_id is null and p.migration_hash is null
			and exists (select 1 from zfgbb.wiki_page_revision r where r.wiki_page_id = p.wiki_page_id)
			and exists (select 1 from zfgbb.wiki_page_revision r where r.wiki_page_id = p.wiki_page_id
				and r.current_flag and r.content = ct.body)
			and not exists (select 1 from zfgbb.wiki_page_revision r where r.wiki_page_id = p.wiki_page_id
				and (r.author_name is distinct from 'ZFGBB' or r.summary is distinct from 'Sync template body'
					or r.migration_hash is not null))
	loop
		insert into zfgbb.wiki_system_template_page(wiki_page_id, code, source_code)
		select synthetic.wiki_page_id, zfgbb.canonical_template_code(coalesce(source_code, code)),
			normalize(regexp_replace(replace(trim(coalesce(source_code, code)), '_', ' '), '\s+', ' ', 'g'), NFC)
		from zfgbb.content_template where content_template_id = synthetic.content_template_id
		on conflict (wiki_page_id) do update set code = excluded.code, source_code = excluded.source_code;
		update zfgbb.content_template set wiki_page_id = null where content_template_id = synthetic.content_template_id;
	end loop;
end $$;

update zfgbb.content_template set source_code = normalize(regexp_replace(replace(trim(code), '_', ' '), '\s+', ' ', 'g'), NFC)
where wiki_page_id is null and source_code is null;
update zfgbb.wiki_system_template_page marker set source_code = coalesce(marker.source_code, ct.source_code)
from zfgbb.content_template ct where ct.wiki_page_id is null and ct.code = marker.code and marker.source_code is null;

update zfgbb.content_template
set code = zfgbb.canonical_template_code(code)
where wiki_page_id is null;

insert into zfgbb.migration_conflict(entity_type, entity_id, field_name, candidates)
select 'CONTENT_TEMPLATE', min(content_template_id), 'wiki_page_format_identity',
	string_agg(content_template_id::text, ' | ' order by content_template_id)
from zfgbb.content_template where wiki_page_id is not null
group by wiki_page_id, content_format having count(*) > 1
on conflict (entity_type, entity_id, field_name) do update
set candidates = excluded.candidates, status = 'OPEN', resolved_source_type = null,
	resolved_value = null, resolved_by_user_id = null, resolved_ts = null, updated_ts = current_timestamp;

insert into zfgbb.migration_conflict(entity_type, entity_id, field_name, candidates)
select 'CONTENT_TEMPLATE', min(content_template_id), 'system_code_format_identity',
	string_agg(content_template_id::text, ' | ' order by content_template_id)
from zfgbb.content_template where wiki_page_id is null
group by code, content_format having count(*) > 1
on conflict (entity_type, entity_id, field_name) do update
set candidates = excluded.candidates, status = 'OPEN', resolved_source_type = null,
	resolved_value = null, resolved_by_user_id = null, resolved_ts = null, updated_ts = current_timestamp;

do $$ declare details text;
begin
	select string_agg(candidates, '; ' order by migration_conflict_id) into details
	from zfgbb.migration_conflict where entity_type = 'CONTENT_TEMPLATE'
		and field_name in ('wiki_page_format_identity', 'system_code_format_identity') and status = 'OPEN';
	if details is not null then
		raise exception 'Cannot establish content-template identity; resolve duplicate row ids: %', details;
	end if;
end $$;

create unique index if not exists ux_content_template_wiki_page_format
	on zfgbb.content_template (wiki_page_id, content_format) where wiki_page_id is not null;
create unique index if not exists ux_content_template_system_code_format
	on zfgbb.content_template (code, content_format) where wiki_page_id is null;

create or replace function zfgbb.wiki_title_key(p_namespace text, p_title text, p_case_mode text default 'FIRST_LETTER')
returns text language sql immutable strict as $$
	select lower(coalesce(nullif(trim(p_namespace), ''), 'MAIN')) || ':' ||
		case when p_case_mode = 'CASE_SENSITIVE' then '' else
		upper(left(normalize(regexp_replace(replace(trim(p_title), '_', ' '), '\s+', ' ', 'g'), NFC), 1)) end ||
		case when p_case_mode = 'CASE_SENSITIVE' then
		normalize(regexp_replace(replace(trim(p_title), '_', ' '), '\s+', ' ', 'g'), NFC) else
		substring(normalize(regexp_replace(replace(trim(p_title), '_', ' '), '\s+', ' ', 'g'), NFC) from 2)
		end
$$;

insert into zfgbb.migration_conflict(entity_type, entity_id, field_name, candidates)
select 'WIKI_PAGE', min(wiki_page_id), 'canonical_title',
	string_agg(slug, ' | ' order by wiki_page_id)
from zfgbb.wiki_page
group by zfgbb.wiki_title_key(namespace, title, coalesce((select case_mode from zfgbb.wiki_namespace n
	where lower(n.name) = lower(wiki_page.namespace)), 'FIRST_LETTER'))
having count(*) > 1
on conflict (entity_type, entity_id, field_name) do update
set candidates = excluded.candidates, status = 'OPEN', resolved_source_type = null,
	resolved_value = null, resolved_by_user_id = null, resolved_ts = null, updated_ts = current_timestamp;

alter table zfgbb.wiki_page add column if not exists canonical_title_key text;

update zfgbb.wiki_page wp
set canonical_title_key = zfgbb.wiki_title_key(wp.namespace, wp.title, coalesce((select case_mode
	from zfgbb.wiki_namespace n where lower(n.name) = lower(wp.namespace)), 'FIRST_LETTER'))
where not exists (
	select 1 from zfgbb.wiki_page other
	where other.wiki_page_id <> wp.wiki_page_id
		and zfgbb.wiki_title_key(other.namespace, other.title, coalesce((select case_mode from zfgbb.wiki_namespace n
			where lower(n.name) = lower(other.namespace)), 'FIRST_LETTER')) =
			zfgbb.wiki_title_key(wp.namespace, wp.title, coalesce((select case_mode from zfgbb.wiki_namespace n
			where lower(n.name) = lower(wp.namespace)), 'FIRST_LETTER')));

create unique index if not exists ux_wiki_page_mediawiki_title
	on zfgbb.wiki_page (canonical_title_key) where canonical_title_key is not null;

create or replace function zfgbb.set_wiki_canonical_title_key()
returns trigger language plpgsql as $$
declare mode text; candidate text;
begin
	select case_mode into mode from zfgbb.wiki_namespace where lower(name) = lower(new.namespace);
	candidate := zfgbb.wiki_title_key(new.namespace, new.title, coalesce(mode, 'FIRST_LETTER'));
	if exists (select 1 from zfgbb.wiki_page p
			where p.wiki_page_id <> coalesce(new.wiki_page_id, -1)
				and lower(p.namespace) = lower(new.namespace)
				and zfgbb.wiki_title_key(p.namespace, p.title, coalesce(mode, 'FIRST_LETTER')) = candidate) then
		if tg_op = 'UPDATE' and old.canonical_title_key is null then
			new.canonical_title_key := null; -- preserve a recorded legacy conflict
			return new;
		end if;
		raise exception 'Wiki title conflicts with an existing canonical title: %', candidate;
	end if;
	new.canonical_title_key := candidate;
	return new;
end $$;

drop trigger if exists wiki_page_canonical_title_key on zfgbb.wiki_page;
create trigger wiki_page_canonical_title_key before insert or update of namespace, title
	on zfgbb.wiki_page for each row execute function zfgbb.set_wiki_canonical_title_key();

create or replace function zfgbb.guard_wiki_namespace_change()
returns trigger language plpgsql as $$
declare page_conflicts text;
begin
	if tg_op = 'UPDATE' and (old.case_mode is distinct from new.case_mode or old.name is distinct from new.name)
		and exists (select 1 from zfgbb.wiki_page where lower(namespace) = lower(old.name)) then
		if lower(old.name) <> 'template' or old.name is distinct from new.name then
			raise exception 'Cannot change namespace name/case mode while pages exist in namespace %', old.name;
		end if;
	end if;
	if tg_op = 'UPDATE' and lower(old.name)='template' and old.case_mode is distinct from new.case_mode then
		if exists (select 1 from (select case when new.case_mode='CASE_SENSITIVE' then source_code
			else upper(left(source_code,1)) || substring(source_code from 2) end code, content_format
			from zfgbb.content_template where wiki_page_id is null) x group by code, content_format having count(*) > 1) then
			raise exception 'Cannot change Template case mode: system template codes would collide';
		end if;
		update zfgbb.content_template set code = case when new.case_mode='CASE_SENSITIVE' then source_code
			else upper(left(source_code,1)) || substring(source_code from 2) end where wiki_page_id is null;
		update zfgbb.wiki_system_template_page set code = case when new.case_mode='CASE_SENSITIVE' then source_code
			else upper(left(source_code,1)) || substring(source_code from 2) end;
		select string_agg(ids, '; ' order by candidate) into page_conflicts from (
			select zfgbb.wiki_title_key(namespace,title,new.case_mode) candidate,
				string_agg(wiki_page_id::text || ':' || slug, ', ' order by wiki_page_id) ids
			from zfgbb.wiki_page where lower(namespace)='template'
			group by zfgbb.wiki_title_key(namespace,title,new.case_mode) having count(*) > 1
		) collisions;
		if page_conflicts is not null then
			raise exception 'Cannot change Template case mode; page title collisions: %', page_conflicts;
		end if;
		update zfgbb.wiki_page set canonical_title_key = zfgbb.wiki_title_key(namespace,title,new.case_mode)
		where lower(namespace)='template';
	end if;
	if exists (select 1 from zfgbb.wiki_namespace_alias a where lower(a.alias) = lower(new.name)) then
		raise exception 'Wiki namespace name conflicts with alias case-insensitively: %', new.name;
	end if;
	return new;
end $$;
drop trigger if exists wiki_namespace_change_guard on zfgbb.wiki_namespace;
create trigger wiki_namespace_change_guard before insert or update on zfgbb.wiki_namespace
	for each row execute function zfgbb.guard_wiki_namespace_change();

create or replace function zfgbb.guard_wiki_namespace_alias()
returns trigger language plpgsql as $$ begin
	if exists (select 1 from zfgbb.wiki_namespace n where lower(n.name)=lower(new.alias)) then
		raise exception 'Wiki namespace alias conflicts with namespace name case-insensitively: %', new.alias;
	end if;
	return new;
end $$;
drop trigger if exists wiki_namespace_alias_guard on zfgbb.wiki_namespace_alias;
create trigger wiki_namespace_alias_guard before insert or update on zfgbb.wiki_namespace_alias
	for each row execute function zfgbb.guard_wiki_namespace_alias();

create or replace function zfgbb.clear_wiki_system_template_marker()
returns trigger language plpgsql as $$ begin
	if tg_table_name = 'content_template' then
		if new.wiki_page_id is not null then
			delete from zfgbb.wiki_system_template_page where wiki_page_id = new.wiki_page_id;
		end if;
	else
		delete from zfgbb.wiki_system_template_page where wiki_page_id = new.wiki_page_id;
	end if;
	return new;
end $$;
drop trigger if exists content_template_clear_system_marker on zfgbb.content_template;
create trigger content_template_clear_system_marker after insert or update of wiki_page_id
	on zfgbb.content_template for each row execute function zfgbb.clear_wiki_system_template_marker();
drop trigger if exists wiki_page_clear_system_marker on zfgbb.wiki_page;
create trigger wiki_page_clear_system_marker after update of namespace, title
	on zfgbb.wiki_page for each row execute function zfgbb.clear_wiki_system_template_marker();

update zfgbb.content_template ct
set code = wp.title
from zfgbb.wiki_page wp
where wp.wiki_page_id = ct.wiki_page_id and ct.code <> wp.title;
