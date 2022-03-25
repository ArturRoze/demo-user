CREATE TABLE app_user
(
    id                 BIGSERIAL NOT NULL PRIMARY KEY,
    login              VARCHAR(255) NOT NULL UNIQUE,
    password           VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL UNIQUE,
    user_role          VARCHAR(30) NOT NULL
);