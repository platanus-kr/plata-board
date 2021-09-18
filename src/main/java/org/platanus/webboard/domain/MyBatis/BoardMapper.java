package org.platanus.webboard.domain.MyBatis;

import org.apache.ibatis.annotations.Mapper;
import org.platanus.webboard.domain.Board;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    int save(@Param("board") Board board);

    int delete(@Param("board") Board board);

    int update(@Param("board") Board board);

    List<Board> findAll();

    Board findById(@Param("id") long id);

    Board findByName(@Param("name") String name);

    void allDelete();
}
