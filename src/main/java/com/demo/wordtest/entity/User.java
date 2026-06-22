package com.demo.wordtest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体 —— 对应 users 表
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;  // 用户名
    private String role;      // admin / student
}
