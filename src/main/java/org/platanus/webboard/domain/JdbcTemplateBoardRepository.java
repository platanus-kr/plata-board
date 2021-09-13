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
public class JdbcTemplateBoardRepository implements BoardRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("BOARDS").usingGeneratedKeyColumns("id");
    }

    public Board save(Board board) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", board.getName());
        parameters.put("description", board.getDescription());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        board.setId(key.longValue());
        return board;
    }

    public int delete(Board board) {
        return jdbcTemplate.update(QueryConst.BOARD_DELETE, board.getId());
    }

    public int update(Board board) {
        return jdbcTemplate.update(QueryConst.BOARD_UPDATE, board.getName(), board.getDescription(), board.getId());
    }

    public List<Board> findAll() {
        return jdbcTemplate.query(QueryConst.BOARD_FIND_ALL, boardRowMapper());
    }

    public Optional<Board> findById(long id) {
        List<Board> result = jdbcTemplate.query(QueryConst.BOARD_FIND_BY_ID, boardRowMapper(), id);
        return result.stream().findAny();
    }

    public Optional<Board> findByName(String name) {
        List<Board> result = jdbcTemplate.query(QueryConst.BOARD_FIND_BY_NAME, boardRowMapper(), name);
        return result.stream().findAny();
    }

    public void allDelete() {
        jdbcTemplate.update(QueryConst.BOARD_ALL_DELETE);
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
