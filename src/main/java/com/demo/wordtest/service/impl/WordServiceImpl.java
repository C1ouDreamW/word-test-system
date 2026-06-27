package com.demo.wordtest.service.impl;

import com.demo.wordtest.dao.WordDao;
import com.demo.wordtest.entity.Word;
import com.demo.wordtest.service.WordService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordServiceImpl implements WordService {

    private final WordDao wordDao;

    public WordServiceImpl(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Override
    public Map<String, Object> getWordList(String keyword, String category, int page, int size) {
        // 计算数据库的偏移量 (offset)
        int offset = (page - 1) * size;
        
        // 调用 DAO 获取数据
        List<Word> list = wordDao.findWords(keyword, category, offset, size);
        int total = wordDao.countWords(keyword, category);

        // 按照 API 文档的要求，把结果打包成 Map 传给 Controller
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", page);
        
        return result;
    }

    @Override
    public Word addWord(Word word) {
        wordDao.insert(word);
        return word; 
    }

    @Override
    public Word updateWord(Word word) {
        wordDao.update(word);
        return word;
    }

    @Override
    public boolean deleteWord(Integer id) {
        return wordDao.delete(id) > 0;
    }
}