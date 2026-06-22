package com.demo.wordtest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目实体 —— 对应 questions 表
 * <p>
 * 学生首次获取考卷时生成并持久化，同一学生的考卷不变。
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Integer id;
    private Integer examId;         // 关联 exams.id
    private Integer userId;         // 关联 users.id
    private Integer wordId;         // 关联 words.id
    private String questionType;    // EN_TO_CN / CN_TO_EN / CHOICE
    private String questionText;    // 题干
    private String correctAnswer;   // 正确答案
    private String options;         // 选择题选项（JSON 数组），非选择题为 NULL
    private Integer sortOrder;      // 排序号，体现随机题序
}
