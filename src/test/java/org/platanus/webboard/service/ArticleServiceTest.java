package org.platanus.webboard.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.repository.ArticleRepository;
import org.platanus.webboard.repository.BoardRepository;
import org.platanus.webboard.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.fail;

public class ArticleServiceTest<article> {

    public static final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/schema.sql")
            .build();
    private static Board board;
    private static User user;
    private static UserRepository userRepository;
    private static UserService userService;
    private static BoardService boardService;
    private static BoardRepository boardRepository;
    private static ArticleRepository articleRepository;
    private static ArticleService articleService;
    Article article;
    private static JdbcTemplate jdbcTemplate;


    @BeforeAll
    static void beforeAll() {
        board = new Board();
        board.setName("BOARD1");
        board.setDescription("게시판입니다.");
        jdbcTemplate = new JdbcTemplate(dataSource);
        boardRepository = new BoardRepository(jdbcTemplate);
        boardService = new BoardService(boardRepository);

        user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus.kr@gmail.com");
        user.setPassword("aaa");
        user.setNickname("PLA");
        userRepository = new UserRepository(jdbcTemplate);
        userService = new UserService(userRepository);

        articleRepository = new ArticleRepository(jdbcTemplate);
        articleService = new ArticleService(articleRepository, boardService);

        try {
            boardService.create(board);
            userService.join(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        article = new Article();
        article.setDeleted(false);
        article.setBoardId(1L);
        article.setAuthorId(1L);
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
    }

    @Test
    public void write() {
        try {
            articleService.write(article);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

}
