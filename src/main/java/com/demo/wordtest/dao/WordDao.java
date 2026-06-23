package com.demo.wordtest.dao;

import com.demo.wordtest.entity.Word;
import java.util.List;

public interface WordDao {
    // 1. 查询单词列表（支持根据英文或中文关键字、分类进行模糊查询）
    List<Word> findWords(String keyword, String category, int offset, int size);
    
    // 2. 统计单词总数（用于前端分页）
    int countWords(String keyword, String category);

    // 3. 新增单词
    int insert(Word word);

    // 4. 修改单词
    int update(Word word);

    // 5. 删除单词
    int delete(Integer id);
}