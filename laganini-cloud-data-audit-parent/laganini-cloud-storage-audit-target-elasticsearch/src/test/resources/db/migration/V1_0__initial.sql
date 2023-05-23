CREATE TABLE IF NOT EXISTS dummy
(
    `id`         INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(55),
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL
);
