package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.WordDao;
import com.demo.wordtest.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WordDaoImpl implements WordDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Word> findWords(String keyword, String category, int offset, int size) {
        StringBuilder sql = new StringBuilder("SELECT id, english, chinese, category FROM words WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (english LIKE ? OR chinese LIKE ?) ");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append("AND category = ? ");
            params.add(category);
        }
        
        sql.append("LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        return jdbc.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(Word.class));
    }

    @Override
    public int countWords(String keyword, String category) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM words WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (english LIKE ? OR chinese LIKE ?) ");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append("AND category = ? ");
            params.add(category);
        }

        Integer count = jdbc.queryForObject(sql.toString(), params.toArray(), Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public int insert(Word word) {
        String sql = "INSERT INTO words (english, chinese, category) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, word.getEnglish());
            ps.setString(2, word.getChinese());
            ps.setString(3, word.getCategory());
            return ps;
        }, keyHolder);
        // 回填自增ID
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            word.setId(generatedId.intValue());
        }
        return rows;
    }

    @Override
    public int update(Word word) {
        String sql = "UPDATE words SET english = ?, chinese = ?, category = ? WHERE id = ?";
        return jdbc.update(sql, word.getEnglish(), word.getChinese(), word.getCategory(), word.getId());
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM words WHERE id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public List<Word> findAll() {
        String sql = "SELECT id, english, chinese, category FROM words";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Word.class));
    }

    @Override
    public List<Word> findRandomWords(int count) {
        String sql = "SELECT id, english, chinese, category FROM words ORDER BY RAND() LIMIT ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Word.class), count);
    }
}