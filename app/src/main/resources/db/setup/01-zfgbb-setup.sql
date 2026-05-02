do $$ begin
  if not exists (select from pg_roles where rolname = 'zfgcadmin') then
    create role zfgcadmin nosuperuser nocreatedb nocreaterole inherit;
  end if;
  if not exists (select from pg_roles where rolname = 'zfgbb_user') then
    create role zfgbb_user login password '123456' nosuperuser nocreatedb nocreaterole inherit;
  end if;
end $$;

do $$
declare r record;
begin
  for r in select rolname from pg_roles
           where rolname in ('zfgbb_user', 'postgres', 'test') loop
    execute format('grant zfgcadmin to %I', r.rolname);
  end loop;
end $$;

create schema if not exists zfgbb;
alter schema zfgbb owner to zfgcadmin;
grant usage, create on schema zfgbb to zfgbb_user;

alter default privileges for role zfgbb_user in schema zfgbb grant all on tables    to zfgbb_user;
alter default privileges for role zfgbb_user in schema zfgbb grant all on sequences to zfgbb_user;
alter default privileges for role zfgbb_user in schema zfgbb grant all on functions to zfgbb_user;
