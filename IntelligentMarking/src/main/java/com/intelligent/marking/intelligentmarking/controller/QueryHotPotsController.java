package com.intelligent.marking.intelligentmarking.controller;

import com.intelligent.marking.intelligentmarking.service.QueryHotPotsService;
import com.intelligent.marking.intelligentmarking.vo.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/im")
public class QueryHotPotsController {
    @Autowired
    private QueryHotPotsService queryHotPotsService;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * queryTime必须是yyyyMMdd格式
     */
    @GetMapping("/queryHotPots")
    public JsonData queryHotPots(String queryTime, Integer count) {
        if (queryTime==null){
            queryTime=sf.format(new Date());
        }
        if (queryTime.length()!=10){
            return new JsonData().fail("1","日期格式错误，请使用yyyy-MM-dd格式");
        }

        // 调用服务查询热门景点
        return  queryHotPotsService.queryHotPots(queryTime,count);

    }
}