package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentResourceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247179576-04:00", comments="Source Table: zfgbb.content_resource")
    long countByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247216094-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247241934-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247296932-04:00", comments="Source Table: zfgbb.content_resource")
    int insert(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247322471-04:00", comments="Source Table: zfgbb.content_resource")
    int insertSelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24735933-04:00", comments="Source Table: zfgbb.content_resource")
    List<ContentResourceDbo> selectByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247382879-04:00", comments="Source Table: zfgbb.content_resource")
    ContentResourceDbo selectByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247405658-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExampleSelective(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247428518-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExample(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247492046-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKeySelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247523735-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKey(ContentResourceDbo row);

    List<ContentResourceDbo> selectByExampleWithLimits(ContentResourceDboExample example);
}