package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class MessageHistoryDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687044709-04:00", comments="Source field: zfgbb.message_history.message_history_id")
    private Integer messageHistoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687075358-04:00", comments="Source field: zfgbb.message_history.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687099907-04:00", comments="Source field: zfgbb.message_history.message_text")
    private String messageText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687126406-04:00", comments="Source field: zfgbb.message_history.current_flag")
    private Boolean currentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687150125-04:00", comments="Source field: zfgbb.message_history.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687175055-04:00", comments="Source field: zfgbb.message_history.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687199334-04:00", comments="Source field: zfgbb.message_history.ip_address_id")
    private Integer ipAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687224663-04:00", comments="Source field: zfgbb.message_history.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687056138-04:00", comments="Source field: zfgbb.message_history.message_history_id")
    public Integer getMessageHistoryId() {
        return messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687066908-04:00", comments="Source field: zfgbb.message_history.message_history_id")
    public void setMessageHistoryId(Integer messageHistoryId) {
        this.messageHistoryId = messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687083988-04:00", comments="Source field: zfgbb.message_history.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687093177-04:00", comments="Source field: zfgbb.message_history.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687108057-04:00", comments="Source field: zfgbb.message_history.message_text")
    public String getMessageText() {
        return messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687119446-04:00", comments="Source field: zfgbb.message_history.message_text")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687134376-04:00", comments="Source field: zfgbb.message_history.current_flag")
    public Boolean getCurrentFlag() {
        return currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687142866-04:00", comments="Source field: zfgbb.message_history.current_flag")
    public void setCurrentFlag(Boolean currentFlag) {
        this.currentFlag = currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687158485-04:00", comments="Source field: zfgbb.message_history.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687167665-04:00", comments="Source field: zfgbb.message_history.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687183994-04:00", comments="Source field: zfgbb.message_history.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687192844-04:00", comments="Source field: zfgbb.message_history.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687206974-04:00", comments="Source field: zfgbb.message_history.ip_address_id")
    public Integer getIpAddressId() {
        return ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687217343-04:00", comments="Source field: zfgbb.message_history.ip_address_id")
    public void setIpAddressId(Integer ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687231883-04:00", comments="Source field: zfgbb.message_history.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687242253-04:00", comments="Source field: zfgbb.message_history.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return messageHistoryId;
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