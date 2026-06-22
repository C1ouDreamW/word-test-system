package com.demo.wordtest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答案实体 —— 对应 answers 表
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private Integer id;
    private Integer questionId;   // 关联 questions.id
    private Integer userId;       // 关联 users.id
    private String userAnswer;    // 学生提交的答案
    private Integer isCorrect;    // 0 错 / 1 对
}
