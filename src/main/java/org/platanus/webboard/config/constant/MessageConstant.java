package org.platanus.webboard.config.constant;

/**
 * 메시지로 사용하는 상수
 */
public class MessageConstant {

    /* UserDetailsService */
    public static final String UDS_USER_NOT_FOUND = "loadUserByUsername : 사용자를 찾을 수 없음";
    public static final String UDS_USER_LOGIN = "사용자 로그인 : {}";

    /* Common */
    public static final String COMMON_DATABASE_ERROR = "정보 변경에 문제가 생겼습니다.";
    public static final String COMMON_DATABASE_DELETE_ERROR = "완전 삭제에 문제가 생겼습니다.";

    /* Board */
    public static final String BOARD_ALREADY_BOARD = "이름이 같은 게시판이 존재합니다.";
    public static final String BOARD_NOT_FOUND = "존재하지 않는 게시판 입니다.";


    /* Article */
    public static final String ARTICLE_NOT_FOUND = "게시글이 없습니다.";
    public static final String ARTICLE_NOT_AUTHOR = "작성자가 아닙니다";

    public static final String ARTICLE_ALREADY_DELETED = "이미 삭제된 게시물 입니다.";

    /* ArticleRecommend */
    public static final String ARTICLE_ALREADY_RECOMMEND = "이미 추천된 게시물 입니다";

    /* Comment */

    public static final String COMMENT_NOT_AUTHOR = "작성자가 아닙니다.";
    public static final String COMMENT_ALREADY_DELETED = "이미 삭제된 댓글 입니다.";
    public static final String COMMENT_NOT_FOUND = "없는 댓글 입니다.";

    /* FileStore */

    public static final String FILE_STORE_UPLOAD_ERROR_CODE = "파일 업로드 에러";
    public static final String FILE_STORE_UPLOAD_ERROR_MSG = "파일 업로드에 실패 했습니다.";

    public static final String FILE_STORE_DELETE_ERROR_CODE = "파일 삭제 에러";
    public static final String FILE_STORE_DELETE_ERROR_NOT_FOUND_ID = "파일 아이디가 없습니다.";
    public static final String FILE_STORE_DELETE_ERROR_NOT_FOUND_FILE = "파일이 존재하지 않습니다.";


}
