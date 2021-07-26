package org.platanus.webboard.controller;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public Board save(@RequestBody Board board) {
        return boardService.save(board);
    }

    @GetMapping
    public List<Board> all() {
        return boardService.findAll();
    }
}
