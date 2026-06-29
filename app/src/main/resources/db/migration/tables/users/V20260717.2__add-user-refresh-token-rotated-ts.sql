alter table zfgbb.user_refresh_token add column if not exists rotated_ts timestamptz;
