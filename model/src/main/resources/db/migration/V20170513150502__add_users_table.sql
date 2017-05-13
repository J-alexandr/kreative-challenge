create table users
(
  id serial not null primary key,
  uuid varchar(255) not null unique,
  first_name varchar(255),
  last_name varchar(255),
  created bigint not null,
  enabled boolean default true not null
)