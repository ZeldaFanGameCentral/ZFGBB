package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentResourceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697127485-04:00", comments="Source Table: zfgbb.content_resource")
    long countByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697145435-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697160404-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697174134-04:00", comments="Source Table: zfgbb.content_resource")
    int insert(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697188493-04:00", comments="Source Table: zfgbb.content_resource")
    int insertSelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697213092-04:00", comments="Source Table: zfgbb.content_resource")
    List<ContentResourceDbo> selectByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697231822-04:00", comments="Source Table: zfgbb.content_resource")
    ContentResourceDbo selectByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697248281-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExampleSelective(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697268781-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExample(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697309909-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKeySelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697337229-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKey(ContentResourceDbo row);

    List<ContentResourceDbo> selectByExampleWithLimits(ContentResourceDboExample example);
}