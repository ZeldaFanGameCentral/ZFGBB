package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414137732-04:00", comments="Source Table: zfgbb.permission")
    long countByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.41420247-04:00", comments="Source Table: zfgbb.permission")
    int deleteByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414263748-04:00", comments="Source Table: zfgbb.permission")
    int deleteByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414329576-04:00", comments="Source Table: zfgbb.permission")
    int insert(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414369924-04:00", comments="Source Table: zfgbb.permission")
    int insertSelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414421003-04:00", comments="Source Table: zfgbb.permission")
    List<PermissionDbo> selectByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4144928-04:00", comments="Source Table: zfgbb.permission")
    PermissionDbo selectByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414543089-04:00", comments="Source Table: zfgbb.permission")
    int updateByExampleSelective(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414597547-04:00", comments="Source Table: zfgbb.permission")
    int updateByExample(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414708633-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKeySelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.414793161-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKey(PermissionDbo row);

    List<PermissionDbo> selectByExampleWithLimits(PermissionDboExample example);
}