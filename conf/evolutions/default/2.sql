# --- !Ups

CREATE TABLE blogs (
  id SERIAL NOT NULL,
  title VARCHAR NOT NULL,
  content VARCHAR NOT NULL,
  user_id VARCHAR NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);


# --- !Downs

DROP TABLE blogs;

