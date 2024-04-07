package com.intelligent.marking.intelligentmarking.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.intelligent.marking.intelligentmarking.entity.*;
import com.intelligent.marking.intelligentmarking.mapper.ImHotInfoMapper;
import com.intelligent.marking.intelligentmarking.mapper.ImSourceDataMapper;
import com.intelligent.marking.intelligentmarking.service.IntelligentMarkingService;
import com.intelligent.marking.intelligentmarking.utils.AnsjUtils;
import com.intelligent.marking.intelligentmarking.utils.BatchDAOUtils;
import com.intelligent.marking.intelligentmarking.utils.IBatchDAO;
import com.intelligent.marking.intelligentmarking.vo.JsonData;
import com.intelligent.marking.intelligentmarking.vo.model.*;
import com.intelligent.marking.intelligentmarking.vo.model1.Data2;
import com.intelligent.marking.intelligentmarking.vo.model1.IntelligentMarkingBO2;
import com.intelligent.marking.intelligentmarking.vo.model1.SourceData2;
import com.intelligent.marking.intelligentmarking.vo.model2.IntelligentMarkingBO3;
import com.intelligent.marking.intelligentmarking.vo.model2.Newslist;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IntelligentMarkingServiceImpl implements IntelligentMarkingService {
    @Autowired
    private ImSourceDataMapper imSourceDataMapper;
    @Autowired
    private ImHotInfoMapper imHotInfoMapper;
    @Autowired
    private IBatchDAO iBatchDAO;
    @Autowired
    BatchDAOUtils batchDAOUtils;
    @Value("${roll.appid}")
    private String appid;
    @Value("${roll.appsecret}")
    private String appsecret;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public JsonData intelligentMarking(String URL) throws Exception {
        IntelligentMarkingBO sourceData = AnsjUtils.getSourceData(URL);
        //插入DB
        List result = insertIntoDB(sourceData);
        insertIntoKeyWords(result);
        return new JsonData().success(result);
    }

    @Override
    public JsonData intelligentMarking2(String URL) throws Exception {
        IntelligentMarkingBO2 sourceData = AnsjUtils.getSourceData2(URL);
        //插入DB
        List<Map<String,Object>> result = insertIntoDB2(sourceData);
        insertIntoKeyWords(result);
        return new JsonData().success(result);
    }

    private Set<String> cacheHotPot() {
        //热点分析
        ImHotInfoExample example=new ImHotInfoExample();
        ImHotInfoExample.Criteria criteria = example.createCriteria();
        String todayString = sf1.format(new Date());
        criteria.andRecordtimeLike(todayString + "%");
        //查询出当天所有的热点记录放到缓存中
        List<ImHotInfo> imHotInfos = imHotInfoMapper.selectByExample(example);
        //关键词集合
        Set<String> keywordsSet = imHotInfos.stream().map(t -> t.getKeyword()).collect(Collectors.toSet());
        return keywordsSet;
    }

    private void insertIntoKeyWords(List<Map<String,Object>> result) {
        Set<String> keywordsSet = cacheHotPot();
        List<ImHotInfo>ImHotInfos=new ArrayList<>();
        List<ImHotInfo>ImHotInfoDumplicate=new ArrayList<>();
        for (Map<String,Object> o : result) {
            List<String> keywords = (List) o.get("keywords");
            for (String keyword : keywords) {
                if (keywordsSet.contains(keyword)){
                    //如果包含该关键词了，则将当天该关键词score+1
                    ImHotInfoExample example=new ImHotInfoExample();
                    ImHotInfoExample.Criteria criteria = example.createCriteria();
                    criteria.andKeywordEqualTo(keyword);
                    List<ImHotInfo> imHotInfos = imHotInfoMapper.selectByExample(example);
                    Long autoincre = imHotInfos.get(0).getAutoincre();
                    Integer score = imHotInfos.get(0).getScore();
                    ImHotInfo info=new ImHotInfo();
                    info.setAutoincre(autoincre);
                    info.setSubmittime(sf.format(new Date()));
                    info.setArticleid((String) o.get("articleId"));
                    info.setScore(score+1);
                    ImHotInfoDumplicate.add(info);
                }else{
                    ImHotInfo info=new ImHotInfo();
                    info.setKeyword(keyword);
                    info.setArticleid((String) o.get("articleId"));
                    info.setSubmittime(sf.format(new Date()));
                    info.setRecordtime((String) o.get("createTime"));
                    ImHotInfos.add(info);
                }

            }

        }
        if (ImHotInfos.size()>0) {
            iBatchDAO.saveBatch(ImHotInfoMapper.class, "insertSelective", ImHotInfos, 500);
        }
        if (ImHotInfoDumplicate.size()>0){
            iBatchDAO.saveBatch(ImHotInfoMapper.class, "updateByPrimaryKeySelective", ImHotInfos, 500);
        }
    }

    @Override
    public JsonData intelligentMarking3(String url3) {
        String tianapi_data = "";
        List resultList=new ArrayList();
        try {
            URL url = new URL( url3);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            OutputStream outputStream = conn.getOutputStream();
            String content = "key=5afc33798db9c7f911936cc314b9dcdb";
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader (inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
            StringBuilder tianapi = new StringBuilder();
            String temp = null;
            while ( null != (temp = bufferedReader.readLine())){
                tianapi.append(temp);
            }
            tianapi_data = tianapi.toString();
            IntelligentMarkingBO3 intelligentMarkingBO3 = JSON.parseObject(tianapi_data, IntelligentMarkingBO3.class);
            inputStream.close();
            resultList = insertIntoDB3(intelligentMarkingBO3);
            insertIntoKeyWords(resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tianapi_data);
        return new JsonData().success(resultList);
    }

    @Override
    public JsonData getnewstypelist(String url4) throws UnsupportedEncodingException {
        StringBuffer sb=new StringBuffer();
        sb.append(url4).append("?app_id=").append(URLEncoder.encode(appid, StandardCharsets.UTF_8.name())).append("&app_secret=").append(URLEncoder.encode(appsecret, StandardCharsets.UTF_8.name()));
        String url = sb.toString();
        JSONObject jsonObject = new JSONObject();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // 设置请求方式
            con.setRequestMethod("GET");
            // 获取响应码
            int responseCode = con.getResponseCode();
            // 读取响应
            if (responseCode == HttpURLConnection.HTTP_OK) { // 成功
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //将上述参数返回的respoonse字符串转换为json对象
                jsonObject=JSONObject.parseObject(response.toString());
                //获取json对象中的data数据
            } else {
                log.error("GET 请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonData().success(jsonObject);
    }

    @Override
    public JsonData gettypenews(String url5, String typeId, String page) throws UnsupportedEncodingException {
        StringBuffer sb=new StringBuffer();
        sb.append(url5).append("?typeId=").append(typeId).append("&page=")
                .append(StringUtils.isEmpty(page)?"1":page)
                .append("&app_id=")
                .append(URLEncoder.encode(appid, StandardCharsets.UTF_8.name()))
                .append("&app_secret=")
                .append(URLEncoder.encode(appsecret, StandardCharsets.UTF_8.name()));
        String url = sb.toString();
        JSONObject jsonObject = new JSONObject();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // 设置请求方式
            con.setRequestMethod("GET");
            // 获取响应码
            int responseCode = con.getResponseCode();
            // 读取响应
            if (responseCode == HttpURLConnection.HTTP_OK) { // 成功
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //将上述参数返回的respoonse字符串转换为json对象
                jsonObject=JSONObject.parseObject(response.toString());
                //获取json对象中的data数据
            } else {
                log.error("GET 请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonData().success(jsonObject);
    }

    @Override
    public JsonData getnewsdetail(String url6, String newsId) throws UnsupportedEncodingException {
        StringBuffer sb=new StringBuffer();
        sb.append(url6).append("?newsId=").append(newsId)
                .append("&app_id=")
                .append(URLEncoder.encode(appid, StandardCharsets.UTF_8.name()))
                .append("&app_secret=")
                .append(URLEncoder.encode(appsecret, StandardCharsets.UTF_8.name()));
        String url = sb.toString();
        JSONObject jsonObject = new JSONObject();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // 设置请求方式
            con.setRequestMethod("GET");
            // 获取响应码
            int responseCode = con.getResponseCode();
            // 读取响应
            if (responseCode == HttpURLConnection.HTTP_OK) { // 成功
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //将上述参数返回的respoonse字符串转换为json对象
                jsonObject=JSONObject.parseObject(response.toString());
                //获取json对象中的data数据
            } else {
                log.error("GET 请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonData().success(jsonObject);
    }

    @Override
    public JsonData getsourceintodatabase(String url4, String url5, String url6) {
        //查询所有的新闻类型

        JsonData jsonData = null;
        JsonData jsondata2=null;
        JsonData jsondata3=null;
        try {
            jsonData = getnewstypelist(url4);
            String returnCode = (String) jsonData.getReturn_code();
            Map data = (Map) jsonData.getData();
            List<ImNewsSource>datalist=new ArrayList<>();
            ImNewsSource imNewsSource=new ImNewsSource();
            if ("0".equals(returnCode)){
                log.info("查询新闻类型成功，{}", com.alibaba.fastjson2.JSON.toJSONString(data));
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
                    jsondata2 = gettypenews(url5, String.valueOf(typeId), "");
                    String returnCode2 = (String) jsondata2.getReturn_code();
                    if ("0".equals(returnCode2)) {
                        log.info("根据newsid查询news新闻成功，{}", com.alibaba.fastjson2.JSON.toJSONString(jsondata2));
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
                            imNewsSource.setImglist(imgList==null?"":imgList.toString());
                            imNewsSource.setVideolist(videoList==null?"":videoList.toString());
                            //根据newsid查询新闻详情
                            jsondata3= getnewsdetail(url6, newsId);
                            String returnCode1 = (String) jsondata3.getReturn_code();
                            if(returnCode1.equals("0")) {
                                log.info("根据newsid查询新闻详情成功，{}", com.alibaba.fastjson2.JSON.toJSONString(jsondata3));
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
                                    imNewsSource.setVideourl(videoUrl==null?"":videoUrl);
                                    imNewsSource.setImageurl(imageUrl==null?"":imageUrl);
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

    private List insertIntoDB3(IntelligentMarkingBO3 intelligentMarkingBO3) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result=new ArrayList();
        List<Newslist> newslists = intelligentMarkingBO3.getResult().getNewslist();
        for (int j = 0; j<newslists.size() ; j++) {
            Map results=new HashMap<>();
            Newslist newslist = newslists.get(j);
            String id = newslist.getId();
            Date ctime = newslist.getCtime();
            String title = newslist.getTitle();
            String description = newslist.getDescription();
            String source = newslist.getSource();
            String picUrl = newslist.getPicUrl();
            String url = newslist.getUrl();


            if ("全球市场早报".equals(title.substring(1,7))||"全球市场晚报".equals(title.substring(1,7))){
                continue;
            }
            ImSourceData imSourceData=new ImSourceData();
            imSourceData.setFrom(source);
            imSourceData.setTitle(title);
            imSourceData.setLink(url);
            imSourceData.setBelongs(source);
            imSourceData.setArticleid(id);
            imSourceData.setSubmittime(sf.format(new Date()));
            imSourceData.setRank(j);
            //解析关键词
            List<String> keywords = AnsjUtils.getKeyword3(newslist);
            imSourceData.setKeywords(keywords.toString());
            //文章id有则更新，无则插入
            ImSourceDataExample example=new ImSourceDataExample();
            example.createCriteria().andArticleidEqualTo(id);
            List<ImSourceData> imSourceDatas = imSourceDataMapper.selectByExample(example);
            if (imSourceDatas.size()>0){
                ImSourceData imSourceData1 = imSourceDatas.get(0);
                imSourceData.setAutoincre(imSourceData1.getAutoincre());
                int i = imSourceDataMapper.updateByPrimaryKeySelective(imSourceData);
                if (i== 1){
                    log.info("更新成功");
                }else{
                    log.info("更新失败");
                }
            }else{
                int i = imSourceDataMapper.insertSelective(imSourceData);
                if (i== 1){
                    log.info("入库成功");
                }else{
                    log.info("入库失败");
                }
            }
            results.put("title",title);
            results.put("keywords",keywords);
            results.put("rank",j+1);
            results.put("createTime",sf.format(new Date()));
            result.add(results);
        }
        return result;
    }

    public static void main(String[] args) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 转换时间戳
        Long create_time =1703474416l ;
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(create_time), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        System.out.println("Formatted Date: " + formattedDate);
    }
    private List<Map<String,Object>> insertIntoDB2(IntelligentMarkingBO2 sourceData) throws Exception {
        List result=new ArrayList();
        Data2 data2 = sourceData.getData();
        int total = data2.getTotal();
        List<SourceData2> list = data2.getList();
        String date = data2.getDate();
        String week = data2.getWeek();
        int code = sourceData.getCode();
        String msg = sourceData.getMsg();
        for (int j = 0; j<list.size() ; j++) {
            Map results=new HashMap<>();
            SourceData2 sourceData2 = list.get(j);
            String id = sourceData2.getId();
            String snap_id = sourceData2.getSnap_id();
            long coll_time = sourceData2.getColl_time();
            int source_type = sourceData2.getSource_type();
            int item_type = sourceData2.getItem_type();
            int flag_type = sourceData2.getFlag_type();
            String news_id = sourceData2.getNews_id();
            String title = sourceData2.getTitle();
            String url = sourceData2.getUrl();
            int top_num = sourceData2.getTop_num();
            int risen_num = sourceData2.getRisen_num();
            String score_num = sourceData2.getScore_num();
            int expire_time = sourceData2.getExpire_time();
            long create_time = sourceData2.getCreate_time();
            String n_words = sourceData2.getN_words();
            long n_create_time = sourceData2.getN_create_time();
            int ai_status = sourceData2.getAi_status();
            String ai_text = sourceData2.getAi_text();
            String ai_mode = sourceData2.getAi_mode();
            int item_num = sourceData2.getItem_num();

            ImSourceData imSourceData=new ImSourceData();
            imSourceData.setFrom("每日财经热点榜");
            imSourceData.setTitle(title);
            imSourceData.setLink(url);
            imSourceData.setTag(n_words);
            imSourceData.setBelongs("每日财经热点榜");
            imSourceData.setArticleid(news_id);
            imSourceData.setSubmittime(sf.format(new Date()));
            imSourceData.setRank(j);
            String formattedDate =unixTimeToDate(create_time);
            imSourceData.setRecordtime(formattedDate);
            //解析关键词
            List<String> keywords = AnsjUtils.getKeyword2(sourceData2);
            imSourceData.setKeywords(keywords.toString());
            imSourceData.setText(ai_text==null?"":ai_text);
            //文章id有则更新，无则插入
            ImSourceDataExample example=new ImSourceDataExample();
            example.createCriteria().andArticleidEqualTo(String.valueOf(news_id));
            List<ImSourceData> imSourceDatas = imSourceDataMapper.selectByExample(example);
            if (imSourceDatas.size()>0){
                ImSourceData imSourceData1 = imSourceDatas.get(0);
                imSourceData.setAutoincre(imSourceData1.getAutoincre());
                int i = imSourceDataMapper.updateByPrimaryKeySelective(imSourceData);
                if (i== 1){
                    log.info("更新成功");
                }else{
                    log.info("更新失败");
                }
            }else{
                int i = imSourceDataMapper.insertSelective(imSourceData);
                if (i== 1){
                    log.info("入库成功");
                }else{
                    log.info("入库失败");
                }
            }
            results.put("createTime",formattedDate);
            results.put("articleId",news_id);
            results.put("title",title);
            results.put("keywords",keywords);
            results.put("text",ai_text);
            results.put("rank",j+1);
            result.add(results);
        }
        return result;
    }

    private String unixTimeToDate(long createTime) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(createTime), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        return formattedDate;
    }

    private List insertIntoDB(IntelligentMarkingBO sourceData) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result=new ArrayList();
        List<Data> data = sourceData.getData();
        for (int j = 0; j<data.size() ; j++) {
            Map results=new HashMap<>();
            Data datum = data.get(j);
            long article_id = datum.getArticle_id();
            int pos = datum.getPos();
            boolean is_stick = datum.getIs_stick();
            int special = datum.getSpecial();
            String title = datum.getTitle();
            String list_title = datum.getList_title();
            String sub_title = datum.getSub_title();
            String short_title = datum.getShort_title();
            String reprint_title = datum.getReprint_title();
            String ori_source = datum.getOri_source();
            boolean is_feature = datum.getIs_feature();
            String share_url = datum.getShare_url();
            String share_image = datum.getShare_image();
            String share_title = datum.getShare_title();
            String share_digest = datum.getShare_digest();
            String digest = datum.getDigest();
            String share_digest_for_pic = datum.getShare_digest_for_pic();
            long published_at = datum.getPublished_at();
            String type = datum.getType();
            String mobile_click_count = datum.getMobile_click_count();
            boolean is_rolling_news = datum.getIs_rolling_news();
            boolean audio_play = datum.getAudio_play();
            int share_to_pic_with_color = datum.getShare_to_pic_with_color();
            String article_plain_text = datum.getArticle_plain_text();
            List<Tag> tag = datum.getTag();
            Access_control access_control = datum.getAccess_control();
            List_show_control list_show_control = datum.getList_show_control();
            Thumbnails thumbnails = datum.getThumbnails();
            String thumbnail_copyright = datum.getThumbnail_copyright();
            ImSourceData imSourceData=new ImSourceData();
            imSourceData.setFrom("每日经济新闻");
            imSourceData.setTitle(title);
            imSourceData.setLink(share_url);
            imSourceData.setTag(tag.toString());
            imSourceData.setReadingamount(dealMobileClickCount(mobile_click_count));
            //这里暂时按照手机点击次数来
            imSourceData.setCommentamount(dealMobileClickCount(mobile_click_count));
            //这里暂时按照手机点击次数来
            imSourceData.setLikes(dealMobileClickCount(mobile_click_count));
            imSourceData.setBelongs(ori_source);
            imSourceData.setArticleid(String.valueOf(article_id));
            imSourceData.setSubmittime(sf.format(new Date()));
            imSourceData.setRank(j);
            //解析关键词
            List<String> keywords = AnsjUtils.getKeyword(datum);
            imSourceData.setKeywords(keywords.toString());
            imSourceData.setText(StringUtils.isEmpty(article_plain_text)?share_digest:article_plain_text);
            //文章id有则更新，无则插入
            ImSourceDataExample example=new ImSourceDataExample();
            example.createCriteria().andArticleidEqualTo(String.valueOf(article_id));
            List<ImSourceData> imSourceDatas = imSourceDataMapper.selectByExample(example);
            if (imSourceDatas.size()>0){
                ImSourceData imSourceData1 = imSourceDatas.get(0);
                imSourceData.setAutoincre(imSourceData1.getAutoincre());
                int i = imSourceDataMapper.updateByPrimaryKeySelective(imSourceData);
                if (i== 1){
                    log.info("更新成功");
                }else{
                    log.info("更新失败");
                }
            }else{
                int i = imSourceDataMapper.insertSelective(imSourceData);
                if (i== 1){
                    log.info("入库成功");
                }else{
                    log.info("入库失败");
                }
            }
            results.put("title",title);
            results.put("keywords",keywords);
            results.put("article_id",String.valueOf(article_id));
            results.put("rank",j+1);
            results.put("text", StringUtils.isEmpty(article_plain_text)?share_digest:article_plain_text);
            result.add(results);
        }
        return result;
    }

    private Integer dealMobileClickCount(String mobileClickCount) {
        String substring = mobileClickCount.substring(mobileClickCount.length() - 1);
        String num = mobileClickCount.substring(0, mobileClickCount.length() - 1);
        switch (substring)
        {
            case "W":
                return (int) (Double.valueOf(num)*10000);
             case "K":
                return (int) (Double.valueOf(num)*1000);
            default:
                return (Integer.valueOf(num));
        }
    }

    private void dealImg(ImSourceData imSourceData, String img_url, String img_title, String img_copyright, String img_width, String img_height, String img_size) {
        if (img_url != null && !img_url.equals("")) {   }
}
}
