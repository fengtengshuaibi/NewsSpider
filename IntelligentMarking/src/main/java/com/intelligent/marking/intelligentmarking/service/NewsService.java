package com.intelligent.marking.intelligentmarking.service;


import com.intelligent.marking.intelligentmarking.entity.News;
import com.intelligent.marking.intelligentmarking.entity.NewsExample;

import java.util.List;

/**
 * @author XXX
 * @since 2018-05-22
 */
public interface NewsService {

    int saveNews(News news);

    List<News> searchNewsForPage(int page, int pageSize, NewsExample example);

    Long countByExample(NewsExample example);
}
