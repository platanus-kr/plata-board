package org.platanus.webboard.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class JdbcTemplateBoardRepositoryTest {

    private static JdbcTemplateBoardRepository jdbcTemplateBoardRepository;
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
    }

//    @AfterAll
//    static void afterAll() {
//        boardRepository.allDelete();
//        List<Board> findBoards = boardRepository.findAll();
//        assertEquals(findBoards.size(), 0);
//    }

    @BeforeEach
    public void beforeEach() {
        board = new Board();
    }

    @Test
    public void save() {
        String boardName = "BOARD 01";
        board.setName(boardName);
        board.setDescription("description");
        Board savedBoard = jdbcTemplateBoardRepository.save(board);
        assertEquals(savedBoard.getName(), boardName);
    }

    @Test
    public void update() {
        board.setName("BOARD 02");
        board.setDescription("description");
        Board savedBoard = jdbcTemplateBoardRepository.save(board);
        String changeBoardName = "BOARD CHANGED";
        savedBoard.setName(changeBoardName);
        int changedRowNumber = jdbcTemplateBoardRepository.update(savedBoard);
        assertEquals(changedRowNumber, 1);
    }

    @Test
    public void findById() {
        board.setName("BOARD 03");
        board.setDescription("description");
        Board savedBoard = jdbcTemplateBoardRepository.save(board);
        Board findBoard = jdbcTemplateBoardRepository.findById(savedBoard.getId()).get();
        assertEquals(findBoard.getId(), savedBoard.getId());
    }

    @Test
    public void findByName() {
        String boardName = "BOARD 04";
        board.setName(boardName);
        board.setDescription("description");
        Board savedBoard = jdbcTemplateBoardRepository.save(board);
        Board findBoard = jdbcTemplateBoardRepository.findByName(savedBoard.getName()).get();
        assertEquals(boardName, findBoard.getName());
    }


}
