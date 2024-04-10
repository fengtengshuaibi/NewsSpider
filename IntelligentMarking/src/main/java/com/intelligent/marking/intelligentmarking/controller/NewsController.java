package com.intelligent.marking.intelligentmarking.controller;


import com.fly.blog.util.NewsUtils;
import com.intelligent.marking.intelligentmarking.entity.News;
import com.intelligent.marking.intelligentmarking.entity.NewsExample;
import com.intelligent.marking.intelligentmarking.service.NewsPuller;
import com.intelligent.marking.intelligentmarking.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author XXX
 * @since 2018-05-13
 */
@RestController
@RequestMapping("/news")
@Api(value = "新闻拉取API")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    @Qualifier("netEasyMacroNewsPuller")
    private NewsPuller netEasyMacroNewsPuller;
    @Autowired
    @Qualifier("netEasyNationalNewsPuller")
    private NewsPuller netEasyNationalNewsPuller;
    @Autowired
    @Qualifier("netEasyFundNewsPuller")
    private NewsPuller netEasyFundNewsPuller;
    @Autowired
    @Qualifier("toutiaoNewsPuller")
    private NewsPuller toutiaoNewsPuller;
    @Autowired
    @Qualifier("ifengNewsPuller")
    private NewsPuller ifengNewsPuller;
    @Autowired
    @Qualifier("sohuNewsPuller")
    private NewsPuller sohuNewsPuller;
    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "爬虫拉取网易宏观新闻")
    @GetMapping("/pull/neteasyMacro")
    public void pullNeteasyMacroNews() {
        netEasyMacroNewsPuller.pullNews();
    }

    @ApiOperation(value = "爬虫拉取网易国际新闻")
    @GetMapping("/pull/neteasyNation")
    public void pullNeteasyNationNews() {
        netEasyNationalNewsPuller.pullNews();
    }

    @ApiOperation(value = "爬虫拉取网易基金新闻")
    @GetMapping("/pull/neteasyFund")
    public void pullNeteasyFundNews() {
        netEasyFundNewsPuller.pullNews();
    }

    @ApiOperation(value = "爬虫拉取今日头条新闻")
    @GetMapping("/pull/toutiao")
    public void pullToutiaoNews() {
        toutiaoNewsPuller.pullNews();
    }

    @ApiOperation(value = "爬虫拉取搜狐新闻")
    @GetMapping("/pull/sohu")
    public void pullSohuNews() {
        sohuNewsPuller.pullNews();
    }
    @ApiOperation(value = "爬虫拉取凤凰新闻")
    @GetMapping("/pull/ifeng")
    public void pullIfengNews() {
        ifengNewsPuller.pullNews();
    }

    @ApiOperation(value = "获取{source}新闻")
    @GetMapping("/{source}")
    public List<News> getToutiaoNews(@RequestParam Integer page, @RequestParam Integer pageSize, @PathVariable String source) {
        NewsExample example = new NewsExample();
        example.createCriteria().andSourceEqualTo(NewsUtils.getSourceFromPathVariable(source));
        example.setOrderByClause("create_date desc");
        return newsService.searchNewsForPage(page, pageSize, example);
    }

    @ApiOperation("获取{source}新闻总数")
    @GetMapping("/{source}/count")
    public Long getToutiaoCount(@PathVariable String source) {
        NewsExample example = new NewsExample();
        example.createCriteria().andSourceEqualTo(NewsUtils.getSourceFromPathVariable(source));
        return newsService.countByExample(example);
    }

    @ApiOperation(value = "获取所有新闻")
    @GetMapping
    public List<News> getNews(@RequestParam Integer page, @RequestParam Integer pageSize) {
        NewsExample example = new NewsExample();
        example.createCriteria();
        example.setOrderByClause("create_date desc");
        return newsService.searchNewsForPage(page, pageSize, example);
    }

    @ApiOperation("获取新闻总数")
    @GetMapping("/count")
    public Long getCount() {
        NewsExample example = new NewsExample();
        example.createCriteria();
        return newsService.countByExample(example);
    }

}
