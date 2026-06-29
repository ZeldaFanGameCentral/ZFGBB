package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PermissionGroupDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.740969401-04:00", comments="Source field: zfgbb.permission_group.permission_group_id")
    private Integer permissionGroupId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7409936-04:00", comments="Source field: zfgbb.permission_group.group_name")
    private String groupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741010769-04:00", comments="Source field: zfgbb.permission_group.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741029219-04:00", comments="Source field: zfgbb.permission_group.min_posts")
    private Integer minPosts;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741044568-04:00", comments="Source field: zfgbb.permission_group.parent_group")
    private Integer parentGroup;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741059348-04:00", comments="Source field: zfgbb.permission_group.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741074767-04:00", comments="Source field: zfgbb.permission_group.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741090217-04:00", comments="Source field: zfgbb.permission_group.star_image")
    private Integer starImage;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741104746-04:00", comments="Source field: zfgbb.permission_group.color")
    private String color;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741121666-04:00", comments="Source field: zfgbb.permission_group.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7409805-04:00", comments="Source field: zfgbb.permission_group.permission_group_id")
    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74098763-04:00", comments="Source field: zfgbb.permission_group.permission_group_id")
    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74099942-04:00", comments="Source field: zfgbb.permission_group.group_name")
    public String getGroupName() {
        return groupName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741005729-04:00", comments="Source field: zfgbb.permission_group.group_name")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741016139-04:00", comments="Source field: zfgbb.permission_group.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741024129-04:00", comments="Source field: zfgbb.permission_group.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741034609-04:00", comments="Source field: zfgbb.permission_group.min_posts")
    public Integer getMinPosts() {
        return minPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741039728-04:00", comments="Source field: zfgbb.permission_group.min_posts")
    public void setMinPosts(Integer minPosts) {
        this.minPosts = minPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741049588-04:00", comments="Source field: zfgbb.permission_group.parent_group")
    public Integer getParentGroup() {
        return parentGroup;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741054558-04:00", comments="Source field: zfgbb.permission_group.parent_group")
    public void setParentGroup(Integer parentGroup) {
        this.parentGroup = parentGroup;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741064948-04:00", comments="Source field: zfgbb.permission_group.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741070107-04:00", comments="Source field: zfgbb.permission_group.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741080227-04:00", comments="Source field: zfgbb.permission_group.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741085487-04:00", comments="Source field: zfgbb.permission_group.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741095097-04:00", comments="Source field: zfgbb.permission_group.star_image")
    public Integer getStarImage() {
        return starImage;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741099927-04:00", comments="Source field: zfgbb.permission_group.star_image")
    public void setStarImage(Integer starImage) {
        this.starImage = starImage;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741110026-04:00", comments="Source field: zfgbb.permission_group.color")
    public String getColor() {
        return color;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741116836-04:00", comments="Source field: zfgbb.permission_group.color")
    public void setColor(String color) {
        this.color = color;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741126656-04:00", comments="Source field: zfgbb.permission_group.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.741132106-04:00", comments="Source field: zfgbb.permission_group.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return permissionGroupId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}