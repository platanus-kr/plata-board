package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query(value = "update Board b set b.name = :name, b.description = :ddescription  where b.id = :id")
    int update(Board board);

    List<Board> findAll();

    Optional<Board> findById(long id);

    Optional<Board> findByName(String name);
}
