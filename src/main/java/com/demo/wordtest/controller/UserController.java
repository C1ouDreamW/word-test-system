package com.demo.wordtest.controller;

import com.demo.wordtest.common.ApiResult;
import com.demo.wordtest.entity.User;
import com.demo.wordtest.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /api/login
     */
    @PostMapping("/login")
    public ApiResult<User> login(@RequestBody User loginParams) {
        // 从请求体中获取前端传来的 username 和 role
        User user = userService.login(loginParams.getUsername(), loginParams.getRole());
        
        if (user != null) {
            // 登录成功，按照 API 文档规范返回 200 和用户数据
            return ApiResult.ok(user);
        } else {
            // 登录失败，返回 400 和错误提示信息
            return ApiResult.fail("用户名不存在或角色错误");
        }
    }
}