package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.dbo.SystemConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45272422-04:00", comments="Source Table: zfgbb.system_config")
    long countByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452768348-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452783478-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452798397-04:00", comments="Source Table: zfgbb.system_config")
    int insert(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452811357-04:00", comments="Source Table: zfgbb.system_config")
    int insertSelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452827276-04:00", comments="Source Table: zfgbb.system_config")
    List<SystemConfigDbo> selectByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452844726-04:00", comments="Source Table: zfgbb.system_config")
    SystemConfigDbo selectByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452861395-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExampleSelective(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452879465-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExample(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452902034-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKeySelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452923153-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKey(SystemConfigDbo row);

    List<SystemConfigDbo> selectByExampleWithLimits(SystemConfigDboExample example);
}