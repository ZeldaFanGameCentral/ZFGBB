create or replace function zfgbb.seed_content_template(p_code text, p_format text, p_scope text, p_source text, p_body text)
returns void
language plpgsql
as $$
declare raw_code text := normalize(regexp_replace(replace(trim(p_code), '_', ' '), '\s+', ' ', 'g'), NFC);
begin

	p_code := zfgbb.canonical_template_code(raw_code);
	update zfgbb.content_template set source_code = raw_code, scope = p_scope, source = p_source, body = p_body,
		updated_ts = current_timestamp
	where code = p_code and content_format = p_format and wiki_page_id is null;
	if not found then
		insert into zfgbb.content_template(code, source_code, content_format, scope, source, body)
		values (p_code, raw_code, p_format, p_scope, p_source, p_body);
	end if;

	return;

end; $$;

select setval(
	pg_get_serial_sequence('zfgbb.content_template', 'content_template_id'),
	greatest((select coalesce(max(content_template_id), 0) from zfgbb.content_template), 1000),
	true);

select zfgbb.seed_content_template('userprofile', 'BBCODE', 'WIKI', '/user-profile/{userid}',
	'[table=infobox][tr][td][b]User Information[/b][/td][td]{{username}}[/td][/tr]{{#bioInfo.avatar.url}}[tr][td]Avatar[/td][td][img]{{bioInfo.avatar.url}}[/img][/td][/tr]{{/bioInfo.avatar.url}}{{^bioInfo.avatar.url}}{{#bioInfo.avatar.contentResourceId}}[tr][td]Avatar[/td][td][img]/content/{{bioInfo.avatar.contentResourceId}}[/img][/td][/tr]{{/bioInfo.avatar.contentResourceId}}{{/bioInfo.avatar.url}}{{#userid}}[tr][td]Profile[/td][td][member={{userid}}]Profile[/member][/td][/tr]{{/userid}}[tr][td]Age[/td][td]{{age}}[/td][/tr][tr][td]Gender[/td][td]{{gender}}[/td][/tr][tr][td]Location[/td][td]{{location}}[/td][/tr][tr][td]Website[/td][td]{{website}}[/td][/tr][tr][td]Notes[/td][td]{{notes}}[/td][/tr][/table]');
select zfgbb.seed_content_template('userprofile', 'MARKDOWN', 'WIKI', '/user-profile/{userid}',
	E'**User Information — {{username}}**\n\n{{#bioInfo.avatar.url}}![avatar]({{bioInfo.avatar.url}})\n{{/bioInfo.avatar.url}}{{^bioInfo.avatar.url}}{{#bioInfo.avatar.contentResourceId}}![avatar](/content/{{bioInfo.avatar.contentResourceId}})\n{{/bioInfo.avatar.contentResourceId}}{{/bioInfo.avatar.url}}- Age: {{age}}\n- Gender: {{gender}}\n- Location: {{location}}\n- Website: {{website}}\n- Notes: {{notes}}\n');

select zfgbb.seed_content_template('pagecount', 'BBCODE', 'ALL', '/wiki/meta/statistics', '{{totalPages}}');
select zfgbb.seed_content_template('pagecount', 'MARKDOWN', 'ALL', '/wiki/meta/statistics', '{{totalPages}}');

select zfgbb.seed_content_template('categorylist', 'BBCODE', 'WIKI', '/wiki/meta/category?name={_1}',
	E'{{#empty}}[i]No pages yet.[/i]{{/empty}}{{^empty}}[list]\n{{#pages}}[li][wiki={{slug}}]{{title}}[/wiki][/li]\n{{/pages}}[/list]{{/empty}}');
select zfgbb.seed_content_template('categorylist', 'MARKDOWN', 'WIKI', '/wiki/meta/category?name={_1}',
	E'{{#empty}}*No pages yet.*{{/empty}}{{^empty}}{{#pages}}- [{{title}}](/wiki/{{slug}})\n{{/pages}}{{/empty}}');

