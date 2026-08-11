do $seed$
declare
	page_id integer;
	legacy_home_body text := $home$[notoc][grid=2][widget=Featured Project][template=featuredproject][/template][/widget][widget=Recent Activity][template=recentactivity]
limit=5
[/template][/widget][/grid]
[widget=Announcements][template=announcements]
boardId=
limit=3
[/template][/widget]
Welcome! Edit this page (Site:Home) in the wiki to customize your site's home page.$home$;
	home_body text := $home$[notoc][grid=2][widget=Featured Project][template=Template:Featuredproject][/template][/widget][widget=Recent Activity][template=recentactivity]
limit=5
[/template][/widget][/grid]
[widget=Announcements][template=announcements]
boardId=
limit=3
[/template][/widget]
Welcome! Edit this page (Site:Home) in the wiki to customize your site's home page.$home$;
begin
	perform setval(
		pg_get_serial_sequence('zfgbb.wiki_page', 'wiki_page_id'),
		greatest((select coalesce(max(wiki_page_id), 0) from zfgbb.wiki_page), 1000),
		true);
	perform setval(
		pg_get_serial_sequence('zfgbb.wiki_page_revision', 'wiki_page_revision_id'),
		greatest((select coalesce(max(wiki_page_revision_id), 0) from zfgbb.wiki_page_revision), 1000),
		true);

	select wiki_page_id into page_id
	from zfgbb.wiki_page
	where namespace = 'Site' and slug = 'Site:Home';

	if page_id is not null then
		update zfgbb.wiki_page_revision
		set content = home_body,
			content_size = length(home_body)
		where wiki_page_id = page_id
			and current_flag = true
			and content = legacy_home_body;
		return;
	end if;

	insert into zfgbb.wiki_page (namespace, title, slug)
	values ('Site', 'Home', 'Site:Home')
	returning wiki_page_id into page_id;

	insert into zfgbb.wiki_page_revision
		(wiki_page_id, content, content_format, summary, current_flag, status, author_name, content_size)
	values
		(page_id, home_body, 'BBCODE', 'Initial Site:Home', true, 'APPROVED', 'ZFGBB', length(home_body));
end $seed$;
