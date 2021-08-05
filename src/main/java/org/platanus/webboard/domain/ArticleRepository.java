package org.platanus.webboard.domain;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("articles").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("board_id", article.getBoardId());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("author_id", article.getAuthorId());
        parameters.put("created_date", article.getCreatedDate());
        parameters.put("modified_date", article.getModifiedDate());
        parameters.put("deleted", article.isDeleted());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
        return article;

    }

    public int delete(Article article) {
        return jdbcTemplate.update("delete from articles where id = ?", article.getId());
    }

    public int update(Article article) {
        return jdbcTemplate.update(
                "update articles set TITLE = ?, CONTENT = ?, MODIFIED_DATE = ? WHERE ID = ?",
                article.getTitle(), article.getContent(), article.getModifiedDate(), article.getId());
    }

    public int updateDeleteFlag(Article article) {
        return jdbcTemplate.update(
                "update articles set DELETED = ? WHERE ID = ?",
                article.isDeleted(), article.getId());
    }

    public Optional<Article> findById(long id) {
        List<Article> result = jdbcTemplate
                .query("select * from articles where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    public List<Article> findByBoardId(long id) {
        return jdbcTemplate.query("select * from articles where board_id = ? ", articleRowMapper(), id);
    }

    public List<Article> findAll() {
        return jdbcTemplate.query("select * from articles", articleRowMapper());
    }

    public List<Article> findByAuthorId(long id) {
        return jdbcTemplate.query("select * from articles where author_id = ?", articleRowMapper(), id);
    }

    public List<Article> findByTitle(String title) {
        return jdbcTemplate.query("select * from articles where title like ?", articleRowMapper(), "%" + title + "%");
    }

    public List<Article> findByContent(String content) {
        return jdbcTemplate.query("select * from articles where content like ?", articleRowMapper(), "%" + content + "%");
    }

    public List<Article> findByTitleAndContent(String title, String content) {
        return jdbcTemplate
                .query("select * from articles where title like ? or content like ?",
                        articleRowMapper(),
                        "%" + title + "%", "%" + content + "%");
    }

    public RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setBoardId(rs.getLong("board_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setAuthorId(rs.getLong("author_id"));
            article.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            article.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
            article.setDeleted(rs.getBoolean("deleted"));
            return article;
        };
    }

}
