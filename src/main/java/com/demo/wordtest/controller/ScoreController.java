package com.demo.wordtest.controller;

import com.demo.wordtest.common.ApiResult;
import com.demo.wordtest.service.ScoreService;
import com.demo.wordtest.vo.ScoreVO;
import com.demo.wordtest.vo.StatsVO;
import com.demo.wordtest.vo.WordCloudItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成绩统计与字符云接口
 * @author wangbo
 */
@RestController
@RequestMapping("/api")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    /**
     * GET /api/scores?userId=1      查询某个学生的所有考试成绩
     * GET /api/scores?examId=1      查询某次考试的所有学生成绩
     */
    @GetMapping("/scores")
    public ApiResult<?> getScores(@RequestParam(required = false) Integer userId,
                                   @RequestParam(required = false) Integer examId) {
        try {
            List<ScoreVO> scores = scoreService.getScores(userId, examId);
            return ApiResult.ok(scores);
        } catch (IllegalArgumentException e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * GET /api/stats/{examId}
     * 考试统计：平均分、最高分、最低分、各题正确率
     */
    @GetMapping("/stats/{examId}")
    public ApiResult<?> getStats(@PathVariable Integer examId) {
        try {
            StatsVO stats = scoreService.getStats(examId);
            return ApiResult.ok(stats);
        } catch (IllegalArgumentException e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * GET /api/wordcloud?userId=1   查询某个学生的错词及频次
     * GET /api/wordcloud             查询全局错词及频次
     */
    @GetMapping("/wordcloud")
    public ApiResult<?> getWordCloud(@RequestParam(required = false) Integer userId) {
        List<WordCloudItemVO> items = scoreService.getWordCloud(userId);
        return ApiResult.ok(items);
    }
}
