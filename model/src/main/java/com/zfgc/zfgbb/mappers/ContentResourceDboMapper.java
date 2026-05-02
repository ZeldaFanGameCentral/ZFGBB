package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentResourceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517709169-04:00", comments="Source Table: zfgbb.content_resource")
    long countByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517729199-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517747008-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517762168-04:00", comments="Source Table: zfgbb.content_resource")
    int insert(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517775397-04:00", comments="Source Table: zfgbb.content_resource")
    int insertSelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517798046-04:00", comments="Source Table: zfgbb.content_resource")
    List<ContentResourceDbo> selectByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517816566-04:00", comments="Source Table: zfgbb.content_resource")
    ContentResourceDbo selectByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517833705-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExampleSelective(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517852595-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExample(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517876184-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKeySelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.517908133-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKey(ContentResourceDbo row);

    List<ContentResourceDbo> selectByExampleWithLimits(ContentResourceDboExample example);
}