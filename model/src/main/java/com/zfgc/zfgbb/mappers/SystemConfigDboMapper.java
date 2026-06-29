package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.dbo.SystemConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695459787-04:00", comments="Source Table: zfgbb.system_config")
    long countByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695492926-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695509205-04:00", comments="Source Table: zfgbb.system_config")
    int deleteByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695521175-04:00", comments="Source Table: zfgbb.system_config")
    int insert(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695534065-04:00", comments="Source Table: zfgbb.system_config")
    int insertSelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695549154-04:00", comments="Source Table: zfgbb.system_config")
    List<SystemConfigDbo> selectByExample(SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695564744-04:00", comments="Source Table: zfgbb.system_config")
    SystemConfigDbo selectByPrimaryKey(String configKey);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695581493-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExampleSelective(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695598883-04:00", comments="Source Table: zfgbb.system_config")
    int updateByExample(@Param("row") SystemConfigDbo row, @Param("example") SystemConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695619642-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKeySelective(SystemConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695640741-04:00", comments="Source Table: zfgbb.system_config")
    int updateByPrimaryKey(SystemConfigDbo row);

    List<SystemConfigDbo> selectByExampleWithLimits(SystemConfigDboExample example);
}