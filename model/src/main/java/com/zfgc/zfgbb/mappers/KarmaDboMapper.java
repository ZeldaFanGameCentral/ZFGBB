package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.KarmaDbo;
import com.zfgc.zfgbb.dbo.KarmaDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KarmaDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371767031-04:00", comments="Source Table: zfgbb.karma")
    long countByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371796461-04:00", comments="Source Table: zfgbb.karma")
    int deleteByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37180783-04:00", comments="Source Table: zfgbb.karma")
    int deleteByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37181768-04:00", comments="Source Table: zfgbb.karma")
    int insert(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37182681-04:00", comments="Source Table: zfgbb.karma")
    int insertSelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371837999-04:00", comments="Source Table: zfgbb.karma")
    List<KarmaDbo> selectByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371849559-04:00", comments="Source Table: zfgbb.karma")
    KarmaDbo selectByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371860828-04:00", comments="Source Table: zfgbb.karma")
    int updateByExampleSelective(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371872898-04:00", comments="Source Table: zfgbb.karma")
    int updateByExample(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371887947-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKeySelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371903107-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKey(KarmaDbo row);
}