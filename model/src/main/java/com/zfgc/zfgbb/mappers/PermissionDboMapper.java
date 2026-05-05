package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.204661745-04:00", comments="Source Table: zfgbb.permission")
    long countByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.204741622-04:00", comments="Source Table: zfgbb.permission")
    int deleteByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.204804071-04:00", comments="Source Table: zfgbb.permission")
    int deleteByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.204899128-04:00", comments="Source Table: zfgbb.permission")
    int insert(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.204948036-04:00", comments="Source Table: zfgbb.permission")
    int insertSelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.20512136-04:00", comments="Source Table: zfgbb.permission")
    List<PermissionDbo> selectByExample(PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.205217727-04:00", comments="Source Table: zfgbb.permission")
    PermissionDbo selectByPrimaryKey(Integer permissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.205290965-04:00", comments="Source Table: zfgbb.permission")
    int updateByExampleSelective(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.205351053-04:00", comments="Source Table: zfgbb.permission")
    int updateByExample(@Param("row") PermissionDbo row, @Param("example") PermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.205480599-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKeySelective(PermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.205579106-04:00", comments="Source Table: zfgbb.permission")
    int updateByPrimaryKey(PermissionDbo row);

    List<PermissionDbo> selectByExampleWithLimits(PermissionDboExample example);
}