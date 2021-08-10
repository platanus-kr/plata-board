package org.platanus.webboard.utils;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.board.ArticleService;
import org.platanus.webboard.web.board.BoardService;
import org.platanus.webboard.web.user.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInitTest {

    private final UserService userService;
    private final BoardService boardService;
    private final ArticleService articleService;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus@canxan.com");
        user.setPassword("test!");
        user.setNickname("PLA");
        try {
            user = userService.join(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Board board = new Board();
        board.setName("Board01");
        board.setDescription("test baord");
        try {
            board = boardService.create(board);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        Article article = new Article();
//        article.setTitle("제목입니다");
//        article.setBoardId(board.getId());
//        article.setAuthorId(user.getId());
//        article.setContent("내용입니다");
//        for (int i = 0; i < 100; i++) {
//            try {
//                article.setTitle(i + "번째 제목입니다");
//                articleService.write(article);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }

    }
}
