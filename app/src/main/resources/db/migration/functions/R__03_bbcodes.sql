drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean);
drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean, boolean);
create or replace function zfgbb.create_bbcode_config(p_bbcode_config_id int, p_bbcode text, p_end_tag text, p_process_content boolean, p_self_closing boolean default false, p_enabled boolean default true)
returns void
language plpgsql
as $$

begin

	insert into zfgbb.bb_code_config(bb_code_config_id, code, end_tag, process_content_flag, self_closing_flag, enabled_flag)
	values(p_bbcode_config_id, p_bbcode, p_end_tag, p_process_content, p_self_closing, p_enabled)
	on conflict (bb_code_config_id)
	do update set code = p_bbcode, end_tag = p_end_tag, process_content_flag = p_process_content, self_closing_flag = p_self_closing, updated_ts = current_timestamp;

	return;

end; $$;

create or replace function zfgbb.create_bbcode_attr_mode(p_attr_mode_id int, p_bbcode text, p_open_tag text, p_close_tag text, p_content_attr_flag boolean, p_output_content_flag boolean)
returns void
language plpgsql
as $$
declare
	bbcode_id int;
begin
	bbcode_id := (select bb_code_config_id 
				 from zfgbb.bb_code_config
	             where code = p_bbcode);
	            
	insert into zfgbb.bb_code_attribute_mode(bb_code_attribute_mode_id, bb_code_config_id, content_is_attribute_flag, open_tag, close_tag, output_content_flag)
	values(p_attr_mode_id, bbcode_id, p_content_attr_flag, p_open_tag, p_close_tag, p_output_content_flag)
	on conflict (bb_code_attribute_mode_id)
	do update set bb_code_config_id = bbcode_id, 
				  content_is_attribute_flag = p_content_attr_flag, 
				  open_tag = p_open_tag, 
				  close_tag = p_close_tag, 
				  output_content_flag = p_output_content_flag, 
				  updated_ts = current_timestamp;
				  
	return;

end; $$;

create or replace function zfgbb.create_bbcode_attr(p_bb_code_attr_id int, p_attr_index int, p_bb_code_mode_id int, p_attr_name text, p_attr_type int)
returns void
language plpgsql
as $$
begin
	
	insert into zfgbb.bb_code_attribute(bb_code_attribute_id, attribute_index, bb_code_attribute_mode_id, name, attribute_data_type)
	values(p_bb_code_attr_id, p_attr_index, p_bb_code_mode_id, p_attr_name, p_attr_type)
	on conflict (bb_code_attribute_id)
	do update set attribute_index = p_attr_index, 
				  bb_code_attribute_mode_id = p_bb_code_mode_id, 
				  name = p_attr_name,
				  attribute_data_type = p_attr_type,
				  updated_ts = current_timestamp;
	
end; $$;

--base configurations
select zfgbb.create_bbcode_config(1, 'b', '</span>', true);
select zfgbb.create_bbcode_config(2, 'u', '</span>', true);
select zfgbb.create_bbcode_config(3, 'i', '</span>', true);
select zfgbb.create_bbcode_config(4, 's', '</span>', true);
select zfgbb.create_bbcode_config(5, 'pre', '</pre>', false);
select zfgbb.create_bbcode_config(6, 'left', '</div>', true);
select zfgbb.create_bbcode_config(7, 'center', '</div>', true);
select zfgbb.create_bbcode_config(8, 'right', '</div>', true);
select zfgbb.create_bbcode_config(9, 'youtube', '</span>', false);
select zfgbb.create_bbcode_config(10, 'spoiler', '</span>', true);
select zfgbb.create_bbcode_config(11, 'img', '</span>', false);
select zfgbb.create_bbcode_config(12, 'url', '</a></span>', true);
select zfgbb.create_bbcode_config(13, 'email', '</span>', false);
select zfgbb.create_bbcode_config(14, 'ftp', '</span>', false);
select zfgbb.create_bbcode_config(15, 'move', '</marquee>', true);
select zfgbb.create_bbcode_config(16, 'black', '</span>', true);
select zfgbb.create_bbcode_config(17, 'blue', '</span>', true);
select zfgbb.create_bbcode_config(18, 'quote', '</div></div>', true);
select zfgbb.create_bbcode_config(19, 'code', '</pre>', false);
select zfgbb.create_bbcode_config(20, 'color', '</span>', true);
select zfgbb.create_bbcode_config(21, 'green', '</span>', true);
select zfgbb.create_bbcode_config(22, 'list', '</ul></span>', true);
select zfgbb.create_bbcode_config(23, 'li', '</li>', true);
select zfgbb.create_bbcode_config(24, 'size', '</span>', true);

