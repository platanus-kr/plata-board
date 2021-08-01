package org.platanus.webboard.service;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    public Comment write(Comment comment) throws Exception {
        articleService.findById(comment.getArticleId());
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment) throws Exception {
        Optional<Comment> getComment = commentRepository.findById(comment.getId());
        if (getComment.isEmpty())
            throw new IllegalArgumentException("없는 댓글 입니다.");
        if (commentRepository.update(comment) != 1)
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
        return comment;
    }

    public void updateDeleteFlag(Comment comment) throws Exception {
        if (commentRepository.findById(comment.getId()).get().isDeleted())
            throw new IllegalArgumentException("이미 삭제된 댓글 입니다.");
        comment.setDeleted(true);
        if (commentRepository.updateDeleteFlag(comment) != 1)
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
    }

    public void delete(Comment comment) throws Exception {
        if (commentRepository.delete(comment) != 1)
            throw new IllegalArgumentException("완전 삭제에 문제가 생겼습니다.");
    }

    public List<Comment> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> returnComments = new ArrayList<>();
        comments.stream().filter(c -> !c.isDeleted()).forEach(c -> returnComments.add(c));
        return returnComments;
    }

    public List<Comment> findAllDeletedComments() {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> returnComments = new ArrayList<>();
        comments.stream().filter(c -> c.isDeleted()).forEach(c -> returnComments.add(c));
        return returnComments;
    }

    public List<Comment> findCommentsByArticleId(long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        List<Comment> returnComments = new ArrayList<>();
        comments.stream().filter(c -> !c.isDeleted()).forEach(c -> returnComments.add(c));
        return returnComments;
    }

    public Comment findById(long id) throws Exception {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty() || comment.get().isDeleted())
            throw new IllegalArgumentException("없는 댓글 입니다.");
        return comment.get();
    }

    public boolean isDeleted(Comment comment) throws Exception {
        Comment returnComment = commentRepository.findById(comment.getId()).get();
        return returnComment.isDeleted();
    }
}
