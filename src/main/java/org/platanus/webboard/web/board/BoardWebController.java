package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.board.dto.ArticleListDto;
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
    public String write(@PathVariable("id") long id,
                        @ModelAttribute("article") ArticleWriteDto articleRequest,
                        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        if (articleRequest.getTitle().trim().length() == 0 && articleRequest.getContent().trim().length() == 0) {
            System.out.println("빈 값이 있습니다.");
            return "redirect:/board/{id}/write";
        }

        Article article = new Article();
        article.setBoardId(id);
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        article.setAuthorId(user.getId());

        try {
            articleService.write(article);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/board/{id}";
    }

    @GetMapping(value = "/{boardId}/article/{articleId}")
    public String view(@PathVariable("boardId") long boardId,
                       @PathVariable("articleId") long articleId, Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) throws Exception {
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

        model.addAttribute("user", user);
        model.addAttribute("board_id", boardId);
        model.addAttribute("article_id", articleId);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", articleResponse);
        model.addAttribute("comments", commentsResponse);
        return "board/boardView";
    }

    @GetMapping(value = "/{boardId}/article/{articleId}/modify")
    public String articleModifyView(@PathVariable("boardId") long boardId,
                                    @PathVariable("articleId") long articleId,
                                    @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user,
                                    Model model) throws Exception {
        if (user.getId() != articleService.findById(articleId).getAuthorId()) {
            System.out.println("글쓴이가 아닙니다.");
            return "redirect:/board/{boardId}/article/{articleId}";
        }


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

    @PostMapping(value = "/{boardId}/article/{articleId}")
    public String commentWrite(@PathVariable("boardId") long boardId,
                               @PathVariable("articleId") long articleId,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user,
                               @ModelAttribute("comment") String commentRequest) {
        if (commentRequest.trim().length() == 0) {
            System.out.println("빈 값이 있습니다.");
            return "redirect:/board/{boardId}/article/{articleId}";
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
        return "redirect:/board/{boardId}/article/{articleId}";
    }


}
