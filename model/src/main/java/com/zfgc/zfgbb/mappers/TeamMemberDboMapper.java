package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.TeamMemberDbo;
import com.zfgc.zfgbb.dbo.TeamMemberDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamMemberDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727418402-04:00", comments="Source Table: zfgbb.team_member")
    long countByExample(TeamMemberDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727439452-04:00", comments="Source Table: zfgbb.team_member")
    int deleteByExample(TeamMemberDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727453411-04:00", comments="Source Table: zfgbb.team_member")
    int deleteByPrimaryKey(Integer teamMemberId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727473601-04:00", comments="Source Table: zfgbb.team_member")
    int insert(TeamMemberDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72748695-04:00", comments="Source Table: zfgbb.team_member")
    int insertSelective(TeamMemberDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72750769-04:00", comments="Source Table: zfgbb.team_member")
    List<TeamMemberDbo> selectByExample(TeamMemberDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727523839-04:00", comments="Source Table: zfgbb.team_member")
    TeamMemberDbo selectByPrimaryKey(Integer teamMemberId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727540429-04:00", comments="Source Table: zfgbb.team_member")
    int updateByExampleSelective(@Param("row") TeamMemberDbo row, @Param("example") TeamMemberDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727556318-04:00", comments="Source Table: zfgbb.team_member")
    int updateByExample(@Param("row") TeamMemberDbo row, @Param("example") TeamMemberDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727576337-04:00", comments="Source Table: zfgbb.team_member")
    int updateByPrimaryKeySelective(TeamMemberDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727596597-04:00", comments="Source Table: zfgbb.team_member")
    int updateByPrimaryKey(TeamMemberDbo row);

    List<TeamMemberDbo> selectByExampleWithLimits(TeamMemberDboExample example);
}