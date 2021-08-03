package org.platanus.webboard.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.repository.ArticleRepository;
import org.platanus.webboard.repository.BoardRepository;
import org.platanus.webboard.repository.CommentRepository;
import org.platanus.webboard.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CommentServiceTest {

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
    private static Article article;
    private static CommentRepository commentRepository;
    private static CommentService commentService;
    Comment c1, c2, c3, c4;
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
        articleService = new ArticleService(articleRepository, boardService, userService);
        article = new Article();
        article.setDeleted(false);
        article.setBoardId(1L);
        article.setAuthorId(1L);
        article.setTitle("제목입니다.");
        article.setContent("내용입니다.");
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());

        commentRepository = new CommentRepository(jdbcTemplate);
        commentService = new CommentService(commentRepository, articleService);

        try {
            boardService.create(board);
            userService.join(user);
            articleService.write(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void beforeEach() {
        c1 = new Comment();
        c1.setArticleId(1L);
        c1.setContent("댓글 입니다");
        c1.setAuthorId(1L);
        c1.setCreatedDate(LocalDateTime.now());
        c1.setModifiedDate(LocalDateTime.now());
        c1.setDeleted(false);

        c2 = new Comment();
        c2.setArticleId(1L);
        c2.setContent("댓글 입니다");
        c2.setAuthorId(1L);
        c2.setCreatedDate(LocalDateTime.now());
        c2.setModifiedDate(LocalDateTime.now());
        c2.setDeleted(false);

        c3 = new Comment();
        c3.setArticleId(1L);
        c3.setContent("댓글 입니다");
        c3.setAuthorId(1L);
        c3.setCreatedDate(LocalDateTime.now());
        c3.setModifiedDate(LocalDateTime.now());
        c3.setDeleted(false);

        c4 = new Comment();
        c4.setArticleId(1L);
        c4.setContent("댓글 입니다");
        c4.setAuthorId(1L);
        c4.setCreatedDate(LocalDateTime.now());
        c4.setModifiedDate(LocalDateTime.now());
        c4.setDeleted(false);

    }

    @Test
    public void write() {
        try {
            Comment writeComment = commentService.write(c1);
            assertEquals(writeComment.getId(), commentService.findById(writeComment.getId()).getId());
            commentService.delete(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void update() {
        try {
            Comment writeComment = commentService.write(c1);
            Comment targetComment = commentService.findById(writeComment.getId());
            targetComment.setContent("변경된 댓글 입니다.");
            commentService.update(targetComment);
            assertEquals(commentService.findById(writeComment.getId()).getContent(), targetComment.getContent());
            commentService.delete(writeComment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void updateDeleteFlag() {
        try {
            Comment writeComment = commentService.write(c1);
            Comment targetComment = commentService.findById(writeComment.getId());
            commentService.updateDeleteFlag(targetComment);
            assertEquals(commentService.isDeleted(commentRepository.findById(writeComment.getId()).get()), true);
            commentService.delete(writeComment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findAllComments() {
        try {
            Comment c1w = commentService.write(c1);
            Comment c2w = commentService.write(c2);
            Comment c3w = commentService.write(c3);
            Comment c4w = commentService.write(c4);
            commentService.updateDeleteFlag(c4w);
            List<Comment> comments = commentService.findAllComments();
            assertEquals(comments.size(), 3);
            commentService.delete(c1w);
            commentService.delete(c2w);
            commentService.delete(c3w);
            commentService.delete(c4w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findAllDeletedComments() {
        try {
            Comment c1w = commentService.write(c1);
            Comment c2w = commentService.write(c2);
            Comment c3w = commentService.write(c3);
            Comment c4w = commentService.write(c4);
            commentService.updateDeleteFlag(c4w);
            List<Comment> comments = commentService.findAllDeletedComments();
            assertEquals(comments.size(), 1);
            commentService.delete(c1w);
            commentService.delete(c2w);
            commentService.delete(c3w);
            commentService.delete(c4w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findCommentsByArticleId() {
        try {
            Comment c1w = commentService.write(c1);
            Comment c2w = commentService.write(c2);
            Comment c3w = commentService.write(c3);
            Comment c4w = commentService.write(c4);
            commentService.updateDeleteFlag(c4w);
            List<Comment> comments = commentService.findCommentsByArticleId(c1w.getArticleId());
            assertEquals(comments.size(), 3);
            commentService.delete(c1w);
            commentService.delete(c2w);
            commentService.delete(c3w);
            commentService.delete(c4w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }


}
