package com.intelligent.marking.intelligentmarking.controller;

import com.intelligent.marking.intelligentmarking.service.IntelligentMarkingService;
import com.intelligent.marking.intelligentmarking.vo.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/im")
public class IntelligentMarkingController
{
    //每日经济新闻
    private static String URL = "http://apiv2.nbd.com.cn/article_lists/multi_columns_article_lists.json?app_key=d401a38c50a567882cd71cec43201c78&app_version_name=7.2.5&column_ids=1838&timestamp=1700666476&uuid=9aa9802925b94a5b83b3ffe50e188458";
    //每日财经热点榜
    private static String URL2 = "http://www.nbd.com.cn/news-rank-nr-h5/rank_index/news";
    //天行数据
    private static String URL3 = "https://apis.tianapi.com/caijing/index";
    //roll每日最新新闻
    private static String URL4 = "https://www.mxnzp.com/api/news/types/v2";
    private static String URL5 = "https://www.mxnzp.com/api/news/list/v2";
    private static String URL6 ="https://www.mxnzp.com/api/news/details/v2";
    @Autowired
    private IntelligentMarkingService intelligentMarkingService;
    @GetMapping("/getkeywords")
    public JsonData intelligentMarking() throws Exception {
       return intelligentMarkingService.intelligentMarking(URL);

    }
    @GetMapping("/getkeywords2")
    public JsonData intelligentMarking2() throws Exception {
        return intelligentMarkingService.intelligentMarking2(URL2);

    }
    @GetMapping("/getkeywords3")
    public JsonData intelligentMarking3() throws Exception {
        return intelligentMarkingService.intelligentMarking3(URL3);

    }
    @GetMapping("/getnewstypelist")
    public JsonData getnewstypelist() throws Exception {
        return intelligentMarkingService.getnewstypelist(URL4);

    }
    @GetMapping("/gettypenews")
    public JsonData gettypenews(String typeId,String page) throws Exception {
        return intelligentMarkingService.gettypenews(URL5,typeId,page);

    }
    @GetMapping("/getnewsdetail")
    public JsonData getnewsdetail(String newsId) throws Exception {
        return intelligentMarkingService.getnewsdetail(URL6,newsId);

    }

    @GetMapping("/getsourceintodatabase")
    public JsonData getsourceintodatabase() throws Exception {
        return intelligentMarkingService.getsourceintodatabase(URL4,URL5,URL6);

    }
}
