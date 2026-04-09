package org.example.frontend_backend.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static org.example.frontend_backend.common.model.BaseResponseStatus.SUCCESS;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse<T> {
    private Boolean success;
    private String message;
    private T result;

    public static <T> BaseResponse success(T result) {
        return new BaseResponse(
                SUCCESS.isSuccess(),
                SUCCESS.getMessage(),
                result
        );
    }

    public static <T> BaseResponse fail(BaseResponseStatus status) {
        return new BaseResponse(
                status.isSuccess(),
                status.getMessage(),
                null
        );
    }

    public static <T> BaseResponse fail(BaseResponseStatus status, T result) {
        return new BaseResponse(
                status.isSuccess(),
                status.getMessage(),
                result
        );
    }
}