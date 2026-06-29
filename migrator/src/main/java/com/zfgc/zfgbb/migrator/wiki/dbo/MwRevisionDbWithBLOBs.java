package com.zfgc.zfgbb.migrator.wiki.dbo;

import jakarta.annotation.Generated;

public class MwRevisionDbWithBLOBs extends MwRevisionDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728169862-04:00", comments="Source field: zfgc_wikirevision.rev_comment")
    private byte[] revComment;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728189101-04:00", comments="Source field: zfgc_wikirevision.rev_sha1")
    private byte[] revSha1;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728205151-04:00", comments="Source field: zfgc_wikirevision.rev_content_format")
    private byte[] revContentFormat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72822096-04:00", comments="Source field: zfgc_wikirevision.rev_content_model")
    private byte[] revContentModel;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728177711-04:00", comments="Source field: zfgc_wikirevision.rev_comment")
    public byte[] getRevComment() {
        return revComment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728183541-04:00", comments="Source field: zfgc_wikirevision.rev_comment")
    public void setRevComment(byte[] revComment) {
        this.revComment = revComment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728194741-04:00", comments="Source field: zfgc_wikirevision.rev_sha1")
    public byte[] getRevSha1() {
        return revSha1;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728199951-04:00", comments="Source field: zfgc_wikirevision.rev_sha1")
    public void setRevSha1(byte[] revSha1) {
        this.revSha1 = revSha1;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.7282105-04:00", comments="Source field: zfgc_wikirevision.rev_content_format")
    public byte[] getRevContentFormat() {
        return revContentFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.7282158-04:00", comments="Source field: zfgc_wikirevision.rev_content_format")
    public void setRevContentFormat(byte[] revContentFormat) {
        this.revContentFormat = revContentFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72822647-04:00", comments="Source field: zfgc_wikirevision.rev_content_model")
    public byte[] getRevContentModel() {
        return revContentModel;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72823177-04:00", comments="Source field: zfgc_wikirevision.rev_content_model")
    public void setRevContentModel(byte[] revContentModel) {
        this.revContentModel = revContentModel;
    }
}