package com.intelligent.marking.intelligentmarking.spider;

import com.intelligent.marking.intelligentmarking.entity.News;
import com.intelligent.marking.intelligentmarking.entity.NewsExample;
import com.intelligent.marking.intelligentmarking.mapper.NewsMapper;
import com.intelligent.marking.intelligentmarking.service.NewsPuller;
import com.intelligent.marking.intelligentmarking.utils.IBatchDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("sinaNewsForexPuller")
public class SinaNewsForexPuller implements NewsPuller {
    private static final Logger logger = LogManager.getLogger(SinaNewsForexPuller.class);
    @Value("${news.sina.forexrul}")
    private String homeUrl;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    private IBatchDAO iBatchDAO;

    @Override
    public void pullNews() {
        List<News> newsList = new ArrayList<>();
        try {
            // 获取首页内容
            Document homeDoc = Jsoup.connect(homeUrl).get();
            logger.info("Fetched home page: {}", homeUrl);

            // 找到所有 <li> 标签
            Elements liTags = homeDoc.select("li");
            logger.info("Found {} <li> tags on home page", liTags.size());

            // 遍历 <li> 标签,获取其中的链接
            for (Element liTag : liTags) {
                Element link = liTag.selectFirst("a");
                if (link != null) {
                    String linkUrl = link.attr("href");
                    logger.debug("Found link: {}", linkUrl);

                    // 访问链接,获取标题、正文和时间
                    Document linkDoc = null;
                    try {
                        linkDoc = Jsoup.connect(linkUrl).get();
                        Element element = linkDoc.selectFirst("h1.main-title");
                        String text = element.text();
                    } catch (Exception e) {
                        continue;
                    }

                    String title = linkDoc.selectFirst("h1.main-title").text();
                    String content = linkDoc.selectFirst("div.article#artibody").text();
                    String dateText = linkDoc.selectFirst("span.date").text();
                    logger.info("Fetched article: title={}, content={}, date={}", title, content, dateText);

                    // 获取正文中的图片链接
                    Elements imgTags = linkDoc.select("div.article#artibody img");
                    StringBuilder sb = new StringBuilder();
                    for (Element imgTag : imgTags) {
                        String imgSrc = imgTag.attr("src");
                        sb.append(",").append(imgTags);
                        logger.debug("Found image: {}", imgSrc);
                    }
                    Date publishTime = parsePublishTime(dateText);


                    News news = new News();
                    news.setSource("新浪");
                    news.setTitle(title);
                    news.setUrl(linkUrl);
                    news.setNewstype1("财经");
                    news.setContent(content);
                    news.setNewstype2("外汇");
                    news.setCreateDate(new Date());
                    news.setImage(sb.toString());
                    news.setCreateDate(new Date());
                    news.setNewsDate(publishTime);

                    NewsExample example = new NewsExample();
                    example.createCriteria().andUrlEqualTo(news.getUrl());
                    List<News> list1 = newsMapper.selectByExample(example);
                    NewsExample example1 = new NewsExample();
                    example1.createCriteria().andTitleEqualTo(news.getTitle());
                    List<News> list2 = newsMapper.selectByExample(example);
                    if (list1.isEmpty() && list2.isEmpty()) {
                        newsList.add(news);
                    }

                }
            }
        } catch (IOException e) {
            logger.error("Error occurred while crawling: {}", e.getMessage(), e);
        }

        if (newsList.size() > 0) {
            iBatchDAO.saveBatch(NewsMapper.class, "insertSelective", newsList, 1000);
        }
    }
    private static Date parsePublishTime(String dateText) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            return formatter.parse(dateText);
        } catch (ParseException e) {
            logger.error("Error parsing publish time: {}", e.getMessage(), e);
            return null;
        }
    }
}