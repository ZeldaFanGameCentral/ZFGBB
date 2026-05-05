package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrUserPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417708978-04:00", comments="Source Table: zfgbb.br_user_permission")
    long countByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417750296-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417779455-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417807124-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insert(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417833074-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insertSelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417865503-04:00", comments="Source Table: zfgbb.br_user_permission")
    List<BrUserPermissionDbo> selectByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417897382-04:00", comments="Source Table: zfgbb.br_user_permission")
    BrUserPermissionDbo selectByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417930691-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExampleSelective(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.418029027-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExample(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.418103755-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKeySelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.418194622-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKey(BrUserPermissionDbo row);

    List<BrUserPermissionDbo> selectByExampleWithLimits(BrUserPermissionDboExample example);
}