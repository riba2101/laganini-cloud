CREATE TABLE IF NOT EXISTS test
(
    `id`         BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(255),
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL
);
