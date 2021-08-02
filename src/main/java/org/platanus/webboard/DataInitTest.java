package org.platanus.webboard;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.service.BoardService;
import org.platanus.webboard.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInitTest {

    private final UserService userService;
    private final BoardService boardService;

    @PostConstruct
    public void init() throws Exception {
        User user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus@canxan.com");
        user.setPassword("test!");
        user.setNickname("PLA");

        userService.join(user);

        Board board = new Board();
        board.setName("Board01");
        board.setDescription("test baord");

        boardService.create(board);

    }
}
