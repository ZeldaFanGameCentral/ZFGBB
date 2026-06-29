package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDbo;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserReactionSummaryViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.637911418-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    long countByExample(UserReactionSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.639059672-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    int deleteByExample(UserReactionSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.639503918-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    int insert(UserReactionSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.639730971-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    int insertSelective(UserReactionSummaryViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.640250585-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    List<UserReactionSummaryViewDbo> selectByExample(UserReactionSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.640693091-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    int updateByExampleSelective(@Param("row") UserReactionSummaryViewDbo row, @Param("example") UserReactionSummaryViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.64107094-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    int updateByExample(@Param("row") UserReactionSummaryViewDbo row, @Param("example") UserReactionSummaryViewDboExample example);

    List<UserReactionSummaryViewDbo> selectByExampleWithLimits(UserReactionSummaryViewDboExample example);
}