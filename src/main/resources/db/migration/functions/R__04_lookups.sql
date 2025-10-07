create or replace function zfgbb.create_content_types(content_type_id int, code text, p_description text) 
returns void
language plpgsql
as $$
begin
	
	insert into zfgbb.content_resource_type(content_resource_type_id, content_code, description)
	values(content_type_id, code, p_description)
	on conflict (content_resource_type_id)
	do update set content_code = code, description = p_description;
	
	return;
	
end; $$;

select zfgbb.create_content_types(1, 'AVR', 'Avatar');
select zfgbb.create_content_types(2, 'ATC', 'Attachment');

create or replace function zfgbb.create_gender_lkup(
    p_code text,
    p_description text,
    p_seqno integer
) returns void as $$
begin
    insert into zfgbb.gender_lkup (code, description, seqno)
    values (p_code, p_description, p_seqno)
    on conflict (upper(code))
    do update
    set
        description = excluded.description,
        seqno = excluded.seqno,
        updated_ts = current_timestamp;
end;
$$ language plpgsql;

select zfgbb.create_gender_lkup('M', 'Male', 0);
select zfgbb.create_gender_lkup('F', 'Female', 1);
select zfgbb.create_gender_lkup('NB', 'Non-Binary', 2);
select zfgbb.create_gender_lkup('NA', 'Other/Prefer not to say', 3);
