package org.platanus.webboard.domain;

import lombok.Data;
import org.platanus.webboard.controller.board.dto.ArticleListDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Article {
    private long id;
    private long boardId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
    private long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
    private long recommend;
    private long viewCount;

    public static Article fromListDto(ArticleListDto a) {
        Article article = new Article();
        article.setId(a.getId());
        article.setBoardId(a.getBoardId());
        article.setTitle(a.getTitle());
        article.setContent("-");
        article.setAuthorId(a.getAuthorId());
        article.setCreatedDate(a.getCreatedDate());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        article.setRecommend(a.getRecommend());
        article.setViewCount(a.getViewCount());
        return article;
    }
}
