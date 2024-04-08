package com.intelligent.marking.intelligentmarking.entity;

/**
 * Table: im_source_data
 */
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
     * Type: VARCHAR(100)
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

    public Long getAutoincre() {
        return autoincre;
    }

    public void setAutoincre(Long autoincre) {
        this.autoincre = autoincre;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from == null ? null : from.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Integer getReadingamount() {
        return readingamount;
    }

    public void setReadingamount(Integer readingamount) {
        this.readingamount = readingamount;
    }

    public Integer getCommentamount() {
        return commentamount;
    }

    public void setCommentamount(Integer commentamount) {
        this.commentamount = commentamount;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getBelongs() {
        return belongs;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs == null ? null : belongs.trim();
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid == null ? null : articleid.trim();
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime == null ? null : submittime.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime == null ? null : recordtime.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }
}