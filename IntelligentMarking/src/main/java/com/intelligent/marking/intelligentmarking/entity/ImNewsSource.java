package com.intelligent.marking.intelligentmarking.entity;

import lombok.Data;

/**
 * Table: im_news_source
 */
@Data
public class ImNewsSource {
    /**
     * Column: autoincre
     * Type: BIGINT UNSIGNED
     */
    private Long autoincre;

    /**
     * Column: typeName
     * Type: VARCHAR(255)
     * Remark: 新闻类型
     */
    private String typename;

    /**
     * Column: typeId
     * Type: INT
     * Remark: 新闻类型id
     */
    private Integer typeid;

    /**
     * Column: newsId
     * Type: INT
     * Remark: 新闻编号
     */
    private Integer newsid;

    /**
     * Column: postTime
     * Type: VARCHAR(255)
     * Remark: 新闻发布时间
     */
    private String posttime;

    /**
     * Column: source
     * Type: VARCHAR(30)
     * Remark: 新闻来源
     */
    private String source;

    /**
     * Column: title
     * Type: VARCHAR(255)
     * Remark: 标题
     */
    private String title;

    /**
     * Column: type
     * Type: VARCHAR(30)
     * Remark: 新闻类型
     */
    private String type;

    /**
     * Column: videoList
     * Type: TEXT
     * Remark: 音频列表
     */
    private String videolist;

    /**
     * Column: digest
     * Type: TEXT
     * Remark: 摘要
     */
    private String digest;

    /**
     * Column: imgList
     * Type: TEXT
     * Remark: 图文列表
     */
    private String imglist;

    /**
     * Column: videoUrl
     * Type: TEXT
     * Remark: 音频链接
     */
    private String videourl;

    /**
     * Column: imageUrl
     * Type: TEXT
     * Remark: 图片链接
     */
    private String imageurl;

    /**
     * Column: content
     * Type: TEXT
     * Remark: 新闻正文
     */
    private String content;
}