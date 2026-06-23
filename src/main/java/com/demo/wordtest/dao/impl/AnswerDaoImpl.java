package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.AnswerDao;
import com.demo.wordtest.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Answer表数据访问层实现类
 * @author wangbo
 */
@Repository
public class AnswerDaoImpl implements AnswerDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public int[] insertBatch(List<Answer> answers) {
        String sql = "INSERT INTO answers (question_id, user_id, user_answer, is_correct) "
                   + "VALUES (?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        for (Answer a : answers) {
            batchArgs.add(new Object[]{
                    a.getQuestionId(),
                    a.getUserId(),
                    a.getUserAnswer(),
                    a.getIsCorrect()
            });
        }
        return jdbc.batchUpdate(sql, batchArgs);
    }

    @Override
    public List<Answer> findByQuestionIdsAndUserId(List<Integer> questionIds, Integer userId) {
        if (questionIds == null || questionIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 构建 IN 子句的占位符
        StringJoiner placeholders = new StringJoiner(", ");
        for (int i = 0; i < questionIds.size(); i++) {
            placeholders.add("?");
        }

        String sql = "SELECT id, question_id AS questionId, user_id AS userId, "
                   + "user_answer AS userAnswer, is_correct AS isCorrect "
                   + "FROM answers WHERE question_id IN (" + placeholders + ") AND user_id = ?";

        List<Object> params = new ArrayList<>(questionIds);
        params.add(userId);

        return jdbc.query(sql, params.toArray(), new BeanPropertyRowMapper<>(Answer.class));
    }
}
