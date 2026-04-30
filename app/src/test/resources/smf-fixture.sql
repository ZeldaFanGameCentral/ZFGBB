create table smf_1categories (
    id_cat tinyint not null,
    cat_order tinyint not null default 0,
    name varchar(255) not null default '',
    can_collapse bit not null default b'1',
    primary key (id_cat)
);

insert into smf_1categories (id_cat, cat_order, name, can_collapse) values
    (1, 0, 'Test Category Alpha', b'1'),
    (2, 1, 'Test Category Beta', b'1');
