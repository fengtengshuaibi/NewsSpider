package com.intelligent.marking.intelligentmarking.utils;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IBatchDAO {

    <T> void saveBatch(Class<?> mapperClass, String methodName, List<T> list, int batchSize);
}
