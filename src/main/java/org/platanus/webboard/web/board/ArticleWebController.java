package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.board.dto.ArticleViewDto;
import org.platanus.webboard.web.board.dto.ArticleWriteDto;
import org.platanus.webboard.web.board.dto.CommentViewDto;
import org.platanus.webboard.web.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/article")
public class ArticleWebController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{articleId}")
    public String view(@PathVariable("articleId") long articleId, Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) throws Exception {
        Article article = articleService.findById(articleId);
        String authorNickname = userService.findById(article.getAuthorId()).getNickname();
        List<CommentViewDto> commentsResponse = new ArrayList<>();
        commentService.findCommentsByArticleId(articleId).stream().forEach(c -> {
            try {
                commentsResponse.add(CommentViewDto.from(c, authorNickname));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        String boardName = boardService.findById(article.getBoardId()).getName();
        String boardId = String.valueOf(article.getBoardId());
        model.addAttribute("user", user);
        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", ArticleViewDto.fromView(article, authorNickname));
        model.addAttribute("comments", commentsResponse);
        return "board/boardView";
    }

    @GetMapping(value = "/{articleId}/modify")
    public String articleModifyView(@PathVariable("articleId") long articleId,
                                    @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user,
                                    Model model) throws Exception {
        if (user.getId() != articleService.findById(articleId).getAuthorId()) {
            System.out.println("글쓴이가 아닙니다.");
            return "redirect:/article/{articleId}";
        }
        Article article = articleService.findById(articleId);
        String boardName = boardService.findById(article.getBoardId()).getName();
        String boardId = String.valueOf(article.getBoardId());
        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", ArticleViewDto.fromModify(article));
        return "board/boardModify";
    }

    @PostMapping(value = "/{articleId}/modify")
    public String articleModify(@PathVariable("articleId") long articleId,
                                @ModelAttribute("article") ArticleWriteDto articleRequest) throws Exception {
        Article article = articleService.findById(articleId);
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        articleService.update(article);
        return "redirect:/article/{articleId}";

    }

    @PostMapping(value = "/{articleId}")
    public String commentWrite(@PathVariable("articleId") long articleId,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user,
                               @ModelAttribute("comment") String commentRequest) {
        if (commentRequest.trim().length() == 0) {
            System.out.println("빈 값이 있습니다.");
            return "redirect:/article/{articleId}";
        }
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setAuthorId(user.getId());
        comment.setContent(commentRequest);
        try {
            commentService.write(comment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/article/{articleId}";
    }
}
