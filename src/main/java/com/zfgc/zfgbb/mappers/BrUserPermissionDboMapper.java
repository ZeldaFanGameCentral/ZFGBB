package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrUserPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342535172-04:00", comments="Source Table: zfgbb.br_user_permission")
    long countByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342607789-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342662278-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342700896-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insert(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342727495-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insertSelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342759704-04:00", comments="Source Table: zfgbb.br_user_permission")
    List<BrUserPermissionDbo> selectByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342852881-04:00", comments="Source Table: zfgbb.br_user_permission")
    BrUserPermissionDbo selectByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342911299-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExampleSelective(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342959908-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExample(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.343013256-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKeySelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.343062834-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKey(BrUserPermissionDbo row);
}