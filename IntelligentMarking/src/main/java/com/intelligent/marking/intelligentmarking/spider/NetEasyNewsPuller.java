package com.intelligent.marking.intelligentmarking.spider;

import com.intelligent.marking.intelligentmarking.entity.News;
import com.intelligent.marking.intelligentmarking.service.NewsPuller;
import com.intelligent.marking.intelligentmarking.service.NewsService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * 网易新闻
 */
@Component("netEasyNewsPuller")
public class NetEasyNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(NetEasyNewsPuller.class);
    @Value("${news.neteasy.url}")
    private String url;
    @Autowired
    private NewsService newsService;
    @Override
    public void pullNews() {
        HashSet<News> newsType1Set=new HashSet();
        HashSet<News> newsType2Set=new HashSet();
        HashSet<News> newsSet=new HashSet();
        logger.info("开始拉取网易热门新闻！");
        // 1.获取首页
        Document html= null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取网易新闻首页失败: {}=============", url);
            e.printStackTrace();
            return;
        }
        Elements newsType1Elements = html.select("div.bd").select("ul").select("li");
        for (Element newsType1Element : newsType1Elements) {
            String href = newsType1Element.select("a").attr("href");
            String newsType1 = newsType1Element.select("a").first().text();
            if (!"首页".equals(newsType1)&&!"王三三".equals(newsType1)){
                if (!href.contains("html")){
                    News news = new News();
                    news.setUrl(href);
                    news.setCreateDate(new Date());
                    news.setSource("网易");
                    news.setNewstype1(newsType1);
                    newsType1Set.add(news);
                }

            }

        }

        // 2.jsoup获取指定标签
//        Elements newsA = html.select("div#whole")
//                .next("div.area-half.left")
//                .select("div.tabContents")
////                .first()
//                .select("tbody > tr")
//                .select("a[href]");
//        Elements newsA = html.select("div#whole div.area-half.left div.tabBox div.tabContents.active tbody > tr > td > a");


//        // 3.从标签中抽取信息，封装成news
//        newsA.forEach(a -> {
//            String url = a.attr("href");
//            News n = new News();
//            n.setSource("网易");
//            n.setUrl(url);
//            n.setCreateDate(new Date());
//            newsSet.add(n);
//        });
        // 4.根据url访问新闻，获取新闻内容
        if (newsType1Set.size()> 0){
            newsType1Set.forEach(news -> {
                logger.info("开始抽取新闻内容：{}", news.getUrl());
                Document newsHtml = null;
                try {
                    newsHtml = getHtmlFromUrl(news.getUrl(), false);
                    //获取主体内容
                    Elements newsType2Elements = newsHtml.select("div.hidden").select("a");
                    Elements post_main = newsHtml.select("div.hidden");
                    StringBuffer contentsb= new StringBuffer();
                    Elements contentelements = post_main.select("div.post_body > p");
                    for (Element contentelement : contentelements) {
                        contentsb.append(contentelement.childNode(0).toString()).append("\n");
                    }
                    Elements titleP = post_main.select("h1.post_title");
                    String title = titleP.text();
                    String image = com.fly.blog.util.NewsUtils.getImageFromContent(post_main.toString());
                    news.setTitle(title);
                    news.setContent(contentsb.toString());
                    news.setImage(image);
                    Elements select = post_main.select("div.post_info");
                    String newsDate = select.get(0).childNode(0).toString().substring(2, 21);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    dateFormat.parse(newsDate);
                    news.setNewsDate(dateFormat.parse(newsDate));
                    int i = newsService.saveNews(news);
                    logger.info("抽取网易新闻《{}》成功！", news.getTitle());
                } catch (Exception e) {
                    logger.error("新闻抽取失败:{}", news.getUrl());
                    e.printStackTrace();
                }
            });
            logger.info("网易新闻拉取完成！");
        }



    }
}
