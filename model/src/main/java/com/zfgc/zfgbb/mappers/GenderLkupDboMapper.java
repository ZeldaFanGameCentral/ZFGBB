package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.GenderLkupDbo;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenderLkupDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705490665-04:00", comments="Source Table: zfgbb.gender_lkup")
    long countByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705509064-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705520614-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705532383-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insert(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705544263-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insertSelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705558293-04:00", comments="Source Table: zfgbb.gender_lkup")
    List<GenderLkupDbo> selectByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705573262-04:00", comments="Source Table: zfgbb.gender_lkup")
    GenderLkupDbo selectByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705588962-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExampleSelective(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705606571-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExample(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70565415-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKeySelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705683739-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKey(GenderLkupDbo row);

    List<GenderLkupDbo> selectByExampleWithLimits(GenderLkupDboExample example);
}