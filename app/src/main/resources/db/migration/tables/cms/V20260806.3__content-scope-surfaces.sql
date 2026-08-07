insert into zfgbb.content_template_scope (code, label, ordinal) values
	('PROJECT', 'Project', 4),
	('RESOURCE', 'Resource', 5),
	('SIGNATURE', 'Signature', 6)
on conflict (code) do nothing;

do $$
declare
	unrepresented text;
begin
	select string_agg(surface, ', ') into unrepresented
	from unnest(array['ALL', 'WIKI', 'FORUM', 'PROJECT', 'RESOURCE', 'SIGNATURE']) as surface
	where surface not in (select code from zfgbb.content_template_scope);
	if unrepresented is not null then
		raise exception 'ContentScope declares % which the scope lookup does not, so a template row could '
			'never store it and parseScope would read it back as the ALL wildcard', unrepresented;
	end if;
end $$;
