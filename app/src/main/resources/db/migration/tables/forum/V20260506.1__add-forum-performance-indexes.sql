create index if not exists idx_thread_board_id on zfgbb.thread(board_id);
create index if not exists idx_message_thread_id on zfgbb.message(thread_id);
create index if not exists idx_message_thread_id_created_ts on zfgbb.message(thread_id, created_ts desc);
create index if not exists idx_message_owner_id on zfgbb.message(owner_id);
create index if not exists idx_message_created_ts on zfgbb.message(created_ts desc);
