package com.demo.wordtest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单词实体 —— 对应 words 表
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private Integer id;
    private String english;   // 英文单词
    private String chinese;   // 中文释义
    private String category;  // 分类（如：网络基础、网络安全、网络协议）
}
