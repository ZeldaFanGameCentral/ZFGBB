create table zfgbb.system_config (
    config_key   text not null primary key,
    config_value text,
    created_ts   timestamp without time zone default current_timestamp not null,
    updated_ts   timestamp without time zone default current_timestamp not null
);

insert into zfgbb.system_config (config_key, config_value) values
  ('installed', 'false');
