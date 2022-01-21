CREATE TABLE types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE rules
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE accidents
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    text    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    type_id INTEGER NOT NULL REFERENCES types (id)
);

CREATE TABLE accident_rules
(
    accident_id INTEGER NOT NULL REFERENCES accident (id) ON DELETE CASCADE,
    rules_id    INTEGER NOT NULL REFERENCES rules (id)
);

CREATE TABLE authorities (
                             id serial primary key,
                             authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
                       id serial primary key,
                       username VARCHAR(50) NOT NULL unique,
                       password VARCHAR(100) NOT NULL,
                       enabled boolean default true,
                       authority_id int not null references authorities(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$uVsTlX3wAlu5.t3iD7.ws.tAX4QJgm8PB9l60PkX2svsF1EmcLd/2',
        (select id from authorities where authority = 'ROLE_ADMIN'));