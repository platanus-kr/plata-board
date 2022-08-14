package org.platanus.webboard.controller.board;

import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;

import java.util.List;

public interface CommentService {
    Comment write(Comment comment) throws Exception;

    Comment update(Comment comment, User user) throws Exception;

    boolean updateDeleteFlag(Comment comment, User user) throws Exception;

    void delete(Comment comment) throws Exception;

    void deleteByBoardId(long boardId);

    List<Comment> findAllComments();

    List<Comment> findAllDeletedComments();

    List<Comment> findCommentsByArticleId(long articleId);

    Comment findById(long id) throws Exception;

    int countByArticleId(long articleId);

    boolean isDeleted(Comment comment) throws Exception;
}
