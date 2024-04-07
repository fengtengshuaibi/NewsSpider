/**
  * Copyright 2023 bejson.com 
  */
package com.intelligent.marking.intelligentmarking.vo.model2;
import java.util.List;

/**
 * Auto-generated: 2023-12-22 11:14:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result {

    private int curpage;
    private int allnum;
    private List<Newslist> newslist;
    public void setCurpage(int curpage) {
         this.curpage = curpage;
     }
     public int getCurpage() {
         return curpage;
     }

    public void setAllnum(int allnum) {
         this.allnum = allnum;
     }
     public int getAllnum() {
         return allnum;
     }

    public void setNewslist(List<Newslist> newslist) {
         this.newslist = newslist;
     }
     public List<Newslist> getNewslist() {
         return newslist;
     }

}