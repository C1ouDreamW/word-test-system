package com.demo.wordtest.dao;

import com.demo.wordtest.entity.Question;
import java.util.List;

public interface QuestionDao {
    /**
     * 查询某学生在某次考试中的全部题目（考卷）
     */
    List<Question> findByExamIdAndUserId(Integer examId, Integer userId);

    /**
     * 批量插入题目（生成考卷时使用），返回受影响行数
     */
    int[] insertBatch(List<Question> questions);

    /**
     * 删除某次考试的全部题目（级联删除时使用）
     */
    int deleteByExamId(Integer examId);
}
