package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ProjectDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715717477-04:00", comments="Source field: zfgbb.project.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715737836-04:00", comments="Source field: zfgbb.project.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715753225-04:00", comments="Source field: zfgbb.project.progress")
    private Short progress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715770895-04:00", comments="Source field: zfgbb.project.language")
    private String language;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715786384-04:00", comments="Source field: zfgbb.project.requirements")
    private String requirements;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715801604-04:00", comments="Source field: zfgbb.project.team_id")
    private Integer teamId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715726066-04:00", comments="Source field: zfgbb.project.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715732286-04:00", comments="Source field: zfgbb.project.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715741796-04:00", comments="Source field: zfgbb.project.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715749526-04:00", comments="Source field: zfgbb.project.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715758475-04:00", comments="Source field: zfgbb.project.progress")
    public Short getProgress() {
        return progress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715765925-04:00", comments="Source field: zfgbb.project.progress")
    public void setProgress(Short progress) {
        this.progress = progress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715775835-04:00", comments="Source field: zfgbb.project.language")
    public String getLanguage() {
        return language;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715781385-04:00", comments="Source field: zfgbb.project.language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715791334-04:00", comments="Source field: zfgbb.project.requirements")
    public String getRequirements() {
        return requirements;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715796694-04:00", comments="Source field: zfgbb.project.requirements")
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715806594-04:00", comments="Source field: zfgbb.project.team_id")
    public Integer getTeamId() {
        return teamId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715811714-04:00", comments="Source field: zfgbb.project.team_id")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public Integer getPkId() {
        return contentEntityId;
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