package org.platanus.webboard.domain.MyBatis;

import org.apache.ibatis.annotations.Mapper;
import org.platanus.webboard.domain.ArticleRecommend;
import org.platanus.webboard.domain.ArticleRecommendRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MyBatisArticleRecommendMapper extends ArticleRecommendRepository {
    @Override
    ArticleRecommend save(@Param("articleRecommend") ArticleRecommend articleRecommend);

    @Override
    Optional<ArticleRecommend> findByArticleIdAndUserId(@Param("articleId") long articleId, @Param("userId") long userId);

    @Override
    List<ArticleRecommend> findByArticleId(@Param("articleId") long articleId);

    @Override
    List<ArticleRecommend> findByUserId(@Param("userId") long userId);

    @Override
    int countByArticleId(@Param("articleId") long articleId);

    @Override
    int countByUserId(@Param("userId") long userId);
}
