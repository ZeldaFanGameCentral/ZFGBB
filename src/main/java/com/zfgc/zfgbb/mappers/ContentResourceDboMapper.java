package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentResourceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369400219-04:00", comments="Source Table: zfgbb.content_resource")
    long countByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369415369-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369427238-04:00", comments="Source Table: zfgbb.content_resource")
    int deleteByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369437468-04:00", comments="Source Table: zfgbb.content_resource")
    int insert(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369447468-04:00", comments="Source Table: zfgbb.content_resource")
    int insertSelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369460817-04:00", comments="Source Table: zfgbb.content_resource")
    List<ContentResourceDbo> selectByExample(ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369473387-04:00", comments="Source Table: zfgbb.content_resource")
    ContentResourceDbo selectByPrimaryKey(Integer contentResourceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369485526-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExampleSelective(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369498636-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByExample(@Param("row") ContentResourceDbo row, @Param("example") ContentResourceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369515355-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKeySelective(ContentResourceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369533465-04:00", comments="Source Table: zfgbb.content_resource")
    int updateByPrimaryKey(ContentResourceDbo row);
}