package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PersonalMessageRecipientDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736202789-04:00", comments="Source field: zfgbb.personal_message_recipient.personal_message_recipient_id")
    private Integer personalMessageRecipientId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736232448-04:00", comments="Source field: zfgbb.personal_message_recipient.personal_message_id")
    private Integer personalMessageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736247857-04:00", comments="Source field: zfgbb.personal_message_recipient.recipient_user_id")
    private Integer recipientUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736261937-04:00", comments="Source field: zfgbb.personal_message_recipient.bcc")
    private Boolean bcc;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736275767-04:00", comments="Source field: zfgbb.personal_message_recipient.read_flag")
    private Boolean readFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736291986-04:00", comments="Source field: zfgbb.personal_message_recipient.deleted_flag")
    private Boolean deletedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736327445-04:00", comments="Source field: zfgbb.personal_message_recipient.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736350904-04:00", comments="Source field: zfgbb.personal_message_recipient.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736366944-04:00", comments="Source field: zfgbb.personal_message_recipient.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736214959-04:00", comments="Source field: zfgbb.personal_message_recipient.personal_message_recipient_id")
    public Integer getPersonalMessageRecipientId() {
        return personalMessageRecipientId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736224188-04:00", comments="Source field: zfgbb.personal_message_recipient.personal_message_recipient_id")
    public void setPersonalMessageRecipientId(Integer personalMessageRecipientId) {
        this.personalMessageRecipientId = personalMessageRecipientId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736238078-04:00", comments="Source field: zfgbb.personal_message_recipient.personal_message_id")
    public Integer getPersonalMessageId() {
        return personalMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736243098-04:00", comments="Source field: zfgbb.personal_message_recipient.personal_message_id")
    public void setPersonalMessageId(Integer personalMessageId) {
        this.personalMessageId = personalMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736252637-04:00", comments="Source field: zfgbb.personal_message_recipient.recipient_user_id")
    public Integer getRecipientUserId() {
        return recipientUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736257487-04:00", comments="Source field: zfgbb.personal_message_recipient.recipient_user_id")
    public void setRecipientUserId(Integer recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736266657-04:00", comments="Source field: zfgbb.personal_message_recipient.bcc")
    public Boolean getBcc() {
        return bcc;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736271267-04:00", comments="Source field: zfgbb.personal_message_recipient.bcc")
    public void setBcc(Boolean bcc) {
        this.bcc = bcc;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736280446-04:00", comments="Source field: zfgbb.personal_message_recipient.read_flag")
    public Boolean getReadFlag() {
        return readFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736285146-04:00", comments="Source field: zfgbb.personal_message_recipient.read_flag")
    public void setReadFlag(Boolean readFlag) {
        this.readFlag = readFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736308956-04:00", comments="Source field: zfgbb.personal_message_recipient.deleted_flag")
    public Boolean getDeletedFlag() {
        return deletedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736319075-04:00", comments="Source field: zfgbb.personal_message_recipient.deleted_flag")
    public void setDeletedFlag(Boolean deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736336135-04:00", comments="Source field: zfgbb.personal_message_recipient.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736344234-04:00", comments="Source field: zfgbb.personal_message_recipient.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736357864-04:00", comments="Source field: zfgbb.personal_message_recipient.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736362574-04:00", comments="Source field: zfgbb.personal_message_recipient.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736371804-04:00", comments="Source field: zfgbb.personal_message_recipient.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736377873-04:00", comments="Source field: zfgbb.personal_message_recipient.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return personalMessageRecipientId;
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