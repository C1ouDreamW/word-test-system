package com.demo.wordtest.dao;

import com.demo.wordtest.entity.Exam;
import java.util.List;

public interface ExamDao {
    /**
     * 查询全部考试列表
     */
    List<Exam> findAll();

    /**
     * 根据 ID 查询单个考试
     */
    Exam findById(Integer id);

    /**
     * 新增考试，返回受影响行数
     */
    int insert(Exam exam);

    /**
     * 根据 ID 删除考试
     */
    int delete(Integer id);
}
