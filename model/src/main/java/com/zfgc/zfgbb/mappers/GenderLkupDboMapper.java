package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.GenderLkupDbo;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenderLkupDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457751109-04:00", comments="Source Table: zfgbb.gender_lkup")
    long countByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457785528-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457800247-04:00", comments="Source Table: zfgbb.gender_lkup")
    int deleteByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457814437-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insert(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457825927-04:00", comments="Source Table: zfgbb.gender_lkup")
    int insertSelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457841996-04:00", comments="Source Table: zfgbb.gender_lkup")
    List<GenderLkupDbo> selectByExample(GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457857616-04:00", comments="Source Table: zfgbb.gender_lkup")
    GenderLkupDbo selectByPrimaryKey(Integer genderId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457975882-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExampleSelective(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.457998841-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByExample(@Param("row") GenderLkupDbo row, @Param("example") GenderLkupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45802942-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKeySelective(GenderLkupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.458059079-04:00", comments="Source Table: zfgbb.gender_lkup")
    int updateByPrimaryKey(GenderLkupDbo row);

    List<GenderLkupDbo> selectByExampleWithLimits(GenderLkupDboExample example);
}