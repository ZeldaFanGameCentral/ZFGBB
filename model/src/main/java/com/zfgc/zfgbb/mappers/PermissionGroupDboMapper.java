package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PermissionGroupDbo;
import com.zfgc.zfgbb.dbo.PermissionGroupDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionGroupDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741174614-04:00", comments="Source Table: zfgbb.permission_group")
    long countByExample(PermissionGroupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741189264-04:00", comments="Source Table: zfgbb.permission_group")
    int deleteByExample(PermissionGroupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741202483-04:00", comments="Source Table: zfgbb.permission_group")
    int deleteByPrimaryKey(Integer permissionGroupId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741213823-04:00", comments="Source Table: zfgbb.permission_group")
    int insert(PermissionGroupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741223313-04:00", comments="Source Table: zfgbb.permission_group")
    int insertSelective(PermissionGroupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741241322-04:00", comments="Source Table: zfgbb.permission_group")
    List<PermissionGroupDbo> selectByExample(PermissionGroupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741255732-04:00", comments="Source Table: zfgbb.permission_group")
    PermissionGroupDbo selectByPrimaryKey(Integer permissionGroupId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741272691-04:00", comments="Source Table: zfgbb.permission_group")
    int updateByExampleSelective(@Param("row") PermissionGroupDbo row, @Param("example") PermissionGroupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741287871-04:00", comments="Source Table: zfgbb.permission_group")
    int updateByExample(@Param("row") PermissionGroupDbo row, @Param("example") PermissionGroupDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74131478-04:00", comments="Source Table: zfgbb.permission_group")
    int updateByPrimaryKeySelective(PermissionGroupDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741343519-04:00", comments="Source Table: zfgbb.permission_group")
    int updateByPrimaryKey(PermissionGroupDbo row);

    List<PermissionGroupDbo> selectByExampleWithLimits(PermissionGroupDboExample example);
}