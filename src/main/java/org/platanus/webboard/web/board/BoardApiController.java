package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardApiController {
    private final BoardService boardService;
    private final ArticleService articleService;
    private final CommentService commentService;

//    @PostMapping(value = "/board")
//    public Board save(@RequestBody Board board) throws Exception {
//        return boardService.create(board);
//    }
//
//    @DeleteMapping(value = "/board/{id}")
//    public void delete(@PathVariable long id) throws Exception {
//        boardService.delete(boardService.findById(id));
//    }
//
//    @GetMapping(value = "/board")
//    public List<Board> all() {
//        return boardService.findAll();
//    }
//
//    @GetMapping(value = "/board/{id}")
//    public List<ArticleListDto> findById(@PathVariable("id") long id) throws Exception {
//        return articleService.findArticlesByBoardId(id);
//    }
//
//    @GetMapping(value = "/article/{id}")
//    public Article getArticle(@PathVariable("id") long id) throws Exception {
//        return articleService.findById(id);
//    }
//
//    @PostMapping(value = "/article/{id}")
//    public Article writeArticle(@PathVariable("id") long id,
//                                @RequestBody Article article) throws Exception {
//        return articleService.write(article);
//    }
//
//    @PutMapping(value = "/article/{id}")
//    public Article updateArticle(@PathVariable("id") long id,
//                                 @RequestBody Article article) throws Exception {
//        return articleService.update(article);
//    }
//
//    @DeleteMapping(value = "/article/{id}")
//    public void deleteArticle(@PathVariable("id") long id) throws Exception {
//        articleService.updateDeleteFlag(articleService.findById(id));
//    }
//
//    @GetMapping(value = "/article/{id}/comments")
//    public List<Comment> getComments(@PathVariable("id") long id) throws Exception {
//        return commentService.findCommentsByArticleId(id);
//    }
//
//    @GetMapping(value = "/article/{articleId}/comment/{commentId}")
//    public Comment getComment(@PathVariable("articleId") long articleId,
//                              @PathVariable("commentId") long commentId) throws Exception {
//        return commentService.findById(commentId);
//    }
//
//    @PostMapping(value = "/article/{id}/comment")
//    public Comment writeComment(@PathVariable("id") long id,
//                                @RequestBody Comment comment) throws Exception {
//        comment.setArticleId(id);
//        return commentService.write(comment);
//    }
//
//    @PutMapping(value = "/comment/{id}")
//    public Comment updateComment(@PathVariable("id") long id,
//                                 @RequestBody Comment comment) throws Exception {
//        return commentService.update(comment);
//    }
//
//    @DeleteMapping(value = "/comment/{id}")
//    public void deleteComment(@PathVariable("id") long id) throws Exception {
//        commentService.updateDeleteFlag(commentService.findById(id));
//    }
}
