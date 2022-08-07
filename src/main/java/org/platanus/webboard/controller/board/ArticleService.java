package org.platanus.webboard.controller.board;

import org.platanus.webboard.controller.board.dto.ArticleListDto;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {
    Article write(Article article) throws Exception;

    Article update(Article article, User user) throws Exception;

    boolean updateViewCount(long articleId);

    boolean updateDeleteFlag(Article article, User user) throws Exception;

    void deleteByBoardId(long boardId);

    List<ArticleListDto> findAllArticles();

    List<ArticleListDto> findAllDeletedArticles();

    List<ArticleListDto> findArticlesByBoardId(long boardId);

    Page<ArticleListDto> findPageOfArticlesByBoardId(long boardId, int pageNum);

    List<ArticleListDto> getArticleListDtos(List<Article> articles);

    Article findById(long id) throws Exception;

    boolean isDeleted(Article article) throws Exception;
}
