package com.demo.wordtest.service.impl;

import com.demo.wordtest.dao.ExamDao;
import com.demo.wordtest.dao.QuestionDao;
import com.demo.wordtest.entity.Exam;
import com.demo.wordtest.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Autowired
    private QuestionDao questionDao;

    @Override
    public List<Exam> getExamList() {
        return examDao.findAll();
    }

    @Override
    public Exam createExam(Exam exam) {
        // 时间校验
        validateExamTime(exam);

        examDao.insert(exam);
        return exam;
    }

    @Override
    @Transactional
    public boolean deleteExam(Integer id) {
        // 级联删除：先删题目，再删考试
        questionDao.deleteByExamId(id);
        return examDao.delete(id) > 0;
    }

    /**
     * 时间校验逻辑：
     * 1. 考试名称不能为空
     * 2. 题目数量必须大于 0
     * 3. 开始时间必须早于结束时间
     */
    private void validateExamTime(Exam exam) {
        if (exam.getTitle() == null || exam.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("考试名称不能为空");
        }
        if (exam.getQuestionCount() == null || exam.getQuestionCount() <= 0) {
            throw new IllegalArgumentException("题目数量必须大于 0");
        }
        if (exam.getStartTime() == null || exam.getEndTime() == null) {
            throw new IllegalArgumentException("考试起止时间不能为空");
        }
        if (!exam.getStartTime().isBefore(exam.getEndTime())) {
            throw new IllegalArgumentException("考试开始时间必须早于结束时间");
        }
    }
}
