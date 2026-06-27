package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.UserDao;
import com.demo.wordtest.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // 必须加这个注解，把 DAO 交给 Spring 管理
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbc;

    public UserDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, role FROM users WHERE username = ?";
        try {
            // 使用 queryForObject 查询单条数据，并自动将结果映射到 User 实体类中
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (Exception e) {
            // 查不到数据时返回 null
            return null;
        }
    }
}