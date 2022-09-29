CREATE TABLE IF NOT EXISTS revision
(
    `id`   VARCHAR(55) PRIMARY KEY,
    `type` VARCHAR(55)
);
CREATE TABLE IF NOT EXISTS revision_entry
(
    `id`          VARCHAR(65) PRIMARY KEY,
    `version`     BIGINT,
    `revision_id` VARCHAR(55),
    `operation`   VARCHAR(55),
    `author`      VARCHAR(55),
    `previous`    MEDIUMTEXT,
    `after`       MEDIUMTEXT,
    `diff`        MEDIUMTEXT,
    `instant`     DATETIME
);

CREATE TABLE IF NOT EXISTS dummy
(
    `id`         INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(55),
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL
);
