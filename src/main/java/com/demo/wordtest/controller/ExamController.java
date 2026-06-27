package com.demo.wordtest.controller;

import com.demo.wordtest.common.ApiResult;
import com.demo.wordtest.entity.Exam;
import com.demo.wordtest.service.ExamService;
import com.demo.wordtest.vo.PaperVO;
import com.demo.wordtest.vo.SubmitRequest;
import com.demo.wordtest.vo.SubmitResultVO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 考试接口
 * @author wangbo
 */
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    /**
     * GET /api/exams
     * 获取考试列表（附带实时状态）
     */
    @GetMapping
    public ApiResult<List<Map<String, Object>>> list() {
        List<Exam> exams = examService.getExamList();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Map<String, Object>> result = exams.stream().map(e -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", e.getId());
            m.put("title", e.getTitle());
            m.put("startTime", e.getStartTime() != null ? e.getStartTime().format(fmt) : null);
            m.put("endTime", e.getEndTime() != null ? e.getEndTime().format(fmt) : null);
            m.put("questionCount", e.getQuestionCount());
            m.put("category", e.getCategory());
            if (e.getStartTime() != null && e.getEndTime() != null) {
                if (now.isBefore(e.getStartTime())) {
                    m.put("status", "未开始");
                } else if (now.isAfter(e.getEndTime())) {
                    m.put("status", "已结束");
                } else {
                    m.put("status", "进行中");
                }
            }
            return m;
        }).collect(Collectors.toList());
        return ApiResult.ok(result);
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

    /**
     * GET /api/exams/{examId}/paper?userId=1
     * 获取考卷（组卷核心接口）
     * 首次请求时随机抽题 + 打乱 + 分配题型并持久化，后续直接返回已有考卷
     */
    @GetMapping("/{examId}/paper")
    public ApiResult<?> getPaper(@PathVariable Integer examId,
                                 @RequestParam Integer userId) {
        try {
            PaperVO paper = examService.getPaper(examId, userId);
            return ApiResult.ok(paper);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * POST /api/exams/{examId}/submit
     * 提交答案并自动判卷
     */
    @PostMapping("/{examId}/submit")
    public ApiResult<?> submit(@PathVariable Integer examId,
                               @RequestBody SubmitRequest request) {
        try {
            SubmitResultVO result = examService.submitAnswer(examId, request);
            return ApiResult.ok(result);
        } catch (IllegalArgumentException e) {
            return ApiResult.fail(e.getMessage());
        }
    }
}
