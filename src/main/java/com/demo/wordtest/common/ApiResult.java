package com.demo.wordtest.common;

import lombok.Getter;

/**
 * 统一返回体 —— 所有 Controller 接口都用此类包裹返回值
 * @author wangbo
 * @param <T> 业务数据类型
 */
@Getter
public class ApiResult<T> {

    private final int code;       // 200 成功，其他为错误码
    private final String message; // 提示信息，"ok" 或错误描述
    private final T data;         // 业务数据

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /** 成功 */
    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(200, "ok", data);
    }

    /** 成功（无数据体） */
    public static <T> ApiResult<T> ok() {
        return new ApiResult<>(200, "ok", null);
    }

    /** 失败 */
    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(400, message, null);
    }

    /** 失败（自定义错误码） */
    public static <T> ApiResult<T> fail(int code, String message) {
        return new ApiResult<>(code, message, null);
    }
}
