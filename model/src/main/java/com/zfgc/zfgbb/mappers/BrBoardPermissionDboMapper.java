package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrBoardPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486907877-04:00", comments="Source Table: zfgbb.br_board_permission")
    long countByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486940336-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486966365-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486991654-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insert(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.487016403-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insertSelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.487047422-04:00", comments="Source Table: zfgbb.br_board_permission")
    List<BrBoardPermissionDbo> selectByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.487076371-04:00", comments="Source Table: zfgbb.br_board_permission")
    BrBoardPermissionDbo selectByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.487105831-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExampleSelective(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.487136739-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExample(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.487175098-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKeySelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.487228406-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKey(BrBoardPermissionDbo row);

    List<BrBoardPermissionDbo> selectByExampleWithLimits(BrBoardPermissionDboExample example);
}