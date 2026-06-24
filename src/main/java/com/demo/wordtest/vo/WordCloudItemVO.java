package com.demo.wordtest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字符云数据项 VO
 * @author wangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordCloudItemVO {
    private String word;
    private Integer wrongCount;
}
