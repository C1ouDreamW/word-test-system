package com.demo.wordtest.dao;

import com.demo.wordtest.entity.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息（用于登录）
     */
    User findByUsername(String username);
}