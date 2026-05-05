package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.KarmaDbo;
import com.zfgc.zfgbb.dbo.KarmaDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KarmaDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250058474-04:00", comments="Source Table: zfgbb.karma")
    long countByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250077384-04:00", comments="Source Table: zfgbb.karma")
    int deleteByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250091783-04:00", comments="Source Table: zfgbb.karma")
    int deleteByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250119422-04:00", comments="Source Table: zfgbb.karma")
    int insert(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250139052-04:00", comments="Source Table: zfgbb.karma")
    int insertSelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250156881-04:00", comments="Source Table: zfgbb.karma")
    List<KarmaDbo> selectByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250173671-04:00", comments="Source Table: zfgbb.karma")
    KarmaDbo selectByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25018948-04:00", comments="Source Table: zfgbb.karma")
    int updateByExampleSelective(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25020738-04:00", comments="Source Table: zfgbb.karma")
    int updateByExample(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250230459-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKeySelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.250251408-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKey(KarmaDbo row);

    List<KarmaDbo> selectByExampleWithLimits(KarmaDboExample example);
}