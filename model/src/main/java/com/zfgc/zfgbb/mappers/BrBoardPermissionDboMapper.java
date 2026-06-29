package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrBoardPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662691427-04:00", comments="Source Table: zfgbb.br_board_permission")
    long countByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662722246-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662753205-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662780984-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insert(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662807803-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insertSelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662839802-04:00", comments="Source Table: zfgbb.br_board_permission")
    List<BrBoardPermissionDbo> selectByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662873381-04:00", comments="Source Table: zfgbb.br_board_permission")
    BrBoardPermissionDbo selectByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66290709-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExampleSelective(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662961198-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExample(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.663046896-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKeySelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.663113354-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKey(BrBoardPermissionDbo row);

    List<BrBoardPermissionDbo> selectByExampleWithLimits(BrBoardPermissionDboExample example);
}