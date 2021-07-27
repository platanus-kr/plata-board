package org.platanus.webboard.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleRepositoryTest {

    public static final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/schema.sql")
            .build();

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    ArticleRepository articleRepository = new ArticleRepository(jdbcTemplate);
    BoardRepository boardRepository = new BoardRepository(jdbcTemplate);
    UserRepository userRepository = new UserRepository(jdbcTemplate);
    Article article;
    Article modifiedArticle;
    Board board;
    User user;

    @BeforeEach
    public void beforeEach() {
        board = new Board();
        board.setName("test");

        user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus.kr@gmail.com");
        user.setPassword("aaa");
        user.setNickname("PLA");

        article = new Article();
        article.setBoardId(1L);
        article.setTitle("제목입니다.");
        article.setContent("글내용 입니다");
        article.setAuthorId(1L);
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);

        modifiedArticle = new Article();
        modifiedArticle.setTitle("수정된 제목 입니다.");
        modifiedArticle.setContent("수정된 내용 입니다.");
        modifiedArticle.setModifiedDate(LocalDateTime.now());

        boardRepository.save(board);
        userRepository.save(user);
    }

    @AfterEach
    public void afterEach() {
//        userRepository.delete(user);
//        boardRepository.delete(board);
    }

    @Test
    public void save() {
        articleRepository.save(article);
        Article result = articleRepository.findById(article.getId()).get();
        assertEquals(article, result);
        articleRepository.delete(article);
    }

    @Test
    public void update() {
        articleRepository.save(article);
        modifiedArticle.setId(article.getId());
        int result = articleRepository.update(modifiedArticle);
        assertEquals(result, 1);
        articleRepository.delete(article);
    }
    

}
