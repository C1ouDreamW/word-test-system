package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.AnswerDao;
import com.demo.wordtest.entity.Answer;
import com.demo.wordtest.vo.WordCloudItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Override
    public List<Map<String, Object>> findScoresByExamId(Integer examId) {
        String sql = "SELECT q.user_id AS userId, u.username, " +
                     "COUNT(a.id) AS total, " +
                     "SUM(a.is_correct) AS correctCount " +
                     "FROM questions q " +
                     "JOIN answers a ON a.question_id = q.id AND a.user_id = q.user_id " +
                     "JOIN users u ON u.id = q.user_id " +
                     "WHERE q.exam_id = ? " +
                     "GROUP BY q.user_id, u.username " +
                     "ORDER BY SUM(a.is_correct) DESC";

        return jdbc.queryForList(sql, examId);
    }

    @Override
    public List<Map<String, Object>> findScoresByUserId(Integer userId) {
        String sql = "SELECT q.exam_id AS examId, e.title AS examTitle, " +
                     "COUNT(a.id) AS total, " +
                     "SUM(a.is_correct) AS correctCount " +
                     "FROM questions q " +
                     "JOIN answers a ON a.question_id = q.id AND a.user_id = q.user_id " +
                     "JOIN exams e ON e.id = q.exam_id " +
                     "WHERE q.user_id = ? " +
                     "GROUP BY q.exam_id, e.title " +
                     "ORDER BY e.id DESC";

        return jdbc.queryForList(sql, userId);
    }

    @Override
    public List<WordCloudItemVO> findWrongWordsByUserId(Integer userId) {
        String sql = "SELECT w.english AS word, COUNT(*) AS wrongCount " +
                     "FROM answers a " +
                     "JOIN questions q ON q.id = a.question_id " +
                     "JOIN words w ON w.id = q.word_id " +
                     "WHERE a.is_correct = 0 AND a.user_id = ? " +
                     "GROUP BY w.id, w.english " +
                     "ORDER BY wrongCount DESC";

        return jdbc.query(sql, new BeanPropertyRowMapper<>(WordCloudItemVO.class), userId);
    }

    @Override
    public List<WordCloudItemVO> findAllWrongWords() {
        String sql = "SELECT w.english AS word, COUNT(*) AS wrongCount " +
                     "FROM answers a " +
                     "JOIN questions q ON q.id = a.question_id " +
                     "JOIN words w ON w.id = q.word_id " +
                     "WHERE a.is_correct = 0 " +
                     "GROUP BY w.id, w.english " +
                     "ORDER BY wrongCount DESC";

        return jdbc.query(sql, new BeanPropertyRowMapper<>(WordCloudItemVO.class));
    }

    @Override
    public List<Map<String, Object>> findQuestionCorrectRates(Integer examId) {
        String sql = "SELECT q.id AS questionId, w.english AS wordEnglish, " +
                     "w.chinese AS wordChinese, q.question_type AS questionType, " +
                     "COALESCE(ROUND(SUM(a.is_correct) * 100.0 / COUNT(a.id)), 0) AS correctRate " +
                     "FROM questions q " +
                     "LEFT JOIN answers a ON a.question_id = q.id " +
                     "JOIN words w ON w.id = q.word_id " +
                     "WHERE q.exam_id = ? " +
                     "GROUP BY q.id, w.english, w.chinese, q.question_type " +
                     "ORDER BY q.sort_order";

        return jdbc.queryForList(sql, examId);
    }

    @Override
    public List<Map<String, Object>> findAnswerDetailsByExamIdAndUserId(Integer examId, Integer userId) {
        String sql = "SELECT q.id AS questionId, q.question_type AS questionType, " +
                     "q.question_text AS questionText, q.correct_answer AS correctAnswer, " +
                     "a.user_answer AS userAnswer, a.is_correct AS isCorrect, " +
                     "w.english AS wordEnglish, w.chinese AS wordChinese " +
                     "FROM questions q " +
                     "LEFT JOIN answers a ON a.question_id = q.id AND a.user_id = q.user_id " +
                     "JOIN words w ON w.id = q.word_id " +
                     "WHERE q.exam_id = ? AND q.user_id = ? " +
                     "ORDER BY q.sort_order";

        return jdbc.queryForList(sql, examId, userId);
    }
}
