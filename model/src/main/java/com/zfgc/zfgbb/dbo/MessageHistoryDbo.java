package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class MessageHistoryDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442264874-04:00", comments="Source field: zfgbb.message_history.message_history_id")
    private Integer messageHistoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442295383-04:00", comments="Source field: zfgbb.message_history.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442329331-04:00", comments="Source field: zfgbb.message_history.message_text")
    private String messageText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44237921-04:00", comments="Source field: zfgbb.message_history.current_flag")
    private Boolean currentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442407119-04:00", comments="Source field: zfgbb.message_history.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442433638-04:00", comments="Source field: zfgbb.message_history.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442457037-04:00", comments="Source field: zfgbb.message_history.ip_address_id")
    private Integer ipAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442478917-04:00", comments="Source field: zfgbb.message_history.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442503046-04:00", comments="Source field: zfgbb.message_history.legacy_id")
    private Integer legacyId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442276623-04:00", comments="Source field: zfgbb.message_history.message_history_id")
    public Integer getMessageHistoryId() {
        return messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442287533-04:00", comments="Source field: zfgbb.message_history.message_history_id")
    public void setMessageHistoryId(Integer messageHistoryId) {
        this.messageHistoryId = messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442303392-04:00", comments="Source field: zfgbb.message_history.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442317022-04:00", comments="Source field: zfgbb.message_history.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442352311-04:00", comments="Source field: zfgbb.message_history.message_text")
    public String getMessageText() {
        return messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44237094-04:00", comments="Source field: zfgbb.message_history.message_text")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442390299-04:00", comments="Source field: zfgbb.message_history.current_flag")
    public Boolean getCurrentFlag() {
        return currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442399949-04:00", comments="Source field: zfgbb.message_history.current_flag")
    public void setCurrentFlag(Boolean currentFlag) {
        this.currentFlag = currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442416009-04:00", comments="Source field: zfgbb.message_history.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442426808-04:00", comments="Source field: zfgbb.message_history.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442442358-04:00", comments="Source field: zfgbb.message_history.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442450298-04:00", comments="Source field: zfgbb.message_history.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442464537-04:00", comments="Source field: zfgbb.message_history.ip_address_id")
    public Integer getIpAddressId() {
        return ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442472277-04:00", comments="Source field: zfgbb.message_history.ip_address_id")
    public void setIpAddressId(Integer ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442486176-04:00", comments="Source field: zfgbb.message_history.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442495986-04:00", comments="Source field: zfgbb.message_history.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442511106-04:00", comments="Source field: zfgbb.message_history.legacy_id")
    public Integer getLegacyId() {
        return legacyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442522395-04:00", comments="Source field: zfgbb.message_history.legacy_id")
    public void setLegacyId(Integer legacyId) {
        this.legacyId = legacyId;
    }

    @Override
    public Integer getPkId() {
        return messageHistoryId;
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