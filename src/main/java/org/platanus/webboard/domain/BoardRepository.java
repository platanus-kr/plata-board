package org.platanus.webboard.domain;

import lombok.RequiredArgsConstructor;
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
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public Board save(Board board) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("boards").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", board.getName());
        parameters.put("description", board.getDescription());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        board.setId(key.longValue());
        return board;
    }

    public List<Board> findAll() {
        return jdbcTemplate.query("select * from BOARDS", boardRowMapper());
    }

    public int delete(Board board) {
        return jdbcTemplate.update("delete from BOARDS where ID = ?", board.getId());
    }

    public int update(Board board) {
        return jdbcTemplate.update("update BOARDS set NAME = ?, DESCRIPTION = ?  where ID = ?",
                board.getName(), board.getDescription(), board.getId());
    }

    public Optional<Board> findById(long id) {
        List<Board> result = jdbcTemplate
                .query("select * from BOARDS where ID = ?", boardRowMapper(), id);
        return result.stream().findAny();
    }

    public Optional<Board> findByName(String name) {
        List<Board> result = jdbcTemplate
                .query("select * from BOARDS where NAME = ?", boardRowMapper(), name);
        return result.stream().findAny();
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getLong("id"));
            board.setName(rs.getString("name"));
            board.setDescription(rs.getString("description"));
            return board;
        };
    }
}
