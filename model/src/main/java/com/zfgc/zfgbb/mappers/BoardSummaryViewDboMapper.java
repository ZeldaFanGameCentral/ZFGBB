package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardSummaryViewDbo;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardSummaryViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470382776-04:00", comments="Source Table: zfgbb.board_summary")
    long countByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470400615-04:00", comments="Source Table: zfgbb.board_summary")
    int deleteByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470414425-04:00", comments="Source Table: zfgbb.board_summary")
    int insert(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470424464-04:00", comments="Source Table: zfgbb.board_summary")
    int insertSelective(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470437524-04:00", comments="Source Table: zfgbb.board_summary")
    List<BoardSummaryViewDbo> selectByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470453983-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExampleSelective(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470469513-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExample(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);

    List<BoardSummaryViewDbo> selectByExampleWithLimits(BoardSummaryViewDboExample example);
}