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
