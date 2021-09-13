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
public class JdbcTemplateCommentRepositoryTest {
    private static JdbcTemplateBoardRepository jdbcTemplateBoardRepository;
    private static JdbcTemplateUserRepository jdbcTemplateUserRepository;
    private static JdbcTemplateArticleRepository jdbcTemplateArticleRepository;
    private static JdbcTemplateCommentRepository jdbcTemplateCommentRepository;
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
        jdbcTemplateBoardRepository = new JdbcTemplateBoardRepository(jdbcTemplate);
        jdbcTemplateBoardRepository.init();
        jdbcTemplateUserRepository = new JdbcTemplateUserRepository(jdbcTemplate);
        jdbcTemplateUserRepository.init();
        jdbcTemplateArticleRepository = new JdbcTemplateArticleRepository(jdbcTemplate);
        jdbcTemplateArticleRepository.init();
        jdbcTemplateCommentRepository = new JdbcTemplateCommentRepository(jdbcTemplate);
        jdbcTemplateCommentRepository.init();
        board = new Board();
        board.setName("board22");
        board.setDescription("description");
        board = jdbcTemplateBoardRepository.save(board);
        user = new User();
        user.setUsername("user22");
        user.setPassword("aaa");
        user.setNickname("user22");
        user.setEmail("user22@gmail.com");
        user.setDeleted(false);
        user = jdbcTemplateUserRepository.save(user);
        article = new Article();
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = jdbcTemplateArticleRepository.save(article);
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
        comment = jdbcTemplateCommentRepository.save(comment);
        Comment findComment = jdbcTemplateCommentRepository.findById(comment.getId()).get();
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
        comment = jdbcTemplateCommentRepository.save(comment);
        int result = jdbcTemplateCommentRepository.delete(comment);
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
        comment = jdbcTemplateCommentRepository.save(comment);
        String content = "수정된 내용입니다.";
        comment.setContent(content);
        int result = jdbcTemplateCommentRepository.update(comment);
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
        comment = jdbcTemplateCommentRepository.save(comment);
        comment.setDeleted(true);
        int result = jdbcTemplateCommentRepository.updateDeleteFlag(comment);
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
        comment = jdbcTemplateCommentRepository.save(comment);
        Comment findComment = jdbcTemplateCommentRepository.findById(comment.getId()).get();
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
        comment = jdbcTemplateCommentRepository.save(comment);
        List<Comment> comments = jdbcTemplateCommentRepository.findByArticleId(comment.getArticleId());
        Comment findComment = comments.stream()
                .filter(c -> c.getId() == comment.getId())
                .findAny()
                .get();
        assertEquals(findComment.getId(), comment.getId());

    }

//    @Test
//    public void findByContent() {
//        String content = "내용";
//        comment.setArticleId(article.getId());
//        comment.setContent("댓글 내용입니다.");
//        comment.setCreatedDate(LocalDateTime.now());
//        comment.setModifiedDate(LocalDateTime.now());
//        comment.setAuthorId(user.getId());
//        comment.setDeleted(false);
//        comment = commentRepository.save(comment);
//        List<Comment> comments = commentRepository.findByContent(content);
//        Comment findComment = comments.stream()
//                .filter(c -> c.getId() == comment.getId())
//                .findAny()
//                .get();
//        assertEquals(findComment.getId(), comment.getId());
//    }

//    @Test
//    public void findAll() {
//        comment.setArticleId(article.getId());
//        comment.setContent("댓글 내용입니다.");
//        comment.setCreatedDate(LocalDateTime.now());
//        comment.setModifiedDate(LocalDateTime.now());
//        comment.setAuthorId(user.getId());
//        comment.setDeleted(false);
//        comment = commentRepository.save(comment);
//        List<Comment> comments = commentRepository.findAll();
//        Comment findComment = comments.stream()
//                .filter(c -> c.getId() == comment.getId())
//                .findAny()
//                .get();
//        assertEquals(findComment.getId(), comment.getId());
//
//    }
}
