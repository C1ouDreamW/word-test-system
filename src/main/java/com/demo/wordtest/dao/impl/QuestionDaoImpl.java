package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.QuestionDao;
import com.demo.wordtest.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Question表数据访问层实现类
 * @author wangbo
 */
@Repository
public class QuestionDaoImpl implements QuestionDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Question> findByExamIdAndUserId(Integer examId, Integer userId) {
        String sql = "SELECT id, exam_id AS examId, user_id AS userId, word_id AS wordId, "
                   + "question_type AS questionType, question_text AS questionText, "
                   + "correct_answer AS correctAnswer, options, sort_order AS sortOrder "
                   + "FROM questions WHERE exam_id = ? AND user_id = ? ORDER BY sort_order";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Question.class), examId, userId);
    }

    @Override
    public int[] insertBatch(List<Question> questions) {
        String sql = "INSERT INTO questions (exam_id, user_id, word_id, question_type, "
                   + "question_text, correct_answer, options, sort_order) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        for (Question q : questions) {
            batchArgs.add(new Object[]{
                    q.getExamId(),
                    q.getUserId(),
                    q.getWordId(),
                    q.getQuestionType(),
                    q.getQuestionText(),
                    q.getCorrectAnswer(),
                    q.getOptions(),
                    q.getSortOrder()
            });
        }
        return jdbc.batchUpdate(sql, batchArgs);
    }

    @Override
    public int deleteByExamId(Integer examId) {
        String sql = "DELETE FROM questions WHERE exam_id = ?";
        return jdbc.update(sql, examId);
    }
}
