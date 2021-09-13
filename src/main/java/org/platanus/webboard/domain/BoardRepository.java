package org.platanus.webboard.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);

    int delete(Board board);

    int update(Board board);

    List<Board> findAll();

    Optional<Board> findById(long id);

    Optional<Board> findByName(String name);

    void allDelete();
}
