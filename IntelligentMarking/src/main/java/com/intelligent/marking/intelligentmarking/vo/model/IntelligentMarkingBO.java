/**
  * Copyright 2023 bejson.com 
  */
package com.intelligent.marking.intelligentmarking.vo.model;
import java.util.List;

/**
 * Auto-generated: 2023-12-13 9:48:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class IntelligentMarkingBO {

    private int status_code;
    private String msg;
    private List<Data> data;
    public void setStatus_code(int status_code) {
         this.status_code = status_code;
     }
     public int getStatus_code() {
         return status_code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}