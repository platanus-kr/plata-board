package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/comment")
public class CommentWebController {
    private final CommentService commentService;

    @GetMapping(value = "/{commentId}/delete")
    public String remove(@PathVariable("commentId") long commentId, @Login User user) throws Exception {
        Comment comment = commentService.findById(commentId);
        long redirectArticleId = comment.getArticleId();
        if (!commentService.updateDeleteFlag(comment, user))
            System.out.println("삭제에 문제가 생겼습니다.");
        return "redirect:/article/" + redirectArticleId;

    }
}
