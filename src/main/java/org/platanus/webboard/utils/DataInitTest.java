package org.platanus.webboard.utils;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInitTest {

    private final UserService userService;
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.net");
        user.setPassword("admin");
        user.setNickname("admin");
        user.setRole(UserRole.ADMIN);
        try {
            user = userService.join(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Board board = new Board();
        board.setName("Board01");
        board.setDescription("the baord");
        try {
            board = boardService.create(board);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        User user2 = new User();
        user2.setUsername("test");
        user2.setEmail("test@test.com");
        user2.setPassword("test");
        user2.setNickname("test");
        user2.setRole(UserRole.USER);
        try {
            user2 = userService.join(user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


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
                System.out.println(e.getMessage());
            }
        }

        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j < 5; j++) {
                try {
                    Comment comment = new Comment();
                    comment.setContent(j + " 번째 댓글 내용 입니다.");
                    comment.setArticleId(i);
                    comment.setAuthorId(1L);
                    comment.setDeleted(false);
                    commentService.write(comment);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
