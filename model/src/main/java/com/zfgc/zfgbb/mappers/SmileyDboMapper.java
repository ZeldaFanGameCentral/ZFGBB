package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.SmileyDbo;
import com.zfgc.zfgbb.dbo.SmileyDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmileyDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.03204003-04:00", comments="Source Table: zfgbb.smiley")
    long countByExample(SmileyDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.03205336-04:00", comments="Source Table: zfgbb.smiley")
    int deleteByExample(SmileyDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032066349-04:00", comments="Source Table: zfgbb.smiley")
    int deleteByPrimaryKey(Integer smileyId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032074769-04:00", comments="Source Table: zfgbb.smiley")
    int insert(SmileyDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032083549-04:00", comments="Source Table: zfgbb.smiley")
    int insertSelective(SmileyDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032094948-04:00", comments="Source Table: zfgbb.smiley")
    List<SmileyDbo> selectByExample(SmileyDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032106988-04:00", comments="Source Table: zfgbb.smiley")
    SmileyDbo selectByPrimaryKey(Integer smileyId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032119798-04:00", comments="Source Table: zfgbb.smiley")
    int updateByExampleSelective(@Param("row") SmileyDbo row, @Param("example") SmileyDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032132247-04:00", comments="Source Table: zfgbb.smiley")
    int updateByExample(@Param("row") SmileyDbo row, @Param("example") SmileyDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032147827-04:00", comments="Source Table: zfgbb.smiley")
    int updateByPrimaryKeySelective(SmileyDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032161637-04:00", comments="Source Table: zfgbb.smiley")
    int updateByPrimaryKey(SmileyDbo row);

    List<SmileyDbo> selectByExampleWithLimits(SmileyDboExample example);
}