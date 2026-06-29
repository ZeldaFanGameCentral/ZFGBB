package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.TeamDbo;
import com.zfgc.zfgbb.dbo.TeamDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725142543-04:00", comments="Source Table: zfgbb.team")
    long countByExample(TeamDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725177992-04:00", comments="Source Table: zfgbb.team")
    int deleteByExample(TeamDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725194121-04:00", comments="Source Table: zfgbb.team")
    int deleteByPrimaryKey(Integer teamId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725208201-04:00", comments="Source Table: zfgbb.team")
    int insert(TeamDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72525319-04:00", comments="Source Table: zfgbb.team")
    int insertSelective(TeamDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725383476-04:00", comments="Source Table: zfgbb.team")
    List<TeamDbo> selectByExample(TeamDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725402305-04:00", comments="Source Table: zfgbb.team")
    TeamDbo selectByPrimaryKey(Integer teamId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72556941-04:00", comments="Source Table: zfgbb.team")
    int updateByExampleSelective(@Param("row") TeamDbo row, @Param("example") TeamDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725677956-04:00", comments="Source Table: zfgbb.team")
    int updateByExample(@Param("row") TeamDbo row, @Param("example") TeamDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725709155-04:00", comments="Source Table: zfgbb.team")
    int updateByPrimaryKeySelective(TeamDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.725738505-04:00", comments="Source Table: zfgbb.team")
    int updateByPrimaryKey(TeamDbo row);

    List<TeamDbo> selectByExampleWithLimits(TeamDboExample example);
}