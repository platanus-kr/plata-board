package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.board.dto.ArticleViewDto;
import org.platanus.webboard.web.board.dto.ArticleWriteDto;
import org.platanus.webboard.web.board.dto.CommentViewDto;
import org.platanus.webboard.web.board.dto.CommentWriteDto;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.platanus.webboard.web.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/article")
public class ArticleWebController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{articleId}")
    public String view(@PathVariable("articleId") long articleId, @Login User user, Model model) throws Exception {
        Article article = articleService.findById(articleId);
        String authorNickname = userService.findById(article.getAuthorId()).getNickname();
        List<CommentViewDto> commentsResponse = new ArrayList<>();
        commentService.findCommentsByArticleId(articleId).stream().forEach(c -> {
            try {
                commentsResponse.add(CommentViewDto.from(c, userService.findById(c.getAuthorId()).getNickname()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        String boardName = boardService.findById(article.getBoardId()).getName();
        String boardId = String.valueOf(article.getBoardId());
        CommentWriteDto commentWriteDto = new CommentWriteDto();
        model.addAttribute("user", user);
        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", ArticleViewDto.fromView(article, authorNickname));
        model.addAttribute("comment", commentWriteDto);
        model.addAttribute("comments", commentsResponse);
        return "board/board_view";
    }

    @GetMapping(value = "/{articleId}/delete")
    public String remove(@PathVariable("articleId") long articleId, @Login User user) throws Exception {
        Article article = articleService.findById(articleId);
        long redirectBoardId = article.getBoardId();
        if (!articleService.updateDeleteFlag(article, user))
            log.info("ArticleController remove #{}: Service Error.", article.getId());
        return "redirect:/board/" + redirectBoardId;
    }

    @GetMapping(value = "/{articleId}/modify")
    public String articleModifyView(@PathVariable("articleId") long articleId, @Login User user,
                                    Model model) throws Exception {
        if (user.getId() != articleService.findById(articleId).getAuthorId()) {
            log.info("ArticleController modify #{}: 글쓴이가 아닙니다. by User {}", articleId, user.getId());
            return "redirect:/article/{articleId}";
        }
        Article article = articleService.findById(articleId);
        String boardName = boardService.findById(article.getBoardId()).getName();
        String boardId = String.valueOf(article.getBoardId());
        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", ArticleViewDto.fromModify(article));
        return "board/board_modify";
    }

    @PostMapping(value = "/{articleId}/modify")
    public String articleModify(@PathVariable("articleId") long articleId, @Login User user,
                                @Valid @ModelAttribute("article") ArticleWriteDto articleRequest,
                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "board/board_modify";
        }
        Article article = articleService.findById(articleId);
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        articleService.update(article, user);
        return "redirect:/article/{articleId}";
    }

    @PostMapping(value = "/{articleId}")
    public String commentWrite(@PathVariable("articleId") long articleId, @Login User user,
                               @Valid @ModelAttribute("comment") CommentWriteDto commentRequest,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("ArticleController comment-write #{} : {}", articleId, bindingResult);
            return "error/has_message";
        }
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setAuthorId(user.getId());
        comment.setContent(commentRequest.getContent());
        try {
            commentService.write(comment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/article/{articleId}";
    }
}
