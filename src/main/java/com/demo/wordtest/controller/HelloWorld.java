package com.demo.wordtest.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.demo.wordtest.common.ApiResult;


/**
 * 测试接口，地址http://localhost:8080/hello
 * @author wangbo
 */
@RestController
public class HelloWorld {
    @GetMapping("/hello")
    public ApiResult<String> getHello() {
        return ApiResult.ok("Hello, Word!");
    }
    
}
