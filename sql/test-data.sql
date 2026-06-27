-- =============================================
-- 通用单词测试系统 — 测试数据集（四六级词汇）
-- 数据库：word_test
-- 字符集：utf8mb4
-- 说明：所有 ID 显式指定，确保可重复执行。执行前需先运行 init.sql 建表。
-- =============================================

USE word_test;

-- =============================================
-- 清空已有数据（按外键依赖顺序，确保可重复执行）
-- =============================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE answers;
TRUNCATE TABLE questions;
TRUNCATE TABLE exams;
TRUNCATE TABLE words;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- 1. 用户数据（显式 ID，1 个管理员 + 8 个学生）
-- =============================================
INSERT INTO users (id, username, role) VALUES
(1,  'admin',       'admin'),
(2,  'zhangsan',    'student'),
(3,  'lisi',        'student'),
(4,  'wangwu',      'student'),
(5,  'zhaoliu',     'student'),
(6,  'sunqi',       'student'),
(7,  'zhouba',      'student'),
(8,  'wujiu',       'student'),
(9,  'zhengshi',    'student');

-- =============================================
-- 2. 单词数据（显式 ID，四六级词汇，5 个分类，共 60 个）
-- =============================================
INSERT INTO words (id, english, chinese, category) VALUES
-- 四级基础名词（CET-4 Basic Nouns）id: 1-12
(1,  'consequence',     '结果',         '四级基础名词'),
(2,  'environment',     '环境',         '四级基础名词'),
(3,  'opportunity',     '机会',         '四级基础名词'),
(4,  'phenomenon',      '现象',         '四级基础名词'),
(5,  'responsibility',  '责任',         '四级基础名词'),
(6,  'circumstance',    '情况',         '四级基础名词'),
(7,  'evidence',        '证据',         '四级基础名词'),
(8,  'competition',     '竞争',         '四级基础名词'),
(9,  'approach',        '方法',         '四级基础名词'),
(10, 'attitude',        '态度',         '四级基础名词'),
(11, 'resource',        '资源',         '四级基础名词'),
(12, 'individual',      '个人',         '四级基础名词'),

-- 四级高频动词（CET-4 High-frequency Verbs）id: 13-24
(13, 'acquire',         '获得',         '四级高频动词'),
(14, 'adapt',           '适应',         '四级高频动词'),
(15, 'appreciate',      '感激',         '四级高频动词'),
(16, 'concentrate',     '集中',         '四级高频动词'),
(17, 'contribute',      '贡献',         '四级高频动词'),
(18, 'distinguish',     '区分',         '四级高频动词'),
(19, 'emphasize',       '强调',         '四级高频动词'),
(20, 'establish',       '建立',         '四级高频动词'),
(21, 'identify',        '识别',         '四级高频动词'),
(22, 'maintain',        '维持',         '四级高频动词'),
(23, 'participate',     '参与',         '四级高频动词'),
(24, 'strengthen',      '加强',         '四级高频动词'),

-- 四级高频形容词（CET-4 High-frequency Adjectives）id: 25-36
(25, 'available',       '可用的',       '四级高频形容词'),
(26, 'beneficial',      '有益的',       '四级高频形容词'),
(27, 'considerable',    '相当大的',     '四级高频形容词'),
(28, 'efficient',       '高效的',       '四级高频形容词'),
(29, 'essential',       '必要的',       '四级高频形容词'),
(30, 'frequent',        '频繁的',       '四级高频形容词'),
(31, 'independent',     '独立的',       '四级高频形容词'),
(32, 'negative',        '消极的',       '四级高频形容词'),
(33, 'positive',        '积极的',       '四级高频形容词'),
(34, 'potential',       '潜在的',       '四级高频形容词'),
(35, 'significant',     '重要的',       '四级高频形容词'),
(36, 'sufficient',      '足够的',       '四级高频形容词'),

-- 六级进阶词汇（CET-6 Advanced Vocabulary）id: 37-48
(37, 'comprehensive',   '全面的',       '六级进阶词汇'),
(38, 'controversial',   '有争议的',     '六级进阶词汇'),
(39, 'deteriorate',     '恶化',         '六级进阶词汇'),
(40, 'elaborate',       '精心制作的',   '六级进阶词汇'),
(41, 'inevitable',      '不可避免的',   '六级进阶词汇'),
(42, 'negotiate',       '谈判',         '六级进阶词汇'),
(43, 'originate',       '起源于',       '六级进阶词汇'),
(44, 'perspective',     '观点',         '六级进阶词汇'),
(45, 'prominent',       '突出的',       '六级进阶词汇'),
(46, 'sophisticated',   '复杂的',       '六级进阶词汇'),
(47, 'sustainable',     '可持续的',     '六级进阶词汇'),
(48, 'tremendous',      '巨大的',       '六级进阶词汇'),

-- 六级学术词汇（CET-6 Academic Vocabulary）id: 49-60
(49, 'accumulate',      '积累',         '六级学术词汇'),
(50, 'ambiguous',       '模棱两可的',   '六级学术词汇'),
(51, 'cognitive',       '认知的',       '六级学术词汇'),
(52, 'demonstrate',     '证明',         '六级学术词汇'),
(53, 'implement',       '实施',         '六级学术词汇'),
(54, 'incorporate',     '纳入',         '六级学术词汇'),
(55, 'innovative',      '创新的',       '六级学术词汇'),
(56, 'presume',         '假定',         '六级学术词汇'),
(57, 'rational',        '理性的',       '六级学术词汇'),
(58, 'subordinate',     '从属的',       '六级学术词汇'),
(59, 'utilize',         '利用',         '六级学术词汇'),
(60, 'verify',          '核实',         '六级学术词汇');

