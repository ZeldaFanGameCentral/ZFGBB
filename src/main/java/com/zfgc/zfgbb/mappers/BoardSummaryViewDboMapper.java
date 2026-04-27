package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardSummaryViewDbo;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardSummaryViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381185932-04:00", comments="Source Table: zfgbb.board_summary")
    long countByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381203081-04:00", comments="Source Table: zfgbb.board_summary")
    int deleteByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381220361-04:00", comments="Source Table: zfgbb.board_summary")
    int insert(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38123649-04:00", comments="Source Table: zfgbb.board_summary")
    int insertSelective(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38125372-04:00", comments="Source Table: zfgbb.board_summary")
    List<BoardSummaryViewDbo> selectByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381266689-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExampleSelective(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381278019-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExample(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);
}