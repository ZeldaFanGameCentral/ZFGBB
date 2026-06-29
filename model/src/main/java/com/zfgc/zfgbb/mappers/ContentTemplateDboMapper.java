package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentTemplateDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733314189-04:00", comments="Source Table: zfgbb.content_template")
    long countByExample(ContentTemplateDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733334878-04:00", comments="Source Table: zfgbb.content_template")
    int deleteByExample(ContentTemplateDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733350128-04:00", comments="Source Table: zfgbb.content_template")
    int deleteByPrimaryKey(Integer contentTemplateId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733362777-04:00", comments="Source Table: zfgbb.content_template")
    int insert(ContentTemplateDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733376507-04:00", comments="Source Table: zfgbb.content_template")
    int insertSelective(ContentTemplateDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733389316-04:00", comments="Source Table: zfgbb.content_template")
    List<ContentTemplateDbo> selectByExample(ContentTemplateDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733402506-04:00", comments="Source Table: zfgbb.content_template")
    ContentTemplateDbo selectByPrimaryKey(Integer contentTemplateId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733416686-04:00", comments="Source Table: zfgbb.content_template")
    int updateByExampleSelective(@Param("row") ContentTemplateDbo row, @Param("example") ContentTemplateDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733431795-04:00", comments="Source Table: zfgbb.content_template")
    int updateByExample(@Param("row") ContentTemplateDbo row, @Param("example") ContentTemplateDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733450775-04:00", comments="Source Table: zfgbb.content_template")
    int updateByPrimaryKeySelective(ContentTemplateDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733472324-04:00", comments="Source Table: zfgbb.content_template")
    int updateByPrimaryKey(ContentTemplateDbo row);

    List<ContentTemplateDbo> selectByExampleWithLimits(ContentTemplateDboExample example);
}