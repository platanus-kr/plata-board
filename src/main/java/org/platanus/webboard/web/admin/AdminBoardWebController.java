package org.platanus.webboard.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.Board;
import org.platanus.webboard.web.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String createForm() {
        return "admin/board_create";
    }

    @PostMapping(value = "/create")
    public String create() {
        return "redirect:/admin/board";
    }

    @GetMapping(value = "/delete")
    public String deleteForm() {
        return "admin/board_delete";
    }

    @PostMapping(value = "/delete")
    public String delete() {
        return "redirect:/admin/board";
    }
}
