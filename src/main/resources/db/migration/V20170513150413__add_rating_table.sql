create table rating
(
  user_id integer not null,
  challenge_id integer not null,
  rate integer not null,
  unique (user_id, challenge_id)
)