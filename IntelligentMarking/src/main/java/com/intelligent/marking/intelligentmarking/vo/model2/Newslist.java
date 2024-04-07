/**
  * Copyright 2023 bejson.com 
  */
package com.intelligent.marking.intelligentmarking.vo.model2;
import java.util.Date;

/**
 * Auto-generated: 2023-12-22 11:14:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Newslist {

    private String id;
    private Date ctime;
    private String title;
    private String description;
    private String source;
    private String picUrl;
    private String url;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setCtime(Date ctime) {
         this.ctime = ctime;
     }
     public Date getCtime() {
         return ctime;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

    public void setPicUrl(String picUrl) {
         this.picUrl = picUrl;
     }
     public String getPicUrl() {
         return picUrl;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

}