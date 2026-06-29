alter table zfgbb.user_refresh_token
    add column family_id varchar(36),
    add column successor_id integer references zfgbb.user_refresh_token(user_refresh_token_id) on delete set null;

update zfgbb.user_refresh_token set family_id = gen_random_uuid()::text where family_id is null;

alter table zfgbb.user_refresh_token alter column family_id set not null;

create index idx_user_refresh_token_family_id on zfgbb.user_refresh_token (family_id);
