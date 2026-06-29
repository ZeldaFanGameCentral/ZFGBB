package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFGameDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713151506-04:00", comments="Source Table: smf_1games")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713166516-04:00", comments="Source Table: smf_1games")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713186025-04:00", comments="Source Table: smf_1games")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713142747-04:00", comments="Source Table: smf_1games")
    public SMFGameDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713155906-04:00", comments="Source Table: smf_1games")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713161286-04:00", comments="Source Table: smf_1games")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713171586-04:00", comments="Source Table: smf_1games")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713176565-04:00", comments="Source Table: smf_1games")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713191405-04:00", comments="Source Table: smf_1games")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713194995-04:00", comments="Source Table: smf_1games")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713208554-04:00", comments="Source Table: smf_1games")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713214264-04:00", comments="Source Table: smf_1games")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713219334-04:00", comments="Source Table: smf_1games")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713224314-04:00", comments="Source Table: smf_1games")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713231444-04:00", comments="Source Table: smf_1games")
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

        public Criteria andIdGameIsNull() {
            addCriterion("ID_GAME is null");
            return (Criteria) this;
        }

        public Criteria andIdGameIsNotNull() {
            addCriterion("ID_GAME is not null");
            return (Criteria) this;
        }

        public Criteria andIdGameEqualTo(Integer value) {
            addCriterion("ID_GAME =", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameNotEqualTo(Integer value) {
            addCriterion("ID_GAME <>", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameGreaterThan(Integer value) {
            addCriterion("ID_GAME >", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_GAME >=", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameLessThan(Integer value) {
            addCriterion("ID_GAME <", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameLessThanOrEqualTo(Integer value) {
            addCriterion("ID_GAME <=", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameIn(List<Integer> values) {
            addCriterion("ID_GAME in", values, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameNotIn(List<Integer> values) {
            addCriterion("ID_GAME not in", values, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameBetween(Integer value1, Integer value2) {
            addCriterion("ID_GAME between", value1, value2, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_GAME not between", value1, value2, "idGame");
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

        public Criteria andProgressIsNull() {
            addCriterion("progress is null");
            return (Criteria) this;
        }

        public Criteria andProgressIsNotNull() {
            addCriterion("progress is not null");
            return (Criteria) this;
        }

        public Criteria andProgressEqualTo(Integer value) {
            addCriterion("progress =", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotEqualTo(Integer value) {
            addCriterion("progress <>", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThan(Integer value) {
            addCriterion("progress >", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThanOrEqualTo(Integer value) {
            addCriterion("progress >=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThan(Integer value) {
            addCriterion("progress <", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThanOrEqualTo(Integer value) {
            addCriterion("progress <=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressIn(List<Integer> values) {
            addCriterion("progress in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotIn(List<Integer> values) {
            addCriterion("progress not in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressBetween(Integer value1, Integer value2) {
            addCriterion("progress between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotBetween(Integer value1, Integer value2) {
            addCriterion("progress not between", value1, value2, "progress");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andRatingIsNull() {
            addCriterion("rating is null");
            return (Criteria) this;
        }

        public Criteria andRatingIsNotNull() {
            addCriterion("rating is not null");
            return (Criteria) this;
        }

        public Criteria andRatingEqualTo(Float value) {
            addCriterion("rating =", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotEqualTo(Float value) {
            addCriterion("rating <>", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThan(Float value) {
            addCriterion("rating >", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThanOrEqualTo(Float value) {
            addCriterion("rating >=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThan(Float value) {
            addCriterion("rating <", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThanOrEqualTo(Float value) {
            addCriterion("rating <=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingIn(List<Float> values) {
            addCriterion("rating in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotIn(List<Float> values) {
            addCriterion("rating not in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingBetween(Float value1, Float value2) {
            addCriterion("rating between", value1, value2, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotBetween(Float value1, Float value2) {
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

        public Criteria andZfgcapiapprovedIsNull() {
            addCriterion("zfgcapiApproved is null");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedIsNotNull() {
            addCriterion("zfgcapiApproved is not null");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedEqualTo(Integer value) {
            addCriterion("zfgcapiApproved =", value, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedNotEqualTo(Integer value) {
            addCriterion("zfgcapiApproved <>", value, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedGreaterThan(Integer value) {
            addCriterion("zfgcapiApproved >", value, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedGreaterThanOrEqualTo(Integer value) {
            addCriterion("zfgcapiApproved >=", value, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedLessThan(Integer value) {
            addCriterion("zfgcapiApproved <", value, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedLessThanOrEqualTo(Integer value) {
            addCriterion("zfgcapiApproved <=", value, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedIn(List<Integer> values) {
            addCriterion("zfgcapiApproved in", values, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedNotIn(List<Integer> values) {
            addCriterion("zfgcapiApproved not in", values, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedBetween(Integer value1, Integer value2) {
            addCriterion("zfgcapiApproved between", value1, value2, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiapprovedNotBetween(Integer value1, Integer value2) {
            addCriterion("zfgcapiApproved not between", value1, value2, "zfgcapiapproved");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountIsNull() {
            addCriterion("zfgcapiOpenCount is null");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountIsNotNull() {
            addCriterion("zfgcapiOpenCount is not null");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountEqualTo(Integer value) {
            addCriterion("zfgcapiOpenCount =", value, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountNotEqualTo(Integer value) {
            addCriterion("zfgcapiOpenCount <>", value, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountGreaterThan(Integer value) {
            addCriterion("zfgcapiOpenCount >", value, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountGreaterThanOrEqualTo(Integer value) {
            addCriterion("zfgcapiOpenCount >=", value, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountLessThan(Integer value) {
            addCriterion("zfgcapiOpenCount <", value, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountLessThanOrEqualTo(Integer value) {
            addCriterion("zfgcapiOpenCount <=", value, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountIn(List<Integer> values) {
            addCriterion("zfgcapiOpenCount in", values, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountNotIn(List<Integer> values) {
            addCriterion("zfgcapiOpenCount not in", values, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountBetween(Integer value1, Integer value2) {
            addCriterion("zfgcapiOpenCount between", value1, value2, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapiopencountNotBetween(Integer value1, Integer value2) {
            addCriterion("zfgcapiOpenCount not between", value1, value2, "zfgcapiopencount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountIsNull() {
            addCriterion("zfgcapiRupeeCount is null");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountIsNotNull() {
            addCriterion("zfgcapiRupeeCount is not null");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountEqualTo(Integer value) {
            addCriterion("zfgcapiRupeeCount =", value, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountNotEqualTo(Integer value) {
            addCriterion("zfgcapiRupeeCount <>", value, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountGreaterThan(Integer value) {
            addCriterion("zfgcapiRupeeCount >", value, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountGreaterThanOrEqualTo(Integer value) {
            addCriterion("zfgcapiRupeeCount >=", value, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountLessThan(Integer value) {
            addCriterion("zfgcapiRupeeCount <", value, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountLessThanOrEqualTo(Integer value) {
            addCriterion("zfgcapiRupeeCount <=", value, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountIn(List<Integer> values) {
            addCriterion("zfgcapiRupeeCount in", values, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountNotIn(List<Integer> values) {
            addCriterion("zfgcapiRupeeCount not in", values, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountBetween(Integer value1, Integer value2) {
            addCriterion("zfgcapiRupeeCount between", value1, value2, "zfgcapirupeecount");
            return (Criteria) this;
        }

        public Criteria andZfgcapirupeecountNotBetween(Integer value1, Integer value2) {
            addCriterion("zfgcapiRupeeCount not between", value1, value2, "zfgcapirupeecount");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713551044-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.713562433-04:00", comments="Source Table: smf_1games")
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