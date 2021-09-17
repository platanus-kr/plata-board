package org.platanus.webboard.domain.MyBatis;

import org.apache.ibatis.annotations.Mapper;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.BoardRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MyBatisBoardMapper extends BoardRepository {

    @Override
    Board save(@Param("board") Board board);

    @Override
    int delete(@Param("board") Board board);

    @Override
    int update(@Param("board") Board board);

    @Override
    List<Board> findAll();

    @Override
    Optional<Board> findById(@Param("id") long id);

    @Override
    Optional<Board> findByName(@Param("name") String name);

    @Override
    void allDelete();
}
