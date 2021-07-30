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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    Article a1, a2, a3, a4;
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
    public void beforeEach() {
        a1 = new Article();
        a1.setDeleted(false);
        a1.setBoardId(1L);
        a1.setAuthorId(1L);
        a1.setTitle("제목입니다.");
        a1.setContent("내용입니다.");
        a1.setCreatedDate(LocalDateTime.now());
        a1.setModifiedDate(LocalDateTime.now());


        a2 = new Article();
        a2.setDeleted(false);
        a2.setBoardId(1L);
        a2.setAuthorId(1L);
        a2.setTitle("제목입니다.");
        a2.setContent("내용입니다.");
        a2.setCreatedDate(LocalDateTime.now());
        a2.setModifiedDate(LocalDateTime.now());


        a3 = new Article();
        a3.setDeleted(false);
        a3.setBoardId(1L);
        a3.setAuthorId(1L);
        a3.setTitle("제목입니다.");
        a3.setContent("내용입니다.");
        a3.setCreatedDate(LocalDateTime.now());
        a3.setModifiedDate(LocalDateTime.now());


        a4 = new Article();
        a4.setDeleted(false);
        a4.setBoardId(1L);
        a4.setAuthorId(1L);
        a4.setTitle("제목입니다.");
        a4.setContent("내용입니다.");
        a4.setCreatedDate(LocalDateTime.now());
        a4.setModifiedDate(LocalDateTime.now());


    }

    @Test
    public void write() {
        try {
            Article writeArticle = articleService.write(a1);
            assertEquals(writeArticle.getId(), articleService.findArticleById(writeArticle.getId()).getId());
            articleService.delete(writeArticle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void update() {
        try {
            Article writeArticle = articleService.write(a1);
            Article targetArticle = articleService.findArticleById(writeArticle.getId());
            targetArticle.setTitle("변경된 제목입니다.");
            articleService.update(targetArticle);
            assertEquals(articleService.findArticleById(writeArticle.getId()).getTitle(), targetArticle.getTitle());
            articleService.delete(writeArticle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void updateDeleteFlag() {
        try {
            Article writeArticle = articleService.write(a1);
            Article targetArticle = articleService.findArticleById(writeArticle.getId());
            articleService.updateDeleteFlag(targetArticle);
            assertEquals(articleService.isDeleted(articleRepository.findById(writeArticle.getId()).get()), true);
            articleService.delete(writeArticle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findAllArticles() {
        try {
            Article a1w = articleService.write(a1);
            Article a2w = articleService.write(a2);
            Article a3w = articleService.write(a3);
            Article a4w = articleService.write(a4);
            articleService.updateDeleteFlag(a4w);
            List<Article> articles = articleService.findAllArticles();
            assertEquals(articles.size(), 3);
            articleService.delete(a1w);
            articleService.delete(a2w);
            articleService.delete(a3w);
            articleService.delete(a4w);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findAllDeletedArticles() {
        try {
            Article a1w = articleService.write(a1);
            Article a2w = articleService.write(a2);
            Article a3w = articleService.write(a3);
            Article a4w = articleService.write(a4);
            articleService.updateDeleteFlag(a4w);
            List<Article> articles = articleService.findAllDeletedArticles();
            assertEquals(articles.size(), 1);
            articleService.delete(a1w);
            articleService.delete(a2w);
            articleService.delete(a3w);
            articleService.delete(a4w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findArticlesByBoardId() {
        try {
            Article a1w = articleService.write(a1);
            Article a2w = articleService.write(a2);
            Article a3w = articleService.write(a3);
            Article a4w = articleService.write(a4);
            articleService.updateDeleteFlag(a4w);
            List<Article> articles = articleService.findArticlesByBoardId(a1w.getBoardId());
            assertEquals(articles.size(), 3);
            articleService.delete(a1w);
            articleService.delete(a2w);
            articleService.delete(a3w);
            articleService.delete(a4w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
