package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserReactionSummaryViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.reputation_points")
    private Long reputationPoints;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.positive_count")
    private Long positiveCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.negative_count")
    private Long negativeCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.reaction_count")
    private Long reactionCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.reputation_points")
    public Long getReputationPoints() {
        return reputationPoints;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.reputation_points")
    public void setReputationPoints(Long reputationPoints) {
        this.reputationPoints = reputationPoints;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.positive_count")
    public Long getPositiveCount() {
        return positiveCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.positive_count")
    public void setPositiveCount(Long positiveCount) {
        this.positiveCount = positiveCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.negative_count")
    public Long getNegativeCount() {
        return negativeCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.negative_count")
    public void setNegativeCount(Long negativeCount) {
        this.negativeCount = negativeCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.reaction_count")
    public Long getReactionCount() {
        return reactionCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.user_reaction_summary_view.reaction_count")
    public void setReactionCount(Long reactionCount) {
        this.reactionCount = reactionCount;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_reaction_summary_view")
    public Integer getPkId() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_reaction_summary_view")
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_reaction_summary_view")
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}