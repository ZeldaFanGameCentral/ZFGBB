package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.2584014-04:00", comments="Source Table: zfgbb.user_permission_view")
    long countByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.258420099-04:00", comments="Source Table: zfgbb.user_permission_view")
    int deleteByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.258438188-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insert(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.258452638-04:00", comments="Source Table: zfgbb.user_permission_view")
    int insertSelective(UserPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.258468967-04:00", comments="Source Table: zfgbb.user_permission_view")
    List<UserPermissionViewDbo> selectByExample(UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.258489917-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExampleSelective(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.258513926-04:00", comments="Source Table: zfgbb.user_permission_view")
    int updateByExample(@Param("row") UserPermissionViewDbo row, @Param("example") UserPermissionViewDboExample example);

    List<UserPermissionViewDbo> selectByExampleWithLimits(UserPermissionViewDboExample example);
}