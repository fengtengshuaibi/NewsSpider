package com.intelligent.marking.intelligentmarking.service.impl;


import com.github.pagehelper.PageHelper;
import com.intelligent.marking.intelligentmarking.entity.News;
import com.intelligent.marking.intelligentmarking.entity.NewsExample;
import com.intelligent.marking.intelligentmarking.mapper.NewsMapper;
import com.intelligent.marking.intelligentmarking.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author XXX
 * @since 2018-05-22
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsDao;

    @Override
    @Transactional
    public int saveNews(News news) {
        //1.check if the news is already existing
        NewsExample newsExample = new NewsExample();
        newsExample.createCriteria().andUrlEqualTo(news.getUrl());
        
        long count = newsDao.countByExample(newsExample);
        //2.if the news is not existing, insert it into the table
        if (count == 0) {
            return newsDao.insert(news);
        }
        return 0;
    }

    @Override
    public List<News> searchNewsForPage(int page, int pageSize, NewsExample example) {
        PageHelper.startPage(page, pageSize);
        List<News> news = newsDao.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isEmpty(news)) {
            return Collections.EMPTY_LIST;
        } else {
            return news;
        }
    }

    @Override
    public Long countByExample(NewsExample example) {
        return newsDao.countByExample(example);
    }

}
