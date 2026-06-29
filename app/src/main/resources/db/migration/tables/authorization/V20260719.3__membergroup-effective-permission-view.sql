create or replace view zfgbb.user_permission_view as
select u.user_id, p.permission_id, p.permission_code
from zfgbb.br_user_permission u
join zfgbb.permission p on p.permission_id = u.user_permission_id
union
select upga.user_id, p.permission_id, p.permission_code
from zfgbb.user_permission_group_assoc upga
join zfgbb.permission_group_assoc pga on pga.permission_group_id = upga.permission_group_id
join zfgbb.permission p on p.permission_id = pga.permission_id;
