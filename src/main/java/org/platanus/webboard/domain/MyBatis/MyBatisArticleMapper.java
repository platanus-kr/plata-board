package org.platanus.webboard.domain.MyBatis;

import org.apache.ibatis.annotations.Mapper;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.ArticleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MyBatisArticleMapper extends ArticleRepository {
    @Override
    Article save(@Param("article") Article article);

    @Override
    int deleteByBoardId(@Param("boardId") long boardId);

    @Override
    int delete(@Param("article") Article article);

    @Override
    int update(@Param("article") Article article);

    @Override
    int updateViewCount(@Param("id") long id);

    @Override
    int updateRecommend(@Param("id") long id);

    @Override
    int updateDeleteFlag(@Param("article") Article article);

    @Override
    Optional<Article> findById(@Param("id") long id);

    @Override
    List<Article> findByBoardId(@Param("id") long id);

    @Override
    List<Article> findByBoardIdPagination(@Param("page") Pageable page, @Param("boardId") long boardId);

    @Override
    List<Article> findAll();

    @Override
    List<Article> findByAuthorId(@Param("id") long id);

    @Override
    List<Article> findByTitle(@Param("title") String title);

    @Override
    List<Article> findByContent(@Param("content") String content);

    @Override
    List<Article> findByTitleAndContent(@Param("title") String title, @Param("content") String content);

    @Override
    void allDelete();

    @Override
    int count(@Param("boardId") long boardId);
}
