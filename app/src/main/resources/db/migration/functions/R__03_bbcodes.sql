create or replace function zfgbb.create_attribute_data_type(p_code text, p_label text, p_ordinal int, p_validation_pattern text, p_fallback_value text, p_value_admits_whitespace boolean, p_lowercases_value boolean, p_bare_integer_unit text, p_allowed_values text)
returns void
language plpgsql
as $$
begin

	insert into zfgbb.attribute_data_type(code, label, ordinal, validation_pattern, fallback_value, value_admits_whitespace, lowercases_value, bare_integer_unit, allowed_values)
	values(p_code, p_label, p_ordinal, p_validation_pattern, p_fallback_value, p_value_admits_whitespace, p_lowercases_value, p_bare_integer_unit, p_allowed_values)
	on conflict (code)
	do update set label = p_label,
				  ordinal = p_ordinal,
				  validation_pattern = p_validation_pattern,
				  fallback_value = p_fallback_value,
				  value_admits_whitespace = p_value_admits_whitespace,
				  lowercases_value = p_lowercases_value,
				  bare_integer_unit = p_bare_integer_unit,
				  allowed_values = p_allowed_values;

	return;

end; $$;

create or replace function zfgbb.create_attribute_value_mapping(p_attribute_data_type text, p_from_value text, p_to_value text)
returns void
language plpgsql
as $$
begin

	insert into zfgbb.attribute_value_mapping(attribute_data_type, from_value, to_value)
	values(p_attribute_data_type, p_from_value, p_to_value)
	on conflict (attribute_data_type, from_value)
	do update set to_value = p_to_value;

	return;

end; $$;

create or replace function zfgbb.create_list_style_type(p_code text, p_label text, p_ordinal int, p_numbers_items boolean)
returns void
language plpgsql
as $$
begin

	insert into zfgbb.list_style_type(code, label, ordinal, numbers_items)
	values(p_code, p_label, p_ordinal, p_numbers_items)
	on conflict (code)
	do update set label = p_label, ordinal = p_ordinal, numbers_items = p_numbers_items;

	return;

end; $$;

create or replace function zfgbb.create_markdown_equivalent(p_code text, p_label text, p_ordinal int)
returns void
language plpgsql
as $$
begin

	insert into zfgbb.markdown_equivalent(code, label, ordinal)
	values(p_code, p_label, p_ordinal)
	on conflict (code)
	do update set label = p_label, ordinal = p_ordinal;

	return;

end; $$;

drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean);
drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean, boolean);
drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean, boolean, boolean);
drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean, boolean, boolean, text, text);
drop function if exists zfgbb.create_bbcode_config(int, text, text, boolean, boolean, boolean, text, text, text, boolean, text, text);
create or replace function zfgbb.create_bbcode_config(p_bbcode_config_id int, p_bbcode text, p_end_tag text, p_process_content boolean, p_self_closing boolean default false, p_enabled boolean default true, p_source_reference_attribute text default null, p_source_reference_resolver text default null, p_markdown_equivalent text default null, p_markdown_canonical boolean default false, p_implicit_item_marker text default null, p_implicit_item_code text default null, p_honoured_in_forum boolean default true, p_honoured_in_wiki boolean default true, p_honoured_in_project boolean default true, p_honoured_in_resource boolean default true, p_honoured_in_signature boolean default true)
returns void
language plpgsql
as $$

begin

	insert into zfgbb.bb_code_config(bb_code_config_id, code, end_tag, process_content_flag, self_closing_flag, enabled_flag, source_reference_attribute, source_reference_resolver, markdown_equivalent, markdown_canonical_flag, implicit_item_marker, implicit_item_code, honoured_in_forum_flag, honoured_in_wiki_flag, honoured_in_project_flag, honoured_in_resource_flag, honoured_in_signature_flag)
	values(p_bbcode_config_id, p_bbcode, p_end_tag, p_process_content, p_self_closing, p_enabled, p_source_reference_attribute, p_source_reference_resolver, p_markdown_equivalent, p_markdown_canonical, p_implicit_item_marker, p_implicit_item_code, p_honoured_in_forum, p_honoured_in_wiki, p_honoured_in_project, p_honoured_in_resource, p_honoured_in_signature)
	on conflict (bb_code_config_id)
	do update set code = p_bbcode, end_tag = p_end_tag, process_content_flag = p_process_content, self_closing_flag = p_self_closing, source_reference_attribute = p_source_reference_attribute, source_reference_resolver = p_source_reference_resolver, markdown_equivalent = p_markdown_equivalent, markdown_canonical_flag = p_markdown_canonical, implicit_item_marker = p_implicit_item_marker, implicit_item_code = p_implicit_item_code, updated_ts = current_timestamp;

	return;