select zfgbb.seed_content_template('projectcard', 'BBCODE', 'ALL', '/projects/card?slug={_1}',
	E'{{#found}}[table][tr]{{#preview}}[td][img]/content/{{preview}}[/img][/td]{{/preview}}[td][b][wiki=Project:{{slug}}]{{title}}[/wiki][/b]\n{{stats}}\n{{summary}}[/td][/tr][/table]{{/found}}{{^found}}[i]No featured project configured.[/i]{{/found}}');
select zfgbb.seed_content_template('projectcard', 'MARKDOWN', 'ALL', '/projects/card?slug={_1}',
	E'{{#found}}**[{{title}}](/projects/{{slug}})**\n\n{{stats}}\n\n{{summary}}{{/found}}{{^found}}*No featured project configured.*{{/found}}');

select zfgbb.seed_content_template('featuredproject', 'BBCODE', 'ALL', '/projects/card?slug={_1}',
	'{{#found}}<div class="p-4">[grid=2]<div>{{#preview}}<a class="bb-resource-link" href="/content/projects/{{slug}}"><img class="bb-code-preview" src="/content/{{preview}}" alt=""/></a>{{/preview}}</div><div><h5 class="text-highlighted"><a class="bb-resource-link" href="/content/projects/{{slug}}">{{title}}</a></h5>{{#author}}<h6 class="text-dimmed">Developer: {{#authorUserId}}[member={{authorUserId}}]{{author}}[/member]{{/authorUserId}}{{^authorUserId}}{{author}}{{/authorUserId}}</h6>{{/author}}{{#stats}}<h6 class="text-dimmed">{{stats}}</h6>{{/stats}}<a class="bb-resource-link text-sm text-highlighted" href="/content/projects/{{slug}}">View project &rarr;</a></div>[/grid]</div>{{/found}}{{^found}}[i]No featured project configured.[/i]{{/found}}');

select zfgbb.seed_content_template('recentactivity', 'BBCODE', 'ALL', '/board/recent-activity?limit={limit}',
	'<div class="p-4 space-y-2">{{#data}}<div class="text-sm">[thread={{threadId}}]{{threadName}}[/thread]<div class="text-dimmed">in {{boardName}}{{#lastPosterId}} by [member={{lastPosterId}}]{{lastPoster}}[/member]{{/lastPosterId}} &middot; {{#formatDate}}{{lastPostTs}}{{/formatDate}}</div></div>{{/data}}{{^data}}[i]No recent activity.[/i]{{/data}}</div>');

select zfgbb.seed_content_template('announcements', 'BBCODE', 'ALL', '/board/recent-activity?boardId={boardId}&limit={limit}',
	E'<div class="p-4">{{#data}}{{#-first}}{{#template}}announcementlead\nthreadId={{threadId}}\n{{/template}}{{/-first}}{{/data}}<ul class="mt-4 border-t border-default pt-3 space-y-1">{{#data}}{{^-first}}<li class="text-sm">[thread={{threadId}}]{{threadName}}[/thread]<span class="text-dimmed"> &middot; {{#lastPosterId}}[member={{lastPosterId}}]{{lastPoster}}[/member]{{/lastPosterId}} &middot; {{#formatDate}}{{lastPostTs}}{{/formatDate}}</span></li>{{/-first}}{{/data}}</ul>{{^data}}[i]No announcements yet.[/i]{{/data}}</div>');

select zfgbb.seed_content_template('announcementlead', 'BBCODE', 'ALL', '/thread/{threadId}?page=1&pageSize=1',
	'<div><h1 class="text-4xl text-highlighted">[thread={{id}}]{{threadName}}[/thread]</h1><h2 class="text-base italic text-muted">{{#messages}}{{#-first}}{{#createdUser.id}}[member={{createdUser.id}}]{{createdUser.displayName}}[/member]{{/createdUser.id}}{{^createdUser.id}}{{createdUser.displayName}}{{/createdUser.id}} &middot; {{#formatDate}}{{createdTs}}{{/formatDate}}{{/-first}}{{/messages}}</h2><div class="mt-4">{{#messages}}{{#-first}}{{currentMessage.messageText}}{{/-first}}{{/messages}}</div></div>');

