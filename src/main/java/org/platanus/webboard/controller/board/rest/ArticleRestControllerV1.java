package org.platanus.webboard.controller.board.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.config.security.dto.UserClaimDto;
import org.platanus.webboard.config.security.permission.HasUserRole;
import org.platanus.webboard.controller.board.ArticleRecommendService;
import org.platanus.webboard.controller.board.ArticleService;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.board.CommentService;
import org.platanus.webboard.controller.board.dto.ArticleResponseDto;
import org.platanus.webboard.controller.board.dto.ArticleWriteDto;
import org.platanus.webboard.controller.board.dto.CommentWriteDto;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.ArticleRecommend;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@Api(tags = {"게시글 관련 RST API Controller"})
public class ArticleRestControllerV1 {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final ArticleRecommendService articleRecommendService;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * 게시글 조회<br />
     *
     * @param articleId
     * @return
     * @throws JsonProcessingException
     */
    @ApiOperation(value = "게시글 조회")
    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable("articleId") long articleId) {
        Article article;
        String authorNickname;
        List<Comment> commentsResponse;
        String boardName;
        String boardId;
        long recommendCount;
        try {
            article = articleService.findById(articleId);
            authorNickname = userService.findById(article.getAuthorId()).getNickname();
            boardName = boardService.findById(article.getBoardId()).getName();
            recommendCount = articleRecommendService.countByArticleId(article.getId());
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        if (article.getId() < 0) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(MessageConstant.ARTICLE_NOT_FOUND).build();
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

    /**
     * 게시글 수정 <br />
     *
     * @param articleId
     * @param user
     * @param articleRequest
     * @return
     */
    @PostMapping("/{articleId}/update")
    @HasUserRole
    public ResponseEntity<?> updateArticle(@PathVariable("articleId") long articleId,
                                           @AuthenticationPrincipal UserClaimDto user,
                                           @Valid @RequestBody ArticleWriteDto articleRequest) {
        User userFromClaim = User.fromUserClaimDto(user);
        Article article;
        try {
            article = articleService.findById(articleId);
            article.setTitle(articleRequest.getTitle());
            article.setContent(articleRequest.getContent());
            articleService.update(article, userFromClaim);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 게시글 삭제<br />
     *
     * @param articleId
     * @param user
     * @return
     */
    @DeleteMapping("/{articleId}")
    @HasUserRole
    public ResponseEntity<?> deleteArticle(@PathVariable("articleId") long articleId,
                                           @AuthenticationPrincipal UserClaimDto user) {
        User userFromClaim = User.fromUserClaimDto(user);
        Article article;
        try {
            // 게시글을 먼저 찾는 유효성 검사를 해야한다. 이 과정에서 소유자 대조가 이뤄진다.
            article = articleService.findById(articleId);
            articleService.updateDeleteFlag(article, userFromClaim);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok().build();
    }


    /**
     * 게시글에 포함된 코멘트 조회  <br />
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{articleId}/comments")
    public ResponseEntity<?> getComment(@PathVariable("articleId") long articleId) {
        List<Comment> comments;
        try {
            comments = commentService.findCommentsByArticleId(articleId);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok(comments);
    }

    /**
     * 게시글에 코멘트 작성 <br />
     *
     * @param articleId
     * @param user
     * @param commentRequest
     * @return
     */
    @PostMapping("/{articleId}/comment")
    @HasUserRole
    public ResponseEntity<?> writeComment(@PathVariable("articleId") long articleId,
                                          @AuthenticationPrincipal UserClaimDto user,
                                          @Valid @RequestBody CommentWriteDto commentRequest) {
        Comment comment = Comment.builder()
                .articleId(articleId)
                .authorId(user.getId())
                .content(commentRequest.getContent())
                .build();
        try {
            comment = commentService.write(comment);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder().errorId(999).errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok(comment);
    }

    /**
     * 게시글 추천<br />
     *
     * @param articleId
     * @param user
     * @return
     */
    @PostMapping("/{articleId}/recommend")
    @HasUserRole
    public ResponseEntity<?> recommendArticle(@PathVariable("articleId") long articleId,
                                              @AuthenticationPrincipal UserClaimDto user) {
        ArticleRecommend articleRecommend = ArticleRecommend.builder()
                .articleId(articleId)
                .userId(user.getId())
                .build();
        try {
            articleRecommendService.save(articleRecommend);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok().build();
    }
}
