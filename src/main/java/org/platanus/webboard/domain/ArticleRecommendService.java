package org.platanus.webboard.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleRecommendService {
    private final ArticleRecommendRepository articleRecommendRepository;
    private final ArticleRepository articleRepository;

    public ArticleRecommend save(ArticleRecommend articleRecommend) {
        Optional<ArticleRecommend> findResult = articleRecommendRepository.findByArticleIdAndUserId(
                articleRecommend.getArticleId(), articleRecommend.getUserId());
        if (findResult.isPresent()) {
            throw new IllegalArgumentException("이미 추천된 게시물 입니다");
        }
        ArticleRecommend result = articleRecommendRepository.save(articleRecommend);
        articleRepository.updateRecommend(articleRecommend.getArticleId());
        return result;
    }

}
