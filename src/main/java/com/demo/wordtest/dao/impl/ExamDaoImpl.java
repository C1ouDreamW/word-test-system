package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.ExamDao;
import com.demo.wordtest.entity.Exam;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Exam表数据访问层实现类
 * @author wangbo
 */
@Repository
public class ExamDaoImpl implements ExamDao {

    private final JdbcTemplate jdbc;

    public ExamDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Exam> findAll() {
        String sql = "SELECT id, title, start_time AS startTime, end_time AS endTime, "
                   + "question_count AS questionCount, category FROM exams ORDER BY id DESC";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Exam.class));
    }

    @Override
    public Exam findById(Integer id) {
        String sql = "SELECT id, title, start_time AS startTime, end_time AS endTime, "
                   + "question_count AS questionCount, category FROM exams WHERE id = ?";
        try {
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Exam.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insert(Exam exam) {
        String sql = "INSERT INTO exams (title, start_time, end_time, question_count, category) "
                   + "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, exam.getTitle());
            ps.setObject(2, exam.getStartTime());
            ps.setObject(3, exam.getEndTime());
            ps.setInt(4, exam.getQuestionCount());
            ps.setString(5, exam.getCategory());
            return ps;
        }, keyHolder);
        // 回填自增ID
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            exam.setId(generatedId.intValue());
        }
        return rows;
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM exams WHERE id = ?";
        return jdbc.update(sql, id);
    }
}
