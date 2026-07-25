package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class BackupJobDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public BackupJobDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andBackupIdIsNull() {
            addCriterion("backup_id is null");
            return (Criteria) this;
        }

        public Criteria andBackupIdIsNotNull() {
            addCriterion("backup_id is not null");
            return (Criteria) this;
        }

        public Criteria andBackupIdEqualTo(Object value) {
            addCriterion("backup_id =", value, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdNotEqualTo(Object value) {
            addCriterion("backup_id <>", value, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdGreaterThan(Object value) {
            addCriterion("backup_id >", value, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdGreaterThanOrEqualTo(Object value) {
            addCriterion("backup_id >=", value, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdLessThan(Object value) {
            addCriterion("backup_id <", value, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdLessThanOrEqualTo(Object value) {
            addCriterion("backup_id <=", value, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdIn(List<Object> values) {
            addCriterion("backup_id in", values, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdNotIn(List<Object> values) {
            addCriterion("backup_id not in", values, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdBetween(Object value1, Object value2) {
            addCriterion("backup_id between", value1, value2, "backupId");
            return (Criteria) this;
        }

        public Criteria andBackupIdNotBetween(Object value1, Object value2) {
            addCriterion("backup_id not between", value1, value2, "backupId");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state ilike", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not ilike", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andRevisionIsNull() {
            addCriterion("revision is null");
            return (Criteria) this;
        }

        public Criteria andRevisionIsNotNull() {
            addCriterion("revision is not null");
            return (Criteria) this;
        }

        public Criteria andRevisionEqualTo(Long value) {
            addCriterion("revision =", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotEqualTo(Long value) {
            addCriterion("revision <>", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionGreaterThan(Long value) {
            addCriterion("revision >", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionGreaterThanOrEqualTo(Long value) {
            addCriterion("revision >=", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionLessThan(Long value) {
            addCriterion("revision <", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionLessThanOrEqualTo(Long value) {
            addCriterion("revision <=", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionIn(List<Long> values) {
            addCriterion("revision in", values, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotIn(List<Long> values) {
            addCriterion("revision not in", values, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionBetween(Long value1, Long value2) {
            addCriterion("revision between", value1, value2, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotBetween(Long value1, Long value2) {
            addCriterion("revision not between", value1, value2, "revision");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdIsNull() {
            addCriterion("creator_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdIsNotNull() {
            addCriterion("creator_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdEqualTo(Integer value) {
            addCriterion("creator_user_id =", value, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdNotEqualTo(Integer value) {
            addCriterion("creator_user_id <>", value, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdGreaterThan(Integer value) {
            addCriterion("creator_user_id >", value, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator_user_id >=", value, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdLessThan(Integer value) {
            addCriterion("creator_user_id <", value, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("creator_user_id <=", value, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdIn(List<Integer> values) {
            addCriterion("creator_user_id in", values, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdNotIn(List<Integer> values) {
            addCriterion("creator_user_id not in", values, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdBetween(Integer value1, Integer value2) {
            addCriterion("creator_user_id between", value1, value2, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatorUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("creator_user_id not between", value1, value2, "creatorUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIsNull() {
            addCriterion("created_ts is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIsNotNull() {
            addCriterion("created_ts is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTsEqualTo(OffsetDateTime value) {
            addCriterion("created_ts =", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("created_ts <>", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThan(OffsetDateTime value) {
            addCriterion("created_ts >", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("created_ts >=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThan(OffsetDateTime value) {
            addCriterion("created_ts <", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("created_ts <=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIn(List<OffsetDateTime> values) {
            addCriterion("created_ts in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("created_ts not in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("created_ts between", value1, value2, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("created_ts not between", value1, value2, "createdTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIsNull() {
            addCriterion("updated_ts is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIsNotNull() {
            addCriterion("updated_ts is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts =", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts <>", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThan(OffsetDateTime value) {
            addCriterion("updated_ts >", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts >=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThan(OffsetDateTime value) {
            addCriterion("updated_ts <", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts <=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIn(List<OffsetDateTime> values) {
            addCriterion("updated_ts in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("updated_ts not in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("updated_ts between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("updated_ts not between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsIsNull() {
            addCriterion("expires_ts is null");
            return (Criteria) this;
        }

        public Criteria andExpiresTsIsNotNull() {
            addCriterion("expires_ts is not null");
            return (Criteria) this;
        }

        public Criteria andExpiresTsEqualTo(OffsetDateTime value) {
            addCriterion("expires_ts =", value, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsNotEqualTo(OffsetDateTime value) {
            addCriterion("expires_ts <>", value, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsGreaterThan(OffsetDateTime value) {
            addCriterion("expires_ts >", value, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("expires_ts >=", value, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsLessThan(OffsetDateTime value) {
            addCriterion("expires_ts <", value, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("expires_ts <=", value, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsIn(List<OffsetDateTime> values) {
            addCriterion("expires_ts in", values, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsNotIn(List<OffsetDateTime> values) {
            addCriterion("expires_ts not in", values, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("expires_ts between", value1, value2, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andExpiresTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("expires_ts not between", value1, value2, "expiresTs");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesIsNull() {
            addCriterion("archive_bytes is null");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesIsNotNull() {
            addCriterion("archive_bytes is not null");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesEqualTo(Long value) {
            addCriterion("archive_bytes =", value, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesNotEqualTo(Long value) {
            addCriterion("archive_bytes <>", value, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesGreaterThan(Long value) {
            addCriterion("archive_bytes >", value, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesGreaterThanOrEqualTo(Long value) {
            addCriterion("archive_bytes >=", value, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesLessThan(Long value) {
            addCriterion("archive_bytes <", value, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesLessThanOrEqualTo(Long value) {
            addCriterion("archive_bytes <=", value, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesIn(List<Long> values) {
            addCriterion("archive_bytes in", values, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesNotIn(List<Long> values) {
            addCriterion("archive_bytes not in", values, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesBetween(Long value1, Long value2) {
            addCriterion("archive_bytes between", value1, value2, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveBytesNotBetween(Long value1, Long value2) {
            addCriterion("archive_bytes not between", value1, value2, "archiveBytes");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256IsNull() {
            addCriterion("archive_sha256 is null");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256IsNotNull() {
            addCriterion("archive_sha256 is not null");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256EqualTo(String value) {
            addCriterion("archive_sha256 =", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256NotEqualTo(String value) {
            addCriterion("archive_sha256 <>", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256GreaterThan(String value) {
            addCriterion("archive_sha256 >", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256GreaterThanOrEqualTo(String value) {
            addCriterion("archive_sha256 >=", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256LessThan(String value) {
            addCriterion("archive_sha256 <", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256LessThanOrEqualTo(String value) {
            addCriterion("archive_sha256 <=", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256Like(String value) {
            addCriterion("archive_sha256 ilike", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256NotLike(String value) {
            addCriterion("archive_sha256 not ilike", value, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256In(List<String> values) {
            addCriterion("archive_sha256 in", values, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256NotIn(List<String> values) {
            addCriterion("archive_sha256 not in", values, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256Between(String value1, String value2) {
            addCriterion("archive_sha256 between", value1, value2, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andArchiveSha256NotBetween(String value1, String value2) {
            addCriterion("archive_sha256 not between", value1, value2, "archiveSha256");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleIsNull() {
            addCriterion("installer_compatible is null");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleIsNotNull() {
            addCriterion("installer_compatible is not null");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleEqualTo(Boolean value) {
            addCriterion("installer_compatible =", value, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleNotEqualTo(Boolean value) {
            addCriterion("installer_compatible <>", value, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleGreaterThan(Boolean value) {
            addCriterion("installer_compatible >", value, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("installer_compatible >=", value, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleLessThan(Boolean value) {
            addCriterion("installer_compatible <", value, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleLessThanOrEqualTo(Boolean value) {
            addCriterion("installer_compatible <=", value, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleIn(List<Boolean> values) {
            addCriterion("installer_compatible in", values, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleNotIn(List<Boolean> values) {
            addCriterion("installer_compatible not in", values, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleBetween(Boolean value1, Boolean value2) {
            addCriterion("installer_compatible between", value1, value2, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerCompatibleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("installer_compatible not between", value1, value2, "installerCompatible");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdIsNull() {
            addCriterion("installer_anchor_administrator_id is null");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdIsNotNull() {
            addCriterion("installer_anchor_administrator_id is not null");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdEqualTo(Integer value) {
            addCriterion("installer_anchor_administrator_id =", value, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdNotEqualTo(Integer value) {
            addCriterion("installer_anchor_administrator_id <>", value, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdGreaterThan(Integer value) {
            addCriterion("installer_anchor_administrator_id >", value, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("installer_anchor_administrator_id >=", value, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdLessThan(Integer value) {
            addCriterion("installer_anchor_administrator_id <", value, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdLessThanOrEqualTo(Integer value) {
            addCriterion("installer_anchor_administrator_id <=", value, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdIn(List<Integer> values) {
            addCriterion("installer_anchor_administrator_id in", values, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdNotIn(List<Integer> values) {
            addCriterion("installer_anchor_administrator_id not in", values, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdBetween(Integer value1, Integer value2) {
            addCriterion("installer_anchor_administrator_id between", value1, value2, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andInstallerAnchorAdministratorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("installer_anchor_administrator_id not between", value1, value2, "installerAnchorAdministratorId");
            return (Criteria) this;
        }

        public Criteria andLastErrorIsNull() {
            addCriterion("last_error is null");
            return (Criteria) this;
        }

        public Criteria andLastErrorIsNotNull() {
            addCriterion("last_error is not null");
            return (Criteria) this;
        }

        public Criteria andLastErrorEqualTo(String value) {
            addCriterion("last_error =", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorNotEqualTo(String value) {
            addCriterion("last_error <>", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorGreaterThan(String value) {
            addCriterion("last_error >", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorGreaterThanOrEqualTo(String value) {
            addCriterion("last_error >=", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorLessThan(String value) {
            addCriterion("last_error <", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorLessThanOrEqualTo(String value) {
            addCriterion("last_error <=", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorLike(String value) {
            addCriterion("last_error ilike", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorNotLike(String value) {
            addCriterion("last_error not ilike", value, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorIn(List<String> values) {
            addCriterion("last_error in", values, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorNotIn(List<String> values) {
            addCriterion("last_error not in", values, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorBetween(String value1, String value2) {
            addCriterion("last_error between", value1, value2, "lastError");
            return (Criteria) this;
        }

        public Criteria andLastErrorNotBetween(String value1, String value2) {
            addCriterion("last_error not between", value1, value2, "lastError");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.backup_job")
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}