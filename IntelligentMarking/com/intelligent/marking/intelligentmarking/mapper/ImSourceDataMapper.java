package com.intelligent.marking.intelligentmarking.mapper;

import com.intelligent.marking.intelligentmarking.entity.ImSourceData;
import com.intelligent.marking.intelligentmarking.entity.ImSourceDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImSourceDataMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(ImSourceDataExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(ImSourceDataExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(ImSourceData row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(ImSourceData row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<ImSourceData> selectByExampleWithBLOBs(ImSourceDataExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<ImSourceData> selectByExample(ImSourceDataExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    ImSourceData selectByPrimaryKey(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("row") ImSourceData row, @Param("example") ImSourceDataExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleWithBLOBs(@Param("row") ImSourceData row, @Param("example") ImSourceDataExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("row") ImSourceData row, @Param("example") ImSourceDataExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(ImSourceData row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeyWithBLOBs(ImSourceData row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(ImSourceData row);
}