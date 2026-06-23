package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.ExamDao;
import com.demo.wordtest.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Exam表数据访问层实现类
 * @author wangbo
 */
@Repository
public class ExamDaoImpl implements ExamDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Exam> findAll() {
        String sql = "SELECT id, title, start_time AS startTime, end_time AS endTime, "
                   + "question_count AS questionCount FROM exams ORDER BY id DESC";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Exam.class));
    }

    @Override
    public Exam findById(Integer id) {
        String sql = "SELECT id, title, start_time AS startTime, end_time AS endTime, "
                   + "question_count AS questionCount FROM exams WHERE id = ?";
        try {
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Exam.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insert(Exam exam) {
        String sql = "INSERT INTO exams (title, start_time, end_time, question_count) "
                   + "VALUES (?, ?, ?, ?)";
        return jdbc.update(sql,
                exam.getTitle(),
                exam.getStartTime(),
                exam.getEndTime(),
                exam.getQuestionCount());
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM exams WHERE id = ?";
        return jdbc.update(sql, id);
    }
}
