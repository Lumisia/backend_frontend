package org.example.frontend_backend.board.controller;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.frontend_backend.board.service.BoardService;
import org.example.frontend_backend.common.model.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.example.frontend_backend.board.model.BoardDto.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService bs;


    @GetMapping("/list")
    public List<ReadBoard> List() {
        return bs.list();
    }

    @GetMapping("/{idx}")
    public ReadBoard read(@PathVariable("idx") Long idx) {

        return bs.read(idx);
    }

    @PostMapping("/reg")
    public BaseResponse write(RegBoard dto) {

        return BaseResponse.success(bs.write(dto));
    }
}
