package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.KarmaDbo;
import com.zfgc.zfgbb.dbo.KarmaDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KarmaDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520220136-04:00", comments="Source Table: zfgbb.karma")
    long countByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520241386-04:00", comments="Source Table: zfgbb.karma")
    int deleteByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520257275-04:00", comments="Source Table: zfgbb.karma")
    int deleteByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520280554-04:00", comments="Source Table: zfgbb.karma")
    int insert(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520300174-04:00", comments="Source Table: zfgbb.karma")
    int insertSelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520315963-04:00", comments="Source Table: zfgbb.karma")
    List<KarmaDbo> selectByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520332293-04:00", comments="Source Table: zfgbb.karma")
    KarmaDbo selectByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520349312-04:00", comments="Source Table: zfgbb.karma")
    int updateByExampleSelective(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.520367681-04:00", comments="Source Table: zfgbb.karma")
    int updateByExample(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.52040008-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKeySelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.52042324-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKey(KarmaDbo row);

    List<KarmaDbo> selectByExampleWithLimits(KarmaDboExample example);
}