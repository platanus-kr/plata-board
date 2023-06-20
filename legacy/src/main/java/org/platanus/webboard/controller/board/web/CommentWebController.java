package org.platanus.webboard.controller.board.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/comment")
public class CommentWebController {
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{commentId}/delete")
    public String remove(@PathVariable("commentId") long commentId,
                         //@Login UserSessionDto user,
                         @AuthenticationPrincipal Object principal) throws Exception {
        Comment comment = commentService.findById(commentId);
        long redirectArticleId = comment.getArticleId();
//        User userFromDto = User.fromLoginSessionDto(user);
        User user = userService.findByUsername(principal.toString());
        if (!commentService.updateDeleteFlag(comment, user)) {
            log.info("Comment Controller deleteflag #{} by User #{}", comment.getId(), user.getId());
        }
        log.info("Comment Controller deleteflag #{} by User #{}", comment.getId(), user.getId());
        return "redirect:/article/" + redirectArticleId;
    }
}
