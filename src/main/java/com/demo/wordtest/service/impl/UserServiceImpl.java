package com.demo.wordtest.service.impl;

import com.demo.wordtest.dao.UserDao;
import com.demo.wordtest.entity.User;
import com.demo.wordtest.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String username, String role) {
        // 1. 调用 DAO 根据用户名去数据库查人
        User user = userDao.findByUsername(username);
        
        // 2. 如果查到了人，并且前端传来的角色和数据库里的角色对得上
        if (user != null && user.getRole().equals(role)) {
            return user; // 登录成功，返回用户信息
        }
        
        // 3. 用户名不存在或角色不匹配
        return null; 
    }
}