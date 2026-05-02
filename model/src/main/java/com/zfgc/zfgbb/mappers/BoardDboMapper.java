package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504704639-04:00", comments="Source Table: zfgbb.board")
    long countByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504723768-04:00", comments="Source Table: zfgbb.board")
    int deleteByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504739768-04:00", comments="Source Table: zfgbb.board")
    int deleteByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504754727-04:00", comments="Source Table: zfgbb.board")
    int insert(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504767307-04:00", comments="Source Table: zfgbb.board")
    int insertSelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504785116-04:00", comments="Source Table: zfgbb.board")
    List<BoardDbo> selectByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504819345-04:00", comments="Source Table: zfgbb.board")
    BoardDbo selectByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504843884-04:00", comments="Source Table: zfgbb.board")
    int updateByExampleSelective(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504868174-04:00", comments="Source Table: zfgbb.board")
    int updateByExample(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504911372-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKeySelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.504957551-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKey(BoardDbo row);

    List<BoardDbo> selectByExampleWithLimits(BoardDboExample example);
}