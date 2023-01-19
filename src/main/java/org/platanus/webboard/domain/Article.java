package org.platanus.webboard.domain;

import lombok.*;
import org.platanus.webboard.controller.board.dto.ArticleListDto;
import org.platanus.webboard.controller.board.dto.ArticleWriteDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ARTICLES")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long authorId;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private boolean deleted;

    private Long recommend;

    private Long viewCount;

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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", deleted=" + deleted +
                ", recommend=" + recommend +
                ", viewCount=" + viewCount +
                '}';
    }
}
