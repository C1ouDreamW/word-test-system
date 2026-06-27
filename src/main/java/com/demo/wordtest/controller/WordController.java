package com.demo.wordtest.controller;

import com.demo.wordtest.common.ApiResult;
import com.demo.wordtest.entity.Word;
import com.demo.wordtest.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/words") // 统一路径前缀
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * GET /api/words
     * 获取单词列表（支持分页和关键字/分类搜索）
     */
    @GetMapping
    public ApiResult<Map<String, Object>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Map<String, Object> result = wordService.getWordList(keyword, category, page, size);
        return ApiResult.ok(result);
    }

    /**
     * POST /api/words
     * 添加新单词
     */
    @PostMapping
    public ApiResult<Word> add(@RequestBody Word word) {
        return ApiResult.ok(wordService.addWord(word));
    }

    /**
     * PUT /api/words/{id}
     * 修改单词
     */
    @PutMapping("/{id}")
    public ApiResult<Word> update(@PathVariable Integer id, @RequestBody Word word) {
        word.setId(id); // 确保修改的是路径里指定的那个单词
        return ApiResult.ok(wordService.updateWord(word));
    }

    /**
     * DELETE /api/words/{id}
     * 删除单词
     */
    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Integer id) {
        boolean success = wordService.deleteWord(id);
        if (success) {
            return ApiResult.ok("删除成功");
        } else {
            return ApiResult.fail("删除失败");
        }
    }
}