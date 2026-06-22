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
    question_count INT NOT NULL COMMENT '题目数量'
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
-- 预置数据：用户
-- =============================================
INSERT INTO users (username, role) VALUES
('admin',    'admin'),
('zhangsan', 'student'),
('lisi',     'student'),
('wangwu',   'student');

-- =============================================
-- 预置数据：单词（网络工程导论）
-- =============================================
INSERT INTO words (english, chinese, category) VALUES
('router',      '路由器',    '网络基础'),
('switch',      '交换机',    '网络基础'),
('subnet',      '子网',      '网络基础'),
('gateway',     '网关',      '网络基础'),
('firewall',    '防火墙',    '网络安全'),
('protocol',    '协议',      '网络基础'),
('bandwidth',   '带宽',      '网络基础'),
('latency',     '延迟',      '网络基础'),
('throughput',  '吞吐量',    '网络基础'),
('packet',      '数据包',    '网络基础'),
('topology',    '拓扑',      '网络基础'),
('encryption',  '加密',      '网络安全'),
('decryption',  '解密',      '网络安全'),
('redundancy',  '冗余',      '网络基础'),
('load balancing','负载均衡', '网络基础'),
('DNS',         '域名系统',   '网络协议'),
('TCP',         '传输控制协议','网络协议'),
('UDP',         '用户数据报协议','网络协议'),
('IP',          '网际协议',   '网络协议'),
('HTTP',        '超文本传输协议','网络协议'),
('FTP',         '文件传输协议','网络协议'),
('SMTP',        '简单邮件传输协议','网络协议'),
('DHCP',        '动态主机配置协议','网络协议'),
('NAT',         '网络地址转换','网络协议'),
('VPN',         '虚拟专用网络','网络安全'),
('VLAN',        '虚拟局域网',  '网络基础'),
('MAC',         '媒体访问控制','网络基础'),
('ARP',         '地址解析协议','网络协议'),
('ICMP',        '互联网控制消息协议','网络协议'),
('OSPF',        '开放最短路径优先','路由协议'),
('BGP',         '边界网关协议','路由协议'),
('RIP',         '路由信息协议','路由协议'),
('EIGRP',       '增强型内部网关路由协议','路由协议'),
('STP',         '生成树协议',  '网络基础'),
('CIDR',        '无类域间路由','网络基础'),
('MTU',         '最大传输单元','网络基础'),
('QoS',         '服务质量',    '网络基础'),
('proxy',       '代理服务器',  '网络安全'),
('payload',     '有效载荷',    '网络基础'),
('handshake',   '握手',        '网络协议');
