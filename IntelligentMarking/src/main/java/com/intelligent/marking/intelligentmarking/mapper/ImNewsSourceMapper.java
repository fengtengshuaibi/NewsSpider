package com.intelligent.marking.intelligentmarking.mapper;

import com.intelligent.marking.intelligentmarking.entity.ImNewsSource;
import com.intelligent.marking.intelligentmarking.entity.ImNewsSourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImNewsSourceMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(ImNewsSource row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(ImNewsSource row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<ImNewsSource> selectByExampleWithBLOBs(ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<ImNewsSource> selectByExampleWithLock(ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<ImNewsSource> selectByExample(ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    ImNewsSource selectByPrimaryKeyWithLock(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    ImNewsSource selectByPrimaryKey(Long autoincre);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("row") ImNewsSource row, @Param("example") ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleWithBLOBs(@Param("row") ImNewsSource row, @Param("example") ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("row") ImNewsSource row, @Param("example") ImNewsSourceExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(ImNewsSource row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeyWithBLOBs(ImNewsSource row);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(ImNewsSource row);
}