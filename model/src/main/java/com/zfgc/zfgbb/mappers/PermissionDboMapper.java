package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.480701952-04:00", comments="Source Table: zfgbb.permission")
    long countByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.48076197-04:00", comments="Source Table: zfgbb.permission")
    int deleteByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.480819528-04:00", comments="Source Table: zfgbb.permission")
    int deleteByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.480890236-04:00", comments="Source Table: zfgbb.permission")
    int insert(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.480935844-04:00", comments="Source Table: zfgbb.permission")
    int insertSelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.480987563-04:00", comments="Source Table: zfgbb.permission")
    List<PermissionDbo> selectByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.48108265-04:00", comments="Source Table: zfgbb.permission")
    PermissionDbo selectByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.481144528-04:00", comments="Source Table: zfgbb.permission")
    int updateByExampleSelective(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.481188736-04:00", comments="Source Table: zfgbb.permission")
    int updateByExample(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.481287423-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKeySelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.48136057-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKey(PermissionDbo row);

    List<PermissionDbo> selectByExampleWithLimits(PermissionDboExample example);
}