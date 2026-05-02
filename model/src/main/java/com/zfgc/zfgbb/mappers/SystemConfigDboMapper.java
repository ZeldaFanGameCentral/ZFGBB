package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.dbo.SystemConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51646071-04:00", comments="Source Table: zfgbb.system_config")
    long countByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516494829-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516512129-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516528708-04:00", comments="Source Table: zfgbb.system_config")
    int insert(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516551817-04:00", comments="Source Table: zfgbb.system_config")
    int insertSelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516569437-04:00", comments="Source Table: zfgbb.system_config")
    List<SystemConfigDbo> selectByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516588486-04:00", comments="Source Table: zfgbb.system_config")
    SystemConfigDbo selectByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516604726-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExampleSelective(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516622105-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExample(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516646224-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKeySelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516669144-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKey(SystemConfigDbo row);

    List<SystemConfigDbo> selectByExampleWithLimits(SystemConfigDboExample example);
}