select zfgbb.seed_content_template('projectnews', 'BBCODE', 'ALL', '/projects/news?slug={_1}&limit={_2}',
	E'{{#empty}}[i]No news yet.[/i]{{/empty}}{{^empty}}[list]\n{{#items}}{{#threadId}}[li][thread={{threadId}}]{{subject}}[/thread]{{date}}[/li]\n{{/threadId}}{{^threadId}}[li][b]{{subject}}[/b]{{date}}[/li]\n{{/threadId}}{{/items}}[/list]{{/empty}}');
select zfgbb.seed_content_template('projectnews', 'MARKDOWN', 'ALL', '/projects/news?slug={_1}&limit={_2}',
	E'{{#empty}}*No news yet.*{{/empty}}{{^empty}}{{#items}}- **{{subject}}**{{date}}\n{{/items}}{{/empty}}');

do $$
declare
	game_body text := '[table=infobox][tr][td][b]Game Information[/b][/td][td]{{title}}[/td][/tr]{{#author}}[tr][td]Author[/td][td]{{author}}[/td][/tr]{{/author}}{{#status}}[tr][td]Status[/td][td]{{status}}[/td][/tr]{{/status}}{{#team}}[tr][td]Team[/td][td]{{name}}[/td][/tr]{{/team}}{{#release}}[tr][td]Date Released[/td][td]{{release}}[/td][/tr]{{/release}}{{#genre}}[tr][td]Genre[/td][td]{{genre}}[/td][/tr]{{/genre}}{{#developer}}[tr][td]Developer[/td][td]{{developer}}[/td][/tr]{{/developer}}{{#publisher}}[tr][td]Publisher[/td][td]{{publisher}}[/td][/tr]{{/publisher}}{{#ESRB}}[tr][td]ESRB Rating[/td][td]{{ESRB}}[/td][/tr]{{/ESRB}}{{#platform}}[tr][td]Platform(s)[/td][td]{{platform}}[/td][/tr]{{/platform}}{{#language}}[tr][td]Made with[/td][td]{{language}}[/td][/tr]{{/language}}{{#rating}}[tr][td]Rating[/td][td]★ {{rating}} ({{voteCount}} votes)[/td][/tr]{{/rating}}{{#downloadCount}}[tr][td]Downloads[/td][td]{{downloadCount}}[/td][/tr]{{/downloadCount}}[/table]';
	code text;
begin
	foreach code in array array['game', 'videogame', 'onlinegame'] loop
		perform zfgbb.seed_content_template(code, 'BBCODE', 'WIKI', '/projects/{pageSlug}', game_body);
	end loop;
end $$;
select zfgbb.seed_content_template('iteminfobox', 'BBCODE', 'WIKI', null,
	'[table][tr][td][b]{{title}}[/b][/td][td]{{img1}} {{cap1}}[/td][/tr][tr][td]Type[/td][td]{{type}}[/td][/tr][tr][td]Location[/td][td]{{location}}[/td][/tr][tr][td]Obtained[/td][td]{{obtained}}[/td][/tr][tr][td]Required[/td][td]{{required}}[/td][/tr][tr][td]Cost[/td][td]{{cost}}[/td][/tr][tr][td]Damage rate[/td][td]{{damagerate}}[/td][/tr][tr][td]Ammo[/td][td]{{ammo}}[/td][/tr][/table]');
select zfgbb.seed_content_template('infobox', 'BBCODE', 'WIKI', null,
	'[table][tr][td][b]{{title}}[/b][/td][td]{{image}} {{caption}}[/td][/tr][tr][td]{{label1}}[/td][td]{{data1}}[/td][/tr][tr][td]{{label2}}[/td][td]{{data2}}[/td][/tr][tr][td]{{label3}}[/td][td]{{data3}}[/td][/tr][tr][td]{{label4}}[/td][td]{{data4}}[/td][/tr][tr][td]{{label5}}[/td][td]{{data5}}[/td][/tr][/table]');
select zfgbb.seed_content_template('stub', 'BBCODE', 'WIKI', null,
	'[table][tr][td][i]This article is a stub. You can help by expanding it.[/i][/td][/tr][/table]');
select zfgbb.seed_content_template('tl', 'BBCODE', 'ALL', null, '{{=<% %>=}}{{[wiki=Template:<%_1%>]<%_1%>[/wiki]}}');