-- =============================================
-- 3. 考试数据（显式 ID，5 场考试）
-- category 为 NULL 则不限制分类；指定分类则试卷生成时仅从该分类抽词
-- =============================================
INSERT INTO exams (id, title, start_time, end_time, question_count, category) VALUES
(1, '四级基础词汇测试',    '2026-05-10 09:00:00', '2026-05-10 11:00:00', 20, NULL),
(2, '四级综合测试',        '2026-05-20 14:00:00', '2026-05-20 16:00:00', 15, NULL),
(3, '六级词汇测试',        '2026-06-23 08:00:00', '2026-06-24 08:00:00', 30, NULL),
(4, '四六级综合补考',      '2026-07-01 10:00:00', '2026-07-01 12:00:00', 20, NULL),
(5, '六级学术词汇专项',    '2026-07-15 09:00:00', '2026-07-15 11:00:00', 10, '六级学术词汇');

-- =============================================
-- 4. 题目数据（显式 ID，共 180 题）
-- =============================================

-- ---------- 四级基础词汇测试（exam_id=1）----------
-- zhangsan（user_id=2）— 20 题（id: 1-20）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(1,  1, 2, 1,  'EN_TO_CN', '请写出 "consequence" 的中文释义',       '结果',         NULL, 1),
(2,  1, 2, 2,  'CN_TO_EN', '请写出 "环境" 的英文单词',              'environment',  NULL, 2),
(3,  1, 2, 3,  'CHOICE',   '"opportunity" 的中文含义是？',          '机会',         '["机会","责任","现象","结果"]', 3),
(4,  1, 2, 5,  'EN_TO_CN', '请写出 "responsibility" 的中文释义',    '责任',         NULL, 4),
(5,  1, 2, 7,  'CN_TO_EN', '请写出 "证据" 的英文单词',              'evidence',     NULL, 5),
(6,  1, 2, 8,  'CHOICE',   '"competition" 的中文含义是？',          '竞争',         '["合作","竞争","完成","比较"]', 6),
(7,  1, 2, 10, 'EN_TO_CN', '请写出 "attitude" 的中文释义',          '态度',         NULL, 7),
(8,  1, 2, 12, 'CN_TO_EN', '请写出 "个人" 的英文单词',              'individual',   NULL, 8),
(9,  1, 2, 4,  'CHOICE',   '"phenomenon" 的中文含义是？',           '现象',         '["问题","现象","原因","结果"]', 9),
(10, 1, 2, 13, 'EN_TO_CN', '请写出 "acquire" 的中文释义',           '获得',         NULL, 10),
(11, 1, 2, 14, 'CN_TO_EN', '请写出 "适应" 的英文单词',              'adapt',        NULL, 11),
(12, 1, 2, 16, 'CHOICE',   '"concentrate" 的中文含义是？',          '集中',         '["分散","集中","关注","忽略"]', 12),
(13, 1, 2, 17, 'EN_TO_CN', '请写出 "contribute" 的中文释义',        '贡献',         NULL, 13),
(14, 1, 2, 18, 'CN_TO_EN', '请写出 "区分" 的英文单词',              'distinguish',  NULL, 14),
(15, 1, 2, 20, 'CHOICE',   '"establish" 的中文含义是？',            '建立',         '["破坏","建立","评估","调整"]', 15),
(16, 1, 2, 21, 'EN_TO_CN', '请写出 "identify" 的中文释义',          '识别',         NULL, 16),
(17, 1, 2, 24, 'CN_TO_EN', '请写出 "加强" 的英文单词',              'strengthen',   NULL, 17),
(18, 1, 2, 9,  'EN_TO_CN', '请写出 "approach" 的中文释义',          '方法',         NULL, 18),
(19, 1, 2, 6,  'CHOICE',   '"circumstance" 的中文含义是？',         '情况',         '["环境","情况","证据","竞争"]', 19),
(20, 1, 2, 11, 'CN_TO_EN', '请写出 "资源" 的英文单词',              'resource',     NULL, 20);

