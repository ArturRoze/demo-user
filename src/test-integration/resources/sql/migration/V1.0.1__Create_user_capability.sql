CREATE TABLE user_capability
(
    id              BIGSERIAL    NOT NULL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    app_user_id     BIGINT       NOT NULL REFERENCES app_user (id) ON DELETE CASCADE,

    UNIQUE (name, app_user_id)
);
