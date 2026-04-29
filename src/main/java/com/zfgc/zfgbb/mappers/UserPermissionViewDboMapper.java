package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.376950231-04:00", comments="Source Table: zfgbb.user_permission_view")
    long countByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37698528-04:00", comments="Source Table: zfgbb.user_permission_view")
    int deleteByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37699748-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insert(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377045588-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insertSelective(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377080187-04:00", comments="Source Table: zfgbb.user_permission_view")
    List<UserPermissionViewDbo> selectByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377107626-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExampleSelective(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377132025-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExample(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);
}