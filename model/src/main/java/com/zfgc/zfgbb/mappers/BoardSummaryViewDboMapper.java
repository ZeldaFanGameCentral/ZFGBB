package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardSummaryViewDbo;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardSummaryViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264306742-04:00", comments="Source Table: zfgbb.board_summary")
    long countByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264323812-04:00", comments="Source Table: zfgbb.board_summary")
    int deleteByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264338321-04:00", comments="Source Table: zfgbb.board_summary")
    int insert(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264348161-04:00", comments="Source Table: zfgbb.board_summary")
    int insertSelective(BoardSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.26436124-04:00", comments="Source Table: zfgbb.board_summary")
    List<BoardSummaryViewDbo> selectByExample(BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.26437821-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExampleSelective(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264394109-04:00", comments="Source Table: zfgbb.board_summary")
    int updateByExample(@Param("row") BoardSummaryViewDbo row, @Param("example") BoardSummaryViewDboExample example);

    List<BoardSummaryViewDbo> selectByExampleWithLimits(BoardSummaryViewDboExample example);
}