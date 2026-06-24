package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 提交判卷结果 —— POST /api/exams/{examId}/submit 的 data 体
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitResultVO {

    private int score;                    // 得分（百分制）
    private int total;                    // 总题数
    private int correctCount;             // 答对题数
    private List<AnswerDetailVO> details; // 逐题详情
}
