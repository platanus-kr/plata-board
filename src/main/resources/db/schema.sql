-- DROP TABLE users;
-- DROP TABLE roles;
-- DROP TABLE boards;
-- DROP TABLE articles;
-- DROP TABLE articles_recommend;
-- DROP TABLE files;


-- users Table Create SQL
CREATE TABLE IF NOT EXISTS `users`
(
    `ID`       INT          NOT NULL AUTO_INCREMENT,
    `USERNAME` VARCHAR(255) NULL,
    `PASSWORD` VARCHAR(255) NULL,
    `NICKNAME` VARCHAR(255) NULL,
    `EMAIL`    VARCHAR(255) NULL,
    `DELETED`  TINYINT      NULL,
    `ROLE`     VARCHAR(255) NULL,
    CONSTRAINT PK_users PRIMARY KEY (ID)
);

-- role Table Create SQL
CREATE TABLE IF NOT EXISTS `roles`
(
    `ID`       INT         NOT NULL AUTO_INCREMENT,
    `ROLENAME` VARCHAR(50) NOT NULL,
    `USER_ID`  INT         NOT NULL,
    CONSTRAINT PK_rolename_userid PRIMARY KEY (ROLENAME, USER_ID)
);

-- boards Table Create SQL
CREATE TABLE IF NOT EXISTS `boards`
(
    `ID`          INT          NOT NULL AUTO_INCREMENT,
    `NAME`        VARCHAR(255) NULL,
    `DESCRIPTION` VARCHAR(255) NULL,
    CONSTRAINT PK_boards PRIMARY KEY (ID)
);

-- articles Table Create SQL
CREATE TABLE IF NOT EXISTS `articles`
(
    `ID`            INT          NOT NULL AUTO_INCREMENT,
    `BOARD_ID`      INT          NULL,
    `CONTENT`       TEXT         NULL,
    `AUTHOR_ID`     INT          NULL,
    `CREATED_DATE`  DATETIME     NULL,
    `MODIFIED_DATE` DATETIME     NULL,
    `DELETED`       TINYINT      NULL,
    `TITLE`         VARCHAR(255) NULL,
    `RECOMMEND`     INT          NULL,
    `VIEW_COUNT`    INT          NULL,
    CONSTRAINT PK_articles PRIMARY KEY (ID)
);

-- articles_recommend Table Create SQL
CREATE TABLE IF NOT EXISTS `articles_recommend`
(
    `ID`         INT NOT NULL AUTO_INCREMENT,
    `ARTICLE_ID` INT NULL,
    `USER_ID`    INT NULL,
    CONSTRAINT PK_articles_recommend PRIMARY KEY (ID)
);

-- comments Table Create SQL
CREATE TABLE IF NOT EXISTS `comments`
(
    `ID`            INT      NOT NULL AUTO_INCREMENT,
    `ARTICLE_ID`    INT      NULL,
    `CONTENT`       TEXT     NULL,
    `AUTHOR_ID`     INT      NULL,
    `CREATED_DATE`  DATETIME NULL,
    `MODIFIED_DATE` DATETIME NULL,
    `DELETED`       TINYINT  NULL,
    `RECOMMEND`     INT      NULL,
    CONSTRAINT PK_comments PRIMARY KEY (ID)
);

-- files Table Create SQL
CREATE TABLE IF NOT EXISTS `files`
(
    `ID`                  INT          NOT NULL AUTO_INCREMENT,
    `USER_ID`             INT          NOT NULL,
    `ORIGINAL_FILENAME`   VARCHAR(255) NULL,
    `ORIGINAL_EXTENSION`  VARCHAR(128) NULL,
    `MANAGEMENT_FILENAME` VARCHAR(255) NULL,
    `STORE_PATH_PREFIX`   TEXT         NULL,
    `SIZE`                INT          NULL,
    `CREATE_DATE`         DATETIME     NULL,
    `UPDATE_DATE`         DATETIME     NULL,
    `DELETED`             TINYINT      NULL,
    `EXPIRE_DATE`         DATETIME     NULL,
    CONSTRAINT PK_files PRIMARY KEY (ID)
);

-- CREATE INDEX IDX_files_by_management_filename ON files
--    (`MANAGEMENT_FILENAME`);

-- CREATE INDEX IDX_files_by_user_id ON files
--    (`USER_ID`);
