package org.platanus.webboard.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.CommentRepository;
import org.platanus.webboard.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment write(Comment comment) throws Exception {
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        comment.setDeleted(false);
        comment = commentRepository.save(comment);
        log.info(MessageConstant.COMMENT_WRITE_SUCCESS_LOG, comment.getId(), comment.getAuthorId());
        return comment;
    }

    @Override
    public Comment update(Comment comment, User user) throws Exception {
        comment.setCreatedDate(findById(comment.getId()).getCreatedDate());
        comment.setModifiedDate(LocalDateTime.now());
//        Optional<Comment> getComment = commentRepository.findById(comment.getId());
//        if (getComment.isEmpty()) {
//            log.info("Comment update #{}: 없는 댓글 입니다.", comment.getId());
//            throw new IllegalArgumentException("없는 댓글 입니다.");
//        }
        if (comment.getAuthorId() != user.getId()) {
            log.info(MessageConstant.COMMENT_NOT_OWNER_LOG, comment.getId(), user.getId());
            throw new IllegalArgumentException(MessageConstant.COMMENT_NOT_AUTHOR);
        }
        if (commentRepository.update(comment) != 1) {
            log.error(MessageConstant.COMMENT_UPDATE_FAILED, comment.getId());
            throw new IllegalArgumentException(MessageConstant.COMMON_DATABASE_ERROR);
        }
        log.info(MessageConstant.COMMENT_UPDATE_SUCCESS_LOG, comment.getId(), user.getId());
        return comment;
    }

    @Override
    public boolean updateDeleteFlag(Comment comment, User user) throws Exception {
        if (commentRepository.findById(comment.getId()).get().isDeleted()) {
            log.info(MessageConstant.COMMENT_ALREADY_DELETE_FLAG_LOG, comment.getId());
            throw new IllegalArgumentException(MessageConstant.COMMENT_ALREADY_DELETED);
        }
        if (comment.getAuthorId() != user.getId()) {
            log.info(MessageConstant.COMMENT_NOT_OWNER_BY_DELETE_FLAG_LOG, comment.getId(), user.getId());
            throw new IllegalArgumentException(MessageConstant.COMMENT_NOT_AUTHOR);
        }
        comment.setDeleted(true);
        if (commentRepository.updateDeleteFlag(comment) != 1) {
            log.error(MessageConstant.COMMENT_FAILED_DELETE_FLAG_LOG, comment.getId());
            throw new IllegalArgumentException(MessageConstant.COMMON_DATABASE_ERROR);
        }
        log.info(MessageConstant.COMMENT_SUCCESS_DELETE_FLAG_LOG, comment.getId(), user.getId());
        return true;
    }

    @Override
    public void delete(Comment comment) throws Exception {
        if (commentRepository.delete(comment) != 1) {
            log.info(MessageConstant.COMMENT_FAILED_DELETE_LOG, comment.getId());
            throw new IllegalArgumentException(MessageConstant.COMMON_DATABASE_DELETE_ERROR);
        }
        log.info(MessageConstant.COMMENT_SUCCESS_DELETE_LOG, comment.getId());
    }

    @Override
    public void deleteByBoardId(long boardId) {
        commentRepository.deleteByBoardId(boardId);
    }

    @Override
    public List<Comment> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> returnComments = new ArrayList<>();
        comments.stream().filter(c -> !c.isDeleted()).forEach(c -> returnComments.add(c));
        return returnComments;
    }

    @Override
    public List<Comment> findAllDeletedComments() {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> returnComments = new ArrayList<>();
        comments.stream().filter(c -> c.isDeleted()).forEach(c -> returnComments.add(c));
        return returnComments;
    }

    @Override
    public List<Comment> findCommentsByArticleId(long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments;
    }

    @Override
    public Comment findById(long id) throws Exception {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty() || comment.get().isDeleted()) {
            log.info(MessageConstant.COMMENT_NOT_FOUND_BY_ID_LOG, id);
            throw new IllegalArgumentException(MessageConstant.COMMENT_NOT_FOUND);
        }
        return comment.get();
    }

    @Override
    public int countByArticleId(long articleId) {
        return commentRepository.findCountByArticleId(articleId);
    }

    @Override
    public boolean isDeleted(Comment comment) throws Exception {
        Comment returnComment = commentRepository.findById(comment.getId()).get();
        return returnComment.isDeleted();
    }
}
