package org.platanus.webboard.domain.MyBatis;

import org.apache.ibatis.annotations.Mapper;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.CommentRepository;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper extends CommentRepository {
    @Override
    Comment save(Comment comment);

    @Override
    int delete(Comment comment);

    @Override
    int deleteByBoardId(long boardId);

    @Override
    int update(Comment comment);

    @Override
    int updateDeleteFlag(Comment comment);

    @Override
    Optional<Comment> findById(long id);

    @Override
    List<Comment> findByArticleId(long id);

    @Override
    int findCountByArticleId(long id);

    @Override
    List<Comment> findByContent(String content);

    @Override
    List<Comment> findAll();
}
