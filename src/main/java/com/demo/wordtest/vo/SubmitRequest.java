package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 提交答案请求体 —— POST /api/exams/{examId}/submit
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitRequest {

    private Integer userId;
    private List<AnswerItem> answers;

    /**
     * 单题答案
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerItem {
        private Integer questionId;
        private String answer;       // 学生填写的答案
    }
}
