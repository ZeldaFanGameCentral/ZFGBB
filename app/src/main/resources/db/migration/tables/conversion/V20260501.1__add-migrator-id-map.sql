create table zfgbb.migrator_id_map (
	migrator_id_map_id bigint generated always as identity primary key,
	entity_type varchar(50) not null,
	legacy_id integer not null,
	zfgbb_id integer not null,
	created_ts timestamp not null default current_timestamp,
	unique (entity_type, legacy_id)
);

create index idx_migrator_id_map_lookup on zfgbb.migrator_id_map (entity_type, legacy_id);
