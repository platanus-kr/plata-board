package org.platanus.webboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArticleRecommend {
    private long id;
    private long articleId;
    private long userId;
}
