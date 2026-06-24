package com.demo.wordtest.service;

import com.demo.wordtest.entity.Exam;
import com.demo.wordtest.vo.PaperVO;
import com.demo.wordtest.vo.SubmitRequest;
import com.demo.wordtest.vo.SubmitResultVO;

import java.util.List;

/**
 * 考试服务接口
 * @author wangbo
 */
public interface ExamService {
    /**
     * 查询全部考试列表
     */
    List<Exam> getExamList();

    /**
     * 创建考试（含时间校验），返回创建后的考试对象
     */
    Exam createExam(Exam exam);

    /**
     * 删除考试
     */
    boolean deleteExam(Integer id);

    /**
     * 获取考卷（组卷核心逻辑）
     * 若该用户已生成过考卷则直接返回，否则随机抽题 + 打乱 + 分配题型后持久化
     */
    PaperVO getPaper(Integer examId, Integer userId);

    /**
     * 提交答案并自动判卷
     * 逐题比对答案、写入 answers 表、计算总分并返回详情
     */
    SubmitResultVO submitAnswer(Integer examId, SubmitRequest request);
}
