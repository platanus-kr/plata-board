package org.platanus.webboard.service;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.dto.ArticleListDto;
import org.platanus.webboard.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardService boardService;
    private final UserService userService;

    public Article write(Article article) throws Exception {
        boardService.findById(article.getBoardId());
        article.setCreatedDate(LocalDateTime.now());
        article.setModifiedDate(LocalDateTime.now());
        article.setDeleted(false);
        return articleRepository.save(article);
    }

    public Article update(Article article) throws Exception {
        article.setCreatedDate(findById(article.getId()).getCreatedDate());
        article.setModifiedDate(LocalDateTime.now());
        Optional<Article> getArticle = articleRepository.findById(article.getId());
        if (getArticle.isEmpty())
            throw new IllegalArgumentException("없는 게시물 입니다.");
        if (articleRepository.update(article) != 1)
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
        return article;
    }

    public void updateDeleteFlag(Article article) throws Exception {
        if (articleRepository.findById(article.getId()).get().isDeleted())
            throw new IllegalArgumentException("이미 삭제된 게시물 입니다.");
        article.setDeleted(true);
        if (articleRepository.updateDeleteFlag(article) != 1)
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
    }

    public void delete(Article article) throws Exception {
        if (articleRepository.delete(article) != 1)
            throw new IllegalArgumentException("완전 삭제에 문제가 생겼습니다.");
    }

    public List<ArticleListDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return getArticleListDtos(articles);
    }

    public List<ArticleListDto> findAllDeletedArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> a.isDeleted()).forEach(a -> {
            try {
                ArticleListDto dto = new ArticleListDto();
                dto.setId(a.getId());
                dto.setBoardId(a.getBoardId());
                dto.setTitle(a.getTitle());
                dto.setAuthorId(a.getAuthorId());
                dto.setAuthorNickname(userService.findById(a.getAuthorId()).getNickname());
                dto.setCreatedDate(a.getCreatedDate());
                dto.setDeleted(a.isDeleted());
                returnArticles.add(dto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    public List<ArticleListDto> findArticlesByBoardId(long boardId) {
        List<Article> articles = articleRepository.findByBoardId(boardId);
        return getArticleListDtos(articles);
    }

    private List<ArticleListDto> getArticleListDtos(List<Article> articles) {
        List<ArticleListDto> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> !a.isDeleted()).forEach(a -> {
            try {
                ArticleListDto dto = new ArticleListDto();
                dto.setId(a.getId());
                dto.setBoardId(a.getBoardId());
                dto.setTitle(a.getTitle());
                dto.setAuthorId(a.getAuthorId());
                dto.setAuthorNickname(userService.findById(a.getAuthorId()).getNickname());
                dto.setCreatedDate(a.getCreatedDate());
                dto.setDeleted(a.isDeleted());
                returnArticles.add(dto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return returnArticles;
    }

    public Article findArticleByBoardId(long boardId, long id) throws Exception {
        boardService.findById(boardId);
        Article returnArticle = findById(id);
        return returnArticle;
    }

    public Article findById(long id) throws Exception {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty() || article.get().isDeleted())
            throw new IllegalArgumentException("없는 게시물 입니다.");
        return article.get();
    }

    public boolean isDeleted(Article article) throws Exception {
        Article returnArticle = articleRepository.findById(article.getId()).get();
        return returnArticle.isDeleted();
    }

}
