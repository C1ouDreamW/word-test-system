# API 接口文档

> 基础路径：`http://localhost:8080`  
> 所有接口以 `/api/` 开头  
> 请求/响应均为 JSON

---

## 1. 登录

### POST `/api/login`

**请求体：**
```json
{
    "username": "zhangsan",
    "role": "student"
}
```

**成功响应：**
```json
{
    "code": 200,
    "message": "ok",
    "data": {
        "id": 1,
        "username": "zhangsan",
        "role": "student"
    }
}
```

---

## 2. 单词管理（管理员）

### GET `/api/words`

获取单词列表，支持搜索和分页。

**参数：** `?keyword=&category=&page=1&size=20`

**响应：**
```json
{
    "code": 200,
    "data": {
        "list": [
            { "id": 1, "english": "router", "chinese": "路由器", "category": "网络基础" },
            { "id": 2, "english": "subnet", "chinese": "子网", "category": "网络基础" }
        ],
        "total": 50,
        "page": 1
    }
}
```

### POST `/api/words`

添加单词。

**请求体：**
```json
{
    "english": "bandwidth",
    "chinese": "带宽",
    "category": "网络基础"
}
```

**响应：** `data` 返回新增的单词对象（含 id）

### PUT `/api/words/{id}`

修改单词。请求体同 POST。

### DELETE `/api/words/{id}`

删除单词。

---

## 3. 考试管理（管理员）

### GET `/api/exams`

获取考试列表。

**响应：**
```json
{
    "code": 200,
    "data": [
        {
            "id": 1,
            "title": "第1章 网络基础测验",
            "startTime": "2026-06-22 08:00:00",
            "endTime": "2026-06-22 20:00:00",
            "questionCount": 20,
            "status": "进行中"
        }
    ]
}
```

### POST `/api/exams`

创建考试。

**请求体：**
```json
{
    "title": "第1章 网络基础测验",
    "startTime": "2026-06-22 08:00:00",
    "endTime": "2026-06-22 20:00:00",
    "questionCount": 20
}
```

### DELETE `/api/exams/{id}`

删除考试。

---

## 4. 考试答题（学生）

### GET `/api/exams/{examId}/paper?userId=1`

获取考卷。系统抽题 + 随机打乱顺序后返回，同时持久化到 questions 表。

**响应：**
```json
{
    "code": 200,
    "data": {
        "examId": 1,
        "examTitle": "第1章 网络基础测验",
        "remainingSeconds": 600,
        "questions": [
            {
                "questionId": 1,
                "type": "EN_TO_CN",
                "question": "router",
                "options": null
            },
            {
                "questionId": 2,
                "type": "CHOICE",
                "question": "bandwidth",
                "options": ["带宽", "延迟", "吞吐量", "丢包率"]
            }
        ]
    }
}
```

**题型说明：**
- `EN_TO_CN`：看英文写中文（无 options）
- `CN_TO_EN`：看中文写英文（无 options）
- `CHOICE`：选择题（options 为 4 个中文选项）

### POST `/api/exams/{examId}/submit`

提交答案，自动判卷。

**请求体：**
```json
{
    "userId": 1,
    "answers": [
        { "questionId": 1, "answer": "路由器" },
        { "questionId": 2, "answer": "带宽" }
    ]
}
```

**响应：**
```json
{
    "code": 200,
    "data": {
        "score": 85,
        "total": 20,
        "correctCount": 17,
        "details": [
            {
                "questionId": 1,
                "question": "router",
                "yourAnswer": "路由器",
                "correctAnswer": "路由器",
                "correct": true
            },
            {
                "questionId": 2,
                "question": "bandwidth",
                "yourAnswer": "带宽",
                "correctAnswer": "带宽",
                "correct": true
            }
        ]
    }
}
```

---

## 5. 成绩（学生 & 管理员）

### GET `/api/scores?userId=1`

查询某个学生的所有考试成绩。

### GET `/api/scores?examId=1`

查询某次考试的所有学生成绩（管理员用）。

**响应：**
```json
{
    "code": 200,
    "data": [
        { "userId": 1, "username": "zhangsan", "score": 85, "correctCount": 17, "total": 20 },
        { "userId": 2, "username": "lisi",     "score": 70, "correctCount": 14, "total": 20 }
    ]
}
```

---

## 6. 考试统计（管理员）

### GET `/api/stats/{examId}`

**响应：**
```json
{
    "code": 200,
    "data": {
        "examTitle": "第1章 网络基础测验",
        "totalStudents": 3,
        "avgScore": 75,
        "maxScore": 85,
        "minScore": 60,
        "questionStats": [
            { "questionId": 1, "question": "router", "correctRate": 100 },
            { "questionId": 2, "question": "bandwidth", "correctRate": 66 }
        ]
    }
}
```

---

## 7. 答题详情（管理员）

### GET `/api/exams/{examId}/answers?userId=1`

查询某学生在某次考试中的完整答题详情，用于管理员在成绩统计页点击查看学生答卷。

**响应：**
```json
{
    "code": 200,
    "data": {
        "score": 85,
        "total": 20,
        "correctCount": 17,
        "details": [
            {
                "questionId": 1,
                "question": "router",
                "yourAnswer": "路由器",
                "correctAnswer": "路由器",
                "correct": true
            },
            {
                "questionId": 2,
                "question": "bandwidth",
                "yourAnswer": "延迟",
                "correctAnswer": "带宽",
                "correct": false
            }
        ]
    }
}
```

---

## 8. 字符云（学生 & 管理员）

### GET `/api/wordcloud?userId=1`

返回某学生答错过的单词及频次。

### GET `/api/wordcloud`

返回全局错词统计（管理员用）。

**响应：**
```json
{
    "code": 200,
    "data": [
        { "word": "subnet",     "wrongCount": 5 },
        { "word": "bandwidth",  "wrongCount": 3 },
        { "word": "latency",    "wrongCount": 2 }
    ]
}
```

---

## 接口汇总

| 方法 | 路径 | 用途 | 权限 |
|------|------|------|:--:|
| POST | `/api/login` | 登录 | 公开 |
| GET | `/api/words` | 单词列表 | 管理员 |
| POST | `/api/words` | 添加单词 | 管理员 |
| PUT | `/api/words/{id}` | 修改单词 | 管理员 |
| DELETE | `/api/words/{id}` | 删除单词 | 管理员 |
| GET | `/api/exams` | 考试列表 | 管理员 |
| POST | `/api/exams` | 创建考试 | 管理员 |
| DELETE | `/api/exams/{id}` | 删除考试 | 管理员 |
| GET | `/api/exams/{examId}/paper` | 获取考卷 | 学生 |
| POST | `/api/exams/{examId}/submit` | 提交答案 | 学生 |
| GET | `/api/exams/{examId}/answers` | 查看学生答卷详情 | 管理员 |
| GET | `/api/scores` | 查询成绩 | 学生/管理员 |
| GET | `/api/stats/{examId}` | 考试统计 | 管理员 |
| GET | `/api/wordcloud` | 字符云数据 | 学生/管理员 |
