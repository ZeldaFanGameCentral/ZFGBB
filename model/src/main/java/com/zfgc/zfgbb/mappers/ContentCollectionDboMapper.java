package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentCollectionDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentCollectionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723383488-04:00", comments="Source Table: zfgbb.content_collection")
    long countByExample(ContentCollectionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723395838-04:00", comments="Source Table: zfgbb.content_collection")
    int deleteByExample(ContentCollectionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723406917-04:00", comments="Source Table: zfgbb.content_collection")
    int deleteByPrimaryKey(Integer contentCollectionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723415757-04:00", comments="Source Table: zfgbb.content_collection")
    int insert(ContentCollectionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723425797-04:00", comments="Source Table: zfgbb.content_collection")
    int insertSelective(ContentCollectionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723440196-04:00", comments="Source Table: zfgbb.content_collection")
    List<ContentCollectionDbo> selectByExample(ContentCollectionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723454456-04:00", comments="Source Table: zfgbb.content_collection")
    ContentCollectionDbo selectByPrimaryKey(Integer contentCollectionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723467085-04:00", comments="Source Table: zfgbb.content_collection")
    int updateByExampleSelective(@Param("row") ContentCollectionDbo row, @Param("example") ContentCollectionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723482215-04:00", comments="Source Table: zfgbb.content_collection")
    int updateByExample(@Param("row") ContentCollectionDbo row, @Param("example") ContentCollectionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723500284-04:00", comments="Source Table: zfgbb.content_collection")
    int updateByPrimaryKeySelective(ContentCollectionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723517524-04:00", comments="Source Table: zfgbb.content_collection")
    int updateByPrimaryKey(ContentCollectionDbo row);

    List<ContentCollectionDbo> selectByExampleWithLimits(ContentCollectionDboExample example);
}