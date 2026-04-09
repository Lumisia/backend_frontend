package org.example.frontend_backend.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    SUCCESS(true,  "게시글이 성공적으로 등록되었습니다."),
    FAIL(false,  "요청 처리에 실패했습니다.");


    private final boolean success;
    private final String message;
}
