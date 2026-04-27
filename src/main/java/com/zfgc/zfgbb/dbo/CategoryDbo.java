package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class CategoryDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358011153-04:00", comments="Source field: zfgbb.category.category_id")
    private Integer categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358035533-04:00", comments="Source field: zfgbb.category.category_name")
    private String categoryName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358056912-04:00", comments="Source field: zfgbb.category.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358076991-04:00", comments="Source field: zfgbb.category.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35809714-04:00", comments="Source field: zfgbb.category.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.3581166-04:00", comments="Source field: zfgbb.category.parent_board_id")
    private Integer parentBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358136759-04:00", comments="Source field: zfgbb.category.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358021053-04:00", comments="Source field: zfgbb.category.category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358029483-04:00", comments="Source field: zfgbb.category.category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358042552-04:00", comments="Source field: zfgbb.category.category_name")
    public String getCategoryName() {
        return categoryName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358051132-04:00", comments="Source field: zfgbb.category.category_name")
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358063622-04:00", comments="Source field: zfgbb.category.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358071621-04:00", comments="Source field: zfgbb.category.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358085061-04:00", comments="Source field: zfgbb.category.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358091881-04:00", comments="Source field: zfgbb.category.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35810486-04:00", comments="Source field: zfgbb.category.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35811148-04:00", comments="Source field: zfgbb.category.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.3581245-04:00", comments="Source field: zfgbb.category.parent_board_id")
    public Integer getParentBoardId() {
        return parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358131509-04:00", comments="Source field: zfgbb.category.parent_board_id")
    public void setParentBoardId(Integer parentBoardId) {
        this.parentBoardId = parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358143019-04:00", comments="Source field: zfgbb.category.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358151689-04:00", comments="Source field: zfgbb.category.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return categoryId;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return updatedTs;
    }
}