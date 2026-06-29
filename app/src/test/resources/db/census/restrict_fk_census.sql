select con.conname
from pg_constraint con
where con.contype = 'f'
	and con.confdeltype in ('a', 'r')
	and con.confrelid in (
		'zfgbb."user"'::regclass,
		'zfgbb.thread'::regclass,
		'zfgbb.message'::regclass,
		'zfgbb.message_history'::regclass,
		'zfgbb.content_resource'::regclass,
		'zfgbb.email_address'::regclass,
		'zfgbb.avatar'::regclass,
		'zfgbb.wiki_page'::regclass,
		'zfgbb.content_entity'::regclass
	)
order by con.conname
