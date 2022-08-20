package org.platanus.webboard.controller.board.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.board.dto.CommentViewDto;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentRestControllerV1 {
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable("commentId") long commentId) {
        Comment comment;
        User user;
        try {
            comment = commentService.findById(commentId);
            user = userService.findById(comment.getAuthorId());
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        CommentViewDto commentDto = CommentViewDto.from(comment, user.getNickname());
        return ResponseEntity.ok(commentDto);
    }
}
