package org.platanus.webboard.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.user.RoleService;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.*;
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

    @PostConstruct
    public void init() {
        User user = User.builder()
                .username("admin")
                .password("admin")
                .email("admin@admin.net")
                .nickname("admin")
                .role(UserRole.ROLE_ADMIN)
                .build();
        try {
            user = userService.join(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        Board board = Board.builder()
                .name("Board")
                .description("the board")
                .build();
        try {
            board = boardService.create(board);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        User user2 = User.builder()
                .username("user")
                .password("user")
                .email("user@user.com")
                .nickname("user")
                .role(UserRole.ROLE_USER)
                .build();
        try {
            user2 = userService.join(user2);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        roleService.join(new Role(UserRole.ROLE_USER, user.getId()));
        roleService.join(new Role(UserRole.ROLE_ADMIN, user.getId()));
        roleService.join(new Role(UserRole.ROLE_USER, user2.getId()));
        roleService.findAll().stream().forEach(v -> {
            log.info(v.toString());
        });

        for (int i = 1; i <= 100; i++) {
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

        for (int i = 0; i <= 100; i++) {
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
