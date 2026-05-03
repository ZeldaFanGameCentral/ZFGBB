create or replace view zfgbb.user_karma_view as
select k.karma_id,
       u.user_id as receiving_user_id,
       m.message_id,
       k.description,
       k.is_positive,
       k.created_ts as karma_given_ts,
       k.commenting_user_id,
       m.thread_id,
       cu.display_name as commenting_user
from zfgbb.karma k
left join zfgbb.message m on m.message_id = k.message_id
join zfgbb.user u on u.user_id = m.owner_id
join zfgbb.user cu on cu.user_id = k.commenting_user_id