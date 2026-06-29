package com.zfgc.zfgbb.migrator.ci.dbo;

import jakarta.annotation.Generated;

public class CiPotmDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050129771-04:00", comments="Source field: ci_potms.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.05014859-04:00", comments="Source field: ci_potms.preview")
    private String preview;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.05016495-04:00", comments="Source field: ci_potms.project_id")
    private Integer projectId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050179039-04:00", comments="Source field: ci_potms.project_title")
    private String projectTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050193469-04:00", comments="Source field: ci_potms.time")
    private Integer time;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050138071-04:00", comments="Source field: ci_potms.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050143741-04:00", comments="Source field: ci_potms.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.05015385-04:00", comments="Source field: ci_potms.preview")
    public String getPreview() {
        return preview;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.05015944-04:00", comments="Source field: ci_potms.preview")
    public void setPreview(String preview) {
        this.preview = preview;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.05016982-04:00", comments="Source field: ci_potms.project_id")
    public Integer getProjectId() {
        return projectId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.05017474-04:00", comments="Source field: ci_potms.project_id")
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050183739-04:00", comments="Source field: ci_potms.project_title")
    public String getProjectTitle() {
        return projectTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050189019-04:00", comments="Source field: ci_potms.project_title")
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050198119-04:00", comments="Source field: ci_potms.time")
    public Integer getTime() {
        return time;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050202749-04:00", comments="Source field: ci_potms.time")
    public void setTime(Integer time) {
        this.time = time;
    }
}