--attribute modes
--add at least one attribute mode for each bbcode
--an attribute mode configures which set of attributes will function for a bbcode
--most will probably just have one mode that doesn't take the content as a passive attribute
--id, bbcode, opentag, closetag, content attr flag, output content flag
select zfgbb.create_bbcode_attr_mode(1,'b','<span class="bb-code-b">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(2,'u','<span class="bb-code-u">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(3,'i','<span class="bb-code-i">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(63,'s','<span class="bb-code-s">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(4,'pre','<pre>','</pre>',false,false);
select zfgbb.create_bbcode_attr_mode(5,'left','<div class="bb-code-left">','</div>',false,false);
select zfgbb.create_bbcode_attr_mode(6,'center','<div class="bb-code-center">','</div>',false,false);
select zfgbb.create_bbcode_attr_mode(7,'right','<div class="bb-code-right">','</div>',false,false);

select zfgbb.create_bbcode_attr_mode(8,'youtube','<div class="bb-code-youtube"><iframe width="640" height="480" src="https://www.youtube.com/embed/{{c}}" frameborder="0" allow="encrypted-media" allowfullscreen> ','</iframe></div>',true,false);
select zfgbb.create_bbcode_attr(1, 0, 8, 'NAMELESS', 5);
select zfgbb.create_bbcode_attr_mode(84,'youtube','<div class="bb-code-youtube"><iframe width="640" height="480" src="https://www.youtube.com/embed/{{c}}" frameborder="0" allow="encrypted-media" allowfullscreen> ','</iframe></div>',true,false);

select zfgbb.create_bbcode_attr_mode(9,'spoiler','<span class="bb-code-spoiler">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(10,'img','<span class="bb-code-img"><img src="{{c}}"/>','</span>',true,false);
select zfgbb.create_bbcode_attr_mode(88,'img','<span class="bb-code-img"><img src="{{c}}" width="{{0}}" height="{{1}}"/>','</span>',true,false);
select zfgbb.create_bbcode_attr(42, 0, 88, 'width', 3);
select zfgbb.create_bbcode_attr(43, 1, 88, 'height', 3);
select zfgbb.create_bbcode_attr_mode(89,'img','<span class="bb-code-img"><img src="{{c}}" width="{{0}}"/>','</span>',true,false);
select zfgbb.create_bbcode_attr(44, 0, 89, 'width', 3);
select zfgbb.create_bbcode_attr_mode(90,'img','<span class="bb-code-img"><img src="{{c}}" height="{{0}}"/>','</span>',true,false);
select zfgbb.create_bbcode_attr(45, 0, 90, 'height', 3);
select zfgbb.create_bbcode_attr_mode(11,'url','<span class="bb-code-url"><a href="{{0}}">','</a></span>',false,true);
select zfgbb.create_bbcode_attr(7, 0, 11, 'NAMELESS', 4);
select zfgbb.create_bbcode_attr_mode(85,'url','<span class="bb-code-url"><a href="{{c}}">','</a></span>',true,true);

select zfgbb.create_bbcode_attr_mode(12,'email','<span class="bb-code-email"><a href="mailto:{{0}}">','</a></span>',false,false);
select zfgbb.create_bbcode_attr(3, 0, 12, 'NAMELESS', 1);
select zfgbb.create_bbcode_attr_mode(86,'email','<span class="bb-code-email"><a href="mailto:{{c}}">','</a></span>',true,true);

select zfgbb.create_bbcode_attr_mode(13,'ftp','<span class="bb-code-ftp"><a href="{{0}}">','</a></span>',false,false);
select zfgbb.create_bbcode_attr_mode(14,'move','<marquee>','</marquee>',false,false);
select zfgbb.create_bbcode_attr_mode(15,'black','<span style="color:black">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(16,'blue','<span style="color:blue">','</span>',false,false);


select zfgbb.create_bbcode_attr_mode(17,'quote','<div class="bb-code-quote"><div class="bb-code-quote-body">','</div></div>',false,false);

select zfgbb.create_bbcode_attr_mode(18,'quote','<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{0}},</div><div class="bb-code-quote-body">','</div></div>',false,false);
select zfgbb.create_bbcode_attr(2, 0, 18, 'author', 1);

select zfgbb.create_bbcode_attr_mode(19,'quote','<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{0}} on {{2}}</div><div class="bb-code-quote-body">','</div></div>',false,false);
select zfgbb.create_bbcode_attr(4, 0, 19, 'author', 1);
select zfgbb.create_bbcode_attr(5, 1, 19, 'link', 4);
select zfgbb.create_bbcode_attr(6, 2, 19, 'date', 0);

select zfgbb.create_bbcode_attr_mode(20,'color','<span style="color:{{0}}">','</span>',false,false);
select zfgbb.create_bbcode_attr(36, 0, 20, 'NAMELESS', 2);
select zfgbb.create_bbcode_attr_mode(21,'green','<span style="color:green">','</span>',false,false);

select zfgbb.create_bbcode_attr_mode(22, 'list','<span><ul>','</ul></span>', false, false);

select zfgbb.create_bbcode_attr_mode(23, 'li','<li>','</li>', false, false);

select zfgbb.create_bbcode_attr_mode(24, 'size','<span style=''font-size:{{0}};''>','</span>', false, false);
select zfgbb.create_bbcode_attr(8, 0, 24, 'NAMELESS', 9);

select zfgbb.create_bbcode_config(30, 'thread', '</a>', true);
select zfgbb.create_bbcode_attr_mode(30, 'thread', '<a class="bb-resource-link" href="/forum/thread/{{0}}/1" data-resource="thread" data-thread-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(10, 0, 30, 'NAMELESS', 3);

select zfgbb.create_bbcode_attr_mode(31, 'thread', '<a class="bb-resource-link" href="/forum/thread/{{0}}/1" data-resource="thread" data-thread-id="{{0}}" data-msg-id="{{1}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(11, 0, 31, 'NAMELESS', 3);
select zfgbb.create_bbcode_attr(12, 1, 31, 'msg', 3);

select zfgbb.create_bbcode_config(31, 'board', '</a>', true);
select zfgbb.create_bbcode_attr_mode(32, 'board', '<a class="bb-resource-link" href="/forum/board/{{0}}/1" data-resource="board" data-board-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(13, 0, 32, 'NAMELESS', 3);

select zfgbb.create_bbcode_config(32, 'member', '</a>', true);
select zfgbb.create_bbcode_attr_mode(33, 'member', '<a class="bb-resource-link" href="/user/profile/{{0}}" data-resource="member" data-user-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(14, 0, 33, 'NAMELESS', 3);

select zfgbb.create_bbcode_attr_mode(40, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" href="{{msg.link}}" data-resource="thread" data-msg-id="{{0}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(17, 0, 40, 'msg', 3);

select zfgbb.create_bbcode_attr_mode(41, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" href="{{msg.link}}" data-resource="thread" data-thread-id="{{0}}" data-msg-id="{{1}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(18, 0, 41, 'thread', 3);
select zfgbb.create_bbcode_attr(19, 1, 41, 'msg', 3);

select zfgbb.create_bbcode_attr_mode(42, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" href="{{msg.link}}" data-resource="thread" data-thread-id="{{1}}" data-msg-id="{{2}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(20, 0, 42, 'author', 1);
select zfgbb.create_bbcode_attr(21, 1, 42, 'thread', 3);
select zfgbb.create_bbcode_attr(22, 2, 42, 'msg', 3);

select zfgbb.create_bbcode_attr_mode(43, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" href="{{msg.link}}" data-resource="thread" data-thread-id="{{2}}" data-msg-id="{{3}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(23, 0, 43, 'author', 1);
select zfgbb.create_bbcode_attr(24, 1, 43, 'date', 0);
select zfgbb.create_bbcode_attr(25, 2, 43, 'thread', 3);
select zfgbb.create_bbcode_attr(26, 3, 43, 'msg', 3);

select zfgbb.create_bbcode_config(33, 'me', '</span>', true);
select zfgbb.create_bbcode_attr_mode(50, 'me', '<span class="bb-code-me">* {{0}} ', '</span>', false, true);
select zfgbb.create_bbcode_attr(27, 0, 50, 'NAMELESS', 1);

select zfgbb.create_bbcode_config(34, 'iurl', '</a>', true);
select zfgbb.create_bbcode_attr_mode(51, 'iurl', '<a class="bb-code-iurl" href="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(28, 0, 51, 'NAMELESS', 4);
select zfgbb.create_bbcode_attr_mode(87, 'iurl', '<a class="bb-code-iurl" href="{{c}}">', '</a>', true, true);

select zfgbb.create_bbcode_config(61, 'notoc', '<span class="bb-notoc"></span>', false, true);
select zfgbb.create_bbcode_attr_mode(82, 'notoc', '', '<span class="bb-notoc"></span>', false, false);

select zfgbb.create_bbcode_config(62, 'toc', '<span class="bb-toc"></span>', false, true);
select zfgbb.create_bbcode_attr_mode(83, 'toc', '', '<span class="bb-toc"></span>', false, false);

select zfgbb.create_bbcode_config(35, 'hr', '<hr/>', false, true);
select zfgbb.create_bbcode_attr_mode(52, 'hr', '', '<hr/>', false, false);

select zfgbb.create_bbcode_config(36, 'table', '</table>', true);
select zfgbb.create_bbcode_attr_mode(53, 'table', '<table class="bb-code-table">', '</table>', false, false);

select zfgbb.create_bbcode_config(60, 'th', '</th>', true);
select zfgbb.create_bbcode_attr_mode(80, 'th', '<th class="bb-code-th">', '</th>', false, false);

select zfgbb.create_bbcode_attr_mode(81, 'table', '<table class="bb-code-table bb-table-{{0}}">', '</table>', false, true);
select zfgbb.create_bbcode_attr(41, 0, 81, 'NAMELESS', 1);

select zfgbb.create_bbcode_config(37, 'tr', '</tr>', true);
select zfgbb.create_bbcode_attr_mode(54, 'tr', '<tr>', '</tr>', false, false);

select zfgbb.create_bbcode_config(38, 'td', '</td>', true);
select zfgbb.create_bbcode_attr_mode(55, 'td', '<td>', '</td>', false, false);

select zfgbb.create_bbcode_config(39, 'sup', '</sup>', true);
select zfgbb.create_bbcode_attr_mode(56, 'sup', '<sup>', '</sup>', false, false);

select zfgbb.create_bbcode_config(40, 'glow', '</span>', true);
select zfgbb.create_bbcode_attr_mode(57, 'glow', '<span class="bb-code-glow" style="text-shadow:0 0 {{1}} {{0}}, 0 0 {{1}} {{0}};color:{{0}};">', '</span>', false, true);
select zfgbb.create_bbcode_attr(29, 0, 57, 'NAMELESS', 2);
select zfgbb.create_bbcode_attr(35, 1, 57, 'NAMELESS', 8);

select zfgbb.create_bbcode_config(41, 'font', '</span>', true);
select zfgbb.create_bbcode_attr_mode(58, 'font', '<span class="bb-code-font" style="font-family:{{0}}">', '</span>', false, true);
select zfgbb.create_bbcode_attr(30, 0, 58, 'NAMELESS', 6);

select zfgbb.create_bbcode_config(42, 'you', '</span>', false, false, false);
select zfgbb.create_bbcode_attr_mode(59, 'you', '<span class="bb-you-placeholder">', '</span>', false, false);

select zfgbb.create_bbcode_config(44, 'resource', '</a>', true);
select zfgbb.create_bbcode_attr_mode(61, 'resource', '<a class="bb-resource-link" data-resource="resource" data-resource-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(31, 0, 61, 'NAMELESS', 3);

select zfgbb.create_bbcode_config(45, 'game', '</a>', true);
select zfgbb.create_bbcode_attr_mode(62, 'game', '<a class="bb-resource-link" data-resource="game" data-game-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(32, 0, 62, 'NAMELESS', 3);

select zfgbb.create_bbcode_attr_mode(64, 'code', '<pre class="bb-code-code">', '</pre>', false, false);

select zfgbb.create_bbcode_config(46, 'sub', '</sub>', true);
select zfgbb.create_bbcode_attr_mode(65, 'sub', '<sub>', '</sub>', false, false);

select zfgbb.create_bbcode_config(47, 'tt', '</span>', true);
select zfgbb.create_bbcode_attr_mode(66, 'tt', '<span class="bb-code-tt">', '</span>', false, false);

select zfgbb.create_bbcode_config(48, 'shadow', '</span>', true);
select zfgbb.create_bbcode_attr_mode(67, 'shadow', '<span class="bb-code-shadow" style="text-shadow:0.15rem 0.15rem 0.25rem {{0}};">', '</span>', false, true);
select zfgbb.create_bbcode_attr(33, 0, 67, 'NAMELESS', 2);

select zfgbb.create_bbcode_attr_mode(68, 'list', '<span><ul style="list-style-type:{{0}};">', '</ul></span>', false, false);
select zfgbb.create_bbcode_attr(34, 0, 68, 'type', 7);

select zfgbb.create_bbcode_config(49, 'h1', '</h1>', true);
select zfgbb.create_bbcode_attr_mode(69, 'h1', '<h1 class="bb-code-h1">', '</h1>', false, false);
select zfgbb.create_bbcode_config(50, 'h2', '</h2>', true);
select zfgbb.create_bbcode_attr_mode(70, 'h2', '<h2 class="bb-code-h2">', '</h2>', false, false);
select zfgbb.create_bbcode_config(51, 'h3', '</h3>', true);
select zfgbb.create_bbcode_attr_mode(71, 'h3', '<h3 class="bb-code-h3">', '</h3>', false, false);
select zfgbb.create_bbcode_config(52, 'h4', '</h4>', true);
select zfgbb.create_bbcode_attr_mode(72, 'h4', '<h4 class="bb-code-h4">', '</h4>', false, false);
select zfgbb.create_bbcode_config(53, 'h5', '</h5>', true);
select zfgbb.create_bbcode_attr_mode(73, 'h5', '<h5 class="bb-code-h5">', '</h5>', false, false);
select zfgbb.create_bbcode_config(54, 'h6', '</h6>', true);
select zfgbb.create_bbcode_attr_mode(74, 'h6', '<h6 class="bb-code-h6">', '</h6>', false, false);

select zfgbb.create_bbcode_config(55, 'wiki', '</a>', true);
select zfgbb.create_bbcode_attr_mode(75, 'wiki', '<a class="bb-resource-link" href="/wiki/{{0}}" data-resource="wiki" data-wiki-slug="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(37, 0, 75, 'NAMELESS', 1);

select zfgbb.create_bbcode_config(56, 'project', '</a>', true);
select zfgbb.create_bbcode_attr_mode(76, 'project', '<a class="bb-resource-link" href="/content/projects/{{0}}" data-resource="project" data-project-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(38, 0, 76, 'NAMELESS', 3);

select zfgbb.create_bbcode_config(57, 'screenshot', '</span>', false);
select zfgbb.create_bbcode_attr_mode(77, 'screenshot', '<span class="bb-code-screenshot"><img src="/content/{{c}}"/>', '</span>', true, false);

select zfgbb.create_bbcode_config(58, 'template', '</div>', false);
select zfgbb.create_bbcode_attr_mode(78, 'template', '<div class="bb-code-template" data-resource="template" data-template-name="{{0}}">', '</div>', false, true);
select zfgbb.create_bbcode_attr(39, 0, 78, 'NAMELESS', 1);

select zfgbb.create_bbcode_config(59, 'attachment', '</a>', true);
select zfgbb.create_bbcode_attr_mode(79, 'attachment', '<a class="bb-resource-link" href="/content/attachment/{{0}}" data-resource="attachment" data-attachment-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(40, 0, 79, 'NAMELESS', 3);

select zfgbb.create_bbcode_config(63, 'grid', '</div>', true);
select zfgbb.create_bbcode_attr_mode(91, 'grid', '<div class="bb-code-grid bb-grid-{{0}}">', '</div>', false, false);
select zfgbb.create_bbcode_attr(46, 0, 91, 'NAMELESS', 3);

select zfgbb.create_bbcode_config(64, 'widget', '</div>', true);
select zfgbb.create_bbcode_attr_mode(92, 'widget', '<div class="bb-code-widget" data-widget-title="{{0}}">', '</div>', false, false);
select zfgbb.create_bbcode_attr(47, 0, 92, 'NAMELESS', 1);
select zfgbb.create_bbcode_attr_mode(93, 'widget', '<div class="bb-code-widget">', '</div>', false, false);
