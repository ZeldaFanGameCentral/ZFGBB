package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.231999847-04:00", comments="Source Table: zfgbb.board")
    long countByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232018877-04:00", comments="Source Table: zfgbb.board")
    int deleteByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232036016-04:00", comments="Source Table: zfgbb.board")
    int deleteByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232051436-04:00", comments="Source Table: zfgbb.board")
    int insert(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232064585-04:00", comments="Source Table: zfgbb.board")
    int insertSelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232082575-04:00", comments="Source Table: zfgbb.board")
    List<BoardDbo> selectByExample(BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232113614-04:00", comments="Source Table: zfgbb.board")
    BoardDbo selectByPrimaryKey(Integer boardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232149503-04:00", comments="Source Table: zfgbb.board")
    int updateByExampleSelective(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232187542-04:00", comments="Source Table: zfgbb.board")
    int updateByExample(@Param("row") BoardDbo row, @Param("example") BoardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.23222892-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKeySelective(BoardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.232275639-04:00", comments="Source Table: zfgbb.board")
    int updateByPrimaryKey(BoardDbo row);

    List<BoardDbo> selectByExampleWithLimits(BoardDboExample example);
}