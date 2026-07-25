package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuoteStripRunDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public QuoteStripRunDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
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

        public Criteria andRunIdIsNull() {
            addCriterion("run_id is null");
            return (Criteria) this;
        }

        public Criteria andRunIdIsNotNull() {
            addCriterion("run_id is not null");
            return (Criteria) this;
        }

        public Criteria andRunIdEqualTo(Object value) {
            addCriterion("run_id =", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdNotEqualTo(Object value) {
            addCriterion("run_id <>", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdGreaterThan(Object value) {
            addCriterion("run_id >", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdGreaterThanOrEqualTo(Object value) {
            addCriterion("run_id >=", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdLessThan(Object value) {
            addCriterion("run_id <", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdLessThanOrEqualTo(Object value) {
            addCriterion("run_id <=", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdIn(List<Object> values) {
            addCriterion("run_id in", values, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdNotIn(List<Object> values) {
            addCriterion("run_id not in", values, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdBetween(Object value1, Object value2) {
            addCriterion("run_id between", value1, value2, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdNotBetween(Object value1, Object value2) {
            addCriterion("run_id not between", value1, value2, "runId");
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

        public Criteria andCandidateRowsIsNull() {
            addCriterion("candidate_rows is null");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsIsNotNull() {
            addCriterion("candidate_rows is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsEqualTo(Integer value) {
            addCriterion("candidate_rows =", value, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsNotEqualTo(Integer value) {
            addCriterion("candidate_rows <>", value, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsGreaterThan(Integer value) {
            addCriterion("candidate_rows >", value, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsGreaterThanOrEqualTo(Integer value) {
            addCriterion("candidate_rows >=", value, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsLessThan(Integer value) {
            addCriterion("candidate_rows <", value, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsLessThanOrEqualTo(Integer value) {
            addCriterion("candidate_rows <=", value, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsIn(List<Integer> values) {
            addCriterion("candidate_rows in", values, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsNotIn(List<Integer> values) {
            addCriterion("candidate_rows not in", values, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsBetween(Integer value1, Integer value2) {
            addCriterion("candidate_rows between", value1, value2, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andCandidateRowsNotBetween(Integer value1, Integer value2) {
            addCriterion("candidate_rows not between", value1, value2, "candidateRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsIsNull() {
            addCriterion("planned_rows is null");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsIsNotNull() {
            addCriterion("planned_rows is not null");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsEqualTo(Integer value) {
            addCriterion("planned_rows =", value, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsNotEqualTo(Integer value) {
            addCriterion("planned_rows <>", value, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsGreaterThan(Integer value) {
            addCriterion("planned_rows >", value, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsGreaterThanOrEqualTo(Integer value) {
            addCriterion("planned_rows >=", value, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsLessThan(Integer value) {
            addCriterion("planned_rows <", value, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsLessThanOrEqualTo(Integer value) {
            addCriterion("planned_rows <=", value, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsIn(List<Integer> values) {
            addCriterion("planned_rows in", values, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsNotIn(List<Integer> values) {
            addCriterion("planned_rows not in", values, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsBetween(Integer value1, Integer value2) {
            addCriterion("planned_rows between", value1, value2, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedRowsNotBetween(Integer value1, Integer value2) {
            addCriterion("planned_rows not between", value1, value2, "plannedRows");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesIsNull() {
            addCriterion("planned_quotes is null");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesIsNotNull() {
            addCriterion("planned_quotes is not null");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesEqualTo(Long value) {
            addCriterion("planned_quotes =", value, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesNotEqualTo(Long value) {
            addCriterion("planned_quotes <>", value, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesGreaterThan(Long value) {
            addCriterion("planned_quotes >", value, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesGreaterThanOrEqualTo(Long value) {
            addCriterion("planned_quotes >=", value, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesLessThan(Long value) {
            addCriterion("planned_quotes <", value, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesLessThanOrEqualTo(Long value) {
            addCriterion("planned_quotes <=", value, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesIn(List<Long> values) {
            addCriterion("planned_quotes in", values, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesNotIn(List<Long> values) {
            addCriterion("planned_quotes not in", values, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesBetween(Long value1, Long value2) {
            addCriterion("planned_quotes between", value1, value2, "plannedQuotes");
            return (Criteria) this;
        }

        public Criteria andPlannedQuotesNotBetween(Long value1, Long value2) {
            addCriterion("planned_quotes not between", value1, value2, "plannedQuotes");
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

        public Criteria andLeaseOwnerIsNull() {
            addCriterion("lease_owner is null");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerIsNotNull() {
            addCriterion("lease_owner is not null");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerEqualTo(Object value) {
            addCriterion("lease_owner =", value, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerNotEqualTo(Object value) {
            addCriterion("lease_owner <>", value, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerGreaterThan(Object value) {
            addCriterion("lease_owner >", value, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerGreaterThanOrEqualTo(Object value) {
            addCriterion("lease_owner >=", value, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerLessThan(Object value) {
            addCriterion("lease_owner <", value, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerLessThanOrEqualTo(Object value) {
            addCriterion("lease_owner <=", value, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerIn(List<Object> values) {
            addCriterion("lease_owner in", values, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerNotIn(List<Object> values) {
            addCriterion("lease_owner not in", values, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerBetween(Object value1, Object value2) {
            addCriterion("lease_owner between", value1, value2, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseOwnerNotBetween(Object value1, Object value2) {
            addCriterion("lease_owner not between", value1, value2, "leaseOwner");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsIsNull() {
            addCriterion("lease_expires_ts is null");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsIsNotNull() {
            addCriterion("lease_expires_ts is not null");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsEqualTo(OffsetDateTime value) {
            addCriterion("lease_expires_ts =", value, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsNotEqualTo(OffsetDateTime value) {
            addCriterion("lease_expires_ts <>", value, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsGreaterThan(OffsetDateTime value) {
            addCriterion("lease_expires_ts >", value, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("lease_expires_ts >=", value, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsLessThan(OffsetDateTime value) {
            addCriterion("lease_expires_ts <", value, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("lease_expires_ts <=", value, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsIn(List<OffsetDateTime> values) {
            addCriterion("lease_expires_ts in", values, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsNotIn(List<OffsetDateTime> values) {
            addCriterion("lease_expires_ts not in", values, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("lease_expires_ts between", value1, value2, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andLeaseExpiresTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("lease_expires_ts not between", value1, value2, "leaseExpiresTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsIsNull() {
            addCriterion("heartbeat_ts is null");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsIsNotNull() {
            addCriterion("heartbeat_ts is not null");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsEqualTo(OffsetDateTime value) {
            addCriterion("heartbeat_ts =", value, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsNotEqualTo(OffsetDateTime value) {
            addCriterion("heartbeat_ts <>", value, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsGreaterThan(OffsetDateTime value) {
            addCriterion("heartbeat_ts >", value, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("heartbeat_ts >=", value, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsLessThan(OffsetDateTime value) {
            addCriterion("heartbeat_ts <", value, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("heartbeat_ts <=", value, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsIn(List<OffsetDateTime> values) {
            addCriterion("heartbeat_ts in", values, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsNotIn(List<OffsetDateTime> values) {
            addCriterion("heartbeat_ts not in", values, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("heartbeat_ts between", value1, value2, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andHeartbeatTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("heartbeat_ts not between", value1, value2, "heartbeatTs");
            return (Criteria) this;
        }

        public Criteria andAttemptNoIsNull() {
            addCriterion("attempt_no is null");
            return (Criteria) this;
        }

        public Criteria andAttemptNoIsNotNull() {
            addCriterion("attempt_no is not null");
            return (Criteria) this;
        }

        public Criteria andAttemptNoEqualTo(Integer value) {
            addCriterion("attempt_no =", value, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoNotEqualTo(Integer value) {
            addCriterion("attempt_no <>", value, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoGreaterThan(Integer value) {
            addCriterion("attempt_no >", value, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("attempt_no >=", value, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoLessThan(Integer value) {
            addCriterion("attempt_no <", value, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoLessThanOrEqualTo(Integer value) {
            addCriterion("attempt_no <=", value, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoIn(List<Integer> values) {
            addCriterion("attempt_no in", values, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoNotIn(List<Integer> values) {
            addCriterion("attempt_no not in", values, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoBetween(Integer value1, Integer value2) {
            addCriterion("attempt_no between", value1, value2, "attemptNo");
            return (Criteria) this;
        }

        public Criteria andAttemptNoNotBetween(Integer value1, Integer value2) {
            addCriterion("attempt_no not between", value1, value2, "attemptNo");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
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