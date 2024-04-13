-- liquibase formatted sql

-- changeset liquibase:1

create sequence if not exists app_seq start with 1;

create table if not exists users (
id int default nextval('app_seq') primary key,
name varchar(100) not null,
phone_number varchar(20) not null unique,
password varchar(255) not null
);

create table if not exists users_roles (
user_id int not null references users(id) on delete cascade,
role varchar(20) not null default 'USER',
primary key (user_id, role)
);

create table if not exists applications (
id int generated by default as identity not null primary key,
description varchar(1000) not null,
phone_number varchar(20) not null,
name varchar(100) not null,
creators_id int not null references users(id) on delete cascade,
status varchar(20) not null default 'DRAFT',
create_date timestamp not null
);