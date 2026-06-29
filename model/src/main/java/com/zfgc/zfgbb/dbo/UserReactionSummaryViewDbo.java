package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserReactionSummaryViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.63463932-04:00", comments="Source field: zfgbb.user_reaction_summary_view.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635387816-04:00", comments="Source field: zfgbb.user_reaction_summary_view.reputation_points")
    private Long reputationPoints;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635519122-04:00", comments="Source field: zfgbb.user_reaction_summary_view.positive_count")
    private Long positiveCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635638819-04:00", comments="Source field: zfgbb.user_reaction_summary_view.negative_count")
    private Long negativeCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635763905-04:00", comments="Source field: zfgbb.user_reaction_summary_view.reaction_count")
    private Long reactionCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635121025-04:00", comments="Source field: zfgbb.user_reaction_summary_view.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635322948-04:00", comments="Source field: zfgbb.user_reaction_summary_view.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635434595-04:00", comments="Source field: zfgbb.user_reaction_summary_view.reputation_points")
    public Long getReputationPoints() {
        return reputationPoints;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635480843-04:00", comments="Source field: zfgbb.user_reaction_summary_view.reputation_points")
    public void setReputationPoints(Long reputationPoints) {
        this.reputationPoints = reputationPoints;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635560311-04:00", comments="Source field: zfgbb.user_reaction_summary_view.positive_count")
    public Long getPositiveCount() {
        return positiveCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.63560109-04:00", comments="Source field: zfgbb.user_reaction_summary_view.positive_count")
    public void setPositiveCount(Long positiveCount) {
        this.positiveCount = positiveCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635678677-04:00", comments="Source field: zfgbb.user_reaction_summary_view.negative_count")
    public Long getNegativeCount() {
        return negativeCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635718856-04:00", comments="Source field: zfgbb.user_reaction_summary_view.negative_count")
    public void setNegativeCount(Long negativeCount) {
        this.negativeCount = negativeCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635817343-04:00", comments="Source field: zfgbb.user_reaction_summary_view.reaction_count")
    public Long getReactionCount() {
        return reactionCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.635863152-04:00", comments="Source field: zfgbb.user_reaction_summary_view.reaction_count")
    public void setReactionCount(Long reactionCount) {
        this.reactionCount = reactionCount;
    }

    @Override
    public Integer getPkId() {
        return null;
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