package org.platanus.webboard.domain;

import java.util.List;
import java.util.Optional;

public interface ArticleRecommendRepository {
    ArticleRecommend save(ArticleRecommend articleRecommend);

    Optional<ArticleRecommend> findByArticleIdAndUserId(long articleId, long userId);

    List<ArticleRecommend> findByArticleId(long articleId);

    List<ArticleRecommend> findByUserId(long userId);

    int countByArticleId(long articleId);

    int countByUserId(long userId);
}
