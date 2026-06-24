package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 考试统计 VO
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsVO {
    private String examTitle;
    private Integer totalStudents;
    private Integer avgScore;
    private Integer maxScore;
    private Integer minScore;
    private List<QuestionStatVO> questionStats;
}