-- lisi（user_id=3）— 20 题（id: 21-40）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(21, 1, 3, 3,  'EN_TO_CN', '请写出 "opportunity" 的中文释义',       '机会',             NULL, 1),
(22, 1, 3, 5,  'CN_TO_EN', '请写出 "责任" 的英文单词',              'responsibility',   NULL, 2),
(23, 1, 3, 1,  'CHOICE',   '"consequence" 的中文含义是？',          '结果',             '["原因","过程","结果","影响"]', 3),
(24, 1, 3, 8,  'EN_TO_CN', '请写出 "competition" 的中文释义',       '竞争',             NULL, 4),
(25, 1, 3, 10, 'CN_TO_EN', '请写出 "态度" 的英文单词',              'attitude',         NULL, 5),
(26, 1, 3, 13, 'CHOICE',   '"acquire" 的中文含义是？',              '获得',             '["失去","获得","给予","交换"]', 6),
(27, 1, 3, 15, 'EN_TO_CN', '请写出 "appreciate" 的中文释义',        '感激',             NULL, 7),
(28, 1, 3, 19, 'CN_TO_EN', '请写出 "强调" 的英文单词',              'emphasize',        NULL, 8),
(29, 1, 3, 22, 'CHOICE',   '"maintain" 的中文含义是？',             '维持',             '["破坏","维持","改变","放弃"]', 9),
(30, 1, 3, 4,  'EN_TO_CN', '请写出 "phenomenon" 的中文释义',        '现象',             NULL, 10),
(31, 1, 3, 7,  'CN_TO_EN', '请写出 "证据" 的英文单词',              'evidence',         NULL, 11),
(32, 1, 3, 23, 'CHOICE',   '"participate" 的中文含义是？',          '参与',             '["退出","参与","观察","评价"]', 12),
(33, 1, 3, 12, 'EN_TO_CN', '请写出 "individual" 的中文释义',        '个人',             NULL, 13),
(34, 1, 3, 2,  'CN_TO_EN', '请写出 "环境" 的英文单词',              'environment',      NULL, 14),
(35, 1, 3, 17, 'CHOICE',   '"contribute" 的中文含义是？',           '贡献',             '["索取","贡献","保留","分配"]', 15),
(36, 1, 3, 21, 'EN_TO_CN', '请写出 "identify" 的中文释义',          '识别',             NULL, 16),
(37, 1, 3, 14, 'CN_TO_EN', '请写出 "适应" 的英文单词',              'adapt',            NULL, 17),
(38, 1, 3, 9,  'EN_TO_CN', '请写出 "approach" 的中文释义',          '方法',             NULL, 18),
(39, 1, 3, 24, 'CN_TO_EN', '请写出 "加强" 的英文单词',              'strengthen',       NULL, 19),
(40, 1, 3, 6,  'CHOICE',   '"circumstance" 的中文含义是？',         '情况',             '["证据","情况","环境","现象"]', 20);

-- ---------- 四级综合测试（exam_id=2）----------
-- zhangsan（user_id=2）— 15 题（id: 41-55）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(41, 2, 2, 25, 'EN_TO_CN', '请写出 "available" 的中文释义',         '可用的',       NULL, 1),
(42, 2, 2, 26, 'CN_TO_EN', '请写出 "有益的" 的英文单词',             'beneficial',   NULL, 2),
(43, 2, 2, 27, 'CHOICE',   '"considerable" 的中文含义是？',         '相当大的',     '["相当大的","可用的","重要的","足够的"]', 3),
(44, 2, 2, 28, 'EN_TO_CN', '请写出 "efficient" 的中文释义',         '高效的',       NULL, 4),
(45, 2, 2, 29, 'CN_TO_EN', '请写出 "必要的" 的英文单词',             'essential',    NULL, 5),
(46, 2, 2, 31, 'CHOICE',   '"independent" 的中文含义是？',          '独立的',       '["独立的","依赖的","积极的","消极的"]', 6),
(47, 2, 2, 33, 'EN_TO_CN', '请写出 "positive" 的中文释义',          '积极的',       NULL, 7),
(48, 2, 2, 35, 'CN_TO_EN', '请写出 "重要的" 的英文单词',             'significant',  NULL, 8),
(49, 2, 2, 16, 'CHOICE',   '"concentrate" 的中文含义是？',          '集中',         '["忽视","集中","分散","转移"]', 9),
(50, 2, 2, 18, 'EN_TO_CN', '请写出 "distinguish" 的中文释义',       '区分',         NULL, 10),
(51, 2, 2, 22, 'CN_TO_EN', '请写出 "维持" 的英文单词',              'maintain',     NULL, 11),
(52, 2, 2, 30, 'CHOICE',   '"frequent" 的中文含义是？',             '频繁的',       '["罕见的","频繁的","固定的","临时的"]', 12),
(53, 2, 2, 34, 'EN_TO_CN', '请写出 "potential" 的中文释义',         '潜在的',       NULL, 13),
(54, 2, 2, 36, 'CN_TO_EN', '请写出 "足够的" 的英文单词',             'sufficient',   NULL, 14),
(55, 2, 2, 32, 'CHOICE',   '"negative" 的中文含义是？',             '消极的',       '["积极的","消极的","中立的","绝对的"]', 15);

-- wangwu（user_id=4）— 15 题（id: 56-70）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(56, 2, 4, 26, 'EN_TO_CN', '请写出 "beneficial" 的中文释义',        '有益的',       NULL, 1),
(57, 2, 4, 28, 'CN_TO_EN', '请写出 "高效的" 的英文单词',             'efficient',    NULL, 2),
(58, 2, 4, 25, 'CHOICE',   '"available" 的中文含义是？',            '可用的',       '["可用的","有益的","必要的","重要的"]', 3),
(59, 2, 4, 30, 'EN_TO_CN', '请写出 "frequent" 的中文释义',          '频繁的',       NULL, 4),
(60, 2, 4, 32, 'CN_TO_EN', '请写出 "消极的" 的英文单词',             'negative',     NULL, 5),
(61, 2, 4, 13, 'CHOICE',   '"acquire" 的中文含义是？',              '获得',         '["获得","失去","交换","提供"]', 6),
(62, 2, 4, 35, 'EN_TO_CN', '请写出 "significant" 的中文释义',       '重要的',       NULL, 7),
(63, 2, 4, 17, 'CN_TO_EN', '请写出 "贡献" 的英文单词',              'contribute',   NULL, 8),
(64, 2, 4, 33, 'CHOICE',   '"positive" 的中文含义是？',             '积极的',       '["积极的","消极的","被动的","主动的"]', 9),
(65, 2, 4, 29, 'EN_TO_CN', '请写出 "essential" 的中文释义',         '必要的',       NULL, 10),
(66, 2, 4, 20, 'CN_TO_EN', '请写出 "建立" 的英文单词',              'establish',    NULL, 11),
(67, 2, 4, 27, 'CHOICE',   '"considerable" 的中文含义是？',         '相当大的',     '["可观的","相当大的","足够的","重要的"]', 12),
(68, 2, 4, 34, 'EN_TO_CN', '请写出 "potential" 的中文释义',         '潜在的',       NULL, 13),
(69, 2, 4, 14, 'CN_TO_EN', '请写出 "适应" 的英文单词',              'adapt',        NULL, 14),
(70, 2, 4, 31, 'CHOICE',   '"independent" 的中文含义是？',          '独立的',       '["独立的","依赖的","自由的","平等的"]', 15);

