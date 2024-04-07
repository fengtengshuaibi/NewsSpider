package com.intelligent.marking.intelligentmarking.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JsonData implements Serializable {
    private boolean success = false;
    private Object  return_code = "";
    private String  return_msg = "";
    private Object  data    =null;
    private Object  errinfo = null;
    public  JsonData success(Object data){
        this.success=true;
        this.data=data;
        this.return_msg="操作成功";
        this.return_code = "0";
        return this;
    }
    public  JsonData fail(String return_code,String return_msg){
        this.success=true;
        this.return_code=return_code;
        this.return_msg=return_msg;
        return this;
    }
}
