package org.platanus.webboard.domain;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    int deleteByBoardId(long boardId);

    int delete(Article article);

    int update(Article article);

    int updateViewCount(long id);

    int updateRecommend(long id);

    int updateDeleteFlag(Article article);

    Optional<Article> findById(long id);

    List<Article> findByBoardId(long id);

    List<Article> findByBoardIdPagination(Pageable page, long boardId);

    List<Article> findAll();

    List<Article> findByAuthorId(long id);

    List<Article> findByTitle(String title);

    List<Article> findByContent(String content);

    List<Article> findByTitleAndContent(String title, String content);

    void allDelete();

    int count(long boardId);

}