-- ---------- 六级词汇测试（exam_id=3）----------
-- zhangsan（user_id=2）— 30 题（id: 71-100）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(71,  3, 2, 37, 'EN_TO_CN', '请写出 "comprehensive" 的中文释义',     '全面的',       NULL, 1),
(72,  3, 2, 49, 'CN_TO_EN', '请写出 "积累" 的英文单词',              'accumulate',   NULL, 2),
(73,  3, 2, 39, 'CHOICE',   '"deteriorate" 的中文含义是？',          '恶化',         '["改善","恶化","维持","发展"]', 3),
(74,  3, 2, 41, 'EN_TO_CN', '请写出 "inevitable" 的中文释义',        '不可避免的',   NULL, 4),
(75,  3, 2, 50, 'CN_TO_EN', '请写出 "模棱两可的" 的英文单词',        'ambiguous',    NULL, 5),
(76,  3, 2, 43, 'CHOICE',   '"originate" 的中文含义是？',            '起源于',       '["终止","起源于","发展","变化"]', 6),
(77,  3, 2, 44, 'EN_TO_CN', '请写出 "perspective" 的中文释义',       '观点',         NULL, 7),
(78,  3, 2, 51, 'CN_TO_EN', '请写出 "认知的" 的英文单词',            'cognitive',    NULL, 8),
(79,  3, 2, 46, 'CHOICE',   '"sophisticated" 的中文含义是？',        '复杂的',       '["简单的","复杂的","传统的","现代的"]', 9),
(80,  3, 2, 48, 'EN_TO_CN', '请写出 "tremendous" 的中文释义',        '巨大的',       NULL, 10),
(81,  3, 2, 53, 'CN_TO_EN', '请写出 "实施" 的英文单词',              'implement',    NULL, 11),
(82,  3, 2, 55, 'CHOICE',   '"innovative" 的中文含义是？',           '创新的',       '["创新的","传统的","保守的","复杂的"]', 12),
(83,  3, 2, 38, 'EN_TO_CN', '请写出 "controversial" 的中文释义',     '有争议的',     NULL, 13),
(84,  3, 2, 52, 'CN_TO_EN', '请写出 "证明" 的英文单词',              'demonstrate',  NULL, 14),
(85,  3, 2, 40, 'CHOICE',   '"elaborate" 的中文含义是？',            '精心制作的',   '["简单的","精心制作的","粗糙的","普通的"]', 15),
(86,  3, 2, 42, 'EN_TO_CN', '请写出 "negotiate" 的中文释义',         '谈判',         NULL, 16),
(87,  3, 2, 54, 'CN_TO_EN', '请写出 "纳入" 的英文单词',              'incorporate',  NULL, 17),
(88,  3, 2, 45, 'CHOICE',   '"prominent" 的中文含义是？',            '突出的',       '["隐藏的","突出的","普通的","微小的"]', 18),
(89,  3, 2, 47, 'EN_TO_CN', '请写出 "sustainable" 的中文释义',       '可持续的',     NULL, 19),
(90,  3, 2, 56, 'CN_TO_EN', '请写出 "假定" 的英文单词',              'presume',      NULL, 20),
(91,  3, 2, 57, 'CHOICE',   '"rational" 的中文含义是？',             '理性的',       '["理性的","感性的","主观的","客观的"]', 21),
(92,  3, 2, 58, 'EN_TO_CN', '请写出 "subordinate" 的中文释义',       '从属的',       NULL, 22),
(93,  3, 2, 59, 'CN_TO_EN', '请写出 "利用" 的英文单词',              'utilize',      NULL, 23),
(94,  3, 2, 60, 'CHOICE',   '"verify" 的中文含义是？',               '核实',         '["假设","核实","忽略","否认"]', 24),
(95,  3, 2, 25, 'EN_TO_CN', '请写出 "available" 的中文释义',         '可用的',       NULL, 25),
(96,  3, 2, 1,  'CN_TO_EN', '请写出 "结果" 的英文单词',              'consequence',  NULL, 26),
(97,  3, 2, 23, 'CHOICE',   '"participate" 的中文含义是？',          '参与',         '["参与","退出","观望","评价"]', 27),
(98,  3, 2, 15, 'EN_TO_CN', '请写出 "appreciate" 的中文释义',        '感激',         NULL, 28),
(99,  3, 2, 35, 'CN_TO_EN', '请写出 "重要的" 的英文单词',            'significant',  NULL, 29),
(100, 3, 2, 19, 'CHOICE',   '"emphasize" 的中文含义是？',            '强调',         '["忽略","强调","轻视","重视"]', 30);

