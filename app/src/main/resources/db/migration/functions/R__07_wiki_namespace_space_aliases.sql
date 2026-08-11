create or replace function zfgbb.sync_wiki_namespace_space_aliases() returns integer
language plpgsql as $$
declare inserted integer;
begin
	insert into zfgbb.wiki_namespace_alias(alias, namespace_name)
	select replace(n.name, '_', ' '), n.name
	  from zfgbb.wiki_namespace n
	 where n.name like '%\_%'
	   and not exists (select 1 from zfgbb.wiki_namespace clash
	                    where lower(clash.name) = lower(replace(n.name, '_', ' ')))
	   and not exists (select 1 from zfgbb.wiki_namespace_alias held
	                    where lower(held.alias) = lower(replace(n.name, '_', ' ')))
	on conflict do nothing;
	get diagnostics inserted = row_count;
	return inserted;
end $$;

select zfgbb.sync_wiki_namespace_space_aliases();
