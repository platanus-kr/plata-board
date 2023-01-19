package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRecommendRepository extends JpaRepository<ArticleRecommend, Long> {
    // 기본 구현
//    ArticleRecommend save(ArticleRecommend articleRecommend);

    Optional<ArticleRecommend> findByArticleIdAndUserId(long articleId, long userId);

    List<ArticleRecommend> findByArticleId(long articleId);

    List<ArticleRecommend> findByUserId(long userId);

    int countByArticleId(long articleId);

    int countByUserId(long userId);
}
