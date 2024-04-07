package com.intelligent.marking.intelligentmarking.mapper;

import com.intelligent.marking.intelligentmarking.entity.ImHotInfo;
import com.intelligent.marking.intelligentmarking.entity.ImHotInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImHotInfoMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(ImHotInfoExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(ImHotInfoExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(ImHotInfo row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(ImHotInfo row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<ImHotInfo> selectByExampleWithLock(ImHotInfoExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<ImHotInfo> selectByExample(ImHotInfoExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    ImHotInfo selectByPrimaryKeyWithLock(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    ImHotInfo selectByPrimaryKey(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("row") ImHotInfo row, @Param("example") ImHotInfoExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("row") ImHotInfo row, @Param("example") ImHotInfoExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(ImHotInfo row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(ImHotInfo row);
}