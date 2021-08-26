package org.platanus.webboard.utils;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;
import org.platanus.webboard.web.board.ArticleService;
import org.platanus.webboard.web.board.BoardService;
import org.platanus.webboard.web.board.CommentService;
import org.platanus.webboard.web.user.UserService;
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
        
//        User user2 = new User();
//        user2.setUsername("test");
//        user2.setEmail("test@test.com");
//        user2.setPassword("test");
//        user2.setNickname("test");
//        user2.setRole(UserRole.USER);
//        try {
//            user2 = userService.join(user2);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

//
//        Article article = new Article();
//        article.setTitle("제목입니다");
//        article.setBoardId(board.getId());
//        article.setAuthorId(user.getId());
//        article.setContent("내용입니다");
//        for (int i = 1; i <= 100; i++) {
//            try {
//                article.setTitle(i + "번째 제목입니다");
//                articleService.write(article);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        Comment comment = new Comment();
//        comment.setContent("## 댓글 내용 입니다.");
//        comment.setArticleId(10L);
//        comment.setAuthorId(1L);
//        comment.setDeleted(false);
//        for (int i = 0; i < 5; i++) {
//            try {
//                commentService.write(comment);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
    }
}
