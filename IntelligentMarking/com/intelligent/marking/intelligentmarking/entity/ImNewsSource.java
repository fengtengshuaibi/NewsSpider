package com.intelligent.marking.intelligentmarking.entity;

/**
 * Table: im_news_source
 */
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

    public Long getAutoincre() {
        return autoincre;
    }

    public void setAutoincre(Long autoincre) {
        this.autoincre = autoincre;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getNewsid() {
        return newsid;
    }

    public void setNewsid(Integer newsid) {
        this.newsid = newsid;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime == null ? null : posttime.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getVideolist() {
        return videolist;
    }

    public void setVideolist(String videolist) {
        this.videolist = videolist == null ? null : videolist.trim();
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    public String getImglist() {
        return imglist;
    }

    public void setImglist(String imglist) {
        this.imglist = imglist == null ? null : imglist.trim();
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl == null ? null : videourl.trim();
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}