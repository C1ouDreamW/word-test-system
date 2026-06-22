package com.demo.wordtest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 考试实体 —— 对应 exams 表
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    private Integer id;
    private String title;           // 考试名称
    private LocalDateTime startTime; // 开始时间
    private LocalDateTime endTime;   // 结束时间
    private Integer questionCount;   // 题目数量
}
