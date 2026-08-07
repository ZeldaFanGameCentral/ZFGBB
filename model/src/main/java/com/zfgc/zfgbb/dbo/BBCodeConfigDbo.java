package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BBCodeConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.end_tag")
    private String endTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    private Boolean processContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    private Boolean selfClosingFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.enabled_flag")
    private Boolean enabledFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.source_reference_attribute")
    private String sourceReferenceAttribute;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.source_reference_resolver")
    private String sourceReferenceResolver;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.markdown_equivalent")
    private String markdownEquivalent;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.markdown_canonical_flag")
    private Boolean markdownCanonicalFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.implicit_item_marker")
    private String implicitItemMarker;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.implicit_item_code")
    private String implicitItemCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_forum_flag")
    private Boolean honouredInForumFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_wiki_flag")
    private Boolean honouredInWikiFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_project_flag")
    private Boolean honouredInProjectFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_resource_flag")
    private Boolean honouredInResourceFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_signature_flag")
    private Boolean honouredInSignatureFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.end_tag")
    public String getEndTag() {
        return endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.end_tag")
    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public Boolean getProcessContentFlag() {
        return processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public void setProcessContentFlag(Boolean processContentFlag) {
        this.processContentFlag = processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    public Boolean getSelfClosingFlag() {
        return selfClosingFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    public void setSelfClosingFlag(Boolean selfClosingFlag) {
        this.selfClosingFlag = selfClosingFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.enabled_flag")
    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.enabled_flag")
    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.source_reference_attribute")
    public String getSourceReferenceAttribute() {
        return sourceReferenceAttribute;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.source_reference_attribute")
    public void setSourceReferenceAttribute(String sourceReferenceAttribute) {
        this.sourceReferenceAttribute = sourceReferenceAttribute;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.source_reference_resolver")
    public String getSourceReferenceResolver() {
        return sourceReferenceResolver;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.source_reference_resolver")
    public void setSourceReferenceResolver(String sourceReferenceResolver) {
        this.sourceReferenceResolver = sourceReferenceResolver;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.markdown_equivalent")
    public String getMarkdownEquivalent() {
        return markdownEquivalent;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.markdown_equivalent")
    public void setMarkdownEquivalent(String markdownEquivalent) {
        this.markdownEquivalent = markdownEquivalent;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.markdown_canonical_flag")
    public Boolean getMarkdownCanonicalFlag() {
        return markdownCanonicalFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.markdown_canonical_flag")
    public void setMarkdownCanonicalFlag(Boolean markdownCanonicalFlag) {
        this.markdownCanonicalFlag = markdownCanonicalFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.implicit_item_marker")
    public String getImplicitItemMarker() {
        return implicitItemMarker;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.implicit_item_marker")
    public void setImplicitItemMarker(String implicitItemMarker) {
        this.implicitItemMarker = implicitItemMarker;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.implicit_item_code")
    public String getImplicitItemCode() {
        return implicitItemCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.implicit_item_code")
    public void setImplicitItemCode(String implicitItemCode) {
        this.implicitItemCode = implicitItemCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_forum_flag")
    public Boolean getHonouredInForumFlag() {
        return honouredInForumFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_forum_flag")
    public void setHonouredInForumFlag(Boolean honouredInForumFlag) {
        this.honouredInForumFlag = honouredInForumFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_wiki_flag")
    public Boolean getHonouredInWikiFlag() {
        return honouredInWikiFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_wiki_flag")
    public void setHonouredInWikiFlag(Boolean honouredInWikiFlag) {
        this.honouredInWikiFlag = honouredInWikiFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_project_flag")
    public Boolean getHonouredInProjectFlag() {
        return honouredInProjectFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_project_flag")
    public void setHonouredInProjectFlag(Boolean honouredInProjectFlag) {
        this.honouredInProjectFlag = honouredInProjectFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_resource_flag")
    public Boolean getHonouredInResourceFlag() {
        return honouredInResourceFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_resource_flag")
    public void setHonouredInResourceFlag(Boolean honouredInResourceFlag) {
        this.honouredInResourceFlag = honouredInResourceFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_signature_flag")
    public Boolean getHonouredInSignatureFlag() {
        return honouredInSignatureFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.bb_code_config.honoured_in_signature_flag")
    public void setHonouredInSignatureFlag(Boolean honouredInSignatureFlag) {
        this.honouredInSignatureFlag = honouredInSignatureFlag;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public Integer getPkId() {
        return bbCodeConfigId;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}