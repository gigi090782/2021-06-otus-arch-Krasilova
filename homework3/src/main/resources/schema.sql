create table if not exists users
(
    id                  bigint primary key generated always as identity,
    user_name           varchar(256)                  not null,
    first_name          varchar(128)                  not null,
    last_name           varchar(128)                  not null,
    phone               varchar(128)                  null,
    email               varchar(128)                  null
    );
