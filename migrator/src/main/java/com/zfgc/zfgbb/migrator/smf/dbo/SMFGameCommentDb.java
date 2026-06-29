package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFGameCommentDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03222847-04:00", comments="Source field: smf_1game_comments.ID_COMMENT")
    private Integer idComment;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032257739-04:00", comments="Source field: smf_1game_comments.ID_GAME")
    private Integer idGame;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032278868-04:00", comments="Source field: smf_1game_comments.ID_MEMBER")
    private Integer idMember;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032325086-04:00", comments="Source field: smf_1game_comments.postTime")
    private Integer posttime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032241089-04:00", comments="Source field: smf_1game_comments.ID_COMMENT")
    public Integer getIdComment() {
        return idComment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032249709-04:00", comments="Source field: smf_1game_comments.ID_COMMENT")
    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032264658-04:00", comments="Source field: smf_1game_comments.ID_GAME")
    public Integer getIdGame() {
        return idGame;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032272338-04:00", comments="Source field: smf_1game_comments.ID_GAME")
    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032285208-04:00", comments="Source field: smf_1game_comments.ID_MEMBER")
    public Integer getIdMember() {
        return idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032292418-04:00", comments="Source field: smf_1game_comments.ID_MEMBER")
    public void setIdMember(Integer idMember) {
        this.idMember = idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032339986-04:00", comments="Source field: smf_1game_comments.postTime")
    public Integer getPosttime() {
        return posttime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032351746-04:00", comments="Source field: smf_1game_comments.postTime")
    public void setPosttime(Integer posttime) {
        this.posttime = posttime;
    }
}