--liquibase formatted sql
--changeset shulika:1


--rollback DROP TABLE category;

--changeset shulika:2
-- CREATE TABLE IF NOT EXISTS Procedure
-- (
--     id          int primary key generated ALWAYS AS IDENTITY,
--     category_id int references Category (id),
--     name        varchar(256) not null unique,
--     enabled     bool         not null default true,
--     created_at  timestamp    not null default CURRENT_TIMESTAMP,
--     updated_at  timestamp    not null default CURRENT_TIMESTAMP
-- );
--rollback DROP TABLE procedure;

