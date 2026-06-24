package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 考卷视图 —— GET /api/exams/{examId}/paper 的 data 体
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperVO {

    private Integer examId;              // 考试 ID
    private String examTitle;            // 考试名称
    private long remainingSeconds;       // 剩余答题秒数
    private List<QuestionVO> questions;  // 题目列表
}
