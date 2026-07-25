drop table if exists zfgbb.attribute_data_type;
drop sequence if exists zfgbb.attribute_data_type_attribute_data_type_id_seq;

create table zfgbb.attribute_data_type (
	code text not null primary key,
	label text not null,
	ordinal integer not null default 0
);

alter table if exists zfgbb.attribute_data_type owner to zfgcadmin;

insert into zfgbb.attribute_data_type (code, label, ordinal) values
	('TIMESTAMP', 'Date/time', 1),
	('TEXT', 'Plain text', 2),
	('COLOR', 'Colour', 3),
	('INTEGER', 'Whole number', 4),
	('URL', 'Link', 5),
	('IDENTIFIER', 'Identifier', 6),
	('FONT_NAME', 'Font name', 7),
	('LIST_TYPE', 'List style', 8),
	('DIMENSION', 'Dimension', 9),
	('SIZE', 'Legacy size level', 10);

alter table zfgbb.bb_code_attribute
	add column attribute_data_type_code text;

update zfgbb.bb_code_attribute set attribute_data_type_code = case attribute_data_type
	when 0 then 'TIMESTAMP'
	when 1 then 'TEXT'
	when 2 then 'COLOR'
	when 3 then 'INTEGER'
	when 4 then 'URL'
	when 5 then 'IDENTIFIER'
	when 6 then 'FONT_NAME'
	when 7 then 'LIST_TYPE'
	when 8 then 'DIMENSION'
	when 9 then 'SIZE'
	else null
end;

do $$
declare
	offending record;
begin
	select bb_code_attribute_id, attribute_data_type into offending
	from zfgbb.bb_code_attribute
	where attribute_data_type_code is null
	limit 1;
	if found then
		raise exception 'bb_code_attribute % declares attribute_data_type ordinal % which has no lookup code',
			offending.bb_code_attribute_id, offending.attribute_data_type;
	end if;
end $$;

alter table zfgbb.bb_code_attribute
	drop column attribute_data_type;

alter table zfgbb.bb_code_attribute
	rename column attribute_data_type_code to attribute_data_type;

alter table zfgbb.bb_code_attribute
	alter column attribute_data_type set not null,
	add constraint fk_bb_code_attribute_data_type
		foreign key (attribute_data_type)
		references zfgbb.attribute_data_type(code);

alter table zfgbb.bb_code_attribute_mode
	add constraint fk_bb_code_attribute_mode_config
		foreign key (bb_code_config_id)
		references zfgbb.bb_code_config(bb_code_config_id) on delete cascade;

alter table zfgbb.bb_code_attribute
	add constraint fk_bb_code_attribute_mode
		foreign key (bb_code_attribute_mode_id)
		references zfgbb.bb_code_attribute_mode(bb_code_attribute_mode_id) on delete cascade;
