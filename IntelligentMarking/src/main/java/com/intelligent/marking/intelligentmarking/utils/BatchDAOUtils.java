package com.intelligent.marking.intelligentmarking.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
@Slf4j
@Component
public class BatchDAOUtils implements IBatchDAO{
    private final SqlSessionFactory sqlSessionFactory;

    public BatchDAOUtils(SqlSessionFactory sqlSessionFactory) {
            this.sqlSessionFactory = sqlSessionFactory;
        }
    @Override
    public <T> void saveBatch(Class<?> mapperClass, String methodName, List<T> list, int batchSize) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            Object mapper = sqlSession.getMapper(mapperClass);
            Method insertMethod = mapperClass.getMethod(methodName, list.get(0).getClass());

            int count = 0;
            for (T item : list) {
                insertMethod.invoke(mapper, item);
                if (++count % batchSize == 0) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                }
            }
            sqlSession.commit();
            log.info("插入数据成功,{}条",count);
            sqlSession.clearCache();
        } catch (Exception e) {
            throw new RuntimeException("Batch insert failed", e);
        }
    }
}
