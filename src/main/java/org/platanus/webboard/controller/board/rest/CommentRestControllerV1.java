package org.platanus.webboard.controller.board.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.security.dto.UserClaimDto;
import org.platanus.webboard.config.security.permission.HasUserRole;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.board.dto.CommentViewDto;
import org.platanus.webboard.controller.board.dto.CommentWriteDto;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@Api(tags = {"댓글 관련 RST API Controller"})
public class CommentRestControllerV1 {
    private final CommentService commentService;
    private final UserService userService;

    /**
     * 코멘트 1개 조회<br />
     * 회원만 조회가 가능한 이유는 무분별한 크롤링 방지<br />
     *
     * @param commentId
     * @return
     */
    @GetMapping("/{commentId}")
    @HasUserRole
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

    /**
     * 코멘트 수정 <br />
     *
     * @param commentId
     * @param user
     * @return
     */
    @PostMapping("/{commentId}/update")
    @HasUserRole
    public ResponseEntity<?> updateComment(@PathVariable("commentId") long commentId,
                                           @AuthenticationPrincipal UserClaimDto user,
                                           @Valid @RequestBody CommentWriteDto commentRequest) {
        User userFromClaim = User.fromUserClaimDto(user);
        Comment comment;
        try {
            comment = commentService.findById(commentId);
            comment.setContent(commentRequest.getContent());
            comment = commentService.update(comment, userFromClaim);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok(comment);
    }

    /**
     * 코멘트 삭제 <br />
     *
     * @param commentId
     * @param user
     * @return
     */
    @DeleteMapping("/{commentId}")
    @HasUserRole
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") long commentId,
                                           @AuthenticationPrincipal UserClaimDto user) {
        User userFromClaim = User.fromUserClaimDto(user);
        Comment comment;
        try {
            comment = commentService.findById(commentId);
            commentService.updateDeleteFlag(comment, userFromClaim);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok().build();
    }
}
