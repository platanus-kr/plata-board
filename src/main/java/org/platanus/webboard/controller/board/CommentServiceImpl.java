package org.platanus.webboard.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Comment write #{} by User #{}", comment.getId(), comment.getAuthorId());
        return comment;
    }

    @Override
    public Comment update(Comment comment, User user) throws Exception {
        comment.setCreatedDate(findById(comment.getId()).getCreatedDate());
        comment.setModifiedDate(LocalDateTime.now());
        Optional<Comment> getComment = commentRepository.findById(comment.getId());
        if (getComment.isEmpty()) {
            log.info("Comment update #{}: 없는 댓글 입니다.", comment.getId());
            throw new IllegalArgumentException("없는 댓글 입니다.");
        }
        if (comment.getAuthorId() != user.getId()) {
            log.info("Comment update #{}: 작성자가 아닙니다. by User #{}", comment.getId(), user.getId());
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        if (commentRepository.update(comment) != 1) {
            log.info("Comment update #{}: Repository Error.", comment.getId());
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
        }
        log.info("Comment update #{} by User #{}", comment.getId(), user.getId());
        return comment;
    }

    @Override
    public boolean updateDeleteFlag(Comment comment, User user) throws Exception {
        if (commentRepository.findById(comment.getId()).get().isDeleted()) {
            log.info("Comment deleteflag #{}: 이미 삭제된 댓글 입니다.", comment.getId());
            throw new IllegalArgumentException("이미 삭제된 댓글 입니다.");
        }
        if (comment.getAuthorId() != user.getId()) {
            log.info("Comment deleteflag #{}: 작성자가 아닙니다. by User #{}", comment.getId(), user.getId());
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        comment.setDeleted(true);
        if (commentRepository.updateDeleteFlag(comment) != 1) {
            log.info("Comment deleteflag #{}: Repository Error.", comment.getId());
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
        }
        log.info("Comment deleteflag #{} by User #{}", comment.getId(), user.getId());
        return true;
    }

    @Override
    public void delete(Comment comment) throws Exception {
        if (commentRepository.delete(comment) != 1) {
            log.info("Comment delete #{}: Repository Error.", comment.getId());
            throw new IllegalArgumentException("완전 삭제에 문제가 생겼습니다.");
        }
        log.info("Comment delete #{}", comment.getId());
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
            log.info("Comment findById #{}: 없는 댓글 입니다.", id);
            throw new IllegalArgumentException("없는 댓글 입니다.");
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
