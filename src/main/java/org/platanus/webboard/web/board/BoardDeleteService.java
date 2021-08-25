package org.platanus.webboard.web.board;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public void delete(Board board) throws Exception {
        List<Article> articles = articleRepository.findByBoardId(board.getId());
        articles.stream().forEach(a -> {
            List<Comment> comments = commentRepository.findByArticleId(a.getId());
            comments.stream().forEach(c -> commentRepository.delete(c));
            articleRepository.delete(a);
        });
        boardRepository.delete(board);
    }
}
