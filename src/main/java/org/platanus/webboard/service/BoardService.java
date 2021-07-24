package org.platanus.webboard.service;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
