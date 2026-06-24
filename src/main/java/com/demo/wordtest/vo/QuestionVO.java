package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 考卷中的单题视图 —— 不暴露正确答案
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {

    private Integer questionId;      // questions.id
    private String type;             // EN_TO_CN / CN_TO_EN / CHOICE
    private String question;         // 题干文本
    private List<String> options;    // 选择题选项，非选择题为 null
}
