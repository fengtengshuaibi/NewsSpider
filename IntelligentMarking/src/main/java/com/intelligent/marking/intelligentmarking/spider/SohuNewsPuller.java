package com.intelligent.marking.intelligentmarking.spider;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("sohuNewsPuller")
public class SohuNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(SohuNewsPuller.class);
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Value("${news.sohu.url}")
    private String url;
    @Autowired
    private NewsService newsService;
    @Autowired
    private IBatchDAO iBatchDAO;
    @Autowired
    NewsMapper newsMapper;

    @Override
    public void pullNews() {
        logger.info("开始拉取搜狐新闻！");
        // 1.获取首页
        Document html = null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", url);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html.select("div.navigation-top-wrapper")
                .select("li.navigation-head-list-li");
        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newstype1Set = new HashSet<>();
        HashSet<News> newstype2Set = new HashSet<>();
        HashSet<News> newsSet = new HashSet<>();
        List<News> newsList = new ArrayList<>();
        for (Element a : newsATags) {
            //新闻内容一级分类
            String newstype1 = a.childNode(0).childNode(0).toString();
            //内容分类网页链接
            String href = a.childNode(0).attr("href");
            News n = new News();
            n.setSource("搜狐");
            n.setUrl(href);
            n.setNewstype1(newstype1);
            n.setCreateDate(new Date());
            newstype1Set.add(n);
        }
        if (newstype1Set.size() > 0) {
            //4.根据一级分类去遍历二级链接存储到集合中
            newstype1Set.forEach(news -> {
                if ("教育".equals(news.getNewstype1())) {
                    logger.info("开始遍历一级分类：{}", news.getUrl());
                    Document newsHtml = null;
                    try {
                        newsHtml = getHtmlFromUrl(news.getUrl(), false);
                        Elements newsType2Elements = newsHtml.select("div.nav_container");
                        Elements aElements = newsType2Elements.select("a");
                        for (Element eduLink : aElements) {
                            if (eduLink != null) {
                                String href = eduLink.attr("href");
                                String newstype2 = eduLink.text();
                                if (!"首页".equals(newstype2) && !"更多".equals(newstype2)) {
                                    //二级分类
                                    logger.info("开始查找二级分类：" + newstype2);
                                    News news2 = new News();
                                    BeanUtils.copyProperties(news, news2);
                                    news2.setUrl(href);
                                    news2.setNewstype2(newstype2);
                                    newstype2Set.add(news2);
                                    logger.info("二级分类放到集合中完成");
                                }

                            } else {
                                System.out.println("未找到a标签");
                            }
                        }
//                        Element eduLink = newsType2Elements.select("a").first();

                    } catch (Exception e) {
                        logger.error("遍历一级分类过程中失败:{}", news.getUrl());
                        e.printStackTrace();
                    }
                } else if ("体育".equals(news.getNewstype1())) {
                    logger.info("开始遍历一级分类：{}", news.getUrl());
                    Document newsHtml = null;
                    try {
                        newsHtml = getHtmlFromUrl(news.getUrl(), false);
                        Elements newsType2Elements = newsHtml.select("div.nav_content_item");
                        Elements aElements = newsType2Elements.select("a");
                        for (Element eduLink : aElements) {
                            if (eduLink != null) {
                                String href = eduLink.attr("href");
                                String newstype2 = eduLink.text();
                                if (!"视频".equals(newstype2) && !"更多".equals(newstype2) && !"冠军".equals(newstype2)) {
                                    //二级分类
                                    logger.info("开始查找二级分类：" + newstype2);
                                    News news2 = new News();
                                    BeanUtils.copyProperties(news, news2);
                                    news2.setUrl(href);
                                    news2.setNewstype2(newstype2);
                                    newstype2Set.add(news2);
                                    logger.info("二级分类放到集合中完成");
                                }

                            } else {
                                System.out.println("未找到a标签");
                            }
                        }
//                        Element eduLink = newsType2Elements.select("a").first();

                    } catch (Exception e) {
                        logger.error("遍历一级分类过程中失败:{}", news.getUrl());
                        e.printStackTrace();
                    }
                }
            });
        }
        if (newstype2Set.size() > 0) {
            // 5.遍历二级分类，获取新闻链接和正文
            newstype2Set.forEach(news -> {
                //根据新闻一级分类分别制定不同的爬取方式
                logger.info("开始遍历一级分类：{}", news.getNewstype1());
                logger.info("开始遍历{},二级分类：{}", news.getNewstype1(), news.getUrl());
                if ("教育".equals(news.getNewstype1())) {
                    Document newsHtml = null;
                    try {
                        newsHtml = getHtmlFromUrl(news.getUrl().startsWith("http") ? news.getUrl() : "https://www.sohu.com" + news.getUrl(), false);
                        Elements newsElements = newsHtml.select("div.side_item");
//                    int i=0;
                        for (Element item : newsElements) {
//                        if (i!=0){
                            // 获取href属性，需要注意的是href属性值可能是相对路径，需要根据实际情况转换为完整URL
                            String href = item.select("a").attr("href");
                            // 补全相对URL（如果需要）
                            if (!href.startsWith("http")) {
                                href = "https://www.sohu.com" + href;
                            }
                            // 获取标题文本
                            String title = item.select(".title").text();
                            // 获取图片src属性
                            String imgSrc = item.select(".img img").attr("src");
                            NewsExample newsExample = new NewsExample();
                            newsExample.createCriteria().andUrlEqualTo(href);
                            List<News> list = newsMapper.selectByExample(newsExample);
                            NewsExample newsExample2 = new NewsExample();
                            newsExample2.createCriteria().andTitleEqualTo(title);
                            List<News> list2 = newsMapper.selectByExample(newsExample);
                            int count = 0;
                            if (list.size() == 0 && list2.size() == 0) {
                                for (News news1 : newsSet) {
                                    if (news1.getUrl().equals(href)
                                            || news1.getTitle().equals(title)) {
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    News news3 = new News();
                                    BeanUtils.copyProperties(news, news3);
                                    news3.setUrl(href.replace("https://www.sohu.com//www.sohu.com", "https://www.sohu.com"));
                                    news3.setTitle(title);
                                    news3.setImage(imgSrc);
                                    newsSet.add(news3);
                                    logger.info("新闻链接放入集合中成功！", news.getTitle());
                                }
                            }
                        }
                        logger.info("新闻链接放入集合中成功！", news.getTitle());
                    } catch (Exception e) {
                        logger.error("新闻链接放入集合中失败:{}", news.getUrl());
                        e.printStackTrace();
                    }
                } else if ("体育".equals(news.getNewstype1())) {
                    if ("马术".equals(news.getNewstype2())) {
                        Document newsHtml = null;
                        try {
                            newsHtml = getHtmlFromUrl(news.getUrl(), false);
                            Elements newsElements = newsHtml.select("div.swiper-slide");
//                    int i=0;
                            for (Element item : newsElements) {
//                        if (i!=0){
                                // 获取href属性，需要注意的是href属性值可能是相对路径，需要根据实际情况转换为完整URL
                                String href = item.select("a").attr("href");
                                // 补全相对URL（如果需要）
                                if (!href.startsWith("http")) {
                                    href = "https://www.sohu.com" + href;
                                }
                                // 获取标题文本
                                String title = item.select("div.shadow-box").text();
                                // 获取图片src属性
                                String imgSrc = item.select(".img img").attr("src");
                                NewsExample newsExample = new NewsExample();
                                newsExample.createCriteria().andUrlEqualTo(href);
                                List<News> list = newsMapper.selectByExample(newsExample);
                                NewsExample newsExample2 = new NewsExample();
                                newsExample2.createCriteria().andTitleEqualTo(title);
                                List<News> list2 = newsMapper.selectByExample(newsExample);
                                int count = 0;
                                if (list.size() == 0 && list2.size() == 0) {
                                    for (News news1 : newsSet) {
                                        if (news1.getUrl().equals(href)
                                                || news1.getTitle().equals(title)) {
                                            count++;
                                        }
                                    }
                                    if (count == 0) {
                                        News news3 = new News();
                                        BeanUtils.copyProperties(news, news3);
                                        news3.setUrl(href.replace("https://www.sohu.com//www.sohu.com", "https://www.sohu.com"));
                                        news3.setTitle(title);
                                        news3.setImage(imgSrc);
                                        newsSet.add(news3);
                                        logger.info("新闻链接放入集合中成功！", news.getTitle());
                                    }
                                }
                            }
                            logger.info("新闻链接放入集合中成功！", news.getTitle());
                        } catch (Exception e) {
                            logger.error("新闻链接放入集合中失败:{}", news.getUrl());
                            e.printStackTrace();
                        }
                    }
                }


            });
        }
        if (newsSet.size() > 0) {
            // 6.便利每个新闻详情链接，获取正文
            newsSet.forEach(news -> {
                String timestr = sf.format(new Date());
                logger.info("开始根据新闻url访问新闻，获取新闻内容：{}", news.getUrl());
                Document newsHtml = null;
                try {
                    newsHtml = getHtmlFromUrl(news.getUrl(), false);
                    Elements articles = newsHtml.select("div.text").select("article.article");
                    Elements time = newsHtml.select("div.text").select("span.time");
                    // 正则表达式匹配yyyy-mm-dd HH:MM:SS格式的日期时间
                    String regex = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(time.get(0).text());
                    while (matcher.find()) {
                        // 直接提取匹配到的日期时间字符串
                        timestr = matcher.group(1).toString() + ":00";
                    }
                    news.setNewsDate(sf.parse(timestr));
                    News news4 = new News();
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
                    }
                    logger.info("抽取搜狐新闻《{}》成功！", news.getTitle());
                } catch (Exception e) {
                    logger.error("新闻抽取失败:{}", news.getUrl());
                    e.printStackTrace();
                }
            });
        }

        if (newsList.size() > 0) {
            iBatchDAO.saveBatch(NewsMapper.class, "insertSelective", newsList, 1000);
        }
    }
}
