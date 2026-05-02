alter table zfgbb.user
add column password_hash varchar(255),
add column password_algo varchar(32),
add column password_salt varchar(64);
