package com.intelligent.marking.intelligentmarking.spider;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.intelligent.marking.intelligentmarking.entity.News;
import com.intelligent.marking.intelligentmarking.entity.NewsExample;
import com.intelligent.marking.intelligentmarking.mapper.NewsMapper;
import com.intelligent.marking.intelligentmarking.service.NewsPuller;
import com.intelligent.marking.intelligentmarking.service.NewsService;
import com.intelligent.marking.intelligentmarking.utils.IBatchDAO;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网易新闻抓取。
 *
 * 网易的一级菜单和二级菜单的关系。例如：一级菜单“时尚”，二级菜单“亲子”、“艺术”。
 *
 * <ul class="ntes-quicknav-column ntes-quicknav-column-7">
 *   <li>
 *     <h3>
 *       <a href="https://fashion.163.com">时尚</a>
 *     </h3>
 *   </li>
 *   <li>
 *     <a href="https://baby.163.com">亲子</a>
 *   </li>
 *   <li>
 *     <a href="https://fashion.163.com/art">艺术</a>
 *   </li>
 * </ul>
 *
 */
@Component("netEaseNewsPuller")
public class NetEaseNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(NetEaseNewsPuller.class);

    /**
     * 年月日时分秒，24小时制。
     * 例如：2024-05-17 15:30:21
     */
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 分隔符横杠
     */
    private static final String SEPARATOR_HORIZONTAL_BAR = "-";

    /**
     * 网易首页url
     */
    @Value("${news.netease.url}")
    private String url;
    @Autowired
    private NewsService newsService;
    @Autowired
    private IBatchDAO iBatchDAO;
    @Autowired
    NewsMapper newsMapper;

    /**
     * 获取首页
     * @param url 首页url
     * @return document 首页html文档
     */
    public Document fetchHomePage(String url) {
        Document document = null;
        try {
            document = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("获取网易首页失败，url是{}", url, e);
        }
        return document;
    }

    /**
     * 获取一级菜单元素集合
     * @param document 首页html文档
     * @return 一级菜单元素集合
     */
    public Elements firstLevelMenuElements(Document document) {
        return document.select("div.ntes-quicknav-content")
                .select("ul.ntes-quicknav-column")
                .select("li:first-child");
    }

    /**
     * 获取二级菜单元素集合
     * @param document 首页html文档
     * @return 二级菜单元素集合
     */
    public Elements secondLevelMenuElements(Document document) {
        return document.select("div.ntes-quicknav-content")
                .select("ul.ntes-quicknav-column")
                .select("li>a");
    }

    /**
     * 获取一、二级菜单元素集合
     * @param document 首页html文档
     * @return 一、二级菜单元素集合
     */
    public Elements firstAndSecondMenuElements(Document document) {
        return document.select("div.ntes-quicknav-content")
                .select("ul.ntes-quicknav-column");
    }

    /**
     * 一级菜单元素集合组成News集合。
     * @param elements 一级菜单元素集合
     * @param newstype1Set News集合
     */
    public void firstLevelMenuElementsToNews(Elements elements, HashSet<News> newstype1Set, Date date) {
        for (Element element : elements) {
            //新闻内容一级分类
            String newstype1 = element.text();
            //内容分类网页链接
            String href = element.select("a").attr("href");
            News news = new News();
            news.setSource("网易");
            news.setUrl(href);
            news.setNewstype1(newstype1);
            news.setCreateDate(date);
            newstype1Set.add(news);
        }
    }

    /**
     * 根据一、二级菜单元素集合组成News集合。
     * @param firstAndSecondMenuElements 一、二级菜单元素集合
     * @param newstype2Set 二级菜单News集合
     */
    public void secondLevelMenuElementsToNews(Elements firstAndSecondMenuElements, HashSet<News> newstype2Set, Date date) {
        for (Element element : firstAndSecondMenuElements) {
            //新闻内容一级分类
            String newstype1 = element.select("li:first-child").get(0).text();
            element.select("li>a").forEach(
                liEle -> {
                    News news = new News();
                    news.setSource("网易");
                    String href = liEle.attr("href");//内容分类网页链接
                    news.setUrl(href);
                    news.setNewstype1(newstype1);
                    String newstype2 = liEle.text();
                    news.setNewstype2(newstype2);
                    news.setCreateDate(date);
                    newstype2Set.add(news);
                }
            );
        }
    }

    /**
     * 根据一级菜单元素集合的News集合组成二级菜单元素集合的News集合。
     * @param newstype1Set 一级菜单元素集合的News集合
     * @param newstype2Set 二级菜单元素集合的News集合
     */
    public void secondLevelMenuElementsToNews(HashSet<News> newstype1Set, HashSet<News> newstype2Set) {
        newstype1Set.forEach(news -> {
            logger.info("开始遍历一级分类：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Elements newsType2Elements = newsHtml.select("div.nav_container");
                Elements aElements = newsType2Elements.select("a");
                for (Element eduLink : aElements) {
                    String href = eduLink.attr("href");
                    String newstype2 = eduLink.text();
                    //二级分类
                    logger.info("开始查找二级分类：" + newstype2);
                    News news2 = new News();
                    BeanUtils.copyProperties(news, news2);
                    news2.setUrl(href);
                    news2.setNewstype2(newstype2);
                    newstype2Set.add(news2);
                    logger.info("二级分类放到集合中完成");
                }
            } catch (Exception e) {
                logger.error("遍历一级分类过程中失败！链接：{}", news.getUrl(), e);
            }
        });
    }

    /**
     * 根据二级菜单的名称返回进入二级菜单页面里面（新闻）列表的css样式。
     * @param firstType 类别，此处指一级菜单。
     * @param secondType 类别，此处指二级菜单。
     * @return 二级菜单进入的页面，里面（新闻）列表的css样式。
     */
    public Map<String, String> getQuery(String firstType, String secondType) {
        Map<String, String> cssMap = new HashMap<>();
        String fetchType = "css";//默认css，还有javascript。代表获取数据的方式。
        String itemCssQuery = "";
        // TODO 内容是视频类型的待处理。详情页的时间和内容的处理。
        String detailTimeCssQuery = "";
        String detailContentCssQuery = "";
        String cssQueryDivHidden = "div.hidden>div>a";
        String cssQueryDivTitleBar = "ul.newsList>li>div.titleBar>h3>a";
        String cssQueryDivPostInfo = "div.post_info";
        String cssQueryDivPostBody = "div.post_body";
        String itemJavascriptQuery = "";//javascript文件
        String type = firstType + SEPARATOR_HORIZONTAL_BAR + secondType;
        String needDecode = "false";
        switch (type) {
            // 一级分类《新闻》开始
            case "新闻-国内":
            case "新闻-国际":
            case "新闻-军事":
                itemCssQuery = cssQueryDivHidden;
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "新闻-评论"://目前是跳转到网易首页了，暂时没有对于的具体二级页面。
            case "新闻-王三三"://目前不是抓取目标。
                itemCssQuery = "";
                break;
            // 一级分类《新闻》结束
            // 一级分类《体育》开始
            case "体育-NBA":
                itemCssQuery = cssQueryDivHidden;
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "体育-CBA":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://sports.163.com/special/000587PL/newsdata_cba_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "体育-综合":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://sports.163.com/special/000587PQ/newsdata_allsports_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                needDecode = "true";
                break;
            case "体育-中超":
                itemCssQuery = "div.new_list>div>h3>a";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "体育-国际足球":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://sports.163.com/special/000587PN/newsdata_world_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                needDecode = "true";
                break;
            case "体育-英超":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://sports.163.com/special/000587PO/newsdata_epl_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                needDecode = "true";
                break;
            case "体育-西甲":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://sports.163.com/special/000587PP/newsdata_laliga_index.js?callback=data_callback&charset=gbk";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                needDecode = "true";
                break;
            case "体育-意甲":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://sports.163.com/special/000587PN/newsdata_world_yj.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                needDecode = "true";
                break;
            // 一级分类《体育》结束
            // 一级分类《娱乐》开始
            case "娱乐-明星":
                itemCssQuery = "div.item-Text>h2>a";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "娱乐-图片":
                itemCssQuery = "div.water-item>ul>li>a";
                detailTimeCssQuery = "div.headline>span";
                detailContentCssQuery = "div.overview";
                break;
            case "娱乐-电影":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://ent.163.com/special/000381Q1/newsdata_movieidx.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "娱乐-电视":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://ent.163.com/special/000381P3/newsdata_tv_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "娱乐-音乐":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://news.163.com/special/0001A1RO/music2022_datalist_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "娱乐-稿事编辑部":
                itemCssQuery = "div.main>div.warp>ul>li";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "娱乐-娱乐FOCUS":
                itemCssQuery = "div.mod_list>ul>li";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            // 一级分类《娱乐》结束
            // 一级分类《财经》开始
            case "财经-股票":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://money.163.com/special/00259K2L/data_stock_redian.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "财经-行情":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://money.163.com/special/00259BVP/news_flow_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "财经-新股":
                itemCssQuery = "div.news_list_container>div";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "财经-金融":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://money.163.com/special/00259BVP/news_flow_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "财经-基金":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://money.163.com/special/00259D2D/fund_newsflow_hot.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "财经-商业":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://money.163.com/special/002557RF/data_idx_shangye.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "财经-理财":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://money.163.com/special/00259BVP/news_flow_index.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            // 一级分类《财经》结束
            // 一级分类《汽车》开始
            case "汽车-购车":
                itemCssQuery = "div.sec-list>div>div";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "汽车-行情":
                itemCssQuery = "div.m-content>ul>li>a";
                detailTimeCssQuery = "";//没有找到时间
                detailContentCssQuery = "div.dealer-prefer-reason";
                break;
            case "汽车-车型库":
                itemCssQuery = "ul.product-list>li";
                detailTimeCssQuery = "";//没有找到时间
                detailContentCssQuery = "";//没有适合的主内容
                break;
            case "汽车-新能源":
                itemCssQuery = "div.sec-list>div>div";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "汽车-行业":
                itemCssQuery = "div.sec-list>div>div";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            // 一级分类《汽车》结束
            // 一级分类《科技》开始
            case "科技-通信":
            case "科技-IT":
            case "科技-互联网":
                itemCssQuery = cssQueryDivTitleBar;
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "科技-特别策划"://打不开页面
                itemCssQuery = "";
                break;
            case "科技-网易智能":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://tech.163.com/special/00097UHL/smart_datalist.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "科技-家电":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://digi.163.com/special/0016980K/home_appliance_datalist.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            // 一级分类《科技》结束
            // 一级分类《时尚》开始
            case "时尚-亲子":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://baby.163.com/special/003687OS/newsdata_hot.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "时尚-艺术":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://art.163.com/special/00999815/art_redian_api.js?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                needDecode = "true";
                break;
            // 一级分类《时尚》结束
            // 一级分类《手机 / 数码》开始
            case "手机 / 数码-移动互联网"://打不开页面
                itemCssQuery = "";
                break;
            case "手机 / 数码-惊奇科技":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://mobile.163.com/special/index_datalist_jqkj/?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "手机 / 数码-易评机":
                fetchType = "javascript";
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemJavascriptQuery = "https://mobile.163.com/special/index_datalist_cpsh/?callback=data_callback";
                itemCssQuery = "";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            // 一级分类《手机 / 数码》结束
            // 一级分类《房产 / 家居》开始
            case "房产 / 家居-北京房产":
            case "房产 / 家居-上海房产":
            case "房产 / 家居-广州房产":
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemCssQuery = "";
                break;
            case "房产 / 家居-全部分站"://页面404
                itemCssQuery = "";
                break;
            case "房产 / 家居-楼盘库":
                itemCssQuery="div.list-box>ul>li";
                detailTimeCssQuery = "";//没有找到时间
                detailContentCssQuery = "div.baseInfo";
                break;
            case "房产 / 家居-家具":
                itemCssQuery="div#subp_news_list>div";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            case "房产 / 家居-卫浴":
                itemCssQuery="div#subp_news_list>div";
                detailTimeCssQuery = cssQueryDivPostInfo;
                detailContentCssQuery = cssQueryDivPostBody;
                break;
            // 一级分类《房产 / 家居》结束
            // 一级分类《旅游》开始
            case "旅游-自驾露营":
            case "旅游-美食":
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemCssQuery = "";
                break;
            // 一级分类《旅游》结束
            // 一级分类《教育》开始
            case "教育-移民":
            case "教育-留学":
            case "教育-外语":
            case "教育-高考":
//                itemCssQuery = "div.ndi_main>div>a";//抓取的页面上只有动态数据，目前不抓取这样的。
                itemCssQuery = "";
                break;
            // 一级分类《教育》结束
            default:
                logger.warn("传入了未知分类：{}，请检查！", type);
        }
        cssMap.put("fetchType", fetchType);
        cssMap.put("itemJavascriptQuery", itemJavascriptQuery);
        cssMap.put("itemCssQuery", itemCssQuery);
        cssMap.put("detailContentCssQuery", detailContentCssQuery);
        cssMap.put("detailTimeCssQuery", detailTimeCssQuery);
        cssMap.put("needDecode", needDecode);
        return cssMap;
    }

    /**
     * 遍历二级News集合，获取News摘要（链接、标题），组成News集合。
     * @param newstype2Set 二级News集合
     * @param newsSet News集合
     */
    public void fetchNewsDescribeToNewsSet(HashSet<News> newstype2Set, HashSet<News> newsSet) {
        for (News news: newstype2Set) {
            //根据新闻一级分类分别制定不同的爬取方式
            String firstType = news.getNewstype1();
            String scondType = news.getNewstype2();
            String scondUrl = news.getUrl();
            logger.info("开始遍历二级分类：{}", scondType);
            logger.info("开始遍历{},二级分类url：{}", scondType, scondUrl);

            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(scondUrl.startsWith("http") ? scondUrl : url + scondUrl, false);
                String fetchType = getQuery(firstType, scondType).get("fetchType");
                if (fetchType.equals("css")) {
                    if (getItemByCss(newsSet, news, firstType, scondType, newsHtml, scondUrl)) continue;
                } else if (fetchType.equals("javascript")) {
                    String needDecode = getQuery(firstType, scondType).get("needDecode");
                    if (getItemByJavascript(newsSet, news, firstType, scondType, newsHtml, scondUrl, needDecode)) continue;
                } else {
                    logger.error("未知的获取每条新闻的方式，即css、javascript之外的方式：{}", fetchType);
                }
                logger.info("新闻链接放入集合中成功！二级分类：{}，链接：{}", scondType, scondUrl);
            } catch (Exception e) {
                logger.error("新闻链接放入集合中失败！二级分类：{}，链接：{}", scondType, scondUrl, e);
            }
        }
    }

    /**
     * 通过css的方式获取每条新闻，并存放到集合里。
     * @param newsSet 获取到的新闻存放的集合。
     * @param news 二级分类
     * @param firstType 一级分类类型
     * @param scondType 二级分类类型
     * @param newsHtml 二级分类页面
     * @param scondUrl 二级分类url
     * @return
     */
    private boolean getItemByCss(HashSet<News> newsSet, News news, String firstType, String scondType, Document newsHtml, String scondUrl) {
        String itemCssQuery = getQuery(firstType, scondType).get("itemCssQuery");
        if (itemCssQuery.isEmpty()) {
            logger.warn("二级分类《{}》没有对应的css样式，请检查方法getQuery。", scondType);
            return true;
        }
        Elements newsElements = newsHtml.select(itemCssQuery);
        for (Element item : newsElements) {
            // 获取href属性，需要注意的是href属性值可能是相对路径，需要根据实际情况转换为完整URL
            String href = item.attr("href");
            // 补全相对URL（如果需要）
            if (!href.startsWith("http")) {
                href = url + href;
            }
            // 获取标题文本
            String title = item.text();
            // 获取图片src属性
            String imgSrc = item.select(".img img").attr("src");
            NewsExample newsExample = new NewsExample();
            newsExample.createCriteria().andUrlEqualTo(href);
            List<News> hrefList = newsMapper.selectByExample(newsExample);
            NewsExample newsExample2 = new NewsExample();
            newsExample2.createCriteria().andTitleEqualTo(title);
            List<News> titleList = newsMapper.selectByExample(newsExample);
            // 检查从数据库里面查询的结果，是否有重复的新闻（链接是否重复，标题是否重复）。
            if (hrefList.size() > 0 || titleList.size() > 0) {
                logger.info("新闻链接放入集合时，判断数据库中，出现重复！二级分类：{}，标题：{}，链接：{}", scondType, title, href);
                continue;
            }
            // 检查正在抓取的新闻里面，是否有重复的新闻（链接是否重复，标题是否重复）。
            int count = 0;
            for (News news1 : newsSet) {
                if (news1.getUrl().equals(href) || news1.getTitle().equals(title)) {
                    count++;
                }
            }
            if (count > 0) {
                logger.info("新闻链接放入集合时，现有集合中，出现重复！二级分类：{}，标题：{}，链接：{}", scondType, title, href);
                continue;
            }
            News news3 = new News();
            BeanUtils.copyProperties(news, news3);
            news3.setUrl(href);
            news3.setTitle(title);
            news3.setImage(imgSrc);
            newsSet.add(news3);
            logger.info("新闻链接放入集合中成功！二级分类：{}，标题：{}，链接：{}", scondType, title, scondUrl);
        }
        return false;
    }

    /**
     * 通过javascript的方式获取每条新闻，并存放到集合里。
     * @param newsSet 获取到的新闻存放的集合。
     * @param news 二级分类
     * @param firstType 一级分类类型
     * @param scondType 二级分类类型
     * @param newsHtml 二级分类页面
     * @param needDecode 是否需要解码（Unicode转换中文）
     * @return
     */
    private boolean getItemByJavascript(HashSet<News> newsSet, News news, String firstType, String scondType,
                                        Document newsHtml, String scondUrl, String needDecode) {
        String itemJavascriptQuery = getQuery(firstType, scondType).get("itemJavascriptQuery");
        if (itemJavascriptQuery.isEmpty()) {
            logger.warn("二级分类《{}》没有对应的javascript，请检查方法getQuery。", scondType);
            return true;
        }
        String content = getFileContent(itemJavascriptQuery);
        // 把前后多余的部分去掉，前面的“data_callback(”，后面的“)”。
        String arrayStr = content.substring(14, content.length()-1);
        JSONArray jsonArray = JSONArray.parseArray(arrayStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // 获取新闻链接，需要注意的是href属性值可能是相对路径，需要根据实际情况转换为完整URL
            String href = jsonObject.getString("docurl");
            // 补全相对URL（如果需要）
            if (!href.startsWith("http")) {
                href = url + href;
            }
            // 获取标题文本
            String title = jsonObject.getString("title");
            if (needDecode.equals("true")) {
                try {
                    title = URLDecoder.decode(title, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("Unicode转换中文，出现异常。Unicode文本：{}", title, e);
                }
            }
            // 获取图片链接
            String imgSrc = jsonObject.getString("imgurl");
            NewsExample newsExample = new NewsExample();
            newsExample.createCriteria().andUrlEqualTo(href);
            List<News> hrefList = newsMapper.selectByExample(newsExample);
            NewsExample newsExample2 = new NewsExample();
            newsExample2.createCriteria().andTitleEqualTo(title);
            List<News> titleList = newsMapper.selectByExample(newsExample);
            // 检查从数据库里面查询的结果，是否有重复的新闻（链接是否重复，标题是否重复）。
            if (hrefList.size() > 0 || titleList.size() > 0) {
                logger.info("新闻链接放入集合时，判断数据库中，出现重复！二级分类：{}，标题：{}，链接：{}", scondType, title, href);
                continue;
            }
            // 检查正在抓取的新闻里面，是否有重复的新闻（链接是否重复，标题是否重复）。
            int count = 0;
            for (News news1 : newsSet) {
                if (news1.getUrl().equals(href) || news1.getTitle().equals(title)) {
                    count++;
                }
            }
            if (count > 0) {
                logger.info("新闻链接放入集合时，现有集合中，出现重复！二级分类：{}，标题：{}，链接：{}", scondType, title, href);
                continue;
            }
            News news3 = new News();
            BeanUtils.copyProperties(news, news3);
            news3.setUrl(href);
            news3.setTitle(title);
            news3.setImage(imgSrc);
            newsSet.add(news3);
            logger.info("新闻链接放入集合中成功！二级分类：{}，标题：{}，链接：{}", scondType, title, scondUrl);
        }
        return false;
    }

    /**
     * 读取文件内容。
     * @param filePath 文件路径
     * @return
     */
    private static String getFileContent(String filePath) {
        BufferedReader bufferedReader = null;
        URL urlfile;
        StringBuilder content = new StringBuilder();
        String inputLine = "";
        try {
            urlfile = new URL(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(urlfile.openStream()));
            inputLine = bufferedReader.readLine();
            while  (inputLine != null )  {
                content.append(inputLine);
                inputLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.error("读取文件内容异常，文件：{}", filePath, e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ioException) {
                    logger.error("关闭流出现异常，文件：{}", filePath, ioException);
                }
            }
        }
        return content.toString();
    }

    /**
     * 遍历每个新闻详情链接，获取正文。
     * @param newsSet 新闻摘要集合
     * @param newsList 新闻详情集合
     */
    public void fetchNewsDetailToNewsList(HashSet<News> newsSet, List<News> newsList) {
        Date startTime = new Date();
        logger.info("抽取所有网易新闻正文，循环开始！时间：{}", sf.format(startTime));
        for (News news : newsSet) {
            Date startTimeSingle = new Date();
            logger.info("抽取网易新闻正文，单条开始时间：{}", sf.format(startTimeSingle));
            String timestr = sf.format(new Date());
            logger.info("一级分类：{}，二级分类：{}，开始根据新闻url访问新闻，获取新闻内容。新闻标题：{}，新闻url：{}",
                    news.getNewstype1(), news.getNewstype2(), news.getTitle(), news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                String firstType = news.getNewstype1();
                String scondType = news.getNewstype2();
                String detailContentCssQuery = getQuery(firstType, scondType).get("detailContentCssQuery");
                String detailTimeCssQuery = getQuery(firstType, scondType).get("detailTimeCssQuery");
                if (!detailTimeCssQuery.isEmpty()) {
                    Elements timeElements = newsHtml.select(detailTimeCssQuery);
                    // 正则表达式匹配yyyy-mm-dd HH:MM:SS格式的日期时间
                    String regex = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(timeElements.get(0).text());
                    while (matcher.find()) {
                        // 直接提取匹配到的日期时间字符串
                        String timeString = matcher.group(1).toString();
                        timestr = timeString.length() == 14 ? timeString + ":00" : timeString;
                    }
                    news.setNewsDate(sf.parse(timestr));
                }
                News news4 = new News();
                Elements articles = newsHtml.select(detailContentCssQuery);
                for (Element article : articles) {
                    // 提取<article>元素内所有<p>标签
                    Elements paragraphs = article.select("p");
                    StringBuffer stringBuffer = new StringBuffer();
                    // 遍历所有<p>标签
                    for (Element paragraph : paragraphs) {
                        // 获取并打印<p>标签的文本内容
                        stringBuffer.append(paragraph.text());
                    }
                    BeanUtils.copyProperties(news, news4);
                    news4.setContent(stringBuffer.toString());
                }
                NewsExample newsExample = new NewsExample();
                newsExample.createCriteria().andUrlEqualTo(news4.getUrl());
                List<News> list = newsMapper.selectByExample(newsExample);
                NewsExample newsExample2 = new NewsExample();
                newsExample2.createCriteria().andTitleEqualTo(news4.getTitle());
                List<News> list2 = newsMapper.selectByExample(newsExample);
                if (list.size() == 0 && list2.size() == 0) {
                    newsList.add(news4);
                    logger.info("一级分类：{}，二级分类：{}，抽取网易新闻《{}》成功！",
                            news.getNewstype1(), news.getNewstype2(), news.getTitle());
                } else {
                    logger.warn("一级分类：{}，二级分类：{}，抽取网易新闻《{}》重复！",
                            news.getNewstype1(), news.getNewstype2(), news.getTitle());
                }
            } catch (Exception e) {
                logger.error("一级分类：{}，二级分类：{}，新闻《{}》抽取失败！链接：{}",
                        news.getNewstype1(), news.getNewstype2(), news.getTitle(), news.getUrl(), e);
            }
            logger.info("一级分类：{}，二级分类：{}，抽取网易新闻《{}》正文，单条耗时：{}毫秒",
                    news.getNewstype1(), news.getNewstype2(), news.getTitle(), System.currentTimeMillis() - startTimeSingle.getTime());
        }
        Date endTime = new Date();
        logger.info("抽取网易新闻正文，循环结束！时间：{}", sf.format(endTime));
        logger.info("抽取网易新闻正文，共耗时：{}毫秒", endTime.getTime() - startTime.getTime());
    }

    /**
     * 拉取新闻。
     */
    @Override
    public void pullNews() {
        logger.info("开始拉取网易新闻！");
        Date startTime = new Date();
        logger.info("开始拉取网易新闻！开始时间：{}", sf.format(startTime));
        // 1.获取首页
        Document document = fetchHomePage(url);
        if (document == null) {
            logger.error("获取网易首页失败。");
            return;
        }

        // 2.jsoup获取一、二级菜单元素集合
        Elements firstAndSecondMenuElements = firstAndSecondMenuElements(document);
        if (firstAndSecondMenuElements.isEmpty()) {
            logger.error("获取一、二级菜单元素集合失败。");
            return;
        }
        // 3.根据首页导航去遍历二级菜单存储到集合newstype2Set中
        HashSet<News> newstype2Set = new HashSet<>();
        secondLevelMenuElementsToNews(firstAndSecondMenuElements, newstype2Set, startTime);
        if (newstype2Set.isEmpty()) {
            logger.error("获取二级菜单元素集合失败。");
            return;
        }

        // 4.遍历二级分类，获取新闻摘要的链接和正文，组成News集合。
        HashSet<News> newsSet = new HashSet<>();
        fetchNewsDescribeToNewsSet(newstype2Set, newsSet);
        if (newsSet.isEmpty()) {
            logger.error("获取二级菜单元素集合的新闻摘要失败。");
            return;
        }

        // 5.便利每个新闻详情链接，获取正文。
        List<News> newsList = new ArrayList<>();
        fetchNewsDetailToNewsList(newsSet, newsList);
        if (newsList.isEmpty()) {
            logger.error("获取二级菜单元素集合的新闻详情失败。");
            return;
        }

        iBatchDAO.saveBatch(NewsMapper.class, "insertSelective", newsList, 1000);

        Date endTime = new Date();
        logger.info("结束拉取网易新闻！");
        logger.info("结束拉取网易新闻！结束时间：{}", sf.format(endTime));
        logger.info("结束拉取网易新闻！共耗时间：{}毫秒", endTime.getTime() - startTime.getTime());
    }

}
