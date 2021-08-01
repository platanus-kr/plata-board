package org.platanus.webboard.controller.board;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Article;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.Comment;
import org.platanus.webboard.service.ArticleService;
import org.platanus.webboard.service.BoardService;
import org.platanus.webboard.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping(value = "/api/board")
    public Board save(@RequestBody Board board) throws Exception {
        return boardService.create(board);
    }

    @DeleteMapping(value = "/api/board/{id}")
    public void delete(@PathVariable long id) throws Exception {
        boardService.delete(boardService.findById(id));
    }

    @GetMapping(value = "/api/board")
    public List<Board> all() {
        return boardService.findAll();
    }

    @GetMapping(value = "/api/board/{id}")
    public List<Article> findById(@PathVariable("id") long id) throws Exception {
        return articleService.findArticlesByBoardId(id);
    }

    @GetMapping(value = "/api/article/{id}")
    public Article getArticle(@PathVariable("id") long id) throws Exception {
        return articleService.findById(id);
    }

//    @GetMapping(value = "/api/board/{boardId}/article/{articleId}")
//    public Article getArticle(@PathVariable("boardId") long boardId, @PathVariable("articleId") long articleId) throws Exception {
//        return articleService.findArticleByBoardId(boardId, articleId);
//    }

    @PostMapping(value = "/api/article/{id}")
    public Article writeArticle(@PathVariable("id") long id, @RequestBody Article article) throws Exception {
        return articleService.write(article);
    }

    @PutMapping(value = "/api/article/{id}")
    public Article updateArticle(@PathVariable("id") long id, @RequestBody Article article) throws Exception {
        return articleService.update(article);
    }

    @DeleteMapping(value = "/api/article/{id}")
    public void deleteArticle(@PathVariable("id") long id) throws Exception {
        articleService.updateDeleteFlag(articleService.findById(id));
    }

    @GetMapping(value = "/api/article/{id}/comments")
    public List<Comment> getComments(@PathVariable("id") long id) throws Exception {
        return commentService.findCommentsByArticleId(id);
    }

    @GetMapping(value = "/api/article/{articleId}/comment/{commentId}")
    public Comment getComment(@PathVariable("articleId") long articleId, @PathVariable("commentId") long commentId) throws Exception {
        return commentService.findById(commentId);
    }

    @PostMapping(value = "/api/article/{id}/comment")
    public Comment writeComment(@PathVariable("id") long id, @RequestBody Comment comment) throws Exception {
        comment.setArticleId(id);
        return commentService.write(comment);
    }

    @PutMapping(value = "/api/comment/{id}")
    public Comment updateComment(@PathVariable("id") long id, @RequestBody Comment comment) throws Exception {
        return commentService.update(comment);
    }

    @DeleteMapping(value = "/api/comment/{id}")
    public void deleteComment(@PathVariable("id") long id) throws Exception {
        commentService.updateDeleteFlag(commentService.findById(id));
    }
}
