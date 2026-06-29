package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class CategoryDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680410235-04:00", comments="Source field: zfgbb.category.category_id")
    private Integer categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680472563-04:00", comments="Source field: zfgbb.category.category_name")
    private String categoryName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680503862-04:00", comments="Source field: zfgbb.category.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680533721-04:00", comments="Source field: zfgbb.category.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68057431-04:00", comments="Source field: zfgbb.category.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680613429-04:00", comments="Source field: zfgbb.category.parent_board_id")
    private Integer parentBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680642398-04:00", comments="Source field: zfgbb.category.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680669457-04:00", comments="Source field: zfgbb.category.category_order")
    private Short categoryOrder;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680447504-04:00", comments="Source field: zfgbb.category.category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680463144-04:00", comments="Source field: zfgbb.category.category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680484193-04:00", comments="Source field: zfgbb.category.category_name")
    public String getCategoryName() {
        return categoryName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680497293-04:00", comments="Source field: zfgbb.category.category_name")
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680514432-04:00", comments="Source field: zfgbb.category.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680525942-04:00", comments="Source field: zfgbb.category.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680546291-04:00", comments="Source field: zfgbb.category.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.6805649-04:00", comments="Source field: zfgbb.category.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68059319-04:00", comments="Source field: zfgbb.category.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680606669-04:00", comments="Source field: zfgbb.category.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680623779-04:00", comments="Source field: zfgbb.category.parent_board_id")
    public Integer getParentBoardId() {
        return parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680635328-04:00", comments="Source field: zfgbb.category.parent_board_id")
    public void setParentBoardId(Integer parentBoardId) {
        this.parentBoardId = parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680651208-04:00", comments="Source field: zfgbb.category.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680661717-04:00", comments="Source field: zfgbb.category.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680678217-04:00", comments="Source field: zfgbb.category.category_order")
    public Short getCategoryOrder() {
        return categoryOrder;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680686827-04:00", comments="Source field: zfgbb.category.category_order")
    public void setCategoryOrder(Short categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    @Override
    public Integer getPkId() {
        return categoryId;
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