package org.platanus.webboard.domain.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.ArticleRecommend;
import org.platanus.webboard.domain.ArticleRecommendRepository;
import org.platanus.webboard.domain.jdbctemplate.constant.QueryConstant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
//public class JdbcTemplateArticleRecommendRepository implements ArticleRecommendRepository {
public class JdbcTemplateArticleRecommendRepository {
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
        List<ArticleRecommend> result = jdbcTemplate.query(QueryConstant.ARTICLE_RECOMMEND_FIND_BY_ARTICLE_ID_AND_USER_ID,
                articleRecommendRowMapper(), articleId, userId);
        return result.stream().findAny();
    }

    public List<ArticleRecommend> findByArticleId(long articleId) {
        return jdbcTemplate.query(QueryConstant.ARTICLE_RECOMMEND_FIND_BY_ARTICLE_ID, articleRecommendRowMapper(), articleId);
    }

    public List<ArticleRecommend> findByUserId(long userId) {
        return jdbcTemplate.query(QueryConstant.ARTICLE_RECOMMEND_FIND_BY_USER_ID, articleRecommendRowMapper(), userId);
    }

    public int countByArticleId(long articleId) {
        return jdbcTemplate.queryForObject(QueryConstant.ARTICLE_RECOMMEND_COUNT_BY_ARTICLE_ID, Integer.class, articleId);
    }

    public int countByUserId(long userId) {
        return jdbcTemplate.queryForObject(QueryConstant.ARTICLE_RECOMMEND_COUNT_BY_USER_ID, Integer.class, userId);
    }

    public RowMapper<ArticleRecommend> articleRecommendRowMapper() {
        return (rs, rowNum) -> ArticleRecommend.builder()
                .id(rs.getLong("id"))
                .articleId(rs.getLong("article_id"))
                .userId(rs.getLong("user_id"))
                .build();
    }
}
