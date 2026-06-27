package com.demo.wordtest.service.impl;

import com.demo.wordtest.dao.AnswerDao;
import com.demo.wordtest.dao.ExamDao;
import com.demo.wordtest.dao.QuestionDao;
import com.demo.wordtest.dao.WordDao;
import com.demo.wordtest.entity.Answer;
import com.demo.wordtest.entity.Exam;
import com.demo.wordtest.entity.Question;
import com.demo.wordtest.entity.Word;
import com.demo.wordtest.service.ExamService;
import com.demo.wordtest.vo.AnswerDetailVO;
import com.demo.wordtest.vo.PaperVO;
import com.demo.wordtest.vo.QuestionVO;
import com.demo.wordtest.vo.SubmitRequest;
import com.demo.wordtest.vo.SubmitResultVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamDao examDao;
    private final QuestionDao questionDao;
    private final WordDao wordDao;
    private final AnswerDao answerDao;
    private final ObjectMapper objectMapper;
    private static final String[] QUESTION_TYPES = {"EN_TO_CN", "CN_TO_EN", "CHOICE"};
    private final Random random = new Random();

    public ExamServiceImpl(ExamDao examDao, QuestionDao questionDao, WordDao wordDao, AnswerDao answerDao, ObjectMapper objectMapper) {
        this.examDao = examDao;
        this.questionDao = questionDao;
        this.wordDao = wordDao;
        this.answerDao = answerDao;
        this.objectMapper = objectMapper;
    }

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

    @Override
    @Transactional
    public PaperVO getPaper(Integer examId, Integer userId) {
        // 1. 校验考试是否存在
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            throw new IllegalArgumentException("考试不存在");
        }
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        // 2. 检查该用户是否已生成过考卷
        List<Question> existingQuestions = questionDao.findByExamIdAndUserId(examId, userId);
        if (!existingQuestions.isEmpty()) {
            return buildPaperVO(exam, existingQuestions);
        }

        // 3. 随机抽取 N 个单词（若考试指定了分类则限定分类）
        List<Word> words = wordDao.findRandomWords(exam.getQuestionCount(), exam.getCategory());
        if (words.size() < exam.getQuestionCount()) {
            String category = exam.getCategory();
            if (category != null && !category.trim().isEmpty()) {
                throw new IllegalStateException("分类「" + category + "」下单词不足" + exam.getQuestionCount() + "个，无法生成考卷");
            }
            throw new IllegalStateException("单词库数量不足，无法生成考卷");
        }

        // 4. 随机打乱顺序
        Collections.shuffle(words, random);

        // 5. 预加载单词（供 CHOICE 题型生成干扰项，若考试指定了分类则限定分类）
        List<Word> allWords = wordDao.findAll(exam.getCategory());

        // 6. 逐题生成
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            String questionType = randomQuestionType();

            Question q = new Question();
            q.setExamId(examId);
            q.setUserId(userId);
            q.setWordId(word.getId());
            q.setQuestionType(questionType);
            q.setSortOrder(i + 1);

            switch (questionType) {
                case "EN_TO_CN":
                    q.setQuestionText("请写出 \"" + word.getEnglish() + "\" 的中文释义");
                    q.setCorrectAnswer(word.getChinese());
                    q.setOptions(null);
                    break;

                case "CN_TO_EN":
                    q.setQuestionText("请写出 \"" + word.getChinese() + "\" 的英文单词");
                    q.setCorrectAnswer(word.getEnglish());
                    q.setOptions(null);
                    break;

                case "CHOICE":
                    q.setQuestionText("\"" + word.getEnglish() + "\" 的中文含义是？");
                    q.setCorrectAnswer(word.getChinese());
                    // 生成 4 个中文选项（1 个正确 + 3 个随机干扰项）
                    List<String> options = generateChoiceOptions(word, allWords);
                    q.setOptions(toJsonString(options));
                    break;
            }
            questions.add(q);
        }

        // 7. 批量插入 questions 表
        questionDao.insertBatch(questions);

        // 8. 重新查询以获取自增 ID（batchInsert 不回填 ID）
        List<Question> persistedQuestions = questionDao.findByExamIdAndUserId(examId, userId);

        // 9. 组装返回
        return buildPaperVO(exam, persistedQuestions);
    }

    /**
     * 随机分配题型
     */
    private String randomQuestionType() {
        return QUESTION_TYPES[random.nextInt(QUESTION_TYPES.length)];
    }

    /**
     * 为 CHOICE 题型生成 4 个中文选项（1 正确 + 3 随机干扰项）
     */
    private List<String> generateChoiceOptions(Word correctWord, List<Word> allWords) {
        // 收集所有其他单词的中文释义作为干扰项池
        List<String> otherChinese = allWords.stream()
                .filter(w -> !w.getId().equals(correctWord.getId()))
                .map(Word::getChinese)
                .collect(Collectors.toList());

        // 随机选 3 个干扰项
        Collections.shuffle(otherChinese, random);
        List<String> distractors = otherChinese.stream()
                .limit(3)
                .toList();

        // 合并正确选项 + 干扰项，再次打乱
        List<String> options = new ArrayList<>();
        options.add(correctWord.getChinese());
        options.addAll(distractors);
        Collections.shuffle(options, random);

        return options;
    }

    /**
     * 将 List 转为 JSON 数组字符串
     */
    private String toJsonString(List<String> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("选项序列化失败", e);
        }
    }

    /**
     * 将 JSON 数组字符串解析为 List
     */
    private List<String> parseOptions(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("选项反序列化失败", e);
        }
    }

    /**
     * 组装 PaperVO
     */
    private PaperVO buildPaperVO(Exam exam, List<Question> questions) {
        // 计算剩余秒数
        long remainingSeconds = Duration.between(LocalDateTime.now(), exam.getEndTime()).getSeconds();
        if (remainingSeconds < 0) {
            remainingSeconds = 0;
        }

        // 转换题目列表
        List<QuestionVO> questionVOs = questions.stream()
                .map(q -> new QuestionVO(
                        q.getId(),
                        q.getQuestionType(),
                        q.getQuestionText(),
                        parseOptions(q.getOptions())
                ))
                .collect(Collectors.toList());

        return new PaperVO(exam.getId(), exam.getTitle(), remainingSeconds, questionVOs);
    }

    // ==================== 判卷 ====================

    @Override
    @Transactional
    public SubmitResultVO submitAnswer(Integer examId, SubmitRequest request) {
        Integer userId = request.getUserId();
        List<SubmitRequest.AnswerItem> submittedAnswers = request.getAnswers();

        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (submittedAnswers == null || submittedAnswers.isEmpty()) {
            throw new IllegalArgumentException("答案列表不能为空");
        }

        // 1. 查出该用户该考试的考卷题目
        List<Question> questions = questionDao.findByExamIdAndUserId(examId, userId);
        if (questions.isEmpty()) {
            throw new IllegalArgumentException("该用户尚未生成考卷，请先获取考卷");
        }

        // 2. 检查是否已提交过（防重复提交）
        List<Integer> questionIds = submittedAnswers.stream()
                .map(SubmitRequest.AnswerItem::getQuestionId)
                .collect(Collectors.toList());
        List<com.demo.wordtest.entity.Answer> existingAnswers =
                answerDao.findByQuestionIdsAndUserId(questionIds, userId);
        if (!existingAnswers.isEmpty()) {
            throw new IllegalArgumentException("您已提交过该考卷，请勿重复提交");
        }

        // 3. 批量查词（供 detail 中展示单词用）
        List<Integer> wordIds = questions.stream()
                .map(Question::getWordId)
                .distinct()
                .collect(Collectors.toList());
        Map<Integer, Word> wordMap = wordDao.findByIds(wordIds).stream()
                .collect(Collectors.toMap(Word::getId, w -> w));

        // 4. 构建 questionId → Question 的快速查找表
        Map<Integer, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        // 5. 逐题判分
        List<Answer> answerRecords = new ArrayList<>();
        List<AnswerDetailVO> details = new ArrayList<>();
        int correctCount = 0;

        for (SubmitRequest.AnswerItem item : submittedAnswers) {
            Question q = questionMap.get(item.getQuestionId());
            if (q == null) {
                continue; // 忽略无效的 questionId
            }

            boolean correct = judgeCorrect(q, item.getAnswer());
            if (correct) {
                correctCount++;
            }

            // 构建答案记录
            Answer record = new Answer();
            record.setQuestionId(item.getQuestionId());
            record.setUserId(userId);
            record.setUserAnswer(item.getAnswer());
            record.setIsCorrect(correct ? 1 : 0);
            answerRecords.add(record);

            // 构建判卷详情
            Word word = wordMap.get(q.getWordId());
            String questionWord = getQuestionWord(q, word);

            AnswerDetailVO detail = new AnswerDetailVO();
            detail.setQuestionId(item.getQuestionId());
            detail.setQuestion(questionWord);
            detail.setYourAnswer(item.getAnswer());
            detail.setCorrectAnswer(q.getCorrectAnswer());
            detail.setCorrect(correct);
            details.add(detail);
        }

        // 6. 批量写入 answers 表
        answerDao.insertBatch(answerRecords);

        // 7. 计算总分（百分制，四舍五入）
        int total = questions.size();
        int score = total > 0 ? (int) Math.round((double) correctCount / total * 100) : 0;

        return new SubmitResultVO(score, total, correctCount, details);
    }

    /**
     * 判题：填空忽略大小写和首尾空格，选择直接字符串匹配
     */
    private boolean judgeCorrect(Question q, String userAnswer) {
        if (userAnswer == null) {
            return false;
        }

        String trimmed = userAnswer.trim();
        String expected = q.getCorrectAnswer().trim();

        if ("CHOICE".equals(q.getQuestionType())) {
            // 选择题：严格匹配
            return trimmed.equals(expected);
        } else {
            // 填空题（EN_TO_CN / CN_TO_EN）：忽略大小写
            return trimmed.equalsIgnoreCase(expected);
        }
    }

    /**
     * 根据题型返回被考查的单词文本（用于 detail.question 字段）
     */
    private String getQuestionWord(Question q, Word word) {
        if (word == null) {
            return "";
        }
        if ("CN_TO_EN".equals(q.getQuestionType())){
            return word.getChinese();
        }else{
            return word.getEnglish();
        }
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
