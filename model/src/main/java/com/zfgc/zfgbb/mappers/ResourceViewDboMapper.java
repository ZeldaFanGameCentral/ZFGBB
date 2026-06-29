package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ResourceViewDbo;
import com.zfgc.zfgbb.dbo.ResourceViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719965434-04:00", comments="Source Table: zfgbb.resource_view")
    long countByExample(ResourceViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719980864-04:00", comments="Source Table: zfgbb.resource_view")
    int deleteByExample(ResourceViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719993403-04:00", comments="Source Table: zfgbb.resource_view")
    int insert(ResourceViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720003293-04:00", comments="Source Table: zfgbb.resource_view")
    int insertSelective(ResourceViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720019093-04:00", comments="Source Table: zfgbb.resource_view")
    List<ResourceViewDbo> selectByExample(ResourceViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720035702-04:00", comments="Source Table: zfgbb.resource_view")
    int updateByExampleSelective(@Param("row") ResourceViewDbo row, @Param("example") ResourceViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720052121-04:00", comments="Source Table: zfgbb.resource_view")
    int updateByExample(@Param("row") ResourceViewDbo row, @Param("example") ResourceViewDboExample example);

    List<ResourceViewDbo> selectByExampleWithLimits(ResourceViewDboExample example);
}