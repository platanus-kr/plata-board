package org.platanus.webboard.service;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardService boardService;

    public Article write(Article article) throws Exception {
        boardService.findById(article.getBoardId());
        return articleRepository.save(article);
    }

    public Article update(Article article) throws Exception {
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

    public List<Article> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<Article> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> !a.isDeleted()).forEach(a -> returnArticles.add(a));
        return returnArticles;
    }

    public List<Article> findAllDeletedArticles() {
        List<Article> articles = articleRepository.findAll();
        List<Article> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> a.isDeleted()).forEach(a -> returnArticles.add(a));
        return returnArticles;
    }

    public List<Article> findArticlesByBoardId(long boardId) {
        List<Article> articles = articleRepository.findByBoardId(boardId);
        List<Article> returnArticles = new ArrayList<>();
        articles.stream().filter(a -> !a.isDeleted()).forEach(a -> returnArticles.add(a));
        return returnArticles;
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
