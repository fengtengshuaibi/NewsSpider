package com.intelligent.marking.intelligentmarking.entity;

import lombok.Data;

/**
 * Table: im_source_data
 */
@Data
public class ImSourceData {
    /**
     * Column: autoincre
     * Type: BIGINT UNSIGNED
     * Remark: 主键
     */
    private Long autoincre;

    /**
     * Column: from
     * Type: VARCHAR(30)
     * Remark: 来源
     */
    private String from;

    /**
     * Column: title
     * Type: VARCHAR(255)
     * Remark: 标题
     */
    private String title;

    /**
     * Column: link
     * Type: VARCHAR(1000)
     * Remark: 链接
     */
    private String link;

    /**
     * Column: tag
     * Type: VARCHAR(255)
     * Remark: 分类标签
     */
    private String tag;

    /**
     * Column: readingamount
     * Type: INT UNSIGNED
     * Default value: 0
     * Remark: 阅读量
     */
    private Integer readingamount;

    /**
     * Column: commentamount
     * Type: INT UNSIGNED
     * Default value: 0
     * Remark: 评论数
     */
    private Integer commentamount;

    /**
     * Column: likes
     * Type: INT UNSIGNED
     * Default value: 0
     * Remark: 点赞数
     */
    private Integer likes;

    /**
     * Column: belongs
     * Type: VARCHAR(30)
     * Remark: 所属栏目
     */
    private String belongs;

    /**
     * Column: articleid
     * Type: VARCHAR(30)
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
     * Column: keywords
     * Type: VARCHAR(255)
     * Remark: 关键词
     */
    private String keywords;

    /**
     * Column: rank
     * Type: INT UNSIGNED
     * Default value: 0
     * Remark: 序列
     */
    private Integer rank;

    /**
     * Column: recordTime
     * Type: VARCHAR(30)
     * Remark: 新闻录入时间yyyy-MM-dd hh:mm:ss
     */
    private String recordtime;

    /**
     * Column: text
     * Type: TEXT
     * Remark: 新闻正文（如果有）
     */
    private String text;
}