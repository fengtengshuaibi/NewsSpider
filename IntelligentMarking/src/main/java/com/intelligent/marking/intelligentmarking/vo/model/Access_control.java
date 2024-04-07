/**
  * Copyright 2023 bejson.com 
  */
package com.intelligent.marking.intelligentmarking.vo.model;

/**
 * Auto-generated: 2023-12-13 9:48:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Access_control {

    private String access_type;
    private String url;
    private boolean is_redirect_url;
    public void setAccess_type(String access_type) {
         this.access_type = access_type;
     }
     public String getAccess_type() {
         return access_type;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

    public void setIs_redirect_url(boolean is_redirect_url) {
         this.is_redirect_url = is_redirect_url;
     }
     public boolean getIs_redirect_url() {
         return is_redirect_url;
     }

}