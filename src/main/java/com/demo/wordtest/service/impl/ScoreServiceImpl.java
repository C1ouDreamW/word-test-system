package com.demo.wordtest.service.impl;

import com.demo.wordtest.dao.AnswerDao;
import com.demo.wordtest.dao.ExamDao;
import com.demo.wordtest.entity.Exam;
import com.demo.wordtest.service.ScoreService;
import com.demo.wordtest.vo.AnswerDetailVO;
import com.demo.wordtest.vo.QuestionStatVO;
import com.demo.wordtest.vo.ScoreVO;
import com.demo.wordtest.vo.StatsVO;
import com.demo.wordtest.vo.SubmitResultVO;
import com.demo.wordtest.vo.WordCloudItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 成绩统计与字符云服务实现
 * @author wangbo
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    private AnswerDao answerDao;

    private final ExamDao examDao;

    public ScoreServiceImpl(ExamDao examDao) {
        this.examDao = examDao;
    }

    @Autowired
    public ScoreServiceImpl(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public List<ScoreVO> getScores(Integer userId, Integer examId) {
        if (userId != null) {
            return getScoresByUserId(userId);
        } else if (examId != null) {
            return getScoresByExamId(examId);
        } else {
            throw new IllegalArgumentException("请提供 userId 或 examId 参数");
        }
    }

    private List<ScoreVO> getScoresByUserId(Integer userId) {
        List<Map<String, Object>> rows = answerDao.findScoresByUserId(userId);
        List<ScoreVO> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            int total = ((Number) row.get("total")).intValue();
            int correctCount = ((Number) row.get("correctCount")).intValue();
            int score = total > 0 ? (int) Math.round((double) correctCount / total * 100) : 0;

            ScoreVO vo = new ScoreVO();
            vo.setUserId(userId);
            vo.setExamId((Integer) row.get("examId"));
            vo.setExamTitle((String) row.get("examTitle"));
            vo.setScore(score);
            vo.setCorrectCount(correctCount);
            vo.setTotal(total);
            result.add(vo);
        }
        return result;
    }

    private List<ScoreVO> getScoresByExamId(Integer examId) {
        List<Map<String, Object>> rows = answerDao.findScoresByExamId(examId);
        List<ScoreVO> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            int total = ((Number) row.get("total")).intValue();
            int correctCount = ((Number) row.get("correctCount")).intValue();
            int score = total > 0 ? (int) Math.round((double) correctCount / total * 100) : 0;

            ScoreVO vo = new ScoreVO();
            vo.setUserId(((Number) row.get("userId")).intValue());
            vo.setUsername((String) row.get("username"));
            vo.setExamId(examId);
            vo.setScore(score);
            vo.setCorrectCount(correctCount);
            vo.setTotal(total);
            result.add(vo);
        }
        return result;
    }

    @Override
    public StatsVO getStats(Integer examId) {
        // 1. 校验考试是否存在
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            throw new IllegalArgumentException("考试不存在");
        }

        // 2. 查询所有学生的成绩
        List<Map<String, Object>> scoreRows = answerDao.findScoresByExamId(examId);

        // 3. 计算平均分、最高分、最低分
        int totalStudents = scoreRows.size();
        int maxScore = 0;
        int minScore = 100;
        int sumScore = 0;

        for (Map<String, Object> row : scoreRows) {
            int total = ((Number) row.get("total")).intValue();
            int correctCount = ((Number) row.get("correctCount")).intValue();
            int score = total > 0 ? (int) Math.round((double) correctCount / total * 100) : 0;
            sumScore += score;
            if (score > maxScore) {
                maxScore = score;
            }
            if (score < minScore) {
                minScore = score;
            }
        }

        int avgScore = totalStudents > 0 ? (int) Math.round((double) sumScore / totalStudents) : 0;
        if (totalStudents == 0) {
            minScore = 0;
        }

        // 4. 查询各题正确率
        List<Map<String, Object>> rateRows = answerDao.findQuestionCorrectRates(examId);
        List<QuestionStatVO> questionStats = new ArrayList<>();
        for (Map<String, Object> row : rateRows) {
            QuestionStatVO stat = new QuestionStatVO();
            stat.setQuestionId((Integer) row.get("questionId"));

            // 根据题型决定展示什么内容
            String questionType = (String) row.get("questionType");
            String wordEnglish = (String) row.get("wordEnglish");
            String wordChinese = (String) row.get("wordChinese");

            // 展示被考查的单词（与判卷详情的 question 字段一致）
            if ("CN_TO_EN".equals(questionType)) {
                stat.setQuestion(wordChinese);
            } else {
                stat.setQuestion(wordEnglish);
            }

            stat.setCorrectRate(((Number) row.get("correctRate")).intValue());
            questionStats.add(stat);
        }

        StatsVO stats = new StatsVO();
        stats.setExamTitle(exam.getTitle());
        stats.setTotalStudents(totalStudents);
        stats.setAvgScore(avgScore);
        stats.setMaxScore(maxScore);
        stats.setMinScore(minScore);
        stats.setQuestionStats(questionStats);

        return stats;
    }

    @Override
    public List<WordCloudItemVO> getWordCloud(Integer userId) {
        if (userId != null) {
            return answerDao.findWrongWordsByUserId(userId);
        } else {
            return answerDao.findAllWrongWords();
        }
    }

    @Override
    public SubmitResultVO getStudentExamDetail(Integer examId, Integer userId) {
        // 1. 校验考试是否存在
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            throw new IllegalArgumentException("考试不存在");
        }

        // 2. 查询答题详情
        List<Map<String, Object>> rows = answerDao.findAnswerDetailsByExamIdAndUserId(examId, userId);

        int total = rows.size();
        int correctCount = 0;
        List<AnswerDetailVO> details = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Integer isCorrect = row.get("isCorrect") != null
                    ? ((Number) row.get("isCorrect")).intValue() : 0;
            if (isCorrect == 1) {
                correctCount++;
            }

            AnswerDetailVO detail = new AnswerDetailVO();
            detail.setQuestionId((Integer) row.get("questionId"));

            // 根据题型决定展示什么内容作为 question 字段
            String questionType = (String) row.get("questionType");
            String wordEnglish = (String) row.get("wordEnglish");
            String wordChinese = (String) row.get("wordChinese");

            if ("CN_TO_EN".equals(questionType)) {
                detail.setQuestion(wordChinese);
            } else {
                detail.setQuestion(wordEnglish);
            }

            detail.setYourAnswer((String) row.get("userAnswer"));
            detail.setCorrectAnswer((String) row.get("correctAnswer"));
            detail.setCorrect(isCorrect == 1);

            details.add(detail);
        }

        int score = total > 0 ? (int) Math.round((double) correctCount / total * 100) : 0;

        return new SubmitResultVO(score, total, correctCount, details);
    }
}
