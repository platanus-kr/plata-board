package org.platanus.webboard.controller.board;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.domain.ArticleRecommend;
import org.platanus.webboard.domain.ArticleRecommendRepository;
import org.platanus.webboard.domain.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleRecommendServiceImpl implements ArticleRecommendService {
    private final ArticleRecommendRepository articleRecommendRepository;
    private final ArticleRepository articleRepository;

    @Override
    public ArticleRecommend save(ArticleRecommend articleRecommend) {
        Optional<ArticleRecommend> findResult = articleRecommendRepository.findByArticleIdAndUserId(
                articleRecommend.getArticleId(), articleRecommend.getUserId());
        if (findResult.isPresent()) {
            throw new IllegalArgumentException(MessageConstant.ARTICLE_ALREADY_RECOMMEND);
        }
        ArticleRecommend result = articleRecommendRepository.save(articleRecommend);
        articleRepository.updateRecommend(articleRecommend.getArticleId());
        return result;
    }

    @Override
    public int countByArticleId(long articleId) {
        return articleRecommendRepository.countByArticleId(articleId);
    }

}
