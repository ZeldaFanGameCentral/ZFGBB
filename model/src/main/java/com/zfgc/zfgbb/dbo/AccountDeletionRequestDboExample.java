package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountDeletionRequestDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public AccountDeletionRequestDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
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

        public Criteria andAccountDeletionRequestIdIsNull() {
            addCriterion("account_deletion_request_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdIsNotNull() {
            addCriterion("account_deletion_request_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdEqualTo(Integer value) {
            addCriterion("account_deletion_request_id =", value, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdNotEqualTo(Integer value) {
            addCriterion("account_deletion_request_id <>", value, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdGreaterThan(Integer value) {
            addCriterion("account_deletion_request_id >", value, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_deletion_request_id >=", value, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdLessThan(Integer value) {
            addCriterion("account_deletion_request_id <", value, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdLessThanOrEqualTo(Integer value) {
            addCriterion("account_deletion_request_id <=", value, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdIn(List<Integer> values) {
            addCriterion("account_deletion_request_id in", values, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdNotIn(List<Integer> values) {
            addCriterion("account_deletion_request_id not in", values, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdBetween(Integer value1, Integer value2) {
            addCriterion("account_deletion_request_id between", value1, value2, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andAccountDeletionRequestIdNotBetween(Integer value1, Integer value2) {
            addCriterion("account_deletion_request_id not between", value1, value2, "accountDeletionRequestId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andModeIsNull() {
            addCriterion("mode is null");
            return (Criteria) this;
        }

        public Criteria andModeIsNotNull() {
            addCriterion("mode is not null");
            return (Criteria) this;
        }

        public Criteria andModeEqualTo(String value) {
            addCriterion("mode =", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotEqualTo(String value) {
            addCriterion("mode <>", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThan(String value) {
            addCriterion("mode >", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThanOrEqualTo(String value) {
            addCriterion("mode >=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThan(String value) {
            addCriterion("mode <", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThanOrEqualTo(String value) {
            addCriterion("mode <=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLike(String value) {
            addCriterion("mode ilike", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotLike(String value) {
            addCriterion("mode not ilike", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeIn(List<String> values) {
            addCriterion("mode in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotIn(List<String> values) {
            addCriterion("mode not in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeBetween(String value1, String value2) {
            addCriterion("mode between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotBetween(String value1, String value2) {
            addCriterion("mode not between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status ilike", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not ilike", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTokenSha256IsNull() {
            addCriterion("token_sha256 is null");
            return (Criteria) this;
        }

        public Criteria andTokenSha256IsNotNull() {
            addCriterion("token_sha256 is not null");
            return (Criteria) this;
        }

        public Criteria andTokenSha256EqualTo(String value) {
            addCriterion("token_sha256 =", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256NotEqualTo(String value) {
            addCriterion("token_sha256 <>", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256GreaterThan(String value) {
            addCriterion("token_sha256 >", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256GreaterThanOrEqualTo(String value) {
            addCriterion("token_sha256 >=", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256LessThan(String value) {
            addCriterion("token_sha256 <", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256LessThanOrEqualTo(String value) {
            addCriterion("token_sha256 <=", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256Like(String value) {
            addCriterion("token_sha256 ilike", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256NotLike(String value) {
            addCriterion("token_sha256 not ilike", value, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256In(List<String> values) {
            addCriterion("token_sha256 in", values, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256NotIn(List<String> values) {
            addCriterion("token_sha256 not in", values, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256Between(String value1, String value2) {
            addCriterion("token_sha256 between", value1, value2, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andTokenSha256NotBetween(String value1, String value2) {
            addCriterion("token_sha256 not between", value1, value2, "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andRequestedTsIsNull() {
            addCriterion("requested_ts is null");
            return (Criteria) this;
        }

        public Criteria andRequestedTsIsNotNull() {
            addCriterion("requested_ts is not null");
            return (Criteria) this;
        }

        public Criteria andRequestedTsEqualTo(OffsetDateTime value) {
            addCriterion("requested_ts =", value, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("requested_ts <>", value, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsGreaterThan(OffsetDateTime value) {
            addCriterion("requested_ts >", value, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("requested_ts >=", value, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsLessThan(OffsetDateTime value) {
            addCriterion("requested_ts <", value, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("requested_ts <=", value, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsIn(List<OffsetDateTime> values) {
            addCriterion("requested_ts in", values, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("requested_ts not in", values, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("requested_ts between", value1, value2, "requestedTs");
            return (Criteria) this;
        }

        public Criteria andRequestedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("requested_ts not between", value1, value2, "requestedTs");
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

        public Criteria andConfirmedTsIsNull() {
            addCriterion("confirmed_ts is null");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsIsNotNull() {
            addCriterion("confirmed_ts is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsEqualTo(OffsetDateTime value) {
            addCriterion("confirmed_ts =", value, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("confirmed_ts <>", value, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsGreaterThan(OffsetDateTime value) {
            addCriterion("confirmed_ts >", value, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("confirmed_ts >=", value, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsLessThan(OffsetDateTime value) {
            addCriterion("confirmed_ts <", value, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("confirmed_ts <=", value, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsIn(List<OffsetDateTime> values) {
            addCriterion("confirmed_ts in", values, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("confirmed_ts not in", values, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("confirmed_ts between", value1, value2, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andConfirmedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("confirmed_ts not between", value1, value2, "confirmedTs");
            return (Criteria) this;
        }

        public Criteria andResendCountIsNull() {
            addCriterion("resend_count is null");
            return (Criteria) this;
        }

        public Criteria andResendCountIsNotNull() {
            addCriterion("resend_count is not null");
            return (Criteria) this;
        }

        public Criteria andResendCountEqualTo(Integer value) {
            addCriterion("resend_count =", value, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountNotEqualTo(Integer value) {
            addCriterion("resend_count <>", value, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountGreaterThan(Integer value) {
            addCriterion("resend_count >", value, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("resend_count >=", value, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountLessThan(Integer value) {
            addCriterion("resend_count <", value, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountLessThanOrEqualTo(Integer value) {
            addCriterion("resend_count <=", value, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountIn(List<Integer> values) {
            addCriterion("resend_count in", values, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountNotIn(List<Integer> values) {
            addCriterion("resend_count not in", values, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountBetween(Integer value1, Integer value2) {
            addCriterion("resend_count between", value1, value2, "resendCount");
            return (Criteria) this;
        }

        public Criteria andResendCountNotBetween(Integer value1, Integer value2) {
            addCriterion("resend_count not between", value1, value2, "resendCount");
            return (Criteria) this;
        }

        public Criteria andLastSentTsIsNull() {
            addCriterion("last_sent_ts is null");
            return (Criteria) this;
        }

        public Criteria andLastSentTsIsNotNull() {
            addCriterion("last_sent_ts is not null");
            return (Criteria) this;
        }

        public Criteria andLastSentTsEqualTo(OffsetDateTime value) {
            addCriterion("last_sent_ts =", value, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsNotEqualTo(OffsetDateTime value) {
            addCriterion("last_sent_ts <>", value, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsGreaterThan(OffsetDateTime value) {
            addCriterion("last_sent_ts >", value, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("last_sent_ts >=", value, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsLessThan(OffsetDateTime value) {
            addCriterion("last_sent_ts <", value, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("last_sent_ts <=", value, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsIn(List<OffsetDateTime> values) {
            addCriterion("last_sent_ts in", values, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsNotIn(List<OffsetDateTime> values) {
            addCriterion("last_sent_ts not in", values, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("last_sent_ts between", value1, value2, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andLastSentTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("last_sent_ts not between", value1, value2, "lastSentTs");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotIsNull() {
            addCriterion("avatar_id_snapshot is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotIsNotNull() {
            addCriterion("avatar_id_snapshot is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotEqualTo(Integer value) {
            addCriterion("avatar_id_snapshot =", value, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotNotEqualTo(Integer value) {
            addCriterion("avatar_id_snapshot <>", value, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotGreaterThan(Integer value) {
            addCriterion("avatar_id_snapshot >", value, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotGreaterThanOrEqualTo(Integer value) {
            addCriterion("avatar_id_snapshot >=", value, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotLessThan(Integer value) {
            addCriterion("avatar_id_snapshot <", value, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotLessThanOrEqualTo(Integer value) {
            addCriterion("avatar_id_snapshot <=", value, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotIn(List<Integer> values) {
            addCriterion("avatar_id_snapshot in", values, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotNotIn(List<Integer> values) {
            addCriterion("avatar_id_snapshot not in", values, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotBetween(Integer value1, Integer value2) {
            addCriterion("avatar_id_snapshot between", value1, value2, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andAvatarIdSnapshotNotBetween(Integer value1, Integer value2) {
            addCriterion("avatar_id_snapshot not between", value1, value2, "avatarIdSnapshot");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorIsNull() {
            addCriterion("purge_cursor is null");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorIsNotNull() {
            addCriterion("purge_cursor is not null");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorEqualTo(String value) {
            addCriterion("purge_cursor =", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorNotEqualTo(String value) {
            addCriterion("purge_cursor <>", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorGreaterThan(String value) {
            addCriterion("purge_cursor >", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorGreaterThanOrEqualTo(String value) {
            addCriterion("purge_cursor >=", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorLessThan(String value) {
            addCriterion("purge_cursor <", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorLessThanOrEqualTo(String value) {
            addCriterion("purge_cursor <=", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorLike(String value) {
            addCriterion("purge_cursor ilike", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorNotLike(String value) {
            addCriterion("purge_cursor not ilike", value, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorIn(List<String> values) {
            addCriterion("purge_cursor in", values, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorNotIn(List<String> values) {
            addCriterion("purge_cursor not in", values, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorBetween(String value1, String value2) {
            addCriterion("purge_cursor between", value1, value2, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorNotBetween(String value1, String value2) {
            addCriterion("purge_cursor not between", value1, value2, "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsIsNull() {
            addCriterion("recorded_blob_paths is null");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsIsNotNull() {
            addCriterion("recorded_blob_paths is not null");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsEqualTo(String value) {
            addCriterion("recorded_blob_paths =", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsNotEqualTo(String value) {
            addCriterion("recorded_blob_paths <>", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsGreaterThan(String value) {
            addCriterion("recorded_blob_paths >", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsGreaterThanOrEqualTo(String value) {
            addCriterion("recorded_blob_paths >=", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsLessThan(String value) {
            addCriterion("recorded_blob_paths <", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsLessThanOrEqualTo(String value) {
            addCriterion("recorded_blob_paths <=", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsLike(String value) {
            addCriterion("recorded_blob_paths ilike", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsNotLike(String value) {
            addCriterion("recorded_blob_paths not ilike", value, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsIn(List<String> values) {
            addCriterion("recorded_blob_paths in", values, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsNotIn(List<String> values) {
            addCriterion("recorded_blob_paths not in", values, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsBetween(String value1, String value2) {
            addCriterion("recorded_blob_paths between", value1, value2, "recordedBlobPaths");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsNotBetween(String value1, String value2) {
            addCriterion("recorded_blob_paths not between", value1, value2, "recordedBlobPaths");
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

        public Criteria andModeContains(String value) {
            addCriterion("mode ilike", LikePatterns.contains(value), "mode");
            return (Criteria) this;
        }

        public Criteria andStatusContains(String value) {
            addCriterion("status ilike", LikePatterns.contains(value), "status");
            return (Criteria) this;
        }

        public Criteria andTokenSha256Contains(String value) {
            addCriterion("token_sha256 ilike", LikePatterns.contains(value), "tokenSha256");
            return (Criteria) this;
        }

        public Criteria andPurgeCursorContains(String value) {
            addCriterion("purge_cursor ilike", LikePatterns.contains(value), "purgeCursor");
            return (Criteria) this;
        }

        public Criteria andRecordedBlobPathsContains(String value) {
            addCriterion("recorded_blob_paths ilike", LikePatterns.contains(value), "recordedBlobPaths");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
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