package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682338755-04:00", comments="Source Table: zfgbb.board")
    long countByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682365904-04:00", comments="Source Table: zfgbb.board")
    int deleteByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682385494-04:00", comments="Source Table: zfgbb.board")
    int deleteByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682401243-04:00", comments="Source Table: zfgbb.board")
    int insert(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682417083-04:00", comments="Source Table: zfgbb.board")
    int insertSelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682435472-04:00", comments="Source Table: zfgbb.board")
    List<BoardDbo> selectByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682461871-04:00", comments="Source Table: zfgbb.board")
    BoardDbo selectByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682485021-04:00", comments="Source Table: zfgbb.board")
    int updateByExampleSelective(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68250952-04:00", comments="Source Table: zfgbb.board")
    int updateByExample(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682535089-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKeySelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.682567898-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKey(BoardDbo row);

    List<BoardDbo> selectByExampleWithLimits(BoardDboExample example);
}