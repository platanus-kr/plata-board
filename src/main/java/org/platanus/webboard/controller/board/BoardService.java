package org.platanus.webboard.controller.board;

import org.platanus.webboard.domain.Board;

import java.util.List;

public interface BoardService {
    Board create(Board board) throws Exception;

    Board update(Board board) throws Exception;

    void delete(Board board) throws Exception;

    Board findById(long id) throws Exception;

    List<Board> findAll();
}
