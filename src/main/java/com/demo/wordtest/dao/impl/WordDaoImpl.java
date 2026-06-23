package com.demo.wordtest.dao.impl;

import com.demo.wordtest.dao.WordDao;
import com.demo.wordtest.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return jdbc.update(sql, word.getEnglish(), word.getChinese(), word.getCategory());
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
}