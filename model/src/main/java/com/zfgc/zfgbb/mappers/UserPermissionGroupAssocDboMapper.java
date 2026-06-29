package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPermissionGroupAssocDbo;
import com.zfgc.zfgbb.dbo.UserPermissionGroupAssocDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionGroupAssocDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74324856-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    long countByExample(UserPermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743264379-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int deleteByExample(UserPermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743275219-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int deleteByPrimaryKey(Integer userPermissionGroupAssocId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743286408-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int insert(UserPermissionGroupAssocDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743309648-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int insertSelective(UserPermissionGroupAssocDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743327407-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    List<UserPermissionGroupAssocDbo> selectByExample(UserPermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743342087-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    UserPermissionGroupAssocDbo selectByPrimaryKey(Integer userPermissionGroupAssocId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743360216-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int updateByExampleSelective(@Param("row") UserPermissionGroupAssocDbo row, @Param("example") UserPermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743376476-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int updateByExample(@Param("row") UserPermissionGroupAssocDbo row, @Param("example") UserPermissionGroupAssocDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743395225-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int updateByPrimaryKeySelective(UserPermissionGroupAssocDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743411875-04:00", comments="Source Table: zfgbb.user_permission_group_assoc")
    int updateByPrimaryKey(UserPermissionGroupAssocDbo row);

    List<UserPermissionGroupAssocDbo> selectByExampleWithLimits(UserPermissionGroupAssocDboExample example);
}