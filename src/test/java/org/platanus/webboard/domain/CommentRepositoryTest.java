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
public class CommentRepositoryTest {
    private static BoardRepository boardRepository;
    private static UserRepository userRepository;
    private static ArticleRepository articleRepository;
    private static CommentRepository commentRepository;
    private static Board board;
    private static User user;
    private static Article article;
    private static Comment comment;

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
        commentRepository = new CommentRepository(jdbcTemplate);
        commentRepository.init();
        board = new Board();
        board.setName("board22");
        board.setDescription("description");
        board = boardRepository.save(board);
        user = new User();
        user.setUsername("user22");
        user.setPassword("aaa");
        user.setNickname("user22");
        user.setEmail("user22@gmail.com");
        user.setDeleted(false);
        user = userRepository.save(user);
        article = new Article();
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
    }

    @BeforeEach
    public void beforeEach() {
        comment = new Comment();
    }

    @Test
    public void save() {
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertEquals(comment.getContent(), findComment.getContent());
    }

    @Test
    public void delete() {
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        int result = commentRepository.delete(comment);
        assertEquals(result, 1);
    }

    @Test
    public void update() {
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        String content = "수정된 내용입니다.";
        comment.setContent(content);
        int result = commentRepository.update(comment);
        assertEquals(result, 1);
    }

    @Test
    public void updateDeleteFlag() {
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        comment.setDeleted(true);
        int result = commentRepository.updateDeleteFlag(comment);
        assertEquals(result, 1);
    }

    @Test
    public void findById() {
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertEquals(findComment.getId(), comment.getId());
    }

    @Test
    public void findByArticleId() {
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        List<Comment> comments = commentRepository.findByArticleId(comment.getArticleId());
        Comment findComment = comments.stream()
                .filter(c -> c.getId() == comment.getId())
                .findAny()
                .get();
        assertEquals(findComment.getId(), comment.getId());

    }

    @Test
    public void findByContent() {
        String content = "내용";
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        List<Comment> comments = commentRepository.findByContent(content);
        Comment findComment = comments.stream()
                .filter(c -> c.getId() == comment.getId())
                .findAny()
                .get();
        assertEquals(findComment.getId(), comment.getId());
    }

    @Test
    public void findAll() {
        comment.setArticleId(article.getId());
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setAuthorId(user.getId());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        List<Comment> comments = commentRepository.findAll();
        Comment findComment = comments.stream()
                .filter(c -> c.getId() == comment.getId())
                .findAny()
                .get();
        assertEquals(findComment.getId(), comment.getId());

    }
}
