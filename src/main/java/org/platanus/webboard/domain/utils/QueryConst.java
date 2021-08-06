package org.platanus.webboard.domain.utils;

public class QueryConst {

    public static final String BOARD_DELETE = "delete from BOARDS where ID = ?";
    public static final String BOARD_UPDATE = "update BOARDS set NAME = ?, DESCRIPTION = ?  where ID = ?";
    public static final String BOARD_FIND_ALL = "select * from BOARDS";
    public static final String BOARD_FIND_BY_ID = "select * from BOARDS where ID = ?";
    public static final String BOARD_FIND_BY_NAME = "select * from BOARDS where NAME = ?";
    public static final String ARTICLE_DELETE = "delete from ARTICLES where ID = ?";
    public static final String ARTICLE_UPDATE = "update ARTICLES set TITLE = ?, CONTENT = ?, MODIFIED_DATE = ? where ID = ?";
    public static final String ARTICLE_UPDATE_DELETE_FLAG = "update ARTICLES set DELETED = ? where ID = ?";
    public static final String ARTICLE_FIND_BY_ID = "select * from ARTICLES where ID = ?";
    public static final String ARTICLE_FIND_BY_BOARD_ID = "select * from ARTICLES where BOARD_ID = ?";
    public static final String ARTICLE_FIND_ALL = "select * from ARTICLES";
    public static final String ARTICLE_FIND_BY_AUTHOR_ID = "select * from ARTICLES where AUTHOR_ID = ?";
    public static final String ARTICLE_FIND_BY_TITLE = "select * from ARTICLES where TITLE like ?";
    public static final String ARTICLE_FIND_BY_CONTENT = "select * from ARTICLES where CONTENT like ?";
    public static final String ARTICLE_FIND_BY_TITLE_AND_CONTENT = "select * from ARTICLES where TITLE like ? or CONTENT like ?";
    public static final String COMMENT_DELETE = "delete from COMMENTS where ID = ?";
    public static final String COMMENT_UPDATE = "update COMMENTS set CONTENT = ?, MODIFIED_DATE=? where ID = ?";
    public static final String COMMENT_UPDATE_DELETE_FLAG = "update COMMENTS set DELETED = ? where ID = ?";
    public static final String COMMENT_FIND_BY_ID = "select * from COMMENTS where ID = ?";
    public static final String COMMENT_FIND_BY_ARTICLE_ID = "select * from COMMENTS where ARTICLE_ID = ?";
    public static final String COMMENT_FIND_BY_CONTENT = "select * from COMMENTS where CONTENT like ?";
    public static final String COMMENT_FIND_ALL = "select * from COMMENTS";
    public static final String USER_DELETE = "delete from USERS where ID = ?";
    public static final String USER_UPDATE = "update users set USERNAME =?, PASSWORD = ?, NICKNAME = ?, EMAIL = ? where ID = ?";
    public static final String USER_UPDATE_DELETE_FLAG = "update USERS set DELETED = ? where ID = ?";
    public static final String USER_FIND_BY_ID = "select * from USERS where ID = ?";
    public static final String USER_FIND_BY_USERNAME = "select * from USERS where USERNAME = ?";
    public static final String USER_FIND_BY_EMAIL = "select * from USERS where EMAIL = ?";
    public static final String USER_FIND_BY_NICKNAME = "select * from USERS where NICKNAME = ?";
    public static final String USER_FIND_ALL = "select * from USERS";
}
