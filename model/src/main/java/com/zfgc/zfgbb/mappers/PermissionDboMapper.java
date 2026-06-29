package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655945627-04:00", comments="Source Table: zfgbb.permission")
    long countByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656068613-04:00", comments="Source Table: zfgbb.permission")
    int deleteByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.65615848-04:00", comments="Source Table: zfgbb.permission")
    int deleteByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656227918-04:00", comments="Source Table: zfgbb.permission")
    int insert(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656274136-04:00", comments="Source Table: zfgbb.permission")
    int insertSelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656339754-04:00", comments="Source Table: zfgbb.permission")
    List<PermissionDbo> selectByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656401912-04:00", comments="Source Table: zfgbb.permission")
    PermissionDbo selectByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656458571-04:00", comments="Source Table: zfgbb.permission")
    int updateByExampleSelective(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656517729-04:00", comments="Source Table: zfgbb.permission")
    int updateByExample(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656592517-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKeySelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.656677084-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKey(PermissionDbo row);

    List<PermissionDbo> selectByExampleWithLimits(PermissionDboExample example);
}