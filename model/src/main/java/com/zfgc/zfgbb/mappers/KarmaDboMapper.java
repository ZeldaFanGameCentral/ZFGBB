package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.KarmaDbo;
import com.zfgc.zfgbb.dbo.KarmaDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KarmaDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456340364-04:00", comments="Source Table: zfgbb.karma")
    long countByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456358523-04:00", comments="Source Table: zfgbb.karma")
    int deleteByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456372293-04:00", comments="Source Table: zfgbb.karma")
    int deleteByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456384153-04:00", comments="Source Table: zfgbb.karma")
    int insert(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456396822-04:00", comments="Source Table: zfgbb.karma")
    int insertSelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456411322-04:00", comments="Source Table: zfgbb.karma")
    List<KarmaDbo> selectByExample(KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456427831-04:00", comments="Source Table: zfgbb.karma")
    KarmaDbo selectByPrimaryKey(Integer karmaId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456442801-04:00", comments="Source Table: zfgbb.karma")
    int updateByExampleSelective(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45645937-04:00", comments="Source Table: zfgbb.karma")
    int updateByExample(@Param("row") KarmaDbo row, @Param("example") KarmaDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4564794-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKeySelective(KarmaDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456499829-04:00", comments="Source Table: zfgbb.karma")
    int updateByPrimaryKey(KarmaDbo row);

    List<KarmaDbo> selectByExampleWithLimits(KarmaDboExample example);
}