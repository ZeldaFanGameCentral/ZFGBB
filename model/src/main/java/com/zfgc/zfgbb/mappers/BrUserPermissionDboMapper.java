package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrUserPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209744174-04:00", comments="Source Table: zfgbb.br_user_permission")
    long countByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209789772-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209825081-04:00", comments="Source Table: zfgbb.br_user_permission")
    int deleteByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.20985727-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insert(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209883569-04:00", comments="Source Table: zfgbb.br_user_permission")
    int insertSelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209916928-04:00", comments="Source Table: zfgbb.br_user_permission")
    List<BrUserPermissionDbo> selectByExample(BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209950397-04:00", comments="Source Table: zfgbb.br_user_permission")
    BrUserPermissionDbo selectByPrimaryKey(Integer brUserPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209983496-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExampleSelective(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.210033355-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByExample(@Param("row") BrUserPermissionDbo row, @Param("example") BrUserPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.210100252-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKeySelective(BrUserPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.210215719-04:00", comments="Source Table: zfgbb.br_user_permission")
    int updateByPrimaryKey(BrUserPermissionDbo row);

    List<BrUserPermissionDbo> selectByExampleWithLimits(BrUserPermissionDboExample example);
}