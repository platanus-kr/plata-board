package org.platanus.webboard.domain;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.utils.QueryConst;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateArticleRecommendRepository implements ArticleRecommendRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ARTICLES_RECOMMEND").usingGeneratedKeyColumns("id");
    }

    public ArticleRecommend save(ArticleRecommend articleRecommend) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("article_id", articleRecommend.getArticleId());
        parameters.put("user_id", articleRecommend.getUserId());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        articleRecommend.setId(key.longValue());
        return articleRecommend;
    }

    public Optional<ArticleRecommend> findByArticleIdAndUserId(long articleId, long userId) {
        List<ArticleRecommend> result = jdbcTemplate.query(QueryConst.ARTICLE_RECOMMEND_FIND_BY_ARTICLE_ID_AND_USER_ID,
                articleRecommendRowMapper(), articleId, userId);
        return result.stream().findAny();
    }

    public List<ArticleRecommend> findByArticleId(long articleId) {
        return jdbcTemplate.query(QueryConst.ARTICLE_RECOMMEND_FIND_BY_ARTICLE_ID, articleRecommendRowMapper(), articleId);
    }

    public List<ArticleRecommend> findByUserId(long userId) {
        return jdbcTemplate.query(QueryConst.ARTICLE_RECOMMEND_FIND_BY_USER_ID, articleRecommendRowMapper(), userId);
    }

    public int countByArticleId(long articleId) {
        return jdbcTemplate.queryForObject(QueryConst.ARTICLE_RECOMMEND_COUNT_BY_ARTICLE_ID, Integer.class, articleId);
    }

    public int countByUserId(long userId) {
        return jdbcTemplate.queryForObject(QueryConst.ARTICLE_RECOMMEND_COUNT_BY_USER_ID, Integer.class, userId);
    }

    public RowMapper<ArticleRecommend> articleRecommendRowMapper() {
        return (rs, rowNum) -> {
            ArticleRecommend articleRecommend = new ArticleRecommend();
            articleRecommend.setId(rs.getLong("id"));
            articleRecommend.setArticleId(rs.getLong("article_id"));
            articleRecommend.setUserId(rs.getLong("user_id"));
            return articleRecommend;
        };
    }
}
