package com.zfgc.zfgbb.migrator.wiki.dbo;

import jakarta.annotation.Generated;

public class MwPageDbWithBLOBs extends MwPageDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726712488-04:00", comments="Source field: zfgc_wikipage.page_restrictions")
    private byte[] pageRestrictions;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726731277-04:00", comments="Source field: zfgc_wikipage.page_content_model")
    private byte[] pageContentModel;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726750357-04:00", comments="Source field: zfgc_wikipage.page_links_updated")
    private byte[] pageLinksUpdated;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726766936-04:00", comments="Source field: zfgc_wikipage.page_lang")
    private byte[] pageLang;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726719678-04:00", comments="Source field: zfgc_wikipage.page_restrictions")
    public byte[] getPageRestrictions() {
        return pageRestrictions;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726725697-04:00", comments="Source field: zfgc_wikipage.page_restrictions")
    public void setPageRestrictions(byte[] pageRestrictions) {
        this.pageRestrictions = pageRestrictions;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726736927-04:00", comments="Source field: zfgc_wikipage.page_content_model")
    public byte[] getPageContentModel() {
        return pageContentModel;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726742467-04:00", comments="Source field: zfgc_wikipage.page_content_model")
    public void setPageContentModel(byte[] pageContentModel) {
        this.pageContentModel = pageContentModel;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726756116-04:00", comments="Source field: zfgc_wikipage.page_links_updated")
    public byte[] getPageLinksUpdated() {
        return pageLinksUpdated;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726761456-04:00", comments="Source field: zfgc_wikipage.page_links_updated")
    public void setPageLinksUpdated(byte[] pageLinksUpdated) {
        this.pageLinksUpdated = pageLinksUpdated;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726772406-04:00", comments="Source field: zfgc_wikipage.page_lang")
    public byte[] getPageLang() {
        return pageLang;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726777796-04:00", comments="Source field: zfgc_wikipage.page_lang")
    public void setPageLang(byte[] pageLang) {
        this.pageLang = pageLang;
    }
}