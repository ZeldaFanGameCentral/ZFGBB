package com.zfgc.zfgbb.migrator.wiki.dbo;

import jakarta.annotation.Generated;

public class MwTextDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729232318-04:00", comments="Source field: zfgc_wikitext.old_id")
    private Integer oldId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729268417-04:00", comments="Source field: zfgc_wikitext.old_flags")
    private String oldFlags;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729285906-04:00", comments="Source field: zfgc_wikitext.old_text")
    private String oldText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729255867-04:00", comments="Source field: zfgc_wikitext.old_id")
    public Integer getOldId() {
        return oldId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729262687-04:00", comments="Source field: zfgc_wikitext.old_id")
    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729274117-04:00", comments="Source field: zfgc_wikitext.old_flags")
    public String getOldFlags() {
        return oldFlags;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729280737-04:00", comments="Source field: zfgc_wikitext.old_flags")
    public void setOldFlags(String oldFlags) {
        this.oldFlags = oldFlags;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729291636-04:00", comments="Source field: zfgc_wikitext.old_text")
    public String getOldText() {
        return oldText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729297356-04:00", comments="Source field: zfgc_wikitext.old_text")
    public void setOldText(String oldText) {
        this.oldText = oldText;
    }
}