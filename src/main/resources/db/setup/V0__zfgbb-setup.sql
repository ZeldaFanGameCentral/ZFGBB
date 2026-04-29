do $$ begin
  if not exists (select from pg_roles where rolname = 'zfgcadmin') then
    create role zfgcadmin nosuperuser nocreatedb nocreaterole inherit;
  end if;
end $$;

grant zfgcadmin to current_user;

create schema if not exists zfgbb;
alter schema zfgbb owner to zfgcadmin;
grant usage, create on schema zfgbb to current_user;
