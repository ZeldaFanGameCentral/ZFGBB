package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class WikiNamespaceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.case_mode")
    private String caseMode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.system_managed")
    private Boolean systemManaged;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.edit_permission_code")
    private String editPermissionCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.engine_role")
    private String engineRole;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.case_mode")
    public String getCaseMode() {
        return caseMode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.case_mode")
    public void setCaseMode(String caseMode) {
        this.caseMode = caseMode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.system_managed")
    public Boolean getSystemManaged() {
        return systemManaged;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.system_managed")
    public void setSystemManaged(Boolean systemManaged) {
        this.systemManaged = systemManaged;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.edit_permission_code")
    public String getEditPermissionCode() {
        return editPermissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.edit_permission_code")
    public void setEditPermissionCode(String editPermissionCode) {
        this.editPermissionCode = editPermissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.engine_role")
    public String getEngineRole() {
        return engineRole;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.wiki_namespace.engine_role")
    public void setEngineRole(String engineRole) {
        this.engineRole = engineRole;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public Integer getPkId() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}