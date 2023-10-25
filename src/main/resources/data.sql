create table if not exists test
(
    id      varchar(255) not null,
    name    varchar(255) not null,
    has_err boolean      not null default false,
    err_msg varchar(255),
    primary key (id)
);
