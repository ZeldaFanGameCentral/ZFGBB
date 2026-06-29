package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PermissionGroupAssocDbo;
import com.zfgc.zfgbb.dbo.PermissionGroupAssocDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionGroupAssocDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742168843-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    long countByExample(PermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742182493-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int deleteByExample(PermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742195183-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int deleteByPrimaryKey(Integer permissionGroupAssocId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742206442-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int insert(PermissionGroupAssocDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742215442-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int insertSelective(PermissionGroupAssocDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742232731-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    List<PermissionGroupAssocDbo> selectByExample(PermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742246431-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    PermissionGroupAssocDbo selectByPrimaryKey(Integer permissionGroupAssocId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74226134-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int updateByExampleSelective(@Param("row") PermissionGroupAssocDbo row, @Param("example") PermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74227889-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int updateByExample(@Param("row") PermissionGroupAssocDbo row, @Param("example") PermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742334258-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int updateByPrimaryKeySelective(PermissionGroupAssocDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742363247-04:00", comments="Source Table: zfgbb.permission_group_assoc")
    int updateByPrimaryKey(PermissionGroupAssocDbo row);

    List<PermissionGroupAssocDbo> selectByExampleWithLimits(PermissionGroupAssocDboExample example);
}