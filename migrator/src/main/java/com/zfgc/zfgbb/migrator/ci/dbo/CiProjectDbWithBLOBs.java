package com.zfgc.zfgbb.migrator.ci.dbo;

import jakarta.annotation.Generated;

public class CiProjectDbWithBLOBs extends CiProjectDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719606512-04:00", comments="Source field: ci_projects.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719632211-04:00", comments="Source field: ci_projects.topic_template")
    private String topicTemplate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719615292-04:00", comments="Source field: ci_projects.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719625102-04:00", comments="Source field: ci_projects.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719639271-04:00", comments="Source field: ci_projects.topic_template")
    public String getTopicTemplate() {
        return topicTemplate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719647561-04:00", comments="Source field: ci_projects.topic_template")
    public void setTopicTemplate(String topicTemplate) {
        this.topicTemplate = topicTemplate;
    }
}