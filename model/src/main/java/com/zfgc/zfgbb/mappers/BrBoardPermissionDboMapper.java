package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrBoardPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419722353-04:00", comments="Source Table: zfgbb.br_board_permission")
    long countByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419759942-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419787541-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.41984039-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insert(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419869079-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insertSelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419897878-04:00", comments="Source Table: zfgbb.br_board_permission")
    List<BrBoardPermissionDbo> selectByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419927497-04:00", comments="Source Table: zfgbb.br_board_permission")
    BrBoardPermissionDbo selectByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419955716-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExampleSelective(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419986655-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExample(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.420028474-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKeySelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.420078512-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKey(BrBoardPermissionDbo row);

    List<BrBoardPermissionDbo> selectByExampleWithLimits(BrBoardPermissionDboExample example);
}