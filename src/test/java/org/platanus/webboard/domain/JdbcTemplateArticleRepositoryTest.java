package org.platanus.webboard.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.jdbctemplate.JdbcTemplateArticleRepository;
import org.platanus.webboard.domain.jdbctemplate.JdbcTemplateBoardRepository;
import org.platanus.webboard.domain.jdbctemplate.JdbcTemplateUserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class JdbcTemplateArticleRepositoryTest {

    private static JdbcTemplateBoardRepository jdbcTemplateBoardRepository;
    private static JdbcTemplateUserRepository jdbcTemplateUserRepository;
    private static JdbcTemplateArticleRepository jdbcTemplateArticleRepository;
    private static Board board;
    private static Board boardForCount;
    private static User user;
    private Article article;

    @BeforeAll
    static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplateBoardRepository = new JdbcTemplateBoardRepository(jdbcTemplate);
        jdbcTemplateBoardRepository.init();
        jdbcTemplateUserRepository = new JdbcTemplateUserRepository(jdbcTemplate);
        jdbcTemplateUserRepository.init();
        jdbcTemplateArticleRepository = new JdbcTemplateArticleRepository(jdbcTemplate);
        jdbcTemplateArticleRepository.init();
        board = new Board();
        board.setName("board21");
        board.setDescription("description");
        board = jdbcTemplateBoardRepository.save(board);
        boardForCount = new Board();
        boardForCount.setName("boardForCount");
        boardForCount.setDescription("-");
        boardForCount = jdbcTemplateBoardRepository.save(boardForCount);
        user = new User();
        user.setUsername("user21");
        user.setPassword("aaa");
        user.setNickname("user21");
        user.setEmail("user21@gmail.com");
        user.setRole(UserRole.USER);
        user.setDeleted(false);
        user = jdbcTemplateUserRepository.save(user);
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
        article = jdbcTemplateArticleRepository.save(article);
        Article findArticle = jdbcTemplateArticleRepository.findById(article.getId()).get();
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
        article = jdbcTemplateArticleRepository.save(article);
        int result = jdbcTemplateArticleRepository.delete(article);
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
        article = jdbcTemplateArticleRepository.save(article);
        String changedTitle = "수정된 제목 입니다.";
        article.setTitle(changedTitle);
        int result = jdbcTemplateArticleRepository.update(article);
        assertEquals(jdbcTemplateArticleRepository.findById(article.getId()).get().getTitle(), changedTitle);
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
        article = jdbcTemplateArticleRepository.save(article);
        int result = jdbcTemplateArticleRepository.updateDeleteFlag(article);
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
        article = jdbcTemplateArticleRepository.save(article);
        Article findArticle = jdbcTemplateArticleRepository.findById(article.getId()).get();
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
        article = jdbcTemplateArticleRepository.save(article);
        List<Article> articles = jdbcTemplateArticleRepository.findByBoardId(article.getBoardId());
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
        article = jdbcTemplateArticleRepository.save(article);
        List<Article> articles = jdbcTemplateArticleRepository.findAll();
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
        article = jdbcTemplateArticleRepository.save(article);
        List<Article> articles = jdbcTemplateArticleRepository.findByAuthorId(article.getAuthorId());
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
        article = jdbcTemplateArticleRepository.save(article);
        List<Article> articles = jdbcTemplateArticleRepository.findByTitle(title);
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
        article = jdbcTemplateArticleRepository.save(article);
        List<Article> articles = jdbcTemplateArticleRepository.findByContent(content);
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
        article = jdbcTemplateArticleRepository.save(article);
        List<Article> articles = jdbcTemplateArticleRepository.findByTitleAndContent(title, content);
        Article findArticle = articles.stream()
                .filter(a -> a.getContent().equals(article.getContent()))
                .findAny()
                .get();
        assertEquals(findArticle.getContent(), article.getContent());
    }

    @Test
    public void count() {
        article.setBoardId(boardForCount.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        for (int i = 0; i < 10; i++)
            article = jdbcTemplateArticleRepository.save(article);
        int result = jdbcTemplateArticleRepository.count(article.getBoardId());
        System.out.println("글 카운팅 : " + result);
        assertEquals(result, 10);
    }

    @Test
    public void findByBoardIdPagination() {
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        for (int i = 0; i < 50; i++) {
            article.setTitle(i + "번째 글입니다.");
            jdbcTemplateArticleRepository.save(article);
        }
        PageRequest pageable = PageRequest.of(0, 15);
        System.out.println(board.getId());
        List<Article> articles = jdbcTemplateArticleRepository.findByBoardIdPagination(pageable, board.getId());
        articles.forEach(a -> System.out.println(a.getTitle()));
        assertEquals(articles.size(), 15);
    }
}
