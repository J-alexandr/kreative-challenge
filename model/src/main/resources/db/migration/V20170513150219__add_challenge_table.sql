create table challenge
(
  id serial not null primary key,
  title varchar(1000) not null,
  description varchar(10000),
  longitude double precision,
  latitude double precision,
  creator_id integer not null,
  created bigint,
  hidden boolean
)