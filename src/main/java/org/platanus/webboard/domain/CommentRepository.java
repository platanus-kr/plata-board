package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from Comment c where c.articleId in (select a.id from Article a where a.boardId = :boardId)")
    int deleteByBoardId(@Param("boardId") long boardId);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Comment c set c.content = :#{#comment.content}, c.modifiedDate = :#{#comment.modifiedDate} where c.id = :#{#comment.id}")
    int update(@Param("comment") Comment comment);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Comment c set c.deleted = :#{#comment.deleted} where c.id = :#{#comment.id}")
    int updateDeleteFlag(@Param("comment") Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findByArticleId(long id);

    Long countById(long id);

    // List<Comment> findByContent(String content);

    List<Comment> findAll();
}

