package org.platanus.webboard.web.board;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.*;
import org.platanus.webboard.web.board.dto.ArticleListDto;
import org.platanus.webboard.web.user.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Transactional
public class ArticleServiceTest {
    private static BoardRepository boardRepository;
    private static BoardService boardService;
    private static UserRepository userRepository;
    private static UserService userService;
    private static ArticleRepository articleRepository;
    private static ArticleService articleService;
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
        boardService = new BoardService(boardRepository);
        userRepository = new UserRepository(jdbcTemplate);
        userRepository.init();
        userService = new UserService(userRepository);
        articleRepository = new ArticleRepository(jdbcTemplate);
        articleRepository.init();
        articleService = new ArticleService(articleRepository, boardService, userService);
        try {
            board = new Board();
            board.setName("board31");
            board.setDescription("description");
            board = boardService.create(board);
            user = new User();
            user.setUsername("user31");
            user.setPassword("aaa");
            user.setNickname("user31");
            user.setEmail("user31@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @BeforeEach
    public void beforeEach() {
        article = new Article();
    }

    @Test
    public void write() {
        try {
            article.setBoardId(board.getId());
            article.setTitle("제목입니다.");
            article.setContent("내용입니다.");
            article.setAuthorId(user.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            article.setDeleted(false);
            article = articleService.write(article);
            Article findArticle = articleService.findById(article.getId());
            assertEquals(findArticle.getId(), article.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void update() {
        try {
            article.setBoardId(board.getId());
            article.setTitle("제목입니다.");
            article.setContent("내용입니다.");
            article.setAuthorId(user.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            article.setDeleted(false);
            article = articleService.write(article);
            String title = "수정된 제목입니다.";
            article.setTitle(title);
            Article updatedArticle = articleService.update(article);
            assertEquals(updatedArticle.getTitle(), title);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void updateDeleteFlag() {
        try {
            article.setBoardId(board.getId());
            article.setTitle("제목입니다.");
            article.setContent("내용입니다.");
            article.setAuthorId(user.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            article.setDeleted(false);
            article = articleService.write(article);
            boolean result = articleService.updateDeleteFlag(article);
            assertEquals(result, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void delete() {
//
//    }

    @Test
    public void findAllArticles() {
        try {
            String title = "찾아야 하는 제목입니다.";
            article.setBoardId(board.getId());
            article.setTitle(title);
            article.setContent("내용입니다.");
            article.setAuthorId(user.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            article.setDeleted(false);
            article = articleService.write(article);
            List<ArticleListDto> articlesDto = articleService.findAllArticles();
            ArticleListDto article = articlesDto.stream()
                    .filter(dto -> dto.getTitle().equals(title))
                    .findAny()
                    .get();
            assertEquals(article.getTitle(), title);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void findAllDeletedArticles(){
//        
//    }

    @Test
    public void findArticlesByBoardId() {
        try {
            String title = "찾아야 하는 제목입니다.";
            article.setBoardId(board.getId());
            article.setTitle(title);
            article.setContent("내용입니다.");
            article.setAuthorId(user.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            article.setDeleted(false);
            article = articleService.write(article);
            List<ArticleListDto> articlesDto = articleService.findArticlesByBoardId(article.getBoardId());
            ArticleListDto article = articlesDto.stream()
                    .filter(dto -> dto.getTitle().equals(title))
                    .findAny()
                    .get();
            assertEquals(article.getTitle(), title);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void findArticleByBoardId() {
//
//    }

    @Test
    public void findById() {
        try {
            article.setBoardId(board.getId());
            article.setTitle("제목입니다.");
            article.setContent("내용입니다.");
            article.setAuthorId(user.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            article.setDeleted(false);
            article = articleService.write(article);
            Article findArticle = articleService.findById(article.getId());
            assertEquals(findArticle.getId(), article.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void isDeleted() {
        try {
            article.setBoardId(board.getId());
            article.setTitle("제목입니다.");
            article.setContent("내용입니다.");
            article.setAuthorId(user.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            article.setDeleted(false);
            article = articleService.write(article);
            articleService.updateDeleteFlag(article);
            boolean result = articleService.isDeleted(article);
            assertEquals(result, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
