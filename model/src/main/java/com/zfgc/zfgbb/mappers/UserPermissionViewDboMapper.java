package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744816111-04:00", comments="Source Table: zfgbb.user_permission_view")
    long countByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74485837-04:00", comments="Source Table: zfgbb.user_permission_view")
    int deleteByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744902068-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insert(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744920128-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insertSelective(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744937717-04:00", comments="Source Table: zfgbb.user_permission_view")
    List<UserPermissionViewDbo> selectByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744959446-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExampleSelective(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744976666-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExample(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    List<UserPermissionViewDbo> selectByExampleWithLimits(UserPermissionViewDboExample example);
}