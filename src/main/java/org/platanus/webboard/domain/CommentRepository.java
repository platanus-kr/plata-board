package org.platanus.webboard.domain;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Comment;
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
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public Comment save(Comment comment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("comments").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("article_id", comment.getArticleId());
        parameters.put("content", comment.getContent());
        parameters.put("author_id", comment.getAuthorId());
        parameters.put("created_date", comment.getCreatedDate());
        parameters.put("modified_date", comment.getModifiedDate());
        parameters.put("deleted", comment.isDeleted());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        comment.setId(key.longValue());
        return comment;
    }

    public int delete(Comment comment) {
        return jdbcTemplate.update("delete from comments where id = ?", comment.getId());
    }

    public int update(Comment comment) {
        return jdbcTemplate.update(
                "update comments set CONTENT = ?, MODIFIED_DATE=? where ID = ?",
                comment.getContent(), comment.getModifiedDate(), comment.getId());
    }

    public int updateDeleteFlag(Comment comment) {
        return jdbcTemplate.update(
                "update comments set DELETED = ? where ID = ?",
                comment.isDeleted(), comment.getId());
    }

    public Optional<Comment> findById(long id) {
        List<Comment> result = jdbcTemplate
                .query("select * from comments where ID = ?", commentRowMapper(), id);
        return result.stream().findAny();
    }

    public List<Comment> findByArticleId(long id) {
        return jdbcTemplate.query("select * from comments where article_id = ?", commentRowMapper(), id);
    }


    public List<Comment> findByContent(String content) {
        return jdbcTemplate.query("select * from comments where content like ?", commentRowMapper(), "%" + content + "%");
    }

    public List<Comment> findAll() {
        return jdbcTemplate.query("select * from comments", commentRowMapper());
    }


    public RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setArticleId(rs.getLong("article_id"));
            comment.setContent(rs.getString("content"));
            comment.setAuthorId(rs.getLong("author_id"));
            comment.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            comment.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
            comment.setDeleted(rs.getBoolean("deleted"));
            return comment;
        };
    }
}
