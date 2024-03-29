package org.platanus.webboard.controller.board.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.ArticleRecommendService;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.board.dto.ArticleResponseDto;
import org.platanus.webboard.controller.board.dto.ArticleWriteDto;
import org.platanus.webboard.controller.board.dto.CommentWriteDto;
import org.platanus.webboard.controller.board.exception.ErrorDto;
import org.platanus.webboard.controller.board.utils.MarkdownParser;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.ArticleRecommend;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/article")
public class ArticleWebController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final ArticleRecommendService articleRecommendService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{articleId}")
    public String view(@PathVariable("articleId") long articleId,
                       Model model) throws Exception {
        Article article = articleService.findById(articleId);
        articleService.updateViewCount(articleId);
        article.setContent(MarkdownParser.from(article.getContent()));
        String authorNickname = userService.findById(article.getAuthorId()).getNickname();
        List<Comment> commentsResponse = commentService.findCommentsByArticleId(articleId);
        commentsResponse.stream().forEach(c -> {
            c.setContent(MarkdownParser.from(c.getContent()));
        });
        String boardName = boardService.findById(article.getBoardId()).getName();
        String boardId = String.valueOf(article.getBoardId());

        CommentWriteDto commentWriteDto = new CommentWriteDto();
        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", ArticleResponseDto.fromView(article, authorNickname));
        model.addAttribute("comment", commentWriteDto);
        model.addAttribute("comments", commentsResponse);
        model.addAttribute("comments_quantity", commentsResponse.size());
        return "board/board_view";
    }

    @GetMapping(value = "/{articleId}/delete")
    public String remove(@PathVariable("articleId") long articleId,
                         //@Login UserSessionDto user,
                         @AuthenticationPrincipal Object principal) throws Exception {
        Article article = articleService.findById(articleId);
        long redirectBoardId = article.getBoardId();
//        User userFromDto = User.fromLoginSessionDto(user);
        User userFromDto = userService.findByUsername(principal.toString());
        if (!articleService.updateDeleteFlag(article, userFromDto))
            log.info("ArticleController remove #{}: Service Error.", article.getId());
        return "redirect:/board/" + redirectBoardId;
    }

    @GetMapping(value = "/{articleId}/modify")
    public String articleModifyView(@PathVariable("articleId") long articleId,
                                    //@Login UserSessionDto user,
                                    @AuthenticationPrincipal Object principal,
                                    Model model) throws Exception {
//        if (user.getId() != articleService.findById(articleId).getAuthorId()) {
        long authorId = articleService.findById(articleId).getAuthorId();
        User userByAuthor = userService.findById(authorId);
        if (principal.toString().equals(userByAuthor.getUsername())) {
            log.info("ArticleController modify #{}: 글쓴이가 아닙니다. by User {}", articleId, userByAuthor.getId());
            return "redirect:/article/{articleId}";
        }
        Article article = articleService.findById(articleId);
        String boardName = boardService.findById(article.getBoardId()).getName();
        String boardId = String.valueOf(article.getBoardId());
        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", ArticleResponseDto.fromModify(article));
        return "board/board_modify";
    }

    @PostMapping(value = "/{articleId}/modify")
    public String articleModify(@PathVariable("articleId") long articleId,
                                //@Login UserSessionDto user,
                                @AuthenticationPrincipal Object principal,
                                @Valid @ModelAttribute("article") ArticleWriteDto articleRequest,
                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "board/board_modify";
        }
        Article article = articleService.findById(articleId);
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
//        User userFromDto = User.fromLoginSessionDto(user);
        User userFromDto = userService.findByUsername(principal.toString());
        articleService.update(article, userFromDto);
        return "redirect:/article/{articleId}";
    }

    @PostMapping(value = "/{articleId}")
    public String commentWrite(@PathVariable("articleId") long articleId,
                               //@Login UserSessionDto user,
                               @AuthenticationPrincipal Object principal,
                               @Valid @ModelAttribute("comment") CommentWriteDto commentRequest,
                               BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("ArticleController comment-write #{} : {}", articleId, bindingResult);
            return "error/has_message";
        }
        User user = userService.findByUsername(principal.toString());
        Comment comment = Comment.builder()
                .articleId(articleId)
                .authorId(user.getId())
                .content(commentRequest.getContent())
                .build();
        try {
            commentService.write(comment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/article/{articleId}";
    }

    @GetMapping(value = "/{articleId}/recommend")
    public String articleRecommendUpdate(@PathVariable("articleId") long articleId,
                                         //@Login UserSessionDto user,
                                         @AuthenticationPrincipal Object principal,
                                         @ModelAttribute("error") ErrorDto errorDto) throws Exception {
        User user = userService.findByUsername(principal.toString());
        ArticleRecommend articleRecommend = ArticleRecommend.builder()
                .articleId(articleId)
                .userId(user.getId())
                .build();
        try {
            articleRecommendService.save(articleRecommend);
        } catch (Exception e) {
            log.info("ArticleController recommend-update #{} : 이미 추천한 게시물 by #{} {}", articleId, user.getId(), user.getUsername());
            errorDto.setErrorMessage("이미 추천한 게시물 입니다.");
            return "error/has_message";
        }
        return "redirect:/article/{articleId}";
    }
}
