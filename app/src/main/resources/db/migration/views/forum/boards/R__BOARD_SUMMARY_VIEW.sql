create or replace view zfgbb.board_summary as
with board_stats as (
    select t.board_id,
           count(distinct t.thread_id) as thread_count,
           count(m.message_id) as post_count
    from zfgbb.thread t
    join zfgbb.message m on m.thread_id = t.thread_id
    group by t.board_id
),
latest_per_board as (
    select board_id, message_id, owner_id, display_name, created_ts, thread_id, thread_name
    from (
        select t.board_id,
               m.message_id,
               m.owner_id,
               u.display_name,
               m.created_ts,
               t.thread_id,
               t.thread_name,
               row_number() over (partition by t.board_id order by m.created_ts desc) as rn
        from zfgbb.thread t
        join zfgbb.message m on m.thread_id = t.thread_id
        join zfgbb.user u on u.user_id = m.owner_id
    ) ranked
    where rn = 1
)
select b.board_id,
       b.description,
       b.board_name,
       coalesce(s.thread_count, 0) as thread_count,
       coalesce(s.post_count, 0) as post_count,
       lp.message_id as latest_message_id,
       lp.thread_id as latest_thread_id,
       lp.owner_id as latest_message_owner_id,
       lp.display_name as latest_message_user_name,
       lp.created_ts as latest_message_created_ts,
       b.category_id,
       b.parent_board_id,
       lp.thread_name
from zfgbb.board b
left join board_stats s on s.board_id = b.board_id
left join latest_per_board lp on lp.board_id = b.board_id
