alter table zfgbb.attribute_data_type
	add column validation_pattern text,
	add column fallback_value text;

update zfgbb.attribute_data_type
set validation_pattern = '^\d+$', fallback_value = ''
where code = 'INTEGER';

update zfgbb.attribute_data_type
set validation_pattern = '^\d+(?:\.\d+)?(?:px|pt|em|rem|%)?$', fallback_value = ''
where code = 'DIMENSION';

update zfgbb.attribute_data_type
set validation_pattern = '^[A-Za-z0-9_-]+$', fallback_value = ''
where code = 'IDENTIFIER';

update zfgbb.attribute_data_type
set validation_pattern = '^[A-Za-z0-9 ,''"_-]+$', fallback_value = ''
where code = 'FONT_NAME';
