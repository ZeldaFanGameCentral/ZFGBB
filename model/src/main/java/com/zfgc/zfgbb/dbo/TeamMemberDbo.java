package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class TeamMemberDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727250448-04:00", comments="Source field: zfgbb.team_member.team_member_id")
    private Integer teamMemberId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727272867-04:00", comments="Source field: zfgbb.team_member.team_id")
    private Integer teamId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727306246-04:00", comments="Source field: zfgbb.team_member.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727334655-04:00", comments="Source field: zfgbb.team_member.member_role")
    private String memberRole;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727262367-04:00", comments="Source field: zfgbb.team_member.team_member_id")
    public Integer getTeamMemberId() {
        return teamMemberId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727268657-04:00", comments="Source field: zfgbb.team_member.team_member_id")
    public void setTeamMemberId(Integer teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727278127-04:00", comments="Source field: zfgbb.team_member.team_id")
    public Integer getTeamId() {
        return teamId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727283207-04:00", comments="Source field: zfgbb.team_member.team_id")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727318226-04:00", comments="Source field: zfgbb.team_member.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727327885-04:00", comments="Source field: zfgbb.team_member.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727339895-04:00", comments="Source field: zfgbb.team_member.member_role")
    public String getMemberRole() {
        return memberRole;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.727345935-04:00", comments="Source field: zfgbb.team_member.member_role")
    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    @Override
    public Integer getPkId() {
        return teamMemberId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}