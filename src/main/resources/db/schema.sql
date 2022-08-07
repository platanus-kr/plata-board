-- users Table Create SQL
CREATE TABLE IF NOT EXISTS users
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
CREATE TABLE `roles`
(
    `ROLENAME` VARCHAR(255) NOT NULL,
    `USER_ID`  INT          NOT NULL,
    CONSTRAINT PK_rolename_userid PRIMARY KEY (ROLENAME, USER_ID)
);


-- boards Table Create SQL
CREATE TABLE IF NOT EXISTS boards
(
    `ID`          INT          NOT NULL AUTO_INCREMENT,
    `NAME`        VARCHAR(255) NULL,
    `DESCRIPTION` VARCHAR(255) NULL,
    CONSTRAINT PK_boards PRIMARY KEY (ID)
);


-- articles Table Create SQL
CREATE TABLE IF NOT EXISTS articles
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
CREATE TABLE IF NOT EXISTS articles_recommend
(
    `ID`         INT NOT NULL AUTO_INCREMENT,
    `ARTICLE_ID` INT NULL,
    `USER_ID`    INT NULL,
    PRIMARY KEY (ID)
);


-- comments Table Create SQL
CREATE TABLE IF NOT EXISTS comments
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

