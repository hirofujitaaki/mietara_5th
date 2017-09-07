# H2 UPPERCASEs all the table and column names, and Postgresql does the opposite.
# That is why double quotes are necessary to keep the case of those characters.
# https://stackoverflow.com/questions/27392232/use-h2-in-dev-and-postgresql-in-prod
# http://white-azalea.hatenablog.jp/entry/2017/03/09/233918

# CHARACTER VARYING vs VARCHAR
# if VARCHAR(n) is used without length specifier, the type accepts strings if any size
# https://stackoverflow.com/questions/1199468/what-is-the-difference-between-character-varying-and-varchar-in-postgresql

# about SERIAL
# https://stackoverflow.com/questions/18389537/does-postgresql-serial-work-differently

# --- !Ups

CREATE TABLE "users" (
  "user_id" VARCHAR NOT NULL PRIMARY KEY,
  "first_name" VARCHAR,
  "last_name" VARCHAR,
  "full_name" VARCHAR,
  "email" VARCHAR,
  "avatar_url" VARCHAR,
  "activated" BOOLEAN DEFAULT FALSE
);

CREATE TABLE "auth_tokens" (
  "id" VARCHAR PRIMARY KEY,
  "user_id" VARCHAR NOT NULL,
  "expiry" VARCHAR
);

CREATE TABLE "login_info" (
  "id" SERIAL PRIMARY KEY,
  "provider_id" VARCHAR NOT NULL,
  "provider_key" VARCHAR NOT NULL
);

CREATE TABLE "user_login_info" (
  "user_id" VARCHAR NOT NULL,
  "login_info_id" INTEGER NOT NULL
);

CREATE TABLE "password_info" (
  "user_id" VARCHAR NOT NULL,
  "password" VARCHAR NOT NULL,
  "salt" VARCHAR,
  "login_info_id" INTEGER NOT NULL
);

CREATE TABLE "oauth1_info" (
  "id" SERIAL PRIMARY KEY,
  "token" VARCHAR NOT NULL,
  "secret" VARCHAR NOT NULL,
  "login_info_id" INTEGER NOT NULL
);

CREATE TABLE "oauth2_info" (
  "id" SERIAL PRIMARY KEY,
  "access_token" VARCHAR NOT NULL,
  "token_type" VARCHAR,
  "expires_in" INTEGER,
  "refresh_token" VARCHAR,
  "login_info_id" INTEGER NOT NULL
);


# --- !Downs

DROP TABLE "oauth2_info";
DROP TABLE "oauth1_info";
DROP TABLE "password_info";
DROP TABLE "user_login_info";
DROP TABLE "login_info";
DROP TABLE "auth_tokens";
DROP TABLE "users";
