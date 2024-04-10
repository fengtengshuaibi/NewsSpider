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

/**
 * 网易财经-宏观新闻(国际新闻算作了宏观新闻)
 */
@Component("netEasyNationalNewsPuller")
public class NetEasyNationalNewsPuller implements NewsPuller {
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(NetEasyNationalNewsPuller.class);
    @Autowired
    NewsMapper newsMapper;
    @Value("${news.neteasy.nationurl}")
    private String url;
    @Autowired
    private NewsService newsService;
    @Autowired
    private IBatchDAO iBatchDAO;

    @Override
    public void pullNews() {
        HashSet<News> newsType1Set = new HashSet();
        HashSet<News> newsType2Set = new HashSet();
        HashSet<News> newsSet = new HashSet();
        List<News> newsList = new ArrayList<>();
        logger.info("开始拉取网易财经-国际新闻！");
        // 1.获取首页
        Document html = null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============拉取网易财经-国际新闻失败: {}=============", url);
            e.printStackTrace();
            return;
        }
        Elements newsElements = html.select("div.col_l").select("div.item_top h2 a");
        if (newsElements != null) {
            for (Element newsElement : newsElements) {
                //获取新闻链接
                String href = newsElement.attr("href");
                //获取新闻标题
                String title = newsElement.text();
                News news = new News();
                news.setSource("网易");
                news.setTitle(title);
                news.setUrl(href);
                news.setNewstype1("财经");
                news.setNewstype2("宏观");
                news.setCreateDate(new Date());
                newsType1Set.add(news);
            }
        }
        // 4.根据url访问新闻，获取新闻内容
        if (newsType1Set.size() > 0) {
            newsType1Set.forEach(news -> {
                logger.info("开始抽取新闻内容：{}", news.getUrl());
                Document newsHtml = null;
                try {
                    newsHtml = getHtmlFromUrl(news.getUrl(), false);
                    //获取主体内容
                    // 获取时间
                    Element postInfoElement = newsHtml.select("div.post_info").first();
                    String time = postInfoElement.text().substring(0, 19);
                    // 获取图片URL
                    Elements images = newsHtml.select("div.post_body img");
                    String imageUrl = "";
                    if (!images.isEmpty()) {
                        imageUrl = images.first().attr("src");
                    }
                    // 获取正文
                    Elements paragraphs = newsHtml.select("div.post_body p");
                    StringBuffer content = new StringBuffer();
                    for (Element paragraph : paragraphs) {
                        content.append(paragraph.text()).append("\n");
                    }
                    News news1 = new News();
                    BeanUtils.copyProperties(news, news1);
                    news1.setNewsDate(sf.parse(time));
                    news1.setImage(imageUrl);
                    news1.setContent(content.toString());
                    NewsExample newsExample = new NewsExample();
                    newsExample.createCriteria().andUrlEqualTo(news1.getUrl());
                    List<News> list = newsMapper.selectByExample(newsExample);
                    NewsExample newsExample2 = new NewsExample();
                    newsExample2.createCriteria().andTitleEqualTo(news1.getTitle());
                    List<News> list2 = newsMapper.selectByExample(newsExample);
                    if (list.size() == 0 && list2.size() == 0) {
                        newsList.add(news1);
                    }
                    logger.info("抽取网易国际新闻《{}》成功！", news.getTitle());
                } catch (Exception e) {
                    logger.error("网易国际新闻抽取失败:{}", news.getUrl());
                    e.printStackTrace();
                }
            });
            logger.info("网易国际新闻拉取完成！");
        }


        if (newsList.size() > 0) {
            iBatchDAO.saveBatch(NewsMapper.class, "insertSelective", newsList, 1000);
        }

    }
}
