package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.SmileySetDbo;
import com.zfgc.zfgbb.dbo.SmileySetDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmileySetDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031273341-04:00", comments="Source Table: zfgbb.smiley_set")
    long countByExample(SmileySetDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031287191-04:00", comments="Source Table: zfgbb.smiley_set")
    int deleteByExample(SmileySetDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031296971-04:00", comments="Source Table: zfgbb.smiley_set")
    int deleteByPrimaryKey(String code);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.03130696-04:00", comments="Source Table: zfgbb.smiley_set")
    int insert(SmileySetDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.0313175-04:00", comments="Source Table: zfgbb.smiley_set")
    int insertSelective(SmileySetDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.03132952-04:00", comments="Source Table: zfgbb.smiley_set")
    List<SmileySetDbo> selectByExample(SmileySetDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031350509-04:00", comments="Source Table: zfgbb.smiley_set")
    SmileySetDbo selectByPrimaryKey(String code);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031364089-04:00", comments="Source Table: zfgbb.smiley_set")
    int updateByExampleSelective(@Param("row") SmileySetDbo row, @Param("example") SmileySetDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031378068-04:00", comments="Source Table: zfgbb.smiley_set")
    int updateByExample(@Param("row") SmileySetDbo row, @Param("example") SmileySetDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031392798-04:00", comments="Source Table: zfgbb.smiley_set")
    int updateByPrimaryKeySelective(SmileySetDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031409388-04:00", comments="Source Table: zfgbb.smiley_set")
    int updateByPrimaryKey(SmileySetDbo row);

    List<SmileySetDbo> selectByExampleWithLimits(SmileySetDboExample example);
}