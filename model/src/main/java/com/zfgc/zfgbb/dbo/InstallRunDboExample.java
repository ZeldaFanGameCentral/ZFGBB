package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class InstallRunDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public InstallRunDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
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

        public Criteria andInstallIdIsNull() {
            addCriterion("install_id is null");
            return (Criteria) this;
        }

        public Criteria andInstallIdIsNotNull() {
            addCriterion("install_id is not null");
            return (Criteria) this;
        }

        public Criteria andInstallIdEqualTo(Short value) {
            addCriterion("install_id =", value, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdNotEqualTo(Short value) {
            addCriterion("install_id <>", value, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdGreaterThan(Short value) {
            addCriterion("install_id >", value, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdGreaterThanOrEqualTo(Short value) {
            addCriterion("install_id >=", value, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdLessThan(Short value) {
            addCriterion("install_id <", value, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdLessThanOrEqualTo(Short value) {
            addCriterion("install_id <=", value, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdIn(List<Short> values) {
            addCriterion("install_id in", values, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdNotIn(List<Short> values) {
            addCriterion("install_id not in", values, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdBetween(Short value1, Short value2) {
            addCriterion("install_id between", value1, value2, "installId");
            return (Criteria) this;
        }

        public Criteria andInstallIdNotBetween(Short value1, Short value2) {
            addCriterion("install_id not between", value1, value2, "installId");
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

        public Criteria andLastCompletedStateIsNull() {
            addCriterion("last_completed_state is null");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateIsNotNull() {
            addCriterion("last_completed_state is not null");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateEqualTo(String value) {
            addCriterion("last_completed_state =", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateNotEqualTo(String value) {
            addCriterion("last_completed_state <>", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateGreaterThan(String value) {
            addCriterion("last_completed_state >", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateGreaterThanOrEqualTo(String value) {
            addCriterion("last_completed_state >=", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateLessThan(String value) {
            addCriterion("last_completed_state <", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateLessThanOrEqualTo(String value) {
            addCriterion("last_completed_state <=", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateLike(String value) {
            addCriterion("last_completed_state ilike", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateNotLike(String value) {
            addCriterion("last_completed_state not ilike", value, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateIn(List<String> values) {
            addCriterion("last_completed_state in", values, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateNotIn(List<String> values) {
            addCriterion("last_completed_state not in", values, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateBetween(String value1, String value2) {
            addCriterion("last_completed_state between", value1, value2, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateNotBetween(String value1, String value2) {
            addCriterion("last_completed_state not between", value1, value2, "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andRequestVersionIsNull() {
            addCriterion("request_version is null");
            return (Criteria) this;
        }

        public Criteria andRequestVersionIsNotNull() {
            addCriterion("request_version is not null");
            return (Criteria) this;
        }

        public Criteria andRequestVersionEqualTo(Integer value) {
            addCriterion("request_version =", value, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionNotEqualTo(Integer value) {
            addCriterion("request_version <>", value, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionGreaterThan(Integer value) {
            addCriterion("request_version >", value, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("request_version >=", value, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionLessThan(Integer value) {
            addCriterion("request_version <", value, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionLessThanOrEqualTo(Integer value) {
            addCriterion("request_version <=", value, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionIn(List<Integer> values) {
            addCriterion("request_version in", values, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionNotIn(List<Integer> values) {
            addCriterion("request_version not in", values, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionBetween(Integer value1, Integer value2) {
            addCriterion("request_version between", value1, value2, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("request_version not between", value1, value2, "requestVersion");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintIsNull() {
            addCriterion("request_fingerprint is null");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintIsNotNull() {
            addCriterion("request_fingerprint is not null");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintEqualTo(String value) {
            addCriterion("request_fingerprint =", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintNotEqualTo(String value) {
            addCriterion("request_fingerprint <>", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintGreaterThan(String value) {
            addCriterion("request_fingerprint >", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintGreaterThanOrEqualTo(String value) {
            addCriterion("request_fingerprint >=", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintLessThan(String value) {
            addCriterion("request_fingerprint <", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintLessThanOrEqualTo(String value) {
            addCriterion("request_fingerprint <=", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintLike(String value) {
            addCriterion("request_fingerprint ilike", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintNotLike(String value) {
            addCriterion("request_fingerprint not ilike", value, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintIn(List<String> values) {
            addCriterion("request_fingerprint in", values, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintNotIn(List<String> values) {
            addCriterion("request_fingerprint not in", values, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintBetween(String value1, String value2) {
            addCriterion("request_fingerprint between", value1, value2, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintNotBetween(String value1, String value2) {
            addCriterion("request_fingerprint not between", value1, value2, "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNull() {
            addCriterion("admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNotNull() {
            addCriterion("admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdEqualTo(Integer value) {
            addCriterion("admin_user_id =", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotEqualTo(Integer value) {
            addCriterion("admin_user_id <>", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThan(Integer value) {
            addCriterion("admin_user_id >", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id >=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThan(Integer value) {
            addCriterion("admin_user_id <", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id <=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIn(List<Integer> values) {
            addCriterion("admin_user_id in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotIn(List<Integer> values) {
            addCriterion("admin_user_id not in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id not between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinIsNull() {
            addCriterion("provision_recycle_bin is null");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinIsNotNull() {
            addCriterion("provision_recycle_bin is not null");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinEqualTo(Boolean value) {
            addCriterion("provision_recycle_bin =", value, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinNotEqualTo(Boolean value) {
            addCriterion("provision_recycle_bin <>", value, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinGreaterThan(Boolean value) {
            addCriterion("provision_recycle_bin >", value, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinGreaterThanOrEqualTo(Boolean value) {
            addCriterion("provision_recycle_bin >=", value, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinLessThan(Boolean value) {
            addCriterion("provision_recycle_bin <", value, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinLessThanOrEqualTo(Boolean value) {
            addCriterion("provision_recycle_bin <=", value, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinIn(List<Boolean> values) {
            addCriterion("provision_recycle_bin in", values, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinNotIn(List<Boolean> values) {
            addCriterion("provision_recycle_bin not in", values, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinBetween(Boolean value1, Boolean value2) {
            addCriterion("provision_recycle_bin between", value1, value2, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andProvisionRecycleBinNotBetween(Boolean value1, Boolean value2) {
            addCriterion("provision_recycle_bin not between", value1, value2, "provisionRecycleBin");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNull() {
            addCriterion("site_name is null");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNotNull() {
            addCriterion("site_name is not null");
            return (Criteria) this;
        }

        public Criteria andSiteNameEqualTo(String value) {
            addCriterion("site_name =", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotEqualTo(String value) {
            addCriterion("site_name <>", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThan(String value) {
            addCriterion("site_name >", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("site_name >=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThan(String value) {
            addCriterion("site_name <", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThanOrEqualTo(String value) {
            addCriterion("site_name <=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLike(String value) {
            addCriterion("site_name ilike", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotLike(String value) {
            addCriterion("site_name not ilike", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameIn(List<String> values) {
            addCriterion("site_name in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotIn(List<String> values) {
            addCriterion("site_name not in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameBetween(String value1, String value2) {
            addCriterion("site_name between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotBetween(String value1, String value2) {
            addCriterion("site_name not between", value1, value2, "siteName");
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

        public Criteria andInstallStrategyIsNull() {
            addCriterion("install_strategy is null");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyIsNotNull() {
            addCriterion("install_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyEqualTo(String value) {
            addCriterion("install_strategy =", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyNotEqualTo(String value) {
            addCriterion("install_strategy <>", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyGreaterThan(String value) {
            addCriterion("install_strategy >", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyGreaterThanOrEqualTo(String value) {
            addCriterion("install_strategy >=", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyLessThan(String value) {
            addCriterion("install_strategy <", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyLessThanOrEqualTo(String value) {
            addCriterion("install_strategy <=", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyLike(String value) {
            addCriterion("install_strategy ilike", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyNotLike(String value) {
            addCriterion("install_strategy not ilike", value, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyIn(List<String> values) {
            addCriterion("install_strategy in", values, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyNotIn(List<String> values) {
            addCriterion("install_strategy not in", values, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyBetween(String value1, String value2) {
            addCriterion("install_strategy between", value1, value2, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyNotBetween(String value1, String value2) {
            addCriterion("install_strategy not between", value1, value2, "installStrategy");
            return (Criteria) this;
        }

        public Criteria andStateContains(String value) {
            addCriterion("state ilike", LikePatterns.contains(value), "state");
            return (Criteria) this;
        }

        public Criteria andLastCompletedStateContains(String value) {
            addCriterion("last_completed_state ilike", LikePatterns.contains(value), "lastCompletedState");
            return (Criteria) this;
        }

        public Criteria andRequestFingerprintContains(String value) {
            addCriterion("request_fingerprint ilike", LikePatterns.contains(value), "requestFingerprint");
            return (Criteria) this;
        }

        public Criteria andSiteNameContains(String value) {
            addCriterion("site_name ilike", LikePatterns.contains(value), "siteName");
            return (Criteria) this;
        }

        public Criteria andLastErrorContains(String value) {
            addCriterion("last_error ilike", LikePatterns.contains(value), "lastError");
            return (Criteria) this;
        }

        public Criteria andInstallStrategyContains(String value) {
            addCriterion("install_strategy ilike", LikePatterns.contains(value), "installStrategy");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.install_run")
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