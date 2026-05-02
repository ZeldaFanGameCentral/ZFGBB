alter table zfgbb.category
add column if not exists category_order smallint not null default 0;
