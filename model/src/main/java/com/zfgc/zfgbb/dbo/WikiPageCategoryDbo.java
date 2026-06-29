package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class WikiPageCategoryDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724028548-04:00", comments="Source field: zfgbb.wiki_page_category.wiki_page_category_id")
    private Integer wikiPageCategoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724049767-04:00", comments="Source field: zfgbb.wiki_page_category.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724064937-04:00", comments="Source field: zfgbb.wiki_page_category.category_name")
    private String categoryName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724037817-04:00", comments="Source field: zfgbb.wiki_page_category.wiki_page_category_id")
    public Integer getWikiPageCategoryId() {
        return wikiPageCategoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724045427-04:00", comments="Source field: zfgbb.wiki_page_category.wiki_page_category_id")
    public void setWikiPageCategoryId(Integer wikiPageCategoryId) {
        this.wikiPageCategoryId = wikiPageCategoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724054987-04:00", comments="Source field: zfgbb.wiki_page_category.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724059997-04:00", comments="Source field: zfgbb.wiki_page_category.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724070096-04:00", comments="Source field: zfgbb.wiki_page_category.category_name")
    public String getCategoryName() {
        return categoryName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724076046-04:00", comments="Source field: zfgbb.wiki_page_category.category_name")
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public Integer getPkId() {
        return wikiPageCategoryId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}