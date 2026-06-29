package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.InstallRunDbo;
import com.zfgc.zfgbb.dbo.InstallRunDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstallRunDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791362105-04:00", comments="Source Table: zfgbb.install_run")
    long countByExample(InstallRunDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791381685-04:00", comments="Source Table: zfgbb.install_run")
    int deleteByExample(InstallRunDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791396054-04:00", comments="Source Table: zfgbb.install_run")
    int deleteByPrimaryKey(Short installId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791410004-04:00", comments="Source Table: zfgbb.install_run")
    int insert(InstallRunDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791422853-04:00", comments="Source Table: zfgbb.install_run")
    int insertSelective(InstallRunDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791437493-04:00", comments="Source Table: zfgbb.install_run")
    List<InstallRunDbo> selectByExample(InstallRunDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791452392-04:00", comments="Source Table: zfgbb.install_run")
    InstallRunDbo selectByPrimaryKey(Short installId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791468282-04:00", comments="Source Table: zfgbb.install_run")
    int updateByExampleSelective(@Param("row") InstallRunDbo row, @Param("example") InstallRunDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791483371-04:00", comments="Source Table: zfgbb.install_run")
    int updateByExample(@Param("row") InstallRunDbo row, @Param("example") InstallRunDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.79151375-04:00", comments="Source Table: zfgbb.install_run")
    int updateByPrimaryKeySelective(InstallRunDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.7915366-04:00", comments="Source Table: zfgbb.install_run")
    int updateByPrimaryKey(InstallRunDbo row);

    List<InstallRunDbo> selectByExampleWithLimits(InstallRunDboExample example);
}