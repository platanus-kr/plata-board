package org.platanus.webboard.web.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.BoardRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.fail;

public class BoardServiceTest {

    public static final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/schema.sql")
            .build();

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    BoardRepository repository = new BoardRepository(jdbcTemplate);
    BoardService service = new BoardService(repository);
    Board board;

    @BeforeEach
    public void beforeEach() {
        board = new Board();
        board.setName("BOARD1");
        board.setDescription("게시판 설명");
    }

    @Test
    public void create() {
        try {
            service.create(board);
            service.delete(board);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
