alter table zfgbb.message_history
	add column content_format text not null default 'BBCODE'
		references zfgbb.content_format(code);
