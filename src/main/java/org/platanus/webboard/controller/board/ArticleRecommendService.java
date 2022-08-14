package org.platanus.webboard.controller.board;

import org.platanus.webboard.domain.ArticleRecommend;

public interface ArticleRecommendService {
    ArticleRecommend save(ArticleRecommend articleRecommend);

    int countByArticleId(long articleId);
}
