package com.demo.wordtest.service;

import com.demo.wordtest.entity.Exam;
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
}
