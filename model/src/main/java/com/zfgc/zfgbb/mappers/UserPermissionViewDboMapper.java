package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527694419-04:00", comments="Source Table: zfgbb.user_permission_view")
    long countByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527711429-04:00", comments="Source Table: zfgbb.user_permission_view")
    int deleteByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527724358-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insert(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527736358-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insertSelective(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527749787-04:00", comments="Source Table: zfgbb.user_permission_view")
    List<UserPermissionViewDbo> selectByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527766367-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExampleSelective(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527782246-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExample(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    List<UserPermissionViewDbo> selectByExampleWithLimits(UserPermissionViewDboExample example);
}