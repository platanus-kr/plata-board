package org.platanus.webboard.web.board.dto;

import lombok.Data;
import org.platanus.webboard.domain.Article;

import java.time.LocalDateTime;

@Data
public class ArticleListDto {
    private long id;
    private long boardId;
    private String title;
    private long authorId;
    private String authorNickname;
    private LocalDateTime createdDate;
    private int commentCount;
    private boolean deleted;
    private long recommend;
    private long viewCount;

    public static ArticleListDto from(Article a, String authorNickname, int commentCount) {
        ArticleListDto dto = new ArticleListDto();
        dto.setId(a.getId());
        dto.setBoardId(a.getBoardId());
        dto.setTitle(a.getTitle());
        dto.setAuthorId(a.getAuthorId());
        dto.setAuthorNickname(authorNickname);
        dto.setCreatedDate(a.getCreatedDate());
        dto.setCommentCount(commentCount);
        dto.setDeleted(a.isDeleted());
        dto.setRecommend(a.getRecommend());
        dto.setViewCount(a.getViewCount());
        return dto;

    }

}
