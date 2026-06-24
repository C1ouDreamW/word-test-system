package com.demo.wordtest.service;

import com.demo.wordtest.vo.ScoreVO;
import com.demo.wordtest.vo.StatsVO;
import com.demo.wordtest.vo.SubmitResultVO;
import com.demo.wordtest.vo.WordCloudItemVO;

import java.util.List;

/**
 * 成绩统计与字符云服务接口
 * @author wangbo
 */
public interface ScoreService {

    /**
     * 查询成绩：按用户或按考试
     * @param userId 学生 ID（与 examId 二选一）
     * @param examId 考试 ID（与 userId 二选一）
     * @return 成绩列表
     */
    List<ScoreVO> getScores(Integer userId, Integer examId);

    /**
     * 获取考试统计（平均分、最高分、最低分、各题正确率）
     */
    StatsVO getStats(Integer examId);

    /**
     * 获取字符云数据
     * @param userId 可选，传入时查询该学生的错词，不传时查询全局错词
     * @return 错词及频次列表
     */
    List<WordCloudItemVO> getWordCloud(Integer userId);

    /**
     * 查询某学生在某次考试中的完整答题详情（管理员查看用）
     * @param examId 考试 ID
     * @param userId 学生 ID
     * @return 判卷结果（含逐题详情）
     */
    SubmitResultVO getStudentExamDetail(Integer examId, Integer userId);
}
