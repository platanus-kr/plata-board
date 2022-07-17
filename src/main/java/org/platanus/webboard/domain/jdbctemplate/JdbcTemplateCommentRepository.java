package org.platanus.webboard.domain.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.CommentRepository;
import org.platanus.webboard.domain.utils.QueryConstant;
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
public class JdbcTemplateCommentRepository implements CommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("COMMENTS").usingGeneratedKeyColumns("id");
    }

    public Comment save(Comment comment) {
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
        return jdbcTemplate.update(QueryConstant.COMMENT_DELETE, comment.getId());
    }

    public int deleteByBoardId(long boardId) {
        return jdbcTemplate.
                update(QueryConstant.COMMENT_DELETE_BY_BOARD_ID, boardId);
    }

    public int update(Comment comment) {
        return jdbcTemplate.update(QueryConstant.COMMENT_UPDATE,
                comment.getContent(), comment.getModifiedDate(), comment.getId());
    }

    public int updateDeleteFlag(Comment comment) {
        return jdbcTemplate.update(QueryConstant.COMMENT_UPDATE_DELETE_FLAG, comment.isDeleted(), comment.getId());
    }

    public Optional<Comment> findById(long id) {
        List<Comment> result = jdbcTemplate.query(QueryConstant.COMMENT_FIND_BY_ID, commentRowMapper(), id);
        return result.stream().findAny();
    }

    public List<Comment> findByArticleId(long id) {
        return jdbcTemplate.query(QueryConstant.COMMENT_FIND_BY_ARTICLE_ID, commentRowMapper(), id);
    }

    public int findCountByArticleId(long id) {
        return jdbcTemplate.queryForObject(QueryConstant.COMMENT_FIND_COUNT_BY_ARTICLE_ID, Integer.class, id);
    }

    public List<Comment> findByContent(String content) {
        return jdbcTemplate.query(QueryConstant.COMMENT_FIND_BY_CONTENT, commentRowMapper(), "%" + content + "%");
    }

    public List<Comment> findAll() {
        return jdbcTemplate.query(QueryConstant.COMMENT_FIND_ALL, commentRowMapper());
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
            comment.setAuthorNickname(rs.getString("author_nickname"));
            return comment;
        };
    }


}
