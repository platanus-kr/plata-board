package org.platanus.webboard.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentRepositoryTest {
    public static final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/schema.sql")
            .build();

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    CommentRepository commentRepository = new CommentRepository(jdbcTemplate);
    ArticleRepository articleRepository = new ArticleRepository(jdbcTemplate);
    BoardRepository boardRepository = new BoardRepository(jdbcTemplate);
    UserRepository userRepository = new UserRepository(jdbcTemplate);
    Comment comment;
    Comment modifiedComment;
    Article article;
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
        article.setBoardId(board.getId());
        article.setTitle("제목입니다.");
        article.setContent("글내용 입니다");
        article.setAuthorId(user.getId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        articleRepository.save(article);


        comment = new Comment();
        comment.setArticleId(article.getId());
        comment.setContent("코멘트 입니다.");
        comment.setAuthorId(user.getId());
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setDeleted(false);

        modifiedComment = new Comment();
        modifiedComment.setContent("수정된 코멘트 입니다.");
        modifiedComment.setModifiedDate(LocalDateTime.now());

    }

    @Test
    public void save() {
        commentRepository.save(comment);
        Optional<Comment> result = commentRepository.findById(comment.getId());
        assertEquals(result.get().getId(), comment.getId());
        commentRepository.delete(comment);
    }

    @Test
    public void update() {
        commentRepository.save(comment);
        modifiedComment.setId(comment.getId());
        int result = commentRepository.update(modifiedComment);
        assertEquals(result, 1);
        commentRepository.delete(comment);
    }

    @Test
    public void findByContent() {
        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findByContent("코멘트");
        assertEquals(comments.size(), 1);
        commentRepository.delete(comment);
    }

    @Test
    public void findByAuthorId() {
        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findByArticleId(comment.getArticleId());
        assertEquals(comments.size(), 1);
        commentRepository.delete(comment);
    }

    @Test
    public void updateDeleteFlag() {
        commentRepository.save(comment);
        comment.setDeleted(true);
        int result = commentRepository.updateDeleteFlag(comment);
        assertEquals(result, 1);
        commentRepository.delete(comment);
    }


}
