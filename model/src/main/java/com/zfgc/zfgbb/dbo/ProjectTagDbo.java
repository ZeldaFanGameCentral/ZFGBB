package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ProjectTagDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729029572-04:00", comments="Source field: zfgbb.project_tag.project_tag_id")
    private Integer projectTagId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729049672-04:00", comments="Source field: zfgbb.project_tag.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729064341-04:00", comments="Source field: zfgbb.project_tag.tag_id")
    private Integer tagId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729038482-04:00", comments="Source field: zfgbb.project_tag.project_tag_id")
    public Integer getProjectTagId() {
        return projectTagId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729044392-04:00", comments="Source field: zfgbb.project_tag.project_tag_id")
    public void setProjectTagId(Integer projectTagId) {
        this.projectTagId = projectTagId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729054791-04:00", comments="Source field: zfgbb.project_tag.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729059671-04:00", comments="Source field: zfgbb.project_tag.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729069321-04:00", comments="Source field: zfgbb.project_tag.tag_id")
    public Integer getTagId() {
        return tagId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729074051-04:00", comments="Source field: zfgbb.project_tag.tag_id")
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public Integer getPkId() {
        return projectTagId;
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