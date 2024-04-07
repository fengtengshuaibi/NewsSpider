package com.intelligent.marking.intelligentmarking.service;

import com.intelligent.marking.intelligentmarking.vo.JsonData;

public interface QueryHotPotsService {
     JsonData queryHotPots(String queryTime, Integer count) ;
}
