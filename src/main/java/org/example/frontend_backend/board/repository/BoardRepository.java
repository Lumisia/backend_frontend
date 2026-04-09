package org.example.frontend_backend.board.repository;

import org.example.frontend_backend.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByIdx(Long idx);
    List<Board> findAllByOrderByIdxDesc();
}
