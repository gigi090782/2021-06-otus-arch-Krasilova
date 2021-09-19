create table if not exists orders
(
    id                  bigint primary key generated always as identity,
    user_id             int                           not null,
    number              varchar(30)                   not null,
    price               int                           not null,
    created_date        timestamp                     null default current_timestamp,
    updated_date        timestamp                     null
    );



create table if not exists versions
(
    id                  bigint primary key generated always as identity,
    version             int                           not null default 0
    );