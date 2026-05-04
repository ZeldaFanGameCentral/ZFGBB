create index if not exists idx_message_history_migration_hash
    on zfgbb.message_history(migration_hash);
