package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFBoardDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685402893-04:00", comments="Source field: smf_1boards.id_board")
    private Integer idBoard;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685467171-04:00", comments="Source field: smf_1boards.id_cat")
    private Integer idCat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68549333-04:00", comments="Source field: smf_1boards.child_level")
    private Byte childLevel;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68551302-04:00", comments="Source field: smf_1boards.id_parent")
    private Integer idParent;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685533399-04:00", comments="Source field: smf_1boards.board_order")
    private Short boardOrder;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685551648-04:00", comments="Source field: smf_1boards.id_last_msg")
    private Integer idLastMsg;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685569668-04:00", comments="Source field: smf_1boards.id_msg_updated")
    private Integer idMsgUpdated;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685589587-04:00", comments="Source field: smf_1boards.member_groups")
    private String memberGroups;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685610657-04:00", comments="Source field: smf_1boards.id_profile")
    private Short idProfile;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685629326-04:00", comments="Source field: smf_1boards.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685649765-04:00", comments="Source field: smf_1boards.num_topics")
    private Integer numTopics;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685756802-04:00", comments="Source field: smf_1boards.num_posts")
    private Integer numPosts;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685775432-04:00", comments="Source field: smf_1boards.count_posts")
    private Byte countPosts;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685794071-04:00", comments="Source field: smf_1boards.id_theme")
    private Byte idTheme;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685812201-04:00", comments="Source field: smf_1boards.override_theme")
    private Byte overrideTheme;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68583268-04:00", comments="Source field: smf_1boards.unapproved_posts")
    private Short unapprovedPosts;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685860479-04:00", comments="Source field: smf_1boards.unapproved_topics")
    private Short unapprovedTopics;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685879289-04:00", comments="Source field: smf_1boards.redirect")
    private String redirect;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685897578-04:00", comments="Source field: smf_1boards.countMoney")
    private Byte countmoney;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685916438-04:00", comments="Source field: smf_1boards.is_redirect")
    private Byte isRedirect;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685933727-04:00", comments="Source field: smf_1boards.redirect_clicks")
    private Integer redirectClicks;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685951606-04:00", comments="Source field: smf_1boards.redirect_count_clicks")
    private Byte redirectCountClicks;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685445112-04:00", comments="Source field: smf_1boards.id_board")
    public Integer getIdBoard() {
        return idBoard;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685458931-04:00", comments="Source field: smf_1boards.id_board")
    public void setIdBoard(Integer idBoard) {
        this.idBoard = idBoard;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685478841-04:00", comments="Source field: smf_1boards.id_cat")
    public Integer getIdCat() {
        return idCat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68548663-04:00", comments="Source field: smf_1boards.id_cat")
    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68550027-04:00", comments="Source field: smf_1boards.child_level")
    public Byte getChildLevel() {
        return childLevel;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68550714-04:00", comments="Source field: smf_1boards.child_level")
    public void setChildLevel(Byte childLevel) {
        this.childLevel = childLevel;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685521519-04:00", comments="Source field: smf_1boards.id_parent")
    public Integer getIdParent() {
        return idParent;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685528979-04:00", comments="Source field: smf_1boards.id_parent")
    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685539639-04:00", comments="Source field: smf_1boards.board_order")
    public Short getBoardOrder() {
        return boardOrder;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685545959-04:00", comments="Source field: smf_1boards.board_order")
    public void setBoardOrder(Short boardOrder) {
        this.boardOrder = boardOrder;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685557768-04:00", comments="Source field: smf_1boards.id_last_msg")
    public Integer getIdLastMsg() {
        return idLastMsg;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685564138-04:00", comments="Source field: smf_1boards.id_last_msg")
    public void setIdLastMsg(Integer idLastMsg) {
        this.idLastMsg = idLastMsg;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685575668-04:00", comments="Source field: smf_1boards.id_msg_updated")
    public Integer getIdMsgUpdated() {
        return idMsgUpdated;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685581707-04:00", comments="Source field: smf_1boards.id_msg_updated")
    public void setIdMsgUpdated(Integer idMsgUpdated) {
        this.idMsgUpdated = idMsgUpdated;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685595867-04:00", comments="Source field: smf_1boards.member_groups")
    public String getMemberGroups() {
        return memberGroups;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685604367-04:00", comments="Source field: smf_1boards.member_groups")
    public void setMemberGroups(String memberGroups) {
        this.memberGroups = memberGroups;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685617076-04:00", comments="Source field: smf_1boards.id_profile")
    public Short getIdProfile() {
        return idProfile;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685623586-04:00", comments="Source field: smf_1boards.id_profile")
    public void setIdProfile(Short idProfile) {
        this.idProfile = idProfile;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685635846-04:00", comments="Source field: smf_1boards.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685643846-04:00", comments="Source field: smf_1boards.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685734973-04:00", comments="Source field: smf_1boards.num_topics")
    public Integer getNumTopics() {
        return numTopics;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685751882-04:00", comments="Source field: smf_1boards.num_topics")
    public void setNumTopics(Integer numTopics) {
        this.numTopics = numTopics;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685763392-04:00", comments="Source field: smf_1boards.num_posts")
    public Integer getNumPosts() {
        return numPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685771172-04:00", comments="Source field: smf_1boards.num_posts")
    public void setNumPosts(Integer numPosts) {
        this.numPosts = numPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685781582-04:00", comments="Source field: smf_1boards.count_posts")
    public Byte getCountPosts() {
        return countPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685787771-04:00", comments="Source field: smf_1boards.count_posts")
    public void setCountPosts(Byte countPosts) {
        this.countPosts = countPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685800291-04:00", comments="Source field: smf_1boards.id_theme")
    public Byte getIdTheme() {
        return idTheme;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685806581-04:00", comments="Source field: smf_1boards.id_theme")
    public void setIdTheme(Byte idTheme) {
        this.idTheme = idTheme;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68582067-04:00", comments="Source field: smf_1boards.override_theme")
    public Byte getOverrideTheme() {
        return overrideTheme;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685827-04:00", comments="Source field: smf_1boards.override_theme")
    public void setOverrideTheme(Byte overrideTheme) {
        this.overrideTheme = overrideTheme;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.6858478-04:00", comments="Source field: smf_1boards.unapproved_posts")
    public Short getUnapprovedPosts() {
        return unapprovedPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685854659-04:00", comments="Source field: smf_1boards.unapproved_posts")
    public void setUnapprovedPosts(Short unapprovedPosts) {
        this.unapprovedPosts = unapprovedPosts;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685867289-04:00", comments="Source field: smf_1boards.unapproved_topics")
    public Short getUnapprovedTopics() {
        return unapprovedTopics;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685873509-04:00", comments="Source field: smf_1boards.unapproved_topics")
    public void setUnapprovedTopics(Short unapprovedTopics) {
        this.unapprovedTopics = unapprovedTopics;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685885418-04:00", comments="Source field: smf_1boards.redirect")
    public String getRedirect() {
        return redirect;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685893238-04:00", comments="Source field: smf_1boards.redirect")
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685904478-04:00", comments="Source field: smf_1boards.countMoney")
    public Byte getCountmoney() {
        return countmoney;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685910928-04:00", comments="Source field: smf_1boards.countMoney")
    public void setCountmoney(Byte countmoney) {
        this.countmoney = countmoney;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685922357-04:00", comments="Source field: smf_1boards.is_redirect")
    public Byte getIsRedirect() {
        return isRedirect;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685928277-04:00", comments="Source field: smf_1boards.is_redirect")
    public void setIsRedirect(Byte isRedirect) {
        this.isRedirect = isRedirect;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685939917-04:00", comments="Source field: smf_1boards.redirect_clicks")
    public Integer getRedirectClicks() {
        return redirectClicks;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685946077-04:00", comments="Source field: smf_1boards.redirect_clicks")
    public void setRedirectClicks(Integer redirectClicks) {
        this.redirectClicks = redirectClicks;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685957706-04:00", comments="Source field: smf_1boards.redirect_count_clicks")
    public Byte getRedirectCountClicks() {
        return redirectCountClicks;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685963826-04:00", comments="Source field: smf_1boards.redirect_count_clicks")
    public void setRedirectCountClicks(Byte redirectCountClicks) {
        this.redirectCountClicks = redirectCountClicks;
    }
}