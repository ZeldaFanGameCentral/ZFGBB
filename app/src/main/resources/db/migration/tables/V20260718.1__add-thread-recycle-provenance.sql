alter table zfgbb.thread add column if not exists recycled_from_board_id integer;
alter table zfgbb.thread add column if not exists recycled_from_thread_id integer;

do $$
begin
	if not exists (
		select 1 from pg_constraint
		where conname = 'fk_thread_recycled_from_board_id'
			and conrelid = 'zfgbb.thread'::regclass) then
		alter table zfgbb.thread
			add constraint fk_thread_recycled_from_board_id
			foreign key (recycled_from_board_id) references zfgbb.board (board_id) on delete set null;
	end if;
	if not exists (
		select 1 from pg_constraint
		where conname = 'fk_thread_recycled_from_thread_id'
			and conrelid = 'zfgbb.thread'::regclass) then
		alter table zfgbb.thread
			add constraint fk_thread_recycled_from_thread_id
			foreign key (recycled_from_thread_id) references zfgbb.thread (thread_id) on delete set null;
	end if;
end
$$;

create index if not exists idx_thread_recycled_from_thread_id
	on zfgbb.thread (recycled_from_thread_id)
	where recycled_from_thread_id is not null;
