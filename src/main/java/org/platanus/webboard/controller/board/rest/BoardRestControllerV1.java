package org.platanus.webboard.controller.board.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.security.dto.UserClaimDto;
import org.platanus.webboard.config.security.permission.HasUserRole;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.dto.ArticleListDto;
import org.platanus.webboard.controller.board.dto.ArticleResponseDto;
import org.platanus.webboard.controller.board.dto.ArticleWriteDto;
import org.platanus.webboard.controller.board.dto.ArticlesResponseDto;
import org.platanus.webboard.controller.board.exception.BoardException;
import org.platanus.webboard.controller.board.exception.ErrorDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@Api(tags = {"게시판 관련 RST API Controller"})
public class BoardRestControllerV1 {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final UserService userService;

    /**
     * 게시판 조회<br />
     * 게시판에 포함된 게시글 목록 조회 </br />
     *
     * @param boardId
     * @param pageNum
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{id}")
    public ArticlesResponseDto list(@PathVariable("id") long boardId,
                                  @RequestParam(value = "page", defaultValue = "1", required = false) int pageNum) {
        String boardName;
        Page<ArticleListDto> articles;
        try {
            boardName = boardService.findById(boardId).getName();
            articles = articleService.findPageOfArticlesByBoardId(boardId, pageNum - 1);
        } catch (Exception e) {
            throw new BoardException(e.getMessage());
        }
        return ArticlesResponseDto.builder()
                .boardId(boardId)
                .boardName(boardName)
                .articles(articles)
                .build();
    }

    /**
     * 게시글 작성
     *
     * @param boardId
     * @param user
     * @param articleRequest
     * @return
     */
    @PostMapping(value = "/{id}/write")
    @HasUserRole
    public ArticleResponseDto write(@PathVariable("id") long boardId,
                                   @AuthenticationPrincipal UserClaimDto user,
                                   @Valid @RequestBody ArticleWriteDto articleRequest) {
        Article article = Article.fromWriteDto(articleRequest);
        article.setBoardId(boardId);
        article.setAuthorId(user.getId());
        Article writtenArticle;
        User findUser;
        try {
            findUser = userService.findById(user.getId());
            writtenArticle = articleService.write(article);
        } catch (Exception e) {
            throw new BoardException(e.getMessage());
        }
        return ArticleResponseDto.fromView(writtenArticle, findUser.getNickname());
    }
}
