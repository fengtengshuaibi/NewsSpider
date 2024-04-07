package com.intelligent.marking.intelligentmarking.mapper;

import com.intelligent.marking.intelligentmarking.entity.News;
import com.intelligent.marking.intelligentmarking.entity.NewsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(News row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(News row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<News> selectByExampleWithBLOBs(NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<News> selectByExampleWithLock(NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<News> selectByExample(NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    News selectByPrimaryKeyWithLock(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    News selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("row") News row, @Param("example") NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleWithBLOBs(@Param("row") News row, @Param("example") NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("row") News row, @Param("example") NewsExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(News row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeyWithBLOBs(News row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(News row);
}