package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PersonalMessageDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735132432-04:00", comments="Source field: zfgbb.personal_message.personal_message_id")
    private Integer personalMessageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735152132-04:00", comments="Source field: zfgbb.personal_message.personal_message_conversation_id")
    private Integer personalMessageConversationId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735167281-04:00", comments="Source field: zfgbb.personal_message.sender_user_id")
    private Integer senderUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735181651-04:00", comments="Source field: zfgbb.personal_message.sender_name")
    private String senderName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7351993-04:00", comments="Source field: zfgbb.personal_message.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7352144-04:00", comments="Source field: zfgbb.personal_message.sent_ts")
    private OffsetDateTime sentTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735229299-04:00", comments="Source field: zfgbb.personal_message.deleted_by_sender")
    private Boolean deletedBySender;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735243569-04:00", comments="Source field: zfgbb.personal_message.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735258108-04:00", comments="Source field: zfgbb.personal_message.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735272158-04:00", comments="Source field: zfgbb.personal_message.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735140982-04:00", comments="Source field: zfgbb.personal_message.personal_message_id")
    public Integer getPersonalMessageId() {
        return personalMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735146782-04:00", comments="Source field: zfgbb.personal_message.personal_message_id")
    public void setPersonalMessageId(Integer personalMessageId) {
        this.personalMessageId = personalMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735157521-04:00", comments="Source field: zfgbb.personal_message.personal_message_conversation_id")
    public Integer getPersonalMessageConversationId() {
        return personalMessageConversationId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735162561-04:00", comments="Source field: zfgbb.personal_message.personal_message_conversation_id")
    public void setPersonalMessageConversationId(Integer personalMessageConversationId) {
        this.personalMessageConversationId = personalMessageConversationId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735172221-04:00", comments="Source field: zfgbb.personal_message.sender_user_id")
    public Integer getSenderUserId() {
        return senderUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735177051-04:00", comments="Source field: zfgbb.personal_message.sender_user_id")
    public void setSenderUserId(Integer senderUserId) {
        this.senderUserId = senderUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73518639-04:00", comments="Source field: zfgbb.personal_message.sender_name")
    public String getSenderName() {
        return senderName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73519448-04:00", comments="Source field: zfgbb.personal_message.sender_name")
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73520463-04:00", comments="Source field: zfgbb.personal_message.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73520988-04:00", comments="Source field: zfgbb.personal_message.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735219759-04:00", comments="Source field: zfgbb.personal_message.sent_ts")
    public OffsetDateTime getSentTs() {
        return sentTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735224739-04:00", comments="Source field: zfgbb.personal_message.sent_ts")
    public void setSentTs(OffsetDateTime sentTs) {
        this.sentTs = sentTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735234039-04:00", comments="Source field: zfgbb.personal_message.deleted_by_sender")
    public Boolean getDeletedBySender() {
        return deletedBySender;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735238819-04:00", comments="Source field: zfgbb.personal_message.deleted_by_sender")
    public void setDeletedBySender(Boolean deletedBySender) {
        this.deletedBySender = deletedBySender;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735248689-04:00", comments="Source field: zfgbb.personal_message.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735253518-04:00", comments="Source field: zfgbb.personal_message.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735263088-04:00", comments="Source field: zfgbb.personal_message.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735269118-04:00", comments="Source field: zfgbb.personal_message.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735276768-04:00", comments="Source field: zfgbb.personal_message.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735281917-04:00", comments="Source field: zfgbb.personal_message.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return personalMessageId;
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