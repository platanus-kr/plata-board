package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.platanus.webboard.web.login.dto.UserSessionDto;
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

    @GetMapping(value = "/{commentId}/delete")
    public String remove(@PathVariable("commentId") long commentId,
                         @Login UserSessionDto user) throws Exception {
        Comment comment = commentService.findById(commentId);
        long redirectArticleId = comment.getArticleId();
        User userFromDto = User.fromLoginSessionDto(user);
        if (!commentService.updateDeleteFlag(comment, userFromDto)) {
            log.info("Comment Controller deleteflag #{} by User #{}", comment.getId(), user.getId());
        }
        log.info("Comment Controller deleteflag #{} by User #{}", comment.getId(), user.getId());
        return "redirect:/article/" + redirectArticleId;
    }
}
