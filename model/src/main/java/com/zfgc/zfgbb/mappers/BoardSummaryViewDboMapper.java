package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardSummaryViewDbo;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardSummaryViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750432816-04:00", comments="Source Table: zfgbb.board_summary")
    long countByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750447506-04:00", comments="Source Table: zfgbb.board_summary")
    int deleteByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750462835-04:00", comments="Source Table: zfgbb.board_summary")
    int insert(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750474075-04:00", comments="Source Table: zfgbb.board_summary")
    int insertSelective(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750488534-04:00", comments="Source Table: zfgbb.board_summary")
    List<BoardSummaryViewDbo> selectByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750511854-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExampleSelective(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750528353-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExample(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);

    List<BoardSummaryViewDbo> selectByExampleWithLimits(BoardSummaryViewDboExample example);
}