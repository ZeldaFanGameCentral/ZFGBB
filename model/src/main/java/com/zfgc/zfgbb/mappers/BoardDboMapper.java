package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437708819-04:00", comments="Source Table: zfgbb.board")
    long countByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437744668-04:00", comments="Source Table: zfgbb.board")
    int deleteByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437765977-04:00", comments="Source Table: zfgbb.board")
    int deleteByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437782617-04:00", comments="Source Table: zfgbb.board")
    int insert(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437798606-04:00", comments="Source Table: zfgbb.board")
    int insertSelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437816986-04:00", comments="Source Table: zfgbb.board")
    List<BoardDbo> selectByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437843525-04:00", comments="Source Table: zfgbb.board")
    BoardDbo selectByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437864414-04:00", comments="Source Table: zfgbb.board")
    int updateByExampleSelective(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437883983-04:00", comments="Source Table: zfgbb.board")
    int updateByExample(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437916752-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKeySelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.437943192-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKey(BoardDbo row);

    List<BoardDbo> selectByExampleWithLimits(BoardDboExample example);
}