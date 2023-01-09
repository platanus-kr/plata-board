package org.platanus.webboard.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 기본 구현.
    //Comment save(Comment comment);

    // 마찬가지
    //int delete(Comment comment);

    int deleteByBoardId(long boardId);

    // save로 통합
    //int update(Comment comment);

    // 마찬가지
    //int updateDeleteFlag(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findByArticleId(long id);

    // 이건 카운트 하는 방법 찾아서 바꾸기.
    //int findCountByArticleId(long id);

    List<Comment> findByContent(String content);

    List<Comment> findAll();
}

