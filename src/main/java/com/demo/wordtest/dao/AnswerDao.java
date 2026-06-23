package com.demo.wordtest.dao;

import com.demo.wordtest.entity.Answer;
import java.util.List;

public interface AnswerDao {
    /**
     * 批量插入答案（交卷时使用）
     */
    int[] insertBatch(List<Answer> answers);

    /**
     * 根据题目 ID 列表和用户 ID 查询已有答案（判卷用）
     */
    List<Answer> findByQuestionIdsAndUserId(List<Integer> questionIds, Integer userId);
}
