package org.platanus.webboard.utils;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.board.BoardService;
import org.platanus.webboard.web.user.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInitTest {

    private final UserService userService;
    private final BoardService boardService;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus@canxan.com");
        user.setPassword("test!");
        user.setNickname("PLA");

        try {
            userService.join(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Board board = new Board();
        board.setName("Board01");
        board.setDescription("test baord");

        try {
            boardService.create(board);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
