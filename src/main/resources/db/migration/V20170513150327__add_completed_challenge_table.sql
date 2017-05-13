create table complete_challenge
(
  user_id integer not null,
  challenge_id integer not null,
  unique (user_id, challenge_id)
)