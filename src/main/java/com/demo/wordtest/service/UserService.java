package com.demo.wordtest.service;

import com.demo.wordtest.entity.User;

public interface UserService {
    /**
     * 处理登录业务逻辑
     */
    User login(String username, String role);
}