-- lisi（user_id=3）— 30 题（id: 101-130）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(101, 3, 3, 38, 'EN_TO_CN', '请写出 "controversial" 的中文释义',     '有争议的',     NULL, 1),
(102, 3, 3, 52, 'CN_TO_EN', '请写出 "证明" 的英文单词',              'demonstrate',  NULL, 2),
(103, 3, 3, 37, 'CHOICE',   '"comprehensive" 的中文含义是？',        '全面的',       '["片面的","全面的","部分的","整体的"]', 3),
(104, 3, 3, 40, 'EN_TO_CN', '请写出 "elaborate" 的中文释义',         '精心制作的',   NULL, 4),
(105, 3, 3, 53, 'CN_TO_EN', '请写出 "实施" 的英文单词',              'implement',    NULL, 5),
(106, 3, 3, 41, 'CHOICE',   '"inevitable" 的中文含义是？',           '不可避免的',   '["可避免的","不可避免的","可预见的","意外的"]', 6),
(107, 3, 3, 44, 'EN_TO_CN', '请写出 "perspective" 的中文释义',       '观点',         NULL, 7),
(108, 3, 3, 55, 'CN_TO_EN', '请写出 "创新的" 的英文单词',            'innovative',   NULL, 8),
(109, 3, 3, 46, 'CHOICE',   '"sophisticated" 的中文含义是？',        '复杂的',       '["复杂的","简单的","粗糙的","原始的"]', 9),
(110, 3, 3, 48, 'EN_TO_CN', '请写出 "tremendous" 的中文释义',        '巨大的',       NULL, 10),
(111, 3, 3, 49, 'CN_TO_EN', '请写出 "积累" 的英文单词',              'accumulate',   NULL, 11),
(112, 3, 3, 57, 'CHOICE',   '"rational" 的中文含义是？',             '理性的',       '["感性的","理性的","冲动的","保守的"]', 12),
(113, 3, 3, 42, 'EN_TO_CN', '请写出 "negotiate" 的中文释义',         '谈判',         NULL, 13),
(114, 3, 3, 54, 'CN_TO_EN', '请写出 "纳入" 的英文单词',              'incorporate',  NULL, 14),
(115, 3, 3, 43, 'CHOICE',   '"originate" 的中文含义是？',            '起源于',       '["终止于","起源于","发展于","结束于"]', 15),
(116, 3, 3, 45, 'EN_TO_CN', '请写出 "prominent" 的中文释义',         '突出的',       NULL, 16),
(117, 3, 3, 56, 'CN_TO_EN', '请写出 "假定" 的英文单词',              'presume',      NULL, 17),
(118, 3, 3, 39, 'CHOICE',   '"deteriorate" 的中文含义是？',          '恶化',         '["恶化","改善","稳定","发展"]', 18),
(119, 3, 3, 47, 'EN_TO_CN', '请写出 "sustainable" 的中文释义',       '可持续的',     NULL, 19),
(120, 3, 3, 58, 'CN_TO_EN', '请写出 "从属的" 的英文单词',            'subordinate',  NULL, 20),
(121, 3, 3, 60, 'CHOICE',   '"verify" 的中文含义是？',               '核实',         '["核实","忽略","假设","推测"]', 21),
(122, 3, 3, 27, 'EN_TO_CN', '请写出 "considerable" 的中文释义',      '相当大的',     NULL, 22),
(123, 3, 3, 13, 'CN_TO_EN', '请写出 "获得" 的英文单词',              'acquire',      NULL, 23),
(124, 3, 3, 32, 'CHOICE',   '"negative" 的中文含义是？',             '消极的',       '["积极的","消极的","绝对的","相对的"]', 24),
(125, 3, 3, 5,  'EN_TO_CN', '请写出 "responsibility" 的中文释义',    '责任',         NULL, 25),
(126, 3, 3, 24, 'CN_TO_EN', '请写出 "加强" 的英文单词',              'strengthen',   NULL, 26),
(127, 3, 3, 30, 'CHOICE',   '"frequent" 的中文含义是？',             '频繁的',       '["频繁的","罕见的","偶尔的","固定的"]', 27),
(128, 3, 3, 12, 'EN_TO_CN', '请写出 "individual" 的中文释义',        '个人',         NULL, 28),
(129, 3, 3, 20, 'CN_TO_EN', '请写出 "建立" 的英文单词',              'establish',    NULL, 29),
(130, 3, 3, 34, 'CHOICE',   '"potential" 的中文含义是？',            '潜在的',       '["明显的","潜在的","积极的","消极的"]', 30);

