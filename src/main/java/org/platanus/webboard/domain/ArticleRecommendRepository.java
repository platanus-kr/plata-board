package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRecommendRepository extends JpaRepository<ArticleRecommend, Long> {
    // 기본 구현
//    ArticleRecommend save(ArticleRecommend articleRecommend);

    Optional<ArticleRecommend> findByArticleIdAndUserId(long articleId, long userId);

    List<ArticleRecommend> findByArticleId(long articleId);

    List<ArticleRecommend> findByUserId(long userId);

    // 이거 어떻게 함?
    int countByArticleId(long articleId);

    // 이것도.
    int countByUserId(long userId);
}
