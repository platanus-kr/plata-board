package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.*;
import org.platanus.webboard.web.board.dto.ArticleListDto;
import org.platanus.webboard.web.board.utils.PageConst;
import org.platanus.webboard.web.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final BoardService boardService;
    private final UserService userService;

    public Article write(Article article) throws Exception {
        boardService.findById(article.getBoardId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article = articleRepository.save(article);
        log.info("Article write #{} by User #{}", article.getId(), article.getAuthorId());
        return article;
    }

    public Article update(Article article, User user) throws Exception {
        article.setCreatedDate(findById(article.getId()).getCreatedDate());
        article.setModifiedDate(LocalDateTime.now());
        Optional<Article> getArticle = articleRepository.findById(article.getId());
        if (getArticle.isEmpty()) {
            log.info("Article update #{}: 없는 게시물 입니다.", article.getId());
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        if (article.getAuthorId() != user.getId()) {
            log.info("Article update #{}: 작성자가 아닙니다.", article.getId());
            throw new IllegalArgumentException("작성자가 아닙니다");
        }
        if (articleRepository.update(article) != 1) {
            log.info("Article update #{}: Repository Error.", article.getId());
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
        }
        log.info("Article update #{} by User #{}", article.getId(), user.getId());
        return article;
    }

    public boolean updateDeleteFlag(Article article, User user) throws Exception {
        if (articleRepository.findById(article.getId()).get().isDeleted()) {
            log.info("Article deleteflag #{}: 이미 삭제된 게시물 입니다..", article.getId());
            throw new IllegalArgumentException("이미 삭제된 게시물 입니다.");
        }
        if (article.getAuthorId() != user.getId()) {
            log.info("Article deleteflag #{}: 작성자가 아닙니다.", article.getId());
            throw new IllegalArgumentException("작성자가 아닙니다");
        }
        List<Comment> comments = commentRepository.findByArticleId(article.getId());
        comments.stream().filter(c -> !c.isDeleted()).forEach(
                c -> {
                    c.setDeleted(true);
                    commentRepository.updateDeleteFlag(c);
                });
        article.setDeleted(true);
        if (articleRepository.updateDeleteFlag(article) != 1) {
            log.info("Article deleteflag #{}: Repository Error.", article.getId());
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
        }
        log.info("Article deleteflag #{} by User #{}", article.getId(), user.getId());
        return true;
    }

    public boolean delete(Article article) throws Exception {
        if (articleRepository.delete(article) != 1) {
            log.info("Article delete #{}: Repository Error.", article.getId());
            throw new IllegalArgumentException("완전 삭제에 문제가 생겼습니다.");
        }
        log.info("Article delete #{}", article.getId());
        return true;
    }

    public List<ArticleListDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return getArticleListDtos(articles);
    }

    public List<ArticleListDto> findAllDeletedArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> a.isDeleted()).forEach(a -> {
            try {
                int commentCount = commentRepository.findCountByArticleId(a.getId());
                returnArticles.add(ArticleListDto
                        .from(a, userService.findById(a.getAuthorId()).getNickname(), commentCount));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    public List<ArticleListDto> findArticlesByBoardId(long boardId) {
        List<Article> articles = articleRepository.findByBoardId(boardId);
        return getArticleListDtos(articles);
    }

    public Page<ArticleListDto> findPageOfArticlesByBoardId(long boardId, int pageNum) {
        PageRequest pageable = PageRequest.of(pageNum, PageConst.PAGE_OFFSET);
        List<Article> articles = articleRepository.findByBoardIdPagination(pageable, boardId);
        List<ArticleListDto> returnArticles = getArticlesListDtoPage(articles);
        return new PageImpl<ArticleListDto>(returnArticles, pageable, articleRepository.count(boardId));
    }

    private List<ArticleListDto> getArticlesListDtoPage(List<Article> articles) {
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> !a.isDeleted()).forEach(a -> {
            try {
                int commentCount = commentRepository.findCountByArticleId(a.getId());
                returnArticles.add(ArticleListDto
                        .from(a, userService.findById(a.getAuthorId()).getNickname(), commentCount));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    private List<ArticleListDto> getArticleListDtos(List<Article> articles) {
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> !a.isDeleted()).forEach(a -> {
            try {
                int commentCount = commentRepository.findCountByArticleId(a.getId());
                returnArticles.add(ArticleListDto
                        .from(a, userService.findById(a.getAuthorId()).getNickname(), commentCount));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    public Article findArticleByBoardId(long boardId, long id) throws Exception {
        boardService.findById(boardId);
        Article returnArticle = findById(id);
        return returnArticle;
    }

    public Article findById(long id) throws Exception {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty() || article.get().isDeleted())
            throw new IllegalArgumentException("없는 게시물 입니다.");
        return article.get();
    }

    public boolean isDeleted(Article article) throws Exception {
        Article returnArticle = articleRepository.findById(article.getId()).get();
        return returnArticle.isDeleted();
    }
}