-- ---------- 四六级综合补考（exam_id=4）----------
-- zhouba（user_id=7）— 20 题（id: 131-150）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(131, 4, 7, 1,  'EN_TO_CN', '请写出 "consequence" 的中文释义',       '结果',         NULL, 1),
(132, 4, 7, 13, 'CN_TO_EN', '请写出 "获得" 的英文单词',              'acquire',      NULL, 2),
(133, 4, 7, 28, 'CHOICE',   '"efficient" 的中文含义是？',            '高效的',       '["高效的","低效的","有效的","无效的"]', 3),
(134, 4, 7, 37, 'EN_TO_CN', '请写出 "comprehensive" 的中文释义',     '全面的',       NULL, 4),
(135, 4, 7, 49, 'CN_TO_EN', '请写出 "积累" 的英文单词',              'accumulate',   NULL, 5),
(136, 4, 7, 7,  'CHOICE',   '"evidence" 的中文含义是？',             '证据',         '["证据","环境","现象","结果"]', 6),
(137, 4, 7, 15, 'EN_TO_CN', '请写出 "appreciate" 的中文释义',        '感激',         NULL, 7),
(138, 4, 7, 29, 'CN_TO_EN', '请写出 "必要的" 的英文单词',            'essential',    NULL, 8),
(139, 4, 7, 44, 'CHOICE',   '"perspective" 的中文含义是？',          '观点',         '["视角","观点","态度","方法"]', 9),
(140, 4, 7, 50, 'EN_TO_CN', '请写出 "ambiguous" 的中文释义',         '模棱两可的',   NULL, 10),
(141, 4, 7, 21, 'CN_TO_EN', '请写出 "识别" 的英文单词',              'identify',     NULL, 11),
(142, 4, 7, 41, 'CHOICE',   '"inevitable" 的中文含义是？',           '不可避免的',   '["可避免的","不可避免的","可预测的","意外的"]', 12),
(143, 4, 7, 9,  'EN_TO_CN', '请写出 "approach" 的中文释义',          '方法',         NULL, 13),
(144, 4, 7, 52, 'CN_TO_EN', '请写出 "证明" 的英文单词',              'demonstrate',  NULL, 14),
(145, 4, 7, 25, 'CHOICE',   '"available" 的中文含义是？',            '可用的',       '["可用的","有益的","必要的","重要的"]', 15),
(146, 4, 7, 42, 'EN_TO_CN', '请写出 "negotiate" 的中文释义',         '谈判',         NULL, 16),
(147, 4, 7, 18, 'CN_TO_EN', '请写出 "区分" 的英文单词',              'distinguish',  NULL, 17),
(148, 4, 7, 55, 'CHOICE',   '"innovative" 的中文含义是？',           '创新的',       '["创新的","传统的","保守的","复杂的"]', 18),
(149, 4, 7, 35, 'EN_TO_CN', '请写出 "significant" 的中文释义',       '重要的',       NULL, 19),
(150, 4, 7, 58, 'CN_TO_EN', '请写出 "从属的" 的英文单词',            'subordinate',  NULL, 20);

-- wujiu（user_id=8）— 20 题（id: 151-170）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(151, 4, 8, 2,  'EN_TO_CN', '请写出 "environment" 的中文释义',       '环境',         NULL, 1),
(152, 4, 8, 17, 'CN_TO_EN', '请写出 "贡献" 的英文单词',              'contribute',   NULL, 2),
(153, 4, 8, 38, 'CHOICE',   '"controversial" 的中文含义是？',        '有争议的',     '["一致的","有争议的","明确的","模糊的"]', 3),
(154, 4, 8, 5,  'EN_TO_CN', '请写出 "responsibility" 的中文释义',    '责任',         NULL, 4),
(155, 4, 8, 53, 'CN_TO_EN', '请写出 "实施" 的英文单词',              'implement',    NULL, 5),
(156, 4, 8, 10, 'CHOICE',   '"attitude" 的中文含义是？',             '态度',         '["方法","态度","行为","习惯"]', 6),
(157, 4, 8, 19, 'EN_TO_CN', '请写出 "emphasize" 的中文释义',         '强调',         NULL, 7),
(158, 4, 8, 31, 'CN_TO_EN', '请写出 "独立的" 的英文单词',            'independent',  NULL, 8),
(159, 4, 8, 47, 'CHOICE',   '"sustainable" 的中文含义是？',          '可持续的',     '["暂时的","可持续的","不可持续的","长久的"]', 9),
(160, 4, 8, 51, 'EN_TO_CN', '请写出 "cognitive" 的中文释义',         '认知的',       NULL, 10),
(161, 4, 8, 14, 'CN_TO_EN', '请写出 "适应" 的英文单词',              'adapt',        NULL, 11),
(162, 4, 8, 40, 'CHOICE',   '"elaborate" 的中文含义是？',            '精心制作的',   '["简单的","精心制作的","粗糙的","普通的"]', 12),
(163, 4, 8, 8,  'EN_TO_CN', '请写出 "competition" 的中文释义',       '竞争',         NULL, 13),
(164, 4, 8, 56, 'CN_TO_EN', '请写出 "假定" 的英文单词',              'presume',      NULL, 14),
(165, 4, 8, 33, 'CHOICE',   '"positive" 的中文含义是？',             '积极的',       '["消极的","积极的","中立的","极端的"]', 15),
(166, 4, 8, 43, 'EN_TO_CN', '请写出 "originate" 的中文释义',         '起源于',       NULL, 16),
(167, 4, 8, 22, 'CN_TO_EN', '请写出 "维持" 的英文单词',              'maintain',     NULL, 17),
(168, 4, 8, 59, 'CHOICE',   '"utilize" 的中文含义是？',              '利用',         '["浪费","利用","忽视","放弃"]', 18),
(169, 4, 8, 27, 'EN_TO_CN', '请写出 "considerable" 的中文释义',      '相当大的',     NULL, 19),
(170, 4, 8, 48, 'CN_TO_EN', '请写出 "巨大的" 的英文单词',            'tremendous',   NULL, 20);

