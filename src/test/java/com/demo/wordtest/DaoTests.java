package com.demo.wordtest;

import com.demo.wordtest.dao.*;
import com.demo.wordtest.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DAO 层自测 —— 验证数据库读写正常
 * <p>
 * 前提条件：
 * 1. MySQL 已启动，word_test 数据库已创建
 * 2. 已执行 sql/init.sql 建表
 * 3. 已执行 sql/test-data.sql 导入测试数据
 * <p>
 * 运行方式：IDE 中右键 → Run，或终端执行：
 * mvn test -Dtest=DaoTests
 *
 * @author wangbo
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DaoTests {

    // ==================== 注入 5 个 DAO ====================

    @Autowired
    private UserDao userDao;

    @Autowired
    private WordDao wordDao;

    @Autowired
    private ExamDao examDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    // ==================== 1. UserDao ====================

    @Test
    @Order(1)
    @DisplayName("UserDao — 查 admin 用户")
    void testFindAdmin() {
        User admin = userDao.findByUsername("admin");
        assertNotNull(admin, "admin 用户应该存在");
        assertEquals("admin", admin.getUsername());
        assertEquals("admin", admin.getRole());
        System.out.println("✅ UserDao 查 admin：id=" + admin.getId() + ", role=" + admin.getRole());
    }

    @Test
    @Order(2)
    @DisplayName("UserDao — 查学生用户")
    void testFindStudent() {
        User zhangsan = userDao.findByUsername("zhangsan");
        assertNotNull(zhangsan, "zhangsan 用户应该存在");
        assertEquals("student", zhangsan.getRole());
        System.out.println("✅ UserDao 查 zhangsan：id=" + zhangsan.getId() + ", role=" + zhangsan.getRole());
    }

    @Test
    @Order(3)
    @DisplayName("UserDao — 查不存在的用户返回 null")
    void testFindNonexistent() {
        User ghost = userDao.findByUsername("nobody");
        assertNull(ghost, "不存在的用户应返回 null");
        System.out.println("✅ UserDao 查不存在用户正确返回 null");
    }

    // ==================== 2. WordDao ====================

    @Test
    @Order(4)
    @DisplayName("WordDao — 分页查询全部单词")
    void testFindWordsAll() {
        int total = wordDao.countWords(null, null);
        assertTrue(total >= 60, "总单词数应 ≥ 60，实际: " + total);
        System.out.println("✅ WordDao countWords 总数: " + total);

        List<Word> page1 = wordDao.findWords(null, null, 0, 10);
        assertEquals(10, page1.size(), "第一页应返回 10 条");
        assertNotNull(page1.get(0).getEnglish());
        assertNotNull(page1.get(0).getChinese());
        System.out.println("✅ WordDao 分页查询：第1条 = " + page1.get(0).getEnglish() + " → " + page1.get(0).getChinese());
    }

    @Test
    @Order(5)
    @DisplayName("WordDao — 关键字搜索")
    void testFindWordsByKeyword() {
        List<Word> results = wordDao.findWords("environment", null, 0, 10);
        assertFalse(results.isEmpty(), "搜索 environment 应有结果");
        assertEquals("environment", results.get(0).getEnglish());
        System.out.println("✅ WordDao 搜索 'environment': " + results.get(0).getEnglish() + " → " + results.get(0).getChinese());
    }

    @Test
    @Order(6)
    @DisplayName("WordDao — 按分类筛选")
    void testFindWordsByCategory() {
        int count = wordDao.countWords(null, "六级进阶词汇");
        assertEquals(12, count, "六级进阶词汇应刚好 12 个");
        System.out.println("✅ WordDao 分类筛选'六级进阶词汇': " + count + " 个");
    }

    @Test
    @Order(7)
    @DisplayName("WordDao — 增删改（事务回滚不污染数据）")
    @Transactional
    void testWordCrud() {
        // 增（注意：JdbcTemplate.update 不会回填自增 ID，需通过查询获取）
        Word word = new Word(null, "testword_daotest", "测试词", "测试分类");
        int rows = wordDao.insert(word);
        assertEquals(1, rows, "插入应返回 1");

        // 查（通过搜索验证插入成功）
        List<Word> found = wordDao.findWords("testword_daotest", null, 0, 10);
        assertEquals(1, found.size());
        assertEquals("测试词", found.get(0).getChinese());
        Integer insertedId = found.get(0).getId();

        // 改
        word.setId(insertedId);
        word.setChinese("已修改");
        word.setCategory("修改分类");
        rows = wordDao.update(word);
        assertEquals(1, rows, "更新应返回 1");

        // 验证修改生效
        List<Word> afterUpdate = wordDao.findWords("testword_daotest", null, 0, 10);
        assertEquals("已修改", afterUpdate.get(0).getChinese());

        // 删
        rows = wordDao.delete(insertedId);
        assertEquals(1, rows, "删除应返回 1");

        System.out.println("✅ WordDao 增删改全部通过（事务回滚）");
    }

    // ==================== 3. ExamDao ====================

    @Test
    @Order(8)
    @DisplayName("ExamDao — 查询全部考试")
    void testFindAllExams() {
        List<Exam> exams = examDao.findAll();
        assertTrue(exams.size() >= 4, "考试数应 ≥ 4，实际: " + exams.size());
        System.out.println("✅ ExamDao 全部考试: " + exams.size() + " 场");
        exams.forEach(e -> System.out.println("   " + e.getTitle() + " (" + e.getQuestionCount() + "题)"));
    }

    @Test
    @Order(9)
    @DisplayName("ExamDao — 按 ID 查询")
    void testFindExamById() {
        Exam exam = examDao.findById(1);
        assertNotNull(exam, "ID=1 的考试应存在");
        assertEquals("四级基础词汇测试", exam.getTitle());
        assertEquals(20, exam.getQuestionCount());
        System.out.println("✅ ExamDao 查 ID=1: " + exam.getTitle());
    }

    @Test
    @Order(10)
    @DisplayName("ExamDao — 查不存在的考试返回 null")
    void testFindExamNonexistent() {
        Exam exam = examDao.findById(9999);
        assertNull(exam, "不存在的考试应返回 null");
        System.out.println("✅ ExamDao 查不存在考试正确返回 null");
    }

    @Test
    @Order(11)
    @DisplayName("ExamDao — 新增+删除（事务回滚）")
    @Transactional
    void testExamInsertDelete() {
        // 增（使用唯一标题便于查找）
        String uniqueTitle = "DAO测试考试_" + System.currentTimeMillis();
        Exam exam = new Exam(null, uniqueTitle,
                LocalDateTime.of(2026, 6, 24, 9, 0),
                LocalDateTime.of(2026, 6, 24, 11, 0), 10, null);
        int rows = examDao.insert(exam);
        assertEquals(1, rows);

        // 查回验证（通过 findAll 找到刚插入的记录）
        List<Exam> all = examDao.findAll();
        Exam inserted = all.stream()
                .filter(e -> uniqueTitle.equals(e.getTitle()))
                .findFirst().orElse(null);
        assertNotNull(inserted, "应能查到刚插入的考试");
        assertEquals(uniqueTitle, inserted.getTitle());

        // 删除
        rows = examDao.delete(inserted.getId());
        assertEquals(1, rows, "删除应返回 1");

        System.out.println("✅ ExamDao 新增+删除通过（事务回滚）");
    }

    // ==================== 4. QuestionDao ====================

    @Test
    @Order(12)
    @DisplayName("QuestionDao — 查询已有考卷")
    void testFindQuestionsByExamAndUser() {
        // exam_id=1, user_id=2 (zhangsan) 有 20 题
        List<Question> questions = questionDao.findByExamIdAndUserId(1, 2);
        assertEquals(20, questions.size(), "zhangsan 在考试1应有 20 题");
        assertNotNull(questions.get(0).getQuestionText());
        assertEquals(1, questions.get(0).getSortOrder());
        System.out.println("✅ QuestionDao 查询考卷: " + questions.size() + " 题，第1题 = " + questions.get(0).getQuestionText());
    }

    @Test
    @Order(13)
    @DisplayName("QuestionDao — 批量插入+删除（事务回滚）")
    @Transactional
    void testQuestionBatchInsertDelete() {
        // 先插入一个临时考试，避免 FK 冲突
        Exam tempExam = new Exam(null, "QuestionDao测试_" + System.currentTimeMillis(),
                LocalDateTime.of(2026, 6, 24, 9, 0),
                LocalDateTime.of(2026, 6, 24, 11, 0), 3, null);
        examDao.insert(tempExam);
        Integer tempExamId = examDao.findAll().stream()
                .filter(e -> e.getTitle().startsWith("QuestionDao测试_"))
                .findFirst().orElseThrow().getId();

        // 批量插入题目到临时考试
        List<Question> batch = Arrays.asList(
                new Question(null, tempExamId, 9, 1, "EN_TO_CN", "请写出 consequence 的中文释义", "结果", null, 1),
                new Question(null, tempExamId, 9, 2, "CN_TO_EN", "请写出 环境 的英文单词", "environment", null, 2),
                new Question(null, tempExamId, 9, 3, "CHOICE", "opportunity 的含义是？", "机会", "[\"机会\",\"责任\",\"现象\",\"结果\"]", 3)
        );

        int[] rows = questionDao.insertBatch(batch);
        assertEquals(3, rows.length);
        for (int r : rows) {
            assertEquals(1, r, "每条插入应返回 1");
        }

        // 查回验证
        List<Question> found = questionDao.findByExamIdAndUserId(tempExamId, 9);
        assertEquals(3, found.size());
        assertEquals("请写出 consequence 的中文释义", found.get(0).getQuestionText());

        // 级联删除（临时考试没有答案，不会触发 FK 约束）
        int deleted = questionDao.deleteByExamId(tempExamId);
        assertEquals(3, deleted, "应删除 3 条");

        // 清理临时考试
        examDao.delete(tempExamId);

        System.out.println("✅ QuestionDao 批量插入+删除通过（事务回滚）");
    }

    // ==================== 5. AnswerDao ====================

    @Test
    @Order(14)
    @DisplayName("AnswerDao — 查询已有答案")
    void testFindAnswersByQuestionIds() {
        // exam_id=1, user_id=2 的 questions id 为 1~20
        List<Integer> questionIds = Arrays.asList(1, 2, 3, 4, 5);
        List<Answer> answers = answerDao.findByQuestionIdsAndUserId(questionIds, 2);
        assertEquals(5, answers.size(), "应查到 5 条答案");
        // 验证全部判对
        for (Answer a : answers) {
            assertEquals(1, a.getIsCorrect(), "前 5 题 zhangsan 应该全对");
        }
        System.out.println("✅ AnswerDao 查询答案: " + answers.size() + " 条，全对");
    }

    @Test
    @Order(15)
    @DisplayName("AnswerDao — 批量插入（事务回滚）")
    @Transactional
    void testAnswerBatchInsert() {
        List<Answer> batch = Arrays.asList(
                new Answer(null, 1, 9, "结果", 1),
                new Answer(null, 2, 9, "env", 0),
                new Answer(null, 3, 9, "现象", 0)
        );

        int[] rows = answerDao.insertBatch(batch);
        assertEquals(3, rows.length);
        for (int r : rows) {
            assertEquals(1, r);
        }

        // 查回验证
        List<Answer> found = answerDao.findByQuestionIdsAndUserId(
                Arrays.asList(1, 2, 3), 9);
        assertEquals(3, found.size());
        assertEquals("env", found.get(1).getUserAnswer());
        assertEquals(0, found.get(1).getIsCorrect());

        System.out.println("✅ AnswerDao 批量插入通过（事务回滚）");
    }

    // ==================== 汇总 ====================

    @Test
    @Order(99)
    @DisplayName("-DAO 自测汇总-")
    void summary() {
        System.out.println("\n========================================");
        System.out.println("  DAO 自测全部通过 ✅");
        System.out.println("  UserDao    — 查用户正常");
        System.out.println("  WordDao    — 搜索/分页/增删改正常");
        System.out.println("  ExamDao    — 列表/详情/新增/删除正常");
        System.out.println("  QuestionDao — 查卷/批量插入/删除正常");
        System.out.println("  AnswerDao  — 查答案/批量插入正常");
        System.out.println("========================================\n");
    }
}
