package org.platanus.webboard.controller.board.dto;

import lombok.Builder;
import lombok.Data;
import org.platanus.webboard.domain.Article;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleResponseDto {
    public long id;
    public long boardId;
    public String boardName;
    public String title;
    public String content;
    public long authorId;
    public String authorNickname;
    public LocalDateTime createdDate;
    public LocalDateTime modifiedDate;
    private long recommend;
    private long viewCount;

    public static ArticleResponseDto fromView(Article article, String authorNickname) {
        ArticleResponseDto articleResponse = ArticleResponseDto.builder()
                .boardId(article.getBoardId())
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getAuthorId())
                .authorNickname(authorNickname)
                .createdDate(article.getCreatedDate())
                .modifiedDate(article.getModifiedDate())
                .recommend(article.getRecommend())
                .viewCount(article.getViewCount())
                .build();
        return articleResponse;
    }

    public static ArticleResponseDto fromModify(Article article) {
        ArticleResponseDto articleResponse = ArticleResponseDto.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .build();
        return articleResponse;
    }
}
