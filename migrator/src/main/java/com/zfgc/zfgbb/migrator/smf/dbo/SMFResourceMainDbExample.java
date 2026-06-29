package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFResourceMainDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715284289-04:00", comments="Source Table: smf_1resources_main")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715300548-04:00", comments="Source Table: smf_1resources_main")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715318218-04:00", comments="Source Table: smf_1resources_main")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715274969-04:00", comments="Source Table: smf_1resources_main")
    public SMFResourceMainDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715290599-04:00", comments="Source Table: smf_1resources_main")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715295539-04:00", comments="Source Table: smf_1resources_main")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715305448-04:00", comments="Source Table: smf_1resources_main")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715308658-04:00", comments="Source Table: smf_1resources_main")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715323598-04:00", comments="Source Table: smf_1resources_main")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715328837-04:00", comments="Source Table: smf_1resources_main")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715333877-04:00", comments="Source Table: smf_1resources_main")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715338687-04:00", comments="Source Table: smf_1resources_main")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715343447-04:00", comments="Source Table: smf_1resources_main")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715347997-04:00", comments="Source Table: smf_1resources_main")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715352827-04:00", comments="Source Table: smf_1resources_main")
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

        public Criteria andIdResourceIsNull() {
            addCriterion("ID_RESOURCE is null");
            return (Criteria) this;
        }

        public Criteria andIdResourceIsNotNull() {
            addCriterion("ID_RESOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andIdResourceEqualTo(Integer value) {
            addCriterion("ID_RESOURCE =", value, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceNotEqualTo(Integer value) {
            addCriterion("ID_RESOURCE <>", value, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceGreaterThan(Integer value) {
            addCriterion("ID_RESOURCE >", value, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_RESOURCE >=", value, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceLessThan(Integer value) {
            addCriterion("ID_RESOURCE <", value, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceLessThanOrEqualTo(Integer value) {
            addCriterion("ID_RESOURCE <=", value, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceIn(List<Integer> values) {
            addCriterion("ID_RESOURCE in", values, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceNotIn(List<Integer> values) {
            addCriterion("ID_RESOURCE not in", values, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceBetween(Integer value1, Integer value2) {
            addCriterion("ID_RESOURCE between", value1, value2, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdResourceNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_RESOURCE not between", value1, value2, "idResource");
            return (Criteria) this;
        }

        public Criteria andIdMemberIsNull() {
            addCriterion("ID_MEMBER is null");
            return (Criteria) this;
        }

        public Criteria andIdMemberIsNotNull() {
            addCriterion("ID_MEMBER is not null");
            return (Criteria) this;
        }

        public Criteria andIdMemberEqualTo(Integer value) {
            addCriterion("ID_MEMBER =", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotEqualTo(Integer value) {
            addCriterion("ID_MEMBER <>", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberGreaterThan(Integer value) {
            addCriterion("ID_MEMBER >", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_MEMBER >=", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberLessThan(Integer value) {
            addCriterion("ID_MEMBER <", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberLessThanOrEqualTo(Integer value) {
            addCriterion("ID_MEMBER <=", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberIn(List<Integer> values) {
            addCriterion("ID_MEMBER in", values, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotIn(List<Integer> values) {
            addCriterion("ID_MEMBER not in", values, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberBetween(Integer value1, Integer value2) {
            addCriterion("ID_MEMBER between", value1, value2, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_MEMBER not between", value1, value2, "idMember");
            return (Criteria) this;
        }

        public Criteria andViewsIsNull() {
            addCriterion("views is null");
            return (Criteria) this;
        }

        public Criteria andViewsIsNotNull() {
            addCriterion("views is not null");
            return (Criteria) this;
        }

        public Criteria andViewsEqualTo(Integer value) {
            addCriterion("views =", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsNotEqualTo(Integer value) {
            addCriterion("views <>", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsGreaterThan(Integer value) {
            addCriterion("views >", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsGreaterThanOrEqualTo(Integer value) {
            addCriterion("views >=", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsLessThan(Integer value) {
            addCriterion("views <", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsLessThanOrEqualTo(Integer value) {
            addCriterion("views <=", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsIn(List<Integer> values) {
            addCriterion("views in", values, "views");
            return (Criteria) this;
        }

        public Criteria andViewsNotIn(List<Integer> values) {
            addCriterion("views not in", values, "views");
            return (Criteria) this;
        }

        public Criteria andViewsBetween(Integer value1, Integer value2) {
            addCriterion("views between", value1, value2, "views");
            return (Criteria) this;
        }

        public Criteria andViewsNotBetween(Integer value1, Integer value2) {
            addCriterion("views not between", value1, value2, "views");
            return (Criteria) this;
        }

        public Criteria andDownloadsIsNull() {
            addCriterion("downloads is null");
            return (Criteria) this;
        }

        public Criteria andDownloadsIsNotNull() {
            addCriterion("downloads is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadsEqualTo(Integer value) {
            addCriterion("downloads =", value, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsNotEqualTo(Integer value) {
            addCriterion("downloads <>", value, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsGreaterThan(Integer value) {
            addCriterion("downloads >", value, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsGreaterThanOrEqualTo(Integer value) {
            addCriterion("downloads >=", value, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsLessThan(Integer value) {
            addCriterion("downloads <", value, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsLessThanOrEqualTo(Integer value) {
            addCriterion("downloads <=", value, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsIn(List<Integer> values) {
            addCriterion("downloads in", values, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsNotIn(List<Integer> values) {
            addCriterion("downloads not in", values, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsBetween(Integer value1, Integer value2) {
            addCriterion("downloads between", value1, value2, "downloads");
            return (Criteria) this;
        }

        public Criteria andDownloadsNotBetween(Integer value1, Integer value2) {
            addCriterion("downloads not between", value1, value2, "downloads");
            return (Criteria) this;
        }

        public Criteria andPosttimeIsNull() {
            addCriterion("postTime is null");
            return (Criteria) this;
        }

        public Criteria andPosttimeIsNotNull() {
            addCriterion("postTime is not null");
            return (Criteria) this;
        }

        public Criteria andPosttimeEqualTo(Integer value) {
            addCriterion("postTime =", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeNotEqualTo(Integer value) {
            addCriterion("postTime <>", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeGreaterThan(Integer value) {
            addCriterion("postTime >", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("postTime >=", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeLessThan(Integer value) {
            addCriterion("postTime <", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeLessThanOrEqualTo(Integer value) {
            addCriterion("postTime <=", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeIn(List<Integer> values) {
            addCriterion("postTime in", values, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeNotIn(List<Integer> values) {
            addCriterion("postTime not in", values, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeBetween(Integer value1, Integer value2) {
            addCriterion("postTime between", value1, value2, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeNotBetween(Integer value1, Integer value2) {
            addCriterion("postTime not between", value1, value2, "posttime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andFilesizeIsNull() {
            addCriterion("fileSize is null");
            return (Criteria) this;
        }

        public Criteria andFilesizeIsNotNull() {
            addCriterion("fileSize is not null");
            return (Criteria) this;
        }

        public Criteria andFilesizeEqualTo(Integer value) {
            addCriterion("fileSize =", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotEqualTo(Integer value) {
            addCriterion("fileSize <>", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeGreaterThan(Integer value) {
            addCriterion("fileSize >", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fileSize >=", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeLessThan(Integer value) {
            addCriterion("fileSize <", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeLessThanOrEqualTo(Integer value) {
            addCriterion("fileSize <=", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeIn(List<Integer> values) {
            addCriterion("fileSize in", values, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotIn(List<Integer> values) {
            addCriterion("fileSize not in", values, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeBetween(Integer value1, Integer value2) {
            addCriterion("fileSize between", value1, value2, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotBetween(Integer value1, Integer value2) {
            addCriterion("fileSize not between", value1, value2, "filesize");
            return (Criteria) this;
        }

        public Criteria andRatingIsNull() {
            addCriterion("rating is null");
            return (Criteria) this;
        }

        public Criteria andRatingIsNotNull() {
            addCriterion("rating is not null");
            return (Criteria) this;
        }

        public Criteria andRatingEqualTo(Integer value) {
            addCriterion("rating =", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotEqualTo(Integer value) {
            addCriterion("rating <>", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThan(Integer value) {
            addCriterion("rating >", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThanOrEqualTo(Integer value) {
            addCriterion("rating >=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThan(Integer value) {
            addCriterion("rating <", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThanOrEqualTo(Integer value) {
            addCriterion("rating <=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingIn(List<Integer> values) {
            addCriterion("rating in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotIn(List<Integer> values) {
            addCriterion("rating not in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingBetween(Integer value1, Integer value2) {
            addCriterion("rating between", value1, value2, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotBetween(Integer value1, Integer value2) {
            addCriterion("rating not between", value1, value2, "rating");
            return (Criteria) this;
        }

        public Criteria andIdPreviewIsNull() {
            addCriterion("ID_PREVIEW is null");
            return (Criteria) this;
        }

        public Criteria andIdPreviewIsNotNull() {
            addCriterion("ID_PREVIEW is not null");
            return (Criteria) this;
        }

        public Criteria andIdPreviewEqualTo(Integer value) {
            addCriterion("ID_PREVIEW =", value, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewNotEqualTo(Integer value) {
            addCriterion("ID_PREVIEW <>", value, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewGreaterThan(Integer value) {
            addCriterion("ID_PREVIEW >", value, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_PREVIEW >=", value, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewLessThan(Integer value) {
            addCriterion("ID_PREVIEW <", value, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewLessThanOrEqualTo(Integer value) {
            addCriterion("ID_PREVIEW <=", value, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewIn(List<Integer> values) {
            addCriterion("ID_PREVIEW in", values, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewNotIn(List<Integer> values) {
            addCriterion("ID_PREVIEW not in", values, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewBetween(Integer value1, Integer value2) {
            addCriterion("ID_PREVIEW between", value1, value2, "idPreview");
            return (Criteria) this;
        }

        public Criteria andIdPreviewNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_PREVIEW not between", value1, value2, "idPreview");
            return (Criteria) this;
        }

        public Criteria andVotecountIsNull() {
            addCriterion("voteCount is null");
            return (Criteria) this;
        }

        public Criteria andVotecountIsNotNull() {
            addCriterion("voteCount is not null");
            return (Criteria) this;
        }

        public Criteria andVotecountEqualTo(Integer value) {
            addCriterion("voteCount =", value, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountNotEqualTo(Integer value) {
            addCriterion("voteCount <>", value, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountGreaterThan(Integer value) {
            addCriterion("voteCount >", value, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountGreaterThanOrEqualTo(Integer value) {
            addCriterion("voteCount >=", value, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountLessThan(Integer value) {
            addCriterion("voteCount <", value, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountLessThanOrEqualTo(Integer value) {
            addCriterion("voteCount <=", value, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountIn(List<Integer> values) {
            addCriterion("voteCount in", values, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountNotIn(List<Integer> values) {
            addCriterion("voteCount not in", values, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountBetween(Integer value1, Integer value2) {
            addCriterion("voteCount between", value1, value2, "votecount");
            return (Criteria) this;
        }

        public Criteria andVotecountNotBetween(Integer value1, Integer value2) {
            addCriterion("voteCount not between", value1, value2, "votecount");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715787213-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.715797283-04:00", comments="Source Table: smf_1resources_main")
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