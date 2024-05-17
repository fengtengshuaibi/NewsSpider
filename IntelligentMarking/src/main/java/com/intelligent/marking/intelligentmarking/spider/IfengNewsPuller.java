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
 * 凤凰财经新闻
 */
@Component("ifengNewsPuller")
public class IfengNewsPuller implements NewsPuller {
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(IfengNewsPuller.class);
    @Value("${news.ifeng.url}")
    private String url;
    @Autowired
    private NewsService newsService;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    private IBatchDAO iBatchDAO;
    @Override
    public void pullNews() {
        logger.info("开始拉取凤凰财经新闻！");
        List<News> newsList = new ArrayList<>();
        // 1.获取首页
        Document html= null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取凤凰首页失败: {} =============", url);
            e.printStackTrace();
            return;
        }
        HashSet<News> newsSet = new HashSet();
        // 2.jsoup获取新闻<a>标签，拿到新闻标题和链接
        Elements links = html.select("div.index_globalContent_pVCDM").select("a");
        for (Element link : links) {
            String href = link.attr("href");
            String titile = link.text();
            News news = new News();
            news.setCreateDate(new Date());
            news.setSource("凤凰网");
            news.setNewstype1("财经");
            news.setNewstype2("宏观");
            news.setUrl(href);
            news.setTitle(titile);
            newsSet.add(news);
        }
        // 3、遍历新闻链接去获取正文
        if (newsSet.size() > 0) {
            newsSet.parallelStream().forEach(news -> {
                logger.info("开始抽取凤凰财经新闻《{}》内容：{}", news.getTitle(), news.getUrl());
                Document newsHtml = null;
                try {
                    newsHtml = getHtmlFromUrl(news.getUrl(), false);
                    Elements imgElements = newsHtml.select("div.index_artical_-6O-z").select("img");
                    StringBuffer imgurl = new StringBuffer();
                    StringBuffer contentBuffer = new StringBuffer();
                    if (imgElements != null) {
                        for (Element imgElement : imgElements) {
                            imgurl.append(imgElement.attr("src")).append(",");
                        }
                    }
                    Elements contentElements = newsHtml.select("div.index_artical_-6O-z").select("p");
                    if (contentElements != null) {
                        for (Element contentElement : contentElements) {
                            contentBuffer.append(contentElement.text()).append("。");
                        }

                    }
                    News news1 = new News();
                    BeanUtils.copyProperties(news, news1);
                    Date newsDate = new Date();
                    Elements select = newsHtml.select("div.index_artical_-6O-z");
                    if (select != null && select.size() > 0) {
                        Elements select1 = select.select("div.index_timeBref_20hzr");
                        if (select1 != null && select1.size() > 0) {
                            Elements select2 = select1.select("a");
                            if (select2 != null && select2.size() > 0) {
                                newsDate = sf.parse(select2.text().substring(0, 20).replace("年", "-").replace("月", "-").replace("日", ""));
                            }
                        }

                    }
                    news1.setImage(imgurl.toString());
                    news1.setContent(contentBuffer.toString());
                    if (newsDate != null && !"".equals(newsDate)) {
                        news1.setNewsDate(newsDate);
                    } else {
                        news1.setNewsDate(new Date());
                    }
                    NewsExample example = new NewsExample();
                    example.createCriteria().andUrlEqualTo(news1.getUrl());
                    List<News> list1 = newsMapper.selectByExample(example);
                    NewsExample example1 = new NewsExample();
                    example1.createCriteria().andTitleEqualTo(news1.getTitle());
                    List<News> list2 = newsMapper.selectByExample(example);
                    if (list1.size() == 0 && list2.size() == 0) {
                        newsList.add(news1);
                    }
                    logger.info("抽取凤凰财经新闻《{}》成功！", news.getTitle());
                } catch (Exception e) {
                    logger.error("凤凰财经新闻抽取失败:{}", news.getUrl());
                    e.printStackTrace();
                }
            });
        }
        if (newsList.size() > 0) {
            iBatchDAO.saveBatch(NewsMapper.class, "insertSelective", newsList, 1000);
        }

        logger.info("凤凰财经新闻抽取完成！");
    }
}
