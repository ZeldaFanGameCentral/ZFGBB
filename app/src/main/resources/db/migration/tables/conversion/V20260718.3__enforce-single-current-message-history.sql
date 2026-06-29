with ranked_current_revisions as (
	select message_history_id,
		row_number() over (
			partition by message_id
			order by created_ts desc, message_history_id desc
		) as newest_rank
	from zfgbb.message_history
	where current_flag
)
update zfgbb.message_history h
set current_flag = false
from ranked_current_revisions r
where h.message_history_id = r.message_history_id
	and r.newest_rank > 1;

drop trigger if exists trigger_message_history_after_insert on zfgbb.message_history;
drop function if exists zfgbb.set_current_msg_after_insert();

create or replace function zfgbb.set_current_msg_before_insert()
returns trigger as $$
begin
	if new.current_flag then
		update zfgbb.message_history
		set current_flag = false
		where message_id = new.message_id
			and current_flag;
	end if;
	return new;
end;
$$ language plpgsql;

drop trigger if exists trigger_message_history_before_insert on zfgbb.message_history;
create trigger trigger_message_history_before_insert
before insert on zfgbb.message_history
for each row execute procedure zfgbb.set_current_msg_before_insert();

create unique index if not exists ux_message_history_current
	on zfgbb.message_history (message_id)
	where current_flag;
