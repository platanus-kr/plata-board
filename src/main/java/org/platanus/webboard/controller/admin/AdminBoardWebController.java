package org.platanus.webboard.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.BoardService;
import org.platanus.webboard.controller.login.argumentresolver.Login;
import org.platanus.webboard.controller.login.dto.UserSessionDto;
import org.platanus.webboard.domain.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board")
public class AdminBoardWebController {
    private final BoardService boardService;

    @GetMapping
    public String boardFront(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "admin/board_front";
    }

    @GetMapping(value = "/create")
    public String createForm(Model model) {
        model.addAttribute("board", new Board());
        return "admin/board_create";
    }

    @PostMapping(value = "/create")
    public String create(@Valid @ModelAttribute("board") Board boardRequest,
                         BindingResult bindingResult,
                         @Login UserSessionDto user) {
        if (bindingResult.hasErrors()) {
            log.info("Admin : Create board #{} by User #{} : {}", boardRequest.getId(), user.getId(), bindingResult);
            return "admin/board_create";
        }
        Board board = new Board();
        board.setName(boardRequest.getName());
        board.setDescription((boardRequest.getDescription()));
        try {
            board = boardService.create(board);
        } catch (Exception e) {
            log.info("Admin : Create board error : {} ", e.getMessage());
        }
        return "redirect:/admin/board";
    }

    @GetMapping(value = "/{boardId}/delete")
    public String deleteForm(@PathVariable("boardId") long boardId,
                             Model model) {
        Board findBoard = new Board();
        try {
            findBoard = boardService.findById(boardId);
        } catch (Exception e) {
            log.info("Admin : Delete board error : Not found board #{}", boardId);
            return "redirect:/admin/board";
        }
        model.addAttribute("board", findBoard);
        return "admin/board_delete";
    }

    @PostMapping(value = "/{boardId}/delete")
    public String delete(@PathVariable("boardId") long boardId) {
        try {
            boardService.delete(boardService.findById(boardId));
        } catch (Exception e) {
            log.info("Admin : Delete board error : {}", e.getMessage());
            return "redirect:/admin/board";
        }
        return "redirect:/admin/board";
    }
}
