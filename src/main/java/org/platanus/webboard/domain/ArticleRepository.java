package org.platanus.webboard.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    int deleteByBoardId(long boardId);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query(value = "update Article a set a.title = :title, a.content = :content, a.modifiedDate = :modifiedDate where a.id = :id")
    int update(Article article);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query(value = "update Article a set a.viewCount = a.viewCount + 1 where a.id = :id")
    int updateViewCount(long id);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query(value = "update Article a set a.recommend = a.recommend + 1 where a.id = :id")
    int updateRecommend(long id);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query(value = "update Article a set a.deleted = :deleted where a.id = :id")
    int updateDeleteFlag(Article article);

    Optional<Article> findById(long id);

    List<Article> findByBoardId(long id);

    // 페이지네이션 어떻게 하는지 함 볼것.
    List<Article> findByBoardId(long boardId, Pageable page);

    List<Article> findAll();

    List<Article> findByAuthorId(long id);

    List<Article> findByTitle(String title);

    List<Article> findByContent(String content);

    List<Article> findByTitleAndContent(String title, String content);

    Long countById(long boardId);

}
