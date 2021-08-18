package org.platanus.webboard.web.board;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.BoardRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Transactional
public class BoardServiceTest {

    private static BoardRepository boardRepository;
    private static BoardService boardService;
//    private Board board;

    @BeforeAll
    static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        boardRepository = new BoardRepository(jdbcTemplate);
        boardRepository.init();
        boardService = new BoardService(boardRepository);
    }

//    @AfterAll
//    static void afterAll() {
//        boardRepository.allDelete();
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        board = new Board();
//    }

    @Test
    public void create() {
        try {
            Board board = new Board();
            String boardName = "BOARD 01";
            board.setName(boardName);
            board.setDescription("description");
            Board createdBoard = boardService.create(board);
            Board targetBoard = boardService.findById(createdBoard.getId());
            assertEquals(targetBoard.getId(), createdBoard.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void update() {
        try {
            Board board = new Board();
            String boardName = "BOARD 02";
            String updateBoardName = "BOARD 02 UPDATE";
            board.setName(boardName);
            board.setDescription("description");
            Board createdBoard = boardService.create(board);
            createdBoard.setName(updateBoardName);
            Board targetBoard = boardService.update(createdBoard);
            Board searchBoard = boardService.findById(targetBoard.getId());
            assertEquals(searchBoard.getName(), updateBoardName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findAll() {
        try {
            Board board = new Board();
            String boardName = "BOARD 03";
            board.setName(boardName);
            board.setDescription("description");
            boardService.create(board);
            List<Board> boards = boardService.findAll();
            Board targetBoard = boards.stream()
                    .filter(b -> b.getName().equals(boardName))
                    .findAny()
                    .get();
            assertEquals(targetBoard.getName(), boardName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

}
