package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PersonalMessageConversationDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734134323-04:00", comments="Source field: zfgbb.personal_message_conversation.personal_message_conversation_id")
    private Integer personalMessageConversationId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734158833-04:00", comments="Source field: zfgbb.personal_message_conversation.subject")
    private String subject;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734175402-04:00", comments="Source field: zfgbb.personal_message_conversation.started_ts")
    private OffsetDateTime startedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734192172-04:00", comments="Source field: zfgbb.personal_message_conversation.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734207831-04:00", comments="Source field: zfgbb.personal_message_conversation.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734221961-04:00", comments="Source field: zfgbb.personal_message_conversation.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734147363-04:00", comments="Source field: zfgbb.personal_message_conversation.personal_message_conversation_id")
    public Integer getPersonalMessageConversationId() {
        return personalMessageConversationId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734155013-04:00", comments="Source field: zfgbb.personal_message_conversation.personal_message_conversation_id")
    public void setPersonalMessageConversationId(Integer personalMessageConversationId) {
        this.personalMessageConversationId = personalMessageConversationId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734164282-04:00", comments="Source field: zfgbb.personal_message_conversation.subject")
    public String getSubject() {
        return subject;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734171942-04:00", comments="Source field: zfgbb.personal_message_conversation.subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734182542-04:00", comments="Source field: zfgbb.personal_message_conversation.started_ts")
    public OffsetDateTime getStartedTs() {
        return startedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734187622-04:00", comments="Source field: zfgbb.personal_message_conversation.started_ts")
    public void setStartedTs(OffsetDateTime startedTs) {
        this.startedTs = startedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734197181-04:00", comments="Source field: zfgbb.personal_message_conversation.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734203161-04:00", comments="Source field: zfgbb.personal_message_conversation.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734212831-04:00", comments="Source field: zfgbb.personal_message_conversation.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734217501-04:00", comments="Source field: zfgbb.personal_message_conversation.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73422667-04:00", comments="Source field: zfgbb.personal_message_conversation.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7342322-04:00", comments="Source field: zfgbb.personal_message_conversation.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return personalMessageConversationId;
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