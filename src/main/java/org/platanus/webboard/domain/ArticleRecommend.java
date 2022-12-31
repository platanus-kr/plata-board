package org.platanus.webboard.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long articleId;
    private long userId;
}
