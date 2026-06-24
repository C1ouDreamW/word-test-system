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

    // 6. 查询全部单词（供组卷时生成选择题干扰项）
    List<Word> findAll();

    // 6b. 按分类查询全部单词（供限定分类组卷时生成干扰项）
    List<Word> findAll(String category);

    // 7. 随机抽取 N 个单词（ORDER BY RAND()）
    List<Word> findRandomWords(int count);

    // 7b. 按分类随机抽取 N 个单词
    List<Word> findRandomWords(int count, String category);
}