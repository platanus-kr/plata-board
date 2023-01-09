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
            log.info(MessageConstant.BOARD_ALREADY_USE_BOARD_NAME_LOG, board.getId());
            throw new IllegalArgumentException(MessageConstant.BOARD_ALREADY_BOARD);
        }
        board = boardRepository.save(board);
        log.info(MessageConstant.BOARD_SUCCESS_CREATE, board.getId(), board.getName());
        return board;
    }

    @Override
    public Board update(Board board) throws Exception {
        if (boardRepository.findById(board.getId()).isEmpty()) {
            log.info(MessageConstant.BOARD_NOT_FOUND_BY_ID_LOG, board.getId());
            throw new IllegalArgumentException(MessageConstant.BOARD_NOT_FOUND);
        }
        if (boardRepository.update(board) == 0) {
            log.error(MessageConstant.BOARD_FAILED_UPDATE_LOG, board.getId());
            throw new IllegalArgumentException(MessageConstant.COMMON_DATABASE_ERROR);
        }
        log.info(MessageConstant.BOARD_SUCCESS_UPDATE, board.getId());
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