-- ---------- 六级学术词汇专项（exam_id=5）----------
-- zhaoliu（user_id=5）— 10 题（id: 171-180）
INSERT INTO questions (id, exam_id, user_id, word_id, question_type, question_text, correct_answer, options, sort_order) VALUES
(171, 5, 5, 49, 'EN_TO_CN', '请写出 "accumulate" 的中文释义',          '积累',         NULL, 1),
(172, 5, 5, 50, 'CN_TO_EN', '请写出 "模棱两可的" 的英文单词',          'ambiguous',    NULL, 2),
(173, 5, 5, 51, 'CHOICE',   '"cognitive" 的中文含义是？',              '认知的',       '["认知的","情感的","行为的","生理的"]', 3),
(174, 5, 5, 52, 'EN_TO_CN', '请写出 "demonstrate" 的中文释义',         '证明',         NULL, 4),
(175, 5, 5, 53, 'CN_TO_EN', '请写出 "实施" 的英文单词',                'implement',    NULL, 5),
(176, 5, 5, 54, 'CHOICE',   '"incorporate" 的中文含义是？',            '纳入',         '["排除","纳入","合并","分离"]', 6),
(177, 5, 5, 55, 'EN_TO_CN', '请写出 "innovative" 的中文释义',          '创新的',       NULL, 7),
(178, 5, 5, 56, 'CN_TO_EN', '请写出 "假定" 的英文单词',                'presume',      NULL, 8),
(179, 5, 5, 57, 'CHOICE',   '"rational" 的中文含义是？',               '理性的',       '["感性的","理性的","主观的","直觉的"]', 9),
(180, 5, 5, 59, 'EN_TO_CN', '请写出 "utilize" 的中文释义',             '利用',         NULL, 10);

-- =============================================
-- 5. 答案数据（显式 ID，模拟学生的答题记录，共 180 条）
-- =============================================

-- ---------- 四级基础词汇测试：zhangsan 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(1,  1,  2, '结果',         1),
(2,  2,  2, 'environment',  1),
(3,  3,  2, '机会',         1),
(4,  4,  2, '责任',         1),
(5,  5,  2, 'evidence',     1),
(6,  6,  2, '竞争',         1),
(7,  7,  2, '态度',         1),
(8,  8,  2, 'individual',   1),
(9,  9,  2, '现象',         1),
(10, 10, 2, '获得',         1),
(11, 11, 2, 'adapt',        1),
(12, 12, 2, '集中',         1),
(13, 13, 2, '贡献',         1),
(14, 14, 2, 'distinguish',  1),
(15, 15, 2, '建立',         0),   -- 答错
(16, 16, 2, '识别',         1),
(17, 17, 2, 'strengthen',   1),
(18, 18, 2, '方法',         1),
(19, 19, 2, '情况',         1),
(20, 20, 2, 'resource',     1);

-- ---------- 四级基础词汇测试：lisi 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(21, 21, 3, '机会',             1),
(22, 22, 3, 'responsibility',   1),
(23, 23, 3, '结果',             1),
(24, 24, 3, '竞争',             1),
(25, 25, 3, 'attitude',         1),
(26, 26, 3, '获得',             1),
(27, 27, 3, '感激',             1),
(28, 28, 3, 'emphasize',        1),
(29, 29, 3, '维持',             1),
(30, 30, 3, '现象',             1),
(31, 31, 3, 'evidence',         1),
(32, 32, 3, '参与',             0),   -- 答错
(33, 33, 3, '个人',             1),
(34, 34, 3, 'environment',      1),
(35, 35, 3, '贡献',             1),
(36, 36, 3, '识别',             1),
(37, 37, 3, 'adapt',            1),
(38, 38, 3, '方法',             1),
(39, 39, 3, 'strengthen',       1),
(40, 40, 3, '情况',             1);

-- ---------- 四级综合测试：zhangsan 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(41, 41, 2, '可用的',       1),
(42, 42, 2, 'beneficial',   1),
(43, 43, 2, '相当大的',     1),
(44, 44, 2, '高效的',       1),
(45, 45, 2, 'essential',    1),
(46, 46, 2, '独立的',       1),
(47, 47, 2, '积极的',       1),
(48, 48, 2, 'significant',  1),
(49, 49, 2, '集中',         1),
(50, 50, 2, '区分',         1),
(51, 51, 2, 'maintain',     1),
(52, 52, 2, '频繁的',       1),
(53, 53, 2, '潜在的',       1),
(54, 54, 2, 'sufficient',   1),
(55, 55, 2, '消极的',       1);

-- ---------- 四级综合测试：wangwu 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(56, 56, 4, '有益的',       1),
(57, 57, 4, 'efficient',    1),
(58, 58, 4, '可用的',       1),
(59, 59, 4, '频繁的',       1),
(60, 60, 4, 'negative',     1),
(61, 61, 4, '获得',         1),
(62, 62, 4, '重要的',       1),
(63, 63, 4, 'contribute',   1),
(64, 64, 4, '积极的',       1),
(65, 65, 4, '必要的',       1),
(66, 66, 4, 'establish',    1),
(67, 67, 4, '相当大的',     1),
(68, 68, 4, '潜在的',       0),   -- 答错
(69, 69, 4, 'adapt',        1),
(70, 70, 4, '独立的',       1);

