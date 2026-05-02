package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrUserPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.484790957-04:00", comments="Source Table: zfgbb.br_user_permission")
    long countByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.484823576-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.484851595-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.484878314-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insert(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.484908753-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insertSelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.484941472-04:00", comments="Source Table: zfgbb.br_user_permission")
    List<BrUserPermissionDbo> selectByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.484975161-04:00", comments="Source Table: zfgbb.br_user_permission")
    BrUserPermissionDbo selectByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.485023599-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExampleSelective(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.485076378-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExample(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.485134736-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKeySelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.485226523-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKey(BrUserPermissionDbo row);

    List<BrUserPermissionDbo> selectByExampleWithLimits(BrUserPermissionDboExample example);
}