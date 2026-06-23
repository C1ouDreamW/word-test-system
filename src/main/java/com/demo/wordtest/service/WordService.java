package com.demo.wordtest.service;

import com.demo.wordtest.entity.Word;
import java.util.Map;

public interface WordService {
    // 1. 获取单词列表（包含分页信息和总数）
    Map<String, Object> getWordList(String keyword, String category, int page, int size);
    
    // 2. 添加单词
    Word addWord(Word word);
    
    // 3. 修改单词
    Word updateWord(Word word);
    
    // 4. 删除单词
    boolean deleteWord(Integer id);
}