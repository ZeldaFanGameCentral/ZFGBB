package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46398338-04:00", comments="Source Table: zfgbb.user_permission_view")
    long countByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46399859-04:00", comments="Source Table: zfgbb.user_permission_view")
    int deleteByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464012799-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insert(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464025019-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insertSelective(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464037578-04:00", comments="Source Table: zfgbb.user_permission_view")
    List<UserPermissionViewDbo> selectByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464055008-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExampleSelective(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464070257-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExample(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    List<UserPermissionViewDbo> selectByExampleWithLimits(UserPermissionViewDboExample example);
}