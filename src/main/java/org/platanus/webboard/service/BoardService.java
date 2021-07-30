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

    public Board create(Board board) throws Exception {
        if (boardRepository.findByName(board.getName()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 게시판 입니다.");
        return boardRepository.save(board);
    }

//    public Board update(Board board) throws Exception {
//        if (boardRepository.findById(board.getId()).isEmpty())
//            throw new IllegalArgumentException("존재하지 않는 게시판 입니다.");
//        if (boardRepository.update(board) == 0)
//            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
//        return board;
//    }

    // todo: 게시판을 지울 떄 작성 글과 코멘트 전부를 삭제되도록 할 것
    public void delete(Board board) throws Exception {
        boardRepository.delete(board);
    }

    public Board findById(long id) throws Exception {
        if (boardRepository.findById(id).isEmpty())
            throw new IllegalArgumentException("존재하지 않는 게시판 입니다.");
        return boardRepository.findById(id).get();
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
