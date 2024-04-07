package com.intelligent.marking.intelligentmarking.scheduletask;

import com.alibaba.fastjson2.JSON;
import com.intelligent.marking.intelligentmarking.entity.ImNewsSource;
import com.intelligent.marking.intelligentmarking.mapper.ImSourceDataMapper;
import com.intelligent.marking.intelligentmarking.service.impl.IntelligentMarkingServiceImpl;
import com.intelligent.marking.intelligentmarking.utils.BatchDAOUtils;
import com.intelligent.marking.intelligentmarking.vo.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScheduledTasks {

    // 每天午夜12点执行package com.example.scheduledtasks;
    //
    //import org.springframework.context.annotation.Configuration;
    //import org.springframework.scheduling.annotation.EnableScheduling;
    //
    //@Configuration
    //@EnableScheduling
    //public class ScheduledTasksConfig {
    //
    //}
    @Autowired
    BatchDAOUtils batchDAOUtils;
    private static String URL4 = "https://www.mxnzp.com/api/news/types/v2";
    @Autowired
    IntelligentMarkingServiceImpl intelligentMarkingService;
    private static String URL5 = "https://www.mxnzp.com/api/news/list/v2";
    private static String URL6 ="https://www.mxnzp.com/api/news/details/v2";
    @Scheduled(cron = "0 0 0 * * ?")
    public JsonData getNews() {
    //查询所有的新闻类型

        JsonData jsonData = null;
        JsonData jsondata2=null;
        JsonData jsondata3=null;
        try {
            jsonData = intelligentMarkingService.getnewstypelist(URL4);
            String returnCode = (String) jsonData.getReturn_code();
            Map data = (Map) jsonData.getData();
            List<ImNewsSource>datalist=new ArrayList<>();
            ImNewsSource imNewsSource=new ImNewsSource();
            if ("0".equals(returnCode)){
                log.info("查询新闻类型成功，{}", JSON.toJSONString(data));
                long start = System.currentTimeMillis();
                Integer code = (Integer) data.get("code");
                if (!"1".equals(String.valueOf(code))){
                    return new JsonData().fail("1",data.get("msg").toString());
                }
                List<Map<String,Object>> newstypelist = (List<Map<String, Object>>) data.get("data");
                for (Map<String, Object> newstypemap : newstypelist) {
                    String typeName = (String) newstypemap.get("typeName");
                    Integer typeId = (Integer) newstypemap.get("typeId");
                    //根据newsid查询news新闻
                    imNewsSource.setTypename(typeName);
                    imNewsSource.setTypeid(typeId);
                    jsondata2 = intelligentMarkingService.gettypenews(URL5, String.valueOf(typeId), "");
                    String returnCode2 = (String) jsondata2.getReturn_code();
                    if ("0".equals(returnCode2)) {
                        log.info("根据newsid查询news新闻成功，{}", JSON.toJSONString(jsondata2));
                        Map data1 = (Map) jsondata2.getData();
                        Integer code2 = (Integer) data1.get("code");
                        if (!"1".equals(String.valueOf(code2))){
                            return new JsonData().fail("1",data1.get("msg").toString());
                        }
                        List<Map<String,Object>> newslist = (List<Map<String, Object>>) data1.get("data");
                        for (Map<String, Object> newsmap : newslist) {
                            String newsId = (String) newsmap.get("newsId");
                            String postTime = (String) newsmap.get("postTime");
                            List<String> videoList= (List<String>) newsmap.get("videoList");
                            String digest = (String) newsmap.get("digest");
                            String source = (String) newsmap.get("source");
                            String title = (String) newsmap.get("title");
                            List<String> imgList = (List<String>) newsmap.get("imgList");
                            imNewsSource.setNewsid(Integer.valueOf(newsId));
                            imNewsSource.setPosttime(postTime);
                            imNewsSource.setDigest(digest);
                            imNewsSource.setSource(source);
                            imNewsSource.setTitle(title);
                            imNewsSource.setImglist(imgList.toString());
                            imNewsSource.setVideolist(videoList.toString());
                            //根据newsid查询新闻详情
                            jsondata3= intelligentMarkingService.getnewsdetail(URL6, newsId);
                            String returnCode1 = (String) jsondata3.getReturn_code();
                            if(returnCode1.equals("0")) {
                                log.info("根据newsid查询新闻详情成功，{}", JSON.toJSONString(jsondata3));
                                Map newsdetails = (Map) jsondata3.getData();
                                Integer id = (Integer) newsdetails.get("id");
                                Map<String,Object>newsdetailsmap= (Map<String, Object>) newsdetails.get("data");
                                String code1 = (String) newsdetails.get("code");
                                if (!"1".equals(String.valueOf(code1))){
                                    return new JsonData().fail("1",newsdetails.get("msg").toString());
                                }
                                List<Map<String,Object>> newsdetaillist = (List) newsdetailsmap.get("items");
                                for (Map<String, Object> newsdetailmap : newsdetaillist) {
                                    String videoUrl = (String) newsdetailmap.get("videoUrl");
                                    String imageUrl = (String) newsdetailmap.get("imageUrl");
                                    String type = (String) newsdetailmap.get("type");
                                    String content = (String) newsdetailmap.get("content");
                                    imNewsSource.setVideourl(videoUrl);
                                    imNewsSource.setImageurl(imageUrl);
                                    imNewsSource.setType(type);
                                    imNewsSource.setContent(content);
                                    datalist.add(imNewsSource);
                                }
                            }else{
                                log.info("根据newsid查询新闻详情失败，{}");
                                return new JsonData().fail("1","根据newsid查询新闻详情失败");
                            }
                        }
                    }else{
                        log.info("查询新闻失败，{}");
                        return new JsonData().fail("1","根据新闻id查询新闻失败");
                    }
                    }
                long end = System.currentTimeMillis();
                log.info("将新闻类型组装进实体类。。。,耗时{}s",end-start/1000);
                if (datalist.size()>0){
                    log.info("开始批量入库,共{}条",datalist.size());
                    long start2 = System.currentTimeMillis();
                    batchDAOUtils.saveBatch(ImSourceDataMapper.class,"insertSelective",datalist,1000);
                    long end2 = System.currentTimeMillis();
                    log.info("开始批量入库,耗时{}s",end2-start2/1000);
                }
            }else{
                log.info("查询新闻类型失败，{}");
                return new JsonData().fail("1","查询新闻类型失败");

            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return new JsonData().success("");
    }
}
