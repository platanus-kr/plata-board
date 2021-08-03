package org.platanus.webboard.controller.web.board;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.dto.ArticleListDto;
import org.platanus.webboard.dto.ArticleViewDto;
import org.platanus.webboard.dto.ArticleWriteDto;
import org.platanus.webboard.dto.CommentViewDto;
import org.platanus.webboard.service.ArticleService;
import org.platanus.webboard.service.BoardService;
import org.platanus.webboard.service.CommentService;
import org.platanus.webboard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardWebController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{id}")
    public String list(@PathVariable("id") long boardId, Model model) throws Exception {
        List<ArticleListDto> articlesByBoardId = articleService.findArticlesByBoardId(boardId);
        String boardName = boardService.findById(boardId).getName();
        model.addAttribute("articles", articlesByBoardId);
        model.addAttribute("board_id", boardId);
        model.addAttribute("board_name", boardName);
        return "board/boardList";
    }

    @GetMapping(value = "/{id}/write")
    public String writeForm(@PathVariable("id") long id, Model model) throws Exception {
        String boardName = boardService.findById(id).getName();
        model.addAttribute("board_id", id);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", new ArticleWriteDto());
        return "board/boardWrite";
    }

    @PostMapping(value = "/{id}/write")
    public String write(@PathVariable("id") long id, @ModelAttribute("article") ArticleWriteDto articleRequest) {
        Article article = new Article();
        article.setBoardId(id);
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        // todo: 로그인 기능 구현해서 꼭 채우기
        article.setAuthorId(1L);

        try {
            articleService.write(article);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/board/{id}";
    }

    @GetMapping(value = "/{boardId}/article/{articleId}")
    public String view(@PathVariable("boardId") long boardId,
                       @PathVariable("articleId") long articleId, Model model) throws Exception {
        ArticleViewDto articleResponse = new ArticleViewDto();
        List<CommentViewDto> commentsResponse = new ArrayList<>();

        Article article = articleService.findById(articleId);
        articleResponse.setBoardId(article.getBoardId());
        articleResponse.setId(article.getId());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        articleResponse.setAuthorId(article.getAuthorId());
        articleResponse.setAuthorNickname(userService.findById(article.getAuthorId()).getNickname());
        articleResponse.setCreatedDate(article.getCreatedDate());
        articleResponse.setModifiedDate(article.getModifiedDate());

        List<Comment> comments = commentService.findCommentsByArticleId(articleId);
        comments.stream().forEach(c -> {
            try {
                CommentViewDto commentResponse = new CommentViewDto();
                commentResponse.setId(c.getId());
                commentResponse.setArticleId(c.getArticleId());
                commentResponse.setContent(c.getContent());
                commentResponse.setAuthorId(c.getAuthorId());
                commentResponse.setAuthorNickname(userService.findById(c.getAuthorId()).getNickname());
                commentResponse.setCreatedDate(c.getCreatedDate());
                commentResponse.setModifiedDate(c.getModifiedDate());
                commentsResponse.add(commentResponse);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });

        String boardName = boardService.findById(boardId).getName();

        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", articleResponse);
        model.addAttribute("comments", commentsResponse);
        return "board/boardView";
    }

    @PostMapping(value = "/{boardId}/article/{articleId}/modify")
    public String articleModify(@PathVariable("boardId") long boardId,
                                @PathVariable("articleId") long articleId,
                                @ModelAttribute("article") ArticleWriteDto articleRequest) throws Exception {

        Article article = articleService.findById(articleId);
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());

        articleService.update(article);

        return "redirect:/board/{boardId}/article/{articleId}";

    }

    @GetMapping(value = "/{boardId}/article/{articleId}/modify")
    public String articleModifyView(@PathVariable("boardId") long boardId,
                                    @PathVariable("articleId") long articleId, Model model) throws Exception {
        ArticleViewDto articleResponse = new ArticleViewDto();
        Article article = articleService.findById(articleId);
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        String boardName = boardService.findById(boardId).getName();

        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", articleResponse);
        return "board/boardModify";

    }

    @PostMapping(value = "/{boardId}/article/{articleId}")
    public String commentWrite(@PathVariable("boardId") long boardId, @PathVariable("articleId") long articleId,
                               @ModelAttribute("comment") String commentRequest) {
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        // todo: 로그인 기능 구현해서 꼭 채우기
        comment.setAuthorId(1L);
        comment.setContent(commentRequest);

        try {
            commentService.write(comment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/board/{boardId}/article/{articleId}";
    }


}
