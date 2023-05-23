CREATE TABLE IF NOT EXISTS base
(
    `id`   BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS temporal
(
    `id`         BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(255),
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL
    );

CREATE TABLE IF NOT EXISTS owned
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`     VARCHAR(255),
    `owner_id` BIGINT NOT NULL
    );

CREATE TABLE IF NOT EXISTS purgeable
(
    `id`         BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(255),
    `deleted_at` DATETIME NOT NULL
    );
