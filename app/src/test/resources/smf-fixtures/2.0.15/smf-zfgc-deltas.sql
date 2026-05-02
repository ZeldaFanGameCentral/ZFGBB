alter table smf_1boards
    add column countMoney tinyint not null default 0,
    add column is_redirect tinyint not null default 0,
    add column redirect_clicks int unsigned not null default 0,
    add column redirect_count_clicks tinyint not null default 0,
    add column redirect_target text,
    add column redirect_url text;

alter table smf_1members
    add column is_spammer tinyint not null default 0,
    add column warnLevel tinyint not null default 0,
    modify column warning int not null default 0;

alter table smf_1polls
    add column ID_TOPIC int unsigned not null default 0;

alter table smf_1log_karma
    add column is_read tinyint not null default 0,
    add column description text,
    add column link text;

alter table smf_1messages
    add column description text;

create table smf_1messages_history (
    id_edit int unsigned not null auto_increment,
    id_msg int unsigned not null default 0,
    modified_name varchar(255) not null default '',
    modified_time int unsigned not null default 0,
    body text,
    primary key (id_edit),
    key id_msg (id_msg)
) engine=MyISAM;
