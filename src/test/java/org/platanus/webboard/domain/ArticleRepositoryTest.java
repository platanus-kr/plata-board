package org.platanus.webboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

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
        boardRepository.save(board);

        user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus.kr@gmail.com");
        user.setPassword("aaa");
        user.setNickname("PLA");
        userRepository.save(user);

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

    @Test
    public void findByTitle() {
        articleRepository.save(article);
        Article dup_article = new Article();
        dup_article.setBoardId(1L);
        dup_article.setTitle("제목입니다.");
        dup_article.setContent("글내용 입니다");
        dup_article.setAuthorId(article.getId());
        dup_article.setCreatedDate(LocalDateTime.now());
        dup_article.setModifiedDate(LocalDateTime.now());
        dup_article.setDeleted(false);
        articleRepository.save(dup_article);
        List<Article> articles = articleRepository.findByTitle("제목");
        assertEquals(articles.size(), 2);
        articleRepository.delete(article);
        articleRepository.delete(dup_article);
    }

    @Test
    public void findByContent() {
        articleRepository.save(article);
        List<Article> articles = articleRepository.findByContent("내용");
        assertEquals(articles.size(), 1);
        articleRepository.delete(article);
    }

    @Test
    public void findByTitleAndContent() {
        articleRepository.save(article);
        List<Article> articles = articleRepository.findByTitleAndContent("제목", "내용");
        assertEquals(articles.size(), 1);
        articleRepository.delete(article);
    }

    @Test
    public void findByAuthorId() {
        articleRepository.save(article);
        List<Article> articles = articleRepository.findByAuthorId(article.getAuthorId());
        assertEquals(articles.size(), 1);
        articleRepository.delete(article);
    }

    @Test
    public void updateDeleteFlag() {
        articleRepository.save(article);
        article.setDeleted(true);
        int result = articleRepository.updateDeleteFlag(article);
        assertEquals(result, 1);
        articleRepository.delete(article);
    }


}
