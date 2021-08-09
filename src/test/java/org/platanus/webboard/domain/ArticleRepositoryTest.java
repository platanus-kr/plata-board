package org.platanus.webboard.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class ArticleRepositoryTest {

    private static BoardRepository boardRepository;
    private static UserRepository userRepository;
    private static ArticleRepository articleRepository;
    private static Board board;
    private static User user;
    private Article article;

    @BeforeAll
    static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        boardRepository = new BoardRepository(jdbcTemplate);
        boardRepository.init();
        userRepository = new UserRepository(jdbcTemplate);
        userRepository.init();
        articleRepository = new ArticleRepository(jdbcTemplate);
        articleRepository.init();
        board = new Board();
        board.setName("board01");
        board.setDescription("description");
        board = boardRepository.save(board);
        user = new User();
        user.setUsername("user01");
        user.setPassword("aaa");
        user.setNickname("user01");
        user.setEmail("user1@gmail.com");
        user.setDeleted(false);
        user = userRepository.save(user);
    }

    @BeforeEach
    public void beforeEach() {
        article = new Article();
    }

    @Test
    public void save() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        Article findArticle = articleRepository.findById(article.getId()).get();
        assertEquals(findArticle.getId(), article.getId());
    }

    @Test
    public void delete() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        int result = articleRepository.delete(article);
        assertEquals(result, 1);
    }

    @Test
    public void update() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        String changedTitle = "수정된 제목 입니다.";
        article.setTitle(changedTitle);
        int result = articleRepository.update(article);
        assertEquals(articleRepository.findById(article.getId()).get().getTitle(), changedTitle);
    }

    @Test
    public void updateDeleteFlag() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        int result = articleRepository.updateDeleteFlag(article);
        assertEquals(result, 1);
    }

    @Test
    public void findById() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        Article findArticle = articleRepository.findById(article.getId()).get();
        assertEquals(findArticle.getId(), article.getId());
    }

    @Test
    public void findByBoardId() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        List<Article> articles = articleRepository.findByBoardId(article.getBoardId());
        Article findArticle = articles.stream()
                .filter(a -> a.getId() == article.getId())
                .findAny()
                .get();
        assertEquals(findArticle.getId(), article.getId());
    }

    @Test
    public void findAll() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        List<Article> articles = articleRepository.findAll();
        Article findArticle = articles.stream()
                .filter(a -> a.getId() == article.getId())
                .findAny()
                .get();
        assertEquals(findArticle.getId(), article.getId());
    }

    @Test
    public void findByAuthorId() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        List<Article> articles = articleRepository.findByAuthorId(article.getAuthorId());
        Article findArticle = articles.stream()
                .filter(a -> a.getAuthorId() == article.getAuthorId())
                .findAny()
                .get();
        assertEquals(findArticle.getAuthorId(), article.getAuthorId());
    }

    @Test
    public void findByTitle() {
        String title = "찾을 제목입니다.";
        article.setBoardId(board.getId());
        article.setTitle(title);
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        List<Article> articles = articleRepository.findByTitle(title);
        Article findArticle = articles.stream()
                .filter(a -> a.getTitle().equals(article.getTitle()))
                .findAny()
                .get();
        assertEquals(findArticle.getTitle(), article.getTitle());
    }

    @Test
    public void findByContent() {
        String content = "찾을 내용입니다.";
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent(content);
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        List<Article> articles = articleRepository.findByContent(content);
        Article findArticle = articles.stream()
                .filter(a -> a.getContent().equals(article.getContent()))
                .findAny()
                .get();
        assertEquals(findArticle.getContent(), article.getContent());
    }

    @Test
    public void findByTitleAndContent() {
        String title = "찾을 제목입니다.";
        String content = "찾을 내용입니다.";
        article.setBoardId(board.getId());
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        List<Article> articles = articleRepository.findByTitleAndContent(title, content);
        Article findArticle = articles.stream()
                .filter(a -> a.getContent().equals(article.getContent()))
                .findAny()
                .get();
        assertEquals(findArticle.getContent(), article.getContent());
    }
}
