CREATE TABLE IF NOT EXISTS revision
(
    `id`   VARCHAR(255) NOT NULL PRIMARY KEY,
    `type` VARCHAR(55)  NOT NULL
);

CREATE TABLE IF NOT EXISTS revision_entry
(
    `id`          VARCHAR(255) NOT NULL PRIMARY KEY,
    `version`     BIGINT       NOT NULL,
    `revision_id` VARCHAR(255) NOT NULL,
    `operation`   VARCHAR(255) NOT NULL,
    `author`      VARCHAR(255) NOT NULL,
    `previous`    LONGTEXT,
    `after`       LONGTEXT,
    `diff`        LONGTEXT,
    `instant`     DATETIME     NOT NULL,
    CONSTRAINT `fk_revision_entry_revision`
        FOREIGN KEY (revision_id) REFERENCES revision (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);
