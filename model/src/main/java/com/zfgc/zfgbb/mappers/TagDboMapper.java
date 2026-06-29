package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.TagDbo;
import com.zfgc.zfgbb.dbo.TagDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728374103-04:00", comments="Source Table: zfgbb.tag")
    long countByExample(TagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728386862-04:00", comments="Source Table: zfgbb.tag")
    int deleteByExample(TagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728399162-04:00", comments="Source Table: zfgbb.tag")
    int deleteByPrimaryKey(Integer tagId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728408291-04:00", comments="Source Table: zfgbb.tag")
    int insert(TagDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728418301-04:00", comments="Source Table: zfgbb.tag")
    int insertSelective(TagDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728430851-04:00", comments="Source Table: zfgbb.tag")
    List<TagDbo> selectByExample(TagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72844404-04:00", comments="Source Table: zfgbb.tag")
    TagDbo selectByPrimaryKey(Integer tagId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72845682-04:00", comments="Source Table: zfgbb.tag")
    int updateByExampleSelective(@Param("row") TagDbo row, @Param("example") TagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728475319-04:00", comments="Source Table: zfgbb.tag")
    int updateByExample(@Param("row") TagDbo row, @Param("example") TagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728491679-04:00", comments="Source Table: zfgbb.tag")
    int updateByPrimaryKeySelective(TagDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728507568-04:00", comments="Source Table: zfgbb.tag")
    int updateByPrimaryKey(TagDbo row);

    List<TagDbo> selectByExampleWithLimits(TagDboExample example);
}