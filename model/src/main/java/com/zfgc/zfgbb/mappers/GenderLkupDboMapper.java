package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.GenderLkupDbo;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenderLkupDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.52133186-04:00", comments="Source Table: zfgbb.gender_lkup")
    long countByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521354319-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521381688-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521404837-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insert(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521423457-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insertSelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521446346-04:00", comments="Source Table: zfgbb.gender_lkup")
    List<GenderLkupDbo> selectByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521467025-04:00", comments="Source Table: zfgbb.gender_lkup")
    GenderLkupDbo selectByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521552722-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExampleSelective(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521579461-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExample(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.52161288-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKeySelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.521647139-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKey(GenderLkupDbo row);

    List<GenderLkupDbo> selectByExampleWithLimits(GenderLkupDboExample example);
}