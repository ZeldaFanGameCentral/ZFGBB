package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentResourceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45396292-04:00", comments="Source Table: zfgbb.content_resource")
    long countByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453980349-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453997589-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454011338-04:00", comments="Source Table: zfgbb.content_resource")
    int insert(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454022838-04:00", comments="Source Table: zfgbb.content_resource")
    int insertSelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454052397-04:00", comments="Source Table: zfgbb.content_resource")
    List<ContentResourceDbo> selectByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454075066-04:00", comments="Source Table: zfgbb.content_resource")
    ContentResourceDbo selectByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454092346-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExampleSelective(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454112825-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExample(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454135314-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKeySelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454155944-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKey(ContentResourceDbo row);

    List<ContentResourceDbo> selectByExampleWithLimits(ContentResourceDboExample example);
}