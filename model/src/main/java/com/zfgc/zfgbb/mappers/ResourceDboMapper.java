package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ResourceDbo;
import com.zfgc.zfgbb.dbo.ResourceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716733475-04:00", comments="Source Table: zfgbb.resource")
    long countByExample(ResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716757954-04:00", comments="Source Table: zfgbb.resource")
    int deleteByExample(ResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716776504-04:00", comments="Source Table: zfgbb.resource")
    int deleteByPrimaryKey(Integer contentEntityId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716830142-04:00", comments="Source Table: zfgbb.resource")
    int insert(ResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716844751-04:00", comments="Source Table: zfgbb.resource")
    int insertSelective(ResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716859081-04:00", comments="Source Table: zfgbb.resource")
    List<ResourceDbo> selectByExample(ResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71687362-04:00", comments="Source Table: zfgbb.resource")
    ResourceDbo selectByPrimaryKey(Integer contentEntityId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71688926-04:00", comments="Source Table: zfgbb.resource")
    int updateByExampleSelective(@Param("row") ResourceDbo row, @Param("example") ResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716930289-04:00", comments="Source Table: zfgbb.resource")
    int updateByExample(@Param("row") ResourceDbo row, @Param("example") ResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716959418-04:00", comments="Source Table: zfgbb.resource")
    int updateByPrimaryKeySelective(ResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716978467-04:00", comments="Source Table: zfgbb.resource")
    int updateByPrimaryKey(ResourceDbo row);

    List<ResourceDbo> selectByExampleWithLimits(ResourceDboExample example);
}