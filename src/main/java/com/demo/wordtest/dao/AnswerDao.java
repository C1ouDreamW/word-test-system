package com.demo.wordtest.dao;

import com.demo.wordtest.entity.Answer;
import com.demo.wordtest.vo.WordCloudItemVO;

import java.util.List;
import java.util.Map;

public interface AnswerDao {
    /**
     * 批量插入答案（交卷时使用）
     */
    int[] insertBatch(List<Answer> answers);

    /**
     * 根据题目 ID 列表和用户 ID 查询已有答案（判卷用）
     */
    List<Answer> findByQuestionIdsAndUserId(List<Integer> questionIds, Integer userId);

    /**
     * 查询某次考试所有学生的成绩（按用户聚合）
     * 返回每行: {user_id, username, correct_count, total}
     */
    List<Map<String, Object>> findScoresByExamId(Integer examId);

    /**
     * 查询某个学生所有考试的成绩（按考试聚合）
     * 返回每行: {exam_id, exam_title, correct_count, total}
     */
    List<Map<String, Object>> findScoresByUserId(Integer userId);

    /**
     * 查询某个学生的错词及频次（字符云用）
     * 返回每行: {word, wrong_count}
     */
    List<WordCloudItemVO> findWrongWordsByUserId(Integer userId);

    /**
     * 查询全局错词及频次（字符云用）
     * 返回每行: {word, wrong_count}
     */
    List<WordCloudItemVO> findAllWrongWords();

    /**
     * 查询某次考试中每题的正确率
     * 返回每行: {question_id, word_english, word_chinese, question_type, correct_rate}
     */
    List<Map<String, Object>> findQuestionCorrectRates(Integer examId);

    /**
     * 查询某学生在某次考试中的答题详情（含题目信息）
     * 返回每行: {question_id, question_type, question_text, correct_answer,
     *            user_answer, is_correct, word_english, word_chinese}
     */
    List<Map<String, Object>> findAnswerDetailsByExamIdAndUserId(Integer examId, Integer userId);
}
