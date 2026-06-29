package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ReactionTypeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699802042-04:00", comments="Source field: zfgbb.reaction_type.reaction_type_id")
    private Integer reactionTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699828761-04:00", comments="Source field: zfgbb.reaction_type.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69985224-04:00", comments="Source field: zfgbb.reaction_type.label")
    private String label;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69987202-04:00", comments="Source field: zfgbb.reaction_type.icon")
    private String icon;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699892919-04:00", comments="Source field: zfgbb.reaction_type.points")
    private Integer points;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699912828-04:00", comments="Source field: zfgbb.reaction_type.ordinal")
    private Integer ordinal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699813001-04:00", comments="Source field: zfgbb.reaction_type.reaction_type_id")
    public Integer getReactionTypeId() {
        return reactionTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699821661-04:00", comments="Source field: zfgbb.reaction_type.reaction_type_id")
    public void setReactionTypeId(Integer reactionTypeId) {
        this.reactionTypeId = reactionTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699835741-04:00", comments="Source field: zfgbb.reaction_type.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69984505-04:00", comments="Source field: zfgbb.reaction_type.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69985891-04:00", comments="Source field: zfgbb.reaction_type.label")
    public String getLabel() {
        return label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69986699-04:00", comments="Source field: zfgbb.reaction_type.label")
    public void setLabel(String label) {
        this.label = label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699878569-04:00", comments="Source field: zfgbb.reaction_type.icon")
    public String getIcon() {
        return icon;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699886459-04:00", comments="Source field: zfgbb.reaction_type.icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699899569-04:00", comments="Source field: zfgbb.reaction_type.points")
    public Integer getPoints() {
        return points;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699906389-04:00", comments="Source field: zfgbb.reaction_type.points")
    public void setPoints(Integer points) {
        this.points = points;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699919398-04:00", comments="Source field: zfgbb.reaction_type.ordinal")
    public Integer getOrdinal() {
        return ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699929118-04:00", comments="Source field: zfgbb.reaction_type.ordinal")
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public Integer getPkId() {
        return reactionTypeId;
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