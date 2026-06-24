package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 判卷详情 —— 单题判分结果
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDetailVO {

    private Integer questionId;      // 题目 ID
    private String question;         // 被考查的单词（英文或中文）
    private String yourAnswer;       // 学生答案
    private String correctAnswer;    // 正确答案
    private boolean correct;         // 是否正确
}
