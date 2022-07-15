package org.platanus.webboard.controller.board.dto;

import lombok.Data;
import org.platanus.webboard.domain.Article;

import java.time.LocalDateTime;

@Data
public class ArticleViewDto {
    public long id;
    public long boardId;
    public String title;
    public String content;
    public long authorId;
    public String authorNickname;
    public LocalDateTime createdDate;
    public LocalDateTime modifiedDate;
    private long recommend;
    private long viewCount;

    public static ArticleViewDto fromView(Article article, String authorNickname) {
        ArticleViewDto articleResponse = new ArticleViewDto();
        articleResponse.setBoardId(article.getBoardId());
        articleResponse.setId(article.getId());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        articleResponse.setAuthorId(article.getAuthorId());
        articleResponse.setAuthorNickname(authorNickname);
        articleResponse.setCreatedDate(article.getCreatedDate());
        articleResponse.setModifiedDate(article.getModifiedDate());
        articleResponse.setRecommend(article.getRecommend());
        articleResponse.setViewCount(article.getViewCount());
        return articleResponse;
    }

    public static ArticleViewDto fromModify(Article article) {
        ArticleViewDto articleResponse = new ArticleViewDto();
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        return articleResponse;
    }
}
