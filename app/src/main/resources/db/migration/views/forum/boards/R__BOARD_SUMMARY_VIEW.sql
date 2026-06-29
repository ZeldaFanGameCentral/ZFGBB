create or replace view zfgbb.board_summary as
with pc as (
	select m.board_id, count(*) as post_count
	from zfgbb.message m
	group by m.board_id
),
tc as (
	select board_id, count(*) as thread_count
	from zfgbb.thread
	group by board_id
)
select b.board_id,
       b.description,
       b.board_name,
       coalesce(tc.thread_count, 0) as thread_count,
       coalesce(pc.post_count, 0)   as post_count,
       lp.message_id                as latest_message_id,
       lp.thread_id                 as latest_thread_id,
       lp.owner_id                  as latest_message_owner_id,
       lp.display_name              as latest_message_user_name,
       lp.created_ts                as latest_message_created_ts,
       b.category_id,
       b.parent_board_id,
       lp.thread_name
from zfgbb.board b
left join tc on tc.board_id = b.board_id
left join pc on pc.board_id = b.board_id
left join lateral (
    select m.message_id, m.owner_id, coalesce(u.display_name, '[deleted]') as display_name, m.created_ts, t.thread_id, t.thread_name
    from zfgbb.message m
    join zfgbb.thread t on t.thread_id = m.thread_id
    left join zfgbb.user u on u.user_id = m.owner_id
    where m.board_id = b.board_id
    order by m.created_ts desc
    limit 1
) lp on true;
