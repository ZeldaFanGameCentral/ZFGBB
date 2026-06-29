package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentEntityDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714794055-04:00", comments="Source Table: zfgbb.content_entity")
    long countByExample(ContentEntityDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714828294-04:00", comments="Source Table: zfgbb.content_entity")
    int deleteByExample(ContentEntityDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714842354-04:00", comments="Source Table: zfgbb.content_entity")
    int deleteByPrimaryKey(Integer contentEntityId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714854263-04:00", comments="Source Table: zfgbb.content_entity")
    int insert(ContentEntityDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714865903-04:00", comments="Source Table: zfgbb.content_entity")
    int insertSelective(ContentEntityDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714879843-04:00", comments="Source Table: zfgbb.content_entity")
    List<ContentEntityDbo> selectByExample(ContentEntityDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714894582-04:00", comments="Source Table: zfgbb.content_entity")
    ContentEntityDbo selectByPrimaryKey(Integer contentEntityId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714910772-04:00", comments="Source Table: zfgbb.content_entity")
    int updateByExampleSelective(@Param("row") ContentEntityDbo row, @Param("example") ContentEntityDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714927541-04:00", comments="Source Table: zfgbb.content_entity")
    int updateByExample(@Param("row") ContentEntityDbo row, @Param("example") ContentEntityDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71494786-04:00", comments="Source Table: zfgbb.content_entity")
    int updateByPrimaryKeySelective(ContentEntityDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71496965-04:00", comments="Source Table: zfgbb.content_entity")
    int updateByPrimaryKey(ContentEntityDbo row);

    List<ContentEntityDbo> selectByExampleWithLimits(ContentEntityDboExample example);
}