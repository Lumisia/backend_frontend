package org.example.frontend_backend.board.model;

import lombok.Builder;
import lombok.Getter;

public class BoardDto {

    public static class RegBoard {
        private String title;
        private String contents;

        public Board toEntity() {
            return Board.builder()
                    .title(title)
                    .contents(contents)
                    .build();
        }
    }

    @Builder
    public static class ReadBoard {
        private Long idx;
        private String title;
        private String contents;

        public static ReadBoard from(Board entity) {
            return ReadBoard.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .build();
        }
    }
}
