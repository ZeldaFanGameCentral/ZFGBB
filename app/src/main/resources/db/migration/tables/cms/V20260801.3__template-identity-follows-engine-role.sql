create or replace function zfgbb.canonical_template_code(p_code text)
returns text language sql stable strict as $$
	select case when coalesce((select case_mode from zfgbb.wiki_namespace where engine_role = 'TEMPLATE'),
			'FIRST_LETTER') = 'CASE_SENSITIVE'
		then normalize(regexp_replace(replace(trim(p_code), '_', ' '), '\s+', ' ', 'g'), NFC)
		else upper(left(normalize(regexp_replace(replace(trim(p_code), '_', ' '), '\s+', ' ', 'g'), NFC), 1)) ||
			substring(normalize(regexp_replace(replace(trim(p_code), '_', ' '), '\s+', ' ', 'g'), NFC) from 2) end
$$;

create or replace function zfgbb.guard_wiki_namespace_change() returns trigger language plpgsql as $$
declare page_conflicts text;
begin
	if tg_op = 'UPDATE' and (old.name is distinct from new.name or old.case_mode is distinct from new.case_mode)
		and exists (select 1 from zfgbb.wiki_page where lower(namespace) = lower(old.name)) then
		if old.engine_role is distinct from 'TEMPLATE' or old.name is distinct from new.name then
			raise exception 'Cannot change namespace name/case mode while pages exist in namespace %', old.name;
		end if;
	end if;
	if tg_op = 'UPDATE' and old.engine_role = 'TEMPLATE' and old.case_mode is distinct from new.case_mode then
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
			from zfgbb.wiki_page where lower(namespace) = lower(new.name)
			group by zfgbb.wiki_title_key(namespace,title,new.case_mode) having count(*) > 1
		) collisions;
		if page_conflicts is not null then
			raise exception 'Cannot change Template case mode; page title collisions: %', page_conflicts;
		end if;
		update zfgbb.wiki_page set canonical_title_key = zfgbb.wiki_title_key(namespace,title,new.case_mode)
		where lower(namespace) = lower(new.name);
	end if;
	if exists (select 1 from zfgbb.wiki_namespace_alias a where lower(a.alias) = lower(new.name)) then
		raise exception 'Wiki namespace name conflicts with alias case-insensitively: %', new.name;
	end if;
	return new;
end $$;
