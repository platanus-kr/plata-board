package org.platanus.webboard.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final ArticleService articleService;

    @Override
    public Board create(Board board) throws Exception {
        if (boardRepository.findByName(board.getName()).isPresent()) {
            log.info("Board create #{}: 이름이 같은 게시판이 존재합니다.", board.getId());
            throw new IllegalArgumentException(MessageConstant.BOARD_ALREADY_BOARD);
        }
        board = boardRepository.save(board);
        log.info("Board create #{}: {}", board.getId(), board.getName());
        return board;
    }

    @Override
    public Board update(Board board) throws Exception {
        if (boardRepository.findById(board.getId()).isEmpty()) {
            log.info("Board update #{}: 존재하지 않는 게시판 입니다.", board.getId());
            throw new IllegalArgumentException(MessageConstant.BOARD_NOT_FOUND);
        }
        if (boardRepository.update(board) == 0) {
            log.info("Board update #{}: Repository Error.", board.getId());
            throw new IllegalArgumentException(MessageConstant.COMMON_DATABASE_ERROR);
        }
        log.info("Board create #{}: 게시판이 수정 되었습니다.", board.getId());
        return board;
    }

    @Override
    public void delete(Board board) throws Exception {
        articleService.deleteByBoardId(board.getId());
        boardRepository.delete(board);
    }

    @Override
    public Board findById(long id) throws Exception {
        if (boardRepository.findById(id).isEmpty()) {
            log.info("Board findBById #{}: 존재하지 않는 게시판 입니다.", id);
            throw new IllegalArgumentException(MessageConstant.BOARD_NOT_FOUND);
        }
        return boardRepository.findById(id).get();
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
