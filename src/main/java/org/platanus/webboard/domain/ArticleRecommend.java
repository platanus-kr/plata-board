package org.platanus.webboard.domain;

import lombok.Data;

@Data
public class ArticleRecommend {
    private long id;
    private long articleId;
    private long userId;
}
