-- =============================================
-- 专业英语单词测试系统 — 数据库初始化脚本
-- 数据库：word_test
-- 字符集：utf8mb4
-- =============================================

-- =============================================
-- 仅用于初始化数据库，执行前请确保已创建数据库
-- =============================================

CREATE DATABASE IF NOT EXISTS word_test DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE word_test;

-- =============================================
-- 1. 用户表
-- =============================================
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS exams;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL COMMENT 'admin 或 student'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- =============================================
-- 2. 单词表
-- =============================================
CREATE TABLE words (
    id INT AUTO_INCREMENT PRIMARY KEY,
    english VARCHAR(100) NOT NULL COMMENT '英文单词',
    chinese VARCHAR(200) NOT NULL COMMENT '中文释义',
    category VARCHAR(100) COMMENT '分类，如：网络基础、路由协议'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单词表';

-- =============================================
-- 3. 考试表
-- =============================================
CREATE TABLE exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '考试名称',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    question_count INT NOT NULL COMMENT '题目数量',
    category VARCHAR(100) COMMENT '限定分类，为空则不限制'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试表';

-- =============================================
-- 4. 题目表
-- =============================================
CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    exam_id INT NOT NULL COMMENT '关联 exams.id',
    user_id INT NOT NULL COMMENT '关联 users.id',
    word_id INT NOT NULL COMMENT '关联 words.id',
    question_type VARCHAR(20) NOT NULL COMMENT 'EN_TO_CN / CN_TO_EN / CHOICE',
    question_text VARCHAR(200) NOT NULL COMMENT '题干',
    correct_answer VARCHAR(200) NOT NULL COMMENT '正确答案',
    options VARCHAR(500) COMMENT 'JSON数组，选择题用，非选择题为 NULL',
    sort_order INT NOT NULL COMMENT '排序号，体现随机题序',
    FOREIGN KEY (exam_id) REFERENCES exams(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (word_id) REFERENCES words(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目表';

-- =============================================
-- 5. 答案表
-- =============================================
CREATE TABLE answers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL COMMENT '关联 questions.id',
    user_id INT NOT NULL COMMENT '关联 users.id',
    user_answer VARCHAR(200) COMMENT '学生提交的答案',
    is_correct TINYINT NOT NULL DEFAULT 0 COMMENT '0 错 / 1 对',
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='答案表';

-- =============================================
-- 预置数据：用户（后续通过应用界面或脚本创建）
-- =============================================
-- 暂无预置数据
