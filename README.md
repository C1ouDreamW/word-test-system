# 专业英语单词测试系统

> **选题**：4．专业英语单词测试 ｜ **等级**：A ｜ **人数**：2  
> **技术栈**：Spring Boot 3.5 + REST API + Bootstrap 5 + MySQL 8.0

---

## 一、项目简介

面向学生和教师端的专业英语单词限时考试系统。支持管理员发布考试、学生在线答题（题目随机排序）、自动判卷、成绩统计，以及根据答题情况生成错词字符云。

---

## 二、快速开始

### 环境要求

- JDK 17
- Maven 3.6+
- MySQL 8.0

### 启动步骤

```bash
# 1. 初始化数据库
mysql -u root -p < sql/init.sql

# 2. 修改数据库连接（如果密码不是 root）
# 编辑 src/main/resources/application.yaml → spring.datasource.password

# 3. 启动项目
mvn spring-boot:run

# 4. 浏览器访问
# http://localhost:8080/login.html
```

### 预置账号

| 用户名 | 角色 |
|--------|------|
| admin | 管理员 |
| zhangsan | 学生 |
| lisi | 学生 |
| wangwu | 学生 |

---

## 三、开发流程

### 分支策略

```
main
├── dev-a   ← A 开发分支
└── dev-b   ← B 开发分支
```

### Commit 格式

```
type(scope): 说明

feat  → 新功能
fix   → 修 bug
docs  → 文档
chore → 构建/配置
```

## 四、项目结构

```
├── sql/init.sql                   数据库初始化脚本
├── pom.xml                        Maven 配置
├── README.md
├── 项目方案/
│   ├── 项目相关/                  需求、架构、流程图、分工
│   └── 开发相关/                  API文档、数据库设计、规范、环境指南
├── src/main/
│   ├── java/com/scut/wordtest/
│   │   ├── WordTestApplication.java
│   │   ├── controller/           REST 接口
│   │   ├── service/              业务逻辑
│   │   ├── dao/                  DAO 接口 + 实现类
│   │   ├── entity/               实体类
│   │   └── config/               配置类
│   └── resources/
│       ├── application.properties
│       └── static/               前端页面（HTML/JS/CSS）
└── docs/                         任务书、设计要求
```
