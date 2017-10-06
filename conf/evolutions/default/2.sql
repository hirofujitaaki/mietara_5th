# --- !Ups

CREATE TABLE blogs (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  title VARCHAR NOT NULL,
  content VARCHAR NOT NULL,
  user_id VARCHAR NOT NULL,
  created_at VARCHAR NOT NULL
);


# --- !Downs

DROP TABLE blogs;

