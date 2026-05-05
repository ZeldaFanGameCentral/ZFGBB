package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.dbo.SystemConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245695133-04:00", comments="Source Table: zfgbb.system_config")
    long countByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245741891-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245760701-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24577585-04:00", comments="Source Table: zfgbb.system_config")
    int insert(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24579052-04:00", comments="Source Table: zfgbb.system_config")
    int insertSelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245807369-04:00", comments="Source Table: zfgbb.system_config")
    List<SystemConfigDbo> selectByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245826129-04:00", comments="Source Table: zfgbb.system_config")
    SystemConfigDbo selectByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245844268-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExampleSelective(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245862868-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExample(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245886847-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKeySelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.245909826-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKey(SystemConfigDbo row);

    List<SystemConfigDbo> selectByExampleWithLimits(SystemConfigDboExample example);
}