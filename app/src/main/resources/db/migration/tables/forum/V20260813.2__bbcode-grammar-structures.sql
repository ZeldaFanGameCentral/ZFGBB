drop table zfgbb.attribute_data_type;
drop sequence if exists zfgbb.attribute_data_type_attribute_data_type_id_seq;

create table zfgbb.attribute_data_type (
	code text not null primary key,
	label text not null,
	ordinal integer not null default 0,
	validation_pattern text,
	fallback_value text,
	value_admits_whitespace boolean not null default false,
	lowercases_value boolean not null default false,
	bare_integer_unit text,
	allowed_values text
);

alter table zfgbb.attribute_data_type owner to zfgcadmin;

alter table zfgbb.bb_code_attribute
	drop column attribute_data_type;

alter table zfgbb.bb_code_attribute
	add column attribute_data_type text not null;

alter table zfgbb.bb_code_attribute
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

alter table zfgbb.message
	add column guest_author_name text;

alter table zfgbb.bb_code_config
	add column source_reference_attribute text,
	add column source_reference_resolver text;

alter table zfgbb.bb_code_config
	add constraint ck_bb_code_config_source_reference_paired
	check ((source_reference_attribute is null) = (source_reference_resolver is null));

create table zfgbb.attribute_semantic_role (
	code text not null primary key,
	label text not null,
	ordinal integer not null default 0
);

insert into zfgbb.attribute_semantic_role (code, label, ordinal) values
	('DESTINATION', 'Link or image destination', 1),
	('LIST_STYLE', 'List style', 2),
	('WIDTH', 'Width', 3),
	('HEIGHT', 'Height', 4);

alter table zfgbb.bb_code_attribute
	add column semantic_role text,
	add constraint fk_bb_code_attribute_semantic_role
		foreign key (semantic_role)
		references zfgbb.attribute_semantic_role(code);

alter table zfgbb.bb_code_attribute_mode
	add column content_semantic_role text,
	add constraint fk_bb_code_attribute_mode_content_semantic_role
		foreign key (content_semantic_role)
		references zfgbb.attribute_semantic_role(code);

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

alter table zfgbb.bb_code_config
	add column honoured_in_forum_flag boolean default true,
	add column honoured_in_wiki_flag boolean default true,
	add column honoured_in_project_flag boolean default true,
	add column honoured_in_resource_flag boolean default true,
	add column honoured_in_signature_flag boolean default true;
