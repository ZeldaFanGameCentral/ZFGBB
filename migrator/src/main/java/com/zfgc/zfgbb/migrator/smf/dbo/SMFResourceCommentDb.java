package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFResourceCommentDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033605924-04:00", comments="Source field: smf_1resource_comments.ID_COMMENT")
    private Integer idComment;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033638873-04:00", comments="Source field: smf_1resource_comments.ID_RESOURCE")
    private Integer idResource;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033658333-04:00", comments="Source field: smf_1resource_comments.ID_MEMBER")
    private Integer idMember;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033678772-04:00", comments="Source field: smf_1resource_comments.postTime")
    private Integer posttime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033621514-04:00", comments="Source field: smf_1resource_comments.ID_COMMENT")
    public Integer getIdComment() {
        return idComment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033631544-04:00", comments="Source field: smf_1resource_comments.ID_COMMENT")
    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033645743-04:00", comments="Source field: smf_1resource_comments.ID_RESOURCE")
    public Integer getIdResource() {
        return idResource;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033652263-04:00", comments="Source field: smf_1resource_comments.ID_RESOURCE")
    public void setIdResource(Integer idResource) {
        this.idResource = idResource;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033664422-04:00", comments="Source field: smf_1resource_comments.ID_MEMBER")
    public Integer getIdMember() {
        return idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033670682-04:00", comments="Source field: smf_1resource_comments.ID_MEMBER")
    public void setIdMember(Integer idMember) {
        this.idMember = idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033684892-04:00", comments="Source field: smf_1resource_comments.postTime")
    public Integer getPosttime() {
        return posttime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033691112-04:00", comments="Source field: smf_1resource_comments.postTime")
    public void setPosttime(Integer posttime) {
        this.posttime = posttime;
    }
}