end; $$;

drop function if exists zfgbb.create_bbcode_attr_mode(int, text, text, text, boolean, boolean);
create or replace function zfgbb.create_bbcode_attr_mode(p_attr_mode_id int, p_bbcode text, p_open_tag text, p_close_tag text, p_content_attr_flag boolean, p_output_content_flag boolean, p_content_semantic_role text default null)
returns void
language plpgsql
as $$
declare
	bbcode_id int;
begin
	bbcode_id := (select bb_code_config_id
				 from zfgbb.bb_code_config
	             where code = p_bbcode);

	insert into zfgbb.bb_code_attribute_mode(bb_code_attribute_mode_id, bb_code_config_id, content_is_attribute_flag, open_tag, close_tag, output_content_flag, content_semantic_role)
	values(p_attr_mode_id, bbcode_id, p_content_attr_flag, p_open_tag, p_close_tag, p_output_content_flag, p_content_semantic_role)
	on conflict (bb_code_attribute_mode_id)
	do update set bb_code_config_id = bbcode_id,
				  content_is_attribute_flag = p_content_attr_flag,
				  open_tag = p_open_tag,
				  close_tag = p_close_tag,
				  output_content_flag = p_output_content_flag,
				  content_semantic_role = p_content_semantic_role,
				  updated_ts = current_timestamp;

	return;

end; $$;

drop function if exists zfgbb.create_bbcode_attr(int, int, int, text, int);
drop function if exists zfgbb.create_bbcode_attr(int, int, int, text, text);
create or replace function zfgbb.create_bbcode_attr(p_bb_code_attr_id int, p_attr_index int, p_bb_code_mode_id int, p_attr_name text, p_attr_type text, p_semantic_role text default null)
returns void
language plpgsql
as $$
begin

	insert into zfgbb.bb_code_attribute(bb_code_attribute_id, attribute_index, bb_code_attribute_mode_id, name, attribute_data_type, semantic_role)
	values(p_bb_code_attr_id, p_attr_index, p_bb_code_mode_id, p_attr_name, p_attr_type, p_semantic_role)
	on conflict (bb_code_attribute_id)
	do update set attribute_index = p_attr_index,
				  bb_code_attribute_mode_id = p_bb_code_mode_id,
				  name = p_attr_name,
				  attribute_data_type = p_attr_type,
				  semantic_role = p_semantic_role,
				  updated_ts = current_timestamp;

end; $$;

--attribute data types
--code, label, ordinal, validation pattern, fallback value, value admits whitespace, lowercases value, bare integer unit, allowed values
select zfgbb.create_attribute_data_type('TIMESTAMP', 'Date/time', 1, null, '', false, false, null, null);
select zfgbb.create_attribute_data_type('TEXT', 'Plain text', 2, null, '', true, false, null, null);
select zfgbb.create_attribute_data_type('COLOR', 'Color', 3, '^(?:#(?:[0-9a-fA-F]{3,4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})|rgba?\(\s*\d{1,3}\s*,\s*\d{1,3}\s*,\s*\d{1,3}\s*(?:,\s*(?:0|1|0?\.\d+)\s*)?\)|[a-zA-Z]+)$', '', true, true, null, null);
select zfgbb.create_attribute_data_type('INTEGER', 'Whole number', 4, '^\d+$', '', false, false, null, null);
select zfgbb.create_attribute_data_type('URL', 'Link', 5, null, '', true, false, null, null);
select zfgbb.create_attribute_data_type('IDENTIFIER', 'Identifier', 6, '^[A-Za-z0-9_-]+$', '', false, false, null, null);
select zfgbb.create_attribute_data_type('FONT_NAME', 'Font name', 7, '^[A-Za-z0-9 ,''"_-]+$', '', true, false, null, null);
select zfgbb.create_attribute_data_type('LIST_TYPE', 'List style', 8, null, 'decimal', false, true, null, null);
select zfgbb.create_attribute_data_type('DIMENSION', 'Dimension', 9, '^\d+(?:\.\d+)?(?:px|pt|em|rem|%)?$', '', false, false, 'px', null);
select zfgbb.create_attribute_data_type('SIZE', 'Legacy size level', 10, '^\d+(?:\.\d+)?(?:px|pt|em|rem|%)?$', '', false, false, 'px', null);
select zfgbb.create_attribute_data_type('ALIGNMENT', 'Alignment', 11, null, 'left', false, true, null, 'left,center,right');

