package org.platanus.webboard.controller.board.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.ArticleRecommendService;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.board.dto.ArticleResponseDto;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleRestControllerV1 {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final ArticleRecommendService articleRecommendService;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * Get a article
     *
     * @param articleId
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/{articleId}")
    public ResponseEntity getArticle(@PathVariable("articleId") long articleId) {
        Article article;
        String authorNickname;
        List<Comment> commentsResponse;
        String boardName;
        String boardId;
        long recommendCount;
        try {
            article = articleService.findById(articleId);
            authorNickname = userService.findById(article.getAuthorId()).getNickname();
//            commentsResponse = commentService.findCommentsByArticleId(articleId);
            boardName = boardService.findById(article.getBoardId()).getName();
            recommendCount = articleRecommendService.countByArticleId(article.getId());
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        if (article.getId() < 0) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage("개시글이 없습니다.").build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        ArticleResponseDto resDto;
        resDto = ArticleResponseDto.builder()
                .id(article.getId())
                .boardId(article.getBoardId())
                .boardName(boardName)
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getAuthorId())
                .authorNickname(authorNickname)
                .createdDate(article.getCreatedDate())
                .modifiedDate(article.getModifiedDate())
                .recommend(recommendCount)
                .build();
        return ResponseEntity.ok(resDto);
    }

    @GetMapping(value = "/{articleId}/comments")
    public ResponseEntity getComment(@PathVariable("articleId") long articleId) {
        List<Comment> comments;
        try {
            comments = commentService.findCommentsByArticleId(articleId);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok(comments);
    }

}
