package org.platanus.webboard.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 기본 구현
    //Article save(Article article);

    int deleteByBoardId(long boardId);

    // 기본 구현
    //int delete(Article article);

    // save로 변경
    //int update(Article article);

    // 이것도 save로 변경
    //int updateViewCount(long id);

    // 이건 좀 생각해볼 필요가 있겠네..
    //int updateRecommend(long id);

    // save로 변경
    //int updateDeleteFlag(Article article);

    Optional<Article> findById(long id);

    List<Article> findByBoardId(long id);

    // 페이지네이션 어떻게 하는지 함 볼것.
    //List<Article> findByBoardIdPagination(Pageable page, long boardId);

    List<Article> findAll();

    List<Article> findByAuthorId(long id);

    List<Article> findByTitle(String title);

    List<Article> findByContent(String content);

    List<Article> findByTitleAndContent(String title, String content);

    //deleteAll로 변경
    //void allDelete();

    // 카운트 어떻게 하는지 볼것.
    //int count(long boardId);

}
