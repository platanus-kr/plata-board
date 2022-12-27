package org.platanus.webboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.platanus.webboard.controller.board.dto.ArticleListDto;
import org.platanus.webboard.controller.board.dto.ArticleWriteDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        return Article.builder()
                .id(a.getId())
                .boardId(a.getBoardId())
                .title(a.getTitle())
                .authorId(a.getAuthorId())
                .createdDate(a.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .deleted(false)
                .recommend(a.getRecommend())
                .viewCount(a.getViewCount())
                .build();
    }

    public static Article fromWriteDto(ArticleWriteDto a) {
        return Article.builder()
                .title(a.getTitle())
                .content(a.getContent())
                .build();
    }
}