--the legacy size levels a bare level number stands for
select zfgbb.create_attribute_value_mapping('SIZE', '1', '8pt');
select zfgbb.create_attribute_value_mapping('SIZE', '2', '10pt');
select zfgbb.create_attribute_value_mapping('SIZE', '3', '12pt');
select zfgbb.create_attribute_value_mapping('SIZE', '4', '14pt');
select zfgbb.create_attribute_value_mapping('SIZE', '5', '18pt');
select zfgbb.create_attribute_value_mapping('SIZE', '6', '24pt');
select zfgbb.create_attribute_value_mapping('SIZE', '7', '36pt');

--the list styles a list may carry, and which of them number their items
select zfgbb.create_list_style_type('decimal', 'Numbered', 1, true);
select zfgbb.create_list_style_type('lower-roman', 'Lower case roman', 2, true);
select zfgbb.create_list_style_type('upper-roman', 'Upper case roman', 3, true);
select zfgbb.create_list_style_type('lower-alpha', 'Lower case letters', 4, true);
select zfgbb.create_list_style_type('upper-alpha', 'Upper case letters', 5, true);
select zfgbb.create_list_style_type('disc', 'Disc', 6, false);
select zfgbb.create_list_style_type('circle', 'Circle', 7, false);
select zfgbb.create_list_style_type('square', 'Square', 8, false);
select zfgbb.create_list_style_type('none', 'No marker', 9, false);

update zfgbb.attribute_data_type
set allowed_values = (select string_agg(code, ',' order by ordinal) from zfgbb.list_style_type)
where code = 'LIST_TYPE';

--the markdown constructs a bb code may stand in for
select zfgbb.create_markdown_equivalent('STRONG_EMPHASIS', 'Strong emphasis', 1);
select zfgbb.create_markdown_equivalent('EMPHASIS', 'Emphasis', 2);
select zfgbb.create_markdown_equivalent('HEADING', 'Heading', 3);
select zfgbb.create_markdown_equivalent('THEMATIC_BREAK', 'Thematic break', 4);
select zfgbb.create_markdown_equivalent('LINK', 'Link', 5);
select zfgbb.create_markdown_equivalent('IMAGE', 'Image', 6);
select zfgbb.create_markdown_equivalent('BLOCK_QUOTE', 'Block quote', 7);
select zfgbb.create_markdown_equivalent('FENCED_CODE', 'Fenced code block', 8);
select zfgbb.create_markdown_equivalent('LIST', 'List', 9);
select zfgbb.create_markdown_equivalent('INLINE_CODE', 'Inline code span', 10);

--base configurations
select zfgbb.create_bbcode_config(1, 'b', '</span>', true, p_markdown_equivalent => 'STRONG_EMPHASIS', p_markdown_canonical => true);
select zfgbb.create_bbcode_config(2, 'u', '</span>', true);
select zfgbb.create_bbcode_config(3, 'i', '</span>', true, p_markdown_equivalent => 'EMPHASIS', p_markdown_canonical => true);
select zfgbb.create_bbcode_config(4, 's', '</span>', true);
select zfgbb.create_bbcode_config(5, 'pre', '</pre>', false);
select zfgbb.create_bbcode_config(65, 'align', '</div>', true);
select zfgbb.create_bbcode_config(9, 'youtube', '</span>', false);
select zfgbb.create_bbcode_config(10, 'spoiler', '</span>', true);
select zfgbb.create_bbcode_config(11, 'img', '</span>', false, p_markdown_equivalent => 'IMAGE', p_markdown_canonical => true);
select zfgbb.create_bbcode_config(12, 'url', '</a></span>', true, p_markdown_equivalent => 'LINK', p_markdown_canonical => true);
select zfgbb.create_bbcode_config(15, 'move', '</marquee>', true);
select zfgbb.create_bbcode_config(18, 'quote', '</div></div>', true, false, true, 'msg', 'MESSAGE', 'BLOCK_QUOTE', true);
select zfgbb.create_bbcode_config(19, 'code', '</pre>', false, p_markdown_equivalent => 'FENCED_CODE', p_markdown_canonical => true);
select zfgbb.create_bbcode_config(20, 'color', '</span>', true);
select zfgbb.create_bbcode_config(22, 'list', '</ul>', true, p_markdown_equivalent => 'LIST', p_markdown_canonical => true, p_implicit_item_marker => '[*]', p_implicit_item_code => 'li');
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
select zfgbb.create_bbcode_attr_mode(95,'align','<div class="bb-code-align bb-align-{{0}}">','</div>',false,false);
select zfgbb.create_bbcode_attr(50, 0, 95, 'NAMELESS', 'ALIGNMENT');

