create or replace function zfgbb.set_current_msg_before_insert()
returns trigger as $$
begin
	if new.current_flag then
		update zfgbb.message_history
		set current_flag = false
		where message_id = new.message_id and current_flag;
	end if;
	return new;
end;
$$ language plpgsql;

drop trigger if exists trigger_message_history_after_insert on zfgbb.message_history;
drop trigger if exists trigger_message_history_before_insert on zfgbb.message_history;
create trigger trigger_message_history_before_insert
before insert on zfgbb.message_history
for each row execute procedure zfgbb.set_current_msg_before_insert();

--drop trigger if exists trigger_message_updated_ts on zfgbb.message;
