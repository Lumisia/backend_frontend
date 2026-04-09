package org.example.frontend_backend.board.service;

import lombok.RequiredArgsConstructor;
import org.example.frontend_backend.board.model.Board;
import org.example.frontend_backend.board.model.BoardDto;
import org.example.frontend_backend.board.repository.BoardRepository;
import org.example.frontend_backend.common.model.BaseResponse;
import org.example.frontend_backend.common.model.BaseResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import static org.example.frontend_backend.board.model.BoardDto.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository br;

    public List<ReadBoard> list() {
        return br.findAll().stream().map(ReadBoard::from).toList();
    }

    public ReadBoard read(Long post_idx) {
        return ReadBoard.from(br.findByIdx(post_idx));
    }

    public Long write(RegBoard dto) {

        Board result = br.save(dto.toEntity());

        return result.getIdx();
    }
}
