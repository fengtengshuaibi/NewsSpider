package com.intelligent.marking.intelligentmarking.entity;

import lombok.Data;

/**
 * Table: im_hot_info
 */
@Data
public class ImHotInfo {
    /**
     * Column: autoincre
     * Type: BIGINT
     * Remark: 主键
     */
    private Long autoincre;

    /**
     * Column: keyword
     * Type: VARCHAR(255)
     * Remark: 关键词
     */
    private String keyword;

    /**
     * Column: articleid
     * Type: VARCHAR(50)
     * Remark: 文章id
     */
    private String articleid;

    /**
     * Column: submitTime
     * Type: VARCHAR(30)
     * Remark: 入库时间yyyy-MM-dd hh:mm:ss
     */
    private String submittime;

    /**
     * Column: recordTime
     * Type: VARCHAR(30)
     * Remark: 新闻录入时间
     */
    private String recordtime;

    /**
     * Column: score
     * Type: INT
     * Default value: 0
     * Remark: 热点得分
     */
    private Integer score;
}