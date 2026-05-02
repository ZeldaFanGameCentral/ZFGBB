drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean);
create or replace function zfgbb.create_bbcode_config(p_bbcode_config_id int, p_bbcode text, p_end_tag text, p_process_content boolean, p_self_closing boolean default false)
returns void
language plpgsql
as $$

begin

	insert into zfgbb.bb_code_config(bb_code_config_id, code, end_tag, process_content_flag, self_closing_flag)
	values(p_bbcode_config_id, p_bbcode, p_end_tag, p_process_content, p_self_closing)
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
select zfgbb.create_bbcode_config(5, 'pre', '</pre>', true);
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
select zfgbb.create_bbcode_config(19, 'code', '</pre>', true);
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

select zfgbb.create_bbcode_attr_mode(9,'spoiler','<span class="bb-code-spoiler">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(10,'img','<span class="bb-code-img"><img src="{{c}}"/>','</span>',true,false);
select zfgbb.create_bbcode_attr_mode(11,'url','<span class="bb-code-url"><a href="{{0}}">','</a></span>',false,true);
select zfgbb.create_bbcode_attr(7, 0, 11, 'NAMELESS', 4);

select zfgbb.create_bbcode_attr_mode(12,'email','<span class="bb-code-email"><a href="mailto:{{0}}">','</a></span>',false,false);
select zfgbb.create_bbcode_attr(3, 0, 12, 'NAMELESS', 1);

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
select zfgbb.create_bbcode_attr(8, 0, 24, 'NAMELESS', 8);

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

select zfgbb.create_bbcode_attr_mode(40, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" data-resource="thread" data-msg-id="{{0}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(17, 0, 40, 'msg', 3);

select zfgbb.create_bbcode_attr_mode(41, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" href="/forum/thread/{{0}}/1" data-resource="thread" data-thread-id="{{0}}" data-msg-id="{{1}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(18, 0, 41, 'thread', 3);
select zfgbb.create_bbcode_attr(19, 1, 41, 'msg', 3);

select zfgbb.create_bbcode_attr_mode(42, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" href="/forum/thread/{{1}}/1" data-resource="thread" data-thread-id="{{1}}" data-msg-id="{{2}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(20, 0, 42, 'author', 1);
select zfgbb.create_bbcode_attr(21, 1, 42, 'thread', 3);
select zfgbb.create_bbcode_attr(22, 2, 42, 'msg', 3);

select zfgbb.create_bbcode_attr_mode(43, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{msg.author}} on {{msg.date}} (<a class="bb-resource-link" href="/forum/thread/{{2}}/1" data-resource="thread" data-thread-id="{{2}}" data-msg-id="{{3}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
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

select zfgbb.create_bbcode_config(35, 'hr', '<hr/>', false, true);
select zfgbb.create_bbcode_attr_mode(52, 'hr', '', '<hr/>', false, false);

select zfgbb.create_bbcode_config(36, 'table', '</table>', true);
select zfgbb.create_bbcode_attr_mode(53, 'table', '<table class="bb-code-table">', '</table>', false, false);

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

select zfgbb.create_bbcode_config(42, 'you', '</span>', false);
select zfgbb.create_bbcode_attr_mode(59, 'you', '<span class="bb-you-placeholder">', '</span>', false, false);

select zfgbb.create_bbcode_config(43, 'request', '', true);
select zfgbb.create_bbcode_attr_mode(60, 'request', '', '', false, false);

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

select zfgbb.create_bbcode_attr_mode(68, 'list', '<span><ol>', '</ol></span>', false, false);
select zfgbb.create_bbcode_attr(34, 0, 68, 'type', 7);
