package com.demo.wordtest.controller;

import com.demo.wordtest.common.ApiResult;
import com.demo.wordtest.entity.Exam;
import com.demo.wordtest.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考试接口
 * @author wangbo
 */
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    /**
     * GET /api/exams
     * 获取考试列表
     */
    @GetMapping
    public ApiResult<List<Exam>> list() {
        List<Exam> exams = examService.getExamList();
        return ApiResult.ok(exams);
    }

    /**
     * POST /api/exams
     * 创建考试
     */
    @PostMapping
    public ApiResult<?> create(@RequestBody Exam exam) {
        try {
            Exam created = examService.createExam(exam);
            return ApiResult.ok(created);
        } catch (IllegalArgumentException e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * DELETE /api/exams/{id}
     * 删除考试（级联删除关联题目）
     */
    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Integer id) {
        boolean success = examService.deleteExam(id);
        if (success) {
            return ApiResult.ok("删除成功");
        } else {
            return ApiResult.fail("考试不存在或删除失败");
        }
    }
}
