package org.platanus.webboard.controller.board;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.jdbctemplate.JdbcTemplateArticleRepository;
import org.platanus.webboard.domain.jdbctemplate.JdbcTemplateBoardRepository;
import org.platanus.webboard.domain.jdbctemplate.JdbcTemplateCommentRepository;
import org.platanus.webboard.domain.jdbctemplate.JdbcTemplateUserRepository;
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

    private static JdbcTemplateBoardRepository jdbcTemplateBoardRepository;
    private static BoardService boardService;
    private static JdbcTemplateArticleRepository jdbcTemplateArticleRepository;
    private static ArticleService articleService;
    private static JdbcTemplateCommentRepository jdbcTemplateCommentRepository;
    private static CommentService commentService;
    private static JdbcTemplateUserRepository jdbcTemplateUserRepository;
    private static UserService userService;
    private Board board;

    @BeforeAll
    static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplateBoardRepository = new JdbcTemplateBoardRepository(jdbcTemplate);
        jdbcTemplateBoardRepository.init();
        jdbcTemplateArticleRepository = new JdbcTemplateArticleRepository(jdbcTemplate);
        jdbcTemplateArticleRepository.init();
        jdbcTemplateCommentRepository = new JdbcTemplateCommentRepository(jdbcTemplate);
        jdbcTemplateCommentRepository.init();
        jdbcTemplateUserRepository = new JdbcTemplateUserRepository(jdbcTemplate);
        jdbcTemplateUserRepository.init();
        userService = new UserService(jdbcTemplateUserRepository);
        articleService = new ArticleService(jdbcTemplateArticleRepository, commentService, userService);
        boardService = new BoardService(jdbcTemplateBoardRepository, articleService);
    }

//    @AfterAll
//    static void afterAll() {
//        boardRepository.allDelete();
//    }

    @BeforeEach
    public void beforeEach() {
        board = new Board();
    }

    @Test
    public void create() {
        try {
            String boardName = "BOARD 11";
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
            String boardName = "BOARD 12";
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
            String boardName = "BOARD 13";
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
