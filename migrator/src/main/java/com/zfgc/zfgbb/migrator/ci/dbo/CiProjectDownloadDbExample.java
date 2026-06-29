package com.zfgc.zfgbb.migrator.ci.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class CiProjectDownloadDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721959678-04:00", comments="Source Table: ci_project_downloads")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721977977-04:00", comments="Source Table: ci_project_downloads")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721997997-04:00", comments="Source Table: ci_project_downloads")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721952028-04:00", comments="Source Table: ci_project_downloads")
    public CiProjectDownloadDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721966148-04:00", comments="Source Table: ci_project_downloads")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721972318-04:00", comments="Source Table: ci_project_downloads")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721983267-04:00", comments="Source Table: ci_project_downloads")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721988267-04:00", comments="Source Table: ci_project_downloads")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722003467-04:00", comments="Source Table: ci_project_downloads")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722008606-04:00", comments="Source Table: ci_project_downloads")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722012046-04:00", comments="Source Table: ci_project_downloads")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722017056-04:00", comments="Source Table: ci_project_downloads")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722021796-04:00", comments="Source Table: ci_project_downloads")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722026626-04:00", comments="Source Table: ci_project_downloads")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722036246-04:00", comments="Source Table: ci_project_downloads")
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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

        public Criteria andEditedMemberNameIsNull() {
            addCriterion("edited_member_name is null");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameIsNotNull() {
            addCriterion("edited_member_name is not null");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameEqualTo(String value) {
            addCriterion("edited_member_name =", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameNotEqualTo(String value) {
            addCriterion("edited_member_name <>", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameGreaterThan(String value) {
            addCriterion("edited_member_name >", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameGreaterThanOrEqualTo(String value) {
            addCriterion("edited_member_name >=", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameLessThan(String value) {
            addCriterion("edited_member_name <", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameLessThanOrEqualTo(String value) {
            addCriterion("edited_member_name <=", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameLike(String value) {
            addCriterion("edited_member_name like", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameNotLike(String value) {
            addCriterion("edited_member_name not like", value, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameIn(List<String> values) {
            addCriterion("edited_member_name in", values, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameNotIn(List<String> values) {
            addCriterion("edited_member_name not in", values, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameBetween(String value1, String value2) {
            addCriterion("edited_member_name between", value1, value2, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberNameNotBetween(String value1, String value2) {
            addCriterion("edited_member_name not between", value1, value2, "editedMemberName");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdIsNull() {
            addCriterion("edited_member_id is null");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdIsNotNull() {
            addCriterion("edited_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdEqualTo(Integer value) {
            addCriterion("edited_member_id =", value, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdNotEqualTo(Integer value) {
            addCriterion("edited_member_id <>", value, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdGreaterThan(Integer value) {
            addCriterion("edited_member_id >", value, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("edited_member_id >=", value, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdLessThan(Integer value) {
            addCriterion("edited_member_id <", value, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("edited_member_id <=", value, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdIn(List<Integer> values) {
            addCriterion("edited_member_id in", values, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdNotIn(List<Integer> values) {
            addCriterion("edited_member_id not in", values, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("edited_member_id between", value1, value2, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("edited_member_id not between", value1, value2, "editedMemberId");
            return (Criteria) this;
        }

        public Criteria andEditedTimeIsNull() {
            addCriterion("edited_time is null");
            return (Criteria) this;
        }

        public Criteria andEditedTimeIsNotNull() {
            addCriterion("edited_time is not null");
            return (Criteria) this;
        }

        public Criteria andEditedTimeEqualTo(Integer value) {
            addCriterion("edited_time =", value, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeNotEqualTo(Integer value) {
            addCriterion("edited_time <>", value, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeGreaterThan(Integer value) {
            addCriterion("edited_time >", value, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("edited_time >=", value, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeLessThan(Integer value) {
            addCriterion("edited_time <", value, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeLessThanOrEqualTo(Integer value) {
            addCriterion("edited_time <=", value, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeIn(List<Integer> values) {
            addCriterion("edited_time in", values, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeNotIn(List<Integer> values) {
            addCriterion("edited_time not in", values, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeBetween(Integer value1, Integer value2) {
            addCriterion("edited_time between", value1, value2, "editedTime");
            return (Criteria) this;
        }

        public Criteria andEditedTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("edited_time not between", value1, value2, "editedTime");
            return (Criteria) this;
        }

        public Criteria andFileIsNull() {
            addCriterion("file is null");
            return (Criteria) this;
        }

        public Criteria andFileIsNotNull() {
            addCriterion("file is not null");
            return (Criteria) this;
        }

        public Criteria andFileEqualTo(String value) {
            addCriterion("file =", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotEqualTo(String value) {
            addCriterion("file <>", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThan(String value) {
            addCriterion("file >", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThanOrEqualTo(String value) {
            addCriterion("file >=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThan(String value) {
            addCriterion("file <", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThanOrEqualTo(String value) {
            addCriterion("file <=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLike(String value) {
            addCriterion("file like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotLike(String value) {
            addCriterion("file not like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileIn(List<String> values) {
            addCriterion("file in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotIn(List<String> values) {
            addCriterion("file not in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileBetween(String value1, String value2) {
            addCriterion("file between", value1, value2, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotBetween(String value1, String value2) {
            addCriterion("file not between", value1, value2, "file");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Integer value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Integer value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Integer value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Integer value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Integer> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Integer> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberNameIsNull() {
            addCriterion("member_name is null");
            return (Criteria) this;
        }

        public Criteria andMemberNameIsNotNull() {
            addCriterion("member_name is not null");
            return (Criteria) this;
        }

        public Criteria andMemberNameEqualTo(String value) {
            addCriterion("member_name =", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotEqualTo(String value) {
            addCriterion("member_name <>", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameGreaterThan(String value) {
            addCriterion("member_name >", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameGreaterThanOrEqualTo(String value) {
            addCriterion("member_name >=", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLessThan(String value) {
            addCriterion("member_name <", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLessThanOrEqualTo(String value) {
            addCriterion("member_name <=", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLike(String value) {
            addCriterion("member_name like", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotLike(String value) {
            addCriterion("member_name not like", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameIn(List<String> values) {
            addCriterion("member_name in", values, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotIn(List<String> values) {
            addCriterion("member_name not in", values, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameBetween(String value1, String value2) {
            addCriterion("member_name between", value1, value2, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotBetween(String value1, String value2) {
            addCriterion("member_name not between", value1, value2, "memberName");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(Integer value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(Integer value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(Integer value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(Integer value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<Integer> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<Integer> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(Integer value1, Integer value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("size is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("size is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(Integer value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(Integer value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(Integer value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(Integer value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(Integer value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<Integer> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<Integer> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(Integer value1, Integer value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Integer value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Integer value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Integer value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Integer value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Integer value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Integer> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Integer> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Integer value1, Integer value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722397634-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722408864-04:00", comments="Source Table: ci_project_downloads")
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