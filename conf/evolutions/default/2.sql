# --- !Ups

CREATE TABLE blogs (
  id SERIAL NOT NULL,
  title VARCHAR,
  content VARCHAR,
  user_id VARCHAR NOT NULL,
  created_at VARCHAR,
  updated_at VARCHAR
);


# --- !Downs

DROP TABLE blogs;

