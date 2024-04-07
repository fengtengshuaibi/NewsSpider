package com.intelligent.marking.intelligentmarking.entity;

import java.util.Date;
import lombok.Data;

/**
 * Table: news
 */
@Data
public class News {
    /**
     * Column: id
     * Type: INT
     * Remark: 主键id
     */
    private Integer id;

    /**
     * Column: title
     * Type: VARCHAR(128)
     * Remark: 新闻标题
     */
    private String title;

    /**
     * Column: url
     * Type: VARCHAR(256)
     * Remark: 新闻链接
     */
    private String url;

    /**
     * Column: image
     * Type: VARCHAR(256)
     * Remark: 新闻图片
     */
    private String image;

    /**
     * Column: create_date
     * Type: DATETIME
     * Remark: 插入日期
     */
    private Date createDate;

    /**
     * Column: news_date
     * Type: DATETIME
     * Remark: 新闻日期
     */
    private Date newsDate;

    /**
     * Column: source
     * Type: VARCHAR(32)
     * Remark: 新闻来源
     */
    private String source;

    /**
     * Column: newstype1
     * Type: VARCHAR(255)
     * Remark: 新闻一级分类
     */
    private String newstype1;

    /**
     * Column: newstype2
     * Type: VARCHAR(255)
     * Remark: 新闻二级分类
     */
    private String newstype2;

    /**
     * Column: content
     * Type: LONGTEXT
     * Remark: 新闻正文
     */
    private String content;
}