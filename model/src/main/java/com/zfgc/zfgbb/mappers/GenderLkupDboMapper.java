package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.GenderLkupDbo;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenderLkupDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251348793-04:00", comments="Source Table: zfgbb.gender_lkup")
    long countByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251371833-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251387892-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251401792-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insert(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251416221-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insertSelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251433891-04:00", comments="Source Table: zfgbb.gender_lkup")
    List<GenderLkupDbo> selectByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25145744-04:00", comments="Source Table: zfgbb.gender_lkup")
    GenderLkupDbo selectByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251671823-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExampleSelective(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251710182-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExample(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251752541-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKeySelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251849917-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKey(GenderLkupDbo row);

    List<GenderLkupDbo> selectByExampleWithLimits(GenderLkupDboExample example);
}