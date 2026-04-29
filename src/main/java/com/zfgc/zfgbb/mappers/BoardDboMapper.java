package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359236673-04:00", comments="Source Table: zfgbb.board")
    long countByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359253872-04:00", comments="Source Table: zfgbb.board")
    int deleteByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359273862-04:00", comments="Source Table: zfgbb.board")
    int deleteByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359296851-04:00", comments="Source Table: zfgbb.board")
    int insert(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359309571-04:00", comments="Source Table: zfgbb.board")
    int insertSelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35932371-04:00", comments="Source Table: zfgbb.board")
    List<BoardDbo> selectByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35933783-04:00", comments="Source Table: zfgbb.board")
    BoardDbo selectByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359351779-04:00", comments="Source Table: zfgbb.board")
    int updateByExampleSelective(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359427217-04:00", comments="Source Table: zfgbb.board")
    int updateByExample(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359448916-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKeySelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.359479285-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKey(BoardDbo row);
}