package com.intelligent.marking.intelligentmarking.service.impl;

import com.intelligent.marking.intelligentmarking.entity.ImHotInfo;
import com.intelligent.marking.intelligentmarking.entity.ImHotInfoExample;
import com.intelligent.marking.intelligentmarking.mapper.ImHotInfoMapper;
import com.intelligent.marking.intelligentmarking.service.QueryHotPotsService;
import com.intelligent.marking.intelligentmarking.vo.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class QueryHotPotsServiceImpl implements QueryHotPotsService {
    @Autowired
    ImHotInfoMapper imHotInfoMapper;
    @Override
    public JsonData queryHotPots(String queryTime, Integer count) {
        ImHotInfoExample example=new ImHotInfoExample();
        ImHotInfoExample.Criteria criteria = example.createCriteria();
        criteria.andRecordtimeLike(queryTime+"%");
        example.setOrderByClause(" recordTime ,score desc");
        List<ImHotInfo> imHotInfos = imHotInfoMapper.selectByExample(example);
        if (count!=null){
            return new JsonData().success(imHotInfos.stream().limit(count).map(t->t.getKeyword()).collect(Collectors.toList()));
        }else {
            return new JsonData().success(imHotInfos.stream().map(t->t.getKeyword()).collect(Collectors.toList()));
        }
    }
}
