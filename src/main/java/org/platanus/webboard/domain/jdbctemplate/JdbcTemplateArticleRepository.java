package org.platanus.webboard.domain.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.ArticleRepository;
import org.platanus.webboard.domain.jdbctemplate.constant.QueryConstant;
import org.springframework.data.domain.Pageable;
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
public class JdbcTemplateArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ARTICLES").usingGeneratedKeyColumns("id");
    }

    public Article save(Article article) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("board_id", article.getBoardId());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("author_id", article.getAuthorId());
        parameters.put("created_date", article.getCreatedDate());
        parameters.put("modified_date", article.getModifiedDate());
        parameters.put("deleted", article.isDeleted());
        parameters.put("recommend", article.getRecommend());
        parameters.put("view_count", article.getViewCount());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
        return article;
    }

    public int deleteByBoardId(long boardId) {
        return jdbcTemplate.update(QueryConstant.ARTICLE_DELETE_BY_BOARD_ID, boardId);
    }

    public int delete(Article article) {
        return jdbcTemplate.update(QueryConstant.ARTICLE_DELETE, article.getId());
    }

    public int update(Article article) {
        return jdbcTemplate.update(QueryConstant.ARTICLE_UPDATE,
                article.getTitle(), article.getContent(), article.getModifiedDate(), article.getId());
    }

    public int updateViewCount(long id) {
        return jdbcTemplate.update(QueryConstant.ARTICLE_UPDATE_VIEW_COUNT, id);
    }

    public int updateRecommend(long id) {
        return jdbcTemplate.update(QueryConstant.ARTICLE_UPDATE_RECOMMEND, id);
    }

    public int updateDeleteFlag(Article article) {
        return jdbcTemplate.update(QueryConstant.ARTICLE_UPDATE_DELETE_FLAG, article.isDeleted(), article.getId());
    }

    public Optional<Article> findById(long id) {
        List<Article> result = jdbcTemplate.query(QueryConstant.ARTICLE_FIND_BY_ID, articleRowMapper(), id);
        return result.stream().findAny();
    }

    public List<Article> findByBoardId(long id) {
        return jdbcTemplate.query(QueryConstant.ARTICLE_FIND_BY_BOARD_ID, articleRowMapper(), id);
    }

    public List<Article> findByBoardIdPagination(Pageable page, long boardId) {
//        Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("ID");
        return jdbcTemplate.query(QueryConstant.ARTICLE_FIND_BY_BOARD_ID_PAGE,
                articleRowMapper(), boardId, page.getPageSize(), page.getOffset());
    }

    public List<Article> findAll() {
        return jdbcTemplate.query(QueryConstant.ARTICLE_FIND_ALL, articleRowMapper());
    }

    public List<Article> findByAuthorId(long id) {
        return jdbcTemplate.query(QueryConstant.ARTICLE_FIND_BY_AUTHOR_ID, articleRowMapper(), id);
    }

    public List<Article> findByTitle(String title) {
        return jdbcTemplate.query(QueryConstant.ARTICLE_FIND_BY_TITLE, articleRowMapper(), likeWrapper(title));
    }

    public List<Article> findByContent(String content) {
        return jdbcTemplate
                .query(QueryConstant.ARTICLE_FIND_BY_CONTENT, articleRowMapper(), likeWrapper(content));
    }

    public List<Article> findByTitleAndContent(String title, String content) {
        return jdbcTemplate.query(QueryConstant.ARTICLE_FIND_BY_TITLE_AND_CONTENT,
                articleRowMapper(), likeWrapper(title), likeWrapper(content));
    }

    public void allDelete() {
        jdbcTemplate.update(QueryConstant.ARTICLE_ALL_DELETE);
    }

    public int count(long boardId) {
        return jdbcTemplate.queryForObject(QueryConstant.ARTICLE_COUNT, Integer.class, boardId);
    }

    public String likeWrapper(String string) {
        return "%" + string + "%";
    }

    public RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> Article.builder()
                .id(rs.getLong("id"))
                .boardId(rs.getLong("board_id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .authorId(rs.getLong("author_id"))
                .createdDate(rs.getTimestamp("created_date").toLocalDateTime())
                .modifiedDate(rs.getTimestamp("modified_date").toLocalDateTime())
                .deleted(rs.getBoolean("deleted"))
                .recommend(rs.getLong("recommend"))
                .viewCount(rs.getLong("view_count"))
                .build();
    }


}
