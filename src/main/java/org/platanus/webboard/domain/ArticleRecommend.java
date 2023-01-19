package org.platanus.webboard.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ARTICLES_RECOMMEND")
public class ArticleRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long articleId;

    private long userId;

    @Override
    public String toString() {
        return "ArticleRecommend{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                '}';
    }
}
