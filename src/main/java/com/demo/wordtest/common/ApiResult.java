package com.demo.wordtest.common;

import lombok.Data;

@Data
public class ApiResult<T> {
    private final int code;       // 200 成功，其他为错误码
    private final String message; // 提示信息，"ok" 或错误描述
    private final T data;         // 业务数据

    public ApiResult(int i, String message, T data) {
        this.code = i;
        this.message = message;
        this.data = data;
    }
    // 成功
    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(200, "ok", data);
    }

    // 失败
    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(400, message, null);
    }
}