select zfgbb.create_bbcode_attr_mode(8,'youtube','<div class="bb-code-youtube"><iframe width="640" height="480" src="https://www.youtube.com/embed/{{c}}" frameborder="0" allow="encrypted-media" allowfullscreen> ','</iframe></div>',true,false);
select zfgbb.create_bbcode_attr(1, 0, 8, 'NAMELESS', 'IDENTIFIER');
select zfgbb.create_bbcode_attr_mode(84,'youtube','<div class="bb-code-youtube"><iframe width="640" height="480" src="https://www.youtube.com/embed/{{c}}" frameborder="0" allow="encrypted-media" allowfullscreen> ','</iframe></div>',true,false);

select zfgbb.create_bbcode_attr_mode(9,'spoiler','<span class="bb-code-spoiler">','</span>',false,false);
select zfgbb.create_bbcode_attr_mode(10,'img','<span class="bb-code-img"><img src="{{c}}"/>','</span>',true,false,'DESTINATION');
select zfgbb.create_bbcode_attr_mode(88,'img','<span class="bb-code-img"><img src="{{c}}" width="{{0}}" height="{{1}}"/>','</span>',true,false,'DESTINATION');
select zfgbb.create_bbcode_attr(42, 0, 88, 'width', 'INTEGER', 'WIDTH');
select zfgbb.create_bbcode_attr(43, 1, 88, 'height', 'INTEGER', 'HEIGHT');
select zfgbb.create_bbcode_attr_mode(89,'img','<span class="bb-code-img"><img src="{{c}}" width="{{0}}"/>','</span>',true,false,'DESTINATION');
select zfgbb.create_bbcode_attr(44, 0, 89, 'width', 'INTEGER', 'WIDTH');
select zfgbb.create_bbcode_attr_mode(90,'img','<span class="bb-code-img"><img src="{{c}}" height="{{0}}"/>','</span>',true,false,'DESTINATION');
select zfgbb.create_bbcode_attr(45, 0, 90, 'height', 'INTEGER', 'HEIGHT');
select zfgbb.create_bbcode_attr_mode(11,'url','<span class="bb-code-url"><a href="{{0}}">','</a></span>',false,true);
select zfgbb.create_bbcode_attr(7, 0, 11, 'NAMELESS', 'URL', 'DESTINATION');
select zfgbb.create_bbcode_attr_mode(85,'url','<span class="bb-code-url"><a href="{{c}}">','</a></span>',true,true,'DESTINATION');

select zfgbb.create_bbcode_attr_mode(14,'move','<marquee>','</marquee>',false,false);


select zfgbb.create_bbcode_attr_mode(17,'quote','<div class="bb-code-quote"><div class="bb-code-quote-body">','</div></div>',false,false);

select zfgbb.create_bbcode_attr_mode(18,'quote','<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{0}},</div><div class="bb-code-quote-body">','</div></div>',false,false);
select zfgbb.create_bbcode_attr(2, 0, 18, 'author', 'TEXT');

select zfgbb.create_bbcode_attr_mode(19,'quote','<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{0}} on {{2}}</div><div class="bb-code-quote-body">','</div></div>',false,false);
select zfgbb.create_bbcode_attr(4, 0, 19, 'author', 'TEXT');
select zfgbb.create_bbcode_attr(5, 1, 19, 'link', 'URL');
select zfgbb.create_bbcode_attr(6, 2, 19, 'date', 'TIMESTAMP');

select zfgbb.create_bbcode_attr_mode(20,'color','<span class="bb-color" style="--bb-color:{{0}}">','</span>',false,false);
select zfgbb.create_bbcode_attr(36, 0, 20, 'NAMELESS', 'COLOR');

select zfgbb.create_bbcode_attr_mode(22, 'list','<ul>','</ul>', false, false);

select zfgbb.create_bbcode_attr_mode(23, 'li','<li>','</li>', false, false);

select zfgbb.create_bbcode_attr_mode(24, 'size','<span class="bb-size" style="--bb-size:{{0}}">','</span>', false, false);
select zfgbb.create_bbcode_attr(8, 0, 24, 'NAMELESS', 'SIZE');

select zfgbb.create_bbcode_config(30, 'thread', '</a>', true);
select zfgbb.create_bbcode_attr_mode(30, 'thread', '<a class="bb-resource-link" href="/forum/thread/{{0}}/1" data-resource="thread" data-thread-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(10, 0, 30, 'NAMELESS', 'INTEGER');

select zfgbb.create_bbcode_attr_mode(31, 'thread', '<a class="bb-resource-link" href="/forum/thread/{{0}}/1" data-resource="thread" data-thread-id="{{0}}" data-msg-id="{{1}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(11, 0, 31, 'NAMELESS', 'INTEGER');
select zfgbb.create_bbcode_attr(12, 1, 31, 'msg', 'INTEGER');

select zfgbb.create_bbcode_config(31, 'board', '</a>', true);
select zfgbb.create_bbcode_attr_mode(32, 'board', '<a class="bb-resource-link" href="/forum/board/{{0}}/1" data-resource="board" data-board-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(13, 0, 32, 'NAMELESS', 'INTEGER');

select zfgbb.create_bbcode_config(32, 'member', '</a>', true);
select zfgbb.create_bbcode_attr_mode(33, 'member', '<a class="bb-resource-link" href="/user/profile/{{0}}" data-resource="member" data-user-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(14, 0, 33, 'NAMELESS', 'INTEGER');

select zfgbb.create_bbcode_attr_mode(40, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{#msg.authorUserId}}<a class="bb-resource-link" href="/user/profile/{{msg.authorUserId}}" data-resource="member" data-user-id="{{msg.authorUserId}}">{{/msg.authorUserId}}{{#msg.author}}{{msg.author}}{{/msg.author}}{{^msg.author}}{{#msg.permitted}}(unknown){{/msg.permitted}}{{^msg.permitted}}(unavailable){{/msg.permitted}}{{/msg.author}}{{#msg.authorUserId}}</a>{{/msg.authorUserId}} on {{#msg.dateIso}}<time class="bb-date-long" datetime="{{msg.dateIso}}">{{msg.dateText}}</time>{{/msg.dateIso}} (<a class="bb-resource-link" href="{{#msg.threadId}}/forum/thread/{{msg.threadId}}/{{msg.page}}#msg{{msg.sourceId}}{{/msg.threadId}}{{^msg.threadId}}#{{/msg.threadId}}" data-resource="thread" data-msg-id="{{0}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(17, 0, 40, 'msg', 'INTEGER');

select zfgbb.create_bbcode_attr_mode(41, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{#msg.authorUserId}}<a class="bb-resource-link" href="/user/profile/{{msg.authorUserId}}" data-resource="member" data-user-id="{{msg.authorUserId}}">{{/msg.authorUserId}}{{#msg.author}}{{msg.author}}{{/msg.author}}{{^msg.author}}{{#msg.permitted}}(unknown){{/msg.permitted}}{{^msg.permitted}}(unavailable){{/msg.permitted}}{{/msg.author}}{{#msg.authorUserId}}</a>{{/msg.authorUserId}} on {{#msg.dateIso}}<time class="bb-date-long" datetime="{{msg.dateIso}}">{{msg.dateText}}</time>{{/msg.dateIso}} (<a class="bb-resource-link" href="{{#msg.threadId}}/forum/thread/{{msg.threadId}}/{{msg.page}}#msg{{msg.sourceId}}{{/msg.threadId}}{{^msg.threadId}}#{{/msg.threadId}}" data-resource="thread" data-thread-id="{{0}}" data-msg-id="{{1}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(18, 0, 41, 'thread', 'INTEGER');
select zfgbb.create_bbcode_attr(19, 1, 41, 'msg', 'INTEGER');

select zfgbb.create_bbcode_attr_mode(42, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{#msg.authorUserId}}<a class="bb-resource-link" href="/user/profile/{{msg.authorUserId}}" data-resource="member" data-user-id="{{msg.authorUserId}}">{{/msg.authorUserId}}{{#msg.author}}{{msg.author}}{{/msg.author}}{{^msg.author}}{{#msg.permitted}}(unknown){{/msg.permitted}}{{^msg.permitted}}(unavailable){{/msg.permitted}}{{/msg.author}}{{#msg.authorUserId}}</a>{{/msg.authorUserId}} on {{#msg.dateIso}}<time class="bb-date-long" datetime="{{msg.dateIso}}">{{msg.dateText}}</time>{{/msg.dateIso}} (<a class="bb-resource-link" href="{{#msg.threadId}}/forum/thread/{{msg.threadId}}/{{msg.page}}#msg{{msg.sourceId}}{{/msg.threadId}}{{^msg.threadId}}#{{/msg.threadId}}" data-resource="thread" data-thread-id="{{1}}" data-msg-id="{{2}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(20, 0, 42, 'author', 'TEXT');
select zfgbb.create_bbcode_attr(21, 1, 42, 'thread', 'INTEGER');
select zfgbb.create_bbcode_attr(22, 2, 42, 'msg', 'INTEGER');

select zfgbb.create_bbcode_attr_mode(43, 'quote', '<div class="bb-code-quote"><div class="bb-code-quote-header">Quote from {{#msg.authorUserId}}<a class="bb-resource-link" href="/user/profile/{{msg.authorUserId}}" data-resource="member" data-user-id="{{msg.authorUserId}}">{{/msg.authorUserId}}{{#msg.author}}{{msg.author}}{{/msg.author}}{{^msg.author}}{{#msg.permitted}}(unknown){{/msg.permitted}}{{^msg.permitted}}(unavailable){{/msg.permitted}}{{/msg.author}}{{#msg.authorUserId}}</a>{{/msg.authorUserId}} on {{#msg.dateIso}}<time class="bb-date-long" datetime="{{msg.dateIso}}">{{msg.dateText}}</time>{{/msg.dateIso}} (<a class="bb-resource-link" href="{{#msg.threadId}}/forum/thread/{{msg.threadId}}/{{msg.page}}#msg{{msg.sourceId}}{{/msg.threadId}}{{^msg.threadId}}#{{/msg.threadId}}" data-resource="thread" data-thread-id="{{2}}" data-msg-id="{{3}}">jump to message</a>)</div><div class="bb-code-quote-body">', '</div></div>', false, false);
select zfgbb.create_bbcode_attr(23, 0, 43, 'author', 'TEXT');
select zfgbb.create_bbcode_attr(24, 1, 43, 'date', 'TIMESTAMP');
select zfgbb.create_bbcode_attr(25, 2, 43, 'thread', 'INTEGER');
select zfgbb.create_bbcode_attr(26, 3, 43, 'msg', 'INTEGER');

select zfgbb.create_bbcode_config(33, 'me', '</span>', true);
select zfgbb.create_bbcode_attr_mode(50, 'me', '<span class="bb-code-me">* {{0}} ', '</span>', false, true);
select zfgbb.create_bbcode_attr(27, 0, 50, 'NAMELESS', 'TEXT');

select zfgbb.create_bbcode_config(61, 'notoc', '<span class="bb-notoc"></span>', false, true);
select zfgbb.create_bbcode_attr_mode(82, 'notoc', '', '<span class="bb-notoc"></span>', false, false);

select zfgbb.create_bbcode_config(62, 'toc', '<span class="bb-toc"></span>', false, true);
select zfgbb.create_bbcode_attr_mode(83, 'toc', '', '<span class="bb-toc"></span>', false, false);

select zfgbb.create_bbcode_config(35, 'hr', '<hr/>', false, true, p_markdown_equivalent => 'THEMATIC_BREAK', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(52, 'hr', '', '<hr/>', false, false);

select zfgbb.create_bbcode_config(36, 'table', '</table>', true);
select zfgbb.create_bbcode_attr_mode(53, 'table', '<table class="bb-code-table">', '</table>', false, false);

select zfgbb.create_bbcode_config(60, 'th', '</th>', true);
select zfgbb.create_bbcode_attr_mode(80, 'th', '<th class="bb-code-th">', '</th>', false, false);

select zfgbb.create_bbcode_attr_mode(81, 'table', '<table class="bb-code-table bb-table-{{0}}">', '</table>', false, true);
select zfgbb.create_bbcode_attr(41, 0, 81, 'NAMELESS', 'TEXT');

select zfgbb.create_bbcode_config(37, 'tr', '</tr>', true);
select zfgbb.create_bbcode_attr_mode(54, 'tr', '<tr>', '</tr>', false, false);

select zfgbb.create_bbcode_config(38, 'td', '</td>', true);
select zfgbb.create_bbcode_attr_mode(55, 'td', '<td>', '</td>', false, false);

select zfgbb.create_bbcode_config(39, 'sup', '</sup>', true);
select zfgbb.create_bbcode_attr_mode(56, 'sup', '<sup>', '</sup>', false, false);

select zfgbb.create_bbcode_config(40, 'glow', '</span>', true);
select zfgbb.create_bbcode_attr_mode(57, 'glow', '<span class="bb-code-glow" style="--bb-glow-color:{{0}};--bb-glow-radius:{{1}}">', '</span>', false, true);
select zfgbb.create_bbcode_attr(29, 0, 57, 'NAMELESS', 'COLOR');
select zfgbb.create_bbcode_attr(35, 1, 57, 'NAMELESS', 'DIMENSION');

select zfgbb.create_bbcode_config(41, 'font', '</span>', true);
select zfgbb.create_bbcode_attr_mode(58, 'font', '<span class="bb-code-font" style="--bb-font:{{0}}">', '</span>', false, true);
select zfgbb.create_bbcode_attr(30, 0, 58, 'NAMELESS', 'FONT_NAME');

select zfgbb.create_bbcode_config(42, 'you', '</span>', false, false, false);
select zfgbb.create_bbcode_attr_mode(59, 'you', '<span class="bb-you-placeholder">', '</span>', false, false);

select zfgbb.create_bbcode_config(44, 'resource', '</a>', true);
select zfgbb.create_bbcode_attr_mode(61, 'resource', '<a class="bb-resource-link" data-resource="resource" data-resource-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(31, 0, 61, 'NAMELESS', 'INTEGER');

select zfgbb.create_bbcode_attr_mode(64, 'code', '<pre class="bb-code-code">', '</pre>', false, false);

select zfgbb.create_bbcode_config(46, 'sub', '</sub>', true);
select zfgbb.create_bbcode_attr_mode(65, 'sub', '<sub>', '</sub>', false, false);

select zfgbb.create_bbcode_config(47, 'tt', '</span>', true, p_markdown_equivalent => 'INLINE_CODE', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(66, 'tt', '<span class="bb-code-tt">', '</span>', false, false);

select zfgbb.create_bbcode_config(48, 'shadow', '</span>', true);
select zfgbb.create_bbcode_attr_mode(67, 'shadow', '<span class="bb-code-shadow" style="--bb-shadow-color:{{0}}">', '</span>', false, true);
select zfgbb.create_bbcode_attr(33, 0, 67, 'NAMELESS', 'COLOR');
select zfgbb.create_bbcode_attr(49, 1, 67, 'NAMELESS', 'IDENTIFIER');

select zfgbb.create_bbcode_attr_mode(68, 'list', '<ul class="bb-list-{{0}}">', '</ul>', false, false);
select zfgbb.create_bbcode_attr(34, 0, 68, 'type', 'LIST_TYPE', 'LIST_STYLE');

select zfgbb.create_bbcode_attr_mode(94, 'list', '<ul class="bb-list-{{0}}">', '</ul>', false, false);
select zfgbb.create_bbcode_attr(48, 0, 94, 'NAMELESS', 'LIST_TYPE', 'LIST_STYLE');

select zfgbb.create_bbcode_config(49, 'h1', '</h1>', true, p_markdown_equivalent => 'HEADING', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(69, 'h1', '<h1 class="bb-code-h1">', '</h1>', false, false);
select zfgbb.create_bbcode_config(50, 'h2', '</h2>', true, p_markdown_equivalent => 'HEADING', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(70, 'h2', '<h2 class="bb-code-h2">', '</h2>', false, false);
select zfgbb.create_bbcode_config(51, 'h3', '</h3>', true, p_markdown_equivalent => 'HEADING', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(71, 'h3', '<h3 class="bb-code-h3">', '</h3>', false, false);
select zfgbb.create_bbcode_config(52, 'h4', '</h4>', true, p_markdown_equivalent => 'HEADING', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(72, 'h4', '<h4 class="bb-code-h4">', '</h4>', false, false);
select zfgbb.create_bbcode_config(53, 'h5', '</h5>', true, p_markdown_equivalent => 'HEADING', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(73, 'h5', '<h5 class="bb-code-h5">', '</h5>', false, false);
select zfgbb.create_bbcode_config(54, 'h6', '</h6>', true, p_markdown_equivalent => 'HEADING', p_markdown_canonical => true);
select zfgbb.create_bbcode_attr_mode(74, 'h6', '<h6 class="bb-code-h6">', '</h6>', false, false);

select zfgbb.create_bbcode_config(55, 'wiki', '</a>', true);
select zfgbb.create_bbcode_attr_mode(75, 'wiki', '<a class="bb-resource-link" href="/wiki/{{0}}" data-resource="wiki" data-wiki-slug="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(37, 0, 75, 'NAMELESS', 'TEXT');

select zfgbb.create_bbcode_config(56, 'project', '</a>', true);
select zfgbb.create_bbcode_attr_mode(76, 'project', '<a class="bb-resource-link" href="/content/projects/{{0}}" data-resource="project" data-project-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(38, 0, 76, 'NAMELESS', 'INTEGER');

select zfgbb.create_bbcode_config(58, 'template', '</div>', false, p_honoured_in_signature => false);
select zfgbb.create_bbcode_attr_mode(78, 'template', '<div class="bb-code-template" data-resource="template" data-template-name="{{0}}">', '</div>', false, true);
select zfgbb.create_bbcode_attr(39, 0, 78, 'NAMELESS', 'TEXT');

select zfgbb.create_bbcode_config(59, 'attachment', '</a>', true);
select zfgbb.create_bbcode_attr_mode(79, 'attachment', '<a class="bb-resource-link" href="/content/attachment/{{0}}" data-resource="attachment" data-attachment-id="{{0}}">', '</a>', false, true);
select zfgbb.create_bbcode_attr(40, 0, 79, 'NAMELESS', 'INTEGER');

select zfgbb.create_bbcode_config(63, 'grid', '</div>', true);
select zfgbb.create_bbcode_attr_mode(91, 'grid', '<div class="bb-code-grid bb-grid-{{0}}">', '</div>', false, false);
select zfgbb.create_bbcode_attr(46, 0, 91, 'NAMELESS', 'INTEGER');

select zfgbb.create_bbcode_config(64, 'widget', '</div>', true, p_honoured_in_forum => false, p_honoured_in_signature => false);
select zfgbb.create_bbcode_attr_mode(92, 'widget', '<div class="bb-code-widget" data-widget-title="{{0}}">', '</div>', false, false);
select zfgbb.create_bbcode_attr(47, 0, 92, 'NAMELESS', 'TEXT');
select zfgbb.create_bbcode_attr_mode(93, 'widget', '<div class="bb-code-widget">', '</div>', false, false);

select setval(
	pg_get_serial_sequence('zfgbb.bb_code_config', 'bb_code_config_id'),
	greatest((select coalesce(max(bb_code_config_id), 0) from zfgbb.bb_code_config), 1000),
	true);
select setval(
	pg_get_serial_sequence('zfgbb.bb_code_attribute_mode', 'bb_code_attribute_mode_id'),
	greatest((select coalesce(max(bb_code_attribute_mode_id), 0) from zfgbb.bb_code_attribute_mode), 1000),
	true);
select setval(
	pg_get_serial_sequence('zfgbb.bb_code_attribute', 'bb_code_attribute_id'),
	greatest((select coalesce(max(bb_code_attribute_id), 0) from zfgbb.bb_code_attribute), 1000),
	true);