-- ---------- 六级词汇测试：zhangsan 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(71,  71,  2, '全面的',         1),
(72,  72,  2, 'accumulate',     1),
(73,  73,  2, '恶化',           1),
(74,  74,  2, '不可避免的',     1),
(75,  75,  2, 'ambiguous',      1),
(76,  76,  2, '起源于',         1),
(77,  77,  2, '观点',           1),
(78,  78,  2, 'cognitive',      1),
(79,  79,  2, '复杂的',         1),
(80,  80,  2, '巨大的',         1),
(81,  81,  2, 'implement',      1),
(82,  82,  2, '创新的',         1),
(83,  83,  2, '有争议的',       1),
(84,  84,  2, 'demonstrate',    1),
(85,  85,  2, '精心制作的',     0),   -- 答错
(86,  86,  2, '谈判',           1),
(87,  87,  2, 'incorporate',    1),
(88,  88,  2, '突出的',         1),
(89,  89,  2, '可持续的',       1),
(90,  90,  2, 'presume',        1),
(91,  91,  2, '理性的',         1),
(92,  92,  2, '从属的',         1),
(93,  93,  2, 'utilize',        1),
(94,  94,  2, '核实',           1),
(95,  95,  2, '可用的',         1),
(96,  96,  2, 'consequence',    1),
(97,  97,  2, '参与',           1),
(98,  98,  2, '感激',           1),
(99,  99,  2, 'significant',    1),
(100, 100, 2, '强调',           1);

-- ---------- 六级词汇测试：lisi 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(101, 101, 3, '有争议的',       1),
(102, 102, 3, 'demonstrate',    1),
(103, 103, 3, '全面的',         1),
(104, 104, 3, '精心制作的',     1),
(105, 105, 3, 'implement',      1),
(106, 106, 3, '不可避免的',     1),
(107, 107, 3, '观点',           1),
(108, 108, 3, 'innovative',     1),
(109, 109, 3, '复杂的',         1),
(110, 110, 3, '巨大的',         1),
(111, 111, 3, 'accumulate',     1),
(112, 112, 3, '理性的',         1),
(113, 113, 3, '谈判',           1),
(114, 114, 3, 'incorporate',    1),
(115, 115, 3, '起源于',         0),   -- 答错
(116, 116, 3, '突出的',         1),
(117, 117, 3, 'presume',        1),
(118, 118, 3, '恶化',           1),
(119, 119, 3, '可持续的',       1),
(120, 120, 3, 'subordinate',    1),
(121, 121, 3, '核实',           1),
(122, 122, 3, '相当大的',       1),
(123, 123, 3, 'acquire',        1),
(124, 124, 3, '消极的',         1),
(125, 125, 3, '责任',           1),
(126, 126, 3, 'strengthen',     1),
(127, 127, 3, '频繁的',         0),   -- 答错
(128, 128, 3, '个人',           1),
(129, 129, 3, 'establish',      1),
(130, 130, 3, '潜在的',         1);

-- ---------- 四六级综合补考：zhouba 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(131, 131, 7, '结果',         1),
(132, 132, 7, 'acquire',      1),
(133, 133, 7, '高效的',       1),
(134, 134, 7, '全面的',       1),
(135, 135, 7, 'accumulate',   1),
(136, 136, 7, '证据',         1),
(137, 137, 7, '感激',         1),
(138, 138, 7, 'essential',    1),
(139, 139, 7, '观点',         1),
(140, 140, 7, '模棱两可的',   1),
(141, 141, 7, 'identify',     1),
(142, 142, 7, '不可避免的',   0),   -- 答错
(143, 143, 7, '方法',         1),
(144, 144, 7, 'demonstrate',  1),
(145, 145, 7, '可用的',       1),
(146, 146, 7, '谈判',         1),
(147, 147, 7, 'distinguish',  1),
(148, 148, 7, '创新的',       1),
(149, 149, 7, '重要的',       1),
(150, 150, 7, 'subordinate',  1);

-- ---------- 四六级综合补考：wujiu 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(151, 151, 8, '环境',         1),
(152, 152, 8, 'contribute',   1),
(153, 153, 8, '有争议的',     1),
(154, 154, 8, '责任',         1),
(155, 155, 8, 'implement',    1),
(156, 156, 8, '态度',         1),
(157, 157, 8, '强调',         1),
(158, 158, 8, 'independent',  1),
(159, 159, 8, '可持续的',     1),
(160, 160, 8, '认知的',       1),
(161, 161, 8, 'adapt',        1),
(162, 162, 8, '精心制作的',   1),
(163, 163, 8, '竞争',         1),
(164, 164, 8, 'presume',      1),
(165, 165, 8, '积极的',       1),
(166, 166, 8, '起源于',       0),   -- 答错
(167, 167, 8, 'maintain',     1),
(168, 168, 8, '利用',         1),
(169, 169, 8, '相当大的',     1),
(170, 170, 8, 'tremendous',   1);

-- ---------- 六级学术词汇专项：zhaoliu 答题 ----------
INSERT INTO answers (id, question_id, user_id, user_answer, is_correct) VALUES
(171, 171, 5, '积累',         1),
(172, 172, 5, 'ambiguous',    1),
(173, 173, 5, '认知的',       1),
(174, 174, 5, '证明',         1),
(175, 175, 5, 'implement',    1),
(176, 176, 5, '合并',         0),   -- 答错
(177, 177, 5, '创新的',       1),
(178, 178, 5, 'presume',      1),
(179, 179, 5, '理性的',       1),
(180, 180, 5, '利用',         1);

-- =============================================
-- 数据统计摘要
-- =============================================
-- 用户：9 人（id: 1 admin + 2~9 student）
-- 单词：60 个（id: 1-12 四级基础名词, 13-24 四级高频动词,
--                 25-36 四级高频形容词, 37-48 六级进阶词汇,
--                 49-60 六级学术词汇）
-- 考试：5 场（id: 1-5）
-- 题目：180 题（id: 1-180，EN_TO_CN / CN_TO_EN / CHOICE）
-- 答案：180 条（id: 1-180，含少量错误答案用于测试评分统计）
-- =============================================
