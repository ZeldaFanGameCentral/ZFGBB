alter table zfgbb.attribute_data_type
	add column value_admits_whitespace boolean not null default false,
	add column lowercases_value boolean not null default false,
	add column bare_integer_unit text,
	add column allowed_values text;

create table zfgbb.attribute_value_mapping (
	attribute_value_mapping_id serial primary key,
	attribute_data_type text not null,
	from_value text not null,
	to_value text not null,
	constraint uq_attribute_value_mapping_from_value unique (attribute_data_type, from_value),
	constraint fk_attribute_value_mapping_data_type
		foreign key (attribute_data_type)
		references zfgbb.attribute_data_type(code) on delete cascade
);

create table zfgbb.list_style_type (
	code text not null primary key,
	label text not null,
	ordinal integer not null default 0,
	numbers_items boolean not null default false
);

create table zfgbb.markdown_equivalent (
	code text not null primary key,
	label text not null,
	ordinal integer not null default 0
);

alter table zfgbb.bb_code_config
	add column markdown_equivalent text,
	add column markdown_canonical_flag boolean default false,
	add column implicit_item_marker text,
	add column implicit_item_code text,
	add constraint fk_bb_code_config_markdown_equivalent
		foreign key (markdown_equivalent)
		references zfgbb.markdown_equivalent(code);
