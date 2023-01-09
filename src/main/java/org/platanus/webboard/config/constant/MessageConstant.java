package org.platanus.webboard.config.constant;

/**
 * 메시지로 사용하는 상수
 */
public class MessageConstant {

    /* UserService */
    public static final String USER_JOIN_FAILED = "회원가입에 실패 했습니다.";
    public static final String USER_ALREADY_USE_NICKNAME_LOG = "User join #{}: 이미 존재하는 닉네임 입니다. - {}";
    public static final String USER_ALREADY_USE_NICKNAME = "이미 존재하는 닉네임 입니다.";
    public static final String USER_ALREADY_USE_USERNAME_LOG = "User join #{}: 이미 존재하는 아이디 입니다.";
    public static final String USER_ALREADY_USE_USERNAME = "이미 존재하는 아이디 입니다.";
    public static final String USER_ALREADY_USE_EMAIL_LOG = "User join #{}: 이미 존재하는 이메일 입니다. - {}";
    public static final String USER_ALREADY_USE_EMAIL = "이미 존재하는 이메일 입니다.";
    public static final String USER_JOIN_SUCCESS_LOG = "User join #{}, {}";
    public static final String USER_UPDATE_FAILED_LOG = "User update #{}: Repository Error.";
    public static final String USER_UPDATE_FAILED = "정보 변경에 문제가 생겼습니다.";
    public static final String USER_ALREADY_REVOKE_LOG = "User revoke #{}: 이미 탈퇴한 회원 입니다.";
    public static final String USER_ALREADY_REVOKE = "이미 탈퇴한 회원입니다";
    public static final String USER_REVOKE_FAILED_LOG = "User revoke #{}: Repository Error.";
    public static final String USER_REVOKE_FAILED = "정보 변경에 문제가 생겼습니다.";
    public static final String USER_NOT_DELETED_LOG = "User delete #{}: 탈퇴되지 않은 회원입니다.";
    public static final String USER_NOT_DELETED = "탈퇴되지 않은 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_ID_LOG = "User findById #{}: 없는 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_ID = "없는 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_USERNAME_LOG = "User findByUsername #{}: 없는 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_USERNAME = "없는 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_NICKNAME_LOG = "User findByNickname #{}: 없는 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_NICKNAME = "없는 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_EMAIL_LOG = "User findByEmail - {}: 없는 회원 입니다.";
    public static final String USER_NOT_FOUND_BY_EMAIL = "없는 회원 입니다.";

    /* LoginService (session) */
    public static final String LOGIN_FAILED_LOG = "Login: 잘못된 로그인 정보입니다. {}";
    public static final String LOGIN_FAILED = "잘못된 로그인 정보입니다.";

    /* UserDetailsService */
    public static final String UDS_USER_NOT_FOUND = "loadUserByUsername : 사용자를 찾을 수 없음";
    public static final String UDS_USER_LOGIN = "사용자 로그인 : {}";

    /* FileService */
    public static final String FS_NOT_FOUND_TARGET_FILE_AS_DELETE_LOG = "삭제 할 파일을 찾을 수 없습니다. fileId : {}";
    public static final String FS_NOT_FILE_OWNER_LOG = "파일을 업로드 한 당사자가 아닙니다. {} / {}";

    /* CommentService */
    public static final String COMMENT_WRITE_SUCCESS_LOG = "Comment write #{} by User #{}";
    public static final String COMMENT_NOT_OWNER_LOG = "Comment update #{}: 작성자가 아닙니다. by User #{}";
    public static final String COMMENT_UPDATE_FAILED = "Comment update #{}: Repository Error.";
    public static final String COMMENT_UPDATE_SUCCESS_LOG = "Comment update #{} by User #{}";
    public static final String COMMENT_ALREADY_DELETE_FLAG_LOG = "Comment deleteflag #{}: 이미 삭제된 댓글 입니다.";
    public static final String COMMENT_NOT_OWNER_BY_DELETE_FLAG_LOG = "Comment deleteflag #{}: 작성자가 아닙니다. by User #{}";
    public static final String COMMENT_FAILED_DELETE_FLAG_LOG = "Comment deleteflag #{}: Repository Error.";
    public static final String COMMENT_SUCCESS_DELETE_FLAG_LOG = "Comment deleteflag #{} by User #{}";
    public static final String COMMENT_FAILED_DELETE_LOG = "Comment delete #{}: Repository Error.";
    public static final String COMMENT_SUCCESS_DELETE_LOG = "Comment delete #{}";
    public static final String COMMENT_NOT_FOUND_BY_ID_LOG = "Comment findById #{}: 없는 댓글 입니다.";

    /* BoardService */
    public static final String BOARD_ALREADY_USE_BOARD_NAME_LOG = "Board create #{}: 이름이 같은 게시판이 존재합니다.";
    public static final String BOARD_SUCCESS_CREATE = "Board create #{}: {}";
    public static final String BOARD_NOT_FOUND_BY_ID_UPDATE_LOG = "Board update #{}: 존재하지 않는 게시판 입니다.";
    public static final String BOARD_FAILED_UPDATE_LOG = "Board update #{}: Repository Error.";
    public static final String BOARD_SUCCESS_UPDATE = "Board create #{}: 게시판이 수정 되었습니다.";
    public static final String BOARD_NOT_FOUND_BY_ID_LOG = "Board findBById #{}: 존재하지 않는 게시판 입니다.";

    /* ArticleService */
    public static final String ARTICLE_WRITE_SUCCESS = "Article write #{} by User #{}";
    public static final String ARTICLE_NOT_OWNER_UPDATE_LOG = "Article update #{}: 작성자가 아닙니다.";
    public static final String ARTICLE_UPDATE_FAILED_LOG = "Article update #{}: Repository Error.";
    public static final String ARTICLE_UPDATE_SUCCESS = "Article update #{} by User #{}";
    public static final String ARTICLE_ALREADY_DELETE_FLAG_LOG = "Article deleteflag #{}: 이미 삭제된 게시물 입니다.";
    public static final String ARTICLE_NOT_OWNER_DELETE_FLAG_LOG = "Article deleteflag #{}: 작성자가 아닙니다.";
    public static final String ARTICLE_DELETE_FLAG_FAILED = "Article deleteflag #{}: Repository Error.";
    public static final String ARTICLE_DELETE_FLAG_SUCCESS = "Article deleteflag #{} by User #{}";

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

    /* StorageManagement */
    public static final String STORE_SUCCESS_LOG = "Upload file : {} , filesize : {}";

}
