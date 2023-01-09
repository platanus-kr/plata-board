package org.platanus.webboard.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //Board save(Board board);

    //int delete(Board board);

    // save로 대체
    //int update(Board board);

    List<Board> findAll();

    Optional<Board> findById(long id);

    Optional<Board> findByName(String name);

    // deleteAll로 변경하기
    //void allDelete();
}
