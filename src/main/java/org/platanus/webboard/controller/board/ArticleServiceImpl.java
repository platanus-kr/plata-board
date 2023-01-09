package org.platanus.webboard.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.controller.board.dto.ArticleListDto;
import org.platanus.webboard.controller.board.utils.PageConst;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.ArticleRepository;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
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
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    private final UserService userService;

    @Override
    public Article write(Article article) throws Exception {
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article.setRecommend(0L);
        article.setViewCount(0L);
        article = articleRepository.save(article);
        log.info(MessageConstant.ARTICLE_WRITE_SUCCESS, article.getId(), article.getAuthorId());
        return article;
    }

    @Override
    public Article update(Article article, User user) throws Exception {
//        article.setCreatedDate(findById(article.getId()).getCreatedDate());
        article.setModifiedDate(LocalDateTime.now());
//        Optional<Article> getArticle = articleRepository.findById(article.getId());
//        if (getArticle.isEmpty()) {
//            log.info("Article update #{}: 없는 게시물 입니다.", article.getId());
//            throw new IllegalArgumentException("없는 게시물 입니다.");
//        }
        if (article.getAuthorId() != user.getId()) {
            log.info(MessageConstant.ARTICLE_NOT_OWNER_UPDATE_LOG, article.getId());
            throw new IllegalArgumentException(MessageConstant.ARTICLE_NOT_AUTHOR);
        }
        if (articleRepository.update(article) != 1) {
            log.error(MessageConstant.ARTICLE_UPDATE_FAILED_LOG, article.getId());
            throw new IllegalArgumentException(MessageConstant.COMMON_DATABASE_ERROR);
        }
        log.info(MessageConstant.ARTICLE_UPDATE_SUCCESS, article.getId(), user.getId());
        return article;
    }

    @Override
    public boolean updateViewCount(long articleId) {
        articleRepository.updateViewCount(articleId);
        return true;
    }

    @Override
    public boolean updateDeleteFlag(Article article, User user) throws Exception {
        if (articleRepository.findById(article.getId()).get().isDeleted()) {
            log.info(MessageConstant.ARTICLE_ALREADY_DELETE_FLAG_LOG, article.getId());
            throw new IllegalArgumentException(MessageConstant.ARTICLE_ALREADY_DELETED);
        }
        if (article.getAuthorId() != user.getId()) {
            log.info(MessageConstant.ARTICLE_NOT_OWNER_DELETE_FLAG_LOG, article.getId());
            throw new IllegalArgumentException(MessageConstant.ARTICLE_NOT_AUTHOR);
        }
        List<Comment> comments = commentService.findCommentsByArticleId(article.getId());
        comments.stream().filter(c -> !c.isDeleted()).forEach(c -> {
            c.setDeleted(true);
            try {
                commentService.updateDeleteFlag(c, user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        article.setDeleted(true);
        if (articleRepository.updateDeleteFlag(article) != 1) {
            log.info(MessageConstant.ARTICLE_DELETE_FLAG_FAILED, article.getId());
            throw new IllegalArgumentException(MessageConstant.COMMON_DATABASE_ERROR);
        }
        log.info(MessageConstant.ARTICLE_DELETE_FLAG_SUCCESS, article.getId(), user.getId());
        return true;
    }

    @Override
    public void deleteByBoardId(long boardId) {
        commentService.deleteByBoardId(boardId);
        articleRepository.deleteByBoardId(boardId);
    }

//    @Override
//    public boolean delete(Article article) throws Exception {
//        List<Comment> comments = commentRepository.findByArticleId(article.getId());
//        comments.stream().forEach(c -> commentRepository.delete(c));
//        if (articleRepository.delete(article) != 1) {
//            log.info("Article delete #{}: Repository Error.", article.getId());
//            throw new IllegalArgumentException("완전 삭제에 문제가 생겼습니다.");
//        }
//        log.info("Article delete #{}", article.getId());
//        return true;
//    }

    @Override
    public List<ArticleListDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return getArticleListDtos(articles);
    }

    @Override
    public List<ArticleListDto> findAllDeletedArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> a.isDeleted()).forEach(a -> {
            try {
                int commentCount = commentService.countByArticleId(a.getId());
                returnArticles.add(ArticleListDto.from(a, userService.findById(a.getAuthorId()).getNickname(), commentCount));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    @Override
    public List<ArticleListDto> findArticlesByBoardId(long boardId) {
        List<Article> articles = articleRepository.findByBoardId(boardId);
        return getArticleListDtos(articles);
    }

    @Override
    public Page<ArticleListDto> findPageOfArticlesByBoardId(long boardId, int pageNum) {
        PageRequest pageable = PageRequest.of(pageNum, PageConst.PAGE_OFFSET);
        List<Article> articles = articleRepository.findByBoardId(boardId, pageable);
        List<ArticleListDto> returnArticles = getArticlesListDtoPage(articles);
        Long count = articleRepository.countById(boardId);
        return new PageImpl<ArticleListDto>(returnArticles, pageable, count);
    }

    private List<ArticleListDto> getArticlesListDtoPage(List<Article> articles) {
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> !a.isDeleted()).forEach(a -> {
            try {
                int commentCount = commentService.countByArticleId(a.getId());
                returnArticles.add(ArticleListDto.from(a, userService.findById(a.getAuthorId()).getNickname(), commentCount));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    @Override
    public List<ArticleListDto> getArticleListDtos(List<Article> articles) {
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> !a.isDeleted()).forEach(a -> {
            try {
                int commentCount = commentService.countByArticleId(a.getId());
                returnArticles.add(ArticleListDto.from(a, userService.findById(a.getAuthorId()).getNickname(), commentCount));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    @Override
    public Article findById(long id) throws Exception {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty() || article.get().isDeleted())
            throw new IllegalArgumentException(MessageConstant.ARTICLE_NOT_FOUND);
        return article.get();
    }

    @Override
    public boolean isDeleted(Article article) throws Exception {
        Article returnArticle = articleRepository.findById(article.getId()).get();
        return returnArticle.isDeleted();
    }
}
