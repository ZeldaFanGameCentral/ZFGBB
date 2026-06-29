package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AwardDbo;
import com.zfgc.zfgbb.dbo.AwardDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AwardDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.702848777-04:00", comments="Source Table: zfgbb.award")
    long countByExample(AwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.702906195-04:00", comments="Source Table: zfgbb.award")
    int deleteByExample(AwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.702926564-04:00", comments="Source Table: zfgbb.award")
    int deleteByPrimaryKey(Integer awardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.702937864-04:00", comments="Source Table: zfgbb.award")
    int insert(AwardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.702965533-04:00", comments="Source Table: zfgbb.award")
    int insertSelective(AwardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.702979963-04:00", comments="Source Table: zfgbb.award")
    List<AwardDbo> selectByExample(AwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.702995052-04:00", comments="Source Table: zfgbb.award")
    AwardDbo selectByPrimaryKey(Integer awardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703011042-04:00", comments="Source Table: zfgbb.award")
    int updateByExampleSelective(@Param("row") AwardDbo row, @Param("example") AwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703025911-04:00", comments="Source Table: zfgbb.award")
    int updateByExample(@Param("row") AwardDbo row, @Param("example") AwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70305698-04:00", comments="Source Table: zfgbb.award")
    int updateByPrimaryKeySelective(AwardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7030765-04:00", comments="Source Table: zfgbb.award")
    int updateByPrimaryKey(AwardDbo row);

    List<AwardDbo> selectByExampleWithLimits(AwardDboExample example);
}