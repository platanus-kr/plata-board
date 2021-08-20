package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.web.board.dto.ArticleListDto;
import org.platanus.webboard.web.board.dto.ArticleWriteDto;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.platanus.webboard.web.login.dto.UserSessionDto;
import org.platanus.webboard.web.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardWebController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{id}")
    public String list(@PathVariable("id") long boardId,
                       @RequestParam(value = "page", defaultValue = "1", required = false) int pageNum,
                       @Login UserSessionDto user,
                       Model model) throws Exception {
        Page<ArticleListDto> articles = articleService.findPageOfArticlesByBoardId(boardId, pageNum - 1);
        String boardName = boardService.findById(boardId).getName();
        model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        model.addAttribute("board_id", boardId);
        model.addAttribute("board_name", boardName);
        return "board/board_list";
    }

    @GetMapping(value = "/{id}/write")
    public String writeForm(@PathVariable("id") long id,
                            @Login UserSessionDto user,
                            Model model) throws Exception {
        String boardName = boardService.findById(id).getName();
        model.addAttribute("user", user);
        model.addAttribute("board_id", id);
        model.addAttribute("board_name", boardName);
        model.addAttribute("article", new ArticleWriteDto());
        return "board/board_write";
    }

    @PostMapping(value = "/{id}/write")
    public String write(@PathVariable("id") long id,
                        @Valid @ModelAttribute("article") ArticleWriteDto articleRequest,
                        BindingResult bindingResult,
                        @Login UserSessionDto user) {

        if (bindingResult.hasErrors()) {
            log.info("Board write #{} by User #{} : {}", id, user.getId(), bindingResult);
            return "board/board_write";
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
        log.info("Board write #{}: {} by User #{}", id, article.getTitle(), user.getId());
        return "redirect:/board/{id}";
    }

}
