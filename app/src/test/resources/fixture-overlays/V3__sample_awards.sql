insert into zfgbb.user_award (
	user_award_id, award_id, user_id, granted_by_user_id, reason, granted_ts)
values
	(1, 1, 3, 1, 'ALTTP tileset project spotlight',
		'2026-07-26 07:46:35.432262+00'::timestamptz),
	(2, 3, 5, 1, 'Always answering newbie questions',
		'2026-07-26 07:46:35.432262+00'::timestamptz)
on conflict do nothing;

select setval(
	pg_get_serial_sequence('zfgbb.user_award', 'user_award_id'),
	greatest((select coalesce(max(user_award_id), 0) from zfgbb.user_award), 1),
	true);
