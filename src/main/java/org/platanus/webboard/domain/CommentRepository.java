package org.platanus.webboard.domain;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    int delete(Comment comment);

    int deleteByBoardId(long boardId);

    int update(Comment comment);

    int updateDeleteFlag(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findByArticleId(long id);

    int findCountByArticleId(long id);

    List<Comment> findByContent(String content);

    List<Comment> findAll();
}

