package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrBoardPermissionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211896545-04:00", comments="Source Table: zfgbb.br_board_permission")
    long countByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211928204-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211953934-04:00", comments="Source Table: zfgbb.br_board_permission")
    int deleteByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211980743-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insert(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.212006382-04:00", comments="Source Table: zfgbb.br_board_permission")
    int insertSelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.212049671-04:00", comments="Source Table: zfgbb.br_board_permission")
    List<BrBoardPermissionDbo> selectByExample(BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.212087269-04:00", comments="Source Table: zfgbb.br_board_permission")
    BrBoardPermissionDbo selectByPrimaryKey(Integer brBoardPermissionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.212137738-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExampleSelective(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.212195716-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByExample(@Param("row") BrBoardPermissionDbo row, @Param("example") BrBoardPermissionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.212282113-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKeySelective(BrBoardPermissionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21237202-04:00", comments="Source Table: zfgbb.br_board_permission")
    int updateByPrimaryKey(BrBoardPermissionDbo row);

    List<BrBoardPermissionDbo> selectByExampleWithLimits(BrBoardPermissionDboExample example);
}