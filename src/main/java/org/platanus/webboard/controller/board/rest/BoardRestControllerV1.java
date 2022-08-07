package org.platanus.webboard.controller.board.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.dto.ArticleListDto;
import org.platanus.webboard.controller.board.dto.ArticleWriteDto;
import org.platanus.webboard.controller.board.dto.ArticlesResponseDto;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardRestControllerV1 {
    private final BoardService boardService;
    private final ArticleService articleService;

    /**
     * Return article list in a board by boardId
     *
     * @param boardId
     * @param pageNum
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity list(@PathVariable("id") long boardId, @RequestParam(value = "page", defaultValue = "1", required = false) int pageNum) throws Exception {
        String boardName;
        Page<ArticleListDto> articles;
        try {
            boardName = boardService.findById(boardId).getName();
            articles = articleService.findPageOfArticlesByBoardId(boardId, pageNum - 1);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        ArticlesResponseDto responseDto = ArticlesResponseDto.builder()
                .boardId(boardId)
                .boardName(boardName)
                .articles(articles)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Post article in a board by boardId
     *
     * @param boardId
     * @param articleRequest
     * @return
     */
    @PreAuthorize("hasRole(UserRole.USER)")
    @PostMapping(value = "/{id}/write")
    public ResponseEntity write(@PathVariable("id") long boardId,
                                @Valid @ModelAttribute("article") ArticleWriteDto articleRequest) {
        Article article = Article.fromWriteDto(articleRequest);
        try {
            articleService.write(article);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok(200);
    }
}
