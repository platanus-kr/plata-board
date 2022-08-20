package org.platanus.webboard.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.ConfigConstant;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.user.RoleService;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;
    @Value("${plataboard.environment.profile}")
    private String profile;

    @Value("${plataboard.environment.frontend-address}")
    private String feAddress;

    @PostConstruct
    public void init() {
        // properties 파일 내 plataboard.environment.profile이 local 일 때만 실행됨.
        if (profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_PRODUCTION)) {
            return;
        }

        log.info("Frontend address for CORS allow : {}", feAddress);


        // 테스트 운영자 생성
        User user = null;
        try {
            user = userService.join(new User(null, "admin", "admin", "운영자", "admin@admin.net", false, UserRole.ROLE_ADMIN));
//            roleService.add(new Role(UserRole.ROLE_ADMIN, user.getId()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 테스트 유저 생성
        User user2;
        try {
            user2 = userService.join(new User(null, "user", "user", "유저", "user@user.com", false, UserRole.ROLE_ADMIN));
//            roleService.add(new Role(UserRole.ROLE_USER, user2.getId()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 테스트 보드 생성
        Board board = null;
        try {
            board = boardService.create(new Board(null, "Board", "the Board"));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        roleService.findAll().stream().forEach(v -> {
            log.info(v.toString());
        });

        for (int i = 1; i <= 2; i++) {
            try {
                Article article = Article.builder()
                        .title(i + "번째 제목입니다")
                        .boardId(board.getId())
                        .authorId(user.getId())
                        .content("내용입니다.")
                        .build();
                articleService.write(article);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j < 5; j++) {
                try {
                    Comment comment = Comment.builder()
                            .content(j + " 번째 댓글 내용 입니다.")
                            .articleId(i)
                            .authorId(1L)
                            .deleted(false)
                            .build();
                    commentService.write(comment);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }

    }
}
