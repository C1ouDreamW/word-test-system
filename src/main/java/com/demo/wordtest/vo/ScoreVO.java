package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成绩查询 VO
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreVO {
    private Integer userId;
    private String username;
    private Integer examId;
    private String examTitle;
    private Integer score;
    private Integer correctCount;
    private Integer total;
}
