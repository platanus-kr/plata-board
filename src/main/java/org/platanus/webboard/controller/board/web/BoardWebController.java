package org.platanus.webboard.controller.board.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.dto.ArticleListDto;
import org.platanus.webboard.controller.board.dto.ArticleWriteDto;
import org.platanus.webboard.controller.login.argumentresolver.Login;
import org.platanus.webboard.controller.login.dto.UserSessionDto;
import org.platanus.webboard.domain.Article;
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

    @GetMapping(value = "/{id}")
    public String list(@PathVariable("id") long boardId,
                       @RequestParam(value = "page", defaultValue = "1", required = false) int pageNum,
                       Model model) throws Exception {
        Page<ArticleListDto> articles = articleService.findPageOfArticlesByBoardId(boardId, pageNum - 1);
        String boardName = boardService.findById(boardId).getName();
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
        Article article = Article.builder()
                .boardId(id)
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .authorId(user.getId())
                .build();
        try {
            article = articleService.write(article);
        } catch (Exception e) {
            log.info("Board write error : {}", e.getMessage());
        }
        log.info("Board write #{}: {} by User #{}", id, article.getTitle(), user.getId());
        return "redirect:/article/" + article.getId();
    }

}
