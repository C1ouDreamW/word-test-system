package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单题正确率统计 VO
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionStatVO {
    private Integer questionId;
    private String question;
    private Integer correctRate;  // 百分比，0-100
}
