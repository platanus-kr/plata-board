package org.platanus.webboard.domain.MyBatis;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.domain.BoardRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisBoardRepository implements BoardRepository {

    private final BoardMapper boardMapper;

    @Override
    public Board save(Board board) {
        long boardId = boardMapper.save(board);
        return boardMapper.findById(boardId);
    }

    @Override
    public int delete(Board board) {
        return boardMapper.delete(board);
    }

    @Override
    public int update(Board board) {
        return boardMapper.update(board);
    }

    @Override
    public List<Board> findAll() {
        return boardMapper.findAll();
    }

    @Override
    public Optional<Board> findById(long id) {
        return Optional.ofNullable(boardMapper.findById(id));
    }

    @Override
    public Optional<Board> findByName(String name) {
        return Optional.ofNullable(boardMapper.findByName(name));
    }

    @Override
    public void allDelete() {
        boardMapper.allDelete();
    }
}