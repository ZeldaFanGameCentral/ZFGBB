package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrUserPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660168225-04:00", comments="Source Table: zfgbb.br_user_permission")
    long countByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660207484-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660238753-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660288532-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insert(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66033394-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insertSelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660496015-04:00", comments="Source Table: zfgbb.br_user_permission")
    List<BrUserPermissionDbo> selectByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660565803-04:00", comments="Source Table: zfgbb.br_user_permission")
    BrUserPermissionDbo selectByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66064452-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExampleSelective(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660702709-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExample(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660777016-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKeySelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660876043-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKey(BrUserPermissionDbo row);

    List<BrUserPermissionDbo> selectByExampleWithLimits(BrUserPermissionDboExample example);
}