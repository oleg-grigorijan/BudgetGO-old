create table if not exists storages
(
    id   int auto_increment
        primary key,
    name varchar(40) not null
)
    charset = utf8mb4;

create table if not exists users
(
    id            int auto_increment
        primary key,
    email         varchar(255) not null,
    name          varchar(40)  null,
    surname       varchar(40)  null,
    password_hash varchar(255) not null,
    password_salt varchar(255) not null,
    constraint users_login_uindex
        unique (email)
)
    charset = utf8mb4;

create table if not exists operations
(
    id            int auto_increment
        primary key,
    storage_id    int         not null,
    money_delta   int         not null,
    date          date        not null,
    description   varchar(80) null,
    creation_date date        not null,
    creator_id    int         not null,
    constraint operations_storages_id_fk
        foreign key (storage_id) references storages (id)
            on update cascade on delete cascade,
    constraint operations_users_id_fk
        foreign key (creator_id) references users (id)
)
    charset = utf8mb4;

create table if not exists users_storages
(
    user_id    int not null,
    storage_id int not null,
    constraint users_storages_storages_id_fk
        foreign key (storage_id) references storages (id)
            on update cascade on delete cascade,
    constraint users_storages_users_id_fk
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
)
    charset = utf8